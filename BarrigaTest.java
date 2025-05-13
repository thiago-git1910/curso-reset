package br.ce.wcaquino.rest.tests;

import static io.restassured.RestAssured.given;

import org.junit.Test;

import br.ce.wcaquino.rest.core.BaseTest;


public class BarrigaTest extends BaseTest {
	
	@Test
	public void naoacessarAPISemToken() {
		given()
			.when()
				.get("/contas")
			.then()
				.statusCode(401)
			;
	}
}
