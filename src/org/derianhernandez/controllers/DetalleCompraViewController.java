
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
import org.derianhernandez.bean.Compras;
import org.derianhernandez.bean.DetalleCompra;
import org.derianhernandez.bean.Productos;
import org.derianhernandez.db.Conexion;
import org.derianhernandez.system.Main;


public class DetalleCompraViewController implements Initializable {

    
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
    private Main escenarioPrincipal;
    @FXML
    private Button btnHome;
    @FXML
    private Button btnAgregarDC;
    @FXML
    private ImageView imgAgregar;
    @FXML
    private Button btnEliminarDC;
    @FXML
    private ImageView imgEliminar;
    @FXML
    private Button btnEditarDC;
    @FXML
    private ImageView imgEditar;
    @FXML
    private Button btnReporte;
    @FXML
    private ImageView imgReporte;
    @FXML
    private TableColumn colCDC;
    @FXML
    private TableColumn colCU;
    @FXML
    private TableColumn colC;
    @FXML
    private TableColumn colCP;
    @FXML
    private TableColumn colND;
    @FXML
    private TextField txtCDC;
    @FXML
    private TextField txtCU;
    @FXML
    private TextField txtC;
    @FXML
    private ComboBox cmbCP;
    @FXML
    private ComboBox cmbND;
    @FXML
    private TableView tblc;
    private ObservableList<Productos> listaProductos;
    private ObservableList<Compras> listaCompras;
    private ObservableList<DetalleCompra> listaDetalleCompra;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        desactivarControllers();
        cargarDatos();
        cmbCP.setItems(getProducto());
        cmbND.setItems(getCompras());
    }

    public void cargarDatos() {
        tblc.setItems(getDetalleCompra());
        colCDC.setCellValueFactory(new PropertyValueFactory<DetalleCompra, Integer>("codigoDetalleCompra"));
        colCU.setCellValueFactory(new PropertyValueFactory<DetalleCompra, Double>("costoUnitario"));
        colC.setCellValueFactory(new PropertyValueFactory<DetalleCompra, Integer>("cantidad"));
        colCP.setCellValueFactory(new PropertyValueFactory<DetalleCompra, String>("codigoProducto"));
        colND.setCellValueFactory(new PropertyValueFactory<DetalleCompra, Integer>("numeroDocumento"));

    }

    public void seleccionarElementos() {
        txtCDC.setText(String.valueOf(((DetalleCompra) tblc.getSelectionModel().getSelectedItem()).getCodigoDetalleCompra()));
        txtCU.setText(String.valueOf(((DetalleCompra) tblc.getSelectionModel().getSelectedItem()).getCostoUnitario()));
        txtC.setText(String.valueOf(((DetalleCompra) tblc.getSelectionModel().getSelectedItem()).getCantidad()));
        cmbCP.getSelectionModel().select(buscarProducto(((DetalleCompra) tblc.getSelectionModel().getSelectedItem()).getCodigoProducto()));
        cmbND.getSelectionModel().select(buscarCompra(((DetalleCompra) tblc.getSelectionModel().getSelectedItem()).getNumeroDocumento()));
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
                        registro.getInt("codigoProveedor")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultado;
    }

    public Compras buscarCompra(int numeroDocumento) {
        Compras resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarCompras(?)}");
            procedimiento.setInt(1, numeroDocumento);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()) {
                resultado = new Compras(registro.getInt("numeroDocumento"),
                        registro.getString("fechaDocumento"),
                        registro.getString("descripcion"),
                        registro.getDouble("totalDocumento")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;
    }

    public ObservableList<DetalleCompra> getDetalleCompra() {
        ArrayList<DetalleCompra> lista = new ArrayList();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarDetalleCompra()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new DetalleCompra(resultado.getInt("codigoDetalleCompra"),
                        resultado.getDouble("costoUnitario"),
                        resultado.getInt("cantidad"),
                        resultado.getString("codigoProducto"),
                        resultado.getInt("numeroDocumento")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaDetalleCompra = FXCollections.observableArrayList(lista);
    }

    public ObservableList<Productos> getProducto() {
        ArrayList<Productos> lista = new ArrayList();
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

    public ObservableList<Compras> getCompras() {
        ArrayList<Compras> lista = new ArrayList();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarCompras()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new Compras(resultado.getInt("numeroDocumento"),
                        resultado.getString("fechaDocumento"),
                        resultado.getString("descripcion"),
                        resultado.getDouble("totalDocumento")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaCompras = FXCollections.observableArrayList(lista);
    }

    public void agregar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                activarControllers();
                limpiarControllers();
                btnAgregarDC.setText("Guardar");
                btnEliminarDC.setText("Cancelar");
                imgAgregar.setImage(new Image("/org/derianhernandez/images/Guardar.png"));
                imgEliminar.setImage(new Image("/org/derianhernandez/images/Eliminar2.png"));
                btnEditarDC.setDisable(true);
                btnReporte.setDisable(true);
                tipoDeOperaciones = operaciones.ACTUALIZAR;
                break;
            case ACTUALIZAR:
                guardar();
                desactivarControllers();
                limpiarControllers();
                btnAgregarDC.setText("Agregar");
                btnEliminarDC.setText("Eliminar");
                imgAgregar.setImage(new Image("org/derianhernandez/images/buy+.png"));
                imgEliminar.setImage(new Image("org/derianhernandez/images/buy-.png"));
                btnEditarDC.setDisable(false);
                btnReporte.setDisable(false);
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
                btnAgregarDC.setText("Agregar");
                btnEliminarDC.setText("Eliminar");
                btnEditarDC.setDisable(false);
                btnReporte.setDisable(false);
                imgAgregar.setImage(new Image("/org/derianhernandez/images/buy+.png"));
                imgEliminar.setImage(new Image("/org/derianhernandez/images/buy-.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
            default:
                if (tblc.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confirmar si elimina registro",
                            "Eliminar Detalle Compra", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarDetalleCompra(?)}");
                            procedimiento.setInt(1, ((DetalleCompra) tblc.getSelectionModel().getSelectedItem()).getCodigoDetalleCompra());
                            procedimiento.execute();
                            listaDetalleCompra.remove(tblc.getSelectionModel().getSelectedItem());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Debe Seleccionar un Elemento");
                }
        }
    }

    public void editar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                if (tblc.getSelectionModel().getSelectedItem() != null) {
                    btnEditarDC.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnAgregarDC.setDisable(true);
                    btnEliminarDC.setDisable(true);
                    imgReporte.setImage(new Image("/org/derianhernandez/images/Eliminar2.png"));
                    activarControllers();
                    txtCDC.setEditable(false);
                    tipoDeOperaciones = operaciones.ACTUALIZAR;
                } else {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar algun elemento");
                }
                break;
            case ACTUALIZAR:
                actualizar();
                desactivarControllers();
                limpiarControllers();
                btnEditarDC.setText("Editar");
                btnReporte.setText("Reportes");
                btnAgregarDC.setDisable(false);
                btnEliminarDC.setDisable(false);
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
                btnEditarDC.setText("Editar");
                btnReporte.setText("Reporte");
                btnAgregarDC.setDisable(false);
                btnEliminarDC.setDisable(false);
                imgEditar.setImage(new Image("/org/derianhernandez/images/buyEdit.png"));
                imgReporte.setImage(new Image("/org/derianhernandez/images/informe-seo.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
        }
    }

    public void guardar() {
        DetalleCompra registro = new DetalleCompra();
        registro.setCodigoDetalleCompra(Integer.parseInt(txtCDC.getText()));
        registro.setCostoUnitario(Double.parseDouble(txtCU.getText()));
        registro.setCantidad(Integer.parseInt(txtC.getText()));
        registro.setCodigoProducto(((Productos) cmbCP.getSelectionModel().getSelectedItem()).getCodigoProducto());
        registro.setNumeroDocumento(((Compras) cmbND.getSelectionModel().getSelectedItem()).getNumeroDocumento());
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarDetalleCompras(?, ?, ?, ?, ?)}");
            procedimiento.setInt(1, registro.getCodigoDetalleCompra());
            procedimiento.setDouble(2, registro.getCostoUnitario());
            procedimiento.setInt(3, registro.getCantidad());
            procedimiento.setString(4, registro.getCodigoProducto());
            procedimiento.setInt(5, registro.getNumeroDocumento());
            procedimiento.execute();
            listaDetalleCompra.add(registro);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void actualizar() {
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarDetalleCompra(?,?,?,?,?)}");
            DetalleCompra registro = (DetalleCompra) tblc.getSelectionModel().getSelectedItem();
            registro.setCodigoDetalleCompra(Integer.parseInt(txtCDC.getText()));
            registro.setCostoUnitario(Double.parseDouble(txtCU.getText()));
            registro.setCantidad(Integer.parseInt(txtC.getText()));
            registro.setCodigoProducto(((Productos) cmbCP.getSelectionModel().getSelectedItem()).getCodigoProducto());
            registro.setNumeroDocumento(((Compras) cmbND.getSelectionModel().getSelectedItem()).getNumeroDocumento());
            procedimiento.setInt(1, registro.getCodigoDetalleCompra());
            procedimiento.setDouble(2, registro.getCostoUnitario());
            procedimiento.setInt(3, registro.getCantidad());
            procedimiento.setString(4, registro.getCodigoProducto());
            procedimiento.setInt(5, registro.getNumeroDocumento());
            procedimiento.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void desactivarControllers() {
        txtCDC.setEditable(false);
        txtCU.setEditable(false);
        txtC.setEditable(false);
        cmbCP.setDisable(true);
        cmbND.setDisable(true);
    }

    public void activarControllers() {
        txtCDC.setEditable(true);
        txtCU.setEditable(true);
        txtC.setEditable(true);
        cmbCP.setDisable(false);
        cmbND.setDisable(false);
    }

    public void limpiarControllers() {
        txtCDC.clear();
        txtCU.clear();
        txtC.clear();
        tblc.getSelectionModel().getSelectedItem();
        cmbCP.getSelectionModel().getSelectedItem();
        cmbND.getSelectionModel().getSelectedItem();

    }

    

    @FXML
    public void clicHome(ActionEvent event) {
        if (event.getSource() == btnHome) {
            escenarioPrincipal.menuPrincipalView();
        }
    }
}