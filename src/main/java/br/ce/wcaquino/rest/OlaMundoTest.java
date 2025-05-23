package br.ce.wcaquino.rest;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*; 
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;




public class OlaMundoTest {
	
	@Test
	public void testOlaMundo() {
	    Response response = RestAssured.request(Method.GET, "http://restapi.wcaquino.me/ola");

        Assert.assertTrue(response.getBody().asString().equals("Ola Mundo!"));
        Assert.assertTrue(response.statusCode() ==200 );
        Assert.assertTrue("O status code deveria ser 200", response.statusCode() ==200 );
        Assert.assertEquals(200, response.getStatusCode());
        
        ValidatableResponse validacao = response.then();
        validacao.statusCode(200);
		
	}
	
	@Test
	public void devoConhecerOutrasFormasRestAssured() {
		Response response = RestAssured.request(Method.GET, "http://restapi.wcaquino.me/ola");
        ValidatableResponse validacao = response.then();
        validacao.statusCode(200);
        
        get("http://restapi.wcaquino.me/ola").then().statusCode(200);
        
        given() // pré condições 
        .when() // ação 
        	.get("http://restapi.wcaquino.me/ola")
        .then() // assertivas 
        	.statusCode(200);
	}
	
	@Test
	public void devoConhecerMatchersHamcrest() {
			Assert.assertThat("ney", Matchers.is("ney"));
			Assert.assertThat(128, Matchers.is(128));
			Assert.assertThat(128, Matchers.isA(Integer.class));
			Assert.assertThat(128d, Matchers.isA(Double.class));
			Assert.assertThat(128d, Matchers.greaterThan(120d));
			Assert.assertThat(128d, Matchers.lessThan(130d));
			
			
			List<Integer> impares = Arrays.asList(1,3,5,7,9);
			assertThat(impares, hasSize(5));
			assertThat(impares, contains(1,3,5,7,9));
			assertThat(impares, containsInAnyOrder(1,3,5,9,7));
			assertThat(impares, hasItem(1));
			assertThat(impares, hasItems(1, 5));
			
			assertThat("vini jr", is(not("ney")));
			assertThat("vini jr", not("ney"));
			assertThat("joaquina", anyOf(is("vini jr"), is("joaquina")));
		
	}
	
	@Test 
	public void devoValidarBody() {
		
		given() // pré condições 
        .when() // ação 
        	.get("http://restapi.wcaquino.me/ola")
        .then() 
        	.statusCode(200)
        	.body( is("Ola Mundo!"))
        	.body(containsString("Mundo"))
        	.body(is(not(nullValue() ))); 
		
	}
	
}
	
