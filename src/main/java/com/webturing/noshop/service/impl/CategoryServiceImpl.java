package com.webturing.noshop.service.impl;

import com.webturing.noshop.mapper.CategoryMapper;
import com.webturing.noshop.pojo.Category;
import com.webturing.noshop.service.CategoryService;
import com.webturing.noshop.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryMapper categoryMapper;
    @Override
    public List<Category> list(Page page) {
        return categoryMapper.list(page);
    }

    @Override
    public int total() {
        return categoryMapper.total();
    }

    @Override
    public void add(Category category) {
        categoryMapper.add(category);
    }
}
