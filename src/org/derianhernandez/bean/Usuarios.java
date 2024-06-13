package org.derianhernandez.bean;

public class Usuarios {
    private String nombreUsuario;
    private String contraseña;
    private int nivelPermisos;

    public Usuarios() {
    }

    public Usuarios(String nombreUsuario, String contraseña, int nivelPermisos) {
        this.nombreUsuario = nombreUsuario;
        this.contraseña = contraseña;
        this.nivelPermisos = nivelPermisos;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public int getNivelPermisos() {
        return nivelPermisos;
    }

    public void setNivelPermisos(int nivelPermisos) {
        this.nivelPermisos = nivelPermisos;
    }

}
