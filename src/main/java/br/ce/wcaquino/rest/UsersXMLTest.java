package br.ce.wcaquino.rest;

import static io.restassured.RestAssured.*;

import org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.is; //as vezes o eclipse não conhece todas as importações pelo *

import org.junit.Test;


public class UsersXMLTest {

		@Test 
		public void trabalharComXML () {
			given()
			.when()
				.get("https://restapi.wcaquino.me/usersxml/3")
			.then ()
				.statusCode(200)
				.body("user.name", is ("Ana Julia"))
				
			;
		}
		
}
