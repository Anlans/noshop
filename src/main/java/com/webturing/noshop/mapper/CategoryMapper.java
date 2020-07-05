package com.webturing.noshop.mapper;

import com.webturing.noshop.pojo.Category;
import com.webturing.noshop.util.Page;
import org.apache.ibatis.annotations.Select;

import java.util.List;
public interface CategoryMapper {
    public List<Category> list(Page page);

    public int total();

    void add(Category category);

    void delete(int id);
}
