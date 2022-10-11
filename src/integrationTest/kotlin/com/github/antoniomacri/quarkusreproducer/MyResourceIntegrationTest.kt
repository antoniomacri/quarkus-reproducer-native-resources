package com.github.antoniomacri.quarkusreproducer

import io.quarkus.test.junit.QuarkusIntegrationTest
import io.restassured.RestAssured.given
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.Test


@QuarkusIntegrationTest
class MyResourceIntegrationTest {
    @Test
    fun test_ua() {
        given().`when`()
            .header(
                "User-Agent",
                "Mozilla/5.0 (iPhone; CPU iPhone OS 5_1 like Mac OS X) AppleWebKit/534.46 (KHTML, like Gecko) Version/5.1 Mobile/9B179 Safari/7534.48.3"
            )
            .get()
            .then()
            .statusCode(200)
            .body(`is`("iPhone"))
    }
}
