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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javax.swing.JOptionPane;
import org.derianhernandez.bean.CargoEmpleado;
import org.derianhernandez.bean.Clientes;
import org.derianhernandez.bean.Compras;
import org.derianhernandez.db.Conexion;
import org.derianhernandez.system.Main;

public class CargoEmpleadoController implements Initializable {

    private ObservableList<CargoEmpleado> listaCargoEmpleado;
    @FXML
    private Button btnHome5;
    @FXML
    private Button btnAgregarCE;
    @FXML
    private Button btnEliminarCE;
    @FXML
    private Button btnReporteCE;
    @FXML
    private ImageView imgAgregar;
    @FXML
    private ImageView imgEliminar;
    @FXML
    private ImageView imgEditar;
    @FXML
    private ImageView imgReporte;
    @FXML
    private Button btnEditarCE;
    @FXML
    private TextField txtCodigoCE;
    @FXML
    private TextField txtNombreCE;
    @FXML
    private TextField txtDescripcionCE;
    @FXML
    private TableView tblCE;
    @FXML
    private TableColumn colCodigoCE;
    @FXML
    private TableColumn colNombreCE;
    @FXML
    private TableColumn colDescripcionCE;

    private Main escenarioPrincipal;

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

    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    public void cargarDatos() {
        tblCE.setItems(getCargoEmpleado());
        colCodigoCE.setCellValueFactory(new PropertyValueFactory<CargoEmpleado, Integer>("codigoCargoEmpleado"));
        colNombreCE.setCellValueFactory(new PropertyValueFactory<CargoEmpleado, String>("nombreCargo"));
        colDescripcionCE.setCellValueFactory(new PropertyValueFactory<CargoEmpleado, String>("descripcionCargo"));
    }

    public void seleccionarElemento() {
        txtCodigoCE.setText(String.valueOf(((CargoEmpleado) tblCE.getSelectionModel().getSelectedItem()).getCodigoCargoEmpleado()));
        txtNombreCE.setText(((CargoEmpleado) tblCE.getSelectionModel().getSelectedItem()).getNombreCargo());
        txtDescripcionCE.setText(((CargoEmpleado) (tblCE.getSelectionModel().getSelectedItem())).getDescripcionCargo());
    }

    public ObservableList<CargoEmpleado> getCargoEmpleado() {
        ArrayList<CargoEmpleado> lista = new ArrayList();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarCargoEmpleado()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new CargoEmpleado(resultado.getInt("codigoCargoEmpleado"),
                        resultado.getString("nombreCargo"),
                        resultado.getString("descripcionCargo")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaCargoEmpleado = FXCollections.observableArrayList(lista);
    }

    public void agregar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                limpiarControles();
                activarControles();
                btnAgregarCE.setText("Guardar");
                btnEliminarCE.setText("Cancelar");
                btnEditarCE.setDisable(true);
                btnReporteCE.setDisable(true);
                imgAgregar.setImage(new Image("/org/derianhernandez/images/Guardar.png"));
                imgEliminar.setImage(new Image("/org/derianhernandez/images/Eliminar2.png"));
                tipoDeOperaciones = CargoEmpleadoController.operaciones.ACTUALIZAR;
                break;
            case ACTUALIZAR:
                guardar();
                desactivarControles();
                limpiarControles();
                btnAgregarCE.setText("Agregar");
                btnEliminarCE.setText("Eliminar");
                btnEditarCE.setDisable(false);
                btnReporteCE.setDisable(false);
                imgAgregar.setImage(new Image("/org/derianhernandez/images/buy+.png"));
                imgEliminar.setImage(new Image("/org/derianhernandez/images/buy-t.png"));
                tipoDeOperaciones = CargoEmpleadoController.operaciones.NINGUNO;
                break;
        }
    }

    public void editar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                if (tblCE.getSelectionModel().getSelectedItem() != null) {
                    btnEditarCE.setText("Actualizar");
                    btnReporteCE.setText("Cancelar");
                    btnAgregarCE.setDisable(true);
                    btnEliminarCE.setDisable(true);
                    imgEditar.setImage(new Image("/org/derianhernandez/images/editarUsuario.png"));
                    imgReporte.setImage(new Image("/org/derianhernandez/images/Eliminar2.png"));
                    activarControles();
                    txtCodigoCE.setEditable(false);
                    tipoDeOperaciones = CargoEmpleadoController.operaciones.ACTUALIZAR;
                } else {
                    JOptionPane.showMessageDialog(null, "Debe de seleccionar algun elemento");
                }
                break;
            case ACTUALIZAR:
                actualizar();
                desactivarControles();
                limpiarControles();
                btnEditarCE.setText("Editar");
                btnReporteCE.setText("Reportes");
                btnAgregarCE.setDisable(false);
                btnEliminarCE.setDisable(false);
                imgEditar.setImage(new Image("/org/derianhernandez/images/editar-perfil.png"));
                imgReporte.setImage(new Image("/org/derianhernandez/images/informe-seo.png"));
                tipoDeOperaciones = CargoEmpleadoController.operaciones.NINGUNO;
                cargarDatos();
                break;
        }
    }

    @FXML
    private void clicHome5(ActionEvent event) throws IOException {
        if (event.getSource() == btnHome5) {
            escenarioPrincipal.menuPrincipalView();
        }

    }

    public void desactivarControles() {
        txtCodigoCE.setEditable(false);
        txtNombreCE.setEditable(false);
        txtDescripcionCE.setEditable(false);
    }

    public void activarControles() {
        txtCodigoCE.setEditable(true);
        txtNombreCE.setEditable(true);
        txtDescripcionCE.setEditable(true);
    }

    public void limpiarControles() {
        txtCodigoCE.clear();
        txtNombreCE.clear();
        txtDescripcionCE.clear();
    }

    public void guardar() {
        CargoEmpleado registro = new CargoEmpleado();
        registro.setCodigoCargoEmpleado(Integer.parseInt(txtCodigoCE.getText()));
        registro.setNombreCargo(txtNombreCE.getText());
        registro.setDescripcionCargo(txtDescripcionCE.getText());
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarCargoEmpleado(?,?,?)}");
            procedimiento.setInt(1, registro.getCodigoCargoEmpleado());
            procedimiento.setString(2, registro.getNombreCargo());
            procedimiento.setString(3, registro.getDescripcionCargo());
            procedimiento.execute();
            listaCargoEmpleado.add(registro);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void eliminar() {
        switch (tipoDeOperaciones) {
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnAgregarCE.setText("Agregar");
                btnEliminarCE.setText("Eliminar");
                btnEditarCE.setDisable(false);
                btnReporteCE.setDisable(false);
                imgAgregar.setImage(new Image("/org/derianhernandez/images/agregar-usuario.png"));
                imgEliminar.setImage(new Image("/org/derianhernandez/images/quitar-usuario.png"));
                tipoDeOperaciones = CargoEmpleadoController.operaciones.NINGUNO;
                break;

            default:
                if (tblCE.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confirmar si elimina el registro",
                            "Eliminar Cargo Empleado", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarCargoEmpleado(?)}");
                            procedimiento.setInt(1, ((CargoEmpleado) tblCE.getSelectionModel().getSelectedItem()).getCodigoCargoEmpleado());
                            procedimiento.execute();
                            listaCargoEmpleado.remove(tblCE.getSelectionModel().getSelectedItem());
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

    public void actualizar() {
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarCargoEmpleado(?,?,?)}");
            CargoEmpleado registro = (CargoEmpleado) tblCE.getSelectionModel().getSelectedItem();
            registro.setNombreCargo(txtNombreCE.getText());
            registro.setDescripcionCargo(txtDescripcionCE.getText());
            procedimiento.setInt(1, registro.getCodigoCargoEmpleado());
            procedimiento.setString(2, registro.getNombreCargo());
            procedimiento.setString(3, registro.getDescripcionCargo());
            procedimiento.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reporte() {
        switch (tipoDeOperaciones) {
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnEditarCE.setText("Editar");
                btnReporteCE.setText("Reportes");
                btnAgregarCE.setDisable(false);
                btnEliminarCE.setDisable(false);
                imgEditar.setImage(new Image("/org/derianhernandez/images/editar-perfil.png"));
                imgReporte.setImage(new Image("/org/derianhernandez/images/informe-seo.png"));
                tipoDeOperaciones = CargoEmpleadoController.operaciones.NINGUNO;
                break;
        }
    }

}
