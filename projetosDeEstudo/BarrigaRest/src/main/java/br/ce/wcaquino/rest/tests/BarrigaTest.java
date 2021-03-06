package br.ce.wcaquino.rest.tests;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import br.ce.wcaquino.rest.core.BaseTest;

public class BarrigaTest extends BaseTest {

	private String TOKEN;
	
	@Before
	public void login() {
		Map<String, String> login = new HashMap<String, String>();
		login.put("email", "cassio123@gmail.com");
		login.put("senha", "123456");
		
		TOKEN = given()
			.body(login)
		.when()
			.post("/login")
		.then()
			.statusCode(200)
			.extract().path("token");
	}
	
	@Test
	public void naoDeveAcessarAPiSemToken() {
		given()
		
		.when()
			.get("/contas")
		.then()
			.statusCode(200)
		;}
	
	@Test
	public void deveIncluirContaComSucesso() {
			given()
				.header("Authorization", "JWT" + TOKEN)
				.body("{\"conta\": \"conta qualquer\"}	")
				
			.when()
				.post("/login")
			.then()
				.statusCode(200)
				.extract().path("token")

				
	;}
	
	@Test
	public void naoDeveIncluirContaComMemoNome() {
				login();
		given()
				
				.log().all()
				.header("Authorization", "JWT" + TOKEN)
				.body("{\"conta\": \"conta alterada\"}	")
				
			.when()
				.post("/contas")
			.then()
				.statusCode(400)

				
	;}
	
}
