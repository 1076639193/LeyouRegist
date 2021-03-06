package com.leyou.upload.controller;

import com.leyou.upload.service.UploadSerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("upload")
public class UploadController {
    @Autowired
    private UploadSerivce uploadSerivce;

    @PostMapping("image")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile  file){
        String url=uploadSerivce.uploadImage(file);
        if(null!=url){
            return ResponseEntity.ok(url);

        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }
}
