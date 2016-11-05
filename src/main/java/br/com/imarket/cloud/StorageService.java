package br.com.imarket.cloud;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.cloud.WriteChannel;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;

@Service
public class StorageService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(StorageService.class);

	private static final int ONE_MB = 1_000_000;
	
	@Autowired
	private Storage storage;

	public void upload(String imagesBucket, String fullBucketPath, MultipartFile multipartFile) throws IOException {
		
		BlobId blobId = BlobId.of(imagesBucket, fullBucketPath + multipartFile.getOriginalFilename());
		BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(multipartFile.getContentType()).build();
		
		if (multipartFile.getSize() > ONE_MB) {
			writeFileInChunks(blobInfo, multipartFile);
		} else {
			byte[] bytes = multipartFile.getBytes();
			
			storage.create(blobInfo, bytes);
		}
	}

	private void writeFileInChunks(BlobInfo blobInfo, MultipartFile multipartFile) throws IOException {
		try (WriteChannel writer = storage.writer(blobInfo)) {
			byte[] buffer = new byte[1024];
			try (InputStream input = multipartFile.getInputStream()) {
				int limit;
				while ((limit = input.read(buffer)) >= 0) {
					try {
						writer.write(ByteBuffer.wrap(buffer, 0, limit));
					} catch (Exception ex) {
						LOGGER.error("Cannot get bytes from large file {}", multipartFile.getOriginalFilename(), ex);
					}
				}
			}
		}
	}

}
