package es.caib.rolsac2.service.model;

import es.caib.rolsac2.service.utils.UtilComparador;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Dades d'un ProcedimientoTramite.
 */
@Schema(name = "ProcedimientoTramite")
public class ProcedimientoTramiteDTO extends ModelApi implements Cloneable, Comparable<ProcedimientoTramiteDTO> {

    /**
     * LOGGER
     */
    private static final Logger LOG = LoggerFactory.getLogger(ProcedimientoTramiteDTO.class);

    private Long codigo;

    private Integer orden;
    private Integer fase;

    private String codigoString;
    private UnidadAdministrativaDTO unidadAdministrativa;
    private ProcedimientoWorkflowDTO procedimiento;
    private TipoTramitacionDTO tipoTramitacion;
    private TipoTramitacionDTO plantillaSel;
    private List<ProcedimientoDocumentoDTO> listaDocumentos;
    private List<ProcedimientoDocumentoDTO> listaModelos;

    private Boolean tasaAsociada = false;
    private Literal requisitos;
    private Literal nombre;
    private Literal documentacion;
    private Literal observacion;
    private Literal terminoMaximo;

    private Date fechaPublicacion;
    private Date fechaInicio;
    private Date fechaCierre;

    /**
     * Tipos de presentacion: telematica, presencial o telefonica.
     **/
    private boolean tramitPresencial;
    private boolean tramitElectronica;
    private boolean tramitTelefonica;

    /**
     * Crea instancia de procedimiento tramite dto.
     *
     * @param idiomas idiomas
     * @return procedimiento tramite dto
     */
    public static ProcedimientoTramiteDTO createInstance(List<String> idiomas) {
        ProcedimientoTramiteDTO procedimientoTramite = new ProcedimientoTramiteDTO();
        procedimientoTramite.setCodigoString(String.valueOf(Calendar.getInstance().getTime().getTime()));
        procedimientoTramite.setTramitPresencial(false);
        procedimientoTramite.setTramitElectronica(true);
        procedimientoTramite.setTramitTelefonica(false);
        if (idiomas == null || idiomas.isEmpty()) {
            procedimientoTramite.setRequisitos(Literal.createInstance());
            procedimientoTramite.setNombre(Literal.createInstance());
            procedimientoTramite.setDocumentacion(Literal.createInstance());
            procedimientoTramite.setObservacion(Literal.createInstance());
            procedimientoTramite.setTerminoMaximo(Literal.createInstance());
        } else {
            procedimientoTramite.setRequisitos(Literal.createInstance(idiomas));
            procedimientoTramite.setNombre(Literal.createInstance(idiomas));
            procedimientoTramite.setDocumentacion(Literal.createInstance(idiomas));
            procedimientoTramite.setObservacion(Literal.createInstance(idiomas));
            procedimientoTramite.setTerminoMaximo(Literal.createInstance(idiomas));
        }
        return procedimientoTramite;
    }

    /**
     * Instancia un nuevo Procedimiento tramite dto.
     */
    public ProcedimientoTramiteDTO() {
    }

    /**
     * Estos dos metodos se necesitan para el datatable y el rowKey
     *
     * @return codigo
     */
    public String getIdString() {
        if (codigo == null) {
            return null;
        } else {
            return String.valueOf(codigo);
        }
    }

    /**
     * Establece id string.
     *
     * @param idString codigo to set
     */
    public void setIdString(final String idString) {
        if (idString == null) {
            this.codigo = null;
        } else {
            this.codigo = Long.valueOf(idString);
        }
    }

    /**
     * Agregar documento.
     *
     * @param doc doc
     */
    public void agregarDocumento(ProcedimientoDocumentoDTO doc) {
        if (getListaDocumentos() == null) {
            this.setListaDocumentos(new ArrayList<>());
        }
        boolean encontrado = false;
        for (int i = 0; i < this.getListaDocumentos().size(); i++) {
            ProcedimientoDocumentoDTO documento = this.getListaDocumentos().get(i);
            if (doc.getCodigo() == null && documento.getCodigo() == null && doc.getCodigoString() != null && doc.getCodigoString().equalsIgnoreCase(documento.getCodigoString())) {
                encontrado = true;
                this.getListaDocumentos().set(i, doc);
                break;
            } else if (doc.getCodigo() != null && documento.getCodigo() != null && doc.getCodigo().compareTo(documento.getCodigo()) == 0) {
                encontrado = true;
                this.getListaDocumentos().set(i, doc);
                break;
            }
        }

        if (!encontrado) {
            this.getListaDocumentos().add(doc);
        }
    }


    /**
     * Agregar modelo.
     *
     * @param doc doc
     */
    public void agregarModelo(ProcedimientoDocumentoDTO doc) {
        if (getListaModelos() == null) {
            this.setListaModelos(new ArrayList<>());
        }
        boolean encontrado = false;
        for (int i = 0; i < this.getListaModelos().size(); i++) {
            ProcedimientoDocumentoDTO documento = this.getListaModelos().get(i);
            if (doc.getCodigo() == null && documento.getCodigo() == null && doc.getCodigoString() != null && doc.getCodigoString().equalsIgnoreCase(documento.getCodigoString())) {
                encontrado = true;
                this.getListaModelos().set(i, doc);
                break;
            } else if (doc.getCodigo() != null && documento.getCodigo() != null && doc.getCodigo().compareTo(documento.getCodigo()) == 0) {
                encontrado = true;
                this.getListaModelos().set(i, doc);
                break;
            }
        }

        if (!encontrado) {
            this.getListaModelos().add(doc);
        }
    }

    /**
     * Instantiates a new Procedimiento tramite dto.
     *
     * @param id id
     */
    public ProcedimientoTramiteDTO(Long id) {
        this.codigo = id;
    }

    /**
     * Obtiene codigo.
     *
     * @return codigo
     */
    public Long getCodigo() {
        return codigo;
    }

    /**
     * Establece codigo.
     *
     * @param codigo codigo
     */
    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    /**
     * Obtiene unidad administrativa.
     *
     * @return unidad administrativa
     */
    public UnidadAdministrativaDTO getUnidadAdministrativa() {
        return unidadAdministrativa;
    }

    /**
     * Establece unidad administrativa.
     *
     * @param unidadAdministrativa unidad administrativa
     */
    public void setUnidadAdministrativa(UnidadAdministrativaDTO unidadAdministrativa) {
        this.unidadAdministrativa = unidadAdministrativa;
    }

    /**
     * Obtiene procedimiento.
     *
     * @return procedimiento
     */
    public ProcedimientoWorkflowDTO getProcedimiento() {
        return procedimiento;
    }

    /**
     * Establece procedimiento.
     *
     * @param procedimiento procedimiento
     */
    public void setProcedimiento(ProcedimientoWorkflowDTO procedimiento) {
        this.procedimiento = procedimiento;
    }

    /**
     * Obtiene tipo tramitacion.
     *
     * @return tipo tramitacion
     */
    public TipoTramitacionDTO getTipoTramitacion() {
        return tipoTramitacion;
    }

    /**
     * Establece tipo tramitacion.
     *
     * @param tipoTramitacion tipo tramitacion
     */
    public void setTipoTramitacion(TipoTramitacionDTO tipoTramitacion) {
        this.tipoTramitacion = tipoTramitacion;
    }

    /**
     * Obtiene lista documentos.
     *
     * @return lista documentos
     */
    public List<ProcedimientoDocumentoDTO> getListaDocumentos() {
        return listaDocumentos;
    }

    /**
     * Establece lista documentos.
     *
     * @param listaDocumentos lista documentos
     */
    public void setListaDocumentos(List<ProcedimientoDocumentoDTO> listaDocumentos) {
        this.listaDocumentos = listaDocumentos;
    }

    /**
     * Obtiene lista modelos.
     *
     * @return lista modelos
     */
    public List<ProcedimientoDocumentoDTO> getListaModelos() {
        return listaModelos;
    }

    /**
     * Establece lista modelos.
     *
     * @param listaModelos lista modelos
     */
    public void setListaModelos(List<ProcedimientoDocumentoDTO> listaModelos) {
        this.listaModelos = listaModelos;
    }

    /**
     * Obtiene tasa asociada.
     *
     * @return tasa asociada
     */
    public Boolean getTasaAsociada() {
        return tasaAsociada;
    }

    /**
     * Establece tasa asociada.
     *
     * @param tasaAsociada tasa asociada
     */
    public void setTasaAsociada(Boolean tasaAsociada) {
        this.tasaAsociada = tasaAsociada;
    }

    /**
     * Obtiene requisitos.
     *
     * @return requisitos
     */
    public Literal getRequisitos() {
        return requisitos;
    }

    /**
     * Establece requisitos.
     *
     * @param requisitos requisitos
     */
    public void setRequisitos(Literal requisitos) {
        this.requisitos = requisitos;
    }

    /**
     * Obtiene nombre.
     *
     * @return nombre
     */
    public Literal getNombre() {
        return nombre;
    }

    /**
     * Establece nombre.
     *
     * @param nombre nombre
     */
    public void setNombre(Literal nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene documentacion.
     *
     * @return documentacion
     */
    public Literal getDocumentacion() {
        return documentacion;
    }

    /**
     * Establece documentacion.
     *
     * @param documentacion documentacion
     */
    public void setDocumentacion(Literal documentacion) {
        this.documentacion = documentacion;
    }

    /**
     * Obtiene observacion.
     *
     * @return observacion
     */
    public Literal getObservacion() {
        return observacion;
    }

    /**
     * Establece observacion.
     *
     * @param observacion observacion
     */
    public void setObservacion(Literal observacion) {
        this.observacion = observacion;
    }

    /**
     * Obtiene termino maximo.
     *
     * @return termino maximo
     */
    public Literal getTerminoMaximo() {
        return terminoMaximo;
    }

    /**
     * Establece termino maximo.
     *
     * @param terminoMaximo termino maximo
     */
    public void setTerminoMaximo(Literal terminoMaximo) {
        this.terminoMaximo = terminoMaximo;
    }

    /**
     * Obtiene fecha publicacion.
     *
     * @return fecha publicacion
     */
    public Date getFechaPublicacion() {
        return fechaPublicacion;
    }

    /**
     * Establece fecha publicacion.
     *
     * @param fechaPublicacion fecha publicacion
     */
    public void setFechaPublicacion(Date fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    /**
     * Obtiene fecha inicio.
     *
     * @return fecha inicio
     */
    public Date getFechaInicio() {
        return fechaInicio;
    }

    /**
     * Establece fecha inicio.
     *
     * @param fechaInicio fecha inicio
     */
    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * Obtiene fecha cierre.
     *
     * @return fecha cierre
     */
    public Date getFechaCierre() {
        return fechaCierre;
    }

    /**
     * Establece fecha cierre.
     *
     * @param fechaCierre fecha cierre
     */
    public void setFechaCierre(Date fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    /**
     * Obtiene codigo string.
     *
     * @return codigo string
     */
    public String getCodigoString() {
        if (codigo == null) {
            return codigoString;
        } else {
            return codigo.toString();
        }
    }

    /**
     * Establece codigo string.
     *
     * @param codigoString codigo string
     */
    public void setCodigoString(String codigoString) {
        this.codigoString = codigoString;
    }

    /**
     * Obtiene fase.
     *
     * @return fase
     */
    public Integer getFase() {
        return fase;
    }

    /**
     * Establece fase.
     *
     * @param fase fase
     */
    public void setFase(Integer fase) {
        this.fase = fase;
    }

    /**
     * Obtiene plantilla sel.
     *
     * @return plantilla sel
     */
    public TipoTramitacionDTO getPlantillaSel() {
        return plantillaSel;
    }

    /**
     * Establece plantilla sel.
     *
     * @param plantillaSel plantilla sel
     */
    public void setPlantillaSel(TipoTramitacionDTO plantillaSel) {
        this.plantillaSel = plantillaSel;
    }

    /**
     * Is tramit presencial boolean.
     *
     * @return boolean
     */
    public boolean isTramitPresencial() {
        return tramitPresencial;
    }

    /**
     * Establece tramit presencial.
     *
     * @param tramitPresencial tramit presencial
     */
    public void setTramitPresencial(boolean tramitPresencial) {
        this.tramitPresencial = tramitPresencial;
    }

    /**
     * Is tramit electronica boolean.
     *
     * @return boolean
     */
    public boolean isTramitElectronica() {
        return tramitElectronica;
    }

    /**
     * Establece tramit electronica.
     *
     * @param tramitElectronica tramit electronica
     */
    public void setTramitElectronica(boolean tramitElectronica) {
        this.tramitElectronica = tramitElectronica;
    }

    /**
     * Is tramit telefonica boolean.
     *
     * @return boolean
     */
    public boolean isTramitTelefonica() {
        return tramitTelefonica;
    }

    /**
     * Establece tramit telefonica.
     *
     * @param tramitTelefonica tramit telefonica
     */
    public void setTramitTelefonica(boolean tramitTelefonica) {
        this.tramitTelefonica = tramitTelefonica;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }


    public Object clone() {
        ProcedimientoTramiteDTO obj = null;
        try {
            obj = (ProcedimientoTramiteDTO) super.clone();
            obj.setOrden(this.getOrden());
            if (obj.tipoTramitacion != null) {
                obj.tipoTramitacion = this.tipoTramitacion.clone();
            }
            if (this.getPlantillaSel() != null) {
                obj.setPlantillaSel(this.getPlantillaSel().clone());
            }

            if (obj.nombre != null) {
                obj.nombre = (Literal) this.nombre.clone();
            }
            if (obj.documentacion != null) {
                obj.documentacion = (Literal) this.documentacion.clone();
            }
            if (obj.requisitos != null) {
                obj.requisitos = (Literal) this.requisitos.clone();
            }
            if (obj.observacion != null) {
                obj.observacion = (Literal) this.observacion.clone();
            }
            if (obj.terminoMaximo != null) {
                obj.terminoMaximo = (Literal) this.terminoMaximo.clone();
            }
            if (listaDocumentos != null) {
                List<ProcedimientoDocumentoDTO> lista = new ArrayList<>();
                for (ProcedimientoDocumentoDTO doc : listaDocumentos) {
                    lista.add((ProcedimientoDocumentoDTO) doc.clone());
                }
                obj.setListaDocumentos(lista);
            }
            if (listaModelos != null) {
                List<ProcedimientoDocumentoDTO> lista = new ArrayList<>();
                for (ProcedimientoDocumentoDTO doc : listaModelos) {
                    lista.add((ProcedimientoDocumentoDTO) doc.clone());
                }
                obj.setListaModelos(lista);
            }
            if (this.getFechaInicio() != null) {
                obj.setFechaInicio(new Date(this.getFechaInicio().getTime()));
            }
            if (this.getFechaCierre() != null) {
                obj.setFechaCierre(new Date(this.getFechaCierre().getTime()));
            }
            if (this.getFechaPublicacion() != null) {
                obj.setFechaPublicacion(new Date(this.getFechaPublicacion().getTime()));
            }
        } catch (CloneNotSupportedException ex) {
            LOG.error(" no se puede duplicar", ex);
        }
        return obj;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProcedimientoTramiteDTO that = (ProcedimientoTramiteDTO) o;
        return codigo.equals(that.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public int compareTo(ProcedimientoTramiteDTO data2) {
        return compareTo(data2, true);
    }

    public int compareTo(ProcedimientoTramiteDTO data2, boolean mostrarLog) {
        if (mostrarLog) {
            LOG.debug("Comparando ProcedimientoTramiteDTO: {} con {}", this.getCodigo(), data2 != null ? data2.getCodigo() : "null");
        }

        if (data2 == null) {
            if (mostrarLog) {
                LOG.error("El segundo es null");
            }
            return 1;
        }

        if (UtilComparador.compareTo(this.getOrden(), data2.getOrden()) != 0) {
            if (mostrarLog) {
                LOG.error("El orden es diferente: {} con {}", this.getOrden(), data2.getOrden());
            }
            return UtilComparador.compareTo(this.getOrden(), data2.getOrden());
        }

        if (UtilComparador.compareTo(this.getCodigo(), data2.getCodigo()) != 0) {
            if (mostrarLog) {
                LOG.error("El codigo es diferente: {} con {}", this.getCodigo(), data2.getCodigo());
            }
            return UtilComparador.compareTo(this.getCodigo(), data2.getCodigo());
        }

        if (UtilComparador.compareTo(this.getDocumentacion(), data2.getDocumentacion()) != 0) {
            if (mostrarLog) {
                LOG.error("La documentacion es diferente: {} con {}", this.getDocumentacion(), data2.getDocumentacion());
            }
            return UtilComparador.compareTo(this.getDocumentacion(), data2.getDocumentacion());
        }

        if (UtilComparador.compareTo(this.getNombre(), data2.getNombre()) != 0) {
            if (mostrarLog) {
                LOG.error("El nombre es diferente: {} con {}", this.getNombre(), data2.getNombre());
            }
            return UtilComparador.compareTo(this.getNombre(), data2.getNombre());
        }

        if (UtilComparador.compareTo(this.getRequisitos(), data2.getRequisitos()) != 0) {
            if (mostrarLog) {
                LOG.error("Los requisitos son diferentes: {} con {}", this.getRequisitos(), data2.getRequisitos());
            }
            return UtilComparador.compareTo(this.getRequisitos(), data2.getRequisitos());
        }

        if (UtilComparador.compareTo(this.getTerminoMaximo(), data2.getTerminoMaximo()) != 0) {
            if (mostrarLog) {
                LOG.error("El termino maximo es diferente: {} con {}", this.getTerminoMaximo(), data2.getTerminoMaximo());
            }
            return UtilComparador.compareTo(this.getTerminoMaximo(), data2.getTerminoMaximo());
        }

        if (UtilComparador.compareTo(this.getObservacion(), data2.getObservacion()) != 0) {
            if (mostrarLog) {
                LOG.error("La observacion es diferente: {} con {}", this.getObservacion(), data2.getObservacion());
            }
            return UtilComparador.compareTo(this.getObservacion(), data2.getObservacion());
        }
        if (UtilComparador.compareTo(this.getFase(), data2.getFase()) != 0) {
            if (mostrarLog) {
                LOG.error("La fase es diferente: {} con {}", this.getFase(), data2.getFase());
            }
            return UtilComparador.compareTo(this.getFase(), data2.getFase());
        }
        if (UtilComparador.compareTo(this.getFechaCierre(), data2.getFechaCierre()) != 0) {
            if (mostrarLog) {
                LOG.error("La fecha de cierre es diferente: {} con {}", this.getFechaCierre(), data2.getFechaCierre());
            }
            return UtilComparador.compareTo(this.getFechaCierre(), data2.getFechaCierre());
        }
        if (UtilComparador.compareTo(this.getFechaInicio(), data2.getFechaInicio()) != 0) {
            if (mostrarLog) {
                LOG.error("La fecha de inicio es diferente: {} con {}", this.getFechaInicio(), data2.getFechaInicio());
            }
            return UtilComparador.compareTo(this.getFechaInicio(), data2.getFechaInicio());
        }
        if (UtilComparador.compareTo(this.getFechaPublicacion(), data2.getFechaPublicacion()) != 0) {
            if (mostrarLog) {
                LOG.error("La fecha de publicacion es diferente: {} con {}", this.getFechaPublicacion(), data2.getFechaPublicacion());
            }
            return UtilComparador.compareTo(this.getFechaPublicacion(), data2.getFechaPublicacion());
        }
        if (ProcedimientoDocumentoDTO.compareTo(this.getListaModelos(), data2.getListaModelos()) != 0) {
            if (mostrarLog) {
                LOG.error("La lista de modelos es diferente: {} con {}", this.getListaModelos(), data2.getListaModelos());
            }
            return ProcedimientoDocumentoDTO.compareTo(this.getListaModelos(), data2.getListaModelos());
        }
        if (ProcedimientoDocumentoDTO.compareTo(this.getListaDocumentos(), data2.getListaDocumentos()) != 0) {
            if (mostrarLog) {
                LOG.error("La lista de documentos es diferente: {} con {}", this.getListaDocumentos(), data2.getListaDocumentos());
            }
            return ProcedimientoDocumentoDTO.compareTo(this.getListaDocumentos(), data2.getListaDocumentos());
        }
        if (UtilComparador.compareTo(this.isTramitElectronica(), data2.isTramitElectronica()) != 0) {
            if (mostrarLog) {
                LOG.error("La tramitacion electronica es diferente: {} con {}", this.isTramitElectronica(), data2.isTramitElectronica());
            }
            return UtilComparador.compareTo(this.isTramitElectronica(), data2.isTramitElectronica());
        }
        if (UtilComparador.compareTo(this.isTramitPresencial(), data2.isTramitPresencial()) != 0) {
            if (mostrarLog) {
                LOG.error("La tramitacion presencial es diferente: {} con {}", this.isTramitPresencial(), data2.isTramitPresencial());
            }
            return UtilComparador.compareTo(this.isTramitPresencial(), data2.isTramitPresencial());
        }
        if (UtilComparador.compareTo(this.isTramitTelefonica(), data2.isTramitTelefonica()) != 0) {
            if (mostrarLog) {
                LOG.error("La tramitacion telefonica es diferente: {} con {}", this.isTramitTelefonica(), data2.isTramitTelefonica());
            }
            return UtilComparador.compareTo(this.isTramitTelefonica(), data2.isTramitTelefonica());
        }
        if (UtilComparador.compareTo(this.getTipoTramitacion(), data2.getTipoTramitacion()) != 0) {
            if (mostrarLog) {
                LOG.error("El tipo de tramitacion es diferente: {} con {}", this.getTipoTramitacion(), data2.getTipoTramitacion());
            }
            return UtilComparador.compareTo(this.getTipoTramitacion(), data2.getTipoTramitacion());
        }
        if (UtilComparador.compareTo(this.getPlantillaSel(), data2.getPlantillaSel()) != 0) {
            if (mostrarLog) {
                LOG.error("La plantilla seleccionada es diferente: {} con {}", this.getPlantillaSel(), data2.getPlantillaSel());
            }
            return UtilComparador.compareTo(this.getPlantillaSel(), data2.getPlantillaSel());
        }
        if (UtilComparador.compareTo(this.getUnidadAdministrativa(), data2.getUnidadAdministrativa()) != 0) {
            if (mostrarLog) {
                LOG.error("La unidad administrativa es diferente: {} con {}", this.getUnidadAdministrativa(), data2.getUnidadAdministrativa());
            }
            return UtilComparador.compareTo(this.getUnidadAdministrativa(), data2.getUnidadAdministrativa());
        }
        if (mostrarLog) {
            LOG.debug("Los ProcedimientoTramiteDTO son iguales");
        }
        return 0;
    }

    public static int compareTo(List<ProcedimientoTramiteDTO> dato, List<ProcedimientoTramiteDTO> dato2) {
        return compareTo(dato, dato2, false);
    }


    public static int compareTo(List<ProcedimientoTramiteDTO> dato, List<ProcedimientoTramiteDTO> dato2, boolean mostrarLog) {
        if ((dato == null || dato.size() == 0) && (dato2 == null || dato2.size() == 0)) {
            if (mostrarLog) {
                LOG.error("Ambos son null o vacios");
            }
            return 0;
        }
        if ((dato == null || dato.size() == 0) && (dato2 != null && dato2.size() > 0)) {
            if (mostrarLog) {
                LOG.error("El primero es null o vacio");
            }
            return -1;
        }
        if ((dato != null && dato.size() > 0) && (dato2 == null || dato2.size() == 0)) {
            if (mostrarLog) {
                LOG.error("El segundo es null o vacio");
            }
            return 1;
        }

        if (dato.size() > dato2.size()) {
            return 1;
        } else if (dato2.size() > dato.size()) {
            return -1;
        } else {
            for (ProcedimientoTramiteDTO tram : dato) {
                boolean encontrado = false;
                for (ProcedimientoTramiteDTO tram2 : dato2) {
                    if (tram.getCodigo() != null && tram2.getCodigo() != null && tram.getCodigo().compareTo(tram2.getCodigo()) == 0) {
                        int comparacion = tram.compareTo(tram2);
                        if (comparacion != 0) {
                            if (mostrarLog) {
                                LOG.error("El tramite ha cambiado: " + tram.getCodigo());
                                LOG.error("Comparacion:" + comparacion);
                                LOG.error("El tramite : " + tram);
                            }
                            return comparacion;
                        }
                        encontrado = true;
                    } else if (tram.getCodigoString() != null && tram2.getCodigoString() != null && tram.getCodigoString().equals(tram2.getCodigoString())) {
                        int comparacion = tram.compareTo(tram2);
                        if (comparacion != 0) {
                            if (mostrarLog) {
                                LOG.error("El tramite ha cambiado: " + tram.getCodigo());
                                LOG.error("ComparacionString:" + comparacion);
                                LOG.error("El tramite : " + tram);
                            }
                            return comparacion;
                        }
                        encontrado = true;
                    }
                }

                if (!encontrado) {
                    if (mostrarLog) {
                        LOG.error("No existe el tramite " + tram.getCodigo());
                        LOG.error("El tramite : " + tram);
                    }
                    return -1;
                }


            }
        }
        return 0;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        try {
            sb.append("ProcedimientoTramiteDTO{");
            sb.append("codigo=").append(codigo);
            sb.append(", orden=").append(orden);
            sb.append(", fase=").append(fase);
            sb.append(", codigoString='").append(codigoString).append('\'');
            sb.append(", unidadAdministrativa=").append(unidadAdministrativa);
            if (procedimiento == null) {
                sb.append(", procedimiento=null");
            } else {
                sb.append(", procedimiento.codigo=").append(procedimiento.getCodigo());
                sb.append(", procedimiento.nombre=").append(procedimiento.getNombre());
            }
            sb.append(", tipoTramitacion=").append(tipoTramitacion);
            sb.append(", plantillaSel=").append(plantillaSel);
            if (listaDocumentos == null) {
                sb.append(", listaDocumentos=null");
            } else if (listaDocumentos.isEmpty()) {
                sb.append(", listaDocumentos=[]");
            } else {
                sb.append(", listaDocumentos.size=").append(listaDocumentos.size());
                sb.append(", listaDocumentos={");
                for (ProcedimientoDocumentoDTO doc : listaDocumentos) {
                    sb.append(", documento=").append(doc);
                }
                sb.append('}');
            }
            if (listaModelos == null) {
                sb.append(", listaModelos=null");
            } else if (listaModelos.isEmpty()) {
                sb.append(", listaModelos=[]");
            } else {
                sb.append(", listaModelos.size=").append(listaModelos.size());
                sb.append(", listaModelos={");
                for (ProcedimientoDocumentoDTO doc : listaModelos) {
                    sb.append(", modelo=").append(doc);
                }
                sb.append('}');
            }
            sb.append(", tasaAsociada=").append(tasaAsociada);
            sb.append(", requisitos=").append(requisitos);
            sb.append(", nombre=").append(nombre);
            sb.append(", documentacion=").append(documentacion);
            sb.append(", observacion=").append(observacion);
            sb.append(", terminoMaximo=").append(terminoMaximo);
            sb.append(", fechaPublicacion=").append(fechaPublicacion);
            sb.append(", fechaInicio=").append(fechaInicio);
            sb.append(", fechaCierre=").append(fechaCierre);
            sb.append(", tramitPresencial=").append(tramitPresencial);
            sb.append(", tramitElectronica=").append(tramitElectronica);
            sb.append(", tramitTelefonica=").append(tramitTelefonica);
            sb.append('}');

        } catch (Exception e) {
            LOG.error("Error al convertir a String el ProcedimientoTramiteDTO", e);
        }
        return sb.toString();
    }
}
