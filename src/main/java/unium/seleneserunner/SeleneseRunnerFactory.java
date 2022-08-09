/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unium.seleneserunner;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import jp.vmi.selenium.selenese.Runner;
import jp.vmi.selenium.selenese.VarsMap;
import jp.vmi.selenium.selenese.command.ICommandFactory;
import jp.vmi.selenium.selenese.config.DefaultConfig;
import jp.vmi.selenium.selenese.config.IConfig;
import jp.vmi.selenium.selenese.log.CookieFilter;
import jp.vmi.selenium.selenese.log.CookieFilter.FilterType;
import jp.vmi.selenium.selenese.log.LogFilter;
import jp.vmi.selenium.webdriver.DriverOptions;
import jp.vmi.selenium.webdriver.DriverOptions.DriverOption;
import jp.vmi.selenium.webdriver.WebDriverManager;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static jp.vmi.selenium.selenese.config.DefaultConfig.DEFAULT_TIMEOUT_MILLISEC_N;

/**
 * @author lian.shen
 */
public class SeleneseRunnerFactory {
    private static final Logger LOG = LoggerFactory.getLogger(SeleneseRunnerFactory.class);

    private static final Pattern EXPR_RE = Pattern.compile("\\s*(?<varName>\\w+)\\s*=\\s*(?<jsonValue>.*)");


    public Runner newInstance() {
        Runner runner = new Runner();

        String[] args = ConfigContainer.getConfigArgs();

        if (0 == args.length) {
            throw new IllegalArgumentException("There were no config arguments supplied");
        }

        IConfig config = new DefaultConfig(args);

        WebDriverManager manager = getWebDriverManagerFromConfig(config);
        applyCommandFactoryFromConfig(runner, config);
        runner.setDriver(manager.get());
        runner.setWebDriverPreparator(manager);
        runner.setHighlight(config.isHighlight());
        runner.getInteractiveModeHandler().setEnabled(config.isInteractive());
        runner.setScreenshotDir(config.getScreenshotDir());
        runner.setScreenshotAllDir(config.getScreenshotAll());
        runner.setScreenshotOnFailDir(config.getScreenshotOnFail());
        runner.setOverridingBaseURL(config.getBaseurl());
        runner.setIgnoredScreenshotCommand(config.isIgnoreScreenshotCommand());
        applyVarsMapFromConfig(runner, config);
        for (String rollup : ArrayUtils.nullToEmpty(config.getRollup())) {
            runner.getRollupRules().load(rollup);
        }
        LogFilter.parse(runner.getLogFilter(), ArrayUtils.nullToEmpty(config.getLogFilter()));
        applyCookieFilterFromConfig(runner, config);
        runner.setJUnitResultDir(config.getXmlResult());
        runner.setHtmlResultDir(config.getHtmlResult());
        setTimeOuts(runner, config);

        runner.setPrintStream(System.out);

        return runner;
    }

    private void applyCookieFilterFromConfig(Runner runner, IConfig config) {
        if (config.getCookieFilter() != null) {
            String cookieFilter = config.getCookieFilter();
            if (cookieFilter.length() < 2) throw new IllegalArgumentException("invalid cookie filter format: " + cookieFilter);
            FilterType filterType;
            switch (cookieFilter.charAt(0)) {
                case '+':
                    filterType = FilterType.PASS;
                    break;
                case '-':
                    filterType = FilterType.SKIP;
                    break;
                default:
                    throw new IllegalArgumentException("invalid cookie filter format: " + cookieFilter);
            }
            String pattern = cookieFilter.substring(1);
            runner.setCookieFilter(new CookieFilter(filterType, pattern));
        }
    }

    private void applyCommandFactoryFromConfig(Runner runner, IConfig config) {
        if (config.getCommandFactory() != null) {
            String factoryName = config.getCommandFactory();
            ICommandFactory factory;
            try {
                Class<?> factoryClass = Class.forName(factoryName);
                factory = (ICommandFactory) factoryClass.getConstructor().newInstance();
            } catch (Exception e) {
                throw new IllegalArgumentException("invalid user defined command factory: " + factoryName);
            }
            runner.getCommandFactory().registerCommandFactory(factory);
            LOG.info("Registered: {}", factoryName);
        } else {
            if (config.getDriver().contains("AndroidDriverFactory") || config.getDriver().contains("IOSDriverFactory")) {
                throw new IllegalArgumentException("--command-factory CommandFactory");
            }
        }
    }

    private void applyVarsMapFromConfig(Runner runner, IConfig config) {
        if (config.getVar() != null) {
            Gson gson = new Gson();
            VarsMap varsMap = runner.getVarsMap();
            for (String expr : config.getVar()) {
                Matcher matcher = EXPR_RE.matcher(expr);
                if (!matcher.matches()) throw new IllegalArgumentException("invalid var option format: " + expr);
                String name = matcher.group("varName");
                Object value;
                try {
                    value = gson.fromJson(matcher.group("jsonValue"), Object.class);
                } catch (JsonSyntaxException e) {
                    throw new IllegalArgumentException("JSON syntax error: " + expr);
                }
                varsMap.put(name, value);
            }
        }
    }

    private void setTimeOuts(Runner runner, IConfig config) {

        if (config.getMaxTime() != null) {
            long maxTime = NumberUtils.toLong(config.getMaxTime(), 0);
            if (maxTime <= 0) throw new IllegalArgumentException("Invalid max time value. (" + config.getMaxTime() + ")");
            runner.setupMaxTimeTimer(maxTime * 1000);
        }

        // this is a override function. in this case always on
        int timeout = NumberUtils.toInt(config.getTimeout(), DEFAULT_TIMEOUT_MILLISEC_N);
        if (timeout <= 0) throw new IllegalArgumentException("Invalid timeout value. (" + config.getTimeout() + ")");
        runner.setTimeout(timeout);

        // speed must be set
        int speed = NumberUtils.toInt(config.getSetSpeed(), 0);
        if (speed < 0) throw new IllegalArgumentException("Invalid speed value. (" + config.getSetSpeed() + ")");
        runner.setInitialSpeed(speed);
        int sstimeout = NumberUtils.toInt(config.getScreenshotScrollTimeout(), 100);
        if (sstimeout < 0) throw new IllegalArgumentException("Invalid screenshot scroll timeout value. (" + config.getScreenshotScrollTimeout() + ")");
        runner.setScreenshotScrollTimeout(sstimeout);
    }

    private WebDriverManager getWebDriverManagerFromConfig(IConfig config) {
        String driverName = config.getDriver();
        DriverOptions driverOptions = new DriverOptions(config);
        if (driverName == null) {
            if (driverOptions.has(DriverOption.FIREFOX) || driverOptions.has(DriverOption.GECKODRIVER)) {
                driverName = WebDriverManager.FIREFOX;
            } else if (driverOptions.has(DriverOption.CHROMEDRIVER)) {
                driverName = WebDriverManager.CHROME;
            } else if (driverOptions.has(DriverOption.IEDRIVER)) {
                driverName = WebDriverManager.IE;
            } else if (driverOptions.has(DriverOption.EDGEDRIVER)) {
                driverName = WebDriverManager.EDGE;
            };
        }
        WebDriverManager manager = WebDriverManager.newInstance();
        manager.setWebDriverFactory(driverName);
        manager.setDriverOptions(driverOptions);
        return manager;
    }


}
