package com.codeutils.exam.rest;

/**
 * This class contains Category, Sub-Category
 * 
 * @author masistani
 *
 */
public class CategorySubCategory {
	private String category;
	private String subCategory;
	
	public CategorySubCategory() {
		// Resolve Jackson library in Test
	}

	public CategorySubCategory(String category, String subCategory) {
		this.category = category;
		this.subCategory = subCategory;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		CategorySubCategory other = (CategorySubCategory) obj;
		
		// input is considered case sensitive
		if (this.category.equals(other.category) && 
				this.subCategory.equals(other.subCategory)) {
			return true;
		}
		
		return false;
	}

}
