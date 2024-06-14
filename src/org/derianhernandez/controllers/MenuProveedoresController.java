package org.derianhernandez.controllers;

import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.derianhernandez.bean.Proveedores;
import org.derianhernandez.db.Conexion;
import org.derianhernandez.reportes.GenerarReportes;
import org.derianhernandez.system.Main;

/* Herencia Multiple concepto, interfaces. POO
 */
public class MenuProveedoresController implements Initializable {

    private ObservableList<Proveedores> listaProveedores;
    @FXML
    private Button btnMenu;
    @FXML
    private TextField txtCodigoProveedor;
    @FXML
    private TextField txtNombreProveedor;
    @FXML
    private TextField txtApellidoProveedor;
    @FXML
    private TextField txtDireccionProveedor;
    @FXML
    private TextField txtRazonSocialProveedor;
    @FXML
    private TextField txtPaginaWebProveedor;
    @FXML
    private TextField txtNitProveedor;
    @FXML
    private TextField txtContactoProveedor;
    @FXML
    private Button btnAgregarProveedor;
    @FXML
    private Button btnEliminarProveedor;
    @FXML
    private Button btnEditarProveedor;
    @FXML
    private Button btnReporte;
    @FXML
    private TableView tblPr;
    @FXML
    private TableColumn colCodigoProveedor;
    @FXML
    private TableColumn colNitProveedor;
    @FXML
    private TableColumn colNombreProveedor;
    @FXML
    private TableColumn colApellidoProveedor;
    @FXML
    private TableColumn colDireccionProveedor;
    @FXML
    private TableColumn colRazonSocialProveedor;
    @FXML
    private TableColumn colContactoPrincipalProveedor;
    @FXML
    private TableColumn colPaginaWebProveedor;
    @FXML
    private ImageView imgAgregar;
    @FXML
    private ImageView imgEliminar;
    @FXML
    private ImageView imgEditar;
    @FXML
    private ImageView imgReporte;
    private Main escenarioPrincipal;

    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    }

    private MenuProveedoresController.operaciones tipoDeOperaciones = MenuProveedoresController.operaciones.NINGUNO;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
        desactivarControles();
    }

    public void cargarDatos() {
        tblPr.setItems(getProveedores());
        colCodigoProveedor.setCellValueFactory(new PropertyValueFactory<Proveedores, Integer>("codigoProveedor"));
        colNitProveedor.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("nitProveedor"));
        colNombreProveedor.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("nombreProveedor"));
        colApellidoProveedor.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("apellidoProveedor"));
        colDireccionProveedor.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("direccionProveedor"));
        colRazonSocialProveedor.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("razonSocial"));
        colContactoPrincipalProveedor.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("contactoPrincipal"));
        colPaginaWebProveedor.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("paginaWeb"));
    }

    public void seleccionarElemento() {
        txtCodigoProveedor.setText(String.valueOf(((Proveedores) tblPr.getSelectionModel().getSelectedItem()).getCodigoProveedor()));
        txtNitProveedor.setText(((Proveedores) tblPr.getSelectionModel().getSelectedItem()).getNitProveedor());
        txtNombreProveedor.setText(((Proveedores) (tblPr.getSelectionModel().getSelectedItem())).getNombreProveedor());
        txtApellidoProveedor.setText(((Proveedores) tblPr.getSelectionModel().getSelectedItem()).getApellidoProveedor());
        txtDireccionProveedor.setText(((Proveedores) tblPr.getSelectionModel().getSelectedItem()).getDireccionProveedor());
        txtRazonSocialProveedor.setText(((Proveedores) tblPr.getSelectionModel().getSelectedItem()).getRazonSocial());
        txtContactoProveedor.setText(((Proveedores) tblPr.getSelectionModel().getSelectedItem()).getContactoPrincipal());
        txtPaginaWebProveedor.setText(((Proveedores) tblPr.getSelectionModel().getSelectedItem()).getPaginaWeb());

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

    public void agregar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                limpiarControles();
                activarControles();
                btnAgregarProveedor.setText("Guardar");
                btnEliminarProveedor.setText("Cancelar");
                btnEditarProveedor.setDisable(true);
                btnReporte.setDisable(true);
                imgAgregar.setImage(new Image("/org/derianhernandez/images/Guardar.png"));
                imgEliminar.setImage(new Image("/org/derianhernandez/images/Eliminar2.png"));
                tipoDeOperaciones = MenuProveedoresController.operaciones.ACTUALIZAR;
                break;
            case ACTUALIZAR:
                guardar();
                desactivarControles();
                limpiarControles();
                btnAgregarProveedor.setText("Agregar");
                btnEliminarProveedor.setText("Eliminar");
                btnEditarProveedor.setDisable(false);
                btnReporte.setDisable(false);
                imgAgregar.setImage(new Image("/org/derianhernandez/images/box+.png"));
                imgEliminar.setImage(new Image("/org/derianhernandez/images/removeBox.png"));
                tipoDeOperaciones = MenuProveedoresController.operaciones.NINGUNO;
                break;
        }
    }

    public void eliminar() {
        switch (tipoDeOperaciones) {
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnAgregarProveedor.setText("Agregar");
                btnEliminarProveedor.setText("Eliminar");
                btnEditarProveedor.setDisable(false);
                btnReporte.setDisable(false);
                imgAgregar.setImage(new Image("/org/derianhernandez/images/box+.png"));
                imgEliminar.setImage(new Image("/org/derianhernandez/images/removeBox.png"));
                tipoDeOperaciones = MenuProveedoresController.operaciones.NINGUNO;
                break;

            default:
                if (tblPr.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confirmar si elimina el registro",
                            "Eliminar Proveedor", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarProveedor(?)}");
                            procedimiento.setInt(1, ((Proveedores) tblPr.getSelectionModel().getSelectedItem()).getCodigoProveedor());
                            procedimiento.execute();
                            listaProveedores.remove(tblPr.getSelectionModel().getSelectedItem());
                            desactivarControles();
                            limpiarControles();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Debe de seleccionar un elemento");

                }
                break;
        }
    }
    
    public void editar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                if (tblPr.getSelectionModel().getSelectedItem() != null) {
                    btnEditarProveedor.setText("Actualizar");
                btnReporte.setText("Cancelar");
                    btnAgregarProveedor.setDisable(true);
                   btnEliminarProveedor.setDisable(true);
                    imgEditar.setImage(new Image("/org/derianhernandez/images/editarCaja.png"));
                    imgReporte.setImage(new Image("/org/derianhernandez/images/Eliminar2.png"));
                    activarControles();
                    txtCodigoProveedor.setEditable(false);
                    tipoDeOperaciones = operaciones.ACTUALIZAR;
                } else {
                    JOptionPane.showMessageDialog(null, "Debe de seleccionar algun elemento");
                }
                break;
            case ACTUALIZAR:
                actualizar();
                desactivarControles();
                limpiarControles();
                btnEditarProveedor.setText("Editar");
                btnReporte.setText("Reportes");
                btnAgregarProveedor.setDisable(false);
                btnEliminarProveedor.setDisable(false);
                imgEditar.setImage(new Image("/org/derianhernandez/images/boxEdit.png"));
                imgReporte.setImage(new Image("/org/derianhernandez/images/informe-seo.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
                cargarDatos();
                break;
        }
    }
    
    public void reporte() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                imprimirReportes();
                break;
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnEditarProveedor.setText("Editar");
                btnReporte.setText("Reporte");
                btnAgregarProveedor.setDisable(false);
                btnEliminarProveedor.setDisable(false);
                imgEditar.setImage(new Image("/org/derianhernandez/images/boxEdit.png"));
                imgReporte.setImage(new Image("/org/derianhernandez/images/informe-seo.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
        }
    }

    public Main getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    @FXML
    private void clicMenu(ActionEvent event) throws IOException {
        if (event.getSource() == btnMenu) {
            escenarioPrincipal.menuPrincipalView();

        }
    }

    public void guardar() {
        Proveedores registro = new Proveedores();
        registro.setCodigoProveedor(Integer.parseInt(txtCodigoProveedor.getText()));
        registro.setNitProveedor(txtNitProveedor.getText());
        registro.setNombreProveedor(txtNombreProveedor.getText());
        registro.setApellidoProveedor(txtApellidoProveedor.getText());
        registro.setDireccionProveedor(txtDireccionProveedor.getText());
        registro.setRazonSocial(txtRazonSocialProveedor.getText());
        registro.setContactoPrincipal(txtContactoProveedor.getText());
        registro.setPaginaWeb(txtPaginaWebProveedor.getText());
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarProveedores(?,?,?,?,?,?,?,?)}");
            procedimiento.setInt(1, registro.getCodigoProveedor());
            procedimiento.setString(4, registro.getNitProveedor());
            procedimiento.setString(2, registro.getNombreProveedor());
            procedimiento.setString(3, registro.getApellidoProveedor());
            procedimiento.setString(5, registro.getDireccionProveedor());
            procedimiento.setString(6, registro.getRazonSocial());
            procedimiento.setString(7, registro.getContactoPrincipal());
            procedimiento.setString(8, registro.getPaginaWeb());
            procedimiento.execute();
            listaProveedores.add(registro);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void actualizar() {
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarProveedores(?,?,?,?,?,?,?,?)}");
            Proveedores registro = (Proveedores) tblPr.getSelectionModel().getSelectedItem();
            registro.setNitProveedor(txtNitProveedor.getText());
            registro.setNombreProveedor(txtNombreProveedor.getText());
            registro.setApellidoProveedor(txtApellidoProveedor.getText());
            registro.setDireccionProveedor(txtDireccionProveedor.getText());
            registro.setRazonSocial(txtRazonSocialProveedor.getText());
            registro.setContactoPrincipal(txtContactoProveedor.getText());
            registro.setPaginaWeb(txtPaginaWebProveedor.getText());
             procedimiento.setInt(1, registro.getCodigoProveedor());
            procedimiento.setString(2, registro.getNitProveedor());
            procedimiento.setString(3, registro.getNombreProveedor());
            procedimiento.setString(4, registro.getApellidoProveedor());
            procedimiento.setString(5, registro.getDireccionProveedor());
            procedimiento.setString(6, registro.getRazonSocial());
            procedimiento.setString(7, registro.getContactoPrincipal());
            procedimiento.setString(8, registro.getPaginaWeb());
            procedimiento.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void desactivarControles() {
        txtCodigoProveedor.setEditable(false);
        txtNitProveedor.setEditable(false);
        txtNombreProveedor.setEditable(false);
        txtApellidoProveedor.setEditable(false);
        txtDireccionProveedor.setEditable(false);
        txtRazonSocialProveedor.setEditable(false);
        txtContactoProveedor.setEditable(false);
        txtPaginaWebProveedor.setEditable(false);
    }

    public void activarControles() {
        txtCodigoProveedor.setEditable(true);
        txtNitProveedor.setEditable(true);
        txtNombreProveedor.setEditable(true);
        txtApellidoProveedor.setEditable(true);
        txtDireccionProveedor.setEditable(true);
        txtRazonSocialProveedor.setEditable(true);
        txtContactoProveedor.setEditable(true);
        txtPaginaWebProveedor.setEditable(true);
    }

    public void limpiarControles() {
        txtCodigoProveedor.clear();
        txtNitProveedor.clear();
        txtNombreProveedor.clear();
        txtApellidoProveedor.clear();
        txtDireccionProveedor.clear();
        txtRazonSocialProveedor.clear();
        txtContactoProveedor.clear();
        txtPaginaWebProveedor.clear();
    }

    public void imprimirReportes() {
        Map parametros = new HashMap();
        parametros.put("codigoProveedor",null);
        GenerarReportes.mostrarReporte("ReporteProveedores.jasper", "Proveedores", parametros);

    }
}
