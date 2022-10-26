package es.caib.rolsac2.back.controller.maestras;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.controller.SessionBean;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.back.utils.ValidacionTipoUtils;
import es.caib.rolsac2.service.facade.AdministracionSupServiceFacade;
import es.caib.rolsac2.service.facade.TipoUnidadAdministrativaServiceFacade;
import es.caib.rolsac2.service.facade.UnidadAdministrativaServiceFacade;
import es.caib.rolsac2.service.model.*;
import es.caib.rolsac2.service.model.types.TypeModoAcceso;
import es.caib.rolsac2.service.model.types.TypeNivelGravedad;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Named
@ViewScoped
public class DialogUnidadAdministrativa extends AbstractController implements Serializable {
    private static final Logger LOG = LoggerFactory.getLogger(DialogUnidadAdministrativa.class);

    private String id = "";

    private UnidadAdministrativaDTO data;

    private List<EntidadDTO> entidadesActivas;

    private List<UnidadAdministrativaDTO> padreSeleccionado;

    private List<TipoUnidadAdministrativaDTO> tipos;

    private List<TipoSexoDTO> tiposSexo;

    private String identificadorOld;

    private List<UsuarioDTO> usuarios;

    private UsuarioDTO usuarioSeleccionado;

    private List<TipoMateriaSIADTO> materiasSIA;

    private TipoMateriaSIADTO materiaSeleccionada;

    private List<SeccionDTO> secciones;

    private SeccionDTO seccionSeleccionada;

    private List<EdificioDTO> edificios;

    private EdificioDTO edificioSeleccionado;

    @EJB
    private UnidadAdministrativaServiceFacade unidadAdministrativaServiceFacade;

    @EJB
    private AdministracionSupServiceFacade administracionSupServiceFacade;

    @EJB
    private TipoUnidadAdministrativaServiceFacade tipoUnidadAdministrativaServiceFacade;

    public void load() {
        LOG.debug("init1");

        this.setearIdioma();
        data = new UnidadAdministrativaDTO();

        tiposSexo = unidadAdministrativaServiceFacade.findTipoSexo();
        entidadesActivas = administracionSupServiceFacade.findEntidadActivas();
        tipos = tipoUnidadAdministrativaServiceFacade.findTipo();

        if (this.isModoAlta()) {
            data = new UnidadAdministrativaDTO();
            data.setEntidad(sessionBean.getEntidad());
            data.setPadre(sessionBean.getUnidadActiva());
            data.setNombre(Literal.createInstance(sessionBean.getIdiomasPermitidosList()));
            data.setUrl(Literal.createInstance(sessionBean.getIdiomasPermitidosList()));
            data.setPresentacion(Literal.createInstance(sessionBean.getIdiomasPermitidosList()));
            data.setResponsable(Literal.createInstance(sessionBean.getIdiomasPermitidosList()));
        } else if (this.isModoEdicion() || this.isModoConsulta()) {
            data = unidadAdministrativaServiceFacade.findById(Long.valueOf(id));
            this.identificadorOld = data.getIdentificador();
        }

        usuarios = new ArrayList<>();

        final UsuarioDTO usu1 = new UsuarioDTO();
        usu1.setCodigo(1L);
        usu1.setIdentificador("Usuario 1_Apellido1_Apellido2");

        final UsuarioDTO usu2 = new UsuarioDTO();
        usu2.setCodigo(2L);
        usu2.setIdentificador("Usuario 2");

        final UsuarioDTO usu3 = new UsuarioDTO();
        usu3.setCodigo(3L);
        usu3.setIdentificador("Usuario 3");

        final UsuarioDTO usu4 = new UsuarioDTO();
        usu4.setCodigo(4L);
        usu4.setIdentificador("Usuario 4");

        final UsuarioDTO usu5 = new UsuarioDTO();
        usu5.setCodigo(5L);
        usu5.setIdentificador("Usuario 5");

        final UsuarioDTO usu6 = new UsuarioDTO();
        usu6.setCodigo(6L);
        usu6.setIdentificador("Usuario 6");

        final UsuarioDTO usuN = new UsuarioDTO();
        usuN.setCodigo(7L);
        usuN.setIdentificador("Usuario N");

        usuarios.add(usu1);
        usuarios.add(usu2);
        usuarios.add(usu3);
        usuarios.add(usu4);
        usuarios.add(usu5);
        usuarios.add(usu6);
        usuarios.add(usuN);

        materiasSIA = new ArrayList<>();

        final TipoMateriaSIADTO mat1 = new TipoMateriaSIADTO();
        final Literal l1= new Literal();
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

        materiasSIA.add(mat1);
        materiasSIA.add(mat2);
        materiasSIA.add(mat3);
        materiasSIA.add(mat4);

        secciones = new ArrayList<>();

        final SeccionDTO sec1 = new SeccionDTO();
        sec1.setCodigo(1L);
        sec1.setIdentificador("Portada Home");

        final SeccionDTO sec2 = new SeccionDTO();
        sec2.setCodigo(2L);
        sec2.setIdentificador("Portada UA");

        SeccionDTO sec3 = new SeccionDTO();
        sec3.setCodigo(3L);
        sec3.setIdentificador("Portada actualidad");

        SeccionDTO sec4 = new SeccionDTO();
        sec4.setCodigo(4L);
        sec4.setIdentificador("Portada administración");

        secciones.add(sec1);
        secciones.add(sec2);
        secciones.add(sec3);
        secciones.add(sec4);


        edificios = new ArrayList<>();

        final EdificioDTO e1 = new EdificioDTO();
        e1.setCodigo(1L);
        e1.setDireccion("C/ de Palma, 15");

        final EdificioDTO e2 = new EdificioDTO();
        e2.setCodigo(2L);
        e2.setDireccion("C/ Balmes, 20");

        final EdificioDTO e3 = new EdificioDTO();
        e3.setCodigo(3L);
        e3.setDireccion("C/ Serrano, 5");

        final EdificioDTO e4 = new EdificioDTO();
        e4.setCodigo(4L);
        e4.setDireccion("C/ de Valencia, 30");

        final EdificioDTO e5 = new EdificioDTO();
        e5.setCodigo(5L);
        e5.setDireccion("C/ de Alexandre Rosselló, 2");

        edificios.add(e1);
        edificios.add(e2);
        edificios.add(e3);
        edificios.add(e4);
        edificios.add(e5);

    }

    public void guardar() {
        if (!verificarGuardar()) {
            return;
        }

        if (this.data.getCodigo() == null) {
            unidadAdministrativaServiceFacade.create(this.data);
        } else {
            unidadAdministrativaServiceFacade.update(this.data);
        }

        // Retornamos resultados
        final DialogResult result = new DialogResult();
        if (this.getModoAcceso() != null) {
            result.setModoAcceso(TypeModoAcceso.valueOf(this.getModoAcceso()));
        } else {
            result.setModoAcceso(TypeModoAcceso.CONSULTA);
        }
        result.setResult(data);
        UtilJSF.closeDialog(result);
    }

    public void cerrar() {
        final DialogResult result = new DialogResult();
        if (this.getModoAcceso() != null) {
            result.setModoAcceso(TypeModoAcceso.valueOf(this.getModoAcceso()));
        } else {
            result.setModoAcceso(TypeModoAcceso.CONSULTA);
        }
        result.setCanceled(true);
        UtilJSF.closeDialog(result);
    }

    private boolean verificarGuardar() {
        if (Objects.isNull(this.data.getCodigo())
                && unidadAdministrativaServiceFacade.checkIdentificador(this.data.getIdentificador())) {
            UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteral("msg.existeIdentificador"), true);
            return false;
        }

        /*if (Objects.nonNull(this.data.getCodigo()) && !identificadorOld.equals(this.data.getIdentificador())
                && unidadAdministrativaServiceFacade.checkIdentificador(this.data.getIdentificador())) {
            UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteral("msg.existeIdentificador"), true);
            return false;
        }*/
        if (Objects.nonNull(this.data.getEmail()) && !ValidacionTipoUtils.esEmailValido(this.data.getEmail())) {
            UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteral("msg.email.novalido"), true);
            return false;
        }
        if (Objects.nonNull(this.data.getTelefono())
                && !ValidacionTipoUtils.esTelefonoValido(this.data.getTelefono())) {
            UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteral("msg.telefono.novalido"), true);
            return false;
        }
        if (Objects.nonNull(this.data.getFax()) && !ValidacionTipoUtils.esNumerico(this.data.getFax())) {
            UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteral("msg.fax.novalido"), true);
            return false;
        }
        List<String> idiomasPendientesDescripcion = ValidacionTipoUtils.esLiteralCorrecto(this.data.getNombre(), sessionBean.getIdiomasObligatoriosList());
        if(!idiomasPendientesDescripcion.isEmpty()) {
            UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, getLiteralFaltanIdiomas("dict.nombre", "dialogLiteral.validacion.idiomas", idiomasPendientesDescripcion), true);
            return false;
        }



        return true;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UnidadAdministrativaDTO getData() {
        return data;
    }

    public void setData(UnidadAdministrativaDTO data) {
        this.data = data;
    }

    public List<EntidadDTO> getEntidadesActivas() {
        return entidadesActivas;
    }

    public void setEntidadesActivas(List<EntidadDTO> entidadesActivas) {
        this.entidadesActivas = entidadesActivas;
    }

    public List<UnidadAdministrativaDTO> getPadreSeleccionado() {
        return padreSeleccionado;
    }

    public void setPadreSeleccionado(List<UnidadAdministrativaDTO> padreSeleccionado) {
        this.padreSeleccionado = padreSeleccionado;
    }

    public List<TipoUnidadAdministrativaDTO> getTipos() {
        return tipos;
    }

    public void setTipos(List<TipoUnidadAdministrativaDTO> tipoSeleccionado) {
        this.tipos = tipoSeleccionado;
    }

    public List<TipoSexoDTO> getTiposSexo() {
        return tiposSexo;
    }

    public void setTiposSexo(List<TipoSexoDTO> tiposSexo) {
        this.tiposSexo = tiposSexo;
    }

    public List<UsuarioDTO> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<UsuarioDTO> usuarios) {
        this.usuarios = usuarios;
    }

    public UsuarioDTO getUsuarioSeleccionado() {
        return usuarioSeleccionado;
    }

    public void setUsuarioSeleccionado(UsuarioDTO usuarioSeleccionado) {
        this.usuarioSeleccionado = usuarioSeleccionado;
    }

    public List<TipoMateriaSIADTO> getMateriasSIA() {
        return materiasSIA;
    }

    public void setMateriasSIA(List<TipoMateriaSIADTO> materiasSIA) {
        this.materiasSIA = materiasSIA;
    }

    public TipoMateriaSIADTO getMateriaSeleccionada() {
        return materiaSeleccionada;
    }

    public void setMateriaSeleccionada(TipoMateriaSIADTO materiaSeleccionada) {
        this.materiaSeleccionada = materiaSeleccionada;
    }

    public List<SeccionDTO> getSecciones() {
        return secciones;
    }

    public void setSecciones(List<SeccionDTO> secciones) {
        this.secciones = secciones;
    }

    public SeccionDTO getSeccionSeleccionada() {
        return seccionSeleccionada;
    }

    public void setSeccionSeleccionada(SeccionDTO seccionSeleccionada) {
        this.seccionSeleccionada = seccionSeleccionada;
    }

    public List<EdificioDTO> getEdificios() {
        return edificios;
    }

    public void setEdificios(List<EdificioDTO> edificios) {
        this.edificios = edificios;
    }

    public EdificioDTO getEdificioSeleccionado() {
        return edificioSeleccionado;
    }

    public void setEdificioSeleccionado(EdificioDTO edificioSeleccionado) {
        this.edificioSeleccionado = edificioSeleccionado;
    }
}
