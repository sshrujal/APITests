package pages;

import org.json.JSONObject;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.http.ContentType;

public class RestUtil {
	 public static Response sendGetRequest(String url) {
        return RestAssured
             .given()
                .baseUri("https://echo.free.beeceptor.com")
                .queryParam("author", "beeceptor")
            .when()
                .get("/sample-request")
            .then()
                .extract()
                .response();
     }
    

   public static Response sendPostRequest(String url) {
        JSONObject payload = new JSONObject();
        payload.put("order_id", "12345");

        JSONObject customer = new JSONObject();
        customer.put("name", "Jane Smith");
        customer.put("email", "janesmith@example.com");
        customer.put("phone", "1-987-654-3210");

        JSONObject address = new JSONObject();
        address.put("street", "456 Oak Street");
        address.put("city", "Metropolis");
        address.put("state", "NY");
        address.put("zipcode", "10001");
        address.put("country", "USA");

        customer.put("address", address);
        payload.put("customer", customer);

        JSONObject item1 = new JSONObject();
        item1.put("product_id", "A101");
        item1.put("name", "Wireless Headphones");
        item1.put("quantity", 1);
        item1.put("price", 79.99);

        JSONObject item2 = new JSONObject();
        item2.put("product_id", "B202");
        item2.put("name", "Smartphone Case");
        item2.put("quantity", 2);
        item2.put("price", 15.99);

        payload.append("items", item1);
        payload.append("items", item2);

        JSONObject payment = new JSONObject();
        payment.put("method", "credit_card");
        payment.put("transaction_id", "txn_67890");
        payment.put("amount", 111.97);
        payment.put("currency", "USD");

        payload.put("payment", payment);

        JSONObject shipping = new JSONObject();
        shipping.put("method", "standard");
        shipping.put("cost", 5.99);
        shipping.put("estimated_delivery", "2024-11-15");

        payload.put("shipping", shipping);
        payload.put("order_status", "processing");
        payload.put("created_at", "2024-11-07T12:00:00Z");

        System.out.println("Pay load ***" + payload.toString());
        
        return RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(payload.toString())
                .when()
                .post(url)
                .then()
                .extract()
                .response();
    }
   
   public static Response sendPostRequestMissingCustomer() {
	    JSONObject payload1 = new JSONObject();
	    payload1.put("order_id", "99999"); 
	    System.out.println("Pay load " + payload1.toString());
	    return RestAssured
	            .given()
	            .contentType(ContentType.JSON)
	            .body(payload1.toString())
	            .when()
	            .post("http://echo.free.beeceptor.com/sample-request?author=beeceptor")
	            .then()
	            .extract().response();
	}


}