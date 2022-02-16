package br.ce.wcaquino.rest;

import static io.restassured.RestAssured.given;

import org.junit.Test;

import io.restassured.http.ContentType;

public class HTML {

	@Test
	public void deveFazerBuscasComHTML() {
		given()
			.log().all()
		.when()
			.get("https://restapi.wcaquino.me/v2/users")
		
		.then()
			.log().all()
			.contentType(ContentType.HTML)
	;}
	
}
