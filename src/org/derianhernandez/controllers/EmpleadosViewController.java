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
import org.derianhernandez.bean.CargoEmpleado;
import org.derianhernandez.bean.Empleados;
import org.derianhernandez.db.Conexion;
import org.derianhernandez.system.Main;

/**
 * FXML Controller class
 *
 * @author mgm14
 */
public class EmpleadosViewController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cmbCCE.setItems(getCargoEmpleado());
        cargarDatos();
    }
    @FXML
    private Button btnHome;
    @FXML
    private Button btnAgregarE;
    @FXML
    private Button btnEliminarE;
    @FXML
    private Button btnReporteE;
    @FXML
    private ImageView imgAgregar;
    @FXML
    private ImageView imgEliminar;
    @FXML
    private ImageView imgEditar;
    @FXML
    private ImageView imgReporte;
    @FXML
    private Button btnEditarE;
    @FXML
    private TextField txtCodigoE;
    @FXML
    private TextField txtTurno;
    @FXML
    private TextField txtNombres;
    @FXML
    private TextField txtApellidos;
    @FXML
    private TextField txtSueldo;
    @FXML
    private TextField txtDireccion;
    @FXML
    private ComboBox cmbCCE;
    @FXML
    private TableView tblE;
    @FXML
    private TableColumn colCodigoEmpleado;
    @FXML
    private TableColumn colTurno;
    @FXML
    private TableColumn colNombres;
    @FXML
    private TableColumn colApellidos;
    @FXML
    private TableColumn colSueldo;
    @FXML
    private TableColumn colDireccion;
    @FXML
    private TableColumn colCodigoCargoEmpleado;
    private Main escenarioPrincipal;

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

    private ObservableList<Empleados> listaEmpleados;
    private ObservableList<CargoEmpleado> listaCargoEmpleado;

    public ObservableList<Empleados> getEmpleados() {
        ArrayList<Empleados> lista = new ArrayList<Empleados>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarEmpleados()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new Empleados(resultado.getInt("codigoEmpleado"),
                        resultado.getString("nombresEmpleado"),
                        resultado.getString("apellidosEmpleado"),
                        resultado.getDouble("sueldo"),
                        resultado.getString("direccion"),
                        resultado.getString("turno"),
                        resultado.getInt("codigoCargoEmpleado")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaEmpleados = FXCollections.observableArrayList(lista);
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

    public void cargarDatos() {
        tblE.setItems(getEmpleados());
        colCodigoEmpleado.setCellValueFactory(new PropertyValueFactory<Empleados, Integer>("codigoEmpleado"));
        colNombres.setCellValueFactory(new PropertyValueFactory<Empleados, String>("nombresEmpleado"));
        colApellidos.setCellValueFactory(new PropertyValueFactory<Empleados, String>("apellidosEmpleado"));
        colSueldo.setCellValueFactory(new PropertyValueFactory<Empleados, Double>("sueldo"));
        colDireccion.setCellValueFactory(new PropertyValueFactory<Empleados, String>("direccion"));
        colTurno.setCellValueFactory(new PropertyValueFactory<Empleados, String>("turno"));
        colCodigoCargoEmpleado.setCellValueFactory(new PropertyValueFactory<Empleados, Integer>("codigoCargoEmpleado"));
    }

    public void seleccionarElemento() {
        txtCodigoE.setText(String.valueOf(((Empleados) tblE.getSelectionModel().getSelectedItem()).getCodigoEmpleado()));
        txtNombres.setText(((Empleados) tblE.getSelectionModel().getSelectedItem()).getNombresEmpleado());
        txtApellidos.setText(String.valueOf(((Empleados) tblE.getSelectionModel().getSelectedItem()).getApellidosEmpleado()));
        txtSueldo.setText(String.valueOf(((Empleados) tblE.getSelectionModel().getSelectedItem()).getSueldo()));
        txtDireccion.setText(((Empleados) tblE.getSelectionModel().getSelectedItem()).getDireccion());
        txtTurno.setText(((Empleados) tblE.getSelectionModel().getSelectedItem()).getTurno());
        cmbCCE.getSelectionModel().select(buscarCargoEmpleado(((Empleados) tblE.getSelectionModel().getSelectedItem()).getCodigoCargoEmpleado()));
    }

    public CargoEmpleado buscarCargoEmpleado(int codigoCargoEmpleado) {
        CargoEmpleado resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarCargoEmpleado(?)}");
            procedimiento.setInt(1, codigoCargoEmpleado);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()) {
                resultado = new CargoEmpleado(registro.getInt("codigoCargoEmpleado"),
                        registro.getString("nombreCargo"),
                        registro.getString("descripcionCargo"));

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
                btnAgregarE.setText("Guardar");
                btnEliminarE.setText("Cancelar");
                btnEditarE.setDisable(true);
                btnReporteE.setDisable(true);
                imgAgregar.setImage(new Image("/org/derianhernandez/images/Guardar.png"));
                imgEliminar.setImage(new Image("/org/derianhernandez/images/Eliminar2.png"));
                tipoDeOperaciones = EmpleadosViewController.operaciones.ACTUALIZAR;
                break;
            case ACTUALIZAR:
                guardar();
                desactivarControles();
                limpiarControles();
                btnAgregarE.setText("Agregar");
                btnEliminarE.setText("Eliminar");
                btnEditarE.setDisable(false);
                btnReporteE.setDisable(false);
                imgAgregar.setImage(new Image("/org/derianhernandez/images/buy+.png"));
                imgEliminar.setImage(new Image("/org/derianhernandez/images/buy-t.png"));
                tipoDeOperaciones = EmpleadosViewController.operaciones.NINGUNO;
                break;
        }
    }

    public void eliminar() {
        switch (tipoDeOperaciones) {
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnAgregarE.setText("Agregar");
                btnEliminarE.setText("Eliminar");
                btnEditarE.setDisable(false);
                btnReporteE.setDisable(false);
                imgAgregar.setImage(new Image("/org/derianhernandez/images/agregar-usuario.png"));
                imgEliminar.setImage(new Image("/org/derianhernandez/images/quitar-usuario.png"));
                tipoDeOperaciones = EmpleadosViewController.operaciones.NINGUNO;
                break;
        }
    }

    public void editar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                if (tblE.getSelectionModel().getSelectedItem() != null) {
                    btnEditarE.setText("Actualizar");
                    btnReporteE.setText("Cancelar");
                    btnAgregarE.setDisable(true);
                    btnEliminarE.setDisable(true);
                    imgEditar.setImage(new Image("/org/derianhernandez/images/editarCaja.png"));
                    imgReporte.setImage(new Image("/org/derianhernandez/images/Eliminar2.png"));
                    activarControles();
                    txtCodigoE.setEditable(false);
                    tipoDeOperaciones = EmpleadosViewController.operaciones.ACTUALIZAR;
                } else {
                    JOptionPane.showMessageDialog(null, "Debe de seleccionar algun elemento");
                }
                break;
            case ACTUALIZAR:
                actualizar();
                desactivarControles();
                limpiarControles();
                btnEditarE.setText("Editar");
                btnReporteE.setText("Reportes");
                btnAgregarE.setDisable(false);
                btnEliminarE.setDisable(false);
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
                btnEditarE.setText("Editar");
                btnReporteE.setText("Reportes");
                btnAgregarE.setDisable(false);
                btnEliminarE.setDisable(false);
                imgEditar.setImage(new Image("/org/derianhernandez/images/productDelete.png"));
                imgReporte.setImage(new Image("/org/derianhernandez/images/informe-seo.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
        }
    }

    public void guardar() {
        Empleados registro = new Empleados();
        registro.setCodigoEmpleado(Integer.parseInt(txtCodigoE.getText()));
        registro.setNombresEmpleado(txtNombres.getText());
        registro.setApellidosEmpleado(txtApellidos.getText());
        registro.setSueldo(Double.parseDouble(txtSueldo.getText()));
        registro.setDireccion(txtDireccion.getText());
        registro.setTurno(txtTurno.getText());
        registro.setCodigoCargoEmpleado(((CargoEmpleado) cmbCCE.getSelectionModel().getSelectedItem()).getCodigoCargoEmpleado());

        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarEmpleados(?, ?, ?, ?, ?, ?, ?)}");
            procedimiento.setInt(1, registro.getCodigoEmpleado());
            procedimiento.setString(2, registro.getNombresEmpleado());
            procedimiento.setString(3, registro.getApellidosEmpleado());
            procedimiento.setDouble(4, registro.getSueldo());
            procedimiento.setString(5, registro.getDireccion());
            procedimiento.setString(6, registro.getTurno());
            procedimiento.setInt(7, registro.getCodigoCargoEmpleado());

            procedimiento.execute();

            listaEmpleados.add(registro);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void actualizar() {
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarEmpleados(?,?,?,?,?,?,?)}");
            Empleados registro = (Empleados) tblE.getSelectionModel().getSelectedItem();
            registro.setNombresEmpleado(txtNombres.getText());
            registro.setApellidosEmpleado(txtApellidos.getText());
            registro.setSueldo(Double.parseDouble(txtSueldo.getText()));
            registro.setDireccion(txtDireccion.getText());
            registro.setTurno(txtTurno.getText());
            registro.setCodigoCargoEmpleado(((CargoEmpleado) cmbCCE.getSelectionModel().getSelectedItem()).getCodigoCargoEmpleado());
            procedimiento.setInt(1, registro.getCodigoEmpleado());
            procedimiento.setString(2, registro.getNombresEmpleado());
            procedimiento.setString(3, registro.getApellidosEmpleado());
            procedimiento.setDouble(4, registro.getSueldo());
            procedimiento.setString(5, registro.getDireccion());
            procedimiento.setString(6, registro.getTurno());
            procedimiento.setInt(7, registro.getCodigoCargoEmpleado());
            procedimiento.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void desactivarControles() {
        txtCodigoE.setEditable(false);
        txtNombres.setEditable(false);
        txtApellidos.setEditable(false);
        txtTurno.setEditable(false);
        txtDireccion.setEditable(false);
        txtSueldo.setEditable(false);
        cmbCCE.setDisable(true);

    }

    public void activarControles() {
        txtCodigoE.setEditable(true);
        txtNombres.setEditable(true);
        txtApellidos.setEditable(true);
        txtTurno.setEditable(true);
        txtDireccion.setEditable(true);
        txtSueldo.setEditable(true);
        cmbCCE.setDisable(false);
    }

    public void limpiarControles() {
        txtCodigoE.clear();
        txtNombres.clear();
        txtApellidos.clear();
        txtTurno.clear();
        txtDireccion.clear();
        txtSueldo.clear();
        cmbCCE.getSelectionModel().getSelectedItem();

    }

    @FXML
    private void clicHome(ActionEvent event) throws IOException {
        if (event.getSource() == btnHome) {
            escenarioPrincipal.menuPrincipalView();
        }
    }
}
