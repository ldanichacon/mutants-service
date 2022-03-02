package com.mercadolibre.mutants;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.mutants.model.StatsResponse;
import com.mercadolibre.mutants.repository.MutantsRepository;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.vertx.core.json.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static io.restassured.RestAssured.given;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@QuarkusTest
@Slf4j
public class MutantsServiceTest {
    private static final String INVALID_ARRAY_PATH = "src/test/resources/invalid-array.json";
    private static final String INVALID_CHARACTER_PATH = "src/test/resources/invalid-character.json";
    private static final String STATS_PATH = "src/test/resources/stats.json";
    private static final String TEST_0_PATH = "src/test/resources/test-0.json";
    private static final String TEST_1_PATH = "src/test/resources/test-1.json";
    private static final String TEST_2_PATH = "src/test/resources/test-2.json";
    private static final String TEST_3_PATH = "src/test/resources/test-3.json";
    private static final String TEST_4_PATH = "src/test/resources/test-4.json";
    private static final String TEST_5_PATH = "src/test/resources/test-5.json";
    private static final String TEST_6_PATH = "src/test/resources/test-6.json";
    private static final String TEST_7_PATH = "src/test/resources/test-7.json";
    private static final String TEST_8_PATH = "src/test/resources/test-8.json";
    private static final String TEST_9_PATH = "src/test/resources/test-9.json";
    private static final String IS_MUTANT_ENDPOINT = "/mutant";
    private static final String STATS_ENDPOINT = "/stats";
    private static final ObjectMapper mapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    private static JsonObject expectedStats;
    @InjectMock
    MutantsRepository repository;

    @BeforeEach
    public void setUp() throws IOException {
        doNothing().when(repository).saveDna(any(), anyBoolean());
        String expectedStatsString = Files.readString(Path.of(STATS_PATH));
        StatsResponse expectedStatsObject = mapper
                .readValue(expectedStatsString, StatsResponse.class);
        expectedStats = JsonObject.mapFrom(expectedStatsObject);
        when(repository.getStats()).thenReturn(expectedStatsObject);
    }

    @Test
    public void testIsMutantEndpointInvalidArray() throws IOException {
        given()
                .when()
                .body(Files.readString(Path.of(INVALID_ARRAY_PATH)))
                .contentType(ContentType.JSON)
                .post(IS_MUTANT_ENDPOINT)
                .then()
                .statusCode(400);
    }

    @Test
    public void testIsMutantEndpointInvalidCharacter() throws IOException {
        given()
                .when()
                .contentType(ContentType.JSON)
                .body(Files.readString(Path.of(INVALID_CHARACTER_PATH)))
                .post(IS_MUTANT_ENDPOINT)
                .then().statusCode(400);
    }

    @Test
    public void testIsMutantEndpoint_0() throws IOException {
        given()
                .when()
                .contentType(ContentType.JSON)
                .body(Files.readString(Path.of(TEST_0_PATH)))
                .post(IS_MUTANT_ENDPOINT)
                .then()
                .statusCode(403);
    }

    @Test
    public void testIsMutantEndpoint_1() throws IOException {
        given()
                .when()
                .contentType(ContentType.JSON)
                .body(Files.readString(Path.of(TEST_1_PATH)))
                .post(IS_MUTANT_ENDPOINT)
                .then()
                .statusCode(200);
    }

    @Test
    public void testIsMutantEndpoint_2() throws IOException {
        given()
                .when()
                .contentType(ContentType.JSON)
                .body(Files.readString(Path.of(TEST_2_PATH)))
                .post(IS_MUTANT_ENDPOINT)
                .then()
                .statusCode(200);
    }

    @Test
    public void testIsMutantEndpoint_3() throws IOException {
        given()
                .when()
                .contentType(ContentType.JSON)
                .body(Files.readString(Path.of(TEST_3_PATH)))
                .post(IS_MUTANT_ENDPOINT)
                .then()
                .statusCode(200);
    }

    @Test
    public void testIsMutantEndpoint_4() throws IOException {
        given()
                .when()
                .contentType(ContentType.JSON)
                .body(Files.readString(Path.of(TEST_4_PATH)))
                .post(IS_MUTANT_ENDPOINT)
                .then()
                .statusCode(200);
    }

    @Test
    public void testIsMutantEndpoint_5() throws IOException {
        given()
                .when()
                .contentType(ContentType.JSON)
                .body(Files.readString(Path.of(TEST_5_PATH)))
                .post(IS_MUTANT_ENDPOINT)
                .then()
                .statusCode(200);
    }

    @Test
    public void testIsMutantEndpoint_6() throws IOException {
        given()
                .when()
                .contentType(ContentType.JSON)
                .body(Files.readString(Path.of(TEST_6_PATH)))
                .post(IS_MUTANT_ENDPOINT)
                .then()
                .statusCode(200);
    }

    @Test
    public void testIsMutantEndpoint_7() throws IOException {
        given()
                .when()
                .contentType(ContentType.JSON)
                .body(Files.readString(Path.of(TEST_7_PATH)))
                .post(IS_MUTANT_ENDPOINT)
                .then()
                .statusCode(200);
    }

    @Test
    public void testIsMutantEndpoint_8() throws IOException {
        given()
                .when()
                .contentType(ContentType.JSON)
                .body(Files.readString(Path.of(TEST_8_PATH)))
                .post(IS_MUTANT_ENDPOINT)
                .then()
                .statusCode(403);
    }

    @Test
    public void testIsMutantEndpoint_9() throws IOException {
        given()
                .when()
                .contentType(ContentType.JSON)
                .body(Files.readString(Path.of(TEST_9_PATH)))
                .post(IS_MUTANT_ENDPOINT)
                .then()
                .statusCode(403);
    }

    @Test
    public void testStats() {
        Response response = given()
                .when()
                .get(STATS_ENDPOINT);
        JsonObject actualResponse = new JsonObject(response.body().print());
        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertEquals(expectedStats, actualResponse);
    }
}