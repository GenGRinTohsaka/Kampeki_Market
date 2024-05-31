/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.derianhernandez.controllers;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
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
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javax.swing.JOptionPane;
import org.derianhernandez.bean.Productos;
import org.derianhernandez.bean.Proveedores;
import org.derianhernandez.bean.TipoProducto;
import org.derianhernandez.db.Conexion;
import org.derianhernandez.system.Main;

/**
 * FXML Controller class
 *
 * @author informatica
 */
public class ProductosViewController implements Initializable {

    @FXML
    private Button btnHome;
    @FXML
    private Button btnAgregarP;
    @FXML
    private Button btnEliminarP;
    @FXML
    private Button btnEditarP;
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
    private TableView tblP;
    @FXML
    private TableColumn colCodigoP;
    @FXML
    private TableColumn colPrecioM;
    @FXML
    private TableColumn colPrecioU;
    @FXML
    private TableColumn colDescripcionP;
    @FXML
    private TableColumn colPrecioD;
    @FXML
    private TableColumn colExistenciaP;
    @FXML
    private TableColumn colCodigoTP;
    @FXML
    private TableColumn colCodigoPR;
    @FXML
    private TableColumn colImagenP;
    @FXML
    private TextField txtCodigoCP;
    @FXML
    private TextField txtPrecioU;
    @FXML
    private TextField txtDescripcionP;
    @FXML
    private TextField txtPrecioM;
    @FXML
    private TextField txtPrecioD;
    @FXML
    private TextField txtImagen;
    @FXML
    private TextField txtExistencia;
    @FXML
    private TextField txtCodigoTP;
    @FXML
    private TextField txtCodigoPR;
    @FXML
    private ComboBox cmbCP;
    @FXML
    private ComboBox cmbCTP;
    @FXML
    private ImageView imgPrueba;
    private String url;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatos();
        cmbCTP.setItems(getTipoProducto());
        cmbCP.setItems(getProveedores());
        desactivarControles();
    }

    private Main escenarioPrincipal;

    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;

    public Main getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    private ObservableList<Productos> listaProductos;
    private ObservableList<TipoProducto> listaTipoProducto;
    private ObservableList<Proveedores> listaProveedor;

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

    public ObservableList<TipoProducto> getTipoProducto() {
        ArrayList<TipoProducto> lista = new ArrayList();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarTipoProducto()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new TipoProducto(resultado.getInt("codigoTipoProducto"),
                        resultado.getString("descripcion")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaTipoProducto = FXCollections.observableArrayList(lista);
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
        return listaProveedor = FXCollections.observableArrayList(lista);

    }

    public void cargarDatos() {
        tblP.setItems(getProducto());
        colCodigoP.setCellValueFactory(new PropertyValueFactory<Productos, String>("codigoProducto"));
        colDescripcionP.setCellValueFactory(new PropertyValueFactory<Productos, String>("descripcionProducto"));
        colPrecioU.setCellValueFactory(new PropertyValueFactory<Productos, Double>("precioUnitario"));
        colPrecioD.setCellValueFactory(new PropertyValueFactory<Productos, Double>("precioDocena"));
        colPrecioM.setCellValueFactory(new PropertyValueFactory<Productos, Double>("precioMayor"));
        colImagenP.setCellValueFactory(new PropertyValueFactory<Productos, String>("imagenProducto"));
        colExistenciaP.setCellValueFactory(new PropertyValueFactory<Productos, Integer>("existencia"));
        colCodigoTP.setCellValueFactory(new PropertyValueFactory<Productos, Integer>("codigoTipoProducto"));
        colCodigoPR.setCellValueFactory(new PropertyValueFactory<Productos, Integer>("codigoProveedor"));
    }

    public void seleccionarElemento() {
        txtCodigoCP.setText(((Productos) tblP.getSelectionModel().getSelectedItem()).getCodigoProducto());
        txtDescripcionP.setText(((Productos) tblP.getSelectionModel().getSelectedItem()).getDescripcionProducto());
        txtPrecioU.setText(String.valueOf(((Productos) tblP.getSelectionModel().getSelectedItem()).getPrecioUnitario()));
        txtPrecioD.setText(String.valueOf(((Productos) tblP.getSelectionModel().getSelectedItem()).getPrecioDocena()));
        txtPrecioM.setText(String.valueOf(((Productos) tblP.getSelectionModel().getSelectedItem()).getPrecioMayor()));
        imgPrueba.setImage(new Image(((Productos) tblP.getSelectionModel().getSelectedItem()).getImagenProducto()));
        txtExistencia.setText(String.valueOf(((Productos) tblP.getSelectionModel().getSelectedItem()).getExistencia()));
        cmbCTP.getSelectionModel().select(buscarTipoProducto(((Productos) tblP.getSelectionModel().getSelectedItem()).getCodigoTipoProducto()));
        cmbCP.getSelectionModel().select(buscarProveedor(((Productos) tblP.getSelectionModel().getSelectedItem()).getCodigoProveedor()));

    }

    public TipoProducto buscarTipoProducto(int codigoTipoProducto) {
        TipoProducto resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_buscarTipoProducto(?)}");
            procedimiento.setInt(1, codigoTipoProducto);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()) {
                resultado = new TipoProducto(registro.getInt("codigoTipoProducto"),
                        registro.getString("descripcion"));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;
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
                        registro.getString("paginaWeb"));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;
    }

    public void agregar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                limpiarControles();
                activarControles();
                btnAgregarP.setText("Guardar");
                btnEliminarP.setText("Cancelar");
                imgAgregar.setImage(new Image("/org/derianhernandez/images/Guardar.png"));
                imgEliminar.setImage(new Image("/org/derianhernandez/images/Eliminar2.png"));
                btnEditarP.setDisable(true);
                btnReportes.setDisable(true);
                tipoDeOperaciones = operaciones.ACTUALIZAR;
                break;
            case ACTUALIZAR:
                guardar();
                desactivarControles();
                limpiarControles();
                btnAgregarP.setText("Agregar");
                btnEliminarP.setText("Eliminar");
                imgAgregar.setImage(new Image("/org/derianhernandez/images/product+.png"));
                imgEliminar.setImage(new Image("/org/derianhernandez/images/product-.png"));
                btnEditarP.setDisable(false);
                btnReportes.setDisable(false);
                tipoDeOperaciones = operaciones.NINGUNO;
                cargarDatos();
                break;
        }

    }

    public void eliminar() {
        switch (tipoDeOperaciones) {
            case ACTUALIZAR:
                desactivarControles();
                btnAgregarP.setText("Agregar");
                btnAgregarP.setText("Agregar");
                btnEliminarP.setText("Eliminar");
                imgAgregar.setImage(new Image("/org/derianhernandez/images/product+.png"));
                imgEliminar.setImage(new Image("/org/derianhernandez/images/product-.png"));
                btnEditarP.setDisable(false);
                btnReportes.setDisable(false);
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
        }
    }

    public void editar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                if (tblP.getSelectionModel().getSelectedItem() != null) {
                    btnEditarP.setText("Actualizar");
                    btnReportes.setText("Cancelar");
                    btnAgregarP.setDisable(true);
                    btnEliminarP.setDisable(true);
                    imgEditar.setImage(new Image("/org/derianhernandez/images/editarCaja.png"));
                    imgReporte.setImage(new Image("/org/derianhernandez/images/Eliminar2.png"));
                    activarControles();
                    txtCodigoCP.setEditable(false);
                    tipoDeOperaciones = ProductosViewController.operaciones.ACTUALIZAR;
                } else {
                    JOptionPane.showMessageDialog(null, "Debe de seleccionar algun elemento");
                }
                break;
            case ACTUALIZAR:
                actualizar();
                desactivarControles();
                limpiarControles();
                btnEditarP.setText("Editar");
                btnReportes.setText("Reportes");
                btnAgregarP.setDisable(false);
                btnEliminarP.setDisable(false);
                imgEditar.setImage(new Image("/org/derianhernandez/images/productDelete.png"));
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
                limpiarControles();
                btnEditarP.setText("Editar");
                btnReportes.setText("Reportes");
                btnAgregarP.setDisable(false);
                btnEliminarP.setDisable(false);
                imgEditar.setImage(new Image("/org/derianhernandez/images/productDelete.png"));
                imgReporte.setImage(new Image("/org/derianhernandez/images/informe-seo.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
        }
    }

    public void actualizar() {
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_editarProducto(?,?,?,?,?,?,?,?,?)}");
            Productos registro = (Productos) tblP.getSelectionModel().getSelectedItem();
            registro.setDescripcionProducto(txtDescripcionP.getText());
            registro.setPrecioDocena(Double.parseDouble(txtPrecioD.getText()));
            registro.setPrecioUnitario(Double.parseDouble(txtPrecioU.getText()));
            registro.setPrecioMayor(Double.parseDouble(txtPrecioM.getText()));
            registro.setImagenProducto(url);
            registro.setExistencia(Integer.parseInt(txtExistencia.getText()));
            registro.setCodigoProveedor(((Proveedores) cmbCP.getSelectionModel().getSelectedItem()).getCodigoProveedor());
            registro.setCodigoTipoProducto(((TipoProducto) cmbCTP.getSelectionModel().getSelectedItem()).getCodigoTipoProducto());
            procedimiento.setString(1, registro.getCodigoProducto());
            procedimiento.setString(2, registro.getDescripcionProducto());
            procedimiento.setDouble(3, registro.getPrecioUnitario());
            procedimiento.setDouble(4, registro.getPrecioDocena());
            procedimiento.setDouble(5, registro.getPrecioMayor());
            procedimiento.setString(6, registro.getImagenProducto());
            procedimiento.setInt(7, registro.getExistencia());
            procedimiento.setInt(8, registro.getCodigoTipoProducto());
            procedimiento.setInt(9, registro.getCodigoProveedor());
            procedimiento.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void desactivarControles() {
        txtCodigoCP.setEditable(false);
        txtDescripcionP.setEditable(false);
        txtPrecioU.setEditable(false);
        txtPrecioD.setEditable(false);
        txtPrecioM.setEditable(false);
        txtExistencia.setEditable(false);
        cmbCTP.setDisable(true);
        cmbCP.setDisable(true);
        imgPrueba.setDisable(true);
    }

    public void activarControles() {
        txtCodigoCP.setEditable(true);
        txtDescripcionP.setEditable(true);
        cmbCTP.setDisable(false);
        cmbCP.setDisable(false);
        imgPrueba.setDisable(false);
    }

    public void limpiarControles() {
        txtCodigoCP.clear();
        txtDescripcionP.clear();
        txtPrecioU.clear();
        txtPrecioD.clear();
        txtPrecioM.clear();
        txtExistencia.clear();
        imgPrueba.setImage(null);
        tblP.getSelectionModel().getSelectedItem();
        cmbCTP.getSelectionModel().getSelectedItem();
        cmbCP.getSelectionModel().getSelectedItem();

    }

    public void guardar() {
        Productos registro = new Productos();
        registro.setCodigoProducto(txtCodigoCP.getText());
        registro.setDescripcionProducto(txtDescripcionP.getText());
        registro.setPrecioDocena(0);
        registro.setPrecioUnitario(0);
        registro.setPrecioMayor(0);
        registro.setImagenProducto(url);
        registro.setExistencia(0);
        registro.setCodigoProveedor(((Proveedores) cmbCP.getSelectionModel().getSelectedItem()).getCodigoProveedor());
        registro.setCodigoTipoProducto(((TipoProducto) cmbCTP.getSelectionModel().getSelectedItem()).getCodigoTipoProducto());

        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarProducto(?, ?, ?, ?, ?, ?, ?, ?,?)}");
            procedimiento.setString(1, registro.getCodigoProducto());
            procedimiento.setString(2, registro.getDescripcionProducto());
            procedimiento.setDouble(3, registro.getPrecioUnitario());
            procedimiento.setDouble(4, registro.getPrecioDocena());
            procedimiento.setDouble(5, registro.getPrecioMayor());
            procedimiento.setString(6, registro.getImagenProducto());
            procedimiento.setInt(7, registro.getExistencia());
            procedimiento.setInt(8, registro.getCodigoTipoProducto());
            procedimiento.setInt(9, registro.getCodigoProveedor());
            procedimiento.execute();

            listaProductos.add(registro);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void clicHome(ActionEvent event) throws IOException {
        if (event.getSource() == btnHome) {
            escenarioPrincipal.menuPrincipalView();
        }
    }

    @FXML
    public void imageViewDragDropped(DragEvent event) throws MalformedURLException {
        Productos p = new Productos();
        Dragboard dg = event.getDragboard();
        if (dg.hasImage() || dg.hasFiles()) {
            try {
                imgPrueba.setImage(new Image(new FileInputStream(dg.getFiles().get(0))));
            } catch (Exception e) {
                e.printStackTrace();
            }
            url = dg.getFiles().get(0).toURI().toURL().toString();
            
        }
    }
    
    @FXML
    public void imageViewDragOver(DragEvent event){
        Dragboard dg = event.getDragboard();
        if(dg.hasImage() || dg.hasFiles()){
            event.acceptTransferModes(TransferMode.COPY);
        }
        
        event.consume(); 
        
    }
}


