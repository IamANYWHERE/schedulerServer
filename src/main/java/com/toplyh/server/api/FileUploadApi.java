package com.toplyh.server.api;

import com.toplyh.server.service.file.StorageFileNotFoundException;
import com.toplyh.server.service.file.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by 我 on 2018/1/10.
 * 图片文件上传下载
 * 因为时间原因只实现了上传，没有下来
 * 服务端图片大小问题还没有解决
 * 所以先暂停使用
 */
@Controller
@RequestMapping("/api")
public class FileUploadApi {

    private final StorageService storageService;

    @Autowired
    public FileUploadApi(StorageService storageService){
        this.storageService=storageService;
    }

    /*@GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file) {

        storageService.store(file);
        *//*redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");
*//*
        return "redirect:/";
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }*/
}
