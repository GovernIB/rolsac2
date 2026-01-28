package es.caib.rolsac2.ejb.util.procesos;

import org.quartz.CronExpression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * The Class InterpreteQuartz.
 */
public final class InterpreteQuartz {
    private static final Logger LOG = LoggerFactory.getLogger(InterpreteQuartz.class);

    /**
     * expresion.
     */
    private String expresion;

    /**
     * fecha proxima ejecucion.
     */
    private Date fechaProximaEjecucion;

    /**
     * fecha ultima ejecucion.
     */
    private Date fechaUltimaEjecucion;

    /**
     * Recupera el campo fecha ultima ejecucion.
     *
     * @return the fechaUltimaEjecucion
     */
    public Date getFechaUltimaEjecucion() {
        return fechaUltimaEjecucion;
    }

    /**
     * Método para setear el campo fecha ultima ejecucion.
     *
     * @param pFechaUltimaEjecucion the fechaUltimaEjecucion to set
     */
    public void setFechaUltimaEjecucion(final Date pFechaUltimaEjecucion) {
        this.fechaUltimaEjecucion = pFechaUltimaEjecucion;
    }

    /**
     * Recupera el campo fecha proxima ejecucion.
     *
     * @return the fechaProximaEjecucion
     */
    public Date getFechaProximaEjecucion() {
        return fechaProximaEjecucion;
    }

    /**
     * Instancia una nueva interprete quartz.
     */
    public InterpreteQuartz() {
        super();

    }

    /**
     * Checks if is activar.
     *
     * @return true, if is activar
     */

    public boolean isActivar() {
        /** El método se encarga de ver cuando es la próxima ejecución a partir de la última
         *  y la expresión cron. */
        try {
            CronExpression cron = new CronExpression(this.expresion);
            Date base = this.fechaUltimaEjecucion != null ? this.fechaUltimaEjecucion : new Date(0);
            fechaProximaEjecucion = cron.getNextValidTimeAfter(base);
            return fechaProximaEjecucion.before(new Date());
        } catch (java.text.ParseException e) {
            // Manejo del error: puedes registrar el error y devolver false
            LOG.error("Error al interpretar cron del proceso. ERROR:" + e.getMessage(), e);
            return false;
        }




        /*final CronTrigger ct = new CronTrigger(this.expresion, ZoneId.systemDefault());
        final ProcesTriggerContext pc = new ProcesTriggerContext();
        Date base = this.fechaUltimaEjecucion != null
                ? this.fechaUltimaEjecucion
                : new Date(0);
        pc.setLast(base);
        fechaProximaEjecucion = ct.getNextRunTime(pc, pc.getRunEnd() == null ? base : pc.getRunEnd());
        return fechaProximaEjecucion.before(new Date());
        */
    }

    /**
     * Recupera el campo expresion.
     *
     * @return the expresion
     */
    public String getExpresion() {
        return expresion;
    }

    /**
     * Método para setear el campo expresion.
     *
     * @param pExpresion the expresion to set
     */
    public void setExpresion(final String pExpresion) {
        this.expresion = pExpresion;
    }


}
