package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.persistence.converter.AyudaConverter;
import es.caib.rolsac2.persistence.converter.EntidadConverter;
import es.caib.rolsac2.persistence.model.JAyuda;
import es.caib.rolsac2.service.model.*;
import es.caib.rolsac2.service.model.filtro.AyudaFiltro;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Stateless
@Local(AyudaRepository.class)
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class AyudaRepositoryBean extends AbstractCrudRepository<JAyuda, Long> implements AyudaRepository {

    protected AyudaRepositoryBean() {
        super(JAyuda.class);
    }

    @Inject
    private AyudaConverter converter;

    @Inject
    private EntidadConverter entidadConverter;


    @Override
    public List<AyudaGridDTO> findAyudaPageByFiltro(AyudaFiltro filtro) {
        Query query = getQueryAyudaUsuario(false, filtro);
        query.setFirstResult(filtro.getPaginaFirst());
        query.setMaxResults(filtro.getPaginaTamanyo());

        List<Object[]> jAyuda = query.getResultList();
        List<AyudaGridDTO> alertaGridDTOS = new ArrayList<>();

        if (jAyuda != null) {
            for (Object[] alerta : jAyuda) {
                AyudaGridDTO alertaGridDTO = new AyudaGridDTO();
                alertaGridDTO.setCodigo((Long) alerta[0]);
                alertaGridDTO.setIdentificador((String) alerta[1]);
                alertaGridDTO.setPerfil((String) alerta[2]);
                if (alerta[4] != null) {
                    Literal desc = new Literal();
                    desc.add(new Traduccion(filtro.getIdioma(), (String) alerta[4]));
                    alertaGridDTO.setDescripcion(desc);
                }
                alertaGridDTO.setFechaCreacion((Date) alerta[5]);
                alertaGridDTO.setFechaModificacion((Date) alerta[6]);
                alertaGridDTOS.add(alertaGridDTO);
            }
        }
        return alertaGridDTOS;
    }

    @Override
    public List<AyudaGridDTO> findPageByFiltro(AyudaFiltro filtro) {
        Query query = getQuery(false, filtro, false);
        query.setFirstResult(filtro.getPaginaFirst());
        query.setMaxResults(filtro.getPaginaTamanyo());

        List<Object[]> jAyuda = query.getResultList();
        List<AyudaGridDTO> alertaGridDTOS = new ArrayList<>();

        if (jAyuda != null) {
            for (Object[] alerta : jAyuda) {
                AyudaGridDTO alertaGridDTO = new AyudaGridDTO();
                alertaGridDTO.setCodigo((Long) alerta[0]);
                alertaGridDTO.setIdentificador((String) alerta[1]);
                alertaGridDTO.setPerfil((String) alerta[2]);
                if (alerta[4] != null) {
                    Literal desc = new Literal();
                    desc.add(new Traduccion(filtro.getIdioma(), (String) alerta[4]));
                    alertaGridDTO.setDescripcion(desc);
                }
                alertaGridDTO.setFechaCreacion((Date) alerta[5]);
                alertaGridDTO.setFechaModificacion((Date) alerta[6]);
                alertaGridDTOS.add(alertaGridDTO);
            }
        }
        return alertaGridDTOS;
    }

    /**
     * Borra todas las alertas usuario que hubiesen anteriormente.
     *
     * @param idAyuda
     */
    @Override
    public void borrarAyudaById(Long idAyuda) {
        StringBuilder sql = new StringBuilder("DELETE FROM JAyudaUsuario jau WHERE jau.alerta.codigo = :idAyuda");
        Query query = entityManager.createQuery(sql.toString());
        query.setParameter("idAyuda", idAyuda);
        query.executeUpdate();
    }

    private Query getQueryAyudaUsuario(boolean isTotal, AyudaFiltro filtro) {
        StringBuilder sql;
        if (isTotal) {
            sql = new StringBuilder("SELECT count(jau) FROM JAyudaUsuario jau LEFT OUTER JOIN jau.alerta j LEFT OUTER JOIN j.traducciones t ON t.idioma=:idioma " + " where 1 = 1 ");
        } else {
            sql = new StringBuilder("SELECT j.codigo, j.entidad, j.tipo, j.ambito, t.descripcion, j.fechaIni, j.fechaFin, jau.fecha FROM JAyudaUsuario jau LEFT OUTER JOIN jau.alerta j LEFT OUTER JOIN j.traducciones t ON t.idioma=:idioma where 1 = 1 ");
        }
        if (filtro.isRellenoTexto()) {
            sql.append(" and (LOWER (t.descripcion) LIKE :filtro OR cast(j.codigo as string) like :filtro " + " OR LOWER (j.ambito) LIKE :filtro OR LOWER (j.tipo) LIKE :filtro ) ");
        }
        if (filtro.isRellenoEntidad()) {
            sql.append(" and j.entidad.codigo =:idEntidad ");
        }

        if (filtro.isRellenoCodigo()) {
            sql.append(" and j.codigo =:codigo ");
        }

        if (filtro.isRellenoIdentificador()) {
            sql.append(" and LOWER (j.identificador) LIKE :identificador ");
        }

        if (filtro.isRellenoUsuario()) {
            sql.append(" and LOWER (jau.usuario) LIKE :usuario ");
        }


        if (filtro.getOrderBy() != null) {
            sql.append(" order by ").append(getOrden(filtro.getOrderBy()));
            sql.append(filtro.isAscendente() ? " asc " : " desc ");
        }

        Query query = entityManager.createQuery(sql.toString());

        if (filtro.isRellenoTexto()) {
            query.setParameter("filtro", "%" + filtro.getTexto().toLowerCase() + "%");
        }
        if (filtro.isRellenoIdentificador()) {
            query.setParameter("identificador", "%" + filtro.getIdentificador().toLowerCase() + "%");
        }
        if (filtro.isRellenoIdioma()) {
            query.setParameter("idioma", filtro.getIdioma());
        }
        if (filtro.isRellenoEntidad()) {
            query.setParameter("idEntidad", filtro.getIdEntidad());
        }
        if (filtro.isRellenoCodigo()) {
            query.setParameter("codigo", filtro.getCodigo());
        }
        if (filtro.isRellenoUsuario()) {
            query.setParameter("usuario", filtro.getUsuario());
        }

        return query;
    }


    private Query getQuery(boolean isTotal, AyudaFiltro filtro, boolean isRest) {
        StringBuilder sql;
        if (isTotal) {
            sql = new StringBuilder("SELECT count(j) FROM JAyuda j LEFT OUTER JOIN j.traducciones t ON t.idioma=:idioma " + " where 1 = 1 ");
        } else if (isRest) {
            sql = new StringBuilder("SELECT j FROM JAyuda j LEFT OUTER JOIN j.traducciones t ON t.idioma=:idioma where 1 = 1 ");
        } else {
            sql = new StringBuilder("SELECT j.codigo, j.entidad, j.tipo, j.ambito, t.descripcion, j.fechaIni, j.fechaFin FROM JAyuda j LEFT OUTER JOIN j.traducciones t ON t.idioma=:idioma where 1 = 1 ");
        }
        if (filtro.isRellenoTexto()) {
            sql.append(" and (LOWER (t.descripcion) LIKE :filtro OR cast(j.codigo as string) like :filtro " + " OR LOWER (j.ambito) LIKE :filtro OR LOWER (j.tipo) LIKE :filtro ) ");
        }
        if (filtro.isRellenoEntidad()) {
            sql.append(" and j.entidad.codigo =:idEntidad ");
        }

        if (filtro.isRellenoCodigo()) {
            sql.append(" and j.codigo =:codigo ");
        }

        if (filtro.isRellenoIdentificador()) {
            sql.append(" and LOWER (j.identificador) LIKE :identificador ");
        }

        if (filtro.getOrderBy() != null) {
            sql.append(" order by ").append(getOrden(filtro.getOrderBy()));
            sql.append(filtro.isAscendente() ? " asc " : " desc ");
        }

        Query query = entityManager.createQuery(sql.toString());

        if (filtro.isRellenoTexto()) {
            query.setParameter("filtro", "%" + filtro.getTexto().toLowerCase() + "%");
        }
        if (filtro.isRellenoIdentificador()) {
            query.setParameter("identificador", "%" + filtro.getIdentificador().toLowerCase() + "%");
        }
        if (filtro.isRellenoIdioma()) {
            query.setParameter("idioma", filtro.getIdioma());
        }
        if (filtro.isRellenoEntidad()) {
            query.setParameter("idEntidad", filtro.getIdEntidad());
        }
        if (filtro.isRellenoCodigo()) {
            query.setParameter("codigo", filtro.getCodigo());
        }

        return query;
    }

    private String getOrden(String order) {
        return "j." + order;
    }

    @Override
    public AyudaDTO getAyuda(String identificador, String perfil) {
        JAyuda jayuda = getJAyuda(identificador, perfil);
        AyudaDTO ayuda;
        if (jayuda == null) {
            ayuda = null;
        } else {
            ayuda = converter.createDTO(jayuda);
        }
        return ayuda;
    }


    @Override
    public AyudaGridDTO getAyudaGrid(String identificador, String perfil) {
        List<AyudaDTO> alertas = new ArrayList<>();
        StringBuilder sql = null;
        JAyuda jayuda = getJAyuda(identificador, perfil);
        AyudaGridDTO ayuda;
        if (jayuda == null) {
            ayuda = null;
        } else {
            ayuda = converter.createGridDTO(jayuda);
        }
        return ayuda;
    }

    private JAyuda getJAyuda(String identificador, String perfil) {

        String sql = " SELECT J FROM JAyuda J WHERE J.identificador = :identificador ";
        Query query = entityManager.createQuery(sql, JAyuda.class);
        query.setParameter("identificador", identificador);
        List<JAyuda> jayudas = query.getResultList();
        AyudaDTO ayuda = null;
        if (jayudas != null) {

            for (JAyuda jayuda : jayudas) {
                if (jayuda.getPerfil().equals(perfil)) {
                    return jayuda;
                }
            }

            for (JAyuda jayuda : jayudas) {
                if (jayuda.getPerfil().equals("TODOS")) {
                    return jayuda;
                }
            }
        }

        return null;
    }

    @Override
    public long countAyudaByFiltro(AyudaFiltro filtro) {
        return (long) getQueryAyudaUsuario(true, filtro).getSingleResult();
    }

    @Override
    public List<AyudaImagenGridDTO> getImagenes() {
        List<AyudaImagenGridDTO> imgsRetorno = new ArrayList<>();

        //Obtener de ficheros (JFicheroExterno) aquellos que son de tipo imagen
        String sql = "SELECT f.FIE_CODIGO, f.FIE_FILENAME, f.FIE_REFDOC, " +
                "  ( " +
                "    SELECT COUNT(t.TAY_CODIGO)\n" +
                "    FROM RS2_TRAAYU t\n" +
                "    WHERE t.TAY_HTML LIKE '%' || SUBSTR(f.FIE_REFDOC, INSTR(f.FIE_REFDOC, '/') + 1) || '%'\n" +
                "  ) AS total\n" +
                "FROM RS2_FICEXT f\n WHERE f.FIE_FICTIP LIKE 'AYUDASIMG'\n";
        Query query = entityManager.createNativeQuery(sql);
        List<Object[]> ficheroExternos = query.getResultList();
        if (ficheroExternos != null) {
            for (Object[] jFicheroExterno : ficheroExternos) {
                AyudaImagenGridDTO img = new AyudaImagenGridDTO();
                img.setCodigo(((BigDecimal) jFicheroExterno[0]).longValue());
                img.setFilename((String) jFicheroExterno[1]);
                img.setRuta((String) jFicheroExterno[2]);
                img.setTotal(((BigDecimal) jFicheroExterno[3]).longValue());
                img.setExisteJFichero(true);
                img.setExisteFicheroFisico(false);
                imgsRetorno.add(img);
            }
        }
        return imgsRetorno;
    }


    private Literal createLiteral(String literalStr, String idioma) {
        Literal literal = new Literal();
        literal.add(new Traduccion(idioma, literalStr));
        return literal;
    }


}
