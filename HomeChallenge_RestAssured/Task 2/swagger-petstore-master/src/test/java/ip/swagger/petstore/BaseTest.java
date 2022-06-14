package ip.swagger.petstore;

import io.restassured.parsing.Parser;
import org.testng.annotations.BeforeTest;

import static io.restassured.RestAssured.*;

public class BaseTest {

    @BeforeTest
    public void setUp()
    {
        useRelaxedHTTPSValidation();
        defaultParser = Parser.JSON;
        baseURI = "http://localhost:8080/api";
    }

}
