package com.webturing.noshop.controller;

import com.webturing.noshop.pojo.Category;
import com.webturing.noshop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

import java.util.List;

@Controller
//访问无需额外地址
@RequestMapping("")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    //映射其路径访问
    @RequestMapping("admin_category_list")
    public String list(Model model) {
        List<Category> cs = categoryService.list();
        model.addAllAttributes("cs", cs);
        return  "admin/listCategory";
    }
}
