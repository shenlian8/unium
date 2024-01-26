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
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

import static jp.vmi.selenium.selenese.command.ArgumentType.LOCATOR;
import static jp.vmi.selenium.selenese.result.Success.SUCCESS;

/**
 *
 * @author lian.shen
 */
public class AppAssertElementPresent extends AbstractCommand {
    private static final int ARG_LOCATOR = 0;
    private static final int ARG_VALUE = 1;
    
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    
    public AppAssertElementPresent(int index, String name, String... args) {
        super(index, name, args, LOCATOR);
    }

    @Override
    protected Result executeImpl(Context context, String... curArgs) {

        log.info(Arrays.toString(curArgs));
        
        final String locator = curArgs[ARG_LOCATOR];
        
        final By byLocator = LocatorBySetter.setBy(locator);
        
        AppiumDriver driver = (AppiumDriver) context.getWrappedDriver();
        WebElement element = driver.findElement(byLocator);

        if (element != null) {
            return SUCCESS;
        }
        return new jp.vmi.selenium.selenese.result.Error(locator + " not found!");
    }
}
