package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.commons.utils.GeneradorId;
import es.caib.rolsac2.persistence.model.JFicheroExterno;
import es.caib.rolsac2.service.exception.FicheroExternoException;
import es.caib.rolsac2.service.model.FicheroDTO;
import es.caib.rolsac2.service.model.migracion.FicheroRolsac1;
import es.caib.rolsac2.service.model.types.TypeFicheroExterno;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.Query;
import java.io.*;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

/**
 * Implementación del repositorio de Personal.
 *
 * @author Indra
 */
@Stateless
@Local(FicheroExternoRepository.class)
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class FicheroExternoRepositoryBean extends AbstractCrudRepository<JFicheroExterno, Long> implements FicheroExternoRepository {

    private static final Logger LOG = LoggerFactory.getLogger(FicheroExternoRepositoryBean.class);


    protected FicheroExternoRepositoryBean() {
        super(JFicheroExterno.class);
    }

    @Override
    public FicheroDTO getContentById(final Long id, final String pathAlmacenamientoFicheros) {
        // Obtiene metadatos fichero
        final JFicheroExterno fic = entityManager.find(JFicheroExterno.class, id);
        if (fic == null) {
            throw new FicheroExternoException("No existe fichero con id: " + id);
        }

        // Obtiene path fichero
        String pathFile = pathAlmacenamientoFicheros + "/" + fic.getReferencia();

        // Obtiene contenido fichero
        try {
            final FileInputStream fis = new FileInputStream(pathFile);
            final byte[] content = IOUtils.toByteArray(fis);
            fis.close();

            final FicheroDTO cf = new FicheroDTO();
            cf.setCodigo(fic.getCodigo());
            cf.setTipo(TypeFicheroExterno.fromString(fic.getTipo()));
            cf.setFilename(fic.getFilename());
            cf.setContenido(content);
            cf.setReferencia(pathFile);

            return cf;
        } catch (final IOException e) {
            throw new FicheroExternoException("Error al acceder al fichero " + id + " con path " + pathFile, e);
        }
    }

    @Override
    public FicheroDTO getMetadata(Long idFichero, String pathAlmacenamientoFicheros) {
        // Obtiene metadatos fichero
        final JFicheroExterno fic = entityManager.find(JFicheroExterno.class, idFichero);
        if (fic == null) {
            throw new FicheroExternoException("No existe fichero con id: " + idFichero);
        }

        // Obtiene path fichero
        String pathFile = pathAlmacenamientoFicheros + "/" + fic.getReferencia();

        final FicheroDTO cf = new FicheroDTO();
        cf.setCodigo(fic.getCodigo());
        cf.setTipo(TypeFicheroExterno.fromString(fic.getTipo()));
        cf.setFilename(fic.getFilename());
        cf.setReferencia(pathFile);

        return cf;
    }


    @Override
    public Long createFicheroExterno(byte[] content, String fileName, TypeFicheroExterno tipoFicheroExterno, Long elementoFicheroExterno, String pathAlmacenamientoFicheros) {
        if (tipoFicheroExterno == null || content == null || fileName == null) {
            throw new FicheroExternoException("Ningún parámetro puede ser nulo");
        }

        JFicheroExterno jFicheroExterno = new JFicheroExterno();
        jFicheroExterno.setBorrar(false);
        jFicheroExterno.setFecha(new java.util.Date());
        jFicheroExterno.setIdElemento(elementoFicheroExterno);
        jFicheroExterno.setFilename(fileName);
        jFicheroExterno.setTemporal(true);
        jFicheroExterno.setTipo(tipoFicheroExterno.getTipo());
        if (elementoFicheroExterno == null) {
            jFicheroExterno.setReferencia("temp/" + GeneradorId.generarId() + "." + FilenameUtils.getExtension(fileName));
        } else {
            jFicheroExterno.setReferencia(tipoFicheroExterno.getRuta() + elementoFicheroExterno + "/" + GeneradorId.generarId() + "." + FilenameUtils.getExtension(fileName));
        }
        entityManager.persist(jFicheroExterno);

        // Almacena fichero
        final String pathAbsolutoFichero = pathAlmacenamientoFicheros + "/" + jFicheroExterno.getReferencia();
        try {
            // FileUtils de apache.
            final ByteArrayInputStream bis = new ByteArrayInputStream(content);
            final File file = new File(pathAbsolutoFichero);
            FileUtils.copyInputStreamToFile(bis, file);
            bis.close();

            //FileUtils de fundacionbit
            //final OutputStream file = new FileOutputStream(pathAbsolutoFichero);
            //InputStream bis = new ByteArrayInputStream(content);
            //FileUtils.copy(bis, file);
            //bis.close();
        } catch (final IOException ex) {
            throw new FicheroExternoException("Error al crear fichero externo " + jFicheroExterno.getCodigo() + " con path " + pathAbsolutoFichero, ex);
        }

        return jFicheroExterno.getCodigo();

    }

    @Override
    public Long createFicheroExternoMigracion(byte[] content, String fileName, TypeFicheroExterno tipoFicheroExterno, Long elementoFicheroExterno, String pathAlmacenamientoFicheros, Long codigoMigracion) {
        if (tipoFicheroExterno == null || content == null || fileName == null) {
            throw new FicheroExternoException("Ningún parámetro puede ser nulo");
        }

        JFicheroExterno jFicheroExterno = new JFicheroExterno();
        //jFicheroExterno.setCodigo(codigoMigracion);
        jFicheroExterno.setBorrar(false);
        jFicheroExterno.setFecha(new java.util.Date());
        jFicheroExterno.setIdElemento(elementoFicheroExterno);
        jFicheroExterno.setFilename(fileName);
        jFicheroExterno.setTemporal(false);
        jFicheroExterno.setTipo(tipoFicheroExterno.getTipo());
        if (elementoFicheroExterno == null) {
            jFicheroExterno.setReferencia("temp/" + GeneradorId.generarId() + "." + FilenameUtils.getExtension(fileName));
        } else {
            jFicheroExterno.setReferencia(tipoFicheroExterno.getRuta() + elementoFicheroExterno + "/" + GeneradorId.generarId() + "." + FilenameUtils.getExtension(fileName));
        }
        entityManager.persist(jFicheroExterno);
        //entityManager.merge(jFicheroExterno);

        // Almacena fichero
        final String pathAbsolutoFichero = pathAlmacenamientoFicheros + "/" + jFicheroExterno.getReferencia();
        try {
            // FileUtils de apache.
            final ByteArrayInputStream bis = new ByteArrayInputStream(content);
            final File file = new File(pathAbsolutoFichero);
            FileUtils.copyInputStreamToFile(bis, file);
            bis.close();

            //FileUtils de fundacionbit
            //final OutputStream file = new FileOutputStream(pathAbsolutoFichero);
            //InputStream bis = new ByteArrayInputStream(content);
            //FileUtils.copy(bis, file);
            //bis.close();
        } catch (final IOException ex) {
            throw new FicheroExternoException("Error al crear fichero externo " + jFicheroExterno.getCodigo() + " con path " + pathAbsolutoFichero, ex);
        }
        return jFicheroExterno.getCodigo();
    }

    @Override
    public void persistFicheroExterno(Long codigoFichero, Long idElemento, String pathAlmacenamientoFicheros) {
        JFicheroExterno jFicheroExterno = entityManager.find(JFicheroExterno.class, codigoFichero);
        if (jFicheroExterno != null && !jFicheroExterno.isBorrar() && jFicheroExterno.isTemporal()) {
            jFicheroExterno.setTemporal(false);
            if (jFicheroExterno.getReferencia().startsWith("temp/")) {

                //Está en un sitio temporal, obtenemos la referenciaActual (en temp) y la movemos a la nueva
                TypeFicheroExterno tipoFicheroExterno = TypeFicheroExterno.fromString(jFicheroExterno.getTipo());
                String referenciaAntigua = jFicheroExterno.getReferencia();
                String referenciaNueva;
                referenciaNueva = tipoFicheroExterno.getRuta() + codigoFichero + "/" + GeneradorId.generarId() + "." + FilenameUtils.getExtension(jFicheroExterno.getFilename());

                //La movemos
                try {
                    FileUtils.moveFile(new File(pathAlmacenamientoFicheros + "/" + referenciaAntigua), new File(pathAlmacenamientoFicheros + "/" + referenciaNueva));
                } catch (IOException e) {
                    throw new FicheroExternoException("Error copiando el fichero " + codigoFichero + " de la referencia antigua:" + referenciaAntigua + " a la nueva:" + referenciaNueva, e);
                }

                //Actualizamos la referencia
                jFicheroExterno.setReferencia(referenciaNueva);
            }
            entityManager.merge(jFicheroExterno);
        }
    }

    @Override
    public void deleteFicheroExterno(Long codigoFichero) {

        JFicheroExterno jFicheroExterno = entityManager.find(JFicheroExterno.class, codigoFichero);
        if (!jFicheroExterno.isBorrar() && !jFicheroExterno.isTemporal()) {
            jFicheroExterno.setBorrar(true);
            entityManager.merge(jFicheroExterno);
        }
    }

    @Override
    public void purgeFicherosExternos(String pathAlmacenamientoFicheros) {

        //Primero borramos los marcados como pendientes para borrar
        final Query query = entityManager.createQuery("select f from JFicheroExterno f where f.borrar = TRUE");
        final List<JFicheroExterno> results = query.getResultList();
        if (results != null) {
            for (final JFicheroExterno jFicheroExterno : results) {

                // Borramos fichero en BD (por si hay relaciones aun)
                entityManager.remove(jFicheroExterno);

                // Borramos fichero en disco
                final String pathAbsoluteFichero = pathAlmacenamientoFicheros + jFicheroExterno.getReferencia();
                final File file = new File(pathAbsoluteFichero);
                final boolean deleted = FileUtils.deleteQuietly(file);
                if (!deleted) {
                    LOG.error("No se ha podido borrar fichero " + pathAbsoluteFichero);
                    throw new FicheroExternoException("No se ha podido borrar el fichero : " + jFicheroExterno.getCodigo() + " con url : " + pathAbsoluteFichero);
                }
            }
        }

        //Segundo borramos los que llevan un dia creados y no se han confirmado
        final Query query2 = entityManager.createQuery("select f from JFicheroExterno f where f.temporal = TRUE and f.fecha <= TO_DATE(''" + getFechaAyer() + "','DD/MM/YYYY')");
        final List<JFicheroExterno> results2 = query2.getResultList();
        if (results2 != null) {
            for (final JFicheroExterno jFicheroExterno : results2) {

                // Borramos fichero en BD
                entityManager.remove(jFicheroExterno);

                // Borramos fichero en disco
                final String pathAbsoluteFichero = pathAlmacenamientoFicheros + jFicheroExterno.getReferencia();
                final File file = new File(pathAbsoluteFichero);
                final boolean deleted = FileUtils.deleteQuietly(file);
                if (!deleted) {
                    LOG.error("No se ha podido borrar fichero " + pathAbsoluteFichero);
                    throw new FicheroExternoException("No se ha podido borrar el fichero : " + jFicheroExterno.getCodigo() + " con url : " + pathAbsoluteFichero);
                }

            }
        }

    }

    @Override
    public FicheroRolsac1 getFicheroRolsac(long idArchivo, String rutaRolsac1) throws IOException {
        final Query query = entityManager.createNativeQuery("select ARC_CODI, ARC_NOMBRE,ARC_MIME,ARC_PESO,ARC_DATOS FROM R1_ARCHIV WHERE ARC_CODI = " + idArchivo);
        FicheroRolsac1 ficheroRolsac1 = new FicheroRolsac1();
        final Object[] resultado = (Object[]) query.getSingleResult();
        ficheroRolsac1.setCodigo(idArchivo);
        ficheroRolsac1.setFilename((String) resultado[1]);
        ficheroRolsac1.setMimetype((String) resultado[2]);
        ficheroRolsac1.setPeso((BigDecimal) resultado[3]);
        if (resultado[4] != null) {
            try {
                Blob blob = (Blob) resultado[4];
                byte[] bytes = blob.getBytes(1l, (int) blob.length());
                ficheroRolsac1.setContenido(bytes);
            } catch (SQLException e) {
                LOG.error("Error obteniendo bytes del idArchivo " + idArchivo, e);
            }
        }
        if (ficheroRolsac1.getContenido() == null) {
            ficheroRolsac1.setContenido(obtenerContenidoRolsac1(idArchivo, rutaRolsac1));
        } else {
            String parar = "";
        }

        return ficheroRolsac1;
    }

    private byte[] obtenerContenidoRolsac1(long idArchivo, String rutaRolsac1) throws IOException {
        byte[] contenido = null;
        try (InputStream in = new FileInputStream(obtenerRutaArchivoExportadoEnFilesystem(idArchivo, rutaRolsac1))) {
            contenido = IOUtils.toByteArray(in);
        } catch (final IOException e) {
            LOG.error("Error obteniendo fichero ", e);
            throw e;
        }
        return contenido;
    }

    private static String obtenerRutaArchivoExportadoEnFilesystem(final Long archivo, final String rutaRolsac1) throws IOException {
        return null;
    }

    /**
     * Metodo privado que obtiene la fecha de ayer en formato DD/MM/YYYY
     *
     * @return
     */
    private String getFechaAyer() {
        StringBuilder diaAYER = new StringBuilder();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -1);

        int dia = calendar.get(Calendar.DAY_OF_MONTH);
        diaAYER.append(dia < 10 ? "0" + dia : dia);

        diaAYER.append("/");

        int mes = calendar.get(Calendar.MONTH);
        diaAYER.append(mes < 10 ? "0" + mes : mes);

        diaAYER.append("/");

        diaAYER.append(calendar.get(Calendar.YEAR));

        return diaAYER.toString();
    }
}
