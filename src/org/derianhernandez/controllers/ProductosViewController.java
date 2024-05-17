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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargaDatos();
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

    public void cargaDatos() {
        tblP.setItems(getProducto());
        colCodigoP.setCellValueFactory(new PropertyValueFactory<Productos, String>("codigoProducto"));
        colDescripcionP.setCellValueFactory(new PropertyValueFactory<Productos, String>("descripcionProducto"));
        colPrecioU.setCellValueFactory(new PropertyValueFactory<Productos, Double>("precioUnitario"));
        colPrecioD.setCellValueFactory(new PropertyValueFactory<Productos, Double>("precioDocena"));
        colPrecioM.setCellValueFactory(new PropertyValueFactory<Productos, Double>("precioMayor"));
        colExistenciaP.setCellValueFactory(new PropertyValueFactory<Productos, Integer>("existencia"));
        colCodigoTP.setCellValueFactory(new PropertyValueFactory<Productos, Integer>("codigoTipoProducto"));
        colCodigoPR.setCellValueFactory(new PropertyValueFactory<Productos, Integer>("codigoProveedor"));
    }
    
    public void seleccionarElemento(){
        txtCodigoCP.setText(((Productos)tblP.getSelectionModel().getSelectedItem()).getCodigoProducto());
        txtDescripcionP.setText(((Productos)tblP.getSelectionModel().getSelectedItem()).getDescripcionProducto());
        txtPrecioU.setText(String.valueOf(((Productos)tblP.getSelectionModel().getSelectedItem()).getPrecioUnitario()));
        txtPrecioD.setText(String.valueOf(((Productos)tblP.getSelectionModel().getSelectedItem()).getPrecioDocena()));
        txtPrecioM.setText(String.valueOf(((Productos)tblP.getSelectionModel().getSelectedItem()).getPrecioMayor()));
        txtExistencia.setText(String.valueOf(((Productos)tblP.getSelectionModel().getSelectedItem()).getExistencia()));
        cmbCTP.getSelectionModel().select(buscarTipoProducto(((Productos)tblP.getSelectionModel().getSelectedItem()).getCodigoTipoProducto()));
        
    }
    
    public TipoProducto buscarTipoProducto (int codigoTipoProducto){
        TipoProducto resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_buscarTipoProducto(?)}");
            procedimiento.setInt(1, codigoTipoProducto);
            ResultSet registro = procedimiento.executeQuery();
            while(registro.next()){
                resultado = new TipoProducto(registro.getInt("codigoTipoProducto"),
                                             registro.getString("descripcion"));
                                            
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return resultado;
    }
    
    public Proveedores buscarProveedor(int codigoProveedor){
        Proveedores resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarProveedor()}");
        }
    }catch(){
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

    }

    public void activarControles() {
        txtCodigoCP.setEditable(true);
        txtDescripcionP.setEditable(true);
        txtPrecioU.setEditable(true);
        txtPrecioD.setEditable(true);
        txtPrecioM.setEditable(true);
        txtExistencia.setEditable(true);
        cmbCTP.setDisable(false);
        cmbCP.setDisable(false);

    }

    public void limpiarControles() {
        txtCodigoCP.clear();
        txtDescripcionP.clear();
        txtPrecioU.clear();
        txtPrecioD.clear();
        txtPrecioM.clear();
        txtExistencia.clear();
        tblP.getSelectionModel().getSelectedItem();
        cmbCTP.getSelectionModel().getSelectedItem();
        cmbCP.getSelectionModel().getSelectedItem();

    }

}
