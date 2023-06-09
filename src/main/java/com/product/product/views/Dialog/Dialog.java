package com.product.product.views.Dialog;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.*;

public class Dialog {

    @FXML
    private Label title;
    @FXML
    private Label message;

    @FXML
    private HBox buttons;

    @FXML
    private void cancel() {

        okBtn.getScene().getWindow().hide();
    }
    @FXML
    private  void ok(){

    }

    @FXML
    private Button okBtn;

    @FXML
    private Button closeBtn;

    private Stage stage;



    public void show(){
        stage.show();

    }

    public static class DialogBuilder {
        private String title;
        private String message;

        private ActionListener okActionListener;

        public DialogBuilder(){
        }

        public Dialog build(){
            try {
                Stage stage = new Stage(StageStyle.UNDECORATED);
                FXMLLoader loader = new FXMLLoader(Dialog.class.getResource("/views/common/Dialog.fxml"));
                Parent view = loader.load();
                stage.setScene(new Scene(view));

                Dialog controller = loader.getController();
                controller.stage = stage;

                controller.title.setText(this.title);
                controller.message.setText(this.message);

                if(null != okActionListener){
                     controller.okBtn.setOnAction(event -> {
                         controller.cancel();
                         okActionListener.doAction();
                     });
                }else {

                    controller.okBtn.setVisible(false);
                    controller.closeBtn.setText("CLOSE");

                }
                return controller;



            }catch (Exception e){
                e.printStackTrace();
                throw new RuntimeException(e);
            }

        }

        public DialogBuilder title(String title){
            this.title = title;
            return this;
        }

        public DialogBuilder message(String message){
            this.message = message;
            return this;
        }

        public DialogBuilder okACtionListener(ActionListener okActionListener){
            this.okActionListener = okActionListener;
            return this;
        }

        public static DialogBuilder builder(){
            return new DialogBuilder();
        }
    }

    public static  interface  ActionListener {
        void doAction();
    }
}
