
//Importamos todos los package que necesitemos

package org.derianhernandez.system;

import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.derianhernandez.controllers.CargoEmpleadoController;
import org.derianhernandez.controllers.MenuClientesController;
import org.derianhernandez.controllers.MenuComprasController;
import org.derianhernandez.controllers.MenuPrincipalController;
import org.derianhernandez.controllers.MenuProveedoresController;
import org.derianhernandez.controllers.MenuTipoProductoController;
import org.derianhernandez.controllers.ProgramadorController;

/**
 * Autor: Derian Hernandez
 * Carnet: 2023346
 * Fecha de Creacion: 18/04/2024
 * Fecha de Actualizacion: 25/04/2024
 */

/*
Declaramos nuestra variable Stage para que almacene el escenario
Declaramos nuestra variable Scene para que sea nuestros escenarios
Declaramos nuestra variable String para acortarnos los URL
*/
public class Main extends Application {

    private Stage escenarioPrincipal;
    private Scene escena;
    private final String URLVIEW = "/org/derianhernandez/views/";

    /*
    Este es el encargado de hacer correr al programa
    Podemos ver que le puse un icono ala ventana
    */
    @Override
    public void start(Stage escenarioPrincipal) throws Exception {
        this.escenarioPrincipal = escenarioPrincipal;

        escenarioPrincipal.getIcons().add(new Image(Principal.class.getResourceAsStream("/org/derianhernandez/images/Logo.png")));
        //Este metodo tiene adentro otro para que se encargue de cargar la escena
        menuPrincipalView();

        //Este ya es el encargado de mostrar la escena
        escenarioPrincipal.show();
    }

    
    //Esta funcion es el encargado de cambiar escena y recible como parametros el nombre del Archivo FXML, el largo y la altura de la escena
    
    public Initializable cambiarEscena(String fxmlName, int width, int height) throws IOException {
        
        //Esta es una variable Initializable en valor nulo  
        Initializable result = null;
        //Creamos nuestro objeto tipo FXMLLoader
        FXMLLoader loader = new FXMLLoader();

        /*
        Estas 3 lineas de codigo son las encargadas de hacer que la varibale 
        "file" tome valor del archivo que se le entrege
        */
        InputStream file = Main.class.getResourceAsStream(URLVIEW + fxmlName);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(Main.class.getResource(URLVIEW + fxmlName));

        /*
        Estas lineas se encargan agregarle el valor a escena para que 
        el escenario tome el valor de escena y despues solo le agregemos su tamaño
        y result puede tomar valor y salir devuelta como el valor para la funcion
        */
        escena = new Scene((AnchorPane) loader.load(file), width, height);
        escenarioPrincipal.setScene(escena);
        escenarioPrincipal.sizeToScene();
        result = (Initializable) loader.getController();

        return result;
    }

    /*
    Con este metodo nos encargamos de mandar los parametros a el metodo cambiarEscena
    para que de forma posterior solo hagamos el set de este escenario y le pongamos
    el nombre a la ventana que sera abierta
    */
    public void menuPrincipalView() {
        try {
            MenuPrincipalController menuPrincipalView = (MenuPrincipalController) cambiarEscena("MenuPrincipalView.fxml", 670, 463);
            menuPrincipalView.setEscenarioPrincipal(this);
            this.escenarioPrincipal.setTitle("Kampeki Market");
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
    /*
    Con este metodo nos encargamos de mandar los parametros a el metodo cambiarEscena
    para que de forma posterior solo hagamos el set de este escenario y le pongamos
    el nombre a la ventana que sera abierta
    */
    public void menuClientesView() {
        try {
            MenuClientesController menuClientesView = (MenuClientesController) cambiarEscena("ClientesView.fxml", 827, 466);
            menuClientesView.setEscenarioPrincipal(this);
            this.escenarioPrincipal.setTitle("Clientes");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /*
    Con este metodo nos encargamos de mandar los parametros a el metodo cambiarEscena
    para que de forma posterior solo hagamos el set de este escenario y le pongamos
    el nombre a la ventana que sera abierta
    */
    public void programadorView() {
        try {
            ProgramadorController programadorView = (ProgramadorController) cambiarEscena("ProgramadorView.fxml", 709, 432);
            programadorView.setEscenarioPrincipal(this);
            this.escenarioPrincipal.setTitle("Programador");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void proveedorView(){
        try{
            MenuProveedoresController proveedoresView = (MenuProveedoresController) cambiarEscena("ProveedoresView.fxml", 894, 502);
            proveedoresView.setEscenarioPrincipal(this);
            this.escenarioPrincipal.setTitle("Proveedores");
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    public void tipoProductoView(){
        try{
            MenuTipoProductoController tipoProductoView = (MenuTipoProductoController) cambiarEscena("MenuTipoProductoView.fxml", 809, 456);
            tipoProductoView.setEscenarioPrincipal(this);
            this.escenarioPrincipal.setTitle("Tipo de Producto");
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void comprasView(){
        try{
            MenuComprasController comprasView = (MenuComprasController) cambiarEscena("ComprasView.fxml", 901, 515);
            comprasView.setEscenarioPrincipal(this);
            this.escenarioPrincipal.setTitle("Compras");
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        launch(args);
    }

     public void cargoEmpleadoView(){
        try{
            CargoEmpleadoController cargoEmpleadoView = (CargoEmpleadoController) cambiarEscena("CargoEmpleadoView.fxml", 915, 514);
            cargoEmpleadoView.setEscenarioPrincipal(this);
            this.escenarioPrincipal.setTitle("Cargo Empleado");
        } catch(Exception e){
            e.printStackTrace();
        }
    }
   
}
