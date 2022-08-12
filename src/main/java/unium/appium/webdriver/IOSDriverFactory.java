/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unium.appium.webdriver;

import jp.vmi.selenium.webdriver.DriverOptions;
import org.openqa.selenium.WebDriver;

/**
 * used for Selenese Runner Java Integration
 * @author lian.shen
 */
@SuppressWarnings("unused")
public class IOSDriverFactory extends NativeDeviceDriverFactory {
    
    @Override
    public WebDriver newInstance(DriverOptions driverOptions) {
        return buildDriver(IOSAppDriver::new,driverOptions);
    }

}
