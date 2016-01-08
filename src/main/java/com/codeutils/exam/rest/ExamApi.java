/**
 * 
 */
package com.codeutils.exam.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest API which will process list of category, sub-category pair. The result
 * is a list which contain unique items in valid category and list if categories
 * and number of item in each category. Also, this API allow user to add and
 * remove valid categories and receive list of valid categories
 * 
 * @author Mohammad Ali Sistani, masistani@yahoo.com
 */
@RestController
@RequestMapping(value = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class ExamApi {
	
	private static final boolean IS_API_ACTIVE = true;
	private static List<String> validCategory;		// define valid category
	
	static {
		// set valid category default values
		validCategory = new ArrayList<String>();
		validCategory.add("PERSON");
		validCategory.add("PLACE");
		validCategory.add("ANIMAL");
		validCategory.add("COMPUTER");
		validCategory.add("OTHER");
	}
	
	/**
	 * iterate through the input list which is contain list of category,
	 * sub-category pairs. during each iteration if category is in valid
	 * category list and not duplicate category, then add this category,
	 * sub-category to list and if category exists in list of category count
	 * then add one to counter, otherwise set the number of counter (number of
	 * categories) to one. Finally return an object which contain the no
	 * duplicate and valid category, and map which contain category and number
	 * of items in input which is available in this category
	 * 
	 * @param cat
	 * @param subCat
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/processcountvaliditem", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public ExamResult doProcessExamResponse(@RequestBody List<CategorySubCategory> input) {
		List<CategorySubCategory> resultCategorySub = new ArrayList<CategorySubCategory>();
		Map<String,Integer> resultCategoryCount = new HashMap<String,Integer>();
		
		for (CategorySubCategory item:input) {
			if (validCategory.contains(item.getCategory()) == true && 
					resultCategorySub.contains(item) == false) {
				resultCategorySub.add(item);			// add category which is in valid category
				String category = item.getCategory();
				int count = resultCategoryCount.containsKey(category) ? resultCategoryCount.get(category) : 0;
				resultCategoryCount.put(category, count + 1);	// add to number that item is repeated
			}
		}
		
		return new ExamResult(resultCategorySub, resultCategoryCount);
	}
	
	/**
	 * provide list of valid categories for managing the categories on run-time,
	 * also, help with testing add/remove categories
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/listcategory", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public List<String> returnListOfCategories() {
		return validCategory;
	}
	
	/**
	 * TODO: Add spring security - since this could impact application on
	 * run-time only authorized user should have access
	 * 
	 * add category which is passed 
	 * 
	 * @param category
	 */
	@ResponseBody
	@RequestMapping(value = "/addcategory", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public void addCategory(@RequestBody List<String> category) {
		validCategory.addAll(category);
	}

	/**
	 * TODO: Add spring security - since this could impact application on
	 * run-time only authorized user should have access
	 * 
	 * remove category which is passed 
	 * 
	 * @param category
	 */
	@ResponseBody
	@RequestMapping(value = "/removecategory", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public void removeCategory(@RequestBody List<String> category) {
		validCategory.removeAll(category);
	}
	
	/**
	 * indicator that shows API is still active and not deprecated
	 * 
	 * @return false if API is not active, otherwise, true
	 */
	@RequestMapping(value = "/isactive", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public boolean isApiActive() {
		return IS_API_ACTIVE;
	}

}
