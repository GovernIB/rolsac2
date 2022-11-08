package es.caib.rolsac2.back.controller.maestras;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.service.model.*;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@ViewScoped
public class DialogServicio extends AbstractController implements Serializable {

    private ProcedimientoDTO data;

    private String objeto;

    private String destinatarios;

    private String termino;

    private String legitimacion;

    private String validacion;

    private TipoPublicoObjetivoDTO publicoSeleccionado;

    private List<TipoPublicoObjetivoDTO> publicoObjetivo;

    private List<TipoPublicoObjetivoDTO> publicoObjetivoSeleccionado;

    private String tipoProcedimientoSeleccionado;

    private List<TipoMateriaSIADTO> materiasSIA;

    private TipoMateriaSIADTO materiaSIASeleccionada;

    private NormativaDTO normativaSeleccionada;

    private ProcedimientoDocumentoDTO documentoSeleccionado;

    private ProcedimientoTramiteDTO tramiteSeleccionado;

    private TipoTramitacionDTO tipoTramitacion;

    private String[] canalesSeleccionados;

    public void load() {
        this.setearIdioma();

        data = new ProcedimientoDTO();
        data.setCodigo(1L);
        final List<NormativaDTO> normativas = new ArrayList<>();
        final NormativaDTO n1 = new NormativaDTO();
        final TipoNormativaDTO tn1 = new TipoNormativaDTO();
        tn1.setIdentificador("Real Decreto 128/75");
        n1.setCodigo(1L);
        n1.setTipoNormativa(tn1);
        normativas.add(n1);
        // data.setNormativas(normativas);

        final List<TipoPublicoObjetivoDTO> publicos = new ArrayList<>();

        final TipoPublicoObjetivoDTO po1 = new TipoPublicoObjetivoDTO();
        po1.setCodigo(1l);
        po1.setIdentificador("Empresas");

        final TipoPublicoObjetivoDTO po2 = new TipoPublicoObjetivoDTO();
        po2.setCodigo(2l);
        po2.setIdentificador("Administraciones");

        final TipoPublicoObjetivoDTO po3 = new TipoPublicoObjetivoDTO();
        po3.setCodigo(3l);
        po3.setIdentificador("Personas");

        final TipoPublicoObjetivoDTO po4 = new TipoPublicoObjetivoDTO();
        po4.setCodigo(4l);
        po4.setIdentificador("Empleados públicos");

        publicos.add(po1);
        publicos.add(po2);
        publicos.add(po3);
        publicos.add(po4);

        data.setPublicosObjetivo(publicos);


        final TipoMateriaSIADTO mat1 = new TipoMateriaSIADTO();
        final Literal l1 = new Literal();
        final List<Traduccion> traducciones = new ArrayList<>();
        final Traduccion t1 = new Traduccion();
        t1.setLiteral("Descripción del tipo de materia SIA.");
        t1.setIdioma("es");

        final Traduccion t2 = new Traduccion();
        t1.setLiteral("Descripción del tipo de materia SIA.");
        t1.setIdioma("ca");

        traducciones.add(t1);
        traducciones.add(t2);

        l1.setTraducciones(traducciones);
        mat1.setCodigo(1L);
        mat1.setIdentificador("Materia SIA 1");
        mat1.setDescripcion(l1);

        final TipoMateriaSIADTO mat2 = new TipoMateriaSIADTO();
        mat2.setCodigo(2L);
        mat2.setIdentificador("Materia SIA 2");
        mat2.setDescripcion(l1);

        final TipoMateriaSIADTO mat3 = new TipoMateriaSIADTO();
        mat3.setCodigo(3L);
        mat3.setIdentificador("Materia SIA 2");
        mat3.setDescripcion(l1);

        final TipoMateriaSIADTO mat4 = new TipoMateriaSIADTO();
        mat4.setCodigo(4L);
        mat4.setIdentificador("Materia SIA N");
        mat4.setDescripcion(l1);

        materiasSIA = new ArrayList<>();
        materiasSIA.add(mat1);
        materiasSIA.add(mat2);
        materiasSIA.add(mat3);
        materiasSIA.add(mat4);


    }

    public ProcedimientoDTO getData() {
        return data;
    }

    public void setData(ProcedimientoDTO data) {
        this.data = data;
    }

    public String getObjeto() {
        return objeto;
    }

    public void setObjeto(String objeto) {
        this.objeto = objeto;
    }

    public String getDestinatarios() {
        return destinatarios;
    }

    public void setDestinatarios(String destinatarios) {
        this.destinatarios = destinatarios;
    }

    public String getTermino() {
        return termino;
    }

    public void setTermino(String termino) {
        this.termino = termino;
    }

    public String getLegitimacion() {
        return legitimacion;
    }

    public void setLegitimacion(String legitimacion) {
        this.legitimacion = legitimacion;
    }

    public String getValidacion() {
        return validacion;
    }

    public void setValidacion(String validacion) {
        this.validacion = validacion;
    }

    public String getTipoProcedimientoSeleccionado() {
        return tipoProcedimientoSeleccionado;
    }

    public void setTipoProcedimientoSeleccionado(String tipoProcedimientoSeleccionado) {
        this.tipoProcedimientoSeleccionado = tipoProcedimientoSeleccionado;
    }

    public List<TipoMateriaSIADTO> getMateriasSIA() {
        return materiasSIA;
    }

    public void setMateriasSIA(List<TipoMateriaSIADTO> materiasSIA) {
        this.materiasSIA = materiasSIA;
    }

    public TipoMateriaSIADTO getMateriaSIASeleccionada() {
        return materiaSIASeleccionada;
    }

    public void setMateriaSIASeleccionada(TipoMateriaSIADTO materiaSIASeleccionada) {
        this.materiaSIASeleccionada = materiaSIASeleccionada;
    }

    public NormativaDTO getNormativaSeleccionada() {
        return normativaSeleccionada;
    }

    public void setNormativaSeleccionada(NormativaDTO normativaSeleccionada) {
        this.normativaSeleccionada = normativaSeleccionada;
    }

    public ProcedimientoDocumentoDTO getDocumentoSeleccionado() {
        return documentoSeleccionado;
    }

    public void setDocumentoSeleccionado(ProcedimientoDocumentoDTO documentoSeleccionado) {
        this.documentoSeleccionado = documentoSeleccionado;
    }

    public ProcedimientoTramiteDTO getTramiteSeleccionado() {
        return tramiteSeleccionado;
    }

    public void setTramiteSeleccionado(ProcedimientoTramiteDTO tramiteSeleccionado) {
        this.tramiteSeleccionado = tramiteSeleccionado;
    }

    public TipoPublicoObjetivoDTO getPublicoSeleccionado() {
        return publicoSeleccionado;
    }

    public void setPublicoSeleccionado(TipoPublicoObjetivoDTO publicoSeleccionado) {
        this.publicoSeleccionado = publicoSeleccionado;
    }


    public TipoTramitacionDTO getTipoTramitacion() {
        return tipoTramitacion;
    }

    public void setTipoTramitacion(TipoTramitacionDTO tipoTramitacion) {
        this.tipoTramitacion = tipoTramitacion;
    }

    public String[] getCanalesSeleccionados() {
        return canalesSeleccionados;
    }

    public void setCanalesSeleccionados(String[] canalesSeleccionados) {
        this.canalesSeleccionados = canalesSeleccionados;
    }
}
