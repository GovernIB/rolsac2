package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.commons.plugins.indexacion.api.model.ResultadoAccion;
import es.caib.rolsac2.commons.plugins.sia.api.model.ResultadoSIA;
import es.caib.rolsac2.persistence.converter.PlatTramitElectronicaConverter;
import es.caib.rolsac2.persistence.converter.ProcedimientoConverter;
import es.caib.rolsac2.persistence.converter.ProcedimientoTramiteConverter;
import es.caib.rolsac2.persistence.converter.TipoTramitacionConverter;
import es.caib.rolsac2.persistence.model.*;
import es.caib.rolsac2.persistence.model.pk.JProcedimientoMateriaSIAPK;
import es.caib.rolsac2.persistence.model.pk.JProcedimientoNormativaPK;
import es.caib.rolsac2.persistence.model.pk.JProcedimientoPublicoObjectivoPK;
import es.caib.rolsac2.persistence.model.traduccion.JProcedimientoDocumentoTraduccion;
import es.caib.rolsac2.persistence.model.traduccion.JTipoTramitacionTraduccion;
import es.caib.rolsac2.service.facade.ProcedimientoServiceFacade;
import es.caib.rolsac2.service.model.*;
import es.caib.rolsac2.service.model.filtro.ProcedimientoFiltro;
import es.caib.rolsac2.service.model.filtro.ProcesoSolrFiltro;
import es.caib.rolsac2.service.model.types.TypeIndexacion;
import es.caib.rolsac2.service.model.types.TypeProcedimientoWorkflow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Implementación del repositorio de Personal.
 *
 * @author Indra
 */
@Stateless
@Local(ProcedimientoRepository.class)
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class ProcedimientoRepositoryBean extends AbstractCrudRepository<JProcedimiento, Long> implements ProcedimientoRepository {

    private static final Logger LOG = LoggerFactory.getLogger(ProcedimientoRepositoryBean.class);

    @Inject
    FicheroExternoRepository ficheroExternoRepository;

    @Inject
    private ProcedimientoConverter converter;

    @Inject
    private ProcedimientoServiceFacade procedimientoService;

    protected ProcedimientoRepositoryBean() {
        super(JProcedimiento.class);
    }

    @Override
    public List<ServicioGridDTO> findServiciosPagedByFiltro(ProcedimientoFiltro filtro) {
        Query query = getQuery(false, filtro);
        query.setFirstResult(filtro.getPaginaFirst());
        query.setMaxResults(filtro.getPaginaTamanyo());

        List<Object[]> jprocs = query.getResultList();
        List<ServicioGridDTO> procs = new ArrayList<>();
        if (jprocs != null) {
            for (Object[] jproc : jprocs) {
                ServicioGridDTO procedimientoGridDTO = new ServicioGridDTO();
                procedimientoGridDTO.setCodigo((Long) jproc[0]);
                procedimientoGridDTO.setCodigoWFPub((Long) jproc[1]);
                procedimientoGridDTO.setCodigoWFMod((Long) jproc[2]);
                procedimientoGridDTO.setEstado((String) jproc[3]);
                procedimientoGridDTO.setTipo((String) jproc[4]);
                procedimientoGridDTO.setCodigoSIA((Integer) jproc[5]);
                procedimientoGridDTO.setEstadoSIA((String) jproc[6]);
                procedimientoGridDTO.setSiaFecha((Date) jproc[7]);
                procedimientoGridDTO.setCodigoDir3SIA((String) jproc[8]);
                String t1 = (String) jproc[9]; //Nombre wf publicado
                String t2 = (String) jproc[10]; //Nombre wf en edicion
                String nombre;
                if (t1 != null && !t1.isEmpty()) {
                    nombre = t1;
                } else {
                    nombre = t2;
                }
                procedimientoGridDTO.setNombre(nombre);
                procedimientoGridDTO.setFechaActualizacion((Date) jproc[13]);
                Integer comun1 = (Integer) jproc[14];
                Integer comun2 = (Integer) jproc[15];
                Boolean comun = null;
                if (comun1 != null) {
                    comun = (comun1 == 1);
                } else if (comun2 != null) {
                    comun = (comun2 == 1);
                }
                procedimientoGridDTO.setComun(comun);

                procedimientoGridDTO.setFechaPublicacion((Date) jproc[17]);
                procedimientoGridDTO.setFechaDespublicacion((Date) jproc[18]);
                procedimientoGridDTO.setMensajesPendienteGestor((Boolean) jproc[19]);
                procedimientoGridDTO.setMensajesPendienteSupervisor((Boolean) jproc[20]);

                procs.add(procedimientoGridDTO);
            }
        }
        return procs;
    }

    @Override
    public List<ProcedimientoGridDTO> findProcedimientosPagedByFiltro(ProcedimientoFiltro filtro) {
        Query query = getQuery(false, filtro);
        query.setFirstResult(filtro.getPaginaFirst());
        query.setMaxResults(filtro.getPaginaTamanyo());

        List<Object[]> jprocs = query.getResultList();
        List<ProcedimientoGridDTO> procs = new ArrayList<>();
        if (jprocs != null) {
            for (Object[] jproc : jprocs) {
                ProcedimientoGridDTO procedimientoGridDTO = new ProcedimientoGridDTO();
                procedimientoGridDTO.setCodigo((Long) jproc[0]);
                procedimientoGridDTO.setCodigoWFPub((Long) jproc[1]);
                procedimientoGridDTO.setCodigoWFMod((Long) jproc[2]);
                procedimientoGridDTO.setEstado((String) jproc[3]);
                procedimientoGridDTO.setTipo((String) jproc[4]);
                procedimientoGridDTO.setCodigoSIA((Integer) jproc[5]);
                procedimientoGridDTO.setEstadoSIA((String) jproc[6]);
                procedimientoGridDTO.setSiaFecha((Date) jproc[7]);
                procedimientoGridDTO.setCodigoDir3SIA((String) jproc[8]);
                String t1 = (String) jproc[9]; //Nombre wf publicado
                String t2 = (String) jproc[10]; //Nombre wf en edicion
                String nombre;
                if (t1 != null && !t1.isEmpty()) {
                    nombre = t1;
                } else {
                    nombre = t2;
                }
                procedimientoGridDTO.setNombre(nombre);
                String tipo1 = (String) jproc[11]; //Nombre wf publicado
                String tipo2 = (String) jproc[12]; //Nombre wf en edicion
                String tipoProcedimiento;
                if (tipo1 != null && !tipo1.isEmpty()) {
                    tipoProcedimiento = tipo1;
                } else {
                    tipoProcedimiento = tipo2;
                }
                procedimientoGridDTO.setTipoProcedimiento(tipoProcedimiento);
                procedimientoGridDTO.setFechaActualizacion((Date) jproc[13]);
                Integer comun1 = (Integer) jproc[14];
                Integer comun2 = (Integer) jproc[15];
                Boolean comun = null;
                if (comun1 != null) {
                    comun = (comun1 == 1);
                } else if (comun2 != null) {
                    comun = (comun2 == 1);
                }
                procedimientoGridDTO.setComun(comun);
                if (jproc[16] != null) {
                    String[] valores = ((String) jproc[16]).split("#");
                    if (valores.length == 3 || valores.length == 2) {
                        if (valores[0] != null) {
                            procedimientoGridDTO.setTramiteInicioCodigo(Long.valueOf(valores[0]));
                        }
                        final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                        if (valores[1] != null) {
                            try {
                                Date fecha = sdf.parse((String) valores[1]);
                                procedimientoGridDTO.setTramiteInicioFechaPublicacion(fecha);
                            } catch (ParseException e) {
                                LOG.error("Error parseando en el codigo " + procedimientoGridDTO.getCodigo() + " la fecha Pub Tram :" + valores[1], e);
                            }

                        }
                        if (valores.length == 3 && valores[2] != null) {
                            try {
                                Date fecha = sdf.parse((String) valores[2]);
                                procedimientoGridDTO.setTramiteIniciofechaCierre(fecha);
                            } catch (ParseException e) {
                                LOG.error("Error parseando en el codigo " + procedimientoGridDTO.getCodigo() + " la fecha Cierre Tram :" + valores[1], e);
                            }
                        }
                    }
                }

                procedimientoGridDTO.setMensajesPendienteGestor((Boolean) jproc[19]);
                procedimientoGridDTO.setMensajesPendienteSupervisor((Boolean) jproc[20]);

                procs.add(procedimientoGridDTO);
            }
        }
        return procs;
    }

    @Override
    public List<ProcedimientoBaseDTO> findProcedimientosPagedByFiltroRest(ProcedimientoFiltro filtro) {
        Query query = getQuery(false, filtro, true);
        query.setFirstResult(filtro.getPaginaFirst());
        query.setMaxResults(filtro.getPaginaTamanyo());
        List<ProcedimientoBaseDTO> procs = new ArrayList<>();


        //3opciones todos (si publicado envio publi, si pubnli=null envio mod, )solo publi, solo mod

        JProcedimientoWorkflow seleccionado = null;

        if (filtro.getEstadoWF() != null) {
            switch (filtro.getEstadoWF()) {
                case "D":
                case "M":
                    List<JProcedimientoWorkflow> jprocsL = query.getResultList();
                    for (Object proc : jprocsL) {
                        if (proc != null) {
                            seleccionado = (JProcedimientoWorkflow) proc;

                            if (seleccionado != null) {
                                ProcedimientoBaseDTO procDTO = procedimientoService.convertirDTO(seleccionado);
                                procs.add(procDTO);
                            }
                        }
                    }
                    break;
                case "A":
                    List<JProcedimientoWorkflow> jprocsA = query.getResultList();
                    for (Object proc : jprocsA) {
                        if (proc != null) {
                            JProcedimientoWorkflow seleccionadoA = (JProcedimientoWorkflow) proc;

                            ProcedimientoBaseDTO procDTO = procedimientoService.convertirDTO(seleccionadoA);
                            procs.add(procDTO);


                        }
                    }
                    break;
                case "T":
                default:
                    List<JProcedimientoWorkflow[]> jprocs = query.getResultList();
                    for (Object[] proc : jprocs) {
                        if (proc != null) {
                            JProcedimientoWorkflow modificado = (JProcedimientoWorkflow) proc[0];
                            JProcedimientoWorkflow publicado = (JProcedimientoWorkflow) proc[1];

                            if (publicado != null) {
                                seleccionado = publicado;
                            } else {
                                seleccionado = modificado;
                            }

                            if (seleccionado != null) {
                                ProcedimientoBaseDTO procDTO = procedimientoService.convertirDTO(seleccionado);
                                procs.add(procDTO);
                            }

                        }

                    }
                    break;

            }
            //	if (jprocs != null) {
            //  for (JProcedimiento jproc : jprocs) {


            //  }
            // }

        } else {
            List<JProcedimientoWorkflow> jprocsA = query.getResultList();
            for (Object proc : jprocsA) {
                if (proc != null) {
                    JProcedimientoWorkflow seleccionadoA = (JProcedimientoWorkflow) proc;

                    ProcedimientoBaseDTO procDTO = procedimientoService.convertirDTO(seleccionadoA);
                    procs.add(procDTO);


                }
            }
        }

        return procs;
    }

    @Override
    public long countByFiltro(ProcedimientoFiltro filtro) {
        return (long) getQuery(true, filtro).getSingleResult();
    }

    @Override
    public Long countByEntidad(Long entidadId) {
        String sql = "SELECT count(a) FROM JProcedimiento a LEFT OUTER JOIN a.procedimientoWF b LEFT OUTER JOIN b.uaResponsable c LEFT OUTER JOIN c.entidad d WHERE d.codigo= :entidadId  and a.tipo='P' ";
        TypedQuery<Long> query = entityManager.createQuery(sql, Long.class);
        query.setParameter("entidadId", entidadId);
        return query.getSingleResult();
    }

    @Override
    public Long countServicioByEntidad(Long entidadId) {
        String sql = "SELECT count(a) FROM JProcedimiento a LEFT OUTER JOIN a.procedimientoWF b LEFT OUTER JOIN b.uaResponsable c LEFT OUTER JOIN c.entidad d WHERE d.codigo= :entidadId and a.tipo='S' ";
        TypedQuery<Long> query = entityManager.createQuery(sql, Long.class);
        query.setParameter("entidadId", entidadId);
        return query.getSingleResult();
    }


    @Override
    public Long getCodigoByWF(Long id, boolean procedimientoEnmodificacion) {
        StringBuilder sql = new StringBuilder("SELECT j.codigo FROM JProcedimientoWorkflow j where j.workflow = :workflow AND j.procedimiento.codigo = :codigoProc ");
        Query query = entityManager.createQuery(sql.toString());
        query.setParameter("workflow", procedimientoEnmodificacion);
        query.setParameter("codigoProc", id);
        Long jproc = null;

        List<Long> jprocs = query.getResultList();
        if (jprocs != null && !jprocs.isEmpty()) {
            jproc = jprocs.get(0);
        }
        return jproc;
    }

    @Override
    public JProcedimientoWorkflow getWFByCodigoWF(Long codigoWF) {
        StringBuilder sql = new StringBuilder("SELECT j FROM JProcedimientoWorkflow j where j.codigo = :codigoWF ");
        Query query = entityManager.createQuery(sql.toString());
        query.setParameter("codigoWF", codigoWF);
        JProcedimientoWorkflow jproc = null;

        List<JProcedimientoWorkflow> jprocs = query.getResultList();
        if (jprocs != null && !jprocs.isEmpty()) {
            jproc = jprocs.get(0);
        }
        return jproc;
    }

    @Override
    public Long obtenerCountPendientesIndexar(boolean pendientesIndexar, String tipo, ProcesoSolrFiltro filtro) {
        StringBuilder sql;
        Query query = getQuerySolr(true, pendientesIndexar, tipo, filtro);
        return (Long) query.getSingleResult();
    }


    private Query getQuerySolr(boolean total, boolean pendientesIndexar, String tipo, ProcesoSolrFiltro filtro) {
        StringBuilder sql;
        if (total) {
            sql = new StringBuilder("SELECT count(*) ");
        } else {
            sql = new StringBuilder("SELECT j.codigo, j.fechaInicioIndexacion, j.fechaIndexacion, j.mensajeIndexacion, j.tipo ");
        }
        sql.append("  FROM JProcedimiento j where 1 = 1  ");
        if (tipo != null) {
            sql.append(" AND j.tipo = :tipo ");
        }
        if (pendientesIndexar) {
            sql.append(" AND j.pendienteIndexar = true ");
        }

        Query query = entityManager.createQuery(sql.toString());
        if (tipo != null) {
            query.setParameter("tipo", tipo);
        }
        if (!total && filtro != null && filtro.isPaginacionActiva()) {
            query.setMaxResults(filtro.getPaginaTamanyo());
            query.setFirstResult(filtro.getPaginaFirst());
        }
        return query;

    }

    @Override
    public void actualizarSolr(IndexacionDTO dato, ResultadoAccion resultadoAccion) {
        JProcedimiento jproc = entityManager.find(JProcedimiento.class, dato.getCodElemento());
        jproc.setFechaIndexacion(new Date());
        jproc.setMensajeIndexacion(resultadoAccion.getMensaje());
        entityManager.merge(jproc);
    }

    @Override
    public void actualizarSIA(IndexacionSIADTO dato, ResultadoSIA resultadoAccion) {
        JProcedimiento jproc = entityManager.find(JProcedimiento.class, dato.getCodElemento());
        if (resultadoAccion.isCorrecto() || (resultadoAccion.getMensaje() != null && resultadoAccion.getMensaje().startsWith("0167"))) {
            jproc.setSiaFecha(new Date());
            if (resultadoAccion.getCodSIA() != null) {
                //Si es una baja, ya no se pasa
                jproc.setCodigoSIA(Integer.parseInt(resultadoAccion.getCodSIA()));
            }
            jproc.setEstadoSIA(resultadoAccion.getEstadoSIA());
            jproc.setMensajeIndexacionSIA("");
            entityManager.merge(jproc);
        } else {
            jproc.setMensajeIndexacionSIA(resultadoAccion.getMensaje());
            entityManager.merge(jproc);
        }

    }

    @Override
    public Long getUAbyCodProcedimiento(Long codProcedimiento) {
        JProcedimientoWorkflow jprocWF = getWF(codProcedimiento, Constantes.PROCEDIMIENTO_DEFINITIVO);
        if (jprocWF == null) {
            jprocWF = getWF(codProcedimiento, Constantes.PROCEDIMIENTO_ENMODIFICACION);
        }
        if (jprocWF == null) {
            return null;
        }
        return jprocWF.getUaInstructor().getCodigo();
    }


    @Override
    public void actualizarFechaActualizacion(Long codigo) {
        //JProcedimiento jproc = entityManager.find(JProcedimiento.class, codigo);
        //jproc.setFechaActualizacion(new Date());
        //entityManager.merge(jproc);
        entityManager.flush();
        Query query = entityManager.createQuery("update JProcedimiento set fechaActualizacion = :fecha where codigo = :codigo");
        query.setParameter("codigo", codigo);
        query.setParameter("fecha", new Date());
        query.executeUpdate();
    }

    @Override
    public String getMensajesByCodigo(Long codigo) {
        JProcedimiento jproc = entityManager.find(JProcedimiento.class, codigo);
        return jproc.getMensajes();
    }

    @Override
    public Pagina<IndexacionDTO> getProcedimientosParaIndexacion(boolean isTipoProcedimiento, Long idEntidad) {
        StringBuilder sql = new StringBuilder("SELECT j.codigo ");

        sql.append("  FROM JProcedimiento j LEFT OUTER JOIN j.procedimientoWF WF ON wf.workflow = " + TypeProcedimientoWorkflow.PUBLICADO.getValor() + " WHERE WF.uaResponsable.entidad.codigo = :entidad ");
        if (isTipoProcedimiento) {
            sql.append(" AND j.tipo = '" + Constantes.PROCEDIMIENTO + "'");
        } else {
            sql.append(" AND j.tipo = '" + Constantes.SERVICIO + "'");
        }
        Query query = entityManager.createQuery(sql.toString());
        query.setParameter("entidad", idEntidad);
        List<Long> datos = query.getResultList();
        Pagina<IndexacionDTO> resultado = null;
        if (datos == null || datos.isEmpty()) {
            resultado = new Pagina<>(new ArrayList<>(), 0);
        } else {
            List<IndexacionDTO> indexacionDTOS = new ArrayList<>();
            for (Long dato : datos) {
                IndexacionDTO indexacionDTO = new IndexacionDTO();
                if (isTipoProcedimiento) {
                    indexacionDTO.setTipo(TypeIndexacion.PROCEDIMIENTO.toString());
                } else {
                    indexacionDTO.setTipo(TypeIndexacion.SERVICIO.toString());
                }
                indexacionDTO.setCodElemento(dato);
                indexacionDTO.setFechaCreacion(new Date());
                indexacionDTO.setAccion(1); //Indexar
                indexacionDTOS.add(indexacionDTO);
            }
            resultado = new Pagina<>(indexacionDTOS, indexacionDTOS.size());
        }
        return resultado;
    }

    @Override
    public Pagina<IndexacionSIADTO> getProcedimientosParaIndexacionSIA(Long idEntidad) {
        StringBuilder sql = new StringBuilder("SELECT j.codigo, j.tipo ");

        sql.append("  FROM JProcedimiento j LEFT OUTER JOIN j.procedimientoWF WF ON wf.workflow = " + TypeProcedimientoWorkflow.PUBLICADO.getValor() + " WHERE WF.uaResponsable.entidad.codigo = :entidad ");

        Query query = entityManager.createQuery(sql.toString());
        query.setParameter("entidad", idEntidad);
        List<Object[]> datos = query.getResultList();
        Pagina<IndexacionSIADTO> resultado = null;
        if (datos == null || datos.isEmpty()) {
            resultado = new Pagina<>(new ArrayList<>(), 0);
        } else {
            List<IndexacionSIADTO> indexacionDTOS = new ArrayList<>();
            for (Object[] dato : datos) {
                IndexacionSIADTO indexacionDTO = new IndexacionSIADTO();
                indexacionDTO.setCodElemento((Long) dato[0]);
                String tipo = (String) dato[1];
                if (tipo.equals(Constantes.PROCEDIMIENTO)) {
                    indexacionDTO.setTipo(TypeIndexacion.PROCEDIMIENTO.toString());
                } else {
                    indexacionDTO.setTipo(TypeIndexacion.SERVICIO.toString());
                }
                indexacionDTO.setFechaCreacion(new Date());
                indexacionDTOS.add(indexacionDTO);
            }
            resultado = new Pagina<>(indexacionDTOS, indexacionDTOS.size());
        }
        return resultado;
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
        entityManager.flush();
    }

    @Override
    public boolean existeProcedimientoConMateria(Long materiaSIA) {
        List<TipoMateriaSIAGridDTO> lista = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT count(j) FROM JProcedimientoMateriaSIA j where j.tipoMateriaSIA.codigo = :materiaSIA ");
        Query query = entityManager.createQuery(sql.toString());
        query.setParameter("materiaSIA", materiaSIA);
        Long resultado = (Long) query.getSingleResult();
        return resultado > 0l;
    }

    @Override
    public List<TipoMateriaSIAGridDTO> getMateriaGridSIAByWF(Long codigoWF) {
        List<TipoMateriaSIAGridDTO> lista = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT j FROM JProcedimientoMateriaSIA j where j.procedimientoWF.codigo = :codigoProcWF ");
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
        StringBuilder sql = new StringBuilder("SELECT j FROM JProcedimientoMateriaSIA j where j.procedimientoWF.codigo = :codigoProcWF ");
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
                    id.setProcedimiento(codigoWF);
                    nuevo.setCodigo(id);
                    entityManager.persist(nuevo);
                    entityManager.flush();
                }
            }
        }
    }


    @Override
    public boolean existeProcedimientoConPublicoObjetivo(Long codigoPub) {
        List<TipoPublicoObjetivoEntidadGridDTO> lista = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT count(j) FROM JProcedimientoPublicoObjectivo j where j.tipoPublicoObjetivo.codigo = :codigoPub ");
        Query query = entityManager.createQuery(sql.toString());
        query.setParameter("codigoPub", codigoPub);
        Long resultado = (Long) query.getSingleResult();
        return resultado > 0l;
    }

    @Override
    public List<TipoPublicoObjetivoEntidadGridDTO> getTipoPubObjEntByWF(Long codigoWF) {
        List<TipoPublicoObjetivoEntidadGridDTO> lista = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT j FROM JProcedimientoPublicoObjectivo j where j.procedimiento.codigo = :codigoProcWF ");
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
    public boolean existeProcedimientoConFormaInicio(Long codigoForIni) {
        StringBuilder sql = new StringBuilder("SELECT count(j) FROM JProcedimientoWorkflow j where j.formaInicio.codigo = :codigoForIni ");
        Query query = entityManager.createQuery(sql.toString());
        query.setParameter("codigoForIni", codigoForIni);
        Long resultado = (Long) query.getSingleResult();
        return resultado > 0;
    }

    @Override
    public boolean existeProcedimientoConSilencio(Long codigoSilen) {
        StringBuilder sql = new StringBuilder("SELECT count(j) FROM JProcedimientoWorkflow j where j.silencioAdministrativo.codigo = :codigoSilen ");
        Query query = entityManager.createQuery(sql.toString());
        query.setParameter("codigoSilen", codigoSilen);
        Long resultado = (Long) query.getSingleResult();
        return resultado > 0;
    }

    @Override
    public boolean existeProcedimientoConLegitimacion(Long codigoLegi) {
        StringBuilder sql = new StringBuilder("SELECT count(j) FROM JProcedimientoWorkflow j where j.datosPersonalesLegitimacion.codigo = :codigoLegi ");
        Query query = entityManager.createQuery(sql.toString());
        query.setParameter("codigoLegi", codigoLegi);
        Long resultado = (Long) query.getSingleResult();
        return resultado > 0;
    }


    @Override
    public boolean existeProcedimientosConNormativas(Long codigoNor) {
        List<NormativaGridDTO> lista = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT count(j) FROM JProcedimientoNormativa j where j.normativa.codigo = :codigoNor ");
        Query query = entityManager.createQuery(sql.toString());
        query.setParameter("codigoNor", codigoNor);
        Long resultado = (Long) query.getSingleResult();
        return resultado > 0;
    }

    @Override
    public boolean existeTramitesConTipoTramitacionPlantilla(Long codigoTipoTramitacion) {
        List<NormativaGridDTO> lista = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT count(j) FROM JProcedimientoTramite j where j.tipoTramitacion.codigo = :codigoTipoTramitacion ");
        Query query = entityManager.createQuery(sql.toString());
        query.setParameter("codigoTipoTramitacion", codigoTipoTramitacion);
        Long resultado = (Long) query.getSingleResult();
        return resultado > 0l;
    }

    @Override
    public List<NormativaGridDTO> getNormativasByWF(Long codigoWF) {
        List<NormativaGridDTO> lista = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT j FROM JProcedimientoNormativa j where j.procedimiento.codigo = :codigoProcWF ");
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
    public List<ProcedimientoNormativaDTO> getProcedimientosByNormativa(Long idNormativa) {
        List<ProcedimientoNormativaDTO> lista = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT j FROM JProcedimientoNormativa j where j.normativa.codigo = :idNormativa and j.procedimiento.procedimiento.tipo='P'");
        Query query = entityManager.createQuery(sql.toString());
        query.setParameter("idNormativa", idNormativa);
        List<JProcedimientoNormativa> jLista = query.getResultList();
        if (jLista != null && !jLista.isEmpty()) {
            for (JProcedimientoNormativa elemento : jLista) {
                lista.add(elemento.toModelProc());
            }
        }
        return lista;

    }

    @Override
    public List<ProcedimientoNormativaDTO> getServiciosByNormativa(Long idNormativa) {
        List<ProcedimientoNormativaDTO> lista = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT j FROM JProcedimientoNormativa j where j.normativa.codigo = :idNormativa and j.procedimiento.procedimiento.tipo='S'");
        Query query = entityManager.createQuery(sql.toString());
        query.setParameter("idNormativa", idNormativa);
        List<JProcedimientoNormativa> jLista = query.getResultList();
        if (jLista != null && !jLista.isEmpty()) {
            for (JProcedimientoNormativa elemento : jLista) {
                lista.add(elemento.toModelProc());
            }
        }
        return lista;

    }

    @Override
    public void mergeDocumentos(Long codigoWF, Long idListaDocumentos, boolean isLopd, List<ProcedimientoDocumentoDTO> docs, String ruta) {
        entityManager.flush();
        JListaDocumentos jListaDocumentos;

        if (idListaDocumentos == null && (docs == null || docs.isEmpty())) {
            //Caso base, si no hay documentos ni jlistacreado, no se hace nada
            return;
        }

        Long idLDosc;
        ///Creamos lista de documentos si no existe. Si existe, la recuperamos
        if (idListaDocumentos == null) {
            JProcedimientoWorkflow jprocWF = entityManager.find(JProcedimientoWorkflow.class, codigoWF);
            jListaDocumentos = new JListaDocumentos();
            entityManager.persist(jListaDocumentos);
            idLDosc = jListaDocumentos.getCodigo();
            if (isLopd) {
                jprocWF.setListaDocumentosLOPD(jListaDocumentos);
            } else {
                jprocWF.setListaDocumentos(jListaDocumentos);
            }
            entityManager.merge(jprocWF);
        } else {
            //jListaDocumentos = entityManager.find(JListaDocumentos.class, idListaDocumentos);
            idLDosc = idListaDocumentos;
        }

        mergearDocumentos(codigoWF, null, idLDosc, docs, ruta);
    }

    @Override
    public List<ProcedimientoDocumentoDTO> getDocumentosByListaDocumentos(JListaDocumentos listaDocumentos) {
        List<ProcedimientoDocumentoDTO> docs = new ArrayList<>();
        if (listaDocumentos != null) {
            List<JProcedimientoDocumento> jdocs = getDocumentos(listaDocumentos.getCodigo());
            if (jdocs != null) {
                for (JProcedimientoDocumento jdoc : jdocs) {
                    ProcedimientoDocumentoDTO doc = jdoc.toModel();
                    docs.add(doc);
                }
            }
        }
        return docs;
    }

    @Inject
    private ProcedimientoTramiteConverter procedimientoTramiteConverter;

    @Override
    public List<ProcedimientoTramiteDTO> getTramitesByWF(Long codigoWF) {
        List<ProcedimientoTramiteDTO> tramites = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT j FROM JProcedimientoTramite j where j.procedimiento.codigo = :codigoProcWF ");
        Query query = entityManager.createQuery(sql.toString());
        query.setParameter("codigoProcWF", codigoWF);
        List<JProcedimientoTramite> jlista = query.getResultList();
        if (jlista != null) {
            for (JProcedimientoTramite jtramite : jlista) {
                ProcedimientoTramiteDTO tramite = procedimientoTramiteConverter.createDTO(jtramite);
                tramite.setTramitElectronica(jtramite.isTramitElectronica());
                tramite.setTramitPresencial(jtramite.isTramitPresencial());
                tramite.setTramitTelefonica(jtramite.isTramitTelefonica());
                if (jtramite.getTipoTramitacion() != null) {
                    //tramite.setTipoTramitacion(tramite.getTipoTramitacion());
                    tramite.setPlantillaSel(null);
                } else if (jtramite.getTipoTramitacionPlantilla() != null) {
                    tramite.setPlantillaSel(tipoTramitacionConverter.createDTO(jtramite.getTipoTramitacionPlantilla()));
                    tramite.setTipoTramitacion(null);
                }
                if (jtramite.getListaDocumentos() != null) {
                    tramite.setListaDocumentos(this.getDocumentosByListaDocumentos(jtramite.getListaDocumentos()));
                }
                if (jtramite.getListaModelos() != null) {
                    tramite.setListaModelos(this.getDocumentosByListaDocumentos(jtramite.getListaModelos()));
                }
                tramites.add(tramite);
            }
        }
        return tramites;
    }

    @Override
    public void actualizarMensajes(Long codigo, String mensajes, boolean pendienteMensajeSupervisor, boolean pendienteMensajesGestor) {
        entityManager.flush();
        Query query = entityManager.createQuery("update JProcedimiento set mensajes = '" + mensajes + "', mensajesPendienteGestor=" + pendienteMensajesGestor + " ,mensajesPendienteSupervisor=" + pendienteMensajeSupervisor + " where codigo = " + codigo);
        query.executeUpdate();
        /*
        JProcedimiento jproc = entityManager.find(JProcedimiento.class, codigo);
        if (jproc != null) {
            entityManager.flush();
            jproc.setMensajes(mensajes);
            entityManager.merge(jproc);
        }*/
    }

    @Override
    public void mergeDocumentosTramite(Long codigoWF, Long codigoTramite, Long idListaDocumentos, boolean isModelo, List<ProcedimientoDocumentoDTO> docs, String ruta) {
        entityManager.flush();
        JListaDocumentos jListaDocumentos;
        if (idListaDocumentos == null && (docs == null || docs.isEmpty())) {
            //Caso base, si no hay documentos ni jlistacreado, no se hace nada
            return;
        }


        Long idLDocs;
        ///Creamos lista de documentos si no existe. Si existe, la recuperamos
        if (idListaDocumentos == null) {
            JProcedimientoTramite jtramite = entityManager.find(JProcedimientoTramite.class, codigoTramite);
            jListaDocumentos = new JListaDocumentos();
            entityManager.persist(jListaDocumentos);
            idLDocs = jListaDocumentos.getCodigo();
            if (isModelo) {
                jtramite.setListaModelos(jListaDocumentos);
            } else {
                jtramite.setListaDocumentos(jListaDocumentos);
            }
            entityManager.merge(jtramite);

        } else {
            //jListaDocumentos = entityManager.find(JListaDocumentos.class, idListaDocumentos);
            idLDocs = idListaDocumentos;
        }
        mergearDocumentos(codigoWF, codigoTramite, idLDocs, docs, ruta);
    }

    private void mergearDocumentos(Long codigoWF, Long codigoTramite, Long idListaDocumentos, List<ProcedimientoDocumentoDTO> docs, String ruta) {
        //Mantenimiento de ficheros
        //Se recuperan los que habían y se comparan con los que se pasan.

        ///////////// COMPARACION CON LOS QUE HABÍAN EN BBDD RESPECTO A LOS QUE SE PASAN
        ////// Los que ya habían en BBDD y ya no están en los que se pasan, se marcan para borrar.
        ////// Los que ya habían en BBDD Y ya están pero cambian fichero, se actualizan y se marcan el fichero como para borrar.

        ///////////// COMPARACION CON LOS QUE SE PASAN RESPECTO A LOS QUE HABÍAN EN BBDD
        ///// Con los que se pasan, si no existen, se crean.
        entityManager.flush();
        List<JProcedimientoDocumento> jlista = getDocumentos(idListaDocumentos);

        List<JProcedimientoDocumento> borrar = new ArrayList<>();
        if (jlista != null && !jlista.isEmpty()) {
            for (JProcedimientoDocumento jelemento : jlista) {
                boolean encontrado = false;
                if (docs != null) {
                    for (ProcedimientoDocumentoDTO elemento : docs) {
                        if (elemento.getCodigo() != null && elemento.getCodigo().compareTo(jelemento.getCodigo()) == 0) {
                            encontrado = true;
                            for (JProcedimientoDocumentoTraduccion traduccion : jelemento.getTraducciones()) {
                                compararFicheros(traduccion, elemento.getDocumentos().getTraduccion(traduccion.getIdioma()), codigoWF, codigoTramite, ruta);
                            }
                            break;
                        }
                    }
                }

                if (!encontrado) {
                    if (jelemento.getTraducciones() != null) {
                        for (JProcedimientoDocumentoTraduccion traduccion : jelemento.getTraducciones()) {
                            if (traduccion.getFichero() != null) {
                                ficheroExternoRepository.deleteFicheroExterno(traduccion.getFichero());
                            }
                        }
                    }
                    borrar.add(jelemento);
                }
            }
        }

        if (!borrar.isEmpty()) {
            for (JProcedimientoDocumento jelemento : borrar) {
                entityManager.remove(jelemento);
            }
            jlista.removeAll(borrar);
        }


        if (docs != null && !docs.isEmpty()) {
            JProcedimientoWorkflow jprocWF = entityManager.find(JProcedimientoWorkflow.class, codigoWF);
            for (ProcedimientoDocumentoDTO elemento : docs) {
                boolean encontrado = false;
                if (!jlista.isEmpty()) {
                    for (JProcedimientoDocumento jelemento : jlista) {
                        if (elemento.getCodigo() != null && elemento.getCodigo().compareTo(jelemento.getCodigo()) == 0) {
                            encontrado = true;
                            jelemento.setOrden(elemento.getOrden());
                            entityManager.merge(jelemento);
                            actualizarTraduccionDocumento(jelemento, elemento, codigoWF, ruta);
                            break;
                        }
                    }
                }

                if (!encontrado) {
                    JProcedimientoDocumento nuevo = new JProcedimientoDocumento();
                    nuevo.setTraducciones(new ArrayList<>());
                    nuevo.setListaDocumentos(idListaDocumentos);
                    nuevo.setOrden(elemento.getOrden());
                    entityManager.persist(nuevo);
                    actualizarTraduccionDocumento(nuevo, elemento, codigoWF, ruta);


                }
            }
        }
        entityManager.flush();

    }

    /**
     * Método que actualiza todo respecto a la traduccion.
     */
    private void actualizarTraduccionDocumento(JProcedimientoDocumento jprocDoc, ProcedimientoDocumentoDTO elemento, Long idProcWF, String ruta) {

        //Primero actualizamos los que estén
        List<String> idiomas = new ArrayList<>();
        for (JProcedimientoDocumentoTraduccion traduccion : jprocDoc.getTraducciones()) {
            idiomas.add(traduccion.getIdioma());
            traduccion.setDocumento(jprocDoc);
            Long fichero = elemento.getDocumentos().getTraduccion(traduccion.getIdioma()) == null ? null : elemento.getDocumentos().getTraduccion(traduccion.getIdioma()).getCodigo();
            //CASO 1. Si antes había y ahora no, se ha borrado (hay que marcar para borrar)
            if (fichero == null && traduccion.getFichero() != null) {
                ficheroExternoRepository.deleteFicheroExterno(traduccion.getFichero());
            }
            //CASO 2. Se ha añadido un fichero, hay que persistirlo
            if (fichero != null && traduccion.getFichero() == null) {
                ficheroExternoRepository.persistFicheroExterno(fichero, idProcWF, ruta);
            }
            //CASO 3. Se ha cambiado el fichero antiguo por uno nuevo, entonces uno se marca para borrar y otro para añadir.
            if (fichero != null && traduccion.getFichero() != null && fichero.compareTo(traduccion.getFichero()) != 0) {
                ficheroExternoRepository.deleteFicheroExterno(traduccion.getFichero());
                ficheroExternoRepository.persistFicheroExterno(fichero, idProcWF, ruta);
            }

            traduccion.setFichero(fichero);
            traduccion.setDescripcion(elemento.getDescripcion().getTraduccion(traduccion.getIdioma()));
            traduccion.setTitulo(elemento.getTitulo().getTraduccion(traduccion.getIdioma()));
        }

        //Ahora creamos los que falten
        List<String> idiomasNuevos = elemento.getTraduccionesSobrantes(idiomas);
        for (String idioma : idiomasNuevos) {
            JProcedimientoDocumentoTraduccion traduccion = new JProcedimientoDocumentoTraduccion();
            traduccion.setIdioma(idioma);
            traduccion.setDocumento(jprocDoc);
            Long fichero = elemento.getDocumentos().getTraduccion(traduccion.getIdioma()) == null ? null : elemento.getDocumentos().getTraduccion(traduccion.getIdioma()).getCodigo();
            if (fichero != null) {
                ficheroExternoRepository.persistFicheroExterno(fichero, idProcWF, ruta);
            }
            traduccion.setFichero(fichero);
            traduccion.setDescripcion(elemento.getDescripcion() == null ? null : elemento.getDescripcion().getTraduccion(traduccion.getIdioma()));
            traduccion.setTitulo(elemento.getTitulo() == null ? null : elemento.getTitulo().getTraduccion(traduccion.getIdioma()));
            jprocDoc.getTraducciones().add(traduccion);
        }

        if (!idiomasNuevos.isEmpty()) {
            entityManager.merge(jprocDoc);
        }
    }

    /**
     * Método complejo ya que hay que comprobar multiples casos:
     * Traduccion tiene fichero:
     * <ul>
     *     <li>Traduccion tiene fichero</li>
     *
     *     <li>Traduccion no tiene fichero. Directamente se persiste ficheroDTO.</li>
     *
     * </ul>
     *
     * @param traduccion
     * @param ficheroDTO
     */
    private void compararFicheros(JProcedimientoDocumentoTraduccion traduccion, FicheroDTO ficheroDTO, Long idProcWF, Long idTramite, String ruta) {
        if (ficheroDTO != null && ficheroDTO.getCodigo() != null) {
            ficheroExternoRepository.persistFicheroExterno(ficheroDTO.getCodigo(), idProcWF, ruta);
        }

        //Los únicos casos raros es si ya existía un jfichero
        if (traduccion.getFichero() != null) {
            if (ficheroDTO == null) {
                //Caso 1. Existía fichero pero ahora no existe, entonces hay que borrar
                ficheroExternoRepository.deleteFicheroExterno(traduccion.getFichero());
            }

            if (ficheroDTO != null && ficheroDTO.getCodigo().compareTo(traduccion.getFichero()) != 0) {
                //Caso 2. Ha cambiado el fichero, hay que borrar el que había
                ficheroExternoRepository.deleteFicheroExterno(traduccion.getFichero());

                //Como ha cambiado el fichero, hay que cambiarlo en la traducción
                traduccion.setFichero(ficheroDTO.getCodigo());
                entityManager.merge(traduccion);
            }
        }
    }

    private List<JProcedimientoDocumento> getDocumentos(Long codigoListaDocumentos) {
        StringBuilder sql = new StringBuilder("SELECT j FROM JProcedimientoDocumento j where j.listaDocumentos = :codigoListaDocumentos ");
        Query query = entityManager.createQuery(sql.toString());
        query.setParameter("codigoListaDocumentos", codigoListaDocumentos);
        return query.getResultList();
    }

    @Override
    public void deleteWF(Long codigo, boolean wf) {
        Long codigoWF = this.getCodigoByWF(codigo, wf);
        deleteWF(codigoWF);
    }

    @Override
    public void deleteWF(Long codigoProc) {
        JProcedimientoWorkflow jprocWF = this.getWFByCodigoWF(codigoProc);
        if (jprocWF == null) {
            return;
        }

        //BORRAMOS todos los publico objetivo asociados
        StringBuilder sql = new StringBuilder("SELECT j FROM JProcedimientoPublicoObjectivo j where j.procedimiento.codigo = :codigoProcWF ");
        Query query = entityManager.createQuery(sql.toString());
        query.setParameter("codigoProcWF", jprocWF.getCodigo());
        List<JProcedimientoPublicoObjectivo> jlista = query.getResultList();
        if (jlista != null) {
            for (JProcedimientoPublicoObjectivo jelemento : jlista) {
                entityManager.remove(jelemento);
            }
        }

        //Borramos las materias SIA asociadas
        StringBuilder sqlSIA = new StringBuilder("SELECT j FROM JProcedimientoMateriaSIA j where j.procedimientoWF.codigo = :codigoProcWF ");
        Query querySIA = entityManager.createQuery(sqlSIA.toString());
        querySIA.setParameter("codigoProcWF", jprocWF.getCodigo());
        List<JProcedimientoMateriaSIA> jlistaSIA = querySIA.getResultList();
        if (jlistaSIA != null) {
            for (JProcedimientoMateriaSIA jelementoSIA : jlistaSIA) {
                entityManager.remove(jelementoSIA);
            }
        }

        //Borramos las normativas asociadas
        StringBuilder sqlNormativas = new StringBuilder("SELECT j FROM JProcedimientoNormativa j where j.procedimiento.codigo = :codigoProcWF ");

        Query queryNormativas = entityManager.createQuery(sqlNormativas.toString());
        queryNormativas.setParameter("codigoProcWF", jprocWF.getCodigo());
        List<JProcedimientoNormativa> jlistaNormativas = queryNormativas.getResultList();
        if (jlistaNormativas != null) {
            for (JProcedimientoNormativa jelementoNormativa : jlistaNormativas) {
                entityManager.remove(jelementoNormativa);
            }
        }

        //Borramos los documentos y documentos LOPD asociados (los ficheros se marcan para borrar)
        if (jprocWF.getListaDocumentos() != null && jprocWF.getListaDocumentos().getCodigo() != null) {
            List<JProcedimientoDocumento> jlistaDocs = getDocumentos(jprocWF.getListaDocumentos().getCodigo());
            if (jlistaDocs != null) {
                for (JProcedimientoDocumento jelementoDoc : jlistaDocs) {
                    borrarJProcedimientoDocumento(jelementoDoc);
                }
            }
        }

        if (jprocWF.getListaDocumentosLOPD() != null && jprocWF.getListaDocumentosLOPD().getCodigo() != null) {
            List<JProcedimientoDocumento> jlistaDocs = getDocumentos(jprocWF.getListaDocumentosLOPD().getCodigo());
            if (jlistaDocs != null) {
                for (JProcedimientoDocumento jelementoDoc : jlistaDocs) {
                    borrarJProcedimientoDocumento(jelementoDoc);
                }
            }
        }

        //Obtenemos los tramites y sus traducciones y borramos los documentos asociados (marcando lso ficheros para borrar)
        StringBuilder sqlTramites = new StringBuilder("SELECT j FROM JProcedimientoTramite j where j.procedimiento.codigo = :codigoProcWF ");
        Query queryTramites = entityManager.createQuery(sqlTramites.toString());
        queryTramites.setParameter("codigoProcWF", jprocWF.getCodigo());
        List<JProcedimientoTramite> jlistaTramites = queryTramites.getResultList();
        if (jlistaTramites != null) {
            for (JProcedimientoTramite tramite : jlistaTramites) {
                borrarTramite(tramite);
            }
        }

        //Lo ultimo es borrar el propio WF
        entityManager.remove(jprocWF);
    }

    @Override
    public void mergePublicoObjetivoProcWF(Long codigoWF, List<TipoPublicoObjetivoEntidadGridDTO> listaNuevos) {
        StringBuilder sql = new StringBuilder("SELECT j FROM JProcedimientoPublicoObjectivo j where j.procedimiento.codigo = :codigoProcWF ");
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

    @Inject
    TipoTramitacionConverter tipoTramitacionConverter;

    @Inject
    PlatTramitElectronicaConverter platTramitElectronicaConverter;

    @Override
    public void mergeTramitesProcWF(Long codigoWF, List<ProcedimientoTramiteDTO> listaNuevos, String ruta) {
        entityManager.flush();
        StringBuilder sql = new StringBuilder("SELECT j FROM JProcedimientoTramite j where j.procedimiento.codigo = :codigoProcWF ");
        Query query = entityManager.createQuery(sql.toString());
        query.setParameter("codigoProcWF", codigoWF);
        List<JProcedimientoTramite> jlista = query.getResultList();

        List<JProcedimientoTramite> borrar = new ArrayList<>();
        if (jlista != null && !jlista.isEmpty()) {
            for (JProcedimientoTramite jelemento : jlista) {
                boolean encontrado = false;
                if (listaNuevos != null) {
                    for (ProcedimientoTramiteDTO elemento : listaNuevos) {
                        if (elemento.getCodigo() != null && elemento.getCodigo().compareTo(jelemento.getCodigo()) == 0) {
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
            for (JProcedimientoTramite jelemento : borrar) {
                borrarTramite(jelemento);
            }
            jlista.removeAll(borrar);
        }


        if (listaNuevos != null && !listaNuevos.isEmpty()) {
            JProcedimientoWorkflow jprocWF = entityManager.find(JProcedimientoWorkflow.class, codigoWF);
            for (ProcedimientoTramiteDTO elemento : listaNuevos) {
                boolean encontrado = false;
                if (!jlista.isEmpty()) {
                    for (JProcedimientoTramite jelemento : jlista) {
                        if (elemento.getCodigo() != null && elemento.getCodigo().compareTo(jelemento.getCodigo()) == 0) {
                            encontrado = true;
                            JTipoTramitacion jTipoTramitacion = null;
                            JTipoTramitacion jTipoTramitacionPlantilla = null;

                            if (elemento.getPlantillaSel() != null && elemento.getPlantillaSel().getCodigo() != null) {
                                jTipoTramitacionPlantilla = entityManager.find(JTipoTramitacion.class, elemento.getPlantillaSel().getCodigo());
                                if (elemento.getTipoTramitacion() != null && elemento.getTipoTramitacion().getCodigo() != null) {
                                    JTipoTramitacion jTipoBorrar = entityManager.find(JTipoTramitacion.class, elemento.getTipoTramitacion().getCodigo());
                                    entityManager.remove(jTipoBorrar);
                                }
                            } else if (elemento.getTipoTramitacion() != null) {// && elemento.getTipoTramitacion().getTramiteId() != null) {
                                if (elemento.getTipoTramitacion().getCodigo() == null) {
                                    jTipoTramitacion = tipoTramitacionConverter.createEntity(elemento.getTipoTramitacion());
                                    entityManager.persist(jTipoTramitacion);
                                } else {
                                    jTipoTramitacion = entityManager.find(JTipoTramitacion.class, elemento.getTipoTramitacion().getCodigo());
                                    jTipoTramitacion.merge(elemento.getTipoTramitacion());
                                    if (elemento.getTipoTramitacion().getCodPlatTramitacion() == null) {
                                        jTipoTramitacion.setCodPlatTramitacion(null);
                                    } else {
                                        JPlatTramitElectronica jplataforma = platTramitElectronicaConverter.createEntity(elemento.getTipoTramitacion().getCodPlatTramitacion());
                                        //JPlatTramitElectronica jplataforma = entityManager.find(JPlatTramitElectronica.class, elemento.getTipoTramitacion().getCodPlatTramitacion().getCodigo());
                                        jTipoTramitacion.setCodPlatTramitacion(jplataforma);
                                    }
                                    jTipoTramitacion.setPlantilla(false);
                                    entityManager.merge(jTipoTramitacion);
                                }

                            }


                            jelemento.merge(elemento, jTipoTramitacionPlantilla, jTipoTramitacion);
                            if (elemento.getListaDocumentos() != null && jelemento.getListaDocumentos() == null) {
                                JListaDocumentos jlistaDoc = new JListaDocumentos();
                                entityManager.persist(jlistaDoc);
                                jelemento.setListaDocumentos(jlistaDoc);
                            }
                            if (elemento.getListaModelos() != null && jelemento.getListaModelos() == null) {
                                JListaDocumentos jlistaDoc = new JListaDocumentos();
                                entityManager.persist(jlistaDoc);
                                jelemento.setListaModelos(jlistaDoc);
                            }
                            entityManager.merge(jelemento);
                            entityManager.flush();
                            if (elemento.getListaDocumentos() != null) {
                                mergearDocumentos(codigoWF, jelemento.getCodigo(), jelemento.getListaDocumentos().getCodigo(), elemento.getListaDocumentos(), ruta);
                            }
                            if (elemento.getListaModelos() != null) {
                                mergearDocumentos(codigoWF, jelemento.getCodigo(), jelemento.getListaModelos().getCodigo(), elemento.getListaModelos(), ruta);
                            }
                            break;
                        }
                    }
                }

                if (!encontrado) {
                    JProcedimientoTramite nuevo = new JProcedimientoTramite();

                    JTipoTramitacion jTipoTramitacion = null;
                    JTipoTramitacion jTipoTramitacionPlantilla = null;
                    if (elemento.getPlantillaSel() != null && elemento.getPlantillaSel().getCodigo() != null) {
                        jTipoTramitacionPlantilla = entityManager.find(JTipoTramitacion.class, elemento.getPlantillaSel().getCodigo());
                        jTipoTramitacionPlantilla.setPlantilla(true);
                    } else if (elemento.getTipoTramitacion() != null) {
                        jTipoTramitacion = tipoTramitacionConverter.createEntity(elemento.getTipoTramitacion());
                        jTipoTramitacion.setPlantilla(false);
                        entityManager.persist(jTipoTramitacion);
                    }

                    JUnidadAdministrativa jua = null;
                    if (elemento.getUnidadAdministrativa() != null) {
                        jua = entityManager.find(JUnidadAdministrativa.class, elemento.getUnidadAdministrativa().getCodigo());
                    }
                    nuevo.setUnidadAdministrativa(jua);
                    nuevo.setProcedimiento(jprocWF);
                    nuevo.merge(elemento, jTipoTramitacionPlantilla, jTipoTramitacion);
                    if (elemento.getListaDocumentos() != null) {
                        JListaDocumentos jListaDocumentos = new JListaDocumentos();
                        entityManager.persist(jListaDocumentos);
                        nuevo.setListaDocumentos(jListaDocumentos);
                    }
                    if (elemento.getListaModelos() != null) {
                        JListaDocumentos jListaDocumentos = new JListaDocumentos();
                        entityManager.persist(jListaDocumentos);
                        nuevo.setListaModelos(jListaDocumentos);
                    }
                    entityManager.persist(nuevo);

                    if (elemento.getListaDocumentos() != null) {
                        mergearDocumentos(codigoWF, nuevo.getCodigo(), nuevo.getListaDocumentos().getCodigo(), elemento.getListaDocumentos(), ruta);
                    }
                    if (elemento.getListaModelos() != null) {
                        mergearDocumentos(codigoWF, nuevo.getCodigo(), nuevo.getListaModelos().getCodigo(), elemento.getListaModelos(), ruta);
                    }
                }
            }
        }
        entityManager.flush();
    }

    private void mergeTraduccionTipoTramitacion(JTipoTramitacion jTipoTramitacion, TipoTramitacionDTO tipoTramitacion) {
        if (tipoTramitacion.getUrl() != null) {
            for (String idioma : tipoTramitacion.getUrl().getIdiomas()) {
                JTipoTramitacionTraduccion trad = jTipoTramitacion.getTraduccion(idioma);
                if (trad == null) {
                    trad = new JTipoTramitacionTraduccion();
                    trad.setUrl(tipoTramitacion.getUrl().getTraduccion(idioma));
                    trad.setIdioma(idioma);
                    trad.setTipoTramitacion(jTipoTramitacion);
                } else {
                    trad.setUrl(tipoTramitacion.getUrl().getTraduccion(idioma));
                }
            }
        }
    }

    private void borrarTramite(JProcedimientoTramite jelemento) {


        if (jelemento.getListaDocumentos() != null && jelemento.getListaDocumentos().getCodigo() != null) {
            List<JProcedimientoDocumento> jlistaDocs = getDocumentos(jelemento.getListaDocumentos().getCodigo());
            if (jlistaDocs != null) {
                for (JProcedimientoDocumento jelementoDoc : jlistaDocs) {
                    borrarJProcedimientoDocumento(jelementoDoc);
                }
            }
        }


        if (jelemento.getListaModelos() != null && jelemento.getListaModelos().getCodigo() != null) {
            List<JProcedimientoDocumento> jlistaDocs = getDocumentos(jelemento.getListaModelos().getCodigo());
            if (jlistaDocs != null) {
                for (JProcedimientoDocumento jelementoDoc : jlistaDocs) {
                    borrarJProcedimientoDocumento(jelementoDoc);
                }
            }
        }
        entityManager.remove(jelemento);

    }

    private void borrarJProcedimientoDocumento(JProcedimientoDocumento jelementoDoc) {
        if (jelementoDoc.getTraducciones() != null) {
            for (JProcedimientoDocumentoTraduccion trad : jelementoDoc.getTraducciones()) {
                if (trad.getFichero() != null) {
                    this.ficheroExternoRepository.deleteFicheroExterno(trad.getFichero());
                }
            }
        }
        entityManager.remove(jelementoDoc);
    }

    @Override
    public void mergeNormativaProcWF(Long codigoWF, List<NormativaGridDTO> listaNuevos) {
        StringBuilder sql = new StringBuilder("SELECT j FROM JProcedimientoNormativa j where j.procedimiento.codigo = :codigoProcWF ");
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
                            jelemento.setOrden(elemento.getOrden());
                            entityManager.merge(jelemento);
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
                    nuevo.setOrden(elemento.getOrden());
                    entityManager.persist(nuevo);
                }
            }
        }
    }

    /**
     * Obtiene el enlace telematico de un servicio..
     *
     * @param idServicio Id servicio
     * @param lang       Idioma, por defecto, ca.
     * @return Devuelve la url.
     * @throws DelegateException
     * @ejb.interface-method
     * @ejb.permission role-name="${role.system},${role.admin},${role.super},${role.oper}"
     */
    @Override
    public String getEnlaceTelematico(ProcedimientoFiltro filtro) {
        List<ProcedimientoBaseDTO> lista = this.findProcedimientosPagedByFiltroRest(filtro);
        ServicioDTO serv = null;
        String res = "";
        if (lista != null && !lista.isEmpty()) {
            serv = (ServicioDTO) lista.get(0);
        }

        if (serv != null) {
            res = montarUrl(serv, filtro.getIdioma());
        }


        return res;
    }

    private String montarUrl(ServicioDTO serv, String lang) {
        TipoTramitacionDTO servTr = serv.getTipoTramitacion();
        String res = "";
        try {
            final String idTramite = servTr.getTramiteId();
            final String numVersion = servTr.getTramiteVersion().toString();
            final String idioma = lang;
            final String parametros;
            if (servTr.getTramiteParametros() == null) {
                parametros = "";
            } else {
                parametros = servTr.getTramiteParametros();
            }
            final String idTramiteRolsac = serv.getCodigo().toString();

            String url = servTr.getCodPlatTramitacion().getUrlAcceso().getTraduccion(lang);

            url = url.replace("${idTramitePlataforma}", idTramite);
            url = url.replace("${versionTramitePlatorma}", numVersion);
            url = url.replace("${parametros}", parametros);
            url = url.replace("${servicio}", String.valueOf(true));
            url = url.replace("${idTramiteRolsac}", idTramiteRolsac);


            res = url;
        } catch (final Exception e) {

            // si ocurre un error es porque alguno de los campos de url del trámite no
            // existen. buscamos en la url externa.
            res = servTr.getUrlTramitacion();
        }

        return res;
    }

    @Override
    public void updateWF(JProcedimientoWorkflow jProcWF) {
        entityManager.merge(jProcWF);
    }

    private Query getQuery(boolean isTotal, ProcedimientoFiltro filtro) {
        return getQuery(isTotal, filtro, false);
    }

    private Query getQuery(boolean isTotal, ProcedimientoFiltro filtro, boolean isRest) {

        StringBuilder sql;
        boolean ambosWf = false;
        if (isTotal) {
            sql = new StringBuilder("SELECT count(j) FROM JProcedimiento j LEFT OUTER JOIN j.procedimientoWF WF ON wf.workflow = true LEFT OUTER JOIN j.procedimientoWF WF2 ON wf2.workflow = false LEFT OUTER JOIN WF.traducciones t ON t.idioma=:idioma LEFT OUTER JOIN WF2.traducciones t2 ON t2.idioma=:idioma LEFT OUTER JOIN WF.tipoProcedimiento TIPPRO1 LEFT OUTER JOIN TIPPRO1.descripcion tipoPro1 on tipoPro1.idioma =:idioma LEFT OUTER JOIN WF2.tipoProcedimiento TIPPRO2 LEFT OUTER JOIN TIPPRO2.descripcion tipoPro2 on tipoPro2.idioma =:idioma where 1 = 1 ");
            ambosWf = true;
        } else if (isRest) {
            if (filtro.getEstadoWF() != null && filtro.getEstadoWF().equals("D")) {
                sql = new StringBuilder("SELECT wf FROM JProcedimiento j INNER JOIN j.procedimientoWF WF ON wf.workflow = false LEFT OUTER JOIN WF.traducciones t ON t.idioma=:idioma LEFT OUTER JOIN WF.tipoProcedimiento TIPPRO1 LEFT OUTER JOIN TIPPRO1.descripcion tipoPro1 on tipoPro1.idioma =:idioma where 1 = 1 ");
            } else if (filtro.getEstadoWF() != null && filtro.getEstadoWF().equals("M")) {
                sql = new StringBuilder("SELECT  wf FROM JProcedimiento j INNER JOIN j.procedimientoWF WF ON wf.workflow = true LEFT OUTER JOIN WF.traducciones t ON t.idioma=:idioma  LEFT OUTER JOIN WF.tipoProcedimiento TIPPRO1 LEFT OUTER JOIN TIPPRO1.descripcion tipoPro1 on tipoPro1.idioma =:idioma where 1 = 1 ");
            } else if (filtro.getEstadoWF() != null && filtro.getEstadoWF().equals("T")) {
                sql = new StringBuilder("SELECT  wf, wf2 FROM JProcedimiento j LEFT JOIN j.procedimientoWF WF ON wf.workflow = true or wf.workflow is null LEFT JOIN j.procedimientoWF WF2 ON wf2.workflow = false or wf2.workflow is null LEFT OUTER JOIN WF.traducciones t ON t.idioma=:idioma LEFT OUTER JOIN WF2.traducciones t2 ON t2.idioma=:idioma LEFT OUTER JOIN WF.tipoProcedimiento TIPPRO1 LEFT OUTER JOIN TIPPRO1.descripcion tipoPro1 on tipoPro1.idioma=:idioma LEFT OUTER JOIN WF2.tipoProcedimiento TIPPRO2 LEFT OUTER JOIN TIPPRO2.descripcion tipoPro2 on tipoPro2.idioma =:idioma where 1 = 1 ");//and((wf.workflow = true and wf2.workflow is null) or (wf.workflow = true and wf2.workflow = false) or (wf.workflow is null and wf2.workflow = false))
                ambosWf = true;
            } else {
                sql = new StringBuilder("SELECT wf FROM JProcedimiento j INNER JOIN j.procedimientoWF WF ON wf.workflow = true or wf.workflow = false LEFT OUTER JOIN WF.traducciones t ON t.idioma=:idioma LEFT OUTER JOIN WF.tipoProcedimiento TIPPRO1 LEFT OUTER JOIN TIPPRO1.descripcion tipoPro1 on tipoPro1.idioma =:idioma  where 1 = 1 ");
            }
        } else {
            sql = new StringBuilder("SELECT j.codigo, wf.codigo, wf2.codigo, wf.estado || '' || wf2.estado, j.tipo , j.codigoSIA, j.estadoSIA , j.siaFecha, j.codigoDir3SIA, t.nombre, t2.nombre, tipoPro1.descripcion, tipoPro2.descripcion, j.fechaActualizacion, wf.comun, wf2.comun, (select tram.codigo || '#' || to_char(tram.fechaPublicacion, 'DD/MM/YYYY HH24:MI') || '#' || to_char(tram.fechaCierre, 'DD/MM/YYYY HH24:MI') FROM JProcedimientoTramite tram where wf.codigo = tram.procedimiento.codigo and tram.fase = 1), wf.fechaPublicacion, wf.fechaCaducidad, j.mensajesPendienteGestor, j.mensajesPendienteSupervisor FROM JProcedimiento j LEFT OUTER JOIN j.procedimientoWF WF ON wf.workflow = " + TypeProcedimientoWorkflow.PUBLICADO.getValor() + " LEFT OUTER JOIN j.procedimientoWF WF2 ON wf2.workflow = " + TypeProcedimientoWorkflow.MODIFICACION.getValor() + " LEFT OUTER JOIN WF.traducciones t ON t.idioma=:idioma LEFT OUTER JOIN WF2.traducciones t2 ON t2.idioma=:idioma LEFT OUTER JOIN WF.tipoProcedimiento TIPPRO1 LEFT OUTER JOIN TIPPRO1.descripcion tipoPro1 on tipoPro1.idioma =:idioma LEFT OUTER JOIN WF2.tipoProcedimiento TIPPRO2 LEFT OUTER JOIN TIPPRO2.descripcion tipoPro2 on tipoPro2.idioma =:idioma where 1 = 1 ");
            ambosWf = true;
        }

        if (filtro.isRellenoTexto() && ambosWf) {
            sql.append(" and ( LOWER(cast(j.codigo as string)) like :filtro " + " OR LOWER(t.nombre) LIKE :filtro  OR LOWER(t2.nombre) LIKE :filtro " + " OR LOWER(wf.estado) LIKE :filtro OR LOWER(wf2.estado) LIKE :filtro    " + " OR LOWER(j.tipo) LIKE :filtro  OR LOWER(cast(j.codigoSIA as string)) LIKE :filtro " + " OR LOWER(cast(j.estadoSIA as string)) LIKE :filtro OR LOWER(cast(j.codigoDir3SIA as string)) LIKE :filtro )");
        } else if (filtro.isRellenoTexto()) {
            sql.append(" and ( LOWER(cast(j.codigo as string)) like :filtro " + " OR LOWER(t.nombre) LIKE :filtro " + " OR LOWER(wf.estado) LIKE :filtro " + " OR LOWER(j.tipo) LIKE :filtro  OR LOWER(cast(j.codigoSIA as string)) LIKE :filtro " + " OR LOWER(cast(j.estadoSIA as string)) LIKE :filtro OR LOWER(cast(j.codigoDir3SIA as string)) LIKE :filtro )");
        }

        if (filtro.isRellenoFormaInicio() && ambosWf) {
            sql.append(" AND ( WF.formaInicio.codigo = :formaInicio or  WF2.formaInicio.codigo = :formaInicio) ");
        } else if (filtro.isRellenoFormaInicio()) {
            sql.append(" AND ( WF.formaInicio.codigo = :formaInicio) ");
        }

        if (filtro.isRellenoPublicoObjetivo()) {
            sql.append(" AND exists (select pubObj from JProcedimientoPublicoObjectivo pubObj where pubObj.tipoPublicoObjetivo = :tipoPublicoObjetivo and pubObj.procedimiento.codigo = WF.codigo ) ");
        }

        if (filtro.isRellenoTipo()) {
            sql.append(" AND j.tipo = :tipo ");
        }

        if (filtro.isRellenoSiaFecha()) {
            sql.append(" AND j.siaFecha = :siaFecha ");
        }

        if (filtro.isRellenoFechaPublicacionDesde() && ambosWf) {
            sql.append(" AND (WF.fechaPublicacion >= :fechaPublicacionDesde or WF2.fechaPublicacion >= :fechaPublicacionDesde) ");
        } else if (filtro.isRellenoFechaPublicacionDesde()) {
            sql.append(" AND (WF.fechaPublicacion >= :fechaPublicacionDesde) ");
        }

        if (filtro.isRellenoFechaPublicacionHasta() && ambosWf) {
            sql.append(" AND (WF.fechaPublicacion <= :fechaPublicacionHasta or WF2.fechaPublicacion <= :fechaPublicacionHasta) ");
        } else if (filtro.isRellenoFechaPublicacionHasta()) {
            sql.append(" AND (WF.fechaPublicacion <= :fechaPublicacionHasta) ");
        }

        if (filtro.isRellenoTipoProcedimiento() && ambosWf) {
            sql.append(" AND (WF.tipoProcedimiento.codigo = :tipoProcedimiento or WF2.tipoProcedimiento.codigo = :tipoProcedimiento) ");
        } else if (filtro.isRellenoTipoProcedimiento()) {
            sql.append(" AND (WF.tipoProcedimiento.codigo = :tipoProcedimiento) ");
        }

        if (filtro.isRellenoSilencioAdministrativo() && ambosWf) {
            sql.append(" AND (WF.silencioAdministrativo.codigo = :tipoSilencio or WF2.silencioAdministrativo.codigo = :tipoSilencio) ");
        } else if (filtro.isRellenoTipoProcedimiento()) {
            sql.append(" AND (WF.silencioAdministrativo.codigo = :tipoSilencio) ");
        }

        if ((filtro.isRellenoHijasActivas() && !filtro.isRellenoUasAux()) || filtro.isRellenoTodasUnidadesOrganicas()) {
            if (ambosWf) {
                sql.append(" AND (WF.uaInstructor.codigo in (:idUAs) OR WF2.uaInstructor.codigo in (:idUAs)) ");
            } else {
                sql.append(" AND (WF.uaInstructor.codigo in (:idUAs)) ");
            }
        } else if ((filtro.isRellenoHijasActivas() && filtro.isRellenoUasAux()) || filtro.isRellenoTodasUnidadesOrganicas()) {
            if (ambosWf) {
                sql.append(" AND (WF.uaInstructor.codigo in (:idUAs) OR WF.uaInstructor.codigo in (:idUAsAux) OR WF2.uaInstructor.codigo in (:idUAs) OR WF2.uaInstructor.codigo in (:idUAsAux)) ");
            } else {
                sql.append(" AND (WF.uaInstructor.codigo in (:idUAs) OR WF.uaInstructor.codigo in (:idUAsAux)) ");
            }
        } else if (filtro.isRellenoIdUA()) {
            if (ambosWf) {
                sql.append(" AND (WF.uaInstructor.codigo = :idUA OR WF2.uaInstructor.codigo = :idUA) ");
            } else {
                sql.append(" AND (WF.uaInstructor.codigo = :idUA) ");
            }
        }
        if (filtro.isRellenoNormativas()) {
            if (ambosWf) {
                sql.append(" AND EXISTS ( SELECT 1 FROM JProcedimientoNormativa procNorm WHERE (procNorm.codigo.procedimiento = WF.codigo OR procNorm.codigo.procedimiento = WF2.codigo) AND procNorm.codigo.normativa IN (:normativas) ) ");
            } else {
                sql.append(" AND EXISTS ( SELECT 1 FROM JProcedimientoNormativa procNorm WHERE (procNorm.codigo.procedimiento = WF.codigo) AND procNorm.codigo.normativa IN (:normativas) ) ");
            }
        }
        if (filtro.isRellenoPublicoObjetivos()) {
            if (ambosWf) {
                sql.append(" AND EXISTS ( SELECT 1 FROM JProcedimientoPublicoObjectivo procPub WHERE (procPub.codigo.procedimiento = WF.codigo OR procPub.codigo.procedimiento = WF2.codigo) AND procPub.codigo.tipoPublicoObjetivo IN (:publicoObjetivos) ) ");
            } else {
                sql.append(" AND EXISTS ( SELECT 1 FROM JProcedimientoPublicoObjectivo procPub WHERE (procPub.codigo.procedimiento = WF.codigo) AND procPub.codigo.tipoPublicoObjetivo IN (:publicoObjetivos) ) ");
            }
        }
        if (filtro.isRellenoMaterias()) {
            if (ambosWf) {
                sql.append(" AND EXISTS ( SELECT 1 FROM JProcedimientoMateriaSIA procMat WHERE (procMat.codigo.procedimiento = WF.codigo OR procMat.codigo.procedimiento = WF2.codigo) AND procMat.codigo.tipoMateriaSIA IN (:materias) ) ");
            } else {
                sql.append(" AND EXISTS ( SELECT 1 FROM JProcedimientoMateriaSIA procMat WHERE (procMat.codigo.procedimiento = WF.codigo) AND procMat.codigo.tipoMateriaSIA IN (:materias) ) ");
            }
        }
        if (filtro.isRellenoTemas()) {
            if (ambosWf) {
                sql.append(" AND EXISTS (SELECT 1 FROM JProcedimientoTema procTema WHERE (procTema.codigo.procedimiento = WF.codigo OR procTema.codigo.procedimiento = WF2.codigo) AND procTema.codigo.tema IN (:temas)) ");
            } else {
                sql.append(" AND EXISTS (SELECT 1 FROM JProcedimientoTema procTema WHERE (procTema.codigo.procedimiento = WF.codigo) AND procTema.codigo.tema IN (:temas)) ");
            }
        }
        if (filtro.isRellenoMensajesPendientes()) {
            if (filtro.getMensajesPendiente().equals("PE")) {
                sql.append(" AND (j.mensajesPendienteGestor = true or j.mensajesPendienteSupervisor = true ) ");
            } else if (filtro.getMensajesPendiente().equals("PG")) {
                sql.append(" AND j.mensajesPendienteGestor = true ");
            } else if (filtro.getMensajesPendiente().equals("PS")) {
                sql.append(" AND j.mensajesPendienteSupervisor = true ");
            } else if (filtro.getMensajesPendiente().equals("NO")) {
                sql.append(" AND j.mensajesPendienteGestor = false AND j.mensajesPendienteSupervisor = false ");
            }
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
        if (filtro.isRellenoCodigoProc()) {
            sql.append(" AND j.codigo LIKE :codigoProc ");
        }
        if (filtro.isRellenoCodigoTram()) {
            if (ambosWf) {
                sql.append(" AND EXISTS (SELECT j FROM JProcedimientoTramite j where (j.procedimiento.codigo = WF.codigo OR j.procedimiento.codigo = WF2.codigo  ) AND j.codigo = :codigoTram ) ");
            } else {
                sql.append(" AND EXISTS (SELECT j FROM JProcedimientoTramite j where (j.procedimiento.codigo = WF.codigo) AND j.codigo = :codigoTram ) ");
            }
        }
        if (filtro.isRellenoCodigoSIA()) {
            sql.append(" AND j.codigoSIA LIKE :codigoSIA ");
        }
        if (filtro.isRellenoCodigoUaDir3()) {
            if (ambosWf) {
                sql.append(" AND ((WF.uaInstructor.codigoDIR3 LIKE :codigoUaDir3) or (WF2.uaInstructor.codigoDIR3 LIKE :codigoUaDir3)) ");
            } else {
                sql.append(" AND (WF.uaInstructor.codigoDIR3 LIKE :codigoUaDir3) ");
            }
        }
        if (filtro.isRellenoEstado()) {
            if (ambosWf) {
                sql.append(" AND ( wf.estado = :estado OR wf2.estado = :estado) ");
            } else {
                sql.append(" AND ( wf.estado = :estado) ");
            }
        }
        if (filtro.isRellenoFinVia()) {
            if (ambosWf) {
                sql.append(" AND (wf.tipoVia.codigo = :finVia or wf2.tipoVia.codigo = :finVia) ");
            } else {
                sql.append(" AND (wf.tipoVia.codigo = :finVia) ");
            }
        }
        if (filtro.isRellenoTramiteVigente()) {
            //ROLSAC1
            //t.fase = 1 AND (t.dataInici > current_date OR t.dataInici IS NULL) AND (t.dataTancament < current_date OR t.dataTancament IS NULL)


            if ("S".equals(filtro.getTramiteVigente())) {
                if (ambosWf) {
                    sql.append(" AND EXISTS (SELECT t FROM JProcedimientoTramite t where t.fase = 1 and (t.procedimiento.codigo = WF.codigo OR t.procedimiento.codigo = WF2.codigo  ) AND (t.fechaInicio < current_date OR t.fechaInicio IS NULL) AND (t.fechaCierre > current_date OR t.fechaCierre IS NULL) )");
                } else {
                    sql.append(" AND EXISTS (SELECT t FROM JProcedimientoTramite t where t.fase = 1 and (t.procedimiento.codigo = WF.codigo) AND (t.fechaInicio < current_date OR t.fechaInicio IS NULL) AND (t.fechaCierre > current_date OR t.fechaCierre IS NULL) )");
                }
            } else {
                if (ambosWf) {
                    sql.append(" AND (EXISTS (SELECT t FROM JProcedimientoTramite t where (t.fase = 1 and ((t.procedimiento.codigo = WF.codigo OR t.procedimiento.codigo = WF2.codigo  ) AND (t.fechaInicio > current_date OR t.fechaCierre < current_date)))) OR WF.codigo NOT IN (SELECT t.procedimiento.codigo FROM JProcedimientoTramite t) OR WF2.codigo NOT IN (SELECT t.procedimiento.codigo FROM JProcedimientoTramite t)) ");
                } else {
                    sql.append(" AND (EXISTS (SELECT t FROM JProcedimientoTramite t where (t.fase = 1 and ((t.procedimiento.codigo = WF.codigo) AND (t.fechaInicio > current_date OR t.fechaCierre < current_date)))) OR WF.codigo NOT IN (SELECT t.procedimiento.codigo FROM JProcedimientoTramite t)) ");
                }
            }
        }
        if (filtro.isRellenoTramiteTelematico()) {
            switch (filtro.getTramiteTelematico()) {
                case "P":
                    if (ambosWf) {
                        sql.append(" AND EXISTS (SELECT j FROM JProcedimientoTramite j where (j.procedimiento.codigo = WF.codigo OR j.procedimiento.codigo = WF2.codigo  ) AND (j.tramitPresencial is true OR j.tipoTramitacion.tramitPresencial is true)) ");
                    } else {
                        sql.append(" AND EXISTS (SELECT j FROM JProcedimientoTramite j where (j.procedimiento.codigo = WF.codigo) AND (j.tramitPresencial is true OR j.tipoTramitacion.tramitPresencial is true)) ");
                    }
                    break;
                case "T":
                    if (ambosWf) {
                        sql.append(" AND EXISTS (SELECT j FROM JProcedimientoTramite j where (j.procedimiento.codigo = WF.codigo OR j.procedimiento.codigo = WF2.codigo  ) AND (j.tramitElectronica is true OR j.tipoTramitacion.tramitElectronica is true)) ");
                    } else {
                        sql.append(" AND EXISTS (SELECT j FROM JProcedimientoTramite j where (j.procedimiento.codigo = WF.codigo) AND (j.tramitElectronica is true OR j.tipoTramitacion.tramitElectronica is true)) ");
                    }
                    break;
                case "A":
                    if (ambosWf) {
                        sql.append(" AND EXISTS (SELECT j FROM JProcedimientoTramite j where (j.procedimiento.codigo = WF.codigo OR j.procedimiento.codigo = WF2.codigo  ) AND (j.tramitElectronica is true OR j.tipoTramitacion.tramitElectronica is true OR j.tramitPresencial is true OR j.tipoTramitacion.tramitPresencial is true)) ");
                    } else {
                        sql.append(" AND EXISTS (SELECT j FROM JProcedimientoTramite j where (j.procedimiento.codigo = WF.codigo) AND (j.tramitElectronica is true OR j.tipoTramitacion.tramitElectronica is true OR j.tramitPresencial is true OR j.tipoTramitacion.tramitPresencial is true)) ");
                    }
                    break;
                default:
                    break;
            }
        }
        if (filtro.isRellenoPlantilla()) {
            if (ambosWf) {
                sql.append(" AND EXISTS (SELECT j FROM JProcedimientoTramite j where (j.procedimiento.codigo = WF.codigo OR j.procedimiento.codigo = WF2.codigo  ) AND j.tipoTramitacionPlantilla.codigo = :plantilla )  ");
            } else {
                sql.append(" AND EXISTS (SELECT j FROM JProcedimientoTramite j where (j.procedimiento.codigo = WF.codigo) AND j.tipoTramitacionPlantilla.codigo = :plantilla )  ");
            }
        }
        if (filtro.isRellenoPlataforma()) {
            if (ambosWf) {
                sql.append(" AND EXISTS (SELECT t FROM JProcedimientoTramite t inner join t.tipoTramitacion as tipo inner join tipo.codPlatTramitacion plataforma where (t.procedimiento.codigo = WF.codigo OR t.procedimiento.codigo = WF2.codigo  ) AND ( plataforma.codigo = :plataforma) ) ");
            } else {
                sql.append(" AND EXISTS (SELECT t FROM JProcedimientoTramite t inner join t.tipoTramitacion as tipo inner join tipo.codPlatTramitacion plataforma where (t.procedimiento.codigo = WF.codigo) AND ( plataforma.codigo = :plataforma) ) ");
            }
        }
        if (filtro.isRellenoComun()) {
            if (ambosWf) {
                sql.append(" AND (wf.comun = :comun or wf2.comun = :comun) ");
            } else {
                sql.append(" AND (wf.comun = :comun) ");
            }
        }

        if (filtro.getOrderBy() != null) {
            sql.append(" order by ").append(getOrden(filtro.getOrderBy(), filtro.isAscendente()));
            sql.append(filtro.isAscendente() ? " asc " : " desc ");
        }

        Query query = entityManager.createQuery(sql.toString());
        query.setParameter("idioma", filtro.getIdioma());
        if (filtro.isRellenoTipo()) {
            query.setParameter("tipo", filtro.getTipo());
        }
        if (filtro.isRellenoSiaFecha()) {
            try {
                DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                Date date = df.parse(filtro.getSiaFecha());
                Timestamp timeStampDate = new Timestamp(date.getTime());
                query.setParameter("siaFecha", timeStampDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if (filtro.isRellenoFechaPublicacionDesde()) {
            try {
                DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                Date date = df.parse(filtro.getFechaPublicacionDesde());
                Timestamp timeStampDate = new Timestamp(date.getTime());
                query.setParameter("fechaPublicacionDesde", timeStampDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if (filtro.isRellenoFechaPublicacionHasta()) {
            try {
                DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                Date date = df.parse(filtro.getFechaPublicacionHasta());
                Timestamp timeStampDate = new Timestamp(date.getTime());
                query.setParameter("fechaPublicacionHasta", timeStampDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
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
        if (filtro.isRellenoCodigoProc()) {
            query.setParameter("codigoProc", filtro.getCodigoProc());
        }
        if (filtro.isRellenoCodigoTram()) {
            query.setParameter("codigoTram", filtro.getCodigoTram());
        }
        if (filtro.isRellenoCodigoSIA()) {
            query.setParameter("codigoSIA", filtro.getCodigoSIA());
        }
        if (filtro.isRellenoEstado()) {
            query.setParameter("estado", filtro.getEstado());
        }
        if (filtro.isRellenoCodigoUaDir3()) {
            query.setParameter("codigoUaDir3", "%" + filtro.getCodigoUaDir3().toUpperCase() + "%");
        }
        if ((filtro.isRellenoHijasActivas() && !filtro.isRellenoUasAux()) || filtro.isRellenoTodasUnidadesOrganicas()) {
            query.setParameter("idUAs", filtro.getIdUAsHijas());
        } else if ((filtro.isRellenoHijasActivas() && filtro.isRellenoUasAux()) || filtro.isRellenoTodasUnidadesOrganicas()) {
            query.setParameter("idUAs", filtro.getIdUAsHijas());
            query.setParameter("idUAsAux", filtro.getIdsUAsHijasAux());
        } else if (filtro.isRellenoIdUA()) {
            query.setParameter("idUA", filtro.getIdUA());
        }
        if (filtro.isRellenoNormativas()) {
            query.setParameter("normativas", filtro.getNormativasId());
        }
        if (filtro.isRellenoPublicoObjetivos()) {
            query.setParameter("publicoObjetivos", filtro.getPublicoObjetivosId());
        }
        if (filtro.isRellenoMaterias()) {
            query.setParameter("materias", filtro.getMateriasId());
        }
        if (filtro.isRellenoTemas()) {
            query.setParameter("temas", filtro.getTemasId());
        }
        if (filtro.isRellenoFinVia()) {
            query.setParameter("finVia", filtro.getFinVia().getCodigo());
        }
        if (filtro.isRellenoPlataforma()) {
            query.setParameter("plataforma", filtro.getPlataforma().getCodigo());
        }
        if (filtro.isRellenoPlantilla()) {
            query.setParameter("plantilla", filtro.getPlantilla().getCodigo());
        }
        if (filtro.isRellenoComun()) {
            query.setParameter("comun", "S".equals(filtro.getComun()) ? 1 : 0);
        }
        return query;
    }

    private String getOrden(String order, boolean ascendente) {
        // Se puede hacer un switch/if pero en este caso, con j.+order sobra
        if ("nombre".equals(order)) {
            return "t.nombre " + (ascendente ? " asc " : "desc ") + " , t2.nombre ";
        }
        return "j." + order;
    }

    @Override
    public Optional<JProcedimiento> findById(String id) {
        TypedQuery<JProcedimiento> query = entityManager.createNamedQuery(JProcedimiento.FIND_BY_ID, JProcedimiento.class);
        query.setParameter("codigo", id);
        List<JProcedimiento> result = query.getResultList();
        return Optional.ofNullable(result.isEmpty() ? null : result.get(0));
    }
}
