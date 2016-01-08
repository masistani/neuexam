/**
 * 
 */
package com.codeutils.exam.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest Api 
 * 
 * @author Mohammad Ali Sistani, masistani@yahoo.com
 *
 */
@RestController
@RequestMapping(value = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class ExamApi {
	
	private static final boolean IS_API_ACTIVE = true;
	// define valid category
	private static List<String> validCategory;
	
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
	 * 
	 * @param cat
	 * @param subCat
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/processcountvaliditem", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public ExamResult doProcessExamResponse(@RequestBody List<CategorySubCategory> input) {
		System.out.println("Start");
		Set<CategorySubCategory> inputSet = removeDuplicate(input);
		List<CategorySubCategory> resultCategorySub = new ArrayList<CategorySubCategory>();
		Map<String,Integer> resultCategoryCount = new HashMap<String,Integer>();
		
		for (CategorySubCategory item:inputSet) {
			if (validCategory.contains(item.getCategory()) == true) {
				resultCategorySub.add(item);			// add category which is in valid category
				String category = item.getCategory();
				int count = resultCategoryCount.containsKey(category) ? resultCategoryCount.get(category) : 0;
				resultCategoryCount.put(category, count + 1);	// add to number that item is repeated
			}
		}
		
		return new ExamResult(resultCategorySub, resultCategoryCount);
	}
	
	@ResponseBody
	@RequestMapping(value = "/tst", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public List<CategorySubCategory> tmp(@RequestBody List<CategorySubCategory> input) {
		return input;
	}
	
	@ResponseStatus(value = HttpStatus.OK)
	@RequestMapping(value = "/isactive", method = RequestMethod.GET)
	public boolean isApiActive() {
		return IS_API_ACTIVE;
	}
	
	/**
	 * remove duplicate from input
	 * 
	 * @param input
	 * @return
	 */
	private Set<CategorySubCategory> removeDuplicate(List<CategorySubCategory> input) {
		return new HashSet<CategorySubCategory>(input);
	}

}
