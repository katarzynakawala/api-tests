package tests;

import config.TestConfig;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

public class FootballTests extends TestConfig {

    @Test
    public void getAllCompetitionsOneSeason(){
        given()
                .spec(football_requestSpec)
                .queryParam("season", 2016)
        .when()
                .get("competions/")
        .then()
                .log().all()
                .spec(responseSpec);
    }

    @Test
    public void getTeamCount_OneComp(){
        given()
                .spec(football_requestSpec)
        .when()
                .get("competitions/426/teams")
        .then()
                .spec(responseSpec)
                .log().all()
                .body("count", equalTo(20));
    }

    @Test
    public void extractFirstTeamName(){

        String firstTeamName =
                given()
                        .spec(football_requestSpec)
                .when()
                        .get("competitions/426/teams")
                        .jsonPath().getString("teams.name[0]");

        System.out.println(firstTeamName);
    }

    @Test
    public void getAllTeamData(){
        //usisng Response class
        Response response =
                given()
                        .spec(football_requestSpec)
                .when()
                        .get("competitions/426/teams")
                .then()
                        .spec(responseSpec)
                        .log().all()
                        .contentType(ContentType.JSON)
                        .extract().response();

        String jsonResponseAsString = response.asString();
    }

    @Test
    public void extractHeaders(){

        Response response =
                given()
                        .spec(football_requestSpec)
                .when()
                        .get("competitions/426/teams")
                .then()
                        .spec(responseSpec)
                        .log().all()
                        .contentType(ContentType.JSON)
                        .extract().response();

        //all headers
        Headers headers = response.getHeaders();

        //just one
        String contentType = response.getHeader("Content-Type");
        System.out.println(contentType);
    }

    @Test
    public void extractAllTeamNames(){
        Response response =
                given()
                        .spec(football_requestSpec)
                .when()
                        .get("competitions/426/teams")
                .then()
                        .spec(responseSpec)
                        .log().all()
                        .contentType(ContentType.JSON)
                        .extract().response();

        List<String> teamNames = response.path("teams.name");

        for(String teamName: teamNames){
            System.out.println(teamName);
        }
    }
}
