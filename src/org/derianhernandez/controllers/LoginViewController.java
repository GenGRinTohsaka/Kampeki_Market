package org.derianhernandez.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import org.derianhernandez.system.Main;

public class LoginViewController implements Initializable {

    private Main escenarioPrincipal;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    public Main getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
         
}
