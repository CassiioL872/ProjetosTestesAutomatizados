package builders;

import static io.restassured.RestAssured.*;

import org.junit.Test;
		


public class TesteApiTheCat {

	@Test
	public void cadastro() {
		//CONDICAO
		given().
		body("{\"email\": \"cassiolima872@gmail.com\", \"appDescription\": \"testeapi\"}").
		
		//ACAO
		when().
		post(" https://api.thecatapi.com/v1/user/passwordlesssignup").
		//VERIFICACAO
		then().log().all();
		
		
	}
	
}
