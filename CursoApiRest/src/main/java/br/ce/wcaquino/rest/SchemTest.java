package br.ce.wcaquino.rest;

import static io.restassured.RestAssured.given;
import static io.restassured.matcher.RestAssuredMatchers.matchesXsdInClasspath;

import org.junit.Test;

public class SchemTest {

	@Test
	public void deveValidarSchemaXML() {
		given()
			.log().all()
		.when()
			.get("https://restapi.wcaquino.me/usersXML")
		.then()
			.log().all()
			.statusCode(200)
			.body(matchesXsdInClasspath("users.xsd"))
			;}
	
}
