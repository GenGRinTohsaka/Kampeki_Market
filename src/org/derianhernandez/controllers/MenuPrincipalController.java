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

import org.derianhernandez.bean.Usuarios;
import org.derianhernandez.system.Main;

/* Herencia Multiple concepto, interfaces. POO
 */
public class MenuPrincipalController implements Initializable {

    private Main escenarioPrincipal;
    Usuarios u = Usuarios.obtenerInstancia();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        desactivarBotones(u.getNivelPermisos());
        

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
    private MenuItem btnEmpleados;
    @FXML
    private MenuItem btnFacturas;
    @FXML
    private MenuItem btnDetalleFactura;
    @FXML
    private MenuItem btnEmailProveedor;
    @FXML
    private MenuItem btnDetalleCompra;
    @FXML
    private MenuItem btnTelefonoProveedor;
    @FXML
    private MenuItem btnLogin;

    @FXML
    private void buttonHandleEvent(ActionEvent event) throws IOException {
        if (event.getSource() == btnClientes) {
            escenarioPrincipal.menuClientesView();

        } else if (event.getSource() == btnProgramador) {
            escenarioPrincipal.programadorView();
        } else if (event.getSource() == btnProveedores) {
            escenarioPrincipal.proveedorView();
        } else if (event.getSource() == btnTipoProducto) {
            escenarioPrincipal.tipoProductoView();
        } else if (event.getSource() == btnCompras) {
            escenarioPrincipal.comprasView();
        } else if (event.getSource() == btnCargoEmpleado) {
            escenarioPrincipal.cargoEmpleadoView();
        } else if (event.getSource() == btnProductos) {
            escenarioPrincipal.productosView();
        } else if (event.getSource() == btnEmpleados) {
            escenarioPrincipal.EmpleadosView();
        } else if (event.getSource() == btnClientes) {
            escenarioPrincipal.menuClientesView();
        } else if (event.getSource() == btnFacturas) {
            escenarioPrincipal.facturaView();
        } else if (event.getSource() == btnDetalleFactura) {
            escenarioPrincipal.detalleFacturaView();
        } else if (event.getSource() == btnEmailProveedor) {
            escenarioPrincipal.emailProveedorView();
        } else if (event.getSource() == btnDetalleCompra) {
            escenarioPrincipal.detalleCompraView();
        } else if (event.getSource() == btnTelefonoProveedor) {
            escenarioPrincipal.telefonoProveedor();
        } else if (event.getSource() == btnLogin) {
            escenarioPrincipal.loginView();
        }

    }

    public void desactivarBotones(int nivelUsuario) {
        if (nivelUsuario == 1) {
            btnProveedores.setDisable(true);
            btnEmpleados.setDisable(true);
            btnEmailProveedor.setDisable(true);
            btnProductos.setDisable(true);
            btnDetalleCompra.setDisable(true);
            btnCompras.setDisable(true);
        } else if (nivelUsuario == 2) {
            btnProveedores.setDisable(false);
            btnEmpleados.setDisable(false);
            btnEmailProveedor.setDisable(false);
            btnProductos.setDisable(false);
            btnDetalleCompra.setDisable(false);
            btnCompras.setDisable(false);
        }

    }

}
