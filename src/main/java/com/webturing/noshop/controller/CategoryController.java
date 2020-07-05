package com.webturing.noshop.controller;

import com.webturing.noshop.pojo.Category;
import com.webturing.noshop.service.CategoryService;
import com.webturing.noshop.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @RequestMapping("admin_category_list")
    public String list(Model model, Page page){

        List<Category> cs = categoryService.list(page);
        int total = categoryService.total();
        page.setTotal(total);
        model.addAttribute("cs", cs);//往前台传cs数据
        model.addAttribute("page", page);//传page数据

        return "admin/listCategory";
    }
}