package com.product.product.service;

import com.product.product.AppException;
import com.product.product.model.entity.Category;
import com.product.product.model.entity.Produc;
import com.product.product.repo.ProductRepo;
import com.sun.javafx.collections.MappingChange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.io.File;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class ProductService {

    @Autowired
    private ProductRepo repos;

    public List<Produc> search(Category category, String name) {
        StringBuffer sb = new StringBuffer("select p from Produc p where 1 = 1");

        Map<String, Object> params = new HashMap<>();

        if(null != category ){
            sb.append(" and p.category = :category");
            params.put("category", category);

        }

        if(!StringUtils.isEmpty(name)){
            sb.append(" and lower(p.name) like lower(:name)");
            params.put("name", name.concat("%"));
        }

        return repos.findBayQuery(sb.toString(),params);
    }

    public void save(Produc product) {

        if(null == product.getCategory()){
            throw new AppException("Please select category");
        }
        if(StringUtils.isEmpty(product.getName())){
            throw new AppException("Please enter Product Name");
        }

        if(product.getPrice() ==0){
            throw new AppException("Please enter Product Price");
        }
        repos.save(product);
    }

    @Transactional
    public void upload(Category category, File file) {
        try {

            Files.lines(file.toPath())
                    .map(line -> line.split("\t"))
                    .filter(array -> array.length >= 2)
                    .map(array ->{
                       try {
                           Produc product = new Produc();
                           product.setCategory(category);
                           product.setName(array[0]);
                           product.setPrice(Integer.parseInt(array[1]));
                           if(array.length > 2){
                               product.setRemark(array[2]);
                           }
                           return  product;

                       }catch (NumberFormatException e){
                           return  null;
                       }
                    })
                    .filter(product -> null != product)
                    .forEach(produc -> repos.save(produc));

        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
