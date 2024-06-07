/*
Mi clase conexion que es la encargada de hacer la coneccion con mi base de datos
para este programa
Aplicamos Singleton para que no se duplique el objeto
Aplicamos setters y getters 
*/
package org.derianhernandez.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    private Connection conexion;
    private static Conexion instancia;
    
    public Conexion(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/DBKampekiMarket?useSSL=false","2023346_IN5BV","abc123!!");
             
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }catch(InstantiationException e){
            e.printStackTrace();
        }catch(IllegalAccessException e){
            e.printStackTrace();
        }catch(SQLException e){
           e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public static Conexion getInstance(){
        if(instancia == null){
             instancia = new Conexion();
        }
        
        return instancia;
    }

    public Connection getConexion() {
        return conexion;
    }

    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }
    
}
