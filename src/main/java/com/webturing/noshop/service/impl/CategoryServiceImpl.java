package com.webturing.noshop.service.impl;

import com.webturing.noshop.mapper.CategoryMapper;
import com.webturing.noshop.pojo.Category;
import com.webturing.noshop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryMapper categoryMapper;
    public List<Category> list() {
        return categoryMapper.list();
    }
}
