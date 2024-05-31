/*
En esta clase ams que todo hacemos la herencia multiple y constructores
Se hace el boton para regresar al menu principal
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
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.derianhernandez.bean.Clientes;
import org.derianhernandez.db.Conexion;
import org.derianhernandez.system.Main;

/* Herencia Multiple concepto, interfaces. POO
 */
public class MenuClientesController implements Initializable {

    private Main escenarioPrincipal;
    private ObservableList<Clientes> listaClientes;
    @FXML
    private Button btnRegresar;
    @FXML
    private Button btnAgregarClientes;
    @FXML
    private Button btnEliminarClientes;
    @FXML
    private Button btnEditarClientes;
    @FXML
    private Button btnReportes;
    @FXML
    private TextField txtDireccionC;
    @FXML
    private TextField txtNitC;
    @FXML
    private TextField txtNombreC;
    @FXML
    private TextField txtApellidosC;
    @FXML
    private TextField txtCorreoC;
    @FXML
    private TextField txtCodigoC;
    @FXML
    private TextField txtTelefonoC;
    @FXML
    private TableView tblCl;
    @FXML
    private TableColumn colCodigoC;
    @FXML
    private TableColumn colNitC;
    @FXML
    private TableColumn colNombreC;
    @FXML
    private TableColumn colApellidosC;
    @FXML
    private TableColumn colCorreoC;
    @FXML
    private TableColumn colDireccionC;
    @FXML
    private TableColumn colTelefonoC;
    @FXML
    private ImageView imgAgregar;
    @FXML
    private ImageView imgEliminar;
    @FXML
    private ImageView imgEditar;
    @FXML
    private ImageView imgReporte;

    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
        desactivarControles();
    }

    public Main getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void cargarDatos() {
        tblCl.setItems(getClientes());
        colCodigoC.setCellValueFactory(new PropertyValueFactory<Clientes, Integer>("codigoCliente"));
        colNitC.setCellValueFactory(new PropertyValueFactory<Clientes, String>("nitCliente"));
        colNombreC.setCellValueFactory(new PropertyValueFactory<Clientes, String>("nombreCliente"));
        colApellidosC.setCellValueFactory(new PropertyValueFactory<Clientes, String>("apellidoCliente"));
        colDireccionC.setCellValueFactory(new PropertyValueFactory<Clientes, String>("direccionCliente"));
        colTelefonoC.setCellValueFactory(new PropertyValueFactory<Clientes, String>("telefonoCliente"));
        colCorreoC.setCellValueFactory(new PropertyValueFactory<Clientes, String>("correoCliente"));
    }

    public void seleccionarElemento() {
        txtCodigoC.setText(String.valueOf(((Clientes) tblCl.getSelectionModel().getSelectedItem()).getCodigoCliente()));
        txtNitC.setText(((Clientes) tblCl.getSelectionModel().getSelectedItem()).getNitCliente());
        txtNombreC.setText(((Clientes) (tblCl.getSelectionModel().getSelectedItem())).getNombreCliente());
        txtApellidosC.setText(((Clientes) tblCl.getSelectionModel().getSelectedItem()).getApellidoCliente());
        txtDireccionC.setText(((Clientes) tblCl.getSelectionModel().getSelectedItem()).getDireccionCliente());
        txtTelefonoC.setText(((Clientes) tblCl.getSelectionModel().getSelectedItem()).getTelefonoCliente());
        txtCorreoC.setText(((Clientes) tblCl.getSelectionModel().getSelectedItem()).getCorreoCliente());

    }

    public ObservableList<Clientes> getClientes() {
        ArrayList<Clientes> lista = new ArrayList();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarClientes()}");
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

    public void agregar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                activarControles();
                limpiarControles();
                btnAgregarClientes.setText("Guardar");
                btnEliminarClientes.setText("Cancelar");
                btnEditarClientes.setDisable(true);
                btnReportes.setDisable(true);
                imgAgregar.setImage(new Image("/org/derianhernandez/images/Guardar.png"));
                imgEliminar.setImage(new Image("/org/derianhernandez/images/Eliminar2.png"));
                tipoDeOperaciones = operaciones.ACTUALIZAR;
                break;
            case ACTUALIZAR:
                if (txtCorreoC.getText().contains("@")) {
                    guardar();
                    desactivarControles();
                    limpiarControles();
                    btnAgregarClientes.setText("Agregar");
                    btnEliminarClientes.setText("Eliminar");
                    btnEditarClientes.setDisable(false);
                    btnReportes.setDisable(false);
                    imgAgregar.setImage(new Image("/org/derianhernandez/images/agregar-usuario.png"));
                    imgEliminar.setImage(new Image("/org/derianhernandez/images/quitar-usuario.png"));
                    tipoDeOperaciones = operaciones.NINGUNO;
                } else {
                    JOptionPane.showMessageDialog(null, "Formato de correo incorrecto, no contiene @");
                }

                break;
        }
    }

    public void eliminar() {
        switch (tipoDeOperaciones) {
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnAgregarClientes.setText("Agregar");
                btnEliminarClientes.setText("Eliminar");
                btnEditarClientes.setDisable(false);
                btnReportes.setDisable(false);
                imgAgregar.setImage(new Image("/org/derianhernandez/images/agregar-usuario.png"));
                imgEliminar.setImage(new Image("/org/derianhernandez/images/quitar-usuario.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
                break;

            default:
                if (tblCl.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confirmar si elimina el registro",
                            "Eliminar Clientes", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarClientes(?)}");
                            procedimiento.setInt(1, ((Clientes) tblCl.getSelectionModel().getSelectedItem()).getCodigoCliente());
                            procedimiento.execute();
                            listaClientes.remove(tblCl.getSelectionModel().getSelectedItem());
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
                if (tblCl.getSelectionModel().getSelectedItem() != null) {
                    btnEditarClientes.setText("Actualizar");
                    btnReportes.setText("Cancelar");
                    btnAgregarClientes.setDisable(true);
                    btnEliminarClientes.setDisable(true);
                    imgEditar.setImage(new Image("/org/derianhernandez/images/editarUsuario.png"));
                    imgReporte.setImage(new Image("/org/derianhernandez/images/Eliminar2.png"));
                    activarControles();
                    txtCodigoC.setEditable(false);
                    tipoDeOperaciones = operaciones.ACTUALIZAR;
                } else {
                    JOptionPane.showMessageDialog(null, "Debe de seleccionar algun elemento");
                }
                break;
            case ACTUALIZAR:
                if (txtCorreoC.getText().contains("@")) {
                    actualizar();
                    desactivarControles();
                    limpiarControles();
                    btnEditarClientes.setText("Editar");
                    btnReportes.setText("Reportes");
                    btnAgregarClientes.setDisable(false);
                    btnEliminarClientes.setDisable(false);
                    imgEditar.setImage(new Image("/org/derianhernandez/images/editar-perfil.png"));
                    imgReporte.setImage(new Image("/org/derianhernandez/images/informe-seo.png"));
                    tipoDeOperaciones = operaciones.NINGUNO;
                    cargarDatos();
                } else {
                    JOptionPane.showMessageDialog(null, "Formato de correo incorrecto, no contiene @");
                }

                break;
        }
    }

    public void actualizar() {
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarClientes(?,?,?,?,?,?,?)}");
            Clientes registro = (Clientes) tblCl.getSelectionModel().getSelectedItem();
            registro.setNitCliente(txtNitC.getText());
            registro.setNombreCliente(txtNombreC.getText());
            registro.setApellidoCliente(txtApellidosC.getText());
            registro.setDireccionCliente(txtDireccionC.getText());
            registro.setTelefonoCliente(txtTelefonoC.getText());
            registro.setCorreoCliente(txtCorreoC.getText());
            procedimiento.setInt(1, registro.getCodigoCliente());
            procedimiento.setString(2, registro.getNitCliente());
            procedimiento.setString(3, registro.getNombreCliente());
            procedimiento.setString(4, registro.getApellidoCliente());
            procedimiento.setString(5, registro.getDireccionCliente());
            procedimiento.setString(6, registro.getTelefonoCliente());
            procedimiento.setString(7, registro.getCorreoCliente());
            procedimiento.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void guardar() {

        Clientes registro = new Clientes();
        registro.setCodigoCliente(Integer.parseInt(txtCodigoC.getText()));
        registro.setNombreCliente(txtNombreC.getText());
        registro.setApellidoCliente(txtApellidosC.getText());
        registro.setNitCliente(txtNitC.getText());
        registro.setTelefonoCliente(txtTelefonoC.getText());
        registro.setDireccionCliente(txtDireccionC.getText());
        registro.setCorreoCliente(txtCorreoC.getText());
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarClientes(?,?,?,?,?,?,?)}");
            procedimiento.setInt(1, registro.getCodigoCliente());
            procedimiento.setString(4, registro.getNitCliente());
            procedimiento.setString(2, registro.getNombreCliente());
            procedimiento.setString(3, registro.getApellidoCliente());
            procedimiento.setString(5, registro.getTelefonoCliente());
            procedimiento.setString(6, registro.getDireccionCliente());
            procedimiento.setString(7, registro.getCorreoCliente());
            procedimiento.execute();
            listaClientes.add(registro);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reporte() {
        switch (tipoDeOperaciones) {
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnEditarClientes.setText("Editar");
                btnReportes.setText("Reporte");
                btnAgregarClientes.setDisable(false);
                btnEliminarClientes.setDisable(false);
                imgEditar.setImage(new Image("/org/derianhernandez/images/editar-perfil.png"));
                imgReporte.setImage(new Image("/org/derianhernandez/images/informe-seo.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
        }
    }

    public void desactivarControles() {
        txtCodigoC.setEditable(false);
        txtCorreoC.setEditable(false);
        txtTelefonoC.setEditable(false);
        txtNombreC.setEditable(false);
        txtApellidosC.setEditable(false);
        txtDireccionC.setEditable(false);
        txtNitC.setEditable(false);

    }

    public void activarControles() {
        txtCodigoC.setEditable(true);
        txtCorreoC.setEditable(true);
        txtTelefonoC.setEditable(true);
        txtNombreC.setEditable(true);
        txtApellidosC.setEditable(true);
        txtDireccionC.setEditable(true);
        txtNitC.setEditable(true);
    }

    public void limpiarControles() {
        txtCodigoC.clear();
        txtCorreoC.clear();
        txtTelefonoC.clear();
        txtNombreC.clear();
        txtApellidosC.clear();
        txtDireccionC.clear();
        txtNitC.clear();
    }

    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    @FXML
    private void clicRegresar(ActionEvent event) throws IOException {
        if (event.getSource() == btnRegresar) {
            escenarioPrincipal.menuPrincipalView();

        }
    }

}
