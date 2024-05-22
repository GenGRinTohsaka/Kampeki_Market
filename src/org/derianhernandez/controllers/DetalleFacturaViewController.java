/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.derianhernandez.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import org.derianhernandez.system.Main;

public class DetalleFacturaViewController implements Initializable {

    @FXML
    private Button btnAgregarDetalleF;
    @FXML
    private Button btnEliminarDetalleF;
    @FXML
    private Button btnEditarDetalleF;
    @FXML
    private Button btnReportes;
    @FXML
    private ImageView imgAgregar;
    @FXML
    private ImageView imgEliminar;
    @FXML
    private ImageView imgEditar;
    @FXML
    private ImageView imgReporte;
    @FXML
    private TextField txtCodigoDF;
    @FXML
    private TextField txtPrecioU;
    @FXML
    private TextField txtCantidad;
    @FXML
    private ComboBox cmbCodigoF;
    @FXML
    private ComboBox cmbCodigoP;
    @FXML
    private TableView tblDF;
    @FXML
    private TableColumn colCodigoDF;
    @FXML
    private TableColumn colPrecioU;
    @FXML
    private TableColumn colCantidadDF;
    @FXML
    private TableColumn colNumeroF;
    @FXML
    private TableColumn colCodigoP;
    private Main escenarioPrincipal;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public Main getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
}
