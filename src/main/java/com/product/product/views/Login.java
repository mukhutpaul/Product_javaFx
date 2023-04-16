package com.product.product.views;

import com.product.product.AppException;
import com.product.product.Product;
import com.product.product.model.entity.Account;
import com.product.product.service.LoginService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;
@Controller
public class Login {
    private static Account loginUser;
    @Autowired
    private LoginService loginService;

    @FXML
    private Button closeBtn;

    @FXML
    private TextField idlogin;

    @FXML
    private Button login;

    @FXML
    private Label message;

    @FXML
    private PasswordField password;

    private void attachEvent(){
          idlogin.getScene().setOnKeyPressed(event ->{

              if(event.getCode() == KeyCode.ENTER){

                  if(closeBtn.isFocused()){
                      close();
                  }

                  if(login.isFocused()){
                      login();
                  }
              }
          });
    }

    @FXML
    private void close() {
        login.getScene().getWindow().hide();

    }

    @FXML
    private void login() {
        try {
            loginUser = loginService.login(idlogin.getText(),password.getText());
            //Open
            MainFrame.show();
            //
            close();
        }catch (AppException e){
            message.setText(e.getMessage());
        }catch (Exception e){
           e.printStackTrace();
           close();
        }

    }

    public static void loadView(Stage stage) {
        try {
            stage = new Stage(StageStyle.UNDECORATED);
            FXMLLoader loader = new FXMLLoader(Login.class.getResource("/views/Login.fxml"));
            loader.setControllerFactory(Product.getApplicationContext()::getBean);

            Parent view = loader.load();
            stage.setScene(new Scene(view));

            Login controller = loader.getController();
            controller.attachEvent();


            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
