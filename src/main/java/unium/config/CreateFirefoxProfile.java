package unium.config;

import org.openqa.selenium.firefox.FirefoxProfile;

import java.io.IOException;

public class CreateFirefoxProfile {

    public static void  main(String[] args) throws IOException {

        FirefoxProfile firefoxProfile= new FirefoxProfile();
        firefoxProfile.setPreference("media.navigator.streams.fake", true);
        firefoxProfile.setPreference("media.navigator.permission.disabled", true);
    }
}
