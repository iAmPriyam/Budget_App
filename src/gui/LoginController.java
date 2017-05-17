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
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


//TODO app's main window should open in new centered window
public class LoginController implements Initializable {
    public SqliteDb db = new SqliteDb();
    @FXML
    public Label error;
    @FXML
    private PasswordField txtPassword;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(db.isDbConnected()) {
            error.setText("Hello!");
        } else {

        }
    }

    public void Login(ActionEvent event) {
        try {
            if(db.validatePassword(txtPassword.getText())) {
                Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                FXMLLoader loader = new FXMLLoader();
                Parent parent = loader.load(getClass().getResource("mainScene.fxml").openStream());
                MainSceneController mainSceneController = (MainSceneController)loader.getController();
                db.initUserData(txtPassword.getText());
                mainSceneController.fillMainScene();
                appStage.setTitle("BudgetApp");
                appStage.setScene(new Scene(parent));
                appStage.setResizable(false);
                appStage.show();
                db.closeConnection();
            } else {
                error.setText("Wrong password");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
