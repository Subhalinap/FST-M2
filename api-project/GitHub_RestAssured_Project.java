import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.oauth2;

public class GitHub_RestAssured_Project {
    public RequestSpecification reqSpec;
    //in code shared only dummy token and ssh key
    String gitAccessToken = "ghp_xxxx";
    String SSHKey = "ssh-ed25519 xxxx";
    int id;

    @BeforeClass
    public void setUp() {

        reqSpec = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setAuth(oauth2(gitAccessToken))
                .setBaseUri("https://api.github.com")
                .setBasePath("/user/keys").build();
    }

    @Test(priority = 0)
    public void gitPostMethod() {

        String reqBody = "{ \"title\": \"TestAPIKey\",\n" +
                "    \"key\": \""+SSHKey+"\"\n}";

        Reporter.log("Request Body => "+reqBody, true);

        Response res = given()
                .spec(reqSpec).body(reqBody).log().uri()
                .when().post();

        Reporter.log(res.prettyPrint(), true);

        //id = res.getBody().path("id");
        id = res.then().extract().path("id");
        Reporter.log("id = " + id, true);

        res.then().statusCode(201);

    }

    @Test(priority = 1)
    public void gitGetMethod() {

        Response res = given().spec(reqSpec).when().get();

        //res.prettyPrint();
        Reporter.log(res.prettyPrint(), true);

        res.then().statusCode(200);

    }

    @Test(priority = 2)
    public void gitDeleteMethod() {

        Response res = given().spec(reqSpec).pathParams("keyId", id)
                .when().delete("{keyId}");

        System.out.println("________________________________________");
        res.then().log().all();
        System.out.println("________________________________________");

        Reporter.log(res.prettyPrint(), true);

        res.then().statusCode(204);
    }
}
