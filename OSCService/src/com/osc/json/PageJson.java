package com.osc.json;

/**
 * 
 * @author siva kurapati
 *
 */
public class PageJson {
	private Integer pageFrom;
	private Integer pageTo;
	
	private String searchName;
	private String searchOperator;
	private String searchValue;
	
	

	public String getSearchName() {
		return searchName;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}

	public String getSearchOperator() {
		return searchOperator;
	}

	public void setSearchOperator(String searchOperator) {
		this.searchOperator = searchOperator;
	}

	public String getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}

	public Integer getPageFrom() {
		return pageFrom;
	}

	public void setPageFrom(Integer pageFrom) {
		this.pageFrom = pageFrom;
	}

	public Integer getPageTo() {
		return pageTo;
	}

	public void setPageTo(Integer pageTo) {
		this.pageTo = pageTo;
	}

}
