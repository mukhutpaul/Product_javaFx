package com.product.product.model.entity;

import com.product.product.utils.FormaUtils;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Produc{


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    private Category category;
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int price;

    private boolean valid = true;
    private String remark;

    public Produc() {
    }

    public String getPriceStr(){
        return FormaUtils.formatNumber(price);
    }

    public String getValidStr(){
        return valid ? "Yes" : "No";
    }
}
