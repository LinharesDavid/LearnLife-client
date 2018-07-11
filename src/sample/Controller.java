package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


public class Controller {
    public TextField txfName;
    public PasswordField pwfPassword;
    public Button btnValidate;

    @FXML
    private void onBtnValidateClick(ActionEvent event){
        System.out.println("BONJOUR");
    }
}
