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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author mgm14
 */
public class EmailProveedorViewController implements Initializable {

    @FXML
    private Button btnAgregarEP;
    @FXML
    private Button btnEliminarEP;
    @FXML
    private Button btnEditarEP;
    @FXML
    private Button btnReporte;
    @FXML
    private ImageView imgAgregar;
    @FXML
    private ImageView imgEliminar;
    @FXML
    private ImageView imgEditar;
    @FXML
    private ImageView imgReporte;
    @FXML
    private ImageView imgEliminar1;
    @FXML
    private TextField txtCEP;
    @FXML
    private TextField txtEP;
    @FXML
    private TextField txtD;
    @FXML
    private Label txtCP;
    @FXML
    private ComboBox<?> cmbCP;
    @FXML
    private TableView<?> tblEP;
    @FXML
    private TableColumn<?, ?> colCodigoDF;
    @FXML
    private TableColumn<?, ?> colPrecioU;
    @FXML
    private TableColumn<?, ?> colCantidadDF;
    @FXML
    private TableColumn<?, ?> colNumeroF;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void agregar(ActionEvent event) {
    }

    @FXML
    private void eliminar(ActionEvent event) {
    }

    @FXML
    private void editar(ActionEvent event) {
    }

    @FXML
    private void reporte(ActionEvent event) {
    }

    @FXML
    private void agregar(MouseEvent event) {
    }

    @FXML
    private void editar(MouseEvent event) {
    }

    @FXML
    private void reporte(MouseEvent event) {
    }

    @FXML
    private void eliminar(MouseEvent event) {
    }

    @FXML
    private void seleccionarElemento(MouseEvent event) {
    }
    
}
