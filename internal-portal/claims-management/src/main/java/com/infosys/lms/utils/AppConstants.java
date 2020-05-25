package com.infosys.lms.utils;

public class AppConstants {

	public static final String EMPLOYEE_URI = "/employees";
	public static final String EMPLOYEE_JSON_PARAM = "empJson";
	public static final String EMPLOYEE_FILE_PARAM = "file";
	public static final String SUCCESS_CODE = "EMP-200";
	public static final String SUCCESS_MSG = "Employee created successfully";
	public static final String FILE_SEPERATOR = "_";
	public static final String DOWNLOAD_PATH = "/downloadFile/";
	public static final String DOWNLOAD_URI = "/downloadFile/{fileName:.+}";	
	public static final String DEFAULT_CONTENT_TYPE = "application/octet-stream";
	public static final String FILE_DOWNLOAD_HTTP_HEADER = "attachment; filename=\"%s\"";
	public static final String FILE_PROPERTIES_PREFIX = "file";
	public static final String FILE_STORAGE_EXCEPTION_PATH_NOT_FOUND = "Could not create the directory where the uploaded files will be stored";
	public static final String FILE_EXCEPTION_NOT_FOUND = "File Not Found";
	public static final String INVALID_FILE_PATH_NAME = "Sorry! Filename contains invalid path sequence";
	public static final String FILE_NOT_FOUND = "File not found ";
	public static final String FILE_STORAGE_EXCEPTION = "Could not store file %s !! Please try again!";
	public static final CharSequence INVALID_FILE_DELIMITER = "..";
	public static final String INDEX_PAGE_URI = "/index";
	public static final String INDEX_PAGE = "index";
	public static final String TEMP_DIR = "C://TMP//";
	public static final String INVALID_FILE_DIMENSIONS = "Invalid file dimensions. File dimension should note be more than 300 X 300";
	public static final String INVALID_FILE_FORMAT = "Only PNG, JPEG and JPG images are allowed";
	public static final String PNG_FILE_FORMAT = ".png";
	public static final String JPEG_FILE_FORMAT = ".jpeg";
	public static final String JPG_FILE_FORMAT = ".jpg";	
	public static final String LIST_CLAIMS = "/list-claims-link";
	public static final String LIST_HR_CLAIMS = "/hr_page-link";
	public static final String LIST_MANAGER_CLAIMS = "/manager_page-link";
	public static final String EDIT_USER_PAGE = "/edit";
	public static final String EDIT_BY_ID = "/edit/{id}";
	public static final String DELETE_USER_CLAIM = "/delete/{id}";
	public static final String EDIT_HR_PAGE = "/edit_hr_page/{id}";
	public static final String DELETE_CLIM_HR = "/deletedall/{id}";	
	public static final String EDIT_CLAIM_MANAGER = "/edit_claim_status_manager/{id}";
	public static final String DELETE_CLAIM_MANAGER = "/deleted/{id}";
	public static final String CREATE_USER_CLAIM = "/createClaim";

}