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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.derianhernandez.bean.TipoProducto;
import org.derianhernandez.db.Conexion;
import org.derianhernandez.system.Main;

/* Herencia Multiple concepto, interfaces. POO
 */


    public class MenuTipoProductoController implements Initializable {
   
        private Main  escenarioPrincipal;
        private ObservableList<TipoProducto> listaTipoProducto;
        @FXML
        private TextField txtCodigoTP;
        @FXML
        private TextArea  txtDescripcionTP;
        @FXML
        private Button btnAgregarTP;
        @FXML
        private Button btnEliminarTP;
        @FXML
        private Button btnEditarTP;
        @FXML
        private Button btnReporteTP;
        @FXML
        private ImageView imgAgregar;
        @FXML
        private ImageView imgEliminar;
        @FXML
        private ImageView imgEditar;
        @FXML
        private ImageView imgReporte;
        @FXML
        private TableView tblTP;
        @FXML
        private TableColumn colCodigoTipoProducto;
        @FXML
        private TableColumn colDescripcion;
        
        private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    
    public void cargarDatos() {
        tblTP.setItems(getTipoProducto());
        colCodigoTipoProducto.setCellValueFactory(new PropertyValueFactory<TipoProducto, Integer>("codigoTipoProducto"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<TipoProducto, String>("descripcion"));
    }

    public ObservableList<TipoProducto> getTipoProducto() {
        ArrayList<TipoProducto> lista = new ArrayList();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarTipoProducto()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new TipoProducto(resultado.getInt("codigoTipoProducto"),
                        resultado.getString("descripcion")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaTipoProducto = FXCollections.observableArrayList(lista);
    }
     public void seleccionarElemento() {
        txtCodigoTP.setText(String.valueOf(((TipoProducto) tblTP.getSelectionModel().getSelectedItem()).getCodigoTipoProducto()));
        txtDescripcionTP.setText(((TipoProducto) tblTP.getSelectionModel().getSelectedItem()).getDescripcion());
    }
     
      public void agregar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                limpiarControles();
                activarControles();
                btnAgregarTP.setText("Guardar");
                btnEliminarTP.setText("Cancelar");
                btnEditarTP.setDisable(true);
                btnReporteTP.setDisable(true);
                imgAgregar.setImage(new Image("/org/derianhernandez/images/Guardar.png"));
                imgEliminar.setImage(new Image("/org/derianhernandez/images/Eliminar2.png"));
                tipoDeOperaciones = MenuTipoProductoController.operaciones.ACTUALIZAR;
                break;
            case ACTUALIZAR:
                guardar();
                desactivarControles();
                limpiarControles();
                btnAgregarTP.setText("Agregar");
                btnEliminarTP.setText("Eliminar");
                btnEditarTP.setDisable(false);
                btnReporteTP.setDisable(false);
                imgAgregar.setImage(new Image("/org/derianhernandez/images/product+.png"));
                imgEliminar.setImage(new Image("/org/derianhernandez/images/product-.png"));
                tipoDeOperaciones = MenuTipoProductoController.operaciones.NINGUNO;
                break;
        }
    }
      
       public void eliminar() {
        switch (tipoDeOperaciones) {
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnAgregarTP.setText("Agregar");
                btnEliminarTP.setText("Eliminar");
                btnEditarTP.setDisable(false);
                btnReporteTP.setDisable(false);
                imgAgregar.setImage(new Image("/org/derianhernandez/images/product+.png"));
                imgEliminar.setImage(new Image("/org/derianhernandez/images/product-.png"));
                tipoDeOperaciones = MenuTipoProductoController.operaciones.NINGUNO;
                break;

            default:
                if (tblTP.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confirmar si elimina el registro",
                            "Eliminar Tipo Producto", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarTipoProducto(?)}");
                            procedimiento.setInt(1, ((TipoProducto) tblTP.getSelectionModel().getSelectedItem()).getCodigoTipoProducto());
                            procedimiento.execute();
                            listaTipoProducto.remove(tblTP.getSelectionModel().getSelectedItem());
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
                if (tblTP.getSelectionModel().getSelectedItem() != null) {
                    btnEditarTP.setText("Actualizar");
                    btnReporteTP.setText("Cancelar");
                    btnAgregarTP.setDisable(true);
                    btnEliminarTP.setDisable(true);
                    imgEditar.setImage(new Image("/org/derianhernandez/images/editProduct.png"));
                    imgReporte.setImage(new Image("/org/derianhernandez/images/Eliminar2.png"));
                    activarControles();
                    txtCodigoTP.setEditable(false);
                    tipoDeOperaciones = operaciones.ACTUALIZAR;
                } else {
                    JOptionPane.showMessageDialog(null, "Debe de seleccionar algun elemento");
                }
                break;
            case ACTUALIZAR:
                actualizar();
                desactivarControles();
                limpiarControles();
                btnEditarTP.setText("Editar");
                btnReporteTP.setText("Reportes");
                btnAgregarTP.setDisable(false);
                btnEliminarTP.setDisable(false);
                imgEditar.setImage(new Image("/org/derianhernandez/images/productDelete.png"));
                imgReporte.setImage(new Image("/org/derianhernandez/images/informe-seo.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
                cargarDatos();
                break;
        }
    }
    
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
    
    @FXML
    private Button btnHome4;
    
    @FXML
    private void clicHome4(ActionEvent event) throws IOException {
        if(event.getSource() == btnHome4){
           escenarioPrincipal.menuPrincipalView();        
        }
    }
     public void guardar() {
        TipoProducto registro = new TipoProducto();
        registro.setCodigoTipoProducto(Integer.parseInt(txtCodigoTP.getText()));
        registro.setDescripcion(txtDescripcionTP.getText());
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarTipoProducto(?,?)}");
            procedimiento.setInt(1, registro.getCodigoTipoProducto());
            procedimiento.setString(2, registro.getDescripcion());
            procedimiento.execute();
            listaTipoProducto.add(registro);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
     
     public void actualizar() {
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarTipoProducto(?,?)}");
            TipoProducto registro = (TipoProducto) tblTP.getSelectionModel().getSelectedItem();
            registro.setDescripcion(txtDescripcionTP.getText());
            procedimiento.setInt(1, registro.getCodigoTipoProducto());
            procedimiento.setString(2, registro.getDescripcion());
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
                btnEditarTP.setText("Editar");
                btnReporteTP.setText("Reporte");
                btnAgregarTP.setDisable(false);
                btnEliminarTP.setDisable(false);
                imgEditar.setImage(new Image("/org/derianhernandez/images/productDelete.png"));
                imgReporte.setImage(new Image("/org/derianhernandez/images/informe-seo.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
        }
    }
     
     public void desactivarControles() {
        txtCodigoTP.setEditable(false);
        txtDescripcionTP.setEditable(false);
    }

    public void activarControles() {
        txtCodigoTP.setEditable(true);
        txtDescripcionTP.setEditable(true);
    }

    public void limpiarControles() {
        txtCodigoTP.clear();
        txtDescripcionTP.clear();
    }
}

