
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import unium.tester.Tester;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestIos {
    private Tester ios11Tester;

    @BeforeAll
    public void initTester() {
        ios11Tester = Tester.createIosTester("LocalIosIphone11SimulatorEn.yml");
        ios11Tester.startTestSuite("iOS 11 Tests");
    }

    @AfterAll
    public void finishTest() {
        ios11Tester.endTestSuite();
    }

    @Test
    public void testClick() {
        ios11Tester.startTestCase("Click test");
        ios11Tester.appClick("Continue");
        ios11Tester.appClick("Allow", "1000");
        ios11Tester.sendKeys("", "");
        ios11Tester.endTestCase();
    }

    //@Test
    public void testError() {

    }
}
