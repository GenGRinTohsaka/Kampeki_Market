/*
En esta cñase mas que todo se hacen los constructore y la hrencia multiple
Hacemos el boton para que regrese al menu principal
*/
package org.derianhernandez.controllers;




import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import org.derianhernandez.system.Main;

/* Herencia Multiple concepto, interfaces. POO
 */


    public class ProgramadorController implements Initializable {
   
        private Main  escenarioPrincipal;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }

    public Main getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    @FXML
    private Button btnHome;
    
    @FXML
    private void clicHome(ActionEvent event) throws IOException {
        if(event.getSource() == btnHome){
           escenarioPrincipal.menuPrincipalView();
        
            
        }
    }
}

