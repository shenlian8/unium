package unium.jmeter;

import org.apache.commons.lang3.StringUtils;
import org.apache.jmeter.reporters.ResultCollector;
import org.apache.jmeter.samplers.SampleEvent;
import unium.tester.Tester;

public class SeleniumResultCollector extends ResultCollector {
    private static final String DEBUG_SAMPLER = "Debug Sampler";
    private Tester tester;

    public SeleniumResultCollector() {
    }

    public SeleniumResultCollector(Tester tester) {
        this.tester = tester;
    }

    /**
     * When a test result is received, store it to selenium
     *
     * @param event the sample event that was received
     */
    @Override
    public void sampleOccurred(SampleEvent event) {
        if (event.getResult().getSampleLabel().equals(DEBUG_SAMPLER)) {
            storeTestResults(event.getResult().getResponseDataAsString());
        }
    }

    private void storeTestResults(final String debugResponseData) {
        final String[] lines = debugResponseData.split("\n");
        for (int index = 1; index < lines.length; index++) {
            String line = lines[index];
            final String[] keyValue = StringUtils.split(line, "=", 2);
            try {
                tester.execCommand("store", keyValue[1], keyValue[0]);
            } catch (Exception e) {
                // do nothing
            }

        }

    }


}
