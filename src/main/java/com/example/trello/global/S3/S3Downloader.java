package com.example.trello.global.S3;

import com.amazonaws.services.s3.AmazonS3;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RequiredArgsConstructor
public class S3Downloader {
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.region.static}")
    private String region;

    private final AmazonS3 s3Client;

//    public  getFile(String fileName) throws IOException {
//        // fileName = dbac534f-f3b6-4b33-9b83-e308e3c2c29d_e52319408af1ee349da788ec09ca6d92ff7bd70a3b99fa287c599037efee.jpg
//        // https://mall-s3.s3.ap-northeast-2.amazonaws.com/dbac534f-f3b6-4b33-9b83-e308e3c2c29d_e52319408af1ee349da788ec09ca6d92ff7bd70a3b99fa287c599037efee.jpg
//        // 로 전환!
//        String urlStr = "https://" + bucketName + ".s3." + region + ".amazonaws.com/" + fileName;
//        Resource resource;
//        HttpHeaders headers = new HttpHeaders();
//        try {
//            URL url = new URL(urlStr);
//            URLConnection urlConnection = url.openConnection();
//            InputStream inputStream = urlConnection.getInputStream();
//            resource = new InputStreamResource(inputStream);
//
//            // MIME 타입 설정
//            String mimeType = urlConnection.getContentType();
//            if (mimeType == null) {
//                Path path = Paths.get(fileName);
//                mimeType = Files.probeContentType(path);
//            }
//            headers.add("Content-Type", mimeType);
//        } catch (IOException e) {
//            return ResponseEntity.internalServerError().build();
//        }
//        return ResponseEntity.ok().headers(headers).body(resource);
//
//    }

}
