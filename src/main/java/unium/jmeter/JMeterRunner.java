package unium.jmeter;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.lazerycode.jmeter.json.TestConfigurations;
import jp.vmi.selenium.selenese.VarsMap;
import org.apache.jmeter.JMeter;
import org.apache.jmeter.config.Argument;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.engine.StandardJMeterEngine;
import org.apache.jmeter.save.SaveService;
import org.apache.jmeter.services.FileServer;
import org.apache.jmeter.testelement.TestPlan;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jorphan.collections.HashTree;
import org.codehaus.plexus.util.FileUtils;
import unium.tester.Tester;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Set;

public class JMeterRunner {

    private Tester tester;

    public JMeterRunner(Tester tester) {
        this.tester = tester;
    }

    public void runJMeter(String jmeterFile) {
        VarsMap varsMap = tester.getSeleneseRunner().getVarsMap();

        StandardJMeterEngine jmeter = new StandardJMeterEngine();
        final Path jmeterHome = buildJMeterHomeFromConfig();
        JMeterUtils.setJMeterHome(jmeterHome.toString());
        final String jmeterProperties = jmeterHome.resolve("bin").resolve("jmeter.properties").toString();
        JMeterUtils.loadJMeterProperties(jmeterProperties);
        JMeterUtils.initLocale();

        File basePath = new File("src/test/resources/JMeter/");
        FileServer.getFileServer().setBase(basePath);

        try {
            SaveService.loadProperties();
        } catch (IOException e) {
            throw new JMeterConfigException("could not load JMeter properties ", e);
        }
        HashTree testPlanTree = buildTestPlanTree(jmeterFile, varsMap, basePath);

        // disabled actions should not be executed
        JMeter.convertSubTree(testPlanTree, true);

        jmeter.configure(testPlanTree);

        jmeter.run();

        jmeter.exit();


    }

    private Path buildJMeterHomeFromConfig() {
        final Path config = Paths.get("target", "config.json");

        Gson gson = new Gson();
        JsonReader reader = null;
        try {
            reader = new JsonReader(new FileReader(config.toFile()));
        } catch (FileNotFoundException e) {
            throw new JMeterConfigException("could not find " + config.toString(), e);
        }
        final TestConfigurations configurations = gson.fromJson(reader, TestConfigurations.class);
        final String jmeterDirectoryPath = configurations.getConfigurations().get(0).getJmeterDirectoryPath();
        return Paths.get(jmeterDirectoryPath);
    }

    private HashTree buildTestPlanTree(String jmeterFile, VarsMap varsMap, File basePath) {
        File jmxFile = FileUtils.resolveFile(basePath, jmeterFile);
        HashTree testPlanTree;
        try {
            testPlanTree = SaveService.loadTree(jmxFile);
        } catch (IOException e) {
            throw new JMeterConfigException("could not load file " + jmxFile.toString(), e);
        }

        TestPlan testPlan = (TestPlan) testPlanTree.getArray()[0];
        Arguments args = testPlan.getArguments();
        ArrayList<Argument> argsList = new ArrayList<>();
        Set<String> keys = varsMap.keySet();
        for (String key : keys) {
            if (key.startsWith("JMeter_")) {
                argsList.add(new Argument(key, varsMap.get(key).toString()));
            }
        }
        args.setArguments(argsList);
        testPlanTree.add(testPlan, new SeleniumResultCollector(tester));
        return testPlanTree;
    }


    private static class JMeterConfigException extends RuntimeException {
        public JMeterConfigException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
