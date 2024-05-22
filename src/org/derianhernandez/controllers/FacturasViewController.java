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
import javafx.scene.input.MouseEvent;
import org.derianhernandez.system.Main;

/**
 * FXML Controller class
 *
 * @author mgm14
 */
public class FacturasViewController implements Initializable {

    @FXML
    private Button btnAgregaFactura;
    @FXML
    private Button btnEliminarFactura;
    @FXML
    private Button btnEditarFactura;
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
    private TextField txtNumeroF;
    @FXML
    private TextField txtEstadoF;
    @FXML
    private TextField txtTotalF;
    @FXML
    private TextField txtFechaF;
    @FXML
    private ComboBox cmbCodigoC;
    @FXML
    private ComboBox cmbCodigoE;
    @FXML
    private TableView tblF;
    @FXML
    private TableColumn colNumeroF;
    @FXML
    private TableColumn colEstadoF;
    @FXML
    private TableColumn colTotalF;
    @FXML
    private TableColumn colFechaF;
    @FXML
    private TableColumn colCodigoC;
    @FXML
    private TableColumn colCodigoE;
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
