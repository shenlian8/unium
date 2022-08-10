package unium.tester;

import com.google.inject.Guice;
import com.google.inject.Injector;
import jp.vmi.selenium.selenese.inject.BindModule;
import unium.tester.Tester;

public class InterceptorBinder {
    private static Injector injector = Guice.createInjector(new BindModule());

    private InterceptorBinder() {
    }

    public static Tester newTester() {
        return injector.getInstance(Tester.class);
    }
}
