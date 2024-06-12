/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.derianhernandez.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javax.swing.JOptionPane;
import org.derianhernandez.bean.Clientes;
import org.derianhernandez.bean.Compras;
import org.derianhernandez.bean.DetalleFactura;
import org.derianhernandez.bean.Empleados;
import org.derianhernandez.bean.Facturas;
import org.derianhernandez.db.Conexion;
import org.derianhernandez.reportes.GenerarReportes;
import org.derianhernandez.system.Main;
import org.derianhernandez.controllers.DetalleFacturaViewController;;

/**
 * FXML Controller class
 *
 * @author mgm14
 */
public class FacturasViewController implements Initializable {

    private ObservableList<Empleados> listaEmpleados;
    private ObservableList<Facturas> listaFacturas;
    private ObservableList<Clientes> listaClientes;
    @FXML
    private Button btnHome;
    @FXML
    private Button btnAgregarFactura;
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
    private DatePicker dpFF;
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
        cargarDatos();
        cmbCodigoC.setItems(getClientes());
        cmbCodigoE.setItems(getEmpleados());
    }

    public Main getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    }

    private operaciones tipoDeOperaciones = operaciones.NINGUNO;

    public ObservableList<Facturas> getFacturas() {
        ArrayList<Facturas> lista = new ArrayList();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion()
                    .prepareCall("{call sp_ListarFactura()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new Facturas(resultado.getInt("numeroFactura"),
                        resultado.getString("estado"),
                        resultado.getDouble("totalFactura"),
                        resultado.getDate("fechaFactura"),
                        resultado.getInt("codigoCliente"),
                        resultado.getInt("codigoEmpleado")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaFacturas = FXCollections.observableArrayList(lista);
    }

    public ObservableList<Clientes> getClientes() {
        ArrayList<Clientes> lista = new ArrayList();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion()
                    .prepareCall("{call sp_ListarClientes()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new Clientes(resultado.getInt("codigoCliente"),
                        resultado.getString("nitCliente"),
                        resultado.getString("nombreCliente"),
                        resultado.getString("apellidoCliente"),
                        resultado.getString("direccionCliente"),
                        resultado.getString("telefonoCliente"),
                        resultado.getString("correoCliente")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaClientes = FXCollections.observableArrayList(lista);
    }

    public ObservableList<Empleados> getEmpleados() {
        ArrayList<Empleados> lista = new ArrayList<Empleados>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion()
                    .prepareCall("{call sp_ListarEmpleados()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new Empleados(resultado.getInt("codigoEmpleado"),
                        resultado.getString("nombresEmpleado"),
                        resultado.getString("apellidosEmpleado"),
                        resultado.getDouble("sueldo"),
                        resultado.getString("direccion"),
                        resultado.getString("turno"),
                        resultado.getInt("codigoCargoEmpleado")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaEmpleados = FXCollections.observableArrayList(lista);
    }

    public Empleados buscarEmpleado(int codigoEmpleado) {
        Empleados resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion()
                    .prepareCall("{call sp_BuscarEmpleados(?)}");
            procedimiento.setInt(1, codigoEmpleado);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()) {
                resultado = new Empleados(registro.getInt("codigoEmpleado"),
                        registro.getString("nombresEmpleado"),
                        registro.getString("apellidosEmpleado"),
                        registro.getDouble("sueldo"),
                        registro.getString("direccion"),
                        registro.getString("turno"),
                        registro.getInt("codigoCargoEmpleado"));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;
    }

    public Clientes buscarCliente(int codigoCliente) {
        Clientes resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion()
                    .prepareCall("{call sp_BuscarClientes(?)}");
            procedimiento.setInt(1, codigoCliente);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()) {
                resultado = new Clientes(registro.getInt("codigoCliente"),
                        registro.getString("NITCliente"),
                        registro.getString("nombreCliente"),
                        registro.getString("apellidoCliente"),
                        registro.getString("direccionCliente"),
                        registro.getString("telefonoCliente"),
                        registro.getString("correoCliente"));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;
    }

    public void cargarDatos() {
        tblF.setItems(getFacturas());
        colNumeroF.setCellValueFactory(new PropertyValueFactory<Facturas, Integer>("numeroFactura"));
        colEstadoF.setCellValueFactory(new PropertyValueFactory<Facturas, String>("estado"));
        colTotalF.setCellValueFactory(new PropertyValueFactory<Facturas, Double>("totalFactura"));
        colFechaF.setCellValueFactory(new PropertyValueFactory<Facturas, DatePicker>("fechaFactura"));
        colCodigoC.setCellValueFactory(new PropertyValueFactory<Facturas, Integer>("codigoCliente"));
        colCodigoE.setCellValueFactory(new PropertyValueFactory<Facturas, Integer>("codigoEmpleado"));
    }

    public void seleccionarElemento() {
        txtNumeroF.setText(String.valueOf(((Facturas) tblF.getSelectionModel().getSelectedItem()).getNumeroFactura()));
        txtEstadoF.setText(((Facturas) tblF.getSelectionModel().getSelectedItem()).getEstado());
        txtTotalF.setText(String.valueOf(((Facturas) tblF.getSelectionModel().getSelectedItem()).getTotalFactura()));
        dpFF.setValue(((Facturas) tblF.getSelectionModel().getSelectedItem()).getFechaFactura().toLocalDate());
        cmbCodigoC.getSelectionModel()
                .select(buscarCliente(((Facturas) tblF.getSelectionModel().getSelectedItem()).getCodigoCliente()));
        cmbCodigoE.getSelectionModel()
                .select(buscarEmpleado(((Facturas) tblF.getSelectionModel().getSelectedItem()).getCodigoEmpleado()));
    }

    public void guardar() {

        Facturas registro = new Facturas();
        registro.setNumeroFactura(Integer.parseInt(txtNumeroF.getText()));
        registro.setEstado(txtEstadoF.getText());
        registro.setTotalFactura(0);
        registro.setFechaFactura(Date.valueOf(dpFF.getValue()));
        registro.setCodigoCliente(((Clientes) cmbCodigoC.getSelectionModel().getSelectedItem()).getCodigoCliente());
        registro.setCodigoEmpleado(((Empleados) cmbCodigoE.getSelectionModel().getSelectedItem()).getCodigoEmpleado());

        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion()
                    .prepareCall("{call sp_AgregarFactura(?, ?, ?, ?, ?, ?)}");
            procedimiento.setInt(1, registro.getNumeroFactura());
            procedimiento.setString(2, registro.getEstado());
            procedimiento.setDouble(3, registro.getTotalFactura());
            procedimiento.setDate(4, registro.getFechaFactura());
            procedimiento.setInt(5, registro.getCodigoCliente());
            procedimiento.setInt(6, registro.getCodigoEmpleado());

            procedimiento.execute();

            listaFacturas.add(registro);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void actualizar() {
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion()
                    .prepareCall("{call sp_EditarFactura(?,?,?,?,?,?)}");
            Facturas registro = (Facturas) tblF.getSelectionModel().getSelectedItem();
            registro.setEstado(txtEstadoF.getText());
            registro.setTotalFactura(Double.parseDouble(txtTotalF.getText()));
            registro.setFechaFactura(Date.valueOf(dpFF.getValue()));
            registro.setCodigoCliente(((Clientes) cmbCodigoC.getSelectionModel().getSelectedItem()).getCodigoCliente());
            registro.setCodigoEmpleado(
                    ((Empleados) cmbCodigoE.getSelectionModel().getSelectedItem()).getCodigoEmpleado());
            procedimiento.setInt(1, registro.getNumeroFactura());
            procedimiento.setString(2, registro.getEstado());
            procedimiento.setDouble(3, registro.getTotalFactura());
            procedimiento.setDate(4, registro.getFechaFactura());
            procedimiento.setInt(5, registro.getCodigoCliente());
            procedimiento.setInt(6, registro.getCodigoEmpleado());

            procedimiento.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void desactivarControles() {
        txtNumeroF.setEditable(false);
        txtEstadoF.setEditable(false);
        txtTotalF.setEditable(false);
        dpFF.setDisable(true);
        cmbCodigoC.setDisable(true);
        cmbCodigoE.setDisable(true);
    }

    public void activarControles() {
        txtNumeroF.setEditable(true);
        txtEstadoF.setEditable(true);
        txtTotalF.setEditable(false);
        dpFF.setDisable(false);
        cmbCodigoC.setDisable(false);
        cmbCodigoE.setDisable(false);
    }

    public void limpiarControles() {
        txtNumeroF.clear();
        txtEstadoF.clear();
        txtTotalF.clear();
        dpFF.getEditor().clear();
        cmbCodigoC.getSelectionModel().getSelectedItem();
        cmbCodigoE.getSelectionModel().getSelectedItem();

    }

    public void agregar() {

        switch (tipoDeOperaciones) {
            case NINGUNO:
                limpiarControles();
                activarControles();
                btnAgregarFactura.setText("Guardar");
                btnEliminarFactura.setText("Cancelar");
                btnEditarFactura.setDisable(true);
                btnReportes.setDisable(true);
                imgAgregar.setImage(new Image("/org/derianhernandez/images/Guardar.png"));
                imgEliminar.setImage(new Image("/org/derianhernandez/images/Eliminar2.png"));
                tipoDeOperaciones = FacturasViewController.operaciones.ACTUALIZAR;
                break;
            case ACTUALIZAR:
                guardar();
                desactivarControles();
                limpiarControles();
                btnAgregarFactura.setText("Agregar");
                btnEliminarFactura.setText("Eliminar");
                btnEditarFactura.setDisable(false);
                btnReportes.setDisable(false);
                imgAgregar.setImage(new Image("/org/derianhernandez/images/factura+.png"));
                imgEliminar.setImage(new Image("/org/derianhernandez/images/factura-.png"));
                tipoDeOperaciones = FacturasViewController.operaciones.NINGUNO;
                break;
        }
    }

    public void eliminar() {
        switch (tipoDeOperaciones) {
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnAgregarFactura.setText("Agregar");
                btnEliminarFactura.setText("Eliminar");
                btnEditarFactura.setDisable(false);
                btnReportes.setDisable(false);
                imgAgregar.setImage(new Image("/org/derianhernandez/images/factura+.png"));
                imgEliminar.setImage(new Image("/org/derianhernandez/images/factura-.png"));
                tipoDeOperaciones = FacturasViewController.operaciones.NINGUNO;
                break;
        }
    }

    public void editar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                if (tblF.getSelectionModel().getSelectedItem() != null) {
                    btnEditarFactura.setText("Actualizar");
                    btnReportes.setText("Cancelar");
                    btnAgregarFactura.setDisable(true);
                    btnEliminarFactura.setDisable(true);
                    imgEditar.setImage(new Image("/org/derianhernandez/images/editarCaja.png"));
                    imgReporte.setImage(new Image("/org/derianhernandez/images/Eliminar2.png"));
                    activarControles();
                    txtNumeroF.setEditable(false);
                    tipoDeOperaciones = FacturasViewController.operaciones.ACTUALIZAR;
                } else {
                    JOptionPane.showMessageDialog(null, "Debe de seleccionar algun elemento");
                }
                break;
            case ACTUALIZAR:
                actualizar();
                desactivarControles();
                limpiarControles();
                btnEditarFactura.setText("Editar");
                btnReportes.setText("Reportes");
                btnAgregarFactura.setDisable(false);
                btnEliminarFactura.setDisable(false);
                imgEditar.setImage(new Image("/org/derianhernandez/images/facturaEdit.png"));
                imgReporte.setImage(new Image("/org/derianhernandez/images/informe-seo.png"));
                tipoDeOperaciones = FacturasViewController.operaciones.NINGUNO;
                cargarDatos();
                break;
        }
    }

    public void reporte() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                if (tblF.getSelectionModel().getSelectedItem() == null) {
                    JOptionPane.showMessageDialog(null, "Debe de seleccionar alguna factura");
                } else {
                    imprimirReportes();
                    break;
                }

            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnEditarFactura.setText("Editar");
                btnReportes.setText("Reportes");
                btnAgregarFactura.setDisable(false);
                btnEliminarFactura.setDisable(false);
                imgEditar.setImage(new Image("/org/derianhernandez/images/facturaEdit.png"));
                imgReporte.setImage(new Image("/org/derianhernandez/images/informe-seo.png"));
                tipoDeOperaciones = FacturasViewController.operaciones.NINGUNO;
                break;
        }
    }

    @FXML
    private void clicHome(ActionEvent event) throws IOException {
        if (event.getSource() == btnHome) {
            escenarioPrincipal.menuPrincipalView();
        }
    }

    public void imprimirReportes() {
        Map parametros = new HashMap();
        parametros.put("_numeroFactura",Integer.parseInt(txtNumeroF.getText()));
        GenerarReportes.mostrarReporte("ReporteFactura.jasper", "Factura", parametros);

    }
}
