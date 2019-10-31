package config;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;

import static org.hamcrest.Matchers.lessThan;

public class TestConfig {

    public static RequestSpecification video_requestSpec;
    public static RequestSpecification football_requestSpec;
    public static ResponseSpecification responseSpec;

    @BeforeClass
    public static void setup(){

        video_requestSpec = new RequestSpecBuilder()
                .setBaseUri("http://localhost")
                .setPort(8080)
                .setBasePath("/app/")
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .build();


        football_requestSpec = new RequestSpecBuilder()
                .setBaseUri("http://api.football-data.org")
                .setBasePath("/v1/")
                .addHeader("X-Response-Control", "minified")
                .build();

        RestAssured.requestSpecification = video_requestSpec;

        responseSpec = new ResponseSpecBuilder()
                //checking status code - it is just for positive responses and also tesponse time < 3s
                .expectStatusCode(200)
                .expectResponseTime(lessThan(3000L))
                .build();

        RestAssured.responseSpecification = responseSpec;
    }
}
