/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unium.appium.appcommand;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

/**
 *
 * @author lian.shen
 */
public class LocatorBySetter {
    private static final Pattern LOCATORS_RE = Pattern.compile("(\\w+)=(.*)|(document\\..*)|(//.*)|(.+)");
    private static final int XPATH_LOCATOR = 4;
    private static final int FREE_STRING = 5;

    private static final Logger LOG = LoggerFactory.getLogger(LocatorBySetter.class);
    
    public static By setBy(String locator) {
        // if match xpath set to xpath
        Matcher matcher = LOCATORS_RE.matcher(locator);
        if (matcher.matches()) {
            if (isNotEmpty(matcher.group(XPATH_LOCATOR))) {
                // start with "//"
                LOG.info("XPath: " + locator);
                return AppiumBy.xpath(locator);
            } else if (isNotEmpty(matcher.group(FREE_STRING))) {
                LOG.info("AccessibilityId: " + locator);
                return AppiumBy.accessibilityId(locator);
            }
        }
        return AppiumBy.accessibilityId(locator);
    }
}
