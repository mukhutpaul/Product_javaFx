package com.product.product.views;

import com.product.product.Product;
import com.product.product.utils.Menu;
import com.product.product.views.Dialog.Dialog;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainFrame {

    @FXML
    private VBox sideBar;
    @FXML
    private StackPane contentView;

    @FXML
    private void clickMenu(MouseEvent event){

        try{
            Node node = ((Node) event.getSource());
            System.out.println(node.getId());

            if(node.getId().equals("Exit")){
                Dialog.DialogBuilder.builder()
                        .title("Confirm")
                        .message("Do you want to exit POS?")
                        .okACtionListener(() ->sideBar.getScene().getWindow().hide())
                        .build().show();
            }else {
                Menu menu = Menu.valueOf(node.getId());

                for(Node nod: sideBar.getChildren()){
                    nod.getStyleClass().remove("active");
                    if (nod.getId().equals(menu.name())) {
                        nod.getStyleClass().add("active");
                    }

                }




                contentView.getChildren().clear();

                FXMLLoader loader = new FXMLLoader(getClass().getResource(menu.getFxml()));
                System.out.println(getClass().getResource(menu.getFxml()));
                loader.setControllerFactory(Product.getApplicationContext()::getBean);

                Parent view = loader.load();

                AbstractController controller = loader.getController();
                controller.setTitle(menu);



                contentView.getChildren().add(view);
            }

        }catch (Exception e){
           e.printStackTrace();
        }

    }

    public static void show() {
   try {
       Stage stage = new Stage();
       Parent root = FXMLLoader.load(MainFrame.class.getResource("/views/MainFrame.fxml"));
       stage.setScene(new Scene(root));
       stage.setFullScreen(true);

       stage.show();

   }catch (Exception e){
       e.printStackTrace();
   }


    }
}
