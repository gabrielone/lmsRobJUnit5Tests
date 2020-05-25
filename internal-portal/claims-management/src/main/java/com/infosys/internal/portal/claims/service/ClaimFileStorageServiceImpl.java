package com.infosys.internal.portal.claims.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.infosys.internal.portal.claims.config.FileStorageException;
import com.infosys.internal.portal.claims.config.FileStorageProperties;
import com.infosys.internal.portal.claims.config.MyFileNotFoundException;
import com.infosys.internal.portal.claims.model.ClaimsEntity;
import com.infosys.lms.utils.AppConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ClaimFileStorageServiceImpl implements ClaimFileStorageService {

	private final Path fileStorageLocation;
	@Autowired
	ClaimFileStorageService fileStorageService;

	private static Logger logger = LoggerFactory.getLogger(ClaimFileStorageServiceImpl.class);

	@Autowired
	public ClaimFileStorageServiceImpl(FileStorageProperties fileStorageProperties) {
		this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();

		try {
			Files.createDirectories(this.fileStorageLocation);
		} catch (Exception ex) {
			logger.error("Could not create the directory where the uploaded files will be stored");
		}
	}

	public String storeFile(MultipartFile file) throws IOException {

		// try {
		if (!(file.getOriginalFilename().endsWith(AppConstants.PNG_FILE_FORMAT)
				|| file.getOriginalFilename().endsWith(AppConstants.JPEG_FILE_FORMAT)
				|| file.getOriginalFilename().endsWith(AppConstants.JPG_FILE_FORMAT))) {
			throw new FileStorageException(AppConstants.INVALID_FILE_FORMAT);
		}

		String localDir = System.getProperty("user.dir");
		File f = new File(localDir + "\\config.properties");
		f.createNewFile();
		FileOutputStream fout = new FileOutputStream(f);
		fout.write(file.getBytes());
		fout.close();
		BufferedImage image = ImageIO.read(f);
		int height = image.getHeight();
		int width = image.getWidth();
		if (width > 300 || height > 300) {
			if (f.exists())
				f.delete();
			throw new FileStorageException(AppConstants.INVALID_FILE_DIMENSIONS);
		}
		if (f.exists()) {
			f.delete();
		}
		// } finally {
		// }

		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		String newFileName = System.currentTimeMillis() + AppConstants.FILE_SEPERATOR + fileName;
		Path targetLocation = this.fileStorageLocation.resolve(newFileName);
		Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
		return newFileName;

	}

	@Override
	public Resource loadFileAsResource(String fileName) throws MalformedURLException {
		// try {
		Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
		Resource resource = new UrlResource(filePath.toUri());
		if (resource.exists()) {
			return resource;
		} else {
			logger.error("File not found");
			// throw new MyFileNotFoundException(AppConstants.FILE_NOT_FOUND + fileName);

		}
		// } finally {
		// }
		return resource;

	}

	@Override
	public String fileStorageBusinessMethod(String filename, MultipartFile[] file) throws IOException {
		List<ClaimsEntity> documents = new ArrayList<ClaimsEntity>();

		for (MultipartFile fisier : file) {
			ClaimsEntity document = new ClaimsEntity();
			String path = fileStorageService.storeFile(fisier);

			document.setProfilePicPath(ServletUriComponentsBuilder.fromCurrentContextPath()
					.path(AppConstants.DOWNLOAD_PATH).path(path).toUriString());

			documents.add(document);
		}
		return filename;

	}

}