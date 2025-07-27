Feature: API Testing using REST Assured

  Scenario: Validate GET response fields
    Given I send a GET request to "https://echo.free.beeceptor.com/sample-request?author=beeceptor"
    Then the response should contain field "path"
    And the response should contain field "ip"
    And all headers should be validated
    
    Scenario: Validate POST response with payload
    Given I send a POST request to "http://echo.free.beeceptor.com/sample-request?author=beeceptor" with order payload
    Then the response should have customer name "Jane Smith"
    And the transaction ID should be "txn_67890"
    And the order status should be "processing"
    
    Scenario: Schema Validation - Response matches expected structure
    Given I send a POST request to "http://echo.free.beeceptor.com/sample-request?author=beeceptor" with order payload
    Then the response should match the expected JSON schema
    
    Scenario: Negative Test - Missing customer field
    Given I send a POST request with missing customer info
    Then the response status code should be 400
    And the error message should contain "customer is required"
    
    Scenario: Performance Test - Response time under 2 seconds
    Given I send a POST request to "http://echo.free.beeceptor.com/sample-request?author=beeceptor" with order payload
    Then the response time should be less than 2000 milliseconds