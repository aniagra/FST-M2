package Activities;

import org.testng.annotations.Test;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import org.testng.annotations.BeforeClass;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

import org.testng.annotations.AfterClass;

public class Activity1 {
	
	// Declare request specification
    RequestSpecification requestSpec;
    // Declare response specification
    ResponseSpecification responseSpec;
    
    String sshKey = "ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABgQC9DmOh+kN2+PMEN3Er3ZTes2dLNW1GD24Bcqrmv9Gl+Kyjwr0dl7pvZTOFHI/zbFTb8pUI12lE1eJ7FRxQEKw27MZ3cHdbiOmKH4bvz0KJyTQrRHQEyRTPz6M0MddYrl9sVn2b3U2XVd8HAbhS3HAh+Ua4i9j7Z9saW6f5jPi5LmCk34eLWaPV8nzlrBH1d7ATWs7WxVGqskKr7Laoe/9UCrkUpPS8kAYL/Eu6O28pyIQlXKvE9M8dPvBBeokfL7KhLxQpEM823ptx+WdCyodGpAEckvzYQmOyKTwcNbXarvReg8qPZvtT2+QWbxztQbHhiy9hfL/pSUL6j/x+zfCAka5RHd0uEHBY0SwYqsNYtUXfq6BJ7+mKffT/h+a/wfxHJX3Scd8K4tULAUJymV3cHOMJDXzeX9XqPUxJ0AAo0G55R0NyrSnf7MYOVEahIM1jgbJlEORV/g9ia9zcIxOsuB7mR7S49z7ZhNEF1FTY5U9+Z5wkJfk3KpXbnEIntBk";
    int Id;
    
    @BeforeClass
    public void setUp() {
  	// Create request specification
        requestSpec = new RequestSpecBuilder()
      		// Set content type
                .setContentType(ContentType.JSON)
                .addHeader("Authorization","token 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")
             // Set base URL
                .setBaseUri("https://api.github.com")
             // Build request specification
                .build();
        
        responseSpec = new ResponseSpecBuilder()
                // Check status code in response
                .expectStatusCode(201)
                // Check response content type
                .expectContentType("application/json")
                // Check if response contains name property
               // .expectBody("status", equalTo("alive"))
                // Build response specification
                .build();
    }
  @Test(priority=1)
  public void addSSH() {
	  
	  String reqBody = "{\"title\": \"TestAPIKey\", \"key\": \"ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABgQC9DmOh+kN2+PMEN3Er3ZTes2dLNW1GD24Bcqrmv9Gl+Kyjwr0dl7pvZTOFHI/zbFTb8pUI12lE1eJ7FRxQEKw27MZ3cHdbiOmKH4bvz0KJyTQrRHQEyRTPz6M0MddYrl9sVn2b3U2XVd8HAbhS3HAh+Ua4i9j7Z9saW6f5jPi5LmCk34eLWaPV8nzlrBH1d7ATWs7WxVGqskKr7Laoe/9UCrkUpPS8kAYL/Eu6O28pyIQlXKvE9M8dPvBBeokfL7KhLxQpEM823ptx+WdCyodGpAEckvzYQmOyKTwcNbXarvReg8qPZvtT2+QWbxztQbHhiy9hfL/pSUL6j/x+zfCAka5RHd0uEHBY0SwYqsNYtUXfq6BJ7+mKffT/h+a/wfxHJX3Scd8K4tULAUJymV3cHOMJDXzeX9XqPUxJ0AAo0G55R0NyrSnf7MYOVEahIM1jgbJlEORV/g9ia9zcIxOsuB7mR7S49z7ZhNEF1FTY5U9+Z5wkJfk3KpXbnEIntBk=\""
	  		+ "}";
	  Response response = given().spec(requestSpec) // Use requestSpec
              .body(reqBody) // Send request body
              .when().post("/user/keys"); // Send POST request
	  
	  System.out.println(response.getBody().prettyPrint());
	  
	  Id =  response.then().extract().path("id");
	  response.then().statusCode(201);
	  
	  
	 // id = response.then().extract().path("[0].id");
	  //System.out.println(Id);
  }
  
  @Test(priority=2)
  public void getAllSSH() {
	  
	  Response response = 
              given().spec(requestSpec)
              // Set headers
              .when().get("/user/keys"); // Send get request
	  String resBody = response.getBody().asPrettyString();
	  System.out.println(resBody);
	  response.then().statusCode(200);
  }
  
  @Test(priority=3)
  public void deleteSSH() {
	  
	  Response response = 
	          given().spec(requestSpec) // Set headers
	          .pathParam("keyId", Id) // Add path parameter
	          .when().delete("/user/keys/{keyId}");
	  String resBody = response.getBody().asPrettyString();
	  System.out.println(resBody);
	  response.then().statusCode(204);
  }
  

}
