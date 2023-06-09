package com.product.product;

import com.product.product.model.entity.Account;
import com.product.product.model.entity.Category;
import com.product.product.repo.AccountRepo;
import com.product.product.repo.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {
    @Autowired
    private CategoryRepo repo;
    @Autowired
    private AccountRepo accountRepo;


    @Bean
    public CommandLineRunner getCommandLineRunner(){
        return args -> {
           // repo.save(new Category("Foods"));
            //repo.save(new Category("Drinks"));
           // repo.save(new Category("Accessories"));

            Account admin = new Account();
            admin.setLoginId("admin");
            admin.setName("Admin User");
            admin.setRole(Account.Role.Admin);
            admin.setPassword("admin");
            accountRepo.save(admin);


        };
    }
}
