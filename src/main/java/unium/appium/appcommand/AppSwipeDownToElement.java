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
import jp.vmi.selenium.selenese.utils.Wait;
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
public class AppSwipeDownToElement extends AbstractCommand {
    private static final int ARG_LOCATOR = 0;
    private static final int ARG_VALUE = 1;
    private final long DEFAULT_TIMEOUT = 60000;

    private static final Logger LOG = LoggerFactory.getLogger(AppSwipeDownPercent.class);

    WebElement parent = null;

    public AppSwipeDownToElement(int index, String name, String... args) {
        super(index, name, args, LOCATOR, VALUE);
    }

    @Override
    protected Result executeImpl(Context context, String[] curArgs) {

        LOG.info(Arrays.toString(curArgs));

        final String locator = curArgs[ARG_LOCATOR];
        final String value = curArgs[ARG_VALUE];

        long timeout = DEFAULT_TIMEOUT;

        final By bySpinnerLocator = LocatorBySetter.setBy(locator);
        final By byValueLocator = LocatorBySetter.setBy(value);

        AppiumDriver driver = (AppiumDriver) context.getWrappedDriver();

        try {
            WebElement elementToFindFirst = driver.findElement(byValueLocator);
            if(null != elementToFindFirst) {
                if(context.getWrappedDriver().getClass().getName().contains("IOSWebDriver")) {
                    try {
                        String visible = elementToFindFirst.getAttribute("visible");
                        // if attribute not found go to catch
                        if (!visible.equals("false")) {
                            LOG.info("Element found without swipe");
                            return SUCCESS;
                        }
                    } catch(Exception e) {
                        // if attribute visible not found it is android and can return true
                    }
                } else {
                    LOG.info("Element found without swipe");
                    return SUCCESS;
                }
            }
        } catch (Exception e) {
            //not jet
        }

        if (timeout <= 0) {
            timeout = DEFAULT_TIMEOUT; // set to default
        }

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(timeout));
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(bySpinnerLocator));
            parent = driver.findElement(bySpinnerLocator);
        } catch (TimeoutException e) {
            return new jp.vmi.selenium.selenese.result.Error("Failed to find parent within " + timeout + " ms");
        }

        if (null == parent) {
            return new jp.vmi.selenium.selenese.result.Error(locator + " not found!");
        }

        LOG.info("Parent element found: " + locator);

        int parentHeight = parent.getSize().getHeight();
        int parentWidth = parent.getSize().getWidth();
        Point parentLocation = parent.getLocation();

        long startTime = System.currentTimeMillis();

        Wait.StopCondition condition = () -> {
            WebElement element = null;
            try {
                Thread.sleep(300);
                element = driver.findElement(byValueLocator);
            } catch (Exception e) {
                //not jet
            }

            if (element == null) {
                // swap
                PointerInput finger1 = new PointerInput(PointerInput.Kind.TOUCH, "finger1");
                Sequence SwipeSequence = new Sequence(finger1, 0);

                SwipeSequence.addAction(finger1.createPointerMove(Duration.ZERO,
                        PointerInput.Origin.viewport(),
                        (int) (parentLocation.x + parentWidth * 0.5),
                        (int) (parentLocation.y + parentHeight * 0.5)));
                SwipeSequence.addAction(finger1.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
                SwipeSequence.addAction(finger1.createPointerMove(Duration.ofMillis(300),
                        PointerInput.Origin.viewport(),
                        (int) (parentLocation.x + parentWidth * 0.5),
                        (int) (parentLocation.y + parentHeight * 0.1)));
                SwipeSequence.addAction(finger1.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

                ArrayList<Sequence> swipeCommand = new ArrayList<>();
                swipeCommand.add(SwipeSequence);

                return false;
            } else if(context.getWrappedDriver().getClass().getName().contains("IOSWebDriver")) {
                try {
                    String visible = element.getAttribute("visible");
                    // if attribute not found go to catch
                    if (visible.equals("false")) {
                        // swap
                        PointerInput finger1 = new PointerInput(PointerInput.Kind.TOUCH, "finger1");
                        Sequence SwipeSequence = new Sequence(finger1, 0);

                        SwipeSequence.addAction(finger1.createPointerMove(Duration.ZERO,
                                PointerInput.Origin.viewport(),
                                (int) (parentLocation.x + parentWidth * 0.5),
                                (int) (parentLocation.y + parentHeight * 0.5)));
                        SwipeSequence.addAction(finger1.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
                        SwipeSequence.addAction(finger1.createPointerMove(Duration.ofMillis(300),
                                PointerInput.Origin.viewport(),
                                (int) (parentLocation.x + parentWidth * 0.5),
                                (int) (parentLocation.y + parentHeight * 0.2)));
                        SwipeSequence.addAction(finger1.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

                        ArrayList<Sequence> swipeCommand = new ArrayList<>();
                        swipeCommand.add(SwipeSequence);

                        return false;
                    }
                } catch(Exception e) {
                    // if attribute visible not found it is android and can return true
                }
            }
            return true;
        };
        if (!Wait.defaultInterval.wait(startTime, timeout, condition)) {
            return new jp.vmi.selenium.selenese.result.Error("Failed to load page within " + timeout + " ms");
        }
        return SUCCESS;
    }

}