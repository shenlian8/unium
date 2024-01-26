/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unium.appium.appcommand;

import jp.vmi.selenium.selenese.command.ICommand;
import jp.vmi.selenium.selenese.command.ICommandFactory;

import java.lang.reflect.InvocationTargetException;

/**
 *
 * @author lian.shen
 */
public class CommandFactory implements ICommandFactory {  
    @Override
    public ICommand newCommand(int index, String name, String[] args) { 
        name = name.replace("app", "App");
        try {
                String packageName = this.getClass().getPackage().getName();
                return (ICommand) Class
                        .forName(packageName + "." + name)
                        .getConstructor(int.class, String.class, String[].class)
                        .newInstance(index, name, args);
            } catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InstantiationException | NoSuchMethodException | SecurityException | InvocationTargetException ex) {
                return null;
            }
    }
    
}
