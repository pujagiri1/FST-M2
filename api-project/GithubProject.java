package liveProject;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class GithubProject {
    RequestSpecification requestSpec;
    String sshKey;
    int sshKeyId;

    @BeforeClass
    public void setUp() {
        // Create request specification
        requestSpec = new RequestSpecBuilder()
                //set content type
                .setContentType(ContentType.JSON)
                //set authorization
                .addHeader("Authorization","token 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")
                //set baseUrl
                .setBaseUri("https://api.github.com")
                .build();
        sshKey="ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQCOLBY9rf8jreRTMifQ/U7CSFKLlRJAlNZDpGL5eXPUkHbkIUy8V89MomMll5KfZS3b2UjbQU9YBHuICtDXbRf6pBv8K6MdQXLQgftw+xuNFf7HC0TWCtUFrIHhEfJSkVWDD0mT5mqbrtzg89SZw+iCVgr7P++LODHgGzyyQJxjeiRzCRsxGpPdMmYBl8UIKDPDPgpkbdLfVYCtclXQ6YHnzaAJKRxY0iWRTYHj4EeXR1RAnVqWjw6gU6r5Kiv6PxbA0B4Fu1GbQVlpNl6WVP1VpW3vAYKLYQKJLEr+jd2yRDmQlPE1TL9SrBH1zJKniSzaAmPxJu1Y2tTa/+CzzipF";
    }
    @Test(priority = 1)
    public void addKeys(){
        String reqBody="{\"title\": \"TestKey\", \"key\": \"" + sshKey +"\"}";
        Response response=given().spec(requestSpec) //use request spec
                .body(reqBody) //send request body
                .when().post(" /user/keys"); //send POST request

        String resBody=response.getBody().asPrettyString();
        System.out.println(resBody);

        sshKeyId = response.then().extract().path("id");

        //Assertions
        response.then().statusCode(201);
    }

    @Test(priority = 2)
    public void getKeys(){
        Response response=given().spec(requestSpec) //use request spec
                .when().get(" /user/keys");
        String resBody=response.getBody().asPrettyString();
        System.out.println(resBody);

        //Assertions
        response.then().statusCode(200);
    }
    @Test(priority = 3)
    public void deleteKeys(){
        Response response=given().spec(requestSpec) //use request spec
                .pathParam("keyId", sshKeyId)
                .when().delete(" /user/keys/{keyId}");
        String resBody=response.getBody().asPrettyString();
        System.out.println(resBody);

        //Assertions
        response.then().statusCode(204);
    }









}
