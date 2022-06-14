package ip.swagger.petstore;

import static io.restassured.RestAssured.*;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.Matchers.*;

public class PetStoreTest extends BaseTest {
    @BeforeTest
    public void setUp()
    {
        super.setUp();
    }

    @Test
    void GetPetBySpecificStatus()
    {
        Response response = get("/v3/pet/findByStatus?status=available");

        List<String> jsonResponse = response.jsonPath().getList("$");
        System.out.println(jsonResponse.size());
        System.out.println("Response: "+response.asString());
        System.out.println("Body: "+response.getBody().asString());
        System.out.println("Status Code: "+response.getStatusCode());
        System.out.println("Header: "+response.getHeader("content-type"));
        System.out.println("Time Taken: "+response.getTime());

        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode,200);
    }

    @Test
    void GetPetsById()
    {
        Response response =
                given().
                        get("/v3/pet/2");

        String jsonResponse = response.getBody().asString();
        JsonPath jsonPath = new JsonPath(jsonResponse);

        int petId = jsonPath.getInt("id");
        String categoryName = jsonPath.get("category.name");

        Assert.assertEquals(petId,2);
        Assert.assertEquals(categoryName,"Cats");
        Assert.assertEquals(response.statusCode(),200);

    }

    @Test
    void GetInvalidPetId()
    {
        Response response =
                given().
                        get("/v3/pet/200");

        Assert.assertTrue( true, "Pet not found");
        Assert.assertEquals(response.statusCode(),404);
    }

    @Test (priority = 1)
    void AddNewPet()
    {
        JSONObject request = new JSONObject();
        JSONObject categoryItems = new JSONObject();
        categoryItems.put("id", "1");
        categoryItems.put("name", "Max");

        JSONArray photos = new JSONArray();
        ArrayList<String> photoUrls = new ArrayList<String>();
        photoUrls.add("image1");
        photos.add(photoUrls);

        JSONArray tagsList = new JSONArray();
        JSONObject tagsItems = new JSONObject();
        tagsItems.put("id", "1");
        tagsItems.put("name", "pet");
        tagsList.add(tagsItems);


        request.put("id", "20");
        request.put("name", "Max");
        request.put("category", categoryItems );
        request.put("photoUrls", photoUrls);
        request.put("tags", tagsList);
        request.put("status", "available");

        given().
                header("Content-Type","application/json").
                body(request.toJSONString()).
        when().
                post("/v3/pet").
        then().
                statusCode(200);
    }

    @Test
    void AddInvalidPet()
    {
        JSONObject request = new JSONObject();
        JSONObject categoryItems = new JSONObject();
        categoryItems.put("id", "1");
        categoryItems.put("name", "Max");

        JSONArray photos = new JSONArray();
        ArrayList<String> photoUrls = new ArrayList<String>();
        photoUrls.add("image1");
        photos.add(photoUrls);

        JSONArray tagsList = new JSONArray();
        JSONObject tagsItems = new JSONObject();
        tagsItems.put("id", "1");
        tagsItems.put("name", "pet");
        tagsList.add(tagsItems);


        request.put("id", "Pet1");
        request.put("name", "Max");
        request.put("category", categoryItems );
        request.put("photoUrls", photoUrls);
        request.put("tags", tagsList);
        request.put("status", "available");

        given().
                header("Content-Type","application/json").
                body(request.toJSONString()).
                when().
                post("/v3/pet").
                then().
                statusCode(400);

        Assert.assertTrue( true, "Input error: unable to convert input to io.swagger.petstore.model.Pet");
    }

    @Test (priority = 2)
    void UpdatePet()
    {
        JSONObject request = new JSONObject();
        JSONObject categoryItems = new JSONObject();
        categoryItems.put("id", "1");
        categoryItems.put("name", "Dogs");

        JSONArray photos = new JSONArray();
        ArrayList<String> photoUrls = new ArrayList<String>();
        photoUrls.add("image1");
        photos.add(photoUrls);

        JSONArray tagsList = new JSONArray();
        JSONObject tagsItems = new JSONObject();
        tagsItems.put("id", "1");
        tagsItems.put("name", "pet");
        tagsList.add(tagsItems);

        request.put("id", "1");
        request.put("name", "Wero");
        request.put("category", categoryItems );
        request.put("photoUrls", photoUrls);
        request.put("tags", tagsList);
        request.put("status", "available");



        given().
                header("Content-Type","application/json").
                body(request.toJSONString()).
        when().
                put("/v3/pet").
        then().
                body("id", equalTo(1)).
                body("name", equalTo("Wero")).
                body("category.name", equalTo("Dogs")).
                statusCode(200);
    }

    @Test
    void DeletePet()
    {
        Response res=
                given().
                when().
                        delete("/v3/pet/3").
                then().
                        statusCode(200).
                        extract().response();

        Assert.assertTrue( true, "Pet deleted");
    }

}
