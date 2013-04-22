package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class Controller {
    @FXML
    public TextField id;
    public void OnSearchClick(ActionEvent actionEvent) {
        System.out.println("bla");
        id.setText("blabla");
    }
}
