/*
 * Copyright 2017 lian.shen.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package unium.appium.webdriver;

import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.logging.Logs;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author lian.shen
 */
public class IOSAppDriver extends IOSDriver implements JavascriptExecutor {

    public IOSAppDriver(URL remoteAddress, Capabilities desiredCapabilities) {
        super(remoteAddress, desiredCapabilities);
    }
   
    @Override
    public Options manage() {
      return new Options() {
          @Override
          public void addCookie(Cookie cookie) {
              throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
          }

          @Override
          public void deleteCookieNamed(String string) {
              throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
          }

          @Override
          public void deleteCookie(Cookie cookie) {
              throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
          }

          @Override
          public void deleteAllCookies() {
              throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
          }

          @Override
          public Set<Cookie> getCookies() {
              throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
          }

          @Override
          public Cookie getCookieNamed(String string) {
              throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
          }

          @Override
          public Timeouts timeouts() {
            return new Timeouts() {
                @Override
                public Timeouts implicitlyWait(long l, TimeUnit tu) {
                    return this;
                }

                @Override
                public Timeouts setScriptTimeout(long l, TimeUnit tu) {
                    return this;
                }

                @Override
                public Timeouts pageLoadTimeout(long l, TimeUnit tu) {
                    return this;
                }
            };
          }

          @Override
          public Window window() {
              throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
          }

          @Override
          public Logs logs() {
              throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
          }
      };
    }    
  
    @Override
    public String getWindowHandle() {
      return null;
    }  
    
    @Override 
    public Object executeScript(String script, Object... args) {
        
        IScriptExecutor jsExec = new GraalvmExecutor();
        Object result = jsExec.executeScript(script);
        
        List list = new ArrayList();
        list.add(result);
        return list;
    }      
}
