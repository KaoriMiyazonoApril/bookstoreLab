package com.example.tomatomall.controller;

import com.example.tomatomall.repository.AdRepository;
import com.example.tomatomall.service.AdService;
import com.example.tomatomall.vo.AdVO;
import com.example.tomatomall.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/advertisements")
public class AdController {
    @Autowired
    AdService adService;

    @GetMapping("")
    public Response getAllAds(){
        return Response.buildSuccess(adService.getAllAds());
    }

    @PutMapping("")
    public Response updateAd(@RequestBody AdVO ad){
        return Response.buildSuccess(adService.updateAd(ad));
    }
}
