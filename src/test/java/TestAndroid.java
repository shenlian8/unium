
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import unium.tester.Tester;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestAndroid {
    private Tester androidTester;

    @BeforeAll
    public void initTester() {
        androidTester = Tester.createAndroidTester("RemoteAndroidPixel4EmulatorDe.yml");
        androidTester.startTestSuite("Android Test");
    }

    @AfterAll
    public void finishTest() {
        androidTester.endTestSuite();
    }

    @Test
    public void testClick() {
        androidTester.startTestCase("Click test");
    }

    //@Test
    public void testError() {

    }
}
