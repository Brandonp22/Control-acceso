/*
 * Servira para mostrar la fecha y la hora en la parte inferior de la ventana principal
 */
package Controlador;

import Vista.PnlFechaHora;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimerTask;
import java.util.Timer;

/**
 *
 * @author Jaasiel Guerra
 */
public class ControlFechaHora {

    public PnlFechaHora fechaHora = null;
    private Date date = null;
    private DateFormat hora = null;
    private DateFormat fecha = null;
    private Timer timer = null;
    private Calendar calendario = null;

    public ControlFechaHora() {

        this.fechaHora = new PnlFechaHora();
        this.hora = new SimpleDateFormat("HH:mm:ss");
        this.fecha = new SimpleDateFormat("dd/MM/yyyy");
        this.timer = new Timer();//timer para subprocesos

        iniciarTimer();
    }

    private void actualizar() {
       
        this.date = new Date();
        this.fechaHora.Hora.setText(hora.format(date));
        this.fechaHora.Fecha.setText(fecha.format(date));
        this.date = null;
    }

    private void iniciarTimer() {

        TimerTask procesos = new TimerTask() {
            @Override
            public void run() {//subProceso aca

                actualizar();

            }
        };
        timer.schedule(procesos, 0, 1000);
    }

    public PnlFechaHora getFechaHora() {
        return fechaHora;
    }

}
