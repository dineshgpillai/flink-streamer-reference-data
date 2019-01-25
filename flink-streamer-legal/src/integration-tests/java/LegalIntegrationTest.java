import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)

@CucumberOptions(features = {"src/integration-tests/resources/features/constructALegalDocumentAndPushToHz.feature"}, plugin = {"pretty", "html:target/reports/cucumber/html",
        "json:target/cucumber.json", "usage:target/usage.jsonx", "junit:target/junit.xml"})
public class LegalIntegrationTest {

}
