package StepDefinitions;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
    
@CucumberOptions(tags = "", features = {"src/test/resources/features/labCorpAPiTest.feature"}, glue = {"StepDefinitions"},
                 plugin = {"pretty", 
                	        "html:target/cucumber-html-report.html"})
    
public class TestRunner extends AbstractTestNGCucumberTests {
    
}