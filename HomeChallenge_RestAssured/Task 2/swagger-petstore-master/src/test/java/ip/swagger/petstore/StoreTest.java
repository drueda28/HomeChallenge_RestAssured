package ip.swagger.petstore;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;

public class StoreTest extends BaseTest {

    @BeforeTest
    public void setUp()
    {
        super.setUp();
    }

    @Test (priority = 2)
    void GetStoreInventory()
    {
        Response response =
                given().
                        get("/v3/store/inventory");

        System.out.println("Response: "+response.asString());
        System.out.println("Body: "+response.getBody().asString());
        System.out.println("Status Code: "+response.getStatusCode());
        System.out.println("Header: "+response.getHeader("content-type"));
        System.out.println("Time Taken: "+response.getTime());

        Assert.assertEquals(response.statusCode(),200);
    }

    @Test
    void GetInvalidOrder()
    {
        Response response =
                given().
                        get("/v3/store/order/100");

        Assert.assertTrue( true, "Order not found");
        Assert.assertEquals(response.statusCode(),404);

    }

    @Test
    void GetOrder()
    {
        Response response =
                given().
                        get("/v3/store/order/2");

        System.out.println("Response: "+response.asString());
        System.out.println("Body: "+response.getBody().asString());
        System.out.println("Status Code: "+response.getStatusCode());
        System.out.println("Header: "+response.getHeader("content-type"));
        System.out.println("Time Taken: "+response.getTime());

        Assert.assertEquals(response.statusCode(),200);
    }

    @Test (priority = 1)
    void DeleteOrder()
    {
        Response res=
                given().
                        when().
                        delete("/v3/store/order/1").
                then().
                        statusCode(200).
                        extract().response();
    }

    @Test
    void DeleteInvalidOrder()
    {
        Response response =
                given().
                        get("/v3/user/test");

        Assert.assertTrue( true, "User not found");
        Assert.assertEquals(response.statusCode(),404);

    }

}
