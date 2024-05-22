/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.derianhernandez.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
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
public class EmpleadosViewController implements Initializable {

    @FXML
    private Button btnAgregarE;
    @FXML
    private Button btnEliminarE;
    @FXML
    private Button btnReporteE;
    @FXML
    private ImageView imgAgregar;
    @FXML
    private ImageView imgEliminar;
    @FXML
    private ImageView imgEditar;
    @FXML
    private ImageView imgReporte;
    @FXML
    private Button btnEditarE;
    @FXML
    private TextField txtCodigoE;
    @FXML
    private TextField txtTurno;
    @FXML
    private TextField txtNombres;
    @FXML
    private TextField txtApellidos;
    @FXML
    private TextField txtSueldo;
    @FXML
    private TextField txtDireccion;
    @FXML
    private ComboBox cmbCCE;
    @FXML
    private TableView tblE;
    @FXML
    private TableColumn colCodigoEmpleado;
    @FXML
    private TableColumn colTurno;
    @FXML
    private TableColumn colNombres;
    @FXML
    private TableColumn colApellidos;
    @FXML
    private TableColumn colSueldo;
    @FXML
    private TableColumn colDireccion;
    @FXML
    private TableColumn colCodigoCargoEmpleado;
    private Main escenarioPrincipal;
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
