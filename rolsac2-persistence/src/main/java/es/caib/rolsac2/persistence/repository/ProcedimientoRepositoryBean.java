package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.persistence.model.*;
import es.caib.rolsac2.persistence.model.pk.JProcedimientoMateriaSIAPK;
import es.caib.rolsac2.persistence.model.pk.JProcedimientoNormativaPK;
import es.caib.rolsac2.persistence.model.pk.JProcedimientoPublicoObjectivoPK;
import es.caib.rolsac2.service.model.NormativaGridDTO;
import es.caib.rolsac2.service.model.ProcedimientoGridDTO;
import es.caib.rolsac2.service.model.TipoMateriaSIAGridDTO;
import es.caib.rolsac2.service.model.TipoPublicoObjetivoEntidadGridDTO;
import es.caib.rolsac2.service.model.filtro.ProcedimientoFiltro;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementación del repositorio de Personal.
 *
 * @author Indra
 */
@Stateless
@Local(ProcedimientoRepository.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class ProcedimientoRepositoryBean extends AbstractCrudRepository<JProcedimiento, Long>
        implements ProcedimientoRepository {

    protected ProcedimientoRepositoryBean() {
        super(JProcedimiento.class);
    }

    @Override
    public List<ProcedimientoGridDTO> findPagedByFiltro(ProcedimientoFiltro filtro) {
        Query query = getQuery(false, filtro);
        query.setFirstResult(filtro.getPaginaFirst());
        query.setMaxResults(filtro.getPaginaTamanyo());

        List<Object[]> jprocs = query.getResultList();
        List<ProcedimientoGridDTO> procs = new ArrayList<>();
        if (jprocs != null) {
            for (Object[] jproc : jprocs) {
                ProcedimientoGridDTO procedimientoGridDTO = new ProcedimientoGridDTO();
                procedimientoGridDTO.setCodigo((Long) jproc[0]);
                procedimientoGridDTO.setTipo((String) jproc[1]);
                procedimientoGridDTO.setCodigoSIA((Integer) jproc[2]);
                procedimientoGridDTO.setEstadoSIA((Boolean) jproc[3]);
                procedimientoGridDTO.setSiaFecha((LocalDate) jproc[4]);
                procedimientoGridDTO.setCodigoDir3SIA((String) jproc[5]);
                procedimientoGridDTO.setNombre((String) jproc[6]);
                procs.add(procedimientoGridDTO);
            }
        }
        return procs;
    }

    @Override
    public long countByFiltro(ProcedimientoFiltro filtro) {
        return (long) getQuery(true, filtro).getSingleResult();
    }

    @Override
    public JProcedimientoWorkflow getWF(Long id, boolean procedimientoEnmodificacion) {
        StringBuilder sql = new StringBuilder("SELECT j FROM JProcedimientoWorkflow j where j.workflow = :workflow AND j.procedimiento.codigo = :codigoProc ");
        Query query = entityManager.createQuery(sql.toString());
        query.setParameter("workflow", procedimientoEnmodificacion);
        query.setParameter("codigoProc", id);
        JProcedimientoWorkflow jproc = null;

        List<JProcedimientoWorkflow> jprocs = query.getResultList();
        if (jprocs != null && !jprocs.isEmpty()) {
            jproc = jprocs.get(0);
        }
        return jproc;
    }

    @Override
    public void createWF(JProcedimientoWorkflow jProcWF) {
        entityManager.persist(jProcWF);
    }

    @Override
    public List<TipoMateriaSIAGridDTO> getMateriaGridSIAByWF(Long codigoWF) {
        List<TipoMateriaSIAGridDTO> lista = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
                "SELECT j FROM JProcedimientoMateriaSIA j where j.procedimientoWF.codigo = :codigoProcWF ");
        Query query = entityManager.createQuery(sql.toString());
        query.setParameter("codigoProcWF", codigoWF);
        List<JProcedimientoMateriaSIA> jlista = query.getResultList();
        if (jlista != null && !jlista.isEmpty()) {
            for (JProcedimientoMateriaSIA elemento : jlista) {
                lista.add(elemento.getTipoMateriaSIA().toModel());
            }
        }
        return lista;
    }

    @Override
    public void mergeMateriaSIAProcWF(Long codigoWF, List<TipoMateriaSIAGridDTO> listaNuevos) {
        StringBuilder sql = new StringBuilder(
                "SELECT j FROM JProcedimientoMateriaSIA j where j.procedimientoWF.codigo = :codigoProcWF ");
        Query query = entityManager.createQuery(sql.toString());
        query.setParameter("codigoProcWF", codigoWF);
        List<JProcedimientoMateriaSIA> jlista = query.getResultList();

        List<JProcedimientoMateriaSIA> borrar = new ArrayList<>();
        if (jlista != null && !jlista.isEmpty()) {
            for (JProcedimientoMateriaSIA jelemento : jlista) {
                boolean encontrado = false;
                if (listaNuevos != null) {
                    for (TipoMateriaSIAGridDTO elemento : listaNuevos) {
                        if (elemento.getCodigo() != null && elemento.getCodigo().compareTo(jelemento.getTipoMateriaSIA().getCodigo()) == 0) {
                            encontrado = true;
                            break;
                        }
                    }
                }

                if (!encontrado) {
                    borrar.add(jelemento);
                }
            }
        }

        if (!borrar.isEmpty()) {
            for (JProcedimientoMateriaSIA jelemento : borrar) {
                entityManager.remove(jelemento);
            }
            jlista.removeAll(borrar);
        }


        if (listaNuevos != null && !listaNuevos.isEmpty()) {
            JProcedimientoWorkflow jprocWF = entityManager.find(JProcedimientoWorkflow.class, codigoWF);
            for (TipoMateriaSIAGridDTO elemento : listaNuevos) {
                boolean encontrado = false;
                if (!jlista.isEmpty()) {
                    for (JProcedimientoMateriaSIA jelemento : jlista) {
                        if (elemento.getCodigo() != null && elemento.getCodigo().compareTo(jelemento.getTipoMateriaSIA().getCodigo()) == 0) {
                            encontrado = true;
                            break;
                        }
                    }
                }

                if (!encontrado) {
                    JProcedimientoMateriaSIA nuevo = new JProcedimientoMateriaSIA();
                    nuevo.setProcedimientoWF(jprocWF);
                    JTipoMateriaSIA jTipoMateriaSIA = entityManager.find(JTipoMateriaSIA.class, elemento.getCodigo());
                    nuevo.setTipoMateriaSIA(jTipoMateriaSIA);
                    JProcedimientoMateriaSIAPK id = new JProcedimientoMateriaSIAPK();
                    id.setTipoMateriaSIA(elemento.getCodigo());
                    id.setProcedimento(codigoWF);
                    nuevo.setCodigo(id);
                    entityManager.persist(nuevo);
                    entityManager.flush();
                }
            }
        }
    }


    @Override
    public List<TipoPublicoObjetivoEntidadGridDTO> getTipoPubObjEntByWF(Long codigoWF) {
        List<TipoPublicoObjetivoEntidadGridDTO> lista = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
                "SELECT j FROM JProcedimientoPublicoObjectivo j where j.procedimiento.codigo = :codigoProcWF ");
        Query query = entityManager.createQuery(sql.toString());
        query.setParameter("codigoProcWF", codigoWF);
        List<JProcedimientoPublicoObjectivo> jlista = query.getResultList();
        if (jlista != null && !jlista.isEmpty()) {
            for (JProcedimientoPublicoObjectivo elemento : jlista) {
                lista.add(elemento.toModel());
            }
        }
        return lista;
    }

    @Override
    public List<NormativaGridDTO> getNormativasByWF(Long codigoWF) {
        List<NormativaGridDTO> lista = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
                "SELECT j FROM JProcedimientoNormativa j where j.procedimiento.codigo = :codigoProcWF ");
        Query query = entityManager.createQuery(sql.toString());
        query.setParameter("codigoProcWF", codigoWF);
        List<JProcedimientoNormativa> jlista = query.getResultList();
        if (jlista != null && !jlista.isEmpty()) {
            for (JProcedimientoNormativa elemento : jlista) {
                lista.add(elemento.toModelGrid());
            }
        }
        return lista;
    }

    @Override
    public void deleteWF(Long codigoProc, boolean enmodificacion) {
        JProcedimientoWorkflow jprocWF = this.getWF(codigoProc, enmodificacion);
        if (jprocWF == null) {
            return;
        }

        StringBuilder sql = new StringBuilder(
                "SELECT j FROM JProcedimientoPublicoObjectivo j where j.procedimiento.codigo = :codigoProcWF ");
        Query query = entityManager.createQuery(sql.toString());
        query.setParameter("codigoProcWF", jprocWF.getCodigo());
        List<JProcedimientoPublicoObjectivo> jlista = query.getResultList();
        if (jlista != null) {
            for (JProcedimientoPublicoObjectivo jelemento : jlista) {
                entityManager.remove(jelemento);
            }
        }

        StringBuilder sqlSIA = new StringBuilder(
                "SELECT j FROM JProcedimientoMateriaSIA j where j.procedimientoWF.codigo = :codigoProcWF ");
        Query querySIA = entityManager.createQuery(sqlSIA.toString());
        querySIA.setParameter("codigoProcWF", jprocWF.getCodigo());
        List<JProcedimientoMateriaSIA> jlistaSIA = querySIA.getResultList();
        if (jlistaSIA != null) {
            for (JProcedimientoMateriaSIA jelementoSIA : jlistaSIA) {
                entityManager.remove(jelementoSIA);
            }
        }

        entityManager.remove(jprocWF);
    }


    @Override
    public void mergePublicoObjetivoProcWF(Long codigoWF, List<TipoPublicoObjetivoEntidadGridDTO> listaNuevos) {
        StringBuilder sql = new StringBuilder(
                "SELECT j FROM JProcedimientoPublicoObjectivo j where j.procedimiento.codigo = :codigoProcWF ");
        Query query = entityManager.createQuery(sql.toString());
        query.setParameter("codigoProcWF", codigoWF);
        List<JProcedimientoPublicoObjectivo> jlista = query.getResultList();

        List<JProcedimientoPublicoObjectivo> borrar = new ArrayList<>();
        if (jlista != null && !jlista.isEmpty()) {
            for (JProcedimientoPublicoObjectivo jelemento : jlista) {
                boolean encontrado = false;
                if (listaNuevos != null) {
                    for (TipoPublicoObjetivoEntidadGridDTO elemento : listaNuevos) {
                        if (elemento.getCodigo() != null && elemento.getCodigo().compareTo(jelemento.getTipoPublicoObjetivo().getCodigo()) == 0) {
                            encontrado = true;
                            break;
                        }
                    }
                }

                if (!encontrado) {
                    borrar.add(jelemento);
                }
            }
        }

        if (!borrar.isEmpty()) {
            for (JProcedimientoPublicoObjectivo jelemento : borrar) {
                entityManager.remove(jelemento);
            }
            jlista.removeAll(borrar);
        }


        if (listaNuevos != null && !listaNuevos.isEmpty()) {
            JProcedimientoWorkflow jprocWF = entityManager.find(JProcedimientoWorkflow.class, codigoWF);
            for (TipoPublicoObjetivoEntidadGridDTO elemento : listaNuevos) {
                boolean encontrado = false;
                if (!jlista.isEmpty()) {
                    for (JProcedimientoPublicoObjectivo jelemento : jlista) {
                        if (elemento.getCodigo() != null && elemento.getCodigo().compareTo(jelemento.getTipoPublicoObjetivo().getCodigo()) == 0) {
                            encontrado = true;
                            break;
                        }
                    }
                }

                if (!encontrado) {
                    JProcedimientoPublicoObjectivo nuevo = new JProcedimientoPublicoObjectivo();
                    nuevo.setProcedimiento(jprocWF);
                    JTipoPublicoObjetivoEntidad jtipoPublicoObjetivo = entityManager.find(JTipoPublicoObjetivoEntidad.class, elemento.getCodigo());
                    nuevo.setTipoPublicoObjetivo(jtipoPublicoObjetivo);
                    JProcedimientoPublicoObjectivoPK id = new JProcedimientoPublicoObjectivoPK();
                    id.setTipoPublicoObjetivo(elemento.getCodigo());
                    id.setProcedimiento(codigoWF);
                    nuevo.setCodigo(id);
                    entityManager.persist(nuevo);
                }
            }
        }
    }


    @Override
    public void mergeNormativaProcWF(Long codigoWF, List<NormativaGridDTO> listaNuevos) {
        StringBuilder sql = new StringBuilder(
                "SELECT j FROM JProcedimientoNormativa j where j.procedimiento.codigo = :codigoProcWF ");
        Query query = entityManager.createQuery(sql.toString());
        query.setParameter("codigoProcWF", codigoWF);
        List<JProcedimientoNormativa> jlista = query.getResultList();

        List<JProcedimientoNormativa> borrar = new ArrayList<>();
        if (jlista != null && !jlista.isEmpty()) {
            for (JProcedimientoNormativa jelemento : jlista) {
                boolean encontrado = false;
                if (listaNuevos != null) {
                    for (NormativaGridDTO elemento : listaNuevos) {
                        if (elemento.getCodigo() != null && elemento.getCodigo().compareTo(jelemento.getNormativa().getCodigo()) == 0) {
                            encontrado = true;
                            break;
                        }
                    }
                }

                if (!encontrado) {
                    borrar.add(jelemento);
                }
            }
        }

        if (!borrar.isEmpty()) {
            for (JProcedimientoNormativa jelemento : borrar) {
                entityManager.remove(jelemento);
            }
            jlista.removeAll(borrar);
        }


        if (listaNuevos != null && !listaNuevos.isEmpty()) {
            JProcedimientoWorkflow jprocWF = entityManager.find(JProcedimientoWorkflow.class, codigoWF);
            for (NormativaGridDTO elemento : listaNuevos) {
                boolean encontrado = false;
                if (!jlista.isEmpty()) {
                    for (JProcedimientoNormativa jelemento : jlista) {
                        if (elemento.getCodigo() != null && elemento.getCodigo().compareTo(jelemento.getNormativa().getCodigo()) == 0) {
                            encontrado = true;
                            break;
                        }
                    }
                }

                if (!encontrado) {
                    JProcedimientoNormativa nuevo = new JProcedimientoNormativa();
                    nuevo.setProcedimiento(jprocWF);
                    JNormativa jNormativa = entityManager.find(JNormativa.class, elemento.getCodigo());
                    nuevo.setNormativa(jNormativa);
                    JProcedimientoNormativaPK id = new JProcedimientoNormativaPK();
                    id.setNormativa(elemento.getCodigo());
                    id.setProcedimiento(codigoWF);
                    nuevo.setCodigo(id);
                    entityManager.persist(nuevo);
                }
            }
        }
    }

    @Override
    public void updateWF(JProcedimientoWorkflow jProcWF) {
        entityManager.merge(jProcWF);
    }

    private Query getQuery(boolean isTotal, ProcedimientoFiltro filtro) {

        StringBuilder sql;
        if (isTotal) {
            sql = new StringBuilder("SELECT count(j) FROM JProcedimiento j LEFT OUTER JOIN j.procedimientoWF WF ON wf.workflow = true LEFT OUTER JOIN WF.traducciones t ON t.idioma=:idioma where 1 = 1 ");
        } else {
            sql = new StringBuilder(
                    "SELECT j.codigo, j.tipo , j.codigoSIA, j.estadoSIA , j.siaFecha, j.codigoDir3SIA, t.nombre FROM JProcedimiento j LEFT OUTER JOIN j.procedimientoWF WF ON wf.workflow = true LEFT OUTER JOIN WF.traducciones t ON t.idioma=:idioma where 1 = 1 ");
        }

        if (filtro.isRellenoTexto()) {
            sql.append(" and ( LOWER(cast(j.codigo as string)) like :filtro "
                    + "OR LOWER(j.tipo) LIKE :filtro  OR LOWER(cast(j.codigoSIA as string)) LIKE :filtro"
                    + " OR LOWER(cast(j.estadoSIA as string)) LIKE :filtro OR LOWER(cast(j.codigoDir3SIA as string)) LIKE :filtro )");
        }
        if (filtro.isRellenoFormaInicio()) {
            sql.append(" AND WF.formaInicio.codigo = :formaInicio ");
        }
        if (filtro.isRellenoPublicoObjetivo()) {
            sql.append(" AND exists (select pubObj from JProcedimientoPublicoObjectivo pubObj where pubObj.tipoPublicoObjetivo = :tipoPublicoObjetivo and pubObj.procedimiento.codigo = WF.codigo ) ");
        }
        if (filtro.isRellenoTipoProcedimiento()) {
            sql.append(" AND WF.tipoProcedimiento.codigo = :tipoProcedimiento ");
        }
        if (filtro.isRellenoSilencioAdministrativo()) {
            sql.append(" AND WF.silencioAdministrativo.codigo = :tipoSilencio ");
        }
        if (filtro.isRellenoVolcadoSIA()) {
            switch (filtro.getVolcadoSIA()) {
                case "S":
                    sql.append(" AND j.codigoSIA is not null ");
                    break;
                case "N":
                    sql.append(" AND j.codigoSIA is null ");
                    break;
                default:
                    break;
            }
        }
        if (filtro.isRellenoCodigoSIA()) {
            sql.append(" AND j.codigoSIA = :codigoSIA ");
        }

        Query query = entityManager.createQuery(sql.toString());
        query.setParameter("idioma", filtro.getIdioma());
        if (filtro.isRellenoTexto()) {
            query.setParameter("filtro", "%" + filtro.getTexto().toLowerCase() + "%");
        }
        if (filtro.isRellenoFormaInicio()) {
            query.setParameter("formaInicio", filtro.getFormaInicio().getCodigo());
        }
        if (filtro.isRellenoPublicoObjetivo()) {
            query.setParameter("tipoPublicoObjetivo", filtro.getPublicoObjetivo().getCodigo());
        }
        if (filtro.isRellenoTipoProcedimiento()) {
            query.setParameter("tipoProcedimiento", filtro.getTipoProcedimiento().getCodigo());
        }
        if (filtro.isRellenoSilencioAdministrativo()) {
            query.setParameter("tipoSilencio", filtro.getSilencioAdministrativo().getCodigo());
        }
        if (filtro.isRellenoCodigoSIA()) {
            query.setParameter("codigoSIA", filtro.getCodigoSIA());
        }
        /*
         * if (filtro.isRellenoCodigoSIA()) { query.setParameter("tipo", "%" + filtro.getTipo().toLowerCase() + "%"); }
         * if (filtro.isRellenoCodigoSIA()) { query.setParameter("codigoSIA", "%" + filtro.getCodigoSIA().toLowerCase()
         * + "%"); } if (filtro.isRellenoEstadoSIA()) { query.setParameter("estadoSIA", "%" +
         * filtro.getEstadoSIA().toLowerCase() + "%"); } if (filtro.isRellenoSiaFecha()) {
         * query.setParameter("siaFecha", "%" + filtro.getSiaFecha().toLowerCase() + "%"); } if
         * (filtro.isRellenoCodigoDir3SIA()) { query.setParameter("codigoDir3SIA)", filtro.getCodigoDir3SIA()); }
         */

        return query;
    }

    private String getOrden(String order) {
        // Se puede hacer un switch/if pero en este caso, con j.+order sobra
        return "j." + order;
    }

    @Override
    public Optional<JProcedimiento> findById(String id) {
        TypedQuery<JProcedimiento> query =
                entityManager.createNamedQuery(JProcedimiento.FIND_BY_ID, JProcedimiento.class);
        query.setParameter("codigo", id);
        List<JProcedimiento> result = query.getResultList();
        return Optional.ofNullable(result.isEmpty() ? null : result.get(0));
    }
}
