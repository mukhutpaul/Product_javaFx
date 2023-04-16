package com.product.product.views;

import com.product.product.AppException;
import com.product.product.model.entity.Category;
import com.product.product.model.entity.Produc;
import com.product.product.service.CategoryService;
import com.product.product.service.ProductService;
import com.product.product.views.Dialog.Dialog;
import com.product.product.views.Popups.ProductEdit;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
public class Products extends AbstractController{
    @FXML
    private ComboBox<Category> category;
    @FXML
    private TextField name;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;
    @FXML
    private void initialize(){

        category.getItems().clear();
        category.getItems().addAll(categoryService.findAll());

        MenuItem edit = new MenuItem("Edit Product");

        edit.setOnAction(event ->{
            Produc product = tableView.getSelectionModel().getSelectedItem();
            if(null != product) {
                ProductEdit.edit(product, this::save, categoryService::findAll);
            }

        });

        MenuItem changeState = new MenuItem("Change Status");
        changeState.setOnAction(event ->{
            Produc product = tableView.getSelectionModel().getSelectedItem();

            Dialog.DialogBuilder.builder()
                    .title("Change Status")
                    .message(String.format("Do you want to change status of %s?",product.getName()))
                    .okACtionListener(() ->{
                        product.setValid(!product.isValid());
                        productService.save(product);
                        search();
                    }).build().show();
        });

        tableView.setContextMenu(new ContextMenu(edit, changeState ));

    }

    @FXML
    private TableView<Produc> tableView;

    @FXML
    private void search(){
        tableView.getItems().clear();
        List<Produc> list =productService.search(category.getValue(),name.getText());
        tableView.getItems().addAll(list);
    }

    @FXML
    private void clear(){
        category.setValue(null);
        name.clear();
        tableView.getItems().clear();

    }

    @FXML
    private void upload(){

        try {
            //get Category
            Category category = this.category.getValue();

            if(null == category) {
                throw new AppException("Please select target category for upload");
            }


            //open file chooser
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Upload Product TSV File");
            Path desktop = Paths.get(System.getProperty("user.home")).resolve("Desktop");
            fileChooser.setInitialDirectory(desktop.toFile());

            fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("Tab Separated File","*.xlsx"));


            File file = fileChooser.showOpenDialog(this.category.getScene().getWindow());

            if(null != file){
                // upload file
                productService.upload(category,file);


                // refresh table (search)
                name.clear();
                search();

            }


        }catch (AppException e){
            Dialog.DialogBuilder.builder().title("Warning")
                    .message(e.getMessage())
                    .okACtionListener(() ->name.getScene().getWindow().hide())
                    .build().show();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    private void addNew(){
        ProductEdit.addNew(this::save,categoryService::findAll);

    }


    private void save(Produc product){
        productService.save(product);
        category.setValue(product.getCategory());
        search();

    }






}
