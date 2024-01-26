/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unium.appium.webdriver;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 *
 * @author lian.shen
 */
public class GraalvmExecutor implements IScriptExecutor{

    @Override
    public Object executeScript(String script) {
        // with storedVars
        if(script.startsWith("return (function(){")) {
            // first line
            script = script.replace("return (function(){", "");
            
            // last line
            if(script.endsWith("})();")) {
                script = script.substring(0, script.length() - 5);
            }
            
            // , storedVars
            script = script.replace(", storedVars", "");
        }
        
        // original script
        script = script.replace("return [(", "");
        //just replace the last
        if(script.endsWith(")];")) {
            script = script.substring(0, script.length() - 3);
        }
        System.out.println(script);
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("graal.js");
        Object result = null;
        try {
            result = engine.eval(script);
        } catch (ScriptException ex) {
//            Logger.getLogger(NashornExecutor.class.getName()).log(Level.SEVERE, null, ex);
        }  
        return result;
    }

}
