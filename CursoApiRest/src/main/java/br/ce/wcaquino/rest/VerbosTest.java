package br.ce.wcaquino.rest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import java.util.*;
import org.junit.Assert;
import org.junit.Test;
import io.restassured.http.ContentType;

public class VerbosTest {

	@Test
	public void deveSalvarUsuarios() {
		given().
			log().all().
			contentType("application/json").
			body("{\"name\":\"Jose\",\"age\":\"50\"}").
		when().
			post("https://restapi.wcaquino.me/users").
			
		then().
			log().all().	
			statusCode(201).
			body("id", is(notNullValue())).
			body("name", is("Jose")).
			body("age", is("50"))
			
			;}
	
	@Test
	public void naoDeveSalvarUsuarioSemNome() {
		
		given().
		log().all().
		contentType("application/json").
		body("{\"age\":\"50\"}").
	when().
		post("https://restapi.wcaquino.me/users").
		
	then().
		log().all().	
		statusCode(400).
		body("id", is(nullValue())).
		body("error", is("Name ? um atributo obrigat?rio"))
		
	;}
	
	@Test
	public void deveSalvarUsuarioXML() {
		
		given().
		log().all().
		contentType(ContentType.XML).
		body("<user><name>Josefina</name><age>50</age></user>").
	when().
		post("https://restapi.wcaquino.me/usersXML").
		
	then().
		log().all().	
		statusCode(201).
		body("user.@id", is(notNullValue())).
		body("user.name", is("Josefina"))
		.body("user.age", is("50"));
		
	;}
	

	@Test
	public void deveAlterarUsuario() {
		
		given().
		log().all().
		contentType(ContentType.JSON).
		body("{\"name\":\"Silveira\",\"age\":\"80\"}").
	when().
		put("https://restapi.wcaquino.me/users/1").
		
	then().
		log().all()	
		.statusCode(200)
		.body("id", is((1)))
		.body("name", is("Silveira"))
		.body("age", is("80"))
		.body("salary",  is(1234.5678f))
	;}
	
	@Test
	public void customizarUrlParte2() {
		
		given().
		log().all().
		contentType(ContentType.JSON).
		body("{\"name\":\"Silveira\",\"age\":\"80\"}").
		
		//muito util para pegar um elemento de outro metodo por exemplo toke da autenticacao, e ja usar aqui
		pathParam("entidade", "users").
		pathParam("userId", 1).
		
	when().
		put("https://restapi.wcaquino.me/{entidade}/{userId}").
		
	then().
		log().all()	
		.statusCode(200)
		.body("id", is((1)))
		.body("name", is("Silveira"))
		.body("age", is("80"))
		.body("salary",  is(1234.5678f))
	;}
	
	
	@Test
	public void deveDeletarUsuario() {
		given().
			log().all().
		when().
			delete("https://restapi.wcaquino.me/users/1").
		then().
			log().all().
			statusCode(204)
	
	;}
	
	@Test
	public void naoDeveRemoverUsuarioInexistente() {
		given().
			log().all().
		when().
			delete("https://restapi.wcaquino.me/users/1000").
		then().
			log().all().
			statusCode(400).
			body("error", is("Registro inexistente"))
	;}
	
	@Test
	public void deveSalvarUsuarioUsandoMap() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", "Usuario via map");
		params.put("age", 50);
		
		given().
			log().all().
			contentType("application/json").
			body(params).
		when().
			post("https://restapi.wcaquino.me/users").
			
		then().
			log().all().	
			statusCode(201).
			body("id", is(notNullValue())).
			body("name", is("Usuario via map")).
			body("age", is(50))
			
			;}
	
	@Test
	public void deveSalvarUsuarioUsandoObjeto() {
		//muito util se no projeto ja tiver os os objetos, ai da pra reutilizar e usar como parametro do body
		User user = new User("Usuario via Objeto", 35);
		
		given().
			log().all().
			contentType("application/json").
			body(user).
		when().
			post("https://restapi.wcaquino.me/users").
			
		then().
			log().all().	
			statusCode(201).
			body("id", is(notNullValue())).
			body("name", is("Usuario via Objeto")).
			body("age", is(35))
			
			;}
	
	@Test
	public void deveDeserializarObjetoAoSalvarUsuario() {
		//muito util se no projeto ja tiver os os objetos, ai da pra reutilizar e usar como parametro do body
		User user = new User("Usuario via Objeto", 35);
		
		User usuarioInserido = given().
			log().all().
			contentType("application/json").
			body(user).
		when().
			post("https://restapi.wcaquino.me/users").
			
		then().
			log().all().	
			statusCode(201).
			extract().body().as(User.class);
			
			System.out.println(usuarioInserido);
			
			Assert.assertEquals("Usuario via Objeto", usuarioInserido.getName());
			Assert.assertThat(usuarioInserido.getId(), is(notNullValue()));
			Assert.assertThat(usuarioInserido.getAge(), is(35));
			}
	
//	@Test
//	public void deveSalvarUsuarioViaXmlUsandoObjeto() {
//		//muito util se no projeto ja tiver os os objetos, ai da pra reutilizar e usar como parametro do body
//		User user = new User("Usuario XML", 45);
//		
//		User usuarioInserido = given().
//			log().all().
//			contentType(ContentType.XML).
//			body(user).
//		when().
//			post("https://restapi.wcaquino.me/usersXML").
//			
//		then().
//			log().all().	
//			statusCode(201).
//			body("user.@id", is(notNullValue()))
//			
//			
//			;}
//	
	
}


