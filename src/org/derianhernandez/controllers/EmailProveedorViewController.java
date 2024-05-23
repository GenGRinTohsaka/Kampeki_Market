/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.derianhernandez.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javax.swing.JOptionPane;
import org.derianhernandez.bean.EmailProveedor;
import org.derianhernandez.bean.Proveedores;
import org.derianhernandez.db.Conexion;
import org.derianhernandez.system.Main;

/**
 * FXML Controller class
 *
 * @author mgm14
 */
public class EmailProveedorViewController implements Initializable {

    @FXML
    private Button btnHome;
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
    private ComboBox cmbCP;
    @FXML
    private TableView tblEP;
    @FXML
    private TableColumn colCodigoEP;
    @FXML
    private TableColumn colEP;
    @FXML
    private TableColumn colD;
    @FXML
    private TableColumn colCP;

    private Main escenarioPrincipal;

    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;

    private ObservableList<Proveedores> listaProveedores;
    private ObservableList<EmailProveedor> listaEmailProveedor;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatos();
        cmbCP.setItems(getProveedores());
        desactivarControles();
    }

    public Main getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    public ObservableList<EmailProveedor> getEmailProveedor() {
        ArrayList<EmailProveedor> lista = new ArrayList<EmailProveedor>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarEmailProveedor()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new EmailProveedor(resultado.getInt("codigoEmailProveedor"),
                        resultado.getString("emailProveedor"),
                        resultado.getString("descripcion"),
                        resultado.getInt("codigoProveedor")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaEmailProveedor = FXCollections.observableArrayList(lista);
    }

    public ObservableList<Proveedores> getProveedores() {
        ArrayList<Proveedores> lista = new ArrayList();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarProveedores()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new Proveedores(resultado.getInt("codigoProveedor"),
                        resultado.getString("nitProveedor"),
                        resultado.getString("nombreProveedor"),
                        resultado.getString("apellidoProveedor"),
                        resultado.getString("direccionProveedor"),
                        resultado.getString("razonSocial"),
                        resultado.getString("contactoPrincipal"),
                        resultado.getString("paginaWeb")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaProveedores = FXCollections.observableArrayList(lista);
    }

    public Proveedores buscarProveedor(int codigoProveedor) {
        Proveedores resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_buscarProveedor(?)}");
            procedimiento.setInt(1, codigoProveedor);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()) {
                resultado = new Proveedores(registro.getInt("codigoProveedor"),
                        registro.getString("NITProveedor"),
                        registro.getString("nombreProveedor"),
                        registro.getString("apellidoProveedor"),
                        registro.getString("direccionProveedor"),
                        registro.getString("razonSocial"),
                        registro.getString("contactoPrincipal"),
                        registro.getString("paginaWeb")
                );

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;
    }

    public void cargarDatos() {
        tblEP.setItems(getEmailProveedor());
        colCodigoEP.setCellValueFactory(new PropertyValueFactory<EmailProveedor, Integer>("codigoEmailProveedor"));
        colEP.setCellValueFactory(new PropertyValueFactory<EmailProveedor, String>("emailProveedor"));
        colD.setCellValueFactory(new PropertyValueFactory<EmailProveedor, String>("descripcion"));
        colCP.setCellValueFactory(new PropertyValueFactory<EmailProveedor, Integer>("codigoProveedor"));
    }

    public void seleccionarElemento() {
        txtCEP.setText(((EmailProveedor) tblEP.getSelectionModel().getSelectedItem()).getEmailProveedor());
        txtEP.setText(((EmailProveedor) tblEP.getSelectionModel().getSelectedItem()).getEmailProveedor());
        txtD.setText(String.valueOf(((EmailProveedor) tblEP.getSelectionModel().getSelectedItem()).getDescripcion()));
        cmbCP.getSelectionModel().select(buscarProveedor(((EmailProveedor) tblEP.getSelectionModel().getSelectedItem()).getCodigoProveedor()));

    }
    
    public void guardar() {
        EmailProveedor registro = new EmailProveedor();
        registro.setCodigoEmailProveedor(Integer.parseInt(txtCEP.getText()));
        registro.setEmailProveedor(txtEP.getText());
        registro.setDescripcion(txtD.getText());
        registro.setCodigoProveedor(((Proveedores) cmbCP.getSelectionModel().getSelectedItem()).getCodigoProveedor());

        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarEmailProveedor(?, ?, ?, ?)}");
            procedimiento.setInt(1, registro.getCodigoEmailProveedor());
            procedimiento.setString(2, registro.getEmailProveedor());
            procedimiento.setString(3, registro.getDescripcion());
            procedimiento.setInt(4, registro.getCodigoProveedor());
            procedimiento.execute();

            listaEmailProveedor.add(registro);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void actualizar() {
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarEmailProveedor(?,?,?,?)}");
            EmailProveedor registro = (EmailProveedor) tblEP.getSelectionModel().getSelectedItem();
            registro.setEmailProveedor(txtEP.getText());
            registro.setDescripcion(txtD.getText());
            registro.setCodigoProveedor(((Proveedores) cmbCP.getSelectionModel().getSelectedItem()).getCodigoProveedor());
            procedimiento.setInt(1, registro.getCodigoEmailProveedor());
            procedimiento.setString(2, registro.getEmailProveedor());
            procedimiento.setString(3, registro.getDescripcion());
            procedimiento.setInt(4, registro.getCodigoProveedor());
            procedimiento.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void desactivarControles() {
        txtCEP.setEditable(false);
        txtEP.setEditable(false);
        txtD.setEditable(false);
        cmbCP.setDisable(true);
    }

    public void activarControles() {
        txtCEP.setEditable(true);
        txtEP.setEditable(true);
        txtD.setEditable(true);
        cmbCP.setDisable(false);
    }

    public void limpiarControles() {
        txtCEP.clear();
        txtEP.clear();
        txtD.clear();
        cmbCP.getSelectionModel().getSelectedItem();
    }

    public void agregar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                limpiarControles();
                activarControles();
                btnAgregarEP.setText("Guardar");
                btnEliminarEP.setText("Cancelar");
                btnEditarEP.setDisable(true);
                btnReporte.setDisable(true);
                imgAgregar.setImage(new Image("/org/derianhernandez/images/Guardar.png"));
                imgEliminar.setImage(new Image("/org/derianhernandez/images/Eliminar2.png"));
                tipoDeOperaciones = EmailProveedorViewController.operaciones.ACTUALIZAR;
                break;
            case ACTUALIZAR:
                guardar();
                desactivarControles();
                limpiarControles();
                btnAgregarEP.setText("Agregar");
                btnEliminarEP.setText("Eliminar");
                btnEditarEP.setDisable(false);
                btnReporte.setDisable(false);
                imgAgregar.setImage(new Image("/org/derianhernandez/images/box+.png"));
                imgEliminar.setImage(new Image("/org/derianhernandez/images/removeBox.png"));
                tipoDeOperaciones = EmailProveedorViewController.operaciones.NINGUNO;
                break;
        }
    }

    public void eliminar() {
        switch (tipoDeOperaciones) {
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnAgregarEP.setText("Agregar");
                btnEliminarEP.setText("Eliminar");
                btnEditarEP.setDisable(false);
                btnReporte.setDisable(false);
                imgAgregar.setImage(new Image("/org/derianhernandez/images/box+.png"));
                imgEliminar.setImage(new Image("/org/derianhernandez/images/removeBox.png"));
                tipoDeOperaciones = EmailProveedorViewController.operaciones.NINGUNO;
                break;
        }
    }

    public void editar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                if (tblEP.getSelectionModel().getSelectedItem() != null) {
                    btnEditarEP.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnAgregarEP.setDisable(true);
                    btnEliminarEP.setDisable(true);
                    imgEditar.setImage(new Image("/org/derianhernandez/images/editarCaja.png"));
                    imgReporte.setImage(new Image("/org/derianhernandez/images/Eliminar2.png"));
                    activarControles();
                    txtCEP.setEditable(false);
                    tipoDeOperaciones = operaciones.ACTUALIZAR;
                } else {
                    JOptionPane.showMessageDialog(null, "Debe de seleccionar algun elemento");
                }
                break;
            case ACTUALIZAR:
                actualizar();
                desactivarControles();
                limpiarControles();
                btnEditarEP.setText("Editar");
                btnReporte.setText("Reportes");
                btnAgregarEP.setDisable(false);
                btnEliminarEP.setDisable(false);
                imgEditar.setImage(new Image("/org/derianhernandez/images/boxEdit.png"));
                imgReporte.setImage(new Image("/org/derianhernandez/images/informe-seo.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
                cargarDatos();
                break;
        }
    }
    
    public void reporte() {
        switch (tipoDeOperaciones) {
            case ACTUALIZAR:
                desactivarControles();
                btnEditarEP.setText("Editar");
                btnReporte.setText("Reportes");
                btnAgregarEP.setDisable(false);
                btnEliminarEP.setDisable(false);
                imgEditar.setImage(new Image("/org/derianhernandez/images/boxEdit.png"));
                imgReporte.setImage(new Image("/org/derianhernandez/images/informe-seo.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
        }
    }
@FXML
    private void clicHome(ActionEvent event) throws IOException {
        if (event.getSource() == btnHome) {
            escenarioPrincipal.menuPrincipalView();
        }
    }
}
