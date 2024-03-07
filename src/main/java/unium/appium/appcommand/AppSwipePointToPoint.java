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
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
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
public class AppSwipePointToPoint extends AbstractCommand {
    private static final int ARG_LOCATOR = 0;
    private static final int ARG_VALUE = 1;
    private final long DEFAULT_TIMEOUT = 3000;

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    WebElement parent = null;

    public AppSwipePointToPoint(int index, String name, String... args) {
        super(index, name, args, LOCATOR, VALUE);
    }

    @Override
    protected Result executeImpl(Context context, String[] curArgs) {

        LOG.info(Arrays.toString(curArgs));
        
        final String locator = curArgs[ARG_LOCATOR];
        final String value = curArgs[ARG_VALUE];
        
        long timeout = DEFAULT_TIMEOUT;
        
        final By bySpinnerLocator = LocatorBySetter.setBy(locator);

        double startPointX = 0;
        double startPointY = 0;
        double endPointX = 0;
        double endPointY = 0;

        try {
            String[] points = value.split(",");
            startPointX = Double.parseDouble(points[0]) / 100;
            startPointY = Double.parseDouble(points[1]) / 100;
            endPointX = Double.parseDouble(points[2]) / 100;
            endPointY = Double.parseDouble(points[3]) / 100;
        } catch(Exception e) {
            return new jp.vmi.selenium.selenese.result.Error("Wrong number format. Use i.appSwipePointToPoint(\"parentElement\", \"30,50,70,50\");");
        }

        AppiumDriver driver = (AppiumDriver) context.getWrappedDriver();

        if (timeout <= 0) {
            timeout = DEFAULT_TIMEOUT; // set to default
        }
        long startTime = System.currentTimeMillis();
       
        Wait.StopCondition parentCondition = () -> {
            try {
                parent = (WebElement) driver.findElement(bySpinnerLocator);
            } catch (Exception e) {
                // do nothing
            }
            return parent != null;
        };
        if (!Wait.defaultInterval.wait(startTime, timeout, parentCondition)) {
            return new jp.vmi.selenium.selenese.result.Error("Failed to find parent within " + timeout + " ms");
        }
        
        if (null == parent) {
            return new jp.vmi.selenium.selenese.result.Error(locator + " not found!");
        }
       
        int parentHeight = parent.getSize().getHeight();
        int parentWidth = parent.getSize().getWidth();
        Point parentLocation = parent.getLocation();

        PointerInput finger1 = new PointerInput(PointerInput.Kind.TOUCH, "finger1");
        Sequence SwipeSequence = new Sequence(finger1, 0);

        SwipeSequence.addAction(finger1.createPointerMove(Duration.ZERO,
                PointerInput.Origin.viewport(),
                (int) (parentLocation.x + parentWidth * startPointX),
                (int) (parentLocation.y + parentHeight * startPointY)));
        SwipeSequence.addAction(finger1.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        SwipeSequence.addAction(finger1.createPointerMove(Duration.ofMillis(300),
                PointerInput.Origin.viewport(),
                (int) (parentLocation.x + parentWidth * endPointX),
                (int) (parentLocation.y + parentHeight * endPointY)));
        SwipeSequence.addAction(finger1.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        ArrayList<Sequence> swipeCommand = new ArrayList<>();
        swipeCommand.add(SwipeSequence);

        driver.perform(swipeCommand);

        return SUCCESS;
    }
    
}
