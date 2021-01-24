package com.danielfegan.gametracker.s3;

import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;
import com.danielfegan.gametracker.config.properties.S3ConfigurationProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class S3Service {

    private final TransferManager transferManager;
    private final S3ConfigurationProperties s3ConfigurationProperties;

    public void uploadFile(final MultipartFile file) throws IOException, InterruptedException {
        final ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());

        final PutObjectRequest putObjectRequest = new PutObjectRequest(
            s3ConfigurationProperties.getBucketName(),
            s3ConfigurationProperties.getOutputPath() + "/" + file.getOriginalFilename(),
            file.getInputStream(),
            metadata
        ).withCannedAcl(CannedAccessControlList.PublicRead);

        final Upload upload = transferManager.upload(putObjectRequest);
        upload.waitForUploadResult();
    }

}
