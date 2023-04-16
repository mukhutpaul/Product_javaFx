package com.product.product.service;


import com.product.product.AppException;
import com.product.product.model.entity.Account;
import com.product.product.repo.AccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class LoginService {

    @Autowired
    private AccountRepo repo;


    public Account login(String loginId,String password){
        if(StringUtils.isEmpty(loginId)){
            throw new AppException("Please enter Login Id.");

        }

        if(StringUtils.isEmpty(password)){
            throw new AppException("Please enter password.");

        }

      Account account =   repo.findById(loginId)
              .orElseThrow(() ->new  AppException("Please check your Login Id"));
        if(!password.equals(account.getPassword())){
            throw new AppException("Please check your password");
        }

      return account;
    }
}
