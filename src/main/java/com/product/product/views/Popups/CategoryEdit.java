package com.product.product.views.Popups;

import com.product.product.AppException;
import com.product.product.model.entity.Category;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.function.Consumer;

public class CategoryEdit {

    @FXML
    private Label title;
    @FXML
    private TextField name;

    @FXML
    private Label message;

    private Consumer<Category> saveHandler;
    private Category category;

    public static void showViews(Consumer<Category> saveHandler) {
        showView(null,saveHandler);
    }
    @FXML
    private void addNew(){

    }



    public static void showView(Category category,Consumer<Category> saveHandler){
        try {
            Stage stage = new Stage(StageStyle.UNDECORATED);
            stage.initModality(Modality.APPLICATION_MODAL);


            FXMLLoader loader = new FXMLLoader(CategoryEdit.class.getResource("/views/Popups/CategoryEdit.fxml"));
            stage.setScene(new Scene(loader.load()));

            CategoryEdit edit = loader.getController();
            edit.init(category, saveHandler);
            

            stage.show();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void init(Category category, Consumer<Category> saveHandler) {
        this.saveHandler = saveHandler;

        if(null == category) {
            this.category = new Category();
            this.title.setText("Add New Category");
        }else {
            this.category = category;
            this.title.setText("Edit Category");
            this.name.setText(category.getName());
        }


    }

    @FXML
    private void close(){
        name.getScene().getWindow().hide();

    }
    @FXML
    private void save() {

        try {
            category.setName(name.getText());
            saveHandler.accept(category);
            close();
        }catch (AppException e){
            message.setText(e.getMessage());

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
