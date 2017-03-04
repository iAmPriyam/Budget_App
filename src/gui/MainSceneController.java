package gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import users.User;

import java.net.URL;
import java.util.ResourceBundle;

public class MainSceneController implements Initializable{
    @FXML
    private Label userLbl;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //do sth here
    }
    public void GetUser(User user) {
        if(user!=null) {
            userLbl.setText(user.getName());
        } else {
            userLbl.setText("zjebawszy cos");
        }
    }

}
