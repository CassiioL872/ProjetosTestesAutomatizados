package br.ce.wcaquino.rest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import org.junit.*;
import io.restassured.RestAssured;
import io.restassured.RestAssured.*;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class UserJsonTest {

	@Test
	public void deveVerificarPrimeiroNivel() {
		given()
		.when()
			.get("https://restapi.wcaquino.me/users/1")
		.then()
			.statusCode(200)
			.body("id", is(1))
			.body("name", containsString("Silva"))
			.body("age", greaterThan(18))
	;}
	
	@Test
	public void deveVerificarPrimeiroNivelComOutrasFormas() {
		Response response = RestAssured.get("https://restapi.wcaquino.me/users/1");
		
		Assert.assertEquals(new Integer(1), response.path("id"));
		
		JsonPath json = new JsonPath(response.asString());
		//Assert.assertEquals(1, json.getInt("1"));
	}
	
	@Test
	public void deveValidarTerceiroId() {
		given()
		.when()
			.get("https://restapi.wcaquino.me/users/2")
		.then()
			.statusCode(200)
			.body("id", is(2))
			.body("name", containsString("Maria Joaquina"))
			.body("endereco.rua", is("Rua dos bobos"))
			;}
	


	@Test
	public void deveValidarLista() {
		given().
		when()
			.get("https://restapi.wcaquino.me/users/3").
		then().
			statusCode(200).
			body("filhos", hasSize(2)).
			body("filhos[0].name", is("Zezinho"))
			.body("filhos[1].name",  is("Luizinho"))
			
		;}

	@Test
	public void deveRetornarMensagemUsuarioInexistente() {
		
		given().
		when()
			.get("https://restapi.wcaquino.me/users/4").
		then().
			statusCode(404).
			body("error", is("Usu?rio inexistente"))
		
	;}
	
	
	@Test
	public void deveVerificarListaRaiz() {
		given().
		when()
			.get("https://restapi.wcaquino.me/users").
		then().
			statusCode(200).
			body("name", hasItems("Jo?o da Silva", "Maria Joaquina", "Ana J?lia"))
		
		
	;}
	
	@Test
	public void deveFazerValidacaoAvancada() {
		given().
		when()
			.get("https://restapi.wcaquino.me/users").
		then().
			statusCode(200).
			body("age.findAll{it <= 25 && it > 20}.size()", is(1)).
			body("findAll{ it.age <= 25 && it.age > 20}[0].name", is("Maria Joaquina"))
		
	;}
	
	@Test
	public void deveunirJsonComJAVA() {
		
		ArrayList<String> nomes = 
		
		given().
		when()
			.get("https://restapi.wcaquino.me/users").
		then().
			statusCode(200).
			extract().path("nome.findAll{it.startsWith('Maria')}");
			assertEquals(1, nomes.size());
			
			
		
	}
	
	
}
