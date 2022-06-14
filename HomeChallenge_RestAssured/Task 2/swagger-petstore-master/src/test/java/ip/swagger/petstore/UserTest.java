package ip.swagger.petstore;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

public class UserTest extends BaseTest {

    @BeforeTest
    public void setUp()
    {
        super.setUp();
    }

    @Test
    void GetUserByUserName()
    {
        Response response =
                given().
                        get("/v3/user/user1");

        String jsonResponse = response.getBody().asString();
        JsonPath jsonPath = new JsonPath(jsonResponse);

        int userId = jsonPath.getInt("id");
        String userName = jsonPath.get("username");

        Assert.assertEquals(userId,1);
        Assert.assertEquals(userName,"user1");
        Assert.assertEquals(response.statusCode(),200);
    }

    @Test
    void GetInvalidUserName()
    {
        Response response =
                given().
                        get("/v3/user/user100");

        Assert.assertTrue( true, "User not found");
        Assert.assertEquals(response.statusCode(),404);
    }

    @Test
    void UserLogin()
    {
        Response response =
                given().
                        get("/v3/user/login?username=user1&password=XXXXXXXXXXX");

        Assert.assertEquals(response.statusCode(),200);

    }

    @Test
    void UserLogout()
    {
        Response response =
                given().
                        get("/v3/user/logout");

        Assert.assertTrue( true, "User logged out");

    }

    @Test (priority = 1)
    void AddUsersList()
    {
        JSONArray userList = new JSONArray();
        JSONObject userItems = new JSONObject();
        userItems.put("id", "12");
        userItems.put("username", "user12");
        userItems.put("firstName", "Max");
        userItems.put("lastName", "Mendez");
        userItems.put("email", "rmendez@test.com");
        userItems.put("password", "testing");
        userItems.put("phone", "456789");
        userItems.put("userStatus", "1");
        userList.add(userItems);

        JSONObject userSecondItems = new JSONObject();
        userSecondItems.put("id", "13");
        userSecondItems.put("username", "user13");
        userSecondItems.put("firstName", "Rick");
        userSecondItems.put("lastName", "Velez");
        userSecondItems.put("email", "rvelez@test.com");
        userSecondItems.put("password", "testing");
        userSecondItems.put("phone", "12345");
        userSecondItems.put("userStatus", "1");
        userList.add(userSecondItems);

        given().
                header("Content-Type","application/json").
                body(userList.toJSONString()).
        when().
                post("/v3/user/createWithList").
        then().
                statusCode(200);

    }

    @Test (priority = 3)
    void AddInvalidUsersList()
    {
        JSONArray userList = new JSONArray();
        JSONObject userItems = new JSONObject();
        userItems.put("id", "String1");
        userItems.put("username", "user12");
        userItems.put("firstName", "Max");
        userItems.put("lastName", "Mendez");
        userItems.put("email", "rmendez@test.com");
        userItems.put("password", "testing");
        userItems.put("phone", "456789");
        userItems.put("userStatus", "1");
        userList.add(userItems);

        JSONObject userSecondItems = new JSONObject();
        userSecondItems.put("id", "String2");
        userSecondItems.put("username", "user13");
        userSecondItems.put("firstName", "Rick");
        userSecondItems.put("lastName", "Velez");
        userSecondItems.put("email", "rvelez@test.com");
        userSecondItems.put("password", "testing");
        userSecondItems.put("phone", "12345");
        userSecondItems.put("userStatus", "1");
        userList.add(userSecondItems);

        given().
                header("Content-Type","application/json").
                body(userList.toJSONString()).
                when().
                post("/v3/user/createWithList").
                then().
                statusCode(400);

        Assert.assertTrue( true, "Input error: unable to convert input to io.swagger.petstore.model.User[]");
    }

    @Test (priority = 2)
    void UpdateUser()
    {
        JSONObject request = new JSONObject();

        request.put("id", "1");
        request.put("username", "user1");
        request.put("firstName", "Alex" );
        request.put("lastName", "Mendez");
        request.put("email", "email1@test.com");
        request.put("password", "XXXXXXXXXXX");
        request.put("phone", "123-456-7890");
        request.put("userStatus", "1");

        given().
                header("Content-Type","application/json").
                body(request.toJSONString()).
        when().
                put("/v3/user/user1").
        then().
                body("id", equalTo(1)).
                body("firstName", equalTo("Alex")).
                body("lastName", equalTo("Mendez")).
                statusCode(200);
    }

    @Test
    void DeleteUser()
    {
        Response res=
                given().
                        when().
                        delete("/v3/user/user3").
                        then().
                        statusCode(200).
                        extract().response();

    }

    @Test
    void DeleteInvalidUserName()
    {
        Response response =
                given().
                        get("/v3/user/test");

        Assert.assertTrue( true, "User not found");
        Assert.assertEquals(response.statusCode(),404);
    }

}
