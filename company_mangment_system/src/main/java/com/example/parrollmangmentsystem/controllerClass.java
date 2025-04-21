package com.example.parrollmangmentsystem;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.Node;
import java.io.IOException;


public class controllerClass {
    @FXML
    private TextField username;

    @FXML
    private TextField password;

    @FXML
    private void handleLogin(ActionEvent event) throws IOException {
        String Admin=username.getText();
        String pass=password.getText();
        if(("Admin".equals(Admin) && "1442009".equals(pass)) ||
                ("Admin1".equals(Admin) && "12345678".equals(pass))){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("login");
            alert.setHeaderText(null);
            alert.setContentText("Successful login");
            Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
            alertStage.getIcons().add(new Image("file:src\\images\\icon.jpeg"));
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add(getClass().getResource("design.css").toExternalForm());
            dialogPane.getStyleClass().add("dialog-pane");
            alert.showAndWait();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("welcome_screen.fxml"));
            Parent root = loader.load();
            Scene newScene = new Scene(root);
            Stage secondStage = new Stage();
            secondStage.setTitle("payroll management system");
            Image image=new Image("file:src\\images\\icon.jpeg");
            secondStage.getIcons().add(image);
            secondStage.setScene(newScene);
            secondStage.setResizable(true);
            secondStage.show();
            Stage loginStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            loginStage.close();
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Check from you username and password");
            Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
            alertStage.getIcons().add(new Image("file:src\\images\\icon.jpeg"));
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add(getClass().getResource("design.css").toExternalForm());
            dialogPane.getStyleClass().add("dialog-pane");
            alert.showAndWait();
            username.clear();
            password.clear();
        }

    }

}
