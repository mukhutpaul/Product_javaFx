package com.product.product.utils;

public enum Menu {
    Home("POS Dashboard"),
    POS("POS"),
    Sales("Sales History"),
    Category("Category Management"),
    Product("Product Management");

    private String title;

    Menu(String title) {

        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String getFxml(){
        return String.format("/views/%s.fxml",name());
    }
}
