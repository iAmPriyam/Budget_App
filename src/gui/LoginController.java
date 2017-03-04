package gui;

import database.SqliteDb;
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
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import users.User;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public SqliteDb db = new SqliteDb();
    @FXML
    public Label isConnected;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private TextField txtName;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(db.isDbConnected()) {
            isConnected.setText("Connected");
        } else {
            isConnected.setText("Not");
        }
    }

    public void Login(ActionEvent event) {
        try {
            if(db.isPasswordCorrect(txtPassword.getText())) {
                isConnected.setText("poprawne");

                FXMLLoader loader = new FXMLLoader();

                Parent parent = FXMLLoader.load(getClass().getResource("mainScene.fxml"));
                Scene scene = new Scene(parent);
                Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                MainSceneController mainSceneController = (MainSceneController)loader.getController();
                mainSceneController.GetUser(db.getUserData(txtName.getText()));
                appStage.setScene(scene);
                appStage.show();


                /*
                Stage primaryStage = new Stage();
                FXMLLoader loader = new FXMLLoader();
                Pane root = loader.load(getClass().getResource("mainScene.fxml").openStream());
                MainSceneController mainSceneController = (MainSceneController)loader.getController();
                mainSceneController.GetUser(txtPassword.getText());
                primaryStage.setTitle("BudgetApp");
                primaryStage.setScene(new Scene(root));
                primaryStage.show();
                */


            } else {
                isConnected.setText("niepoprawne");
            }
        } catch(SQLException e) {
            System.out.println(e);
            isConnected.setText("wyjatek");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
