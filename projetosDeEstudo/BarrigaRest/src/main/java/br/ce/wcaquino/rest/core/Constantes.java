package br.ce.wcaquino.rest.core;

import io.restassured.http.ContentType;

public interface Constantes {

	String APP_BASE_URL = "https://seubarriga.wcaquino.me";
	String APP_BASE_PATH = "";
	Integer APP_PORT = 443;
	ContentType APP_CONTENT_TYPE = ContentType.JSON;
	Long MAX_TIMEOUT = 5000L;
	
}
