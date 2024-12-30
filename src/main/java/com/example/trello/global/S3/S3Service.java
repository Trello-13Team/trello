package com.example.trello.global.S3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.example.trello.global.dto.UploadFileInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequiredArgsConstructor
public class S3Service {
    private final S3Uploader s3Uploader;
    private final AmazonS3Client s3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public UploadFileInfo uploadFile(MultipartFile file){
        return s3Uploader.uploadFile(file);
    }

    public void deleteFile(String fileKey){
        try {
            s3Client.deleteObject(new DeleteObjectRequest(bucket, fileKey));
            System.out.println("File deleted successfully: " + fileKey);
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete file: " + fileKey, e);
        }
    }
}
