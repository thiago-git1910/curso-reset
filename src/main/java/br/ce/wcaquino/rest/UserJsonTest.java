package br.ce.wcaquino.rest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import org.junit.Assert;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.util.*;




public class UserJsonTest {
	
	@Test 
	public void verificarPrimeiroNivel() {
		given()
		.when()
			.get("https://restapi.wcaquino.me/users/1")
		.then()
			.statusCode(200)
			.body("id", is (1)) 
			.body("name", containsString("Silva"))
			.body("age",greaterThan(18)) ;
	}

	@Test 
	public void deveVerificarPrimeiroNivelOutrasFormas() {
		Response response = RestAssured.request(Method.GET, "https://restapi.wcaquino.me/users/1") ;
		
		//path 
		 Assert.assertEquals (new Integer(1), response.path("id"));
		 Assert.assertEquals (new Integer(1), response.path("%s", "id"))
		 
		 ;
		 
		 //jsonpath
		 JsonPath jpath = new JsonPath(response.asString());
		 Assert.assertEquals(1, jpath.getInt("id")) ;
		 
		 //from 
		int id = JsonPath.from (response.asString()).getInt ("id");
		Assert.assertEquals(1, id);
	}
	
	@Test
	public void verificarSegundoNivel() {
		given()
		.when()
			.get("https://restapi.wcaquino.me/users/2")
		.then()
			.statusCode(200) 
			.body("name", containsString("Joaquina"))
			.body("endereco.rua", is ("Rua dos bobos")) 
			
			;
		}
	
	@Test 
	public void verificarLista() { 
	given()
	.when()
		.get("https://restapi.wcaquino.me/users/3")
	.then()
		.statusCode(200)
		.body("name", containsString("Ana"))
		.body("filhos", hasSize (2))
		.body("filhos[0].name ", is ("Zezinho") )
		.body("filhos[1].name ", is ("Luizinho") )
		.body("filhos.name ", hasItem ("Zezinho") )
		
		;
	}
	
	@Test 
	public void retornonarUserInexistente() { 
	given()
	.when()
		.get("https://restapi.wcaquino.me/users/4")
	.then()
		.statusCode(404)
		.body ("error", is ("Usuário inexistente") )
			;
	}
	
	@Test 
	public void verificarListaRaiz() {
		given()
		.when()
			.get("https://restapi.wcaquino.me/users")
		.then() 
			.statusCode(200)
			.body ("", hasSize (3) )
			.body("age[1]", is (25))
			.body("filhos.name", hasItem ( Arrays.asList("Zezinho")))
			;
	}
	
	@Test
	public void verificacoesAvancadas () {
		given()
		.when()
			.get("https://restapi.wcaquino.me/users")
		.then() 
			.statusCode(200)
			.body ("", hasSize (3) )
			.body("age.findAll {it <= 25} .size()", is (2))
			.body("age.findAll {it <= 25 && it > 20} .size()", is (1))
			.body("findAll {it.age <= 25 && it.age > 20} .name", hasItem ("Maria Joaquina"))
			.body("findAll {it.age <= 25 }[0].name", is ("Maria Joaquina"))
			.body("findAll {it.age <= 25 }[-1].name", is ("Ana Júlia"))
			.body("find {it.age <= 25 }.name", is ("Maria Joaquina"))
			.body("findAll{it.name.contains('n')} .name ", hasItems("Maria Joaquina" , "Ana Júlia"))
			;
	}
	
	@Test
	public void unirJsonPathComJAVA () {
		ArrayList <String> names =
		given() 
		.when()
			.get("https://restapi.wcaquino.me/users")
		.then() 
			.statusCode(200)
			.extract().path ("name.findAll{it.startsWith('Maria')}")
			
			;
		Assert.assertEquals(1, names.size());
		Assert.assertTrue(names.get(0) .equalsIgnoreCase ("maria joaquina"));
		
	}
	
}
