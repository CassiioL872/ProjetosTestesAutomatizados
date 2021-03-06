package br.ce.wcaquino.rest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.junit.Assert;
import org.junit.Test;

public class FileTest {

	@Test
	public void deveObrigarEnviarArquivo() {
		given()
			.log()
			.all()
		
		.when()
			.post("https://restapi.wcaquino.me/upload")
		.then()
			.log().all()
			.statusCode(404)
			.body("error", is("Arquivo n?o enviado"))
			;}
	
	@Test
	public void deveFazerUploaDoArquivo() {
		given()
			.log().all()
			.multiPart("arquivo", new File("src/main/resources/users.pdf"))
		.when()
			.post("https://restapi.wcaquino.me/upload")
		.then()
			.log().all()
			.statusCode(200)
			.body("name", is("users.pdf"))
			;}
	
	@Test
	public void naoDeveFazerUpLoadDeArquivoGrande() {
		given()
			.log().all()
			.multiPart("arquivo", new File("src/main/resources/guiaUSTIBB.pdf"))
		.when()
			.post("https://restapi.wcaquino.me/upload")
		.then()
			.log().all()
			.statusCode(413)
			.time(lessThan(5000L))
			;}
	
	@Test
	public void deveBaixarArquivo() throws IOException {
		
		byte[] image = given()
		.log().all()
	.when()
		.get("https://restapi.wcaquino.me/download")
	.then()
		.log().all()
		.statusCode(200)
		.extract().asByteArray();
	
		File imagem = new File("src/main/resources/file.jpeg");
		OutputStream out = new FileOutputStream(imagem); 
		out.write(image);
		out.close();
			
		System.out.println(image.length);
		Assert.assertThat(imagem.length(), lessThan(10000L));
		
		;}
	

	}

