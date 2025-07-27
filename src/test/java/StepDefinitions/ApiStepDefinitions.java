package StepDefinitions;

import io.cucumber.java.en.*;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import pages.RestUtil;

import static org.hamcrest.Matchers.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.io.File;

public class ApiStepDefinitions {

    Response response;

    @Given("I send a GET request to {string}")
    public void i_send_get_request(String url) {
        response = RestUtil.sendGetRequest(url);
    }

    @Then("the response should contain field {string}")
    public void response_should_contain_field(String field) {
        response.then().assertThat().body("$", hasKey(field));
    }

    @And("all headers should be validated")
    public void validate_headers() {
    	assertNotNull(response.getHeaders());
    	response.then().assertThat().header("Content-Type", "application/json");
    }

    @Given("I send a POST request to {string} with order payload")
    public void i_send_post_request(String url) {
        response = RestUtil.sendPostRequest(url);
    }

    @Then("the response should have customer name {string}")
    public void validate_customer_name(String name) {
    	//String customerName = response.jsonPath().getString("parsedBody.customer.name");
        response.then().assertThat().body("parsedBody.customer.name", equalTo(name));
    }

    @And("the transaction ID should be {string}")
    public void validate_transaction_id(String txnId) {
        response.then().assertThat().body("parsedBody.payment.transaction_id", equalTo(txnId));
    }

    @And("the order status should be {string}")
    public void validate_order_status(String status) {
        response.then().assertThat().body("parsedBody.order_status", equalTo(status));
    }
    
    @Then("the response should match the expected JSON schema")
    public void validateJsonSchema() {
        File schema = new File("src/main/resources/order-response-schema.json");
        response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(schema));
    }
    
    @Given("I send a POST request with missing customer info")
    public void postWithMissingCustomer() {
        response = RestUtil.sendPostRequestMissingCustomer();
    }
    
    @Then("the response status code should be {int}")
    public void validateStatusCode(int code) {
    	assertEquals(response.getStatusCode(), code);
    }

    @And("the error message should contain {string}")
    public void validateErrorMessage(String errorMsg) {
        assertEquals(response.getBody().asString(), errorMsg);
    }
    
    @Then("the response time should be less than {int} milliseconds")
    public void validateResponseTime(int maxTime) {
        long time = response.getTime();
        System.out.println("Response time: " + time + " ms");
        assertTrue(time < maxTime, "Response time exceeds expected limit!");
    }
}