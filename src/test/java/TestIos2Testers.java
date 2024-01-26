
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import unium.tester.Tester;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestIos2Testers {
    private Tester ios11Tester;
    private Tester ios12Tester;

    @BeforeAll
    public void initTester() {
        ios11Tester = Tester.createIosTester("LocalIosIphone11SimulatorEn.yml");
        ios11Tester.startTestSuite("iOS 11 Tests");

        ios12Tester = Tester.createIosTester("RemoteIosIphone15SimulatorDe.yml");
        ios12Tester.startTestSuite("iOS 12 Tests");
    }

    @AfterAll
    public void finishTest() {
        ios11Tester.endTestSuite();
        ios12Tester.endTestSuite();
    }

    @Test
    public void testClick() {
        ios11Tester.startTestCase("Click test");
        ios11Tester.appClick("Continue");
        ios11Tester.endTestCase();

        ios12Tester.startTestCase("Click test");
        ios12Tester.appClick("Weiter");
        ios12Tester.endTestCase();
    }

    //@Test
    public void testError() {
        ios12Tester.startTestCase("Click test");
        ios12Tester.appClick("No element");
        ios12Tester.endTestCase();
    }
}
