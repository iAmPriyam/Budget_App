package gui;

import database.LoginModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public LoginModel loginModel = new LoginModel();
    @FXML
    public Label isConnected;
    @FXML
    private PasswordField txtPassword;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(loginModel.isDbConnected()) {
            isConnected.setText("Connected");
        } else {
            isConnected.setText("Not");
        }
    }

    public void Login() {
        try {
            if(loginModel.isLogin(txtPassword.getText())) {
                isConnected.setText("poprawne");

                Stage primaryStage = new Stage();
                FXMLLoader loader = new FXMLLoader();
                Pane root = loader.load(getClass().getResource("mainScene.fxml").openStream());
                MainSceneController mainSceneController = (MainSceneController)loader.getController();
                mainSceneController.GetUser(txtPassword.getText());
                primaryStage.setTitle("BudgetApp");
                primaryStage.setScene(new Scene(root));
                primaryStage.show();

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
