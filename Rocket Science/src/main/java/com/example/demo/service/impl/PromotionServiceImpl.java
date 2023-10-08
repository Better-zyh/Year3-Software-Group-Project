package com.example.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.mapper.PromotionMapper;
import com.example.demo.model.entity.Promotion;
import com.example.demo.service.PromotionService;
import org.springframework.stereotype.Service;

@Service
public class PromotionServiceImpl extends ServiceImpl<PromotionMapper,Promotion> implements PromotionService {
}
