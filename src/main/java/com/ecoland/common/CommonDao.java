package com.ecoland.common;

import org.springframework.stereotype.Repository;

/**
 * @class CompanyGroupDao
 * 
 * Dao of company group function
 * 
 * @author ITSG - HoanNNC
 */
@Repository
public class CommonDao {
	
	public String genderQueryPaging(int pageSize, int pageNo) {
		
		int offset = (pageNo-1) * pageSize;
		
		return " \nLIMIT '" + pageSize + "' OFFSET '" + offset + "'";
	}

}
