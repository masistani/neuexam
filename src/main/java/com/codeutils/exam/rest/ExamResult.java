package com.codeutils.exam.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExamResult {
	List<CategorySubCategory> resultCategorySub = new ArrayList<CategorySubCategory>();
	Map<String,Integer> resultCategoryCount = new HashMap<String,Integer>();
	
	public ExamResult() {
		// Resolve Jackson Library issue during test
	}
	
	public ExamResult(List<CategorySubCategory> resultCategorySub, Map<String, Integer> resultCategoryCount) {
		this.resultCategorySub = resultCategorySub;
		this.resultCategoryCount = resultCategoryCount;
	}
	public List<CategorySubCategory> getResultCategorySub() {
		return resultCategorySub;
	}
	public void setResultCategorySub(List<CategorySubCategory> resultCategorySub) {
		this.resultCategorySub = resultCategorySub;
	}
	public Map<String, Integer> getResultCategoryCount() {
		return resultCategoryCount;
	}
	public void setResultCategoryCount(Map<String, Integer> resultCategoryCount) {
		this.resultCategoryCount = resultCategoryCount;
	}
}
