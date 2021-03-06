package br.ce.wcaquino.rest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import org.junit.Test;
import io.restassured.http.ContentType;

public class EnviaDadosTeste {

	@Test
	public void deveEnviarValorViaQuery() {
		given()
		.when()
			.get("https://restapi.wcaquino.me/v2/users?format=xml")
		
		.then()
			.log().all()
			.statusCode(200)
			.contentType(ContentType.XML)
			;}
	
	@Test
	public void deveEnviarValorViaQueryParam() {
		given()
			.log().all()
			.queryParam("format", "xml")
		.when()
			.get("https://restapi.wcaquino.me/v2/users")
		
		.then()
			.log().all()
			.statusCode(200)
			.contentType(ContentType.XML)
			.contentType(containsString("utf-8"))
			;}
	
	@Test
	public void deveEnviarValorViaHeader() {
		given()
			.log().all()
			.accept(ContentType.JSON)
			.when()
			.get("https://restapi.wcaquino.me/v2/users")
		
		.then()
			.log().all()
			.statusCode(200)
			.contentType(ContentType.HTML)
			;}
	
}
