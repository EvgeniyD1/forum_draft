package com.forum.forum_draft.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class DownloadService {

    @Value("${upload.path}")
    private String uploadPath;

    public String getNewFileName(MultipartFile file) throws IOException {
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
        String uuid = UUID.randomUUID().toString();
        String newFileName = uuid + "." + file.getOriginalFilename();
        file.transferTo(new File(uploadPath.concat("/") + newFileName));
        return newFileName;
    }
}
