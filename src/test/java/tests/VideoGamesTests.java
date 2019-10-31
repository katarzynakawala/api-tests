package tests;

import config.EndPoint;
import config.TestConfig;
import org.testng.annotations.Test;
import org.json.simple.JSONObject;
import static io.restassured.RestAssured.*;


public class VideoGamesTests extends TestConfig {

    JSONObject gameBodyJsonDTO = new JSONObject();


    @Test
    public void createNewGame(){
        //posting new game
        //creation of the body to post - as Json Object
        gameBodyJsonDTO.put("id", 11);
        gameBodyJsonDTO.put("name", "NewGame");
        gameBodyJsonDTO.put("releaseDate", "2017-12-07T11:49:25.712Z");
        gameBodyJsonDTO.put("reviewScore", 50);
        gameBodyJsonDTO.put("category", "Funny");
        gameBodyJsonDTO.put("rating", "Universal");

        given()
                .spec(video_requestSpec)
                .body(gameBodyJsonDTO)
        .when()
                .post(EndPoint.VIDEOGAMES)
        .then()
                .log().all()
                .spec(responseSpec);
    }

    @Test
    public void updateGame(){
        gameBodyJsonDTO.put("id", 11);
        gameBodyJsonDTO.put("name", "UpdatedGame");
        gameBodyJsonDTO.put("releaseDate", "2017-12-07T11:49:25.712Z");
        gameBodyJsonDTO.put("reviewScore", 50);
        gameBodyJsonDTO.put("category", "Funny");
        gameBodyJsonDTO.put("rating", "Universal");

        given()
                .spec(video_requestSpec)
                .log().all()
                .body(gameBodyJsonDTO)
        .when()
                .put("/videogames/11")
        .then()
                .log().all()
                .spec(responseSpec);
    }

    @Test
    public void getAllGames(){
        //getting all games
        given()
                .spec(video_requestSpec)
                .when()
                .get(EndPoint.VIDEOGAMES)
                .then()
                .log().all()
                .spec(responseSpec);
    }

    @Test
    public void getSingleGame(){
        given()
                .spec(video_requestSpec)
                .pathParam("videoGameId", 4)
                .when()
                .get(EndPoint.SINGLE_VIDEOGAME)
                .then()
                .log().all()
                .spec(responseSpec);
    }

    @Test
    public void deleteGame(){
        given()
                .spec(video_requestSpec)
        .when()
                .delete("/videogames/11")
        .then()
                .log().all()
                .spec(responseSpec);
    }
}