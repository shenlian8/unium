package unium.seleneserunner;

import org.apache.commons.lang3.ArrayUtils;

public class ConfigContainer {
    private static ThreadLocal<String[]> configArgs = new ThreadLocal<>();

    private ConfigContainer() {
    }

    public static String[] getConfigArgs() {
        if (ConfigContainer.configArgs.get() == null) {
            ConfigContainer.configArgs.set(new String[]{});
        }
        return configArgs.get();
    }

    public static void setConfigArgs(String[] configArgs) {
        ConfigContainer.configArgs.set(configArgs);
    }

    public static void removeTester() {
        ConfigContainer.configArgs.remove();
    }

    public static void appendConfigArg(String key, String value) {
        String[] configArgsAsArray=ConfigContainer.configArgs.get();
        configArgsAsArray = ArrayUtils.add(configArgsAsArray, key);
        configArgsAsArray = ArrayUtils.add(configArgsAsArray, value);
        setConfigArgs(configArgsAsArray);
    }
}
