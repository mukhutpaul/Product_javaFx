package com.product.product.model.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class Account {

    @Id
    @Column(length = 30)
    private String loginId;
    private String name;

    private Role role;
    private String password;


    public enum Role {
        Admin, Sale, Purchase;
    }

}
