package unium.appium.webdriver;

import io.appium.java_client.AppiumDriver;
import jp.vmi.selenium.webdriver.DriverOptions;
import jp.vmi.selenium.webdriver.WebDriverFactory;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.function.BiFunction;

import static jp.vmi.selenium.webdriver.DriverOptions.DriverOption.*;

public abstract class NativeDeviceDriverFactory extends WebDriverFactory {
    private static final Logger log = LoggerFactory.getLogger(NativeDeviceDriverFactory.class);
    private URL buildUrl(DriverOptions driverOptions) {
        URL url;
        if (driverOptions.has(REMOTE_URL)) {
            try {
                url = new URL(driverOptions.get(REMOTE_URL));
            } catch (MalformedURLException e) {
                throw new IllegalArgumentException("Invalid --remote-url: " + e.getMessage());
            }
        } else {
            throw new IllegalArgumentException("Require --remote-url to know where to connect to");
        }
        return url;
    }

    private DesiredCapabilities buildDesiredCapabilities(DriverOptions driverOptions) {
        DesiredCapabilities caps = setupProxy(new DesiredCapabilities(), driverOptions);
        // Set capabilities according to https://github.com/appium/appium/blob/master/docs/caps.md
        caps.setBrowserName("");
        if (driverOptions.has(REMOTE_BROWSER))
            caps.setCapability("app", driverOptions.get(REMOTE_BROWSER));
        if (driverOptions.has(REMOTE_PLATFORM))
            caps.setCapability("device", driverOptions.get(REMOTE_PLATFORM));
        if (driverOptions.has(REMOTE_VERSION))
            caps.setCapability(CapabilityType.BROWSER_VERSION, driverOptions.get(REMOTE_VERSION));
        caps.merge(driverOptions.getCapabilities());
        return caps;
    }

    protected AppiumDriver buildDriver(BiFunction<URL, Capabilities,AppiumDriver> driverConstructor,DriverOptions driverOptions){
        final DesiredCapabilities desiredCapabilities = buildDesiredCapabilities(driverOptions);
        final AppiumDriver driver = driverConstructor.apply(buildUrl(driverOptions), desiredCapabilities);
        log.info(desiredCapabilities.toString());
        log.info(MessageFormat.format("Session ID: {0}", driver.getSessionId()));
        return driver;
    }
}
