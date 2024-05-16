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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.derianhernandez.bean.Compras;
import org.derianhernandez.db.Conexion;

import org.derianhernandez.system.Main;

/* Herencia Multiple concepto, interfaces. POO
 */
public class MenuComprasController implements Initializable {

    @FXML
    private Button btnHome4;

    private ObservableList<Compras> listaCompras;

    private Main escenarioPrincipal;
    @FXML
    private Button btnAgregarCompras;
    @FXML
    private Button btnEliminarCompras;
    @FXML
    private Button btnEditarCompras;
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
    private TextField txtCodigoC;
    @FXML
    private TextField txtFechaC;
    @FXML
    private TextField txtDescripcionC;
    @FXML
    private TextField txtTotalC;
    @FXML
    private TableView tblC;
    @FXML
    private TableColumn colCodigoC;
    @FXML
    private TableColumn colFechaC;
    @FXML
    private TableColumn colDescripcionC;
    @FXML
    private TableColumn colTotalC;

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
        tblC.setItems(getCompras());
        colCodigoC.setCellValueFactory(new PropertyValueFactory<Compras, Integer>("numeroDocumento"));
        colFechaC.setCellValueFactory(new PropertyValueFactory<Compras, String>("fechaDocumento"));
        colDescripcionC.setCellValueFactory(new PropertyValueFactory<Compras, String>("descripcion"));
        colTotalC.setCellValueFactory(new PropertyValueFactory<Compras, Double>("totalDocumento"));
    }

    public void seleccionarElemento() {
        txtCodigoC.setText(String.valueOf(((Compras) tblC.getSelectionModel().getSelectedItem()).getNumeroDocumento()));
        txtFechaC.setText(((Compras) tblC.getSelectionModel().getSelectedItem()).getFechaDocumento());
        txtDescripcionC.setText(((Compras) (tblC.getSelectionModel().getSelectedItem())).getDescripcion());
        txtTotalC.setText(String.valueOf(((Compras) tblC.getSelectionModel().getSelectedItem()).getTotalDocumento()));
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
                        resultado.getDouble("totalDocumento")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaCompras = FXCollections.observableArrayList(lista);
    }

    public void agregar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                limpiarControles();
                activarControles();
                btnAgregarCompras.setText("Guardar");
                btnEliminarCompras.setText("Cancelar");
                btnEditarCompras.setDisable(true);
                btnReportes.setDisable(true);
                imgAgregar.setImage(new Image("/org/derianhernandez/images/Guardar.png"));
                imgEliminar.setImage(new Image("/org/derianhernandez/images/Eliminar2.png"));
                tipoDeOperaciones = MenuComprasController.operaciones.ACTUALIZAR;
                break;
            case ACTUALIZAR:
                guardar();
                desactivarControles();
                limpiarControles();
                btnAgregarCompras.setText("Agregar");
                btnEliminarCompras.setText("Eliminar");
                btnEditarCompras.setDisable(false);
                btnReportes.setDisable(false);
                imgAgregar.setImage(new Image("/org/derianhernandez/images/buy+.png"));
                imgEliminar.setImage(new Image("/org/derianhernandez/images/buy-t.png"));
                tipoDeOperaciones = MenuComprasController.operaciones.NINGUNO;
                break;
        }
    }

    public void eliminar() {
        switch (tipoDeOperaciones) {
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnAgregarCompras.setText("Agregar");
                btnEliminarCompras.setText("Eliminar");
                btnEditarCompras.setDisable(false);
                btnReportes.setDisable(false);
                imgAgregar.setImage(new Image("/org/derianhernandez/images/buy+.png"));
                imgEliminar.setImage(new Image("/org/derianhernandez/images/buy-t.png"));
                tipoDeOperaciones = MenuComprasController.operaciones.NINGUNO;
                break;

            default:
                if (tblC.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confirmar si elimina el registro",
                            "Eliminar Compra", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarCompras(?)}");
                            procedimiento.setInt(1, ((Compras) tblC.getSelectionModel().getSelectedItem()).getNumeroDocumento());
                            procedimiento.execute();
                            listaCompras.remove(tblC.getSelectionModel().getSelectedItem());
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
                if (tblC.getSelectionModel().getSelectedItem() != null) {
                    btnEditarCompras.setText("Actualizar");
                    btnReportes.setText("Cancelar");
                    btnAgregarCompras.setDisable(true);
                    btnEliminarCompras.setDisable(true);
                    imgEditar.setImage(new Image("/org/derianhernandez/images/editarUsuario.png"));
                    imgReporte.setImage(new Image("/org/derianhernandez/images/Eliminar2.png"));
                    activarControles();
                    txtCodigoC.setEditable(false);
                    tipoDeOperaciones = MenuComprasController.operaciones.ACTUALIZAR;
                } else {
                    JOptionPane.showMessageDialog(null, "Debe de seleccionar algun elemento");
                }
                break;
            case ACTUALIZAR:
                actualizar();
                desactivarControles();
                limpiarControles();
                btnEditarCompras.setText("Editar");
                btnReportes.setText("Reportes");
                btnAgregarCompras.setDisable(false);
                btnEliminarCompras.setDisable(false);
                imgEditar.setImage(new Image("/org/derianhernandez/images/editar-perfil.png"));
                imgReporte.setImage(new Image("/org/derianhernandez/images/informe-seo.png"));
                tipoDeOperaciones = MenuComprasController.operaciones.NINGUNO;
                cargarDatos();
                break;
        }
    }
    
     public void reporte() {
        switch (tipoDeOperaciones) {
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnEditarCompras.setText("Editar");
                btnReportes.setText("Reportes");
                btnAgregarCompras.setDisable(false);
                btnEliminarCompras.setDisable(false);
                imgEditar.setImage(new Image("/org/derianhernandez/images/editar-perfil.png"));
                imgReporte.setImage(new Image("/org/derianhernandez/images/informe-seo.png"));
                tipoDeOperaciones = MenuComprasController.operaciones.NINGUNO;
                break;
        }
    }
     
    @FXML
    private void clicHome4(ActionEvent event) throws IOException {
        if (event.getSource() == btnHome4) {
            escenarioPrincipal.menuPrincipalView();

        }
    }

    public void guardar() {
        Compras registro = new Compras();
        registro.setNumeroDocumento(Integer.parseInt(txtCodigoC.getText()));
        registro.setFechaDocumento(txtFechaC.getText());
        registro.setDescripcion(txtDescripcionC.getText());
        registro.setTotalDocumento(Double.parseDouble(txtTotalC.getText()));
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarCompras(?,?,?,?)}");
            procedimiento.setInt(1, registro.getNumeroDocumento());
            procedimiento.setString(2, registro.getFechaDocumento());
            procedimiento.setString(3, registro.getDescripcion());
            procedimiento.setDouble(4, registro.getTotalDocumento());
            procedimiento.execute();
            listaCompras.add(registro);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void actualizar() {
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarCompras(?,?,?,?)}");
            Compras registro = (Compras) tblC.getSelectionModel().getSelectedItem();
            registro.setFechaDocumento(txtFechaC.getText());
            registro.setDescripcion(txtDescripcionC.getText());
            registro.setTotalDocumento(Double.parseDouble(txtTotalC.getText()));
            procedimiento.setInt(1, registro.getNumeroDocumento());
            procedimiento.setString(2, registro.getFechaDocumento());
            procedimiento.setString(3, registro.getDescripcion());
            procedimiento.setDouble(4, registro.getTotalDocumento());
            procedimiento.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void desactivarControles() {
        txtCodigoC.setEditable(false);
        txtFechaC.setEditable(false);
        txtDescripcionC.setEditable(false);
        txtTotalC.setEditable(false);
    }

    public void activarControles() {
        txtCodigoC.setEditable(true);
        txtFechaC.setEditable(true);
        txtDescripcionC.setEditable(true);
        txtTotalC.setEditable(true);
    }

    public void limpiarControles() {
        txtCodigoC.clear();
        txtFechaC.clear();
        txtDescripcionC.clear();
        txtTotalC.clear();
    }

}
