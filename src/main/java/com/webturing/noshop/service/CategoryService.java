package com.webturing.noshop.service;

import com.webturing.noshop.pojo.Category;
import com.webturing.noshop.util.Page;

import java.util.List;

public interface CategoryService {
    int total();
    List<Category> list(Page page);
}
