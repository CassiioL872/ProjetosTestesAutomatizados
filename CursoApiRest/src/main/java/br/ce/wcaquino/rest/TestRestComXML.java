package br.ce.wcaquino.rest;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasXPath;
import static org.hamcrest.Matchers.is;

import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;


public class TestRestComXML {

	public static ResponseSpecification respS;
	public static RequestSpecification reqS;
	
	@BeforeClass
	public static void setup() {
		baseURI = "https://restapi.wcaquino.me";
//		port = 80;
//		basePath = "/v2";
		
		RequestSpecBuilder reqBuilder = new RequestSpecBuilder();
		reqBuilder.log(LogDetail.ALL);
		reqS = reqBuilder.build();
		
		ResponseSpecBuilder respBuilder = new ResponseSpecBuilder();
		respBuilder.expectStatusCode(200);
		respS = respBuilder.build();
	
		RestAssured.requestSpecification = reqS;
		RestAssured.responseSpecification = respS;
		
	}
	
	
	
	@Test
	public void devoTrabalharComXML() {
		
		
		
		String nome = given().
		when().
			get("/usersXML/").
		then().
				body("users.user.size()", is(3)).
				body("users.user.@id", hasItems("1", "2", "3")).
				body("users.user[0].name", is("Jo?o da Silva")).
				body("users.user.find{it.age == 25}.name", is("Maria Joaquina")).
				extract().path("users.user.name.findAll{it.toString().startsWith('Maria')}");
	
	
	}
	
	@Test
	public void devoMapearElementosComXPATH() {
		given().
		
		when().
			get("https://restapi.wcaquino.me/usersXML").
		then().
			body(hasXPath("count(/users/user)", is("3"))).
			body(hasXPath("/users/user[@id='1']")).
			body(hasXPath("//users/user[@id='1']"))
			
	
	;}

	


}
