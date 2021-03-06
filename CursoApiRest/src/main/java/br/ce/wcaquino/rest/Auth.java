package br.ce.wcaquino.rest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.sun.javafx.binding.SelectBinding.AsString;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class Auth {

	@Test
	public void deveAcessarSWAPI() {
		
		given().
			log().all()
		.when()
			.get("https://swapi.co/api/people/1")
		.then()
			.log().all()
			.body("name", is("Luke Skywalker"))
			
			;}
	


	
	@Test
	public void deveObterClima() {
		
		given().
			log().all()
			.queryParam("q", "Fortaleza,BR")
			.queryParam("appid", "742056cb04c74becf7267879ba3bcfd3")
			.queryParam("units", "metric")
			.when()
			.get("https://api.openweathermap.org/data/2.5/weather?q=Fortaleza,BR&appid=742056cb04c74becf7267879ba3bcfd3&units=metric")
		.then()
			.log().all()
			.statusCode(200)
			.body("name", is("Fortaleza"))
			.body("coord.lon", is(-38.5247f))
			;}
	
	
	@Test
	public void naoDeveAcessarSemSenha() {
		
		given().
			log().all()
			.when()
			.get("https://restapi.wcaquino.me/basicauth")
		.then()
			.log().all()
			.statusCode(401)
			;}
	
	@Test
	public void deveFazerAutenticacaoBasica() {
		
		given().
			log().all()
			.when()
			.get("https://admin:senha@restapi.wcaquino.me/basicauth")
		.then()
			.log().all()
			.statusCode(200)
			.body("status", is("logado"))
			;}
	
	@Test
	public void deveFazerAutenticacaoBasica2() {
		
		given().
			log().all()
			.auth().basic("admin", "senha")
			.when()
			.get("https://restapi.wcaquino.me/basicauth")
		.then()
			.log().all()
			.statusCode(200)
			.body("status", is("logado"))
			;}
	
	@Test
	public void deveFazerAutenticacaoBasicaChallenge() {
		
		given().
			log().all()
			.auth().preemptive().basic("admin", "senha")
			.when()
			.get("https://restapi.wcaquino.me/basicauth2")
		.then()
			.log().all()
			.statusCode(200)
			.body("status", is("logado"))
			;}
	
	@Test
	public void deveFazerAutenticacaoComToken() {
		Map<String, String> login = new HashMap<String, String>();
		login.put("email", "cassio123@gmail.com");
		login.put("senha", "123456");
		
		
		String token = given().
			log().all()
			.body(login)
			.contentType(ContentType.JSON)
		.when()
			.post("http://barrigarest.wcaquino.me/signin")
		.then()
			.log().all()
			.statusCode(200)
			.extract().path("token");
			
	
			given().
				log().all()
				.header("Authorization", "JWT" + token)
			.when()
				.get("http://barrigarest.wcaquino.me/contas")
			.then()
				.log().all()
				.statusCode(200)
				//	.extract().path("token");
			;}

	@Test
	public void deveAcessarAplicacaoWeb() {
		
		given().
			log().all()
			.formParam("email", "cassio123@gmail.com")
			.formParam("senha", "123456")
			.contentType(ContentType.URLENC.withCharset("UTF-8"))
		.when()
			.post("http://barrigarest.wcaquino.me/login")
		.then()
			.log().all()
		//	.statusCode(200)
			//	.extract().path("token");
	;}
	
//	================ desafio===============
	
	@Test
	public void naoDeveAcessarApiSemToken() {
		given()
			.log().all()
		.when()
			.get("https://seubarriga.wcaquino.me/contas")
		.then()
			.log().all()		
			//.statusCode(401)
		
	;}
	
	@Test
	public void deveFazerLoginERecuperarToken() {
		Map<String, String>	login = new HashMap<String, String>();
		login.put("email", "cassio123@gmail.com");
		login.put("senha", "123456");
		
		given()
		.log().all()
			.body(login)
		.when()
			.post("https://seubarriga.wcaquino.me/cadastro")
		.then()
			.log().all()		
			.statusCode(200)
		
	;}
	
}