package org.derianhernandez.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import org.derianhernandez.bean.Clientes;
import org.derianhernandez.bean.Productos;
import org.derianhernandez.bean.Usuarios;
import org.derianhernandez.db.Conexion;
import org.derianhernandez.system.Main;

public class LoginViewController implements Initializable {

    @FXML
    private Button btnISesion;
    @FXML
    private TextField txtUsuario;
    @FXML
    private PasswordField pfContraseña;
    private ObservableList<Usuarios> listaUsuarios;
    public String contraseñaDB;
    public int nivelUsuario;
    public void buscarUsuario(String nombreUsuario) {
       
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion()
                    .prepareCall("{call sp_BuscarUsuario(?)}");
            procedimiento.setString(1, nombreUsuario);
            ResultSet registro = procedimiento.executeQuery();
            
            if (registro.next()) {
                 contraseñaDB= registro.getString("contraseña");
                 nivelUsuario = registro.getInt("nivelPermisos"); 
                if(pfContraseña.getText().equals(contraseñaDB)){
                    
                    JOptionPane.showMessageDialog(null,"Bienvenido "+txtUsuario.getText(), "Bienvenida"
                    , nivelUsuario);
                   Usuarios u = Usuarios.obtenerInstancia();
                   u.setNivelPermisos(nivelUsuario);
                   escenarioPrincipal.menuPrincipalView();
                   
    
                }else{
                    JOptionPane.showMessageDialog(null, "Contraseña o usuario no coinciden", "Error usuario", 0);
                }   
            }else{
                JOptionPane.showMessageDialog(null,"Nombre de Usuario o contraseña Incorrectos", "Error inicio de sesion", 0);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    
    }

    

    private Main escenarioPrincipal;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public Main getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    @FXML
    private void buttonHandleEvent(ActionEvent event) throws IOException {
        if (event.getSource() == btnISesion) {
            buscarUsuario(txtUsuario.getText());
           
        }
    }
}
