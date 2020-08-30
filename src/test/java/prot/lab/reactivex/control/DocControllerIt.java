package prot.lab.reactivex.control;

import io.restassured.http.Method;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import prot.lab.reactivex.model.Doc;

import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.*;
//import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class DocControllerIt {
    @BeforeAll
    public static void setup() {
        // these 2 values are default
        baseURI = "http://localhost";
        port = 8080;
    }

    @Test
    public void test001_ok() {
        get("/001")
                .then().statusCode(200)
                .assertThat()
                .body(equalTo("Hello World"));
    }

    @Test
    public void test002_ok() {
        get("/002")
                .then().statusCode(200)
                .assertThat()
                .body(equalTo("How is the weather?"));
    }

    @Test
    public void test003_doc_ok() {
        get("/003")
                .then().statusCode(200)
                .assertThat()
                .body("id", equalTo("999"));
    }

    @Test
    public void test004_param_empty_ok() {
        get("/004")
                .then().statusCode(200)
                .assertThat()
                .body("requestIds", nullValue());
    }

    @Test
    public void test004_param_n_ok() {
        get("/004?id=80&id=90&name=george&name=lily&name=linda")
                .then().statusCode(200)
                .assertThat()
                .body("requestIds", hasItems("80", "90")
                        , "requestNames", hasItems("george")
                        , "requestNames", hasSize(3));
    }

    @Test
    public void test005_id_name_path_ok() {
        get("/005/88/george")
                .then().statusCode(200)
                .assertThat()
                .body("id", equalTo("88")
                        , "name", equalTo("george"));
    }

    @Test
    public void test006_id_name_path_ok() {
        get("/006/888_linda-wang")
                .then().statusCode(200)
                .assertThat()
                .body("id", equalTo("888")
                        , "name", equalTo("linda-wang"));
    }

    @Test
    public void test_create_doc_ok() {
        Doc doc = new Doc();
        doc.setName("Effective Java");
        doc.setFormat("application/pdf");
        doc.setSize(9838938L);
        with().contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(doc).when().request(Method.POST, "/docs")
                .then().statusCode(200)
                .assertThat()
                .body("id", notNullValue()
                        , "name", equalTo(doc.getName()));
    }

    @Test
    public void test_flux_sse() {
        Response response = get("/docs/flux-sse");
        long timeInS = response.timeIn(TimeUnit.SECONDS);
        System.out.printf("Time in seconds: [%d]%n", timeInS);
        response.then().time(greaterThan(100L))
                .assertThat().statusCode(200)
                .assertThat().body( equalTo("data:result-001\n" +
                                "\n" +
                                "data:result-002\n" +
                                "\n" +
                                "data:result-003\n" +
                                "\n" +
                                "data:result-004\n" +
                                "\n" +
                                "data:result-005\n" +
                                "\n" +
                                "data:result-006\n" +
                                "\n" +
                                "data:result-007\n" +
                                "\n" +
                                "data:result-008\n" +
                                "\n" +
                                "data:result-009\n" +
                                "\n" +
                                "data:result-010\n" +
                                "\n" +
                                "data:result-011\n" +
                                "\n" +
                                "data:result-012\n" +
                                "\n" +
                                "data:result-013\n" +
                                "\n" +
                                "data:result-014\n" +
                                "\n" +
                                "data:result-015\n" +
                                "\n" +
                                "data:result-016\n" +
                                "\n" +
                                "data:result-017\n" +
                                "\n" +
                                "data:result-018\n" +
                                "\n" +
                                "data:result-019\n\n"));
    }


}
