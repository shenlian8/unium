/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unium.appium.appcommand;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import jp.vmi.selenium.selenese.Context;
import jp.vmi.selenium.selenese.command.AbstractCommand;
import jp.vmi.selenium.selenese.result.Result;
import jp.vmi.selenium.selenese.result.Success;
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

import static io.appium.java_client.touch.offset.ElementOption.element;
import static jp.vmi.selenium.selenese.command.ArgumentType.LOCATOR;
import static jp.vmi.selenium.selenese.command.ArgumentType.VALUE;

/**
 *
 * @author lian.shen
 */
public class AppClickAt extends AbstractCommand {
    private static final int ARG_LOCATOR = 0;
    private static final int ARG_VALUE = 1;
    private final long DEFAULT_TIMEOUT = 2000;

    private double startPointX = 0;
    private double startPointY = 0;
    private Integer clickPointX;
    private Integer clickPointY;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    WebElement element = null;

    public AppClickAt(int index, String name, String... args) {
        super(index, name, args, LOCATOR, VALUE);
    }

    @Override
    protected Result executeImpl(Context context, String[] curArgs) { 

        log.info(Arrays.toString(curArgs));

        final String locator = curArgs[ARG_LOCATOR];
        final String value = curArgs[ARG_VALUE];

        try {
            String[] points = value.split(",");
            startPointX = Double.parseDouble(points[0]) / 100;
            startPointY = Double.parseDouble(points[1]) / 100;
        } catch(Exception e) {
            return new jp.vmi.selenium.selenese.result.Error("Wrong number format. Use i.appSwipePointToPoint(\"element\", \"30,50\");");
        }

        final By byLocator = LocatorBySetter.setBy(locator);

        AppiumDriver driver = (AppiumDriver) context.getWrappedDriver();

        long timeout = DEFAULT_TIMEOUT; // set to default;

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(timeout));
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(byLocator));
            element = driver.findElement(byLocator);
        } catch (TimeoutException e) {
            return new jp.vmi.selenium.selenese.result.Error("Failed to find element within " + timeout + " ms");
        }

        int elementHeight = element.getSize().getHeight();
        int elementWidth = element.getSize().getWidth();
        Point elementLocation = element.getLocation();

        PointerInput finger1 = new PointerInput(PointerInput.Kind.TOUCH, "finger1");
        Sequence finger1Tap = new Sequence(finger1, 0);
        finger1Tap.addAction(finger1.createPointerMove(Duration.ofMillis(100),
                PointerInput.Origin.viewport(),
                (int) (elementLocation.x + elementWidth * startPointX),
                (int) (elementLocation.y + elementHeight * startPointY)));
        finger1Tap.addAction(finger1.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        finger1Tap.addAction(finger1.createPointerMove(Duration.ZERO,
                PointerInput.Origin.viewport(),
                (int) (elementLocation.x + elementWidth * startPointX),
                (int) (elementLocation.y + elementHeight * startPointY)));
        finger1Tap.addAction(finger1.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        ArrayList<Sequence> clickAtSequence = new ArrayList<Sequence>();
        clickAtSequence.add(finger1Tap);

        driver.perform(clickAtSequence);

        return new Success("Clicked at " + clickPointX.toString() + ", " + clickPointY.toString() + " from the left top corner of the element.");
    }
    
}
