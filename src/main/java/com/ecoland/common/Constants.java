package com.ecoland.common;

/**
 * 
 * @author thaotv@its-global.vn
 * 
 */
public class Constants {
    public static final String DELETE_FLAG = "delete_flag=0";
    public static final String SEARCH_LIKE = "%";
    public static final String SECRET = "secret";
    public static final String SUCCESS = "Success get infomation record";
    public static final String ERROR = "Error get information record";
    public static final String DELETE_SUCCESS = "Delete record success";
    public static final String DELETE_FAIL = "Delete record fail";
    public static final String CREATE_SUCCESS = "Create or update record success";
    public static final String RECORD_DOES_NOT_EXIST = "The record does not exist";
    public static final String FIELD_INVALID = "Some field invalid";
    public static final String RECORD_ALREADY_EXISTS = "The record already exists";
    public static final String RECORD_NOT_FOUND = "Record Not Found";
    public static final String USER_GROUP_ASSIGN_USER_ACCOUNTS = "User groups being assigned to user accounts";
    public static final String RECORD_REFER = "Record is refer of another table - foreign key";

    public static final Integer PAGE_SIZE = 50;
    public static final Integer MAX_NAVIGATION_RESULT = 20;

    public static final String DELETE = "DELETE";
    public static final String CREATE = "CREATE";
    public static final String UPDATE = "UPDATE";

    // http code
    public static final String HTTP_CODE_200 = "200";
    public static final String HTTP_CODE_400 = "400";
    public static final String HTTP_CODE_401 = "401";
    public static final String HTTP_CODE_403 = "403";
    public static final String HTTP_CODE_404 = "404";
    public static final String HTTP_CODE_406 = "406";
    public static final String HTTP_CODE_500 = "500";

    //
    public static final String LOGG_IN_SUCCESS = "User id login success";
    public static final String LOGG_IN_ERROR = "User id login error";
    public static final String LOGGED_OUT_SUCCESS = "User has successfully logged out from the system!";
    public static final String LOGGED_OUT_ERROR = "Logged out the system error";

    public static final String VALIDATE_THE_FIELD = "The field can't empty";
    public static final String VALIDATE_MAX_SIZE_5 = "No more than 5 characters";
    public static final String VALIDATE_MAX_SIZE_7 = "No more than 7 characters";
    public static final String VALIDATE_MAX_SIZE_13 = "No more than 13 characters";
    public static final String VALIDATE_MAX_SIZE_20 = "No more than 20 characters";
    public static final String VALIDATE_MAX_SIZE_30 = "No more than 30 characters";
    public static final String VALIDATE_MAX_SIZE_60 = "No more than 60 characters";

    // Flag
    public static final Integer DELETE_TRUE = 1;
    public static final Integer DELETE_NONE = 0;

    // table
    public static final String USER_ACCOUNTS = "user_accounts";
    public static final String USER_GROUPS = "user_groups";
    public static final String BRANCHES = "branches";
    public static final String WEB_LARGE_CATEGORIES= "web_large_categories";
    public static final String PRODUCT_CATEGORIES="product_categories";
    public static final String WEB_SMALL_CATEGORIES= "web_small_categories";

}
