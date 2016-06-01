package controller.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * Created by luigi on 5/31/16.
 */
public class ServerOverviewController {
    @FXML
    private Label nConnessi;

    @FXML
    private Label port;

    public void addUser()
    {
        String s = Integer.toString((Integer.parseInt(nConnessi.getText()) +1));
        nConnessi.setText(s);
    }

    public void delUser()
    {
        nConnessi.setText(Integer.toString((Integer.parseInt(nConnessi.getText()) -1)));
    }

    public void setPort(int n)
    {
        port.setText(Integer.toString(n));
    }
}
