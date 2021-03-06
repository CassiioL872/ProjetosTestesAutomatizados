package br.ce.wcaquino.rest;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.request;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

public class OlaMundoTest {

	@Test
	public void testOlaMundo() {
		
		Response response = request(Method.GET, "http://restapi.wcaquino.me:80/ola");
		assertTrue(response.getBody().asString().equals("Ola Mundo!"));
		assertTrue("O status code deve ser 201", response.statusCode() == 200);
		
		ValidatableResponse validacao = response.then();
		validacao.statusCode(200);
		
	}
	
	@Test
	public void devoConhecerOutrasFormasRestAssured() {
		Response response = request(Method.GET, "http://restapi.wcaquino.me:80/ola");
		ValidatableResponse validacao = response.then();
		validacao.statusCode(200);
	
		given()
		.when()
			.get("http://restapi.wcaquino.me:80/ola")
		.then()
			.statusCode(200);
	}
	
	@Test
	public void devoValidarBody() {
		given().
		when().
			get("http://restapi.wcaquino.me:80/ola").
		then().
		statusCode(200).	
		body(is("Ola Mundo!"))
		.body(containsString("Mundo"));
	}
	
	
}
