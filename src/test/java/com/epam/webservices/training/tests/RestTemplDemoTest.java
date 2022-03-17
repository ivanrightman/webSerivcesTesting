package com.epam.webservices.training.tests;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.epam.webservices.training.model.post.Post;

public class RestTemplDemoTest {

	@Test
	public void checkStatusCode() {
		RestTemplate restTeample = new RestTemplate();
		ResponseEntity<Post[]> response = restTeample.getForEntity("http://jsonplaceholder.typicode.com/posts", Post[].class);
		int actStatusCode = response.getStatusCode().value();
		Assert.assertEquals(actStatusCode, 200);
	}
	
	@Test
	public void checkResponseHeader() {
		RestTemplate restTeample = new RestTemplate();
		ResponseEntity<Post[]> response = restTeample.getForEntity("http://jsonplaceholder.typicode.com/posts", Post[].class);
		HttpHeaders headers = response.getHeaders();
		
		String actContentTypeValue = headers.getContentType().toString();		
		Assert.assertEquals(actContentTypeValue, "application/json;charset=utf-8");
	}
	
	@Test()
	public void checkResponseBody() {
		RestTemplate restTeample = new RestTemplate();
		ResponseEntity<Post[]> response = restTeample.getForEntity("http://jsonplaceholder.typicode.com/posts", Post[].class);
		Post[] actPosts = response.getBody();
		
		Assert.assertEquals(actPosts.length, 100);
	}
	
	
}
