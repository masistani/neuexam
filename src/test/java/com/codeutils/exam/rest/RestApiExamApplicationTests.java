package com.codeutils.exam.rest;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
	private static final String BASE_URL = "http://localhost:10443/api/v1";
	private static final String URL = BASE_URL + "/processcountvaliditem";
	private static final String URL_LIST_CATEGORY = BASE_URL + "/listcategory";
	private static final String URL_ADD_CATEGORY = BASE_URL + "/addcategory";
	private static final String URL_REMOVE_CATEGORY = BASE_URL + "/removecategory";
	private static final String URL_IS_ACTIVE = BASE_URL + "/isactive";
	
	RestTemplate rt = new TestRestTemplate();

	/**
	 * test if the expected response of category, sub-category is correct
	 * (remove duplicate and only pass valid categories)
	 */
	@Test
	public void testCategorySubcategoryData() {
		ExamResult response = rt.postForObject(URL, getRequestData(), ExamResult.class);
		assertTrue(response.getResultCategorySub().equals(getExpectedResponseData()));
	}
	
	/**
	 * test if the expected response (category count) is correct
	 */
	@Test
	public void testCateoryCount() {
		ExamResult response = rt.postForObject(URL, getRequestData(), ExamResult.class);
		assertTrue(response.getResultCategoryCount().equals(getExpectedResponseCount()));
	}
	
	/**
	 * test if add an item which is not a part of valid category, then we still
	 * have to receive same result. Randomize category to prevent test case
	 * which category might be same
	 */
	@Test
	public void testExpectedResult() {
		addCategory("new_random_category_to_test_" + UUID.randomUUID().toString());
		testCategorySubcategoryData();
		testCateoryCount();
	}
	
	/**
	 * add a new and get the list of categories, if item exists then pass the test otherwise fail
	 */
	@Test
	public void testAddCategory() {
		String testCategory = "new_sample_category";
		addCategory(testCategory);
		String[] response = rt.getForObject(URL_LIST_CATEGORY, String[].class);

		for (String item : response) {
			if (item.equals(testCategory)) {
				assertTrue(true);	// if category that we just added exists
				return;
			}
		}

		assertTrue(false);
	}
	
	private void addCategory(String category) {
		rt.postForLocation(URL_ADD_CATEGORY, new String[]{category});
	}

	/**
	 * get list of categories and remove the first category. then retrieve a
	 * list again and make sure item does not exists
	 */
	@Test
	public void testRemoveCategory() {
		String[] cagories = rt.getForObject(URL_LIST_CATEGORY, String[].class);
		if (cagories.length == 0) {
			assertTrue(false);
			return;
		}
		String firstCategory = cagories[0];
		// remove first category
		rt.postForLocation(URL_REMOVE_CATEGORY, new String[]{firstCategory});		
		String[] response = rt.getForObject(URL_LIST_CATEGORY, String[].class);

		for (String item : response) {
			if (item.equals(firstCategory)) {
				assertTrue(false);	// if category that we just removed exists
				return;
			}
		}

		assertTrue(true);
	}
	
	/**
	 * this is an extra test which check if API is active or not
	 */
	@Test
	public void isServerAliveTest() {
		Boolean response = rt.getForObject(URL_IS_ACTIVE, Boolean.class);
		assertTrue(response);
	}
	
	/**
	 * list of sample data in question
	 * 
	 * @return List<CategorySubCategory>
	 */
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
	
	/**
	 * list of response which is categories and sub-categories has been removed
	 * from the list
	 * 
	 * @return
	 */
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
	
	/**
	 * expected number of categories for each valid category
	 * 
	 * @return
	 */
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
