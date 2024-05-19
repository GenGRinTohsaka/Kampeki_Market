/*
En esta clase ams que todo hacemos la herencia multiple y constructores
Se hacen los botonos o menuItem para que al presionarlos lleven ala vista seleccionada
 */
package org.derianhernandez.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import org.derianhernandez.system.Main;

/* Herencia Multiple concepto, interfaces. POO
 */
public class MenuPrincipalController implements Initializable {

    private Main escenarioPrincipal;

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
    private MenuItem btnClientes;
    @FXML
    private MenuItem btnProgramador;
    @FXML
    private MenuItem btnProveedores;
    @FXML
    private MenuItem btnTipoProducto;
    @FXML
    private MenuItem btnCompras;
    @FXML
    private MenuItem btnCargoEmpleado;
    @FXML
    private MenuItem btnProductos;
    

    @FXML
    private void buttonHandleEvent(ActionEvent event) throws IOException {
        if (event.getSource() == btnClientes) {
            escenarioPrincipal.menuClientesView();

        } else if (event.getSource() == btnProgramador) {
            escenarioPrincipal.programadorView();
        } else if (event.getSource() == btnProveedores) {
            escenarioPrincipal.proveedorView();
        } else if (event.getSource() == btnTipoProducto){
            escenarioPrincipal.tipoProductoView();
        } else if (event.getSource() == btnCompras){
            escenarioPrincipal.comprasView();
        } else if (event.getSource() == btnCargoEmpleado){
            escenarioPrincipal.cargoEmpleadoView();
        } else if (event.getSource() == btnProductos){
            escenarioPrincipal.productosView();
        }

    }

}