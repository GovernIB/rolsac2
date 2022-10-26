package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.persistence.model.JFicheroExterno;
import es.caib.rolsac2.service.exception.FicheroExternoException;
import es.caib.rolsac2.service.model.FicheroDTO;
import es.caib.rolsac2.service.model.types.TypeFicheroExterno;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.Query;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

/**
 * Implementación del repositorio de Personal.
 *
 * @author areus
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
        String pathFile = pathAlmacenamientoFicheros + "\\"
                + fic.getReferencia() + fic.getFilename();

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

            return cf;
        } catch (final IOException e) {
            throw new FicheroExternoException("Error al acceder al fichero "
                    + id + " con path " + pathFile, e);
        }
    }


    @Override
    public Long createFicheroExterno(byte[] content, String fileName, TypeFicheroExterno tipoFicheroExterno, Long elementoFicheroExterno, String pathAlmacenamientoFicheros) {
        if (tipoFicheroExterno == null || content == null || fileName == null || elementoFicheroExterno == null) {
            throw new FicheroExternoException("Ningún parámetro puede ser nulo");
        }

        JFicheroExterno jFicheroExterno = new JFicheroExterno();
        jFicheroExterno.setBorrar(false);
        jFicheroExterno.setFecha(new java.util.Date());
        jFicheroExterno.setIdElemento(elementoFicheroExterno);
        jFicheroExterno.setFilename(fileName);
        jFicheroExterno.setTemporal(true);
        jFicheroExterno.setTipo(tipoFicheroExterno.getTipo());
        jFicheroExterno.setReferencia(elementoFicheroExterno + tipoFicheroExterno.getRuta());
        entityManager.persist(jFicheroExterno);

        // Almacena fichero
        final String pathAbsolutoFichero = pathAlmacenamientoFicheros + "/"
                + jFicheroExterno.getReferencia() + jFicheroExterno.getFilename();
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
            throw new FicheroExternoException("Error al crear fichero externo "
                    + jFicheroExterno.getCodigo() + " con path " + pathAbsolutoFichero,
                    ex);
        }

        return jFicheroExterno.getCodigo();

    }

    @Override
    public void persistFicheroExterno(Long codigoFichero, Long idElemento, String pathAlmacenamientoFicheros) {
        JFicheroExterno jFicheroExterno = entityManager.find(JFicheroExterno.class, codigoFichero);
        if (!jFicheroExterno.isBorrar() && jFicheroExterno.isTemporal()) {
            jFicheroExterno.setTemporal(false);
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
        final Query query = entityManager.createQuery(
                "select f from JFicheroExterno f where f.borrar = TRUE");
        final List<JFicheroExterno> results = query.getResultList();
        if (results != null) {
            for (final JFicheroExterno jFicheroExterno : results) {

                // Borramos fichero en BD (por si hay relaciones aun)
                entityManager.remove(jFicheroExterno);

                // Borramos fichero en disco
                final String pathAbsoluteFichero = pathAlmacenamientoFicheros
                        + jFicheroExterno.getReferencia();
                final File file = new File(pathAbsoluteFichero);
                final boolean deleted = FileUtils.deleteQuietly(file);
                if (!deleted) {
                    LOG.error("No se ha podido borrar fichero " + pathAbsoluteFichero);
                    throw new FicheroExternoException("No se ha podido borrar el fichero : " + jFicheroExterno.getCodigo() + " con url : " + pathAbsoluteFichero);
                }
            }
        }

        //Segundo borramos los que llevan un dia creados y no se han confirmado
        final Query query2 = entityManager.createQuery(
                "select f from JFicheroExterno f where f.temporal = TRUE and f.fecha <= TO_DATE(''" + getFechaAyer() + "','DD/MM/YYYY')");
        final List<JFicheroExterno> results2 = query2.getResultList();
        if (results2 != null) {
            for (final JFicheroExterno jFicheroExterno : results2) {

                // Borramos fichero en BD
                entityManager.remove(jFicheroExterno);

                // Borramos fichero en disco
                final String pathAbsoluteFichero = pathAlmacenamientoFicheros
                        + jFicheroExterno.getReferencia();
                final File file = new File(pathAbsoluteFichero);
                final boolean deleted = FileUtils.deleteQuietly(file);
                if (!deleted) {
                    LOG.error("No se ha podido borrar fichero "
                            + pathAbsoluteFichero);
                    throw new FicheroExternoException("No se ha podido borrar el fichero : " + jFicheroExterno.getCodigo() + " con url : " + pathAbsoluteFichero);
                }

            }
        }

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
