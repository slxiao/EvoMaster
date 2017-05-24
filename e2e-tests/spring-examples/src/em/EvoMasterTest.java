

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static io.restassured.RestAssured.given;
import static org.evomaster.clientJava.controllerApi.EMTestUtils.*;
import org.evomaster.clientJava.controller.SutHandler;


/**
 * This file was automatically generated by EvoMaster on 2017-05-20T20:31:34.883+02:00[Europe/Oslo]
 * <br>
 * The generated test suite contains 2 tests
 */
public class EvoMasterTest {

    
    private static SutHandler controller = new com.foo.rest.examples.spring.positiveinteger.PIController();
    private static String baseUrlOfSut;
    
    
    @BeforeClass
    public static void initClass() {
        baseUrlOfSut = controller.startSut();
        assertNotNull(baseUrlOfSut);
    }
    
    
    @AfterClass
    public static void tearDown() {
        controller.stopSut();
    }
    
    
    @Before
    public void initTest() {
        controller.resetStateOfSUT();
    }
    
    
    
    
    @Test
    public void test0() throws Exception {
        
        given().accept("*/*")
                .get(baseUrlOfSut + "/api/pi/-814198304")
                .then()
                .statusCode(200);
    }
    
    
    @Test
    public void test1() throws Exception {
        
        given().accept("*/*")
                .contentType("application/json")
                .body("{\"value\":117}")
                .post(baseUrlOfSut + "/api/pi")
                .then()
                .statusCode(200);
    }


}