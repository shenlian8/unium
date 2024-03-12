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
import org.openqa.selenium.Point;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;

import static jp.vmi.selenium.selenese.command.ArgumentType.LOCATOR;
import static jp.vmi.selenium.selenese.command.ArgumentType.VALUE;
import static jp.vmi.selenium.selenese.result.Success.SUCCESS;

/**
 *
 * @author lian.shen
 */
public class AppLongPress extends AbstractCommand {
    private static final int ARG_LOCATOR = 0;
    private static final int ARG_VALUE = 1;
    private final long DEFAULT_TIMEOUT = 2000;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    WebElement parent = null;
    
    public AppLongPress(int index, String name,  String... args) {
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
            parent = driver.findElement(byLocator);
        } catch (TimeoutException e) {
            return new jp.vmi.selenium.selenese.result.Error("Failed to find parent within " + timeout + " ms");
        }

        if (null == parent) {
            return new jp.vmi.selenium.selenese.result.Error(locator + " not found!");
        }

        Point parentLocation = parent.getLocation();

        PointerInput finger1 = new PointerInput(PointerInput.Kind.TOUCH, "finger1");
        Sequence finger1LongTap = new Sequence(finger1, 0);
        finger1LongTap.addAction(finger1.createPointerMove(Duration.ZERO,
                PointerInput.Origin.viewport(),
                parentLocation.x,
                parentLocation.y));
        finger1LongTap.addAction(finger1.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        finger1LongTap.addAction(finger1.createPointerMove(Duration.ofMillis(3000),
                PointerInput.Origin.viewport(),
                parentLocation.x,
                parentLocation.y));
        finger1LongTap.addAction(finger1.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        ArrayList<Sequence> oneFingerLongTap = new ArrayList<>();
        oneFingerLongTap.add(finger1LongTap);

        driver.perform(oneFingerLongTap);

        return SUCCESS;
    }
}
