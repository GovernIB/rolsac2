package es.caib.rolsac2.persistence.repository;

import es.caib.rolsac2.persistence.converter.ProcedimientoTramiteConverter;
import es.caib.rolsac2.persistence.converter.TipoTramitacionConverter;
import es.caib.rolsac2.persistence.model.*;
import es.caib.rolsac2.persistence.model.pk.JProcedimientoMateriaSIAPK;
import es.caib.rolsac2.persistence.model.pk.JProcedimientoNormativaPK;
import es.caib.rolsac2.persistence.model.pk.JProcedimientoPublicoObjectivoPK;
import es.caib.rolsac2.persistence.model.traduccion.JProcedimientoDocumentoTraduccion;
import es.caib.rolsac2.persistence.model.traduccion.JTipoTramitacionTraduccion;
import es.caib.rolsac2.service.model.*;
import es.caib.rolsac2.service.model.filtro.ProcedimientoFiltro;
import es.caib.rolsac2.service.model.types.TypeProcedimientoWorfklow;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
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

    @Inject
    FicheroExternoRepository ficheroExternoRepository;

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
                procedimientoGridDTO.setCodigoWFPub((Long) jproc[1]);
                procedimientoGridDTO.setCodigoWFMod((Long) jproc[2]);
                procedimientoGridDTO.setEstado((String) jproc[3]);
                procedimientoGridDTO.setTipo((String) jproc[4]);
                procedimientoGridDTO.setCodigoSIA((Integer) jproc[5]);
                procedimientoGridDTO.setEstadoSIA((Boolean) jproc[6]);
                procedimientoGridDTO.setSiaFecha((LocalDate) jproc[7]);
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
        StringBuilder sql = new StringBuilder(
                "SELECT count(j) FROM JProcedimientoMateriaSIA j where j.tipoMateriaSIA.codigo = :materiaSIA ");
        Query query = entityManager.createQuery(sql.toString());
        query.setParameter("materiaSIA", materiaSIA);
        Long resultado = (Long) query.getSingleResult();
        return resultado > 0l;
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
    public boolean existeProcedimientoConPublicoObjetivo(Long codigoPub) {
        List<TipoPublicoObjetivoEntidadGridDTO> lista = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
                "SELECT count(j) FROM JProcedimientoPublicoObjectivo j where j.tipoPublicoObjetivo.codigo = :codigoPub ");
        Query query = entityManager.createQuery(sql.toString());
        query.setParameter("codigoPub", codigoPub);
        Long resultado = (Long) query.getSingleResult();
        return resultado > 0l;
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
    public boolean existeProcedimientoConFormaInicio(Long codigoForIni) {
        StringBuilder sql = new StringBuilder(
                "SELECT count(j) FROM JProcedimientoWorkflow j where j.formaInicio.codigo = :codigoForIni ");
        Query query = entityManager.createQuery(sql.toString());
        query.setParameter("codigoForIni", codigoForIni);
        Long resultado = (Long) query.getSingleResult();
        return resultado > 0;
    }

    @Override
    public boolean existeProcedimientoConSilencio(Long codigoSilen) {
        StringBuilder sql = new StringBuilder(
                "SELECT count(j) FROM JProcedimientoWorkflow j where j.silencioAdministrativo.codigo = :codigoSilen ");
        Query query = entityManager.createQuery(sql.toString());
        query.setParameter("codigoSilen", codigoSilen);
        Long resultado = (Long) query.getSingleResult();
        return resultado > 0;
    }

    @Override
    public boolean existeProcedimientoConLegitimacion(Long codigoLegi) {
        StringBuilder sql = new StringBuilder(
                "SELECT count(j) FROM JProcedimientoWorkflow j where j.datosPersonalesLegitimacion.codigo = :codigoLegi ");
        Query query = entityManager.createQuery(sql.toString());
        query.setParameter("codigoLegi", codigoLegi);
        Long resultado = (Long) query.getSingleResult();
        return resultado > 0;
    }


    @Override
    public boolean existeProcedimientosConNormativas(Long codigoNor) {
        List<NormativaGridDTO> lista = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
                "SELECT count(j) FROM JProcedimientoNormativa j where j.normativa.codigo = :codigoNor ");
        Query query = entityManager.createQuery(sql.toString());
        query.setParameter("codigoNor", codigoNor);
        Long resultado = (Long) query.getSingleResult();
        return resultado > 0;
    }

    @Override
    public boolean existeTramitesConTipoTramitacionPlantilla(Long codigoTipoTramitacion) {
        List<NormativaGridDTO> lista = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
                "SELECT count(j) FROM JProcedimientoTramite j where j.tipoTramitacion.codigo = :codigoTipoTramitacion ");
        Query query = entityManager.createQuery(sql.toString());
        query.setParameter("codigoTipoTramitacion", codigoTipoTramitacion);
        Long resultado = (Long) query.getSingleResult();
        return resultado > 0l;
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
    public List<ProcedimientoNormativaDTO> getProcedimientosByNormativa(Long idNormativa) {
        List<ProcedimientoNormativaDTO> lista = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT j FROM JProcedimientoNormativa j where j.normativa.codigo = :idNormativa ");
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
        StringBuilder sql = new StringBuilder(
                "SELECT j FROM JProcedimientoTramite j where j.procedimiento.codigo = :codigoProcWF ");
        Query query = entityManager.createQuery(sql.toString());
        query.setParameter("codigoProcWF", codigoWF);
        List<JProcedimientoTramite> jlista = query.getResultList();
        if (jlista != null) {
            for (JProcedimientoTramite jtramite : jlista) {
                ProcedimientoTramiteDTO tramite = procedimientoTramiteConverter.createDTO(jtramite);
                tramite.setTramitElectronica(jtramite.isTramitElectronica());
                tramite.setTramitPresencial(jtramite.isTramitPresencial());
                tramite.setTramitTelefonica(jtramite.isTramitTelefonica());
                if (tramite.getTipoTramitacion() != null && tramite.getTipoTramitacion().isPlantilla()) {
                    tramite.setPlantillaSel(tramite.getTipoTramitacion());
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
    public void actualizarMensajes(Long codigo, String mensajes) {
        entityManager.flush();
        Query query = entityManager.createQuery("update JProcedimiento set mensajes = '" + mensajes + "' where codigo = " + codigo);
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
                            actualizarTraduccionDocumento(jelemento, elemento, codigoWF, ruta);
                            break;
                        }
                    }
                }

                if (!encontrado) {
                    JProcedimientoDocumento nuevo = new JProcedimientoDocumento();
                    nuevo.setTraducciones(new ArrayList<>());
                    nuevo.setListaDocumentos(idListaDocumentos);
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
        StringBuilder sql = new StringBuilder(
                "SELECT j FROM JProcedimientoDocumento j where j.listaDocumentos = :codigoListaDocumentos ");
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

        //Borramos las materias SIA asociadas
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

        //Borramos las normativas asociadas
        StringBuilder sqlNormativas = new StringBuilder(
                "SELECT j FROM JProcedimientoNormativa j where j.procedimiento.codigo = :codigoProcWF ");

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
        StringBuilder sqlTramites = new StringBuilder(
                "SELECT j FROM JProcedimientoTramite j where j.procedimiento.codigo = :codigoProcWF ");
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

    @Inject
    TipoTramitacionConverter tipoTramitacionConverter;

    @Override
    public void mergeTramitesProcWF(Long codigoWF, List<ProcedimientoTramiteDTO> listaNuevos, String ruta) {
        entityManager.flush();
        StringBuilder sql = new StringBuilder(
                "SELECT j FROM JProcedimientoTramite j where j.procedimiento.codigo = :codigoProcWF ");
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
                            if (elemento.getPlantillaSel() != null) {
                                jTipoTramitacion = entityManager.find(JTipoTramitacion.class, elemento.getPlantillaSel().getCodigo());
                            } else {
                                jTipoTramitacion = tipoTramitacionConverter.createEntity(elemento.getTipoTramitacion());
                                jTipoTramitacion.setPlantilla(false);
                                if (elemento.getTipoTramitacion() != null && elemento.getTipoTramitacion().getUrl() != null) {
                                    mergeTraduccionTipoTramitacion(jTipoTramitacion, elemento.getTipoTramitacion());
                                }
                            }

                            jelemento.merge(elemento, jTipoTramitacion);
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
                    if (elemento.getPlantillaSel() != null && elemento.getPlantillaSel().getCodigo() != null) {
                        jTipoTramitacion = entityManager.find(JTipoTramitacion.class, elemento.getPlantillaSel().getCodigo());
                    } else {
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
                    nuevo.merge(elemento, jTipoTramitacion);
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
            sql = new StringBuilder("SELECT count(j) FROM JProcedimiento j LEFT OUTER JOIN j.procedimientoWF WF ON wf.workflow = true LEFT OUTER JOIN j.procedimientoWF WF2 ON wf2.workflow = false LEFT OUTER JOIN WF.traducciones t ON t.idioma=:idioma LEFT OUTER JOIN WF2.traducciones t2 ON t2.idioma=:idioma where 1 = 1 ");
        } else {
            sql = new StringBuilder(
                    "SELECT j.codigo, wf.codigo, wf2.codigo, wf.estado || '' || wf2.estado, j.tipo , j.codigoSIA, j.estadoSIA , j.siaFecha, j.codigoDir3SIA, t.nombre, t2.nombre FROM JProcedimiento j LEFT OUTER JOIN j.procedimientoWF WF ON wf.workflow = " + TypeProcedimientoWorfklow.PUBLICADO.getValor() + " LEFT OUTER JOIN j.procedimientoWF WF2 ON wf2.workflow = " + TypeProcedimientoWorfklow.MODIFICACION.getValor() + " LEFT OUTER JOIN WF.traducciones t ON t.idioma=:idioma LEFT OUTER JOIN WF2.traducciones t2 ON t2.idioma=:idioma where 1 = 1 ");
        }

        if (filtro.isRellenoTexto()) {
            sql.append(" and ( LOWER(cast(j.codigo as string)) like :filtro "
                    + " OR LOWER(t.nombre) LIKE :filtro  OR LOWER(t2.nombre) LIKE :filtro "
                    + " OR LOWER(j.tipo) LIKE :filtro  OR LOWER(cast(j.codigoSIA as string)) LIKE :filtro "
                    + " OR LOWER(cast(j.estadoSIA as string)) LIKE :filtro OR LOWER(cast(j.codigoDir3SIA as string)) LIKE :filtro )");
        }
        if (filtro.isRellenoFormaInicio()) {
            sql.append(" AND ( WF.formaInicio.codigo = :formaInicio or  WF2.formaInicio.codigo = :formaInicio)");
        }
        if (filtro.isRellenoPublicoObjetivo()) {
            sql.append(" AND exists (select pubObj from JProcedimientoPublicoObjectivo pubObj where pubObj.tipoPublicoObjetivo = :tipoPublicoObjetivo and pubObj.procedimiento.codigo = WF.codigo ) ");
        }
        if (filtro.isRellenoTipoProcedimiento()) {
            sql.append(" AND (WF.tipoProcedimiento.codigo = :tipoProcedimiento or WF2.tipoProcedimiento.codigo = :tipoProcedimiento) ");
        }
        if (filtro.isRellenoSilencioAdministrativo()) {
            sql.append(" AND (WF.silencioAdministrativo.codigo = :tipoSilencio or WF2.silencioAdministrativo.codigo = :tipoSilencio) ");
        }
        if (filtro.isRellenoIdUA()) {
            sql.append(" AND (WF.uaInstructor.codigo = :idUA OR WF2.uaInstructor.codigo = :idUA) ");
        }/*
        if (filtro.isRellenoNormativas()) {
            sql.append(" AND EXISTS ( SELECT procNorm FROM JProcedimientoNormativa procNorm WHERE (procNorm.codigo.procedimiento = WF.codigo OR procNorm.codigo.procedimiento = WF2.codigo) AND procNorm.codigo.normativa IN (:normativas) ) ");
        }*/
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
        if (filtro.isRellenoEstado()) {
            sql.append(" AND ( wf.estado = :estado OR wf2.estado = :estado) ");
        }
        if (filtro.getOrderBy() != null) {
            sql.append(" order by ").append(getOrden(filtro.getOrderBy(), filtro.isAscendente()));
            sql.append(filtro.isAscendente() ? " asc " : " desc ");
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
        if (filtro.isRellenoEstado()) {
            query.setParameter("estado", filtro.getEstado());
        }
        if (filtro.isRellenoIdUA()) {
            query.setParameter("idUA", filtro.getIdUA());
        }
        if (filtro.isRellenoNormativas()) {
            query.setParameter("normativas", filtro.getNormativasId());
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
        TypedQuery<JProcedimiento> query =
                entityManager.createNamedQuery(JProcedimiento.FIND_BY_ID, JProcedimiento.class);
        query.setParameter("codigo", id);
        List<JProcedimiento> result = query.getResultList();
        return Optional.ofNullable(result.isEmpty() ? null : result.get(0));
    }
}
