package com.dingkoshop.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

@Service
@Slf4j
public class FileService {

    public String uploadFile(String uploadPath, String originalFileName, byte[] fileData) throws Exception {
        UUID uuid = UUID.randomUUID();
        String uniqueId = uuid.toString().substring(0, 4);

        //확장자 뽑기
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String realFileName = originalFileName.substring(0,originalFileName.lastIndexOf("."));

        //확장자 + 파일 실제 이름 + uuid
        String savedFileName = uniqueId + realFileName + extension;

        String fileUploadFullUrl = uploadPath + "/" + savedFileName;

        FileOutputStream fos = new FileOutputStream(fileUploadFullUrl);
        fos.write(fileData);
        fos.close();

        return savedFileName;
    }

    public void deleteFile(String filePath) throws Exception {
        File deleteFile = new File(filePath);

        if (deleteFile.exists()) {
            log.info("파일을 삭제합니다 file={}", deleteFile);
            deleteFile.delete();
        } else {
            log.info("파일이 존재하지 않습니다.");
        }
    }
}
