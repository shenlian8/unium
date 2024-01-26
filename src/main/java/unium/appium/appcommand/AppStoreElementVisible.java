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
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

import static jp.vmi.selenium.selenese.command.ArgumentType.LOCATOR;
import static jp.vmi.selenium.selenese.command.ArgumentType.VALUE;

/**
 *
 * @author lian.shen
 */
public class AppStoreElementVisible extends AbstractCommand {
    private static final int ARG_LOCATOR = 0;
    private static final int ARG_VALUE = 1;

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    
    public AppStoreElementVisible(int index, String name, String... args) {
        super(index, name, args, LOCATOR, VALUE);
    }

    @Override
    protected Result executeImpl(Context context, String[] curArgs) {

        log.info(Arrays.toString(curArgs));
        
        final String locator = curArgs[ARG_LOCATOR];
        final String varName = curArgs[ARG_VALUE];

        final By byLocator = LocatorBySetter.setBy(locator);

        AppiumDriver driver = (AppiumDriver) context.getWrappedDriver();
        
        WebElement element = null;
        try {
            element = driver.findElement(byLocator);
        } catch (Exception e) {
             //not jet
        } 
        
        context.getVarsMap().put(varName, "unknow");
        
        String result;
        String valueToStore;
        if(element != null) {
            try {
                String visibleValue = element.getAttribute("visible");
                result = "Element found and visible attribute found. Visible: " + visibleValue;
                valueToStore = "found and visible: " + visibleValue;                   
            } catch (Exception e) {
                result = "Element found, no visible attribute found";
                valueToStore = "found, no visible attribute";       
            }
        } else {
            result = "Element not found";
            valueToStore = "not found";
        }
        context.getVarsMap().put(varName, valueToStore);
        return new Success(result);
    }
    
}
