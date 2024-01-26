/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unium.appium.appcommand;

import jp.vmi.selenium.selenese.Context;
import jp.vmi.selenium.selenese.ScreenshotHandler;
import jp.vmi.selenium.selenese.command.AbstractCommand;
import jp.vmi.selenium.selenese.result.Result;
import jp.vmi.selenium.selenese.result.Success;
import jp.vmi.selenium.selenese.result.Warning;
import jp.vmi.selenium.selenese.utils.LangUtils;

import static jp.vmi.selenium.selenese.command.ArgumentType.LOCATOR;
import static jp.vmi.selenium.selenese.command.ArgumentType.VALUE;

/**
 *
 * @author lian.shen
 */
public class AppTakeScreenshot extends AbstractCommand {
    public AppTakeScreenshot(int index, String name, String... args) {
        super(index, name, args, LOCATOR, VALUE);
    }

    @Override
    protected Result executeImpl(Context context, String[] curArgs) {
        if (!(context instanceof ScreenshotHandler)) {
            return new Success("captureEntirePageScreenshot is not supported.");
        } else {
            String filename = curArgs[0];
            if (LangUtils.isBlank(filename)) {
                return new Warning("captureEntirePageScreenshot is ignored: empty filename.");
            } else {
                ScreenshotHandler handler = (ScreenshotHandler)context;
                if (handler.isIgnoredScreenshotCommand()) {
                    return new Success("captureEntirePageScreenshot is ignored.");
                } else {
                    try {
                        this.addScreenshot(handler.takeScreenshot(filename), "cmd");
                        return Success.SUCCESS;
                    } catch (UnsupportedOperationException var6) {
                        return new Warning(var6.getMessage());
                    }
                }
            }
        }
    }
    
}
