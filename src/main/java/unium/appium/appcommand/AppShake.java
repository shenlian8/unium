/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unium.appium.appcommand;

import io.appium.java_client.ios.IOSDriver;
import jp.vmi.selenium.selenese.Context;
import jp.vmi.selenium.selenese.command.AbstractCommand;
import jp.vmi.selenium.selenese.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static jp.vmi.selenium.selenese.result.Success.SUCCESS;

/**
 *
 * @author lian.shen
 */
public class AppShake extends AbstractCommand {
    private static final int ARG_LOCATOR = 0;
    private static final int ARG_VALUE = 1;

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    
    public AppShake(int index, String name, String... args) {
        super(index, name, args);
    }

    @Override
    protected Result executeImpl(Context context, String[] curArgs) {

        if (context.getWrappedDriver().getClass().getName().endsWith("AndroidWebDriver")) {
            // android
            return new jp.vmi.selenium.selenese.result.Error("Shake not suported in Android " + context.getWrappedDriver().getClass().getName());
        } else if (context.getWrappedDriver().getClass().getName().endsWith("IOSWebDriver")) {
            log.info("Shake under iOS");
            IOSDriver driver = (IOSDriver) context.getWrappedDriver();
            driver.shake();
        } else {
            return new jp.vmi.selenium.selenese.result.Error("Unkown webdriver: " + context.getWrappedDriver().getClass().getName());
        }

        return SUCCESS;
    }
    
}
