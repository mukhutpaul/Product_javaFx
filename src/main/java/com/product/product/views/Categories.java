package com.product.product.views;

import com.product.product.model.entity.Category;
import com.product.product.service.CategoryService;
import com.product.product.views.Popups.CategoryEdit;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.shape.SVGPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import  com.product.product.views.Popups.CategoryEdit;

import java.util.function.Consumer;

@Controller
public class Categories extends AbstractController {

    @FXML
    private TilePane container;

    @Autowired
    private CategoryService service;

    @FXML
    private void initialize(){
        reload();
    }
    @FXML
    private void addNew(){
        CategoryEdit.showViews(this::save);

    }

    private void save(Category category){
        service.save(category);
        reload();
    }

    private void reload(){
        container.getChildren().clear();
        service.findAll().stream().forEach(category -> container.getChildren().add(new CategoryItem(category,this::save  )));
    }
    public  class CategoryItem extends HBox {


        public CategoryItem(Category category, Consumer<Category> consumer) {

            SVGPath icon = new SVGPath();
            icon.setContent("M38.5 0h-12c-0.825 0-1.977 0.477-2.561 1.061l-14.879 14.879c-0.583 0.583-0.583 1.538 0 2.121l12.879 12.879c0.583 0.583 1.538 0.583 2.121 0l14.879-14.879c0.583-0.583 1.061-1.736 1.061-2.561v-12c0-0.825-0.675-1.5-1.5-1.5zM31 12c-1.657 0-3-1.343-3-3s1.343-3 3-3 3 1.343 3 3-1.343 3-3 3z");
            Label name = new Label();
            name.setText(category.getName());

            getChildren().addAll(icon,name);

            getStyleClass().add("category-item");

            setOnMouseClicked(event -> {

                if(event.getClickCount() == 2){
                     CategoryEdit.showView(category,consumer);
                }
            });
        }

    }

}
