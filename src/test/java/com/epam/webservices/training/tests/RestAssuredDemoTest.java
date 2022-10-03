package com.epam.webservices.training.tests;

import com.epam.webservices.training.model.user.User;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.epam.webservices.training.model.post.Post;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class RestAssuredDemoTest {

	@BeforeTest
	public void initTest() {
		RestAssured.baseURI = "http://jsonplaceholder.typicode.com";
	}
	private String uriPosts = "/posts";
	private String uriUsers = "/users";

	@DataProvider(parallel = true)
	public Iterator<Object[]> uriFromCsv() throws IOException {
		List<Object> uriList = new ArrayList<>();
		BufferedReader reader = new BufferedReader(new FileReader(("src/test/resources/uri.csv")));
		String line = reader.readLine();
		while (line != null) {
			uriList.add(line);
			line = reader.readLine();
		}
		return uriList.stream()
				.map((g) -> new Object[] {g})
				.collect(Collectors.toList()).iterator();
	}

	@Test(dataProvider = "uriFromCsv")
	public void checkStatusCodes(String uri) {
		Response response = RestAssured
				.when()
				.get(uri)
				.andReturn();
		Assert.assertEquals(response.getStatusCode(), 200);
	}

	@Test(dataProvider = "uriFromCsv")
	public void checkResponseHeader(String uri) {
		Response response = RestAssured
				.when()
				.get(uri)
				.andReturn();
		String rpContentTypeHeader = response.getHeader("Content-Type");
		Assert.assertEquals(rpContentTypeHeader, "application/json; charset=utf-8");
	}
	
	@Test
	public void checkResponseBodyPosts() {
		Response response = RestAssured
				.when()
				.get(uriPosts)
				.andReturn();
		ResponseBody<?> responseBody = response.getBody();
		Post[] posts = responseBody.as(Post[].class);
		Assert.assertEquals(posts.length, 100);
	}

	@Test
	public void checkResponseBodyUsers() {
		Response response = RestAssured
				.when()
				.get(uriUsers)
				.andReturn();
		ResponseBody<?> responseBody = response.getBody();
		User[] users = responseBody.as(User[].class);
		Assert.assertEquals(users.length, 10);
	}
	
}
