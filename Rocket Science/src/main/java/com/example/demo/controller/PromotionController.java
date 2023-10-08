package com.example.demo.controller;


import com.example.demo.common.api.ApiResult;
import com.example.demo.model.entity.Promotion;
import com.example.demo.service.PromotionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/promotion")
public class PromotionController {
    @Resource
    private PromotionService promotionService;

    @GetMapping("/all")
    public ApiResult<List<Promotion>> list() {
        List<Promotion> list = promotionService.list();
        return ApiResult.success(list);
    }
}
