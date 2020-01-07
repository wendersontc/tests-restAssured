package br.com.lojasrenner.uri.tests;

import static io.restassured.RestAssured.basic;
import static io.restassured.RestAssured.delete;
import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.com.lojasrenner.uri.model.ItemFull;
import io.restassured.RestAssured;
import io.restassured.response.Response;



public class UriApiTest {
	
	public static String URL = "https://uristorageprod.blob.core.windows.net/item/v1/001/BR/full/10009.json";
	
	
	/*@Test
	public void verificaCabecalhoResposta() {
		
		Long id = 10009L;
		
		List<Long> ids = Arrays.asList(1L,2L,3L,4L,11100L,12121L);
		
		ids.stream().forEach(i -> {
			get(URL+id+".json").
			then().
			statusCode(200).
				and().
			contentType(JSON);
		});
		
	}*/
	
	
	@Test
	public void verificaCabecalhoResposta() {
		get(URL).
			then().
			statusCode(200).
				and().
			contentType("application/octet-stream");
	}
	
	
	@Test
	public void verificaRetornoDeItensESizes() {
		get(URL).
			then().
			body("itens", notNullValue()).
				and().
			body("itens", not(empty())).
				and().
			body("sizes", notNullValue());

	}
	
	@Test
	public void verificaAtributosDoCampoItens() {
		List<ItemFull> item = get(URL).getBody().jsonPath().getList("itens", ItemFull.class);
		
		assertThat(item, everyItem(hasProperty("sku", notNullValue())));
		assertThat(item, everyItem(hasProperty("size", notNullValue())));
		assertThat(item, everyItem(hasProperty("parentitemcode", notNullValue())));
		assertThat(item, everyItem(hasProperty("rfid" , notNullValue())));
		
	}


}
