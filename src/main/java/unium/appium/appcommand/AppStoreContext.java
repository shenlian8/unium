/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unium.appium.appcommand;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.remote.SupportsContextSwitching;
import jp.vmi.selenium.selenese.Context;
import jp.vmi.selenium.selenese.command.AbstractCommand;
import jp.vmi.selenium.selenese.result.Result;
import jp.vmi.selenium.selenese.result.Success;
import jp.vmi.selenium.selenese.utils.SeleniumUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Set;

import static jp.vmi.selenium.selenese.command.ArgumentType.LOCATOR;
import static jp.vmi.selenium.selenese.command.ArgumentType.VALUE;

/**
 *
 * @author lian.shen
 */
public class AppStoreContext extends AbstractCommand {

    private static final int ARG_LOCATOR = 0;
    private static final int ARG_VALUE = 1;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public AppStoreContext(int index, String name, String... args) {
        super(index, name, args, LOCATOR, VALUE);
    }

    @Override
    protected Result executeImpl(Context context, String[] curArgs) {

        log.info(Arrays.toString(curArgs));

        final String locator = curArgs[ARG_LOCATOR];
        final String varName = curArgs[ARG_VALUE];

        AppiumDriver driver = (AppiumDriver) context.getWrappedDriver();

        String contextResult = "";
        
        if (locator.equals("all")) {
            try {
                Set<String> contextNames = ((SupportsContextSwitching) driver).getContextHandles();
                contextResult = String.join("::", contextNames);
            } catch (Exception e) {
                contextResult = ((SupportsContextSwitching) driver).getContext();
            }
        } else {
            contextResult = ((SupportsContextSwitching) driver).getContext();
        }

        log.info(contextResult);

        context.getVarsMap().put(varName, contextResult);
        String infoTest = "all".equals(locator) ? "Stored all contexts -> " : "Stored current context -> ";
        return new Success(SeleniumUtils.convertToString(infoTest + varName));
    }

}
