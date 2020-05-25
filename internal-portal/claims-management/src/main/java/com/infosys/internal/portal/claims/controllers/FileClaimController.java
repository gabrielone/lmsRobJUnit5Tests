package com.infosys.internal.portal.claims.controllers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.infosys.internal.portal.claims.config.FileStorageException;
import com.infosys.internal.portal.claims.model.AppResponse;
import com.infosys.internal.portal.claims.model.ClaimsEntity;
import com.infosys.internal.portal.claims.service.ClaimService;
import com.infosys.internal.portal.claims.service.ClaimFileStorageService;
import com.infosys.internal.portal.claims.service.ClaimFileStorageServiceImpl;
import com.infosys.lms.utils.AppConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class FileClaimController {

	@Autowired
	private ClaimService applicationService;
	private ObjectMapper objectMapper = new ObjectMapper();

	@Autowired
	private ClaimFileStorageService fileStorageService;

	private static Logger logger = LoggerFactory.getLogger(FileClaimController.class);

	@RequestMapping(value = AppConstants.EMPLOYEE_URI, method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public AppResponse createEmployee(
			@RequestParam(value = AppConstants.EMPLOYEE_JSON_PARAM, required = true) String empJson,
			@RequestParam(required = true, value = AppConstants.EMPLOYEE_FILE_PARAM) MultipartFile[] file)
			throws JsonParseException, JsonMappingException, IOException {
		String fileName = fileStorageService.storeFile(file[0]);
		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path(AppConstants.DOWNLOAD_PATH)
				.path(fileName).toUriString();
		ObjectMapper objectMapper = new ObjectMapper();
		ClaimsEntity employee = objectMapper.readValue(empJson, ClaimsEntity.class);
		employee.setProfilePicPath(fileDownloadUri);
		applicationService.createOrUpdateClaims(employee);
		fileStorageService.fileStorageBusinessMethod("filename", file);

		return new AppResponse(AppConstants.SUCCESS_CODE, AppConstants.SUCCESS_MSG);
	}

	@RequestMapping(value = AppConstants.EMPLOYEE_URI, method = RequestMethod.GET)
	public List<ClaimsEntity> getAllEmployees() {
		return applicationService.getAllClaims();
	}

	@RequestMapping(value = AppConstants.DOWNLOAD_URI, method = RequestMethod.GET)
	public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request)
			throws MalformedURLException {
		Resource resource = fileStorageService.loadFileAsResource(fileName);
		String contentType = null;
		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		} catch (IOException ex) {
			logger.error("Error occured file not found");
		}
		if (contentType == null) {
			contentType = AppConstants.DEFAULT_CONTENT_TYPE;
		}
		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION,
						String.format(AppConstants.FILE_DOWNLOAD_HTTP_HEADER, resource.getFilename()))
				.body(resource);
	}

}