package unium.tester;

import jp.vmi.selenium.selenese.result.Result;

public abstract class Commander {
    protected abstract Result execCommand(String commandName, String... args);

    private String getMethodName() {
        return new Throwable().getStackTrace()[1].getMethodName();
    }


    /*==================================================      JMeter commands    ==================================================*/

    public abstract Result runJmeter(String... args);

    /*==================================================      checkium commands   ==================================================*/

    /**
     * app2PointsLongPress(locator, value)<br /><br />
     * * 2 finger long press on an element. <br />
     * * Example: <b>app2PointsLongPress("element", "20,20,80,80")</b>.<br />
     * * @param args <br />
     * * locator - an element locator<br />
     * * value - Coordinates in percent. First point x, first point y, second point x, second point y.
     */
    public Result app2PointsLongPress(String... args) {
        return execCommand(getMethodName(), args);
    }

    /**
     * appAssertElementPresent(locator)<br /><br />
     * * Assert element present. Return SUCCESS when element found. Return ERROR when element cannot be found.<br />
     * * @param args <br />
     * *locator - an element locator<br />
     */
    public Result appAssertElementPresent(String... args) {
        return execCommand(getMethodName(), args);
    }

    /**
     * appCheckbox(locator, value)<br /><br />
     * * Check / uncheck the checkbox <br />
     * * The value is optional <br />
     * - If the value is not set, toggle the checkbox<br />
     * - if the value is set, "true" or "false", set the state of the checkbox.<br />
     * * @param args <br />
     * * locator - an element locator<br />
     * * value - optional, true or false
     */
    public Result appCheckbox(String... args) {
        return execCommand(getMethodName(), args);
    }

    /**
     * appClick(locator, value)<br /><br />
     * * Tap on the element. <br />
     * * The value is optional to set timeout. <br />
     * - If the value is not set, wait for 2 seconds as default.<br />
     * - if the value is set, wait for so much time.<br />
     * * @param args <br />
     * * locator - an element locator<br />
     * * value - timeout in milliseconds
     */
    public Result appClick(String... args) {
        return execCommand(getMethodName(), args);
    }

    /**
     * appClickIfExists(locator, value)<br /><br />
     * * Tap on the element if the element exists. <br />
     * *This command returns always success as result.
     * This command gives the user the possibility to tap the element, which occurs occasionally.<br />
     * * The value is optional to set timeout. <br />
     * - If the value is not set, wait for 2 seconds as default.<br />
     * - If the value is set, wait for so much time.<br />
     * This option gives the user the possibility to tap the element, which occurs occasionally.<br />
     * * @param args <br />
     * * locator - an element locator<br />
     * * value - timeout in milliseconds
     */
    public Result appClickIfExists(String... args) {
        return execCommand(getMethodName(), args);
    }

    /**
     * appClickAt(locator, value)<br /><br />
     * * Tap on the position related to the element <br />
     * This command gives the user the possibility to tap on a position in the element, or a position outside the element.<br />
     * * @param args <br />
     * * locator - an element locator<br />
     * * value - coordinate x,y in percent. For example 50,50 means tap in the middle.
     * - If the number smaller than 0, it means left or above outside the element
     * - If the number grater than 100, it means right or below outside the element
     */
    public Result appClickAt(String... args) {
        return execCommand(getMethodName(), args);
    }

    /**
     * appLongPress(locator)<br /><br />
     * * Long press (3 seconds) on the element <br />
     * This command gives the user the possibility to long press on the element.<br />
     * * @param args <br />
     * * locator - an element locator
     */
    public Result appLongPress(String... args) {
        return execCommand(getMethodName(), args);
    }

    /**
     * appPressKey(locator)<br /><br />
     * * Press key <br />
     * For Android the key name can be found in io.appium.java_client.android.nativekey.AndroidKey<br />
     * E.g. 'HOME', 'BACK' or 'ENTER'
     * For iOS is only 'HOME' supported and this sends the App to background.
     * * @param args <br />
     * * locator - Key name
     */
    public Result appPressKey(String... args) {
        return execCommand(getMethodName(), args);
    }

    /**
     * appSendKeys(locator, value)<br /><br />
     * * Write text to on the element <br />
     * Tap the element, remove the content and fill the element.<br />
     * * @param args <br />
     * * locator - an element locator<br />
     * * value - text to write into the element
     */
    public Result appSendKeys(String... args) {
        return execCommand(getMethodName(), args);
    }

    /**
     * appSetContext(locator, value)<br /><br />
     * * Switch context <br />
     * * @param args <br />
     * * locator - Name of the context to be set<br />
     * * value - "true" -> a part of the context name is given in the locator
     */
    public Result appSetContext(String... args) {
        return execCommand(getMethodName(), args);
    }

    /**
     * appShake()<br /><br />
     * * Simulate shake of the device <br />
     * This works only on iOS.<br />
     * * @param args <br />
     * * No parameter is needed
     */
    public Result appShake(String... args) {
        return execCommand(getMethodName(), args);
    }

    /**
     * AppStoreAttribute(locator, value)<br /><br />
     * * Store attribute value to variable<br />
     * * @param args <br />
     * * locator - an element locator<br />
     * * value - name of the attribute. Value will be stored into the variable `attribute_<value>`
     */
    public Result AppStoreAttribute(String... args) {
        return execCommand(getMethodName(), args);
    }

    /**
     * appStoreCheckboxState(locator, value)<br /><br />
     * * Store checkbox state to variable<br />
     * * @param args <br />
     * * locator - an element locator<br />
     * * value - name of the variable
     */
    public Result appStoreCheckboxState(String... args) {
        return execCommand(getMethodName(), args);
    }

    /**
     * appStoreContext(locator, value)<br /><br />
     * * Store context name to variable <br />
     * This command save the context name to the variable. <br />
     * If "all" is set as locator, all the context name will be stored with "::" as separator.<br />
     * Else the current context name will be stored to the given variable<br />
     * * @param args <br />
     * * locator - "all" -> store all context names, else current context name<br />
     * * value - name of the variable
     */
    public Result appStoreContext(String... args) {
        return execCommand(getMethodName(), args);
    }

    /**
     * appStoreElementVisible(locator, value)<br /><br />
     * * Store if the element is visible to variable <br />
     * * The stored value can be: <br />
     * - found and visible: true<br />
     * - found and visible: false<br />
     * - found, no visible attribute<br />
     * - not found<br />
     * * @param args <br />
     * * locator - an element locator<br />
     * * value - name of the variable
     */
    public Result appStoreElementVisible(String... args) {
        return execCommand(getMethodName(), args);
    }

    /**
     * appStoreText(locator, value)<br /><br />
     * * Store element text to variable<br />
     * * @param args <br />
     * * locator - an element locator<br />
     * * value - name of the variable
     */
    public Result appStoreText(String... args) {
        return execCommand(getMethodName(), args);
    }

    /**
     * appSwipeDownPercent(locator, value)<br /><br />
     * * Swipe down due to percent of element height.<br />
     * * @param args <br />
     * * locator - an element locator<br />
     * * value - percent of the element height to swipe
     */
    public Result appSwipeDownPercent(String... args) {
        return execCommand(getMethodName(), args);
    }

    /**
     * appSwipeDownToElement(locator, value)<br /><br />
     * * Swipe down until an element occurs.<br />
     * * @param args <br />
     * * locator - The locator for the element which is to swiped.<br />
     * * value - The locator for the wished element
     */
    public Result appSwipeDownToElement(String... args) {
        return execCommand(getMethodName(), args);
    }

    /**
     * appSwipePointToPoint(locator, value)<br /><br />
     * * Swipe point to point. The points are defined as percent.<br />
     * * @param args <br />
     * * locator - The locator for the element which is to swiped.<br />
     * * value - Format of "startX,startY,endX,endY", like "30,50,70,50".<br />
     * can be minus or more than 100
     */
    public Result appSwipePointToPoint(String... args) {
        return execCommand(getMethodName(), args);
    }

    /**
     * appSwipeUpToElement(locator, value)<br /><br />
     * * Swipe up until an element occurs.<br />
     * * @param args <br />
     * * locator - The locator for the element which is to swiped.<br />
     * * value - The locator for the wished element
     */
    public Result appSwipeUpToElement(String... args) {
        return execCommand(getMethodName(), args);
    }

    /**
     * appTakeScreenshot(locator)<br /><br />
     * * Take a screenshot of the current screen. <br />
     * * @param args <br />
     * * locator - File path<br />
     */
    public Result appTakeScreenshot(String... args) {
        return execCommand(getMethodName(), args);
    }

    /**
     * appType(locator, value)<br /><br />
     * * Write text to on the element <br />
     * Fill the element. The difference to appSendKeys is, appType do not click and clear the element.<br />
     * * @param args <br />
     * * locator - an element locator<br />
     * * value - text to write into the element
     */
    public Result appType(String... args) {
        return execCommand(getMethodName(), args);
    }

    /**
     * appWaitForElement(locator, value)<br /><br />
     * * Wait for an element occurs.<br />
     * If the element does not occur within the defined milliseconds -> error<br />
     * * @param args <br />
     * * locator - an element locator<br />
     * * value - Time to wait in milliseconds
     */
    public Result appWaitForElement(String... args) {
        return execCommand(getMethodName(), args);
    }

    /*==================================================    Selenese Runner commands    ==================================================*/

    public Result addCollection(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result addLocationStrategy(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result addSelection(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result addToCollection(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result allowNativeXpath(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result altKeyDown(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result altKeyUp(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result answerOnNextNativeAlert(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result answerOnNextPrompt(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result assertAlert(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result assertAlertNotPresent(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result assertAlertPresent(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result assertAllButtons(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result assertAllFields(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result assertAllLinks(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result assertAllWindowNames(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result assertAllWindowTitles(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result assertAttribute(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result assertAttributeFromAllWindows(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result assertBodyText(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result assertChecked(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result assertConfirmation(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result assertConfirmationNotPresent(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result assertConfirmationPresent(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result assertCookie(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result assertCookieByName(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result assertCookieNotPresent(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result assertCookiePresent(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result assertCssCount(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result assertCursorPosition(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result assertEditable(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result assertElementHeight(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result assertElementIndex(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result assertElementNotPresent(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result assertElementPositionLeft(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result assertElementPositionTop(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result assertElementPresent(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result assertElementWidth(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result assertEval(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result assertExpression(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result assertHtmlSource(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result assertLocation(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result assertNativeAlert(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result assertNativeAlertNotPresent(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result assertNativeAlertPresent(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result assertNotAlert(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result assertNotAllButtons(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result assertNotAllFields(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result assertNotAllLinks(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result assertNotAllWindowNames(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result assertNotAllWindowTitles(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result assertNotAttribute(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result assertNotAttributeFromAllWindows(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result assertNotBodyText(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result assertNotChecked(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result assertNotConfirmation(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result assertNotCookie(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result assertNotCookieByName(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result assertNotCssCount(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result assertNotCursorPosition(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result assertNotEditable(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result assertNotElementHeight(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result assertNotElementIndex(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result assertNotElementPositionLeft(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result assertNotElementPositionTop(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result assertNotElementWidth(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result assertNotEval(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result assertNotExpression(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result assertNotHtmlSource(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result assertNotLocation(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result assertNotNativeAlert(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result assertNotOrdered(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result assertNotPrompt(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result assertNotSelectOptions(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result assertNotSelectedId(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result assertNotSelectedIds(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result assertNotSelectedIndex(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result assertNotSelectedIndexes(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result assertNotSelectedLabel(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result assertNotSelectedLabels(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result assertNotSelectedValue(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result assertNotSelectedValues(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result assertNotSomethingSelected(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result assertNotSpeed(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result assertNotTable(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result assertNotText(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result assertNotTitle(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result assertNotValue(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result assertNotVisible(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result assertNotXpathCount(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result assertOrdered(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result assertPrompt(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result assertPromptNotPresent(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result assertPromptPresent(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result assertSelectOptions(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result assertSelectedId(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result assertSelectedIds(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result assertSelectedIndex(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result assertSelectedIndexes(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result assertSelectedLabel(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result assertSelectedLabels(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result assertSelectedValue(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result assertSelectedValues(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result assertSomethingSelected(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result assertSpeed(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result assertTable(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result assertText(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result assertTextNotPresent(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result assertTextPresent(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result assertTitle(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result assertValue(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result assertVisible(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result assertXpathCount(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result assignId(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result attachFile(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result captureEntirePageScreenshot(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result check(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result chooseCancelOnNextConfirmation(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result chooseCancelOnNextNativeAlert(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result chooseOkOnNextConfirmation(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result chooseOkOnNextNativeAlert(String... args) {
        return execCommand(getMethodName(), args);
    }

    /**
     * click(locator)<br /><br />     * Clicks on a link, button, checkbox or radio button. If the click action causes a new page to load (like a link usually
     * does), call waitForPageToLoad.     *     * @param args <br />*locator - an element locator
     */
    public Result click(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result clickAt(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result close(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result comment(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result contextMenu(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result controlKeyDown(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result controlKeyUp(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result createCookie(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result deleteAllVisibleCookies(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result deleteCookie(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result deselectPopUp(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result doubleClick(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result dragAndDrop(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result dragAndDropToObject(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result dragdrop(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result echo(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result editContent(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result fireEvent(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result focus(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result goBack(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result gotoLabel(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result gotolabel(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result highlight(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result include(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result keyDown(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result keyPress(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result keyUp(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result label(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result metaKeyDown(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result metaKeyUp(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result mouseDown(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result mouseDownAt(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result mouseMove(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result mouseMoveAt(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result mouseOut(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result mouseOver(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result mouseUp(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result mouseUpAt(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result open(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result openWindow(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result pause(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result refresh(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result removeAllSelections(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result removeSelection(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result rollup(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result runScript(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result select(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result selectFrame(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result selectPopUp(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result selectWindow(String... args) {
        return execCommand(getMethodName(), args);
    }

    /**
     * sendKeys(locator, value)<br /><br />     * This simulates a real user typing every character in the specified string; it is also bound by the
     * limitations
     * of a real user, like not being able to     * type into an invisible or read only elements. This is useful for dynamic UI widgets (like auto-completing
     * combo boxes) that require explicit key events.     * Unlike the simple "type" getMethodName(), which forces the specified value into the page directly,
     * this command will not replace the existing content.     * If you     * want to replace the existing contents, you need to use the simple "type"
     * command to
     * set the value of the field to empty string to clear the field and     * then the "sendKeys" command to send the keystroke for what you want to type.
     * *
     * * @param args <br />*locator - an element locator     *             <br />*value - the value to type
     */
    public Result sendKeys(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result setBrowserLogLevel(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result setCursorPosition(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result setSpeed(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result setTimeout(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result shiftKeyDown(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result shiftKeyUp(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result store(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result storeAlert(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result storeAlertPresent(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result storeAllButtons(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result storeAllFields(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result storeAllLinks(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result storeAllWindowNames(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result storeAllWindowTitles(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result storeAttribute(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result storeAttributeFromAllWindows(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result storeBodyText(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result storeChecked(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result storeConfirmation(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result storeConfirmationPresent(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result storeCookie(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result storeCookieByName(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result storeCookiePresent(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result storeCssCount(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result storeCursorPosition(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result storeEditable(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result storeElementHeight(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result storeElementIndex(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result storeElementPositionLeft(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result storeElementPositionTop(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result storeElementPresent(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result storeElementWidth(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result storeEval(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result storeExpression(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result storeFor(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result storeHtmlSource(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result storeLocation(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result storeNativeAlert(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result storeNativeAlertPresent(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result storeOrdered(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result storePrompt(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result storePromptPresent(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result storeSelectOptions(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result storeSelectedId(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result storeSelectedIds(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result storeSelectedIndex(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result storeSelectedIndexes(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result storeSelectedLabel(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result storeSelectedLabels(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result storeSelectedValue(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result storeSelectedValues(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result storeSomethingSelected(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result storeSpeed(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result storeTable(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result storeText(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result storeTextPresent(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result storeTitle(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result storeValue(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result storeVisible(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result storeXpathCount(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result submit(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result type(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result typeKeys(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result uncheck(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result useXpathLibrary(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result verifyAlert(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result verifyAlertNotPresent(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result verifyAlertPresent(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result verifyAllButtons(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result verifyAllFields(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result verifyAllLinks(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result verifyAllWindowNames(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result verifyAllWindowTitles(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result verifyAttribute(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result verifyAttributeFromAllWindows(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result verifyBodyText(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result verifyChecked(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result verifyConfirmation(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result verifyConfirmationNotPresent(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result verifyConfirmationPresent(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result verifyCookie(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result verifyCookieByName(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result verifyCookieNotPresent(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result verifyCookiePresent(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result verifyCssCount(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result verifyCursorPosition(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result verifyEditable(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result verifyElementHeight(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result verifyElementIndex(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result verifyElementNotPresent(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result verifyElementPositionLeft(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result verifyElementPositionTop(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result verifyElementPresent(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result verifyElementWidth(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result verifyEval(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result verifyExpression(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result verifyHtmlSource(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result verifyLocation(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result verifyNativeAlert(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result verifyNativeAlertNotPresent(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result verifyNativeAlertPresent(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result verifyNotAlert(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result verifyNotAllButtons(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result verifyNotAllFields(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result verifyNotAllLinks(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result verifyNotAllWindowNames(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result verifyNotAllWindowTitles(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result verifyNotAttribute(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result verifyNotAttributeFromAllWindows(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result verifyNotBodyText(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result verifyNotChecked(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result verifyNotConfirmation(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result verifyNotCookie(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result verifyNotCookieByName(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result verifyNotCssCount(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result verifyNotCursorPosition(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result verifyNotEditable(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result verifyNotElementHeight(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result verifyNotElementIndex(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result verifyNotElementPositionLeft(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result verifyNotElementPositionTop(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result verifyNotElementWidth(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result verifyNotEval(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result verifyNotExpression(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result verifyNotHtmlSource(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result verifyNotLocation(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result verifyNotNativeAlert(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result verifyNotOrdered(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result verifyNotPrompt(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result verifyNotSelectOptions(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result verifyNotSelectedId(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result verifyNotSelectedIds(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result verifyNotSelectedIndex(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result verifyNotSelectedIndexes(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result verifyNotSelectedLabel(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result verifyNotSelectedLabels(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result verifyNotSelectedValue(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result verifyNotSelectedValues(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result verifyNotSomethingSelected(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result verifyNotSpeed(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result verifyNotTable(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result verifyNotText(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result verifyNotTitle(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result verifyNotValue(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result verifyNotVisible(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result verifyNotXpathCount(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result verifyOrdered(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result verifyPrompt(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result verifyPromptNotPresent(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result verifyPromptPresent(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result verifySelectOptions(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result verifySelectedId(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result verifySelectedIds(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result verifySelectedIndex(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result verifySelectedIndexes(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result verifySelectedLabel(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result verifySelectedLabels(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result verifySelectedValue(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result verifySelectedValues(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result verifySomethingSelected(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result verifySpeed(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result verifyTable(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result verifyText(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result verifyTextNotPresent(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result verifyTextPresent(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result verifyTitle(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result verifyValue(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result verifyVisible(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result verifyXpathCount(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result waitForAlert(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result waitForAlertNotPresent(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result waitForAlertPresent(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result waitForAllButtons(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result waitForAllFields(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result waitForAllLinks(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result waitForAllWindowNames(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result waitForAllWindowTitles(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result waitForAttribute(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result waitForAttributeFromAllWindows(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result waitForBodyText(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result waitForChecked(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result waitForCondition(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result waitForConfirmation(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result waitForConfirmationNotPresent(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result waitForConfirmationPresent(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result waitForCookie(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result waitForCookieByName(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result waitForCookieNotPresent(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result waitForCookiePresent(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result waitForCssCount(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result waitForCursorPosition(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result waitForEditable(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result waitForElementHeight(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result waitForElementIndex(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result waitForElementNotPresent(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result waitForElementPositionLeft(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result waitForElementPositionTop(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result waitForElementPresent(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result waitForElementWidth(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result waitForEval(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result waitForExpression(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result waitForFrameToLoad(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result waitForHtmlSource(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result waitForLocation(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result waitForNativeAlert(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result waitForNativeAlertNotPresent(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result waitForNativeAlertPresent(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result waitForNotAlert(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result waitForNotAllButtons(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result waitForNotAllFields(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result waitForNotAllLinks(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result waitForNotAllWindowNames(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result waitForNotAllWindowTitles(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result waitForNotAttribute(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result waitForNotAttributeFromAllWindows(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result waitForNotBodyText(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result waitForNotChecked(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result waitForNotConfirmation(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result waitForNotCookie(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result waitForNotCookieByName(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result waitForNotCssCount(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result waitForNotCursorPosition(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result waitForNotEditable(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result waitForNotElementHeight(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result waitForNotElementIndex(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result waitForNotElementPositionLeft(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result waitForNotElementPositionTop(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result waitForNotElementWidth(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result waitForNotEval(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result waitForNotExpression(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result waitForNotHtmlSource(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result waitForNotLocation(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result waitForNotNativeAlert(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result waitForNotOrdered(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result waitForNotPrompt(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result waitForNotSelectOptions(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result waitForNotSelectedId(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result waitForNotSelectedIds(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result waitForNotSelectedIndex(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result waitForNotSelectedIndexes(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result waitForNotSelectedLabel(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result waitForNotSelectedLabels(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result waitForNotSelectedValue(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result waitForNotSelectedValues(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result waitForNotSomethingSelected(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result waitForNotSpeed(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result waitForNotTable(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result waitForNotText(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result waitForNotTitle(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result waitForNotValue(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result waitForNotVisible(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result waitForNotXpathCount(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result waitForOrdered(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result waitForPageToLoad(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result waitForPopUp(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result waitForPrompt(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result waitForPromptNotPresent(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result waitForPromptPresent(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result waitForSelectOptions(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result waitForSelectedId(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result waitForSelectedIds(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result waitForSelectedIndex(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result waitForSelectedIndexes(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result waitForSelectedLabel(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result waitForSelectedLabels(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result waitForSelectedValue(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result waitForSelectedValues(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result waitForSomethingSelected(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result waitForSpeed(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result waitForTable(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result waitForText(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result waitForTextNotPresent(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result waitForTextPresent(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result waitForTitle(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result waitForValue(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result waitForVisible(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result waitForXpathCount(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result windowFocus(String... args) {
        return execCommand(getMethodName(), args);
    }

    public Result windowMaximize(String... args) {
        return execCommand(getMethodName(), args);
    }
}