package com.codeutils.exam.rest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = RestApiExamApplication.class)
@WebIntegrationTest({"server.port=10443"})
public class RestApiExamApplicationTests {
	private static final String URL = "http://localhost:10443/api/v1/processcountvaliditem";
	private static final String URL_TST = "http://localhost:10443/api/v1/tst";
	
	RestTemplate rt = new TestRestTemplate();

//	@Test
//	public void testSampleData() {
//		ExamResult response = rt.postForObject(URL, getRequestData(), ExamResult.class);
//		System.out.println(response.getResultCategoryCount().toString() + response.getResultCategoryCount().toString());
//		for (CategorySubCategory item:response.getResultCategorySub())
//			System.out.println("this is value: " + item.getCategory() + ", " + item.getSubCategory());
//	}
	
	@Test
	public void testSampleData() {
		CategorySubCategory[] response = rt.postForObject(URL_TST, getRequestData(), CategorySubCategory[].class);
		for (CategorySubCategory item:response)
			System.out.println(item.getCategory());
	}
	
	private List<CategorySubCategory> getRequestData() {
		List<CategorySubCategory> requestData = new ArrayList<CategorySubCategory>();
		requestData.add(new CategorySubCategory("PERSON", "Bob Jones"));
		requestData.add(new CategorySubCategory("PLACE", "Washington"));	
		requestData.add(new CategorySubCategory("PERSON", "Mary"));	
		requestData.add(new CategorySubCategory("COMPUTER", "Mac"));	
		requestData.add(new CategorySubCategory("PERSON", "Bob Jones"));	
		requestData.add(new CategorySubCategory("OTHER", "Tree"));	
		requestData.add(new CategorySubCategory("ANIMAL", "Dog"));	
		requestData.add(new CategorySubCategory("PLACE", "Texas"));	
		requestData.add(new CategorySubCategory("FOOD", "Steak"));	
		requestData.add(new CategorySubCategory("ANIMAL", "Cat"));			
		requestData.add(new CategorySubCategory("PERSON", "Mac"));
		
		return requestData;
	}
	
	private List<CategorySubCategory> getExpectedResponseData() {
		List<CategorySubCategory> responseData = new ArrayList<CategorySubCategory>();
		responseData.add(new CategorySubCategory("PERSON", "Bob Jones"));
		responseData.add(new CategorySubCategory("PLACE", "Washington"));	
		responseData.add(new CategorySubCategory("PERSON", "Mary"));	
		responseData.add(new CategorySubCategory("COMPUTER", "Mac"));
		responseData.add(new CategorySubCategory("OTHER", "Tree"));	
		responseData.add(new CategorySubCategory("ANIMAL", "Dog"));	
		responseData.add(new CategorySubCategory("PLACE", "Texas"));	
		responseData.add(new CategorySubCategory("ANIMAL", "Cat"));			
		responseData.add(new CategorySubCategory("PERSON", "Mac"));
		
		return responseData;
	}
	
	private Map<String, Integer> getExpectedResponseCount() {
		Map<String, Integer> responseData = new HashMap<String, Integer>();
		responseData.put("PERSON", 3);
		responseData.put("PLACE", 2);
		responseData.put("ANIMAL", 2);
		responseData.put("COMPUTER", 1);		
		responseData.put("OTHER", 1);
		responseData.put("PLACE", 2);
		
		return responseData;
	}

}
