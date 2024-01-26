/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unium.appium.appcommand;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.ios.IOSDriver;
import jp.vmi.selenium.selenese.Context;
import jp.vmi.selenium.selenese.command.AbstractCommand;
import jp.vmi.selenium.selenese.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

import static jp.vmi.selenium.selenese.command.ArgumentType.LOCATOR;
import static jp.vmi.selenium.selenese.result.Success.SUCCESS;

/**
 *
 * @author lian.shen
 */
public class AppPressKey extends AbstractCommand {
    private static final int ARG_LOCATOR = 0;

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    
    public AppPressKey(int index, String name, String... args) {
        super(index, name, args, LOCATOR);
    }

    @Override
    protected Result executeImpl(Context context, String[] curArgs) {
        final String locator = curArgs[ARG_LOCATOR];
        
        if(context.getWrappedDriver().getClass().getName().endsWith("AndroidAppDriver")) {
            // android
            log.info("Android");
            AndroidDriver driver = (AndroidDriver) context.getWrappedDriver();

            try {
                driver.pressKey(new KeyEvent().withKey(AndroidKey.valueOf(locator)));
            } catch (IllegalArgumentException | SecurityException ex) {
                log.info(ex.getMessage());
                return new jp.vmi.selenium.selenese.result.Error("Failed to press key: " + locator);
            }
        } else if(context.getWrappedDriver().getClass().getName().endsWith("IOSAppDriver")) {
            // ios only the home button can be pressed. just let the app run in background
            log.info("iOS");
            if(locator.equals("HOME")) {
                IOSDriver driver = (IOSDriver)context.getWrappedDriver();
                driver.runAppInBackground(Duration.ofSeconds(-1));
            } else {
                return new jp.vmi.selenium.selenese.result.Error("Failed to press key: " + locator);
            }
        } else {
            return new jp.vmi.selenium.selenese.result.Error("Unkown webdriver: " + context.getWrappedDriver().getClass().getName());
        }        

        return SUCCESS;
    }
    
}
