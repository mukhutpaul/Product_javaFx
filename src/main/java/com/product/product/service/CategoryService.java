package com.product.product.service;

import com.product.product.AppException;
import com.product.product.model.entity.Category;
import com.product.product.repo.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepo repo;

    public List<Category> findAll(){
        return repo.findAll();
    }


    public  void save(Category category) {

        if(StringUtils.isEmpty(category.getName())){
            throw new AppException("Please enter category name");
        }

        repo.save(category);
    }
}
