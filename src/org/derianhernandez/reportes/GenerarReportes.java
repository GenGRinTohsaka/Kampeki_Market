
package org.derianhernandez.reportes;

import java.io.InputStream;
import java.util.Map;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

import org.derianhernandez.bean.Usuarios;
import org.derianhernandez.db.Conexion;

import javafx.stage.WindowEvent;

/**
 *
 * @author Derian
 */
public class GenerarReportes {
    private static GenerarReportes instancia;

    public static GenerarReportes getInstancia() {
        if (instancia == null) {
            instancia = new GenerarReportes();
        }
        return instancia;
    }
    public static void mostrarReporte(String nombreReporte, String titulo, Map parametro) {
        InputStream reporte = GenerarReportes.class.getResourceAsStream(nombreReporte);
        try {
            JasperReport reporteMaestro = (JasperReport) JRLoader.loadObject(reporte);
            JasperPrint reporteImpreso = JasperFillManager.fillReport(reporteMaestro, parametro, Conexion.getInstance().getConexion());
            JasperViewer visor = new JasperViewer(reporteImpreso,false);
            visor.setTitle(titulo);
            visor.setVisible(true);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
