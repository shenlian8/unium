package unium.tester;

import io.appium.java_client.remote.MobilePlatform;
import unium.jmeter.JMeterRunner;
import jp.vmi.html.result.HtmlResult;
import jp.vmi.selenium.selenese.*;
import jp.vmi.selenium.selenese.command.CommandList;
import jp.vmi.selenium.selenese.command.CommandSequence;
import jp.vmi.selenium.selenese.command.ICommand;
import jp.vmi.selenium.selenese.command.Screenshot;
import jp.vmi.selenium.selenese.inject.DoCommand;
import jp.vmi.selenium.selenese.result.Error;
import jp.vmi.selenium.selenese.result.*;
import jp.vmi.selenium.selenese.utils.LogRecorder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import unium.seleneserunner.ConfigContainer;
import unium.seleneserunner.SeleneseRunnerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static jp.vmi.selenium.selenese.result.Success.SUCCESS;
import static jp.vmi.selenium.selenese.result.Unexecuted.UNEXECUTED;

public class Tester extends Commander {
    private static final Pattern JS_BLOCK_RE = Pattern.compile("javascript\\{(.*)\\}", Pattern.DOTALL);
    private final Logger log = LoggerFactory.getLogger(Tester.class);
    private static String configFilePath = "src/test/resources/environment/";
    private String configFile;

    protected Runner seleneseRunner;
    CommandSequence sequence;
    private boolean isAborted = false;

    // just for debug. if set skip commands in debug mode
    private boolean toSkip = false;
    private TestSuite testSuite = null;

    protected Tester() {
        super();
    }

    private void initTester(String mobilePlatform, String configFile) {
        initConfig(mobilePlatform, configFile);
        SeleneseRunnerFactory trf = new SeleneseRunnerFactory();
        this.seleneseRunner = trf.newInstance();
    }
    public static Tester createTester(String configFile) {
        Tester tester = InterceptorBinder.newTester();
        tester.initTester("", configFile);
        return tester;
    }

    public static Tester createIosTester(String configFile) {
        Tester tester = InterceptorBinder.newTester();
        tester.initTester(MobilePlatform.IOS, configFile);
        return tester;
    }

    public static Tester createAndroidTester(String configFile) {
        Tester tester = InterceptorBinder.newTester();
        tester.initTester(MobilePlatform.ANDROID, configFile);
        return tester;
    }

    private void initConfig(String mobilePlatform, String configFileName) {
        this.configFile = configFilePath + configFileName;
        File configurationFile = new File(this.configFile);
        if(! configurationFile.exists()) {
            configurationFile = new File(configFileName);
        }
        String configFilePathName = configurationFile.getAbsolutePath();
        String[] configArgs = new String[]{"--config", configFilePathName};
        ConfigContainer.setConfigArgs(configArgs);
        if(mobilePlatform == MobilePlatform.IOS) {
            ConfigContainer.appendConfigArg("--driver", "unium.appium.webdriver.IOSDriverFactory");
        }
        if(mobilePlatform == MobilePlatform.ANDROID) {
            ConfigContainer.appendConfigArg("--driver", "unium.appium.webdriver.AndroidDriverFactory");
        }
        ConfigContainer.appendConfigArg("--command-factory", "unium.appium.appcommand.CommandFactory");
    }

    public void startTestSuite(String featureName) {
        testSuite = new TestSuite().initialize(null, featureName);
        testSuite.setWebDriverName(seleneseRunner.getWrappedDriver().getClass().getSimpleName());
        this.testSuite.getStopWatch().start();
    }

    public TestSuite getTestSuite() {
        return testSuite;
    }

    public void endTestSuite() {
        testSuite.getStopWatch().end();
        for (Selenese testCase : testSuite.getSeleneseList()) {
            final Result result = testCase.getResult();
            testSuite.setResult(testSuite.getResult().updateWithChildResult(testCase, result));
        }

        if ((testSuite.getResult().getLevel().value == Result.Level.WARNING.value)) {
            testSuite.setResult(SUCCESS);
        }

        HtmlResult htmlResult = (seleneseRunner != null) ? seleneseRunner.getHtmlResult() : null;

        if (testSuite.getParent() == null && htmlResult != null) {
            htmlResult.generate(testSuite);
        }
    }

    public void startTestCase(String sceratioName) {

        TestCase tc = new TestCase().initialize(SourceType.SELENESE, null, sceratioName, "");
        testSuite.addSelenese(tc);

        tc.getStopWatch().start();

        CommandList cl = tc.getCommandList();
        seleneseRunner.pushCommandListIterator(cl.iterator());
        sequence = cl.iterator().getCommandSequence();

        LogRecorder lr = new LogRecorder(seleneseRunner.getPrintStream());
        tc.setLogRecorder(lr);

        seleneseRunner.setCurrentTestCase(tc);

        isAborted = false;
    }

    public void endTestCase() {
        TestCase testCase = seleneseRunner.getCurrentTestCase();

        testCase.getStopWatch().end();
    }

    public Runner getSeleneseRunner() {
        return seleneseRunner;
    }

    public void setSeleneseRunner(Runner seleneseRunner) {
        this.seleneseRunner = seleneseRunner;
    }

    public boolean isAborted() {
        return isAborted;
    }

    public void setToSkip(boolean toSkip) {
        this.toSkip = toSkip;
    }

    public void quitSeleneseRunner() {
        endTestSuite();
        try {
            seleneseRunner.getWrappedDriver().quit();
        } catch (Exception e) {
            // if the web driver is already killed, no problem
        }
    }

    @DoCommand
    protected Result doCommand(Context context, ICommand command, String... curArgs) {
        try {
            if (!isAborted) {
                seleneseRunner.waitSpeed();
                Result result = command.execute(context, curArgs);
                if (!isDebug()) {
                    isAborted = result.isAborted();
                }
                return result;
            } else {
                return new Unexecuted(new Warning("Skipped"));
            }
        } catch (SeleneseCommandErrorException e) {
            return e.getError();
        } catch (Exception e) {
            return new Error(e);
        }
    }

    @Override
    public Result execCommand(String commandName, String... args) {
        if (shouldBeSkipped()) {
            return UNEXECUTED;
        }

        Integer commandIndex = seleneseRunner.getCurrentTestCase().getCommandList().size() + 1;
        ICommand command = seleneseRunner.getCommandFactory().newCommand(commandIndex, commandName, args);
        sequence.increment(command);
        List<Screenshot> ss = command.getScreenshots();
        int prevSSIndex = (ss == null) ? 0 : ss.size();
        String[] curArgs = command.getVariableResolvedArguments(seleneseRunner.getCurrentTestCase().getSourceType(), seleneseRunner.getVarsMap());
        evalCurArgs(seleneseRunner, curArgs);
        seleneseRunner.getCurrentTestCase().addCommand(command);
        seleneseRunner.getCommandListIterator().getCommandSequence().increment(command);

        Result result = this.doCommand(seleneseRunner, command, curArgs);

        ss = command.getScreenshots();
        List<Screenshot> newSS;
        if (ss == null || prevSSIndex == ss.size()) {
            newSS = null;
        } else {
            newSS = new ArrayList<>(ss.subList(prevSSIndex, ss.size()));
        }
        CommandResultList cresultList = seleneseRunner.getCurrentTestCase().getResultList();
        CommandResult cresult = new CommandResult(sequence.toString(), command, newSS, result, cresultList.getEndTime(), System.currentTimeMillis());
        cresultList.add(cresult);


        if (result.isAborted() && (!isDebug())) {
            throw new SeleneseCommandErrorException(result.getMessage());
        }

        return result;
    }

    @Override
    public Result runJmeter(String... args) {
        if (shouldBeSkipped()) {
            return SUCCESS;
        }

        String jmeterFile = "";
        try {
            jmeterFile = args[0];
        } catch (Exception e) {
            // do nothing
        }
        this.execCommand("comment", "Start JMeter " + jmeterFile);

        JMeterRunner jMeterRunner = new JMeterRunner(this);
        jMeterRunner.runJMeter(jmeterFile);

        return SUCCESS;
    }

    protected void evalCurArgs(Context context, String[] curArgs) {
        for (int i = 0; i < curArgs.length; i++) {
            Matcher matcher = JS_BLOCK_RE.matcher(curArgs[i]);
            if (matcher.matches()) {
                Object value = context.getEval().eval(context, matcher.group(1));
                if (value == null) value = "";
                curArgs[i] = value.toString();
            }
        }
    }


    public String getVariable(final String key) {
        final VarsMap varsMap = this.getSeleneseRunner().getVarsMap();
        return String.valueOf(varsMap.getOrDefault(key, ""));
    }

    private boolean shouldBeSkipped() {
        return isDebug() && toSkip;
    }

    private boolean isDebug() {
        return java.lang.management.ManagementFactory.getRuntimeMXBean().
            getInputArguments().toString().contains("-agentlib:jdwp");
    }

    private String formatSuiteName(String featureString) {
        featureString = featureString.replaceAll("\\\\", "/");

        String featureName = featureString.substring(featureString.lastIndexOf("/") + 1);

        featureName = featureName.replaceAll(":", ".");

        return featureName;
    }

}
