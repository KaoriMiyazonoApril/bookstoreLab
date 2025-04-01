package com.example.tomatomall.controller;
import com.example.tomatomall.exception.TomatoMallException;
import com.example.tomatomall.service.AccountService;
import com.example.tomatomall.serviceImpl.OssService;
import com.example.tomatomall.vo.AccountVO;
import com.example.tomatomall.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api")

public class ImageController {
    @PostMapping("/image")
    public Response uploadPicture(@RequestParam("pic") MultipartFile pic){
        //创建商店到数据库中
        OssService o=new OssService();
        try {return Response.buildSuccess(o.upload(pic,""));}
        catch (TomatoMallException | IOException e){
            throw TomatoMallException.ossError();
        }
    }
}
