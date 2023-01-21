/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import databases.ConnectionSingleton;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author RiPou
 */
public class LoginController implements Initializable {
    
    private Label label;
    @FXML
    private TextField tf_username;
    @FXML
    private PasswordField tf_password;
    
    double width, height;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Etablir la connexion avec la base de donnes sqlite
        ConnectionSingleton.getConnection();
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        width = screenSize.getWidth();
        height = screenSize.getHeight();
    }    

    @FXML
    private void login(ActionEvent event) throws IOException {
        if(tf_username.getText().toLowerCase().equals("admin") && tf_password.getText().toLowerCase().equals("admin")){
            //HIDE THE CURRENT WINDOW
            ((Node)event.getSource()).getScene().getWindow().hide();
            
            //CREATE AND OPEN NOW WINDOW
            Parent root = FXMLLoader.load(getClass().getResource("/fxmls/DashboardUI.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Dashboard");
            stage.setWidth(width - 100);
            stage.setHeight(height - 100);
            stage.setMaximized(true);
            stage.setScene(scene);
            stage.initStyle(StageStyle.DECORATED);
            stage.show();
        }else
            utils.Utilities.showAlert("WARNING", "LE MOT DE PASSE OU LE NOM UTILISATEUR EST INCORRECT");
    }

    @FXML
    private void exit(ActionEvent event) {
        System.out.println("exit application");
        ConnectionSingleton.closeConnection();
        Platform.exit();
    }
    
}
