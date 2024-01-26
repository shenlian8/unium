package unium.config;

import org.apache.commons.lang3.StringUtils;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import unium.seleneserunner.ConfigContainer;

import java.io.File;

public class SeleneseConfig implements TestRule {

    private static String configFilePath = "src/test/resources/environment/";
    private String configFile = configFilePath + "LocalChrome.yml";

    public SeleneseConfig() {
    }

    public SeleneseConfig(String configFile) {
        String configFilePerSystem = configFile;
        this.configFile = configFilePath + configFilePerSystem;
    }

    @Override
    public Statement apply(Statement base, Description description) {
        File configurationFile = new File(this.configFile);
        String configFilePathName = configurationFile.getAbsolutePath();
        String[] configArgs = new String[]{"--config", configFilePathName};
        ConfigContainer.setConfigArgs(configArgs);
        if (StringUtils.isNotEmpty(System.getProperty("baseUrl"))) {
            System.out.println("Testing with baseUrl= " + System.getProperty("baseUrl"));
            ConfigContainer.appendConfigArg("--baseurl", System.getProperty("baseUrl"));
        }
        return base;
    }
}
