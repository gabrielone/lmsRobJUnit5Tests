package com.infosys.internal.portal.claims.service;

import java.io.IOException;
import java.net.MalformedURLException;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface ClaimFileStorageService {

	public String storeFile(MultipartFile file) throws IOException;

	// /throws MalformedURLException;
	public Resource loadFileAsResource(String fileName) throws MalformedURLException;

	public String fileStorageBusinessMethod(String filename, MultipartFile[] file) throws IOException;
}
