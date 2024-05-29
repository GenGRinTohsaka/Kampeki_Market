/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.derianhernandez.controllers;

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
import javax.swing.JOptionPane;
import org.derianhernandez.bean.Proveedores;
import org.derianhernandez.bean.TelefonoProveedor;
import org.derianhernandez.db.Conexion;
import org.derianhernandez.system.Main;

/**
 *
 * @author 50258
 */
public class TelefonoProveedorViewController implements Initializable {

    private Main escenarioPrincipal;
    @FXML
    private Button btnHome;
    @FXML
    private Button btnAgregarTP;
    @FXML
    private ImageView imgAgregar;
    @FXML
    private Button btnEliminarTP;
    @FXML
    private ImageView imgEliminar;
    @FXML
    private Button btnEditarTP;
    @FXML
    private ImageView imgEditar;
    @FXML
    private Button btnReporte;
    @FXML
    private ImageView imgReporte;
    @FXML
    private TableView tblTP;
    @FXML
    private TextField txtCTP;
    @FXML
    private TextField txtNP;
    @FXML
    private TextField txtNS;
    @FXML
    private TextField txtO;
    @FXML
    private ComboBox cmbCP;
    @FXML
    private TableColumn colCodigoTP;
    @FXML
    private TableColumn colNP;
    @FXML
    private TableColumn colNS;
    @FXML
    private TableColumn colO;
    @FXML
    private TableColumn colCP;

    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;

    private ObservableList<Proveedores> listaProveedores;
    private ObservableList<TelefonoProveedor> listaTelefonoProveedor;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        desactivarControllers();
        cargarDatos();
        cmbCP.setItems(getProveedores());
    }

    public void cargarDatos() {
        tblTP.setItems(getTelefonoProveedor());
        colCodigoTP.setCellValueFactory(new PropertyValueFactory<TelefonoProveedor, Integer>("codigoTelefonoProveedor"));
        colNP.setCellValueFactory(new PropertyValueFactory<TelefonoProveedor, String>("numeroPrincipal"));
        colNS.setCellValueFactory(new PropertyValueFactory<TelefonoProveedor, String>("numeroSecundario"));
        colO.setCellValueFactory(new PropertyValueFactory<TelefonoProveedor, String>("observaciones"));
        colCP.setCellValueFactory(new PropertyValueFactory<TelefonoProveedor, Integer>("codigoProveedor"));

    }

    public void seleccionarElementos() {
        txtCTP.setText(String.valueOf(((TelefonoProveedor) tblTP.getSelectionModel().getSelectedItem()).getCodigoTelefonoProveedor()));
        txtNP.setText(((TelefonoProveedor) tblTP.getSelectionModel().getSelectedItem()).getNumeroPrincipal());
        txtNS.setText(((TelefonoProveedor) tblTP.getSelectionModel().getSelectedItem()).getNumeroSecundario());
        txtO.setText(((TelefonoProveedor) tblTP.getSelectionModel().getSelectedItem()).getObservaciones());
        cmbCP.getSelectionModel().select(buscarProveedor(((TelefonoProveedor) tblTP.getSelectionModel().getSelectedItem()).getCodigoProveedor()));
    }

    public Proveedores buscarProveedor(int codigoProveedor) {
        Proveedores resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarProveedor(?)}");
            procedimiento.setInt(1, codigoProveedor);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()) {
                resultado = new Proveedores(registro.getInt("codigoProveedor"),
                        registro.getString("nitProveedor"),
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

    public ObservableList<TelefonoProveedor> getTelefonoProveedor() {
        ArrayList<TelefonoProveedor> lista = new ArrayList();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarTelefonoProveedor()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new TelefonoProveedor(resultado.getInt("codigoTelefonoProveedor"),
                        resultado.getString("numeroPrincipal"),
                        resultado.getString("numeroSecundario"),
                        resultado.getString("observaciones"),
                        resultado.getInt("codigoProveedor")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaTelefonoProveedor = FXCollections.observableArrayList(lista);
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
                        resultado.getString("paginaWeb")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaProveedores = FXCollections.observableArrayList(lista);
    }

    public void guardar() {
        TelefonoProveedor registro = new TelefonoProveedor();
        registro.setCodigoTelefonoProveedor(Integer.parseInt(txtCTP.getText()));
        registro.setCodigoProveedor(((Proveedores) cmbCP.getSelectionModel().getSelectedItem()).getCodigoProveedor());
        registro.setNumeroPrincipal(txtNP.getText());
        registro.setNumeroSecundario(txtNS.getText());
        registro.setObservaciones(txtO.getText());
        registro.setCodigoProveedor(((Proveedores) cmbCP.getSelectionModel().getSelectedItem()).getCodigoProveedor());
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarTelefonoProveedor(?, ?, ?, ?, ?)}");
            procedimiento.setInt(1, registro.getCodigoTelefonoProveedor());
            procedimiento.setString(2, registro.getNumeroPrincipal());
            procedimiento.setString(3, registro.getNumeroSecundario());
            procedimiento.setString(4, registro.getObservaciones());
            procedimiento.setInt(5, registro.getCodigoProveedor());
            procedimiento.execute();
            listaTelefonoProveedor.add(registro);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void agregar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                activarControllers();
                limpiarControllers();
                btnAgregarTP.setText("Guardar");
                btnEliminarTP.setText("Cancelar");
                btnEditarTP.setDisable(true);
                btnReporte.setDisable(true);
                imgAgregar.setImage(new Image("/org/derianhernandez/images/Guardar.png"));
                imgEliminar.setImage(new Image("/org/derianhernandez/images/Eliminar2.png"));
                tipoDeOperaciones = operaciones.ACTUALIZAR;
                break;
            case ACTUALIZAR:
                guardar();
                desactivarControllers();
                limpiarControllers();
                btnAgregarTP.setText("Agregar");
                btnEliminarTP.setText("Eliminar");
                btnEditarTP.setDisable(false);
                btnReporte.setDisable(false);
                imgAgregar.setImage(new Image("/org/derianhernandez/images/box+.png"));
                imgEliminar.setImage(new Image("/org/derianhernandez/images/removeBox.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
                cargarDatos();
                break;
        }

    }

    public void eliminar() {
        switch (tipoDeOperaciones) {
            case ACTUALIZAR:
                desactivarControllers();
                limpiarControllers();
                btnAgregarTP.setText("Agregar");
                btnEliminarTP.setText("Eliminar");
                btnEditarTP.setDisable(false);
                btnReporte.setDisable(false);
                imgAgregar.setImage(new Image("/org/derianhernandez/images/box+.png"));
                imgEliminar.setImage(new Image("/org/derianhernandez/images/removeBox.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
            default:
                if (tblTP.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confirmar si elimina registro",
                            "Eliminar Telefono Proveedor", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarTelefonoProveedor(?)}");
                            procedimiento.setInt(1, ((TelefonoProveedor) tblTP.getSelectionModel().getSelectedItem()).getCodigoTelefonoProveedor());
                            procedimiento.execute();
                            listaTelefonoProveedor.remove(tblTP.getSelectionModel().getSelectedItem());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Debe Seleccionar un Elemento");
                }
        }
    }

    public void actualizar() {
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarTelefonoProveedor(?,?,?,?,?)}");
            TelefonoProveedor registro = (TelefonoProveedor) tblTP.getSelectionModel().getSelectedItem();
            registro.setCodigoTelefonoProveedor(Integer.parseInt(txtCTP.getText()));
            registro.setCodigoProveedor(((Proveedores) cmbCP.getSelectionModel().getSelectedItem()).getCodigoProveedor());
            registro.setNumeroPrincipal(txtNP.getText());
            registro.setNumeroSecundario(txtNS.getText());
            registro.setObservaciones(txtO.getText());
            procedimiento.setInt(1, registro.getCodigoTelefonoProveedor());
            procedimiento.setString(2, registro.getNumeroPrincipal());
            procedimiento.setString(3, registro.getNumeroSecundario());
            procedimiento.setString(4, registro.getObservaciones());
            procedimiento.setInt(5, registro.getCodigoProveedor());
            procedimiento.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void editar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                if (tblTP.getSelectionModel().getSelectedItem() != null) {
                    btnEditarTP.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnAgregarTP.setDisable(true);
                    btnEliminarTP.setDisable(true);
                    imgReporte.setImage(new Image("/org/derianhernandez/images/Eliminar2.png"));
                    activarControllers();
                    txtCTP.setEditable(false);
                    tipoDeOperaciones = operaciones.ACTUALIZAR;
                } else {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar algun elemento");
                }
                break;
            case ACTUALIZAR:
                actualizar();
                desactivarControllers();
                limpiarControllers();
                btnEditarTP.setText("Editar");
                btnReporte.setText("Reporte");
                btnAgregarTP.setDisable(false);
                btnEliminarTP.setDisable(false);
                imgReporte.setImage(new Image("/org/derianhernandez/images/informe-seo.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
                cargarDatos();
                break;
        }
    }

    public void reporte() {
        switch (tipoDeOperaciones) {
            case ACTUALIZAR:
                desactivarControllers();
                limpiarControllers();
                btnEditarTP.setText("Editar");
                btnReporte.setText("Reporte");
                btnAgregarTP.setDisable(false);
                btnEliminarTP.setDisable(false);
                imgEditar.setImage(new Image("/org/derianhernandez/images/avatar-de-usuario.png"));
                imgReporte.setImage(new Image("/org/derianhernandez/images/informe-seo.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
        }
    }

    public void desactivarControllers() {
        txtCTP.setEditable(false);
        txtNP.setEditable(false);
        txtNS.setEditable(false);
        txtO.setDisable(true);
        cmbCP.setDisable(true);
    }

    public void activarControllers() {
        txtCTP.setEditable(true);
        txtNP.setEditable(true);
        txtNS.setEditable(true);
        txtO.setDisable(false);
        cmbCP.setDisable(false);
    }

    public void limpiarControllers() {
        txtCTP.clear();
        txtNP.clear();
        txtNS.clear();
        tblTP.getSelectionModel().getSelectedItem();
        txtO.clear();
        cmbCP.getSelectionModel().getSelectedItem();
    }

    public Main getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    @FXML
    public void clicHome(ActionEvent event) {
        if (event.getSource() == btnHome) {
            escenarioPrincipal.menuPrincipalView();
        }
    }
}
