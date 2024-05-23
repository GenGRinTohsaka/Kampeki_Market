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
import javax.swing.JOptionPane;
import org.derianhernandez.bean.Clientes;
import org.derianhernandez.bean.DetalleFactura;
import org.derianhernandez.bean.Empleados;
import org.derianhernandez.bean.Facturas;
import org.derianhernandez.bean.Productos;
import org.derianhernandez.db.Conexion;
import org.derianhernandez.system.Main;

public class DetalleFacturaViewController implements Initializable {

    @FXML
    private Button btnHome;
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

    private ObservableList<DetalleFactura> listaDetalleFactura;
    private ObservableList<Facturas> listaFacturas;
    private ObservableList<Productos> listaProductos;

    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
       cargarDatos();
       cmbCodigoF.setItems(getFacturas());
       cmbCodigoP.setItems(getProducto());
    }

    public Main getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    public ObservableList<DetalleFactura> getDetalleFactura() {
        ArrayList<DetalleFactura> lista = new ArrayList();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarDetalleFactura()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new DetalleFactura(resultado.getInt("codigoDetalleFactura"),
                        resultado.getDouble("precioUnitario"),
                        resultado.getInt("cantidad"),
                        resultado.getInt("numeroFactura"),
                        resultado.getString("codigoProducto")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaDetalleFactura = FXCollections.observableArrayList(lista);
    }

    public ObservableList<Productos> getProducto() {
        ArrayList<Productos> lista = new ArrayList<Productos>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarProductos()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new Productos(resultado.getString("codigoProducto"),
                        resultado.getString("descripcionProducto"),
                        resultado.getDouble("precioUnitario"),
                        resultado.getDouble("precioDocena"),
                        resultado.getDouble("precioMayor"),
                        resultado.getString("imagenProducto"),
                        resultado.getInt("existencia"),
                        resultado.getInt("codigoTipoProducto"),
                        resultado.getInt("codigoProveedor")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaProductos = FXCollections.observableArrayList(lista);
    }

    public ObservableList<Facturas> getFacturas() {
        ArrayList<Facturas> lista = new ArrayList();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarFactura()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new Facturas(resultado.getInt("numeroFactura"),
                        resultado.getString("estado"),
                        resultado.getDouble("totalFactura"),
                        resultado.getString("fechaFactura"),
                        resultado.getInt("codigoCliente"),
                        resultado.getInt("codigoEmpleado")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaFacturas = FXCollections.observableArrayList(lista);
    }

    public Facturas buscarFactura(int numeroFactura) {
        Facturas resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarFactura(?)}");
            procedimiento.setInt(1, numeroFactura);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()) {
                resultado = new Facturas(registro.getInt("numeroFactura"),
                        registro.getString("estado"),
                        registro.getDouble("totalFactura"),
                        registro.getString("fechaFactura"),
                        registro.getInt("codigoCliente"),
                        registro.getInt("codigoEmpleado"));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;
    }

    public Productos buscarProducto(String codigoProducto) {
        Productos resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarProductos(?)}");
            procedimiento.setString(1, codigoProducto);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()) {
                resultado = new Productos(registro.getString("codigoProducto"),
                        registro.getString("descripcionProducto"),
                        registro.getDouble("precioUnitario"),
                        registro.getDouble("precioDocena"),
                        registro.getDouble("precioMayor"),
                        registro.getString("imagenProducto"),
                        registro.getInt("existencia"),
                        registro.getInt("codigoTipoProducto"),
                        registro.getInt("codigoProveedor"));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;
    }

    public void cargarDatos() {
        tblDF.setItems(getDetalleFactura());
        colCodigoDF.setCellValueFactory(new PropertyValueFactory<DetalleFactura, Integer>("codigoDetalleFactura"));
        colPrecioU.setCellValueFactory(new PropertyValueFactory<DetalleFactura, Double>("precioUnitario"));
        colCantidadDF.setCellValueFactory(new PropertyValueFactory<DetalleFactura, Integer>("cantidad"));
        colNumeroF.setCellValueFactory(new PropertyValueFactory<DetalleFactura, Integer>("numeroFactura"));
        colCodigoP.setCellValueFactory(new PropertyValueFactory<DetalleFactura, String>("codigoProducto"));
    }

    public void seleccionarElemento() {
        txtCodigoDF.setText(String.valueOf(((DetalleFactura) tblDF.getSelectionModel().getSelectedItem()).getNumeroFactura()));
        txtPrecioU.setText(String.valueOf(((DetalleFactura) tblDF.getSelectionModel().getSelectedItem()).getPrecioUnitario()));
        txtCantidad.setText(String.valueOf(((DetalleFactura) tblDF.getSelectionModel().getSelectedItem()).getCantidad()));
        cmbCodigoF.getSelectionModel().select(buscarFactura(((DetalleFactura) tblDF.getSelectionModel().getSelectedItem()).getNumeroFactura()));
        cmbCodigoP.getSelectionModel().select(buscarProducto(((DetalleFactura) tblDF.getSelectionModel().getSelectedItem()).getCodigoProducto()));
    }

    public void guardar() {

        DetalleFactura registro = new DetalleFactura();
        registro.setCodigoDetalleFactura(Integer.parseInt(txtCodigoDF.getText()));
        registro.setPrecioUnitario(Double.parseDouble(txtPrecioU.getText()));
        registro.setCantidad(Integer.parseInt(txtCantidad.getText()));
        registro.setNumeroFactura(((Facturas) cmbCodigoF.getSelectionModel().getSelectedItem()).getNumeroFactura());
        registro.setCodigoProducto(((Productos) cmbCodigoP.getSelectionModel().getSelectedItem()).getCodigoProducto());

        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarDetalleFactura(?, ?, ?, ?, ?)}");
            procedimiento.setInt(1, registro.getCodigoDetalleFactura());
            procedimiento.setDouble(2, registro.getPrecioUnitario());
            procedimiento.setInt(3, registro.getCantidad());
            procedimiento.setInt(4, registro.getNumeroFactura());
            procedimiento.setInt(5, registro.getCodigoDetalleFactura());

            procedimiento.execute();

            listaDetalleFactura.add(registro);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void actualizar() {
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarDetalleFactura(?,?,?,?,?)}");
            DetalleFactura registro = (DetalleFactura) tblDF.getSelectionModel().getSelectedItem();
            registro.setPrecioUnitario(Double.parseDouble(txtPrecioU.getText()));
            registro.setCantidad(Integer.parseInt(txtCantidad.getText()));
            registro.setNumeroFactura(((Facturas) cmbCodigoF.getSelectionModel().getSelectedItem()).getNumeroFactura());
            registro.setCodigoProducto(((Productos) cmbCodigoP.getSelectionModel().getSelectedItem()).getCodigoProducto());
            procedimiento.setInt(1, registro.getCodigoDetalleFactura());
            procedimiento.setDouble(2, registro.getPrecioUnitario());
            procedimiento.setInt(3, registro.getCantidad());
            procedimiento.setInt(4, registro.getNumeroFactura());
            procedimiento.setInt(5, registro.getCodigoDetalleFactura());

            procedimiento.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void desactivarControles() {
        txtCodigoDF.setEditable(false);
        txtPrecioU.setEditable(false);
        txtCantidad.setEditable(false);
        cmbCodigoF.setDisable(true);
        cmbCodigoP.setDisable(true);
    }

    public void activarControles() {
        txtCodigoDF.setEditable(true);
        txtPrecioU.setEditable(true);
        txtCantidad.setEditable(true);
        cmbCodigoF.setDisable(false);
        cmbCodigoP.setDisable(false);
    }

    public void limpiarControles() {
        txtCodigoDF.clear();
        txtPrecioU.clear();
        txtCantidad.clear();
        cmbCodigoF.getSelectionModel().getSelectedItem();
        cmbCodigoP.getSelectionModel().getSelectedItem();
    }
    
    public void agregar() {
  
        switch (tipoDeOperaciones) {
            case NINGUNO:
                limpiarControles();
                activarControles();
                btnAgregarDetalleF.setText("Guardar");
                btnEliminarDetalleF.setText("Cancelar");
                btnEditarDetalleF.setDisable(true);
                btnReportes.setDisable(true);
                imgAgregar.setImage(new Image("/org/derianhernandez/images/Guardar.png"));
                imgEliminar.setImage(new Image("/org/derianhernandez/images/Eliminar2.png"));
                tipoDeOperaciones = DetalleFacturaViewController.operaciones.ACTUALIZAR;
                break;
            case ACTUALIZAR:
                guardar();
                desactivarControles();
                limpiarControles();
                btnAgregarDetalleF.setText("Agregar");
                btnEliminarDetalleF.setText("Eliminar");
                btnEditarDetalleF.setDisable(false);
                btnReportes.setDisable(false);
                imgAgregar.setImage(new Image("/org/derianhernandez/images/factura+.png"));
                imgEliminar.setImage(new Image("/org/derianhernandez/images/factura-.png"));
                tipoDeOperaciones = DetalleFacturaViewController.operaciones.NINGUNO;
                break;
        }
    }
    
    public void eliminar() {
        switch (tipoDeOperaciones) {
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnAgregarDetalleF.setText("Agregar");
                btnEliminarDetalleF.setText("Eliminar");
                btnEditarDetalleF.setDisable(false);
                btnReportes.setDisable(false);
                imgAgregar.setImage(new Image("/org/derianhernandez/images/factura+.png"));
                imgEliminar.setImage(new Image("/org/derianhernandez/images/factura-.png"));
                tipoDeOperaciones = DetalleFacturaViewController.operaciones.NINGUNO;
                break;
        }
    }
    
    public void editar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                if (tblDF.getSelectionModel().getSelectedItem() != null) {
                    btnEditarDetalleF.setText("Actualizar");
                    btnReportes.setText("Cancelar");
                    btnEliminarDetalleF.setDisable(true);
                    btnAgregarDetalleF.setDisable(true);
                    imgEditar.setImage(new Image("/org/derianhernandez/images/editarCaja.png"));
                    imgReporte.setImage(new Image("/org/derianhernandez/images/Eliminar2.png"));
                    activarControles();
                    txtCodigoDF.setEditable(false);
                    tipoDeOperaciones = DetalleFacturaViewController.operaciones.ACTUALIZAR;
                } else {
                    JOptionPane.showMessageDialog(null, "Debe de seleccionar algun elemento");
                }
                break;
            case ACTUALIZAR:
                actualizar();
                desactivarControles();
                limpiarControles();
                btnEditarDetalleF.setText("Editar");
                btnReportes.setText("Reportes");
                btnAgregarDetalleF.setDisable(false);
                btnEliminarDetalleF.setDisable(false);
                imgEditar.setImage(new Image("/org/derianhernandez/images/facturaEdit.png"));
                imgReporte.setImage(new Image("/org/derianhernandez/images/informe-seo.png"));
                tipoDeOperaciones = DetalleFacturaViewController.operaciones.NINGUNO;
                cargarDatos();
                break;
        }
    }
    
    public void reporte() {
        switch (tipoDeOperaciones) {
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnEditarDetalleF.setText("Editar");
                btnReportes.setText("Reportes");
                btnAgregarDetalleF.setDisable(false);
                btnEliminarDetalleF.setDisable(false);
                imgEditar.setImage(new Image("/org/derianhernandez/images/facturaEdit.png"));
                imgReporte.setImage(new Image("/org/derianhernandez/images/informe-seo.png"));
                tipoDeOperaciones = DetalleFacturaViewController.operaciones.NINGUNO;
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
