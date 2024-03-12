/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unium.appium.appcommand;

import io.appium.java_client.AppiumDriver;
import jp.vmi.selenium.selenese.Context;
import jp.vmi.selenium.selenese.command.AbstractCommand;
import jp.vmi.selenium.selenese.result.Result;
import jp.vmi.selenium.selenese.result.Success;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Arrays;

import static jp.vmi.selenium.selenese.command.ArgumentType.LOCATOR;
import static jp.vmi.selenium.selenese.command.ArgumentType.VALUE;
import static jp.vmi.selenium.selenese.result.Success.SUCCESS;

/**
 *
 * @author lian.shen
 */
public class AppClickIfExists extends AbstractCommand {
    private static final int ARG_LOCATOR = 0;
    private static final int ARG_VALUE = 1;
    private final long DEFAULT_TIMEOUT = 2000;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public AppClickIfExists(int index, String name, String... args) {
        super(index, name, args, LOCATOR, VALUE);
    }

    @Override
    protected Result executeImpl(Context context, String[] curArgs) { 

        log.info(Arrays.toString(curArgs));

        final String locator = curArgs[ARG_LOCATOR];
        final String value = curArgs[ARG_VALUE];

        long timeout = 0;
        try {
            timeout = Long.parseLong(value);
        } catch (NumberFormatException ex) {
            // do nothing
        } 

        final By byLocator = LocatorBySetter.setBy(locator);
        
        AppiumDriver driver = (AppiumDriver) context.getWrappedDriver();

        if (timeout <= 0) {
            timeout = DEFAULT_TIMEOUT; // set to default
        }

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(timeout));
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(byLocator));
            driver.findElement(byLocator).click();
        } catch (TimeoutException e) {
            return new Success("Failed to find element within " + timeout + " ms");
        }

        return SUCCESS;
    }
    
}
