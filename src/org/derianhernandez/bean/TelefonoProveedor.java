
package org.derianhernandez.bean;

public class TelefonoProveedor {
    private int codigoTelefonoProveedor;
    private String numeroPrincipal;
    private String numeroSecundario;
    private String observaciones;
    private int codigoProveedor;

    public TelefonoProveedor() {
    }

    public TelefonoProveedor(int codigoTelefonoProveedor, String numeroPrincipal, String numeroSecundario, String observaciones, int codigoProveedores) {
        this.codigoTelefonoProveedor = codigoTelefonoProveedor;
        this.numeroPrincipal = numeroPrincipal;
        this.numeroSecundario = numeroSecundario;
        this.observaciones = observaciones;
        this.codigoProveedor = codigoProveedores;
    }

    public int getCodigoTelefonoProveedor() {
        return codigoTelefonoProveedor;
    }

    public void setCodigoTelefonoProveedor(int codigoTelefonoProveedor) {
        this.codigoTelefonoProveedor = codigoTelefonoProveedor;
    }

    public String getNumeroPrincipal() {
        return numeroPrincipal;
    }

    public void setNumeroPrincipal(String numeroPrincipal) {
        this.numeroPrincipal = numeroPrincipal;
    }

    public String getNumeroSecundario() {
        return numeroSecundario;
    }

    public void setNumeroSecundario(String numeroSecundario) {
        this.numeroSecundario = numeroSecundario;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public int getCodigoProveedor() {
        return codigoProveedor;
    }

    public void setCodigoProveedor(int codigoProveedores) {
        this.codigoProveedor = codigoProveedores;
    }
    
    @Override
    public String toString() {
        return getCodigoTelefonoProveedor() + " | " + getNumeroPrincipal() + " | "
                + getNumeroSecundario() + " | " + getObservaciones()+ " | "+
                getCodigoProveedor();
    }
}
