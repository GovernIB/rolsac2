package es.caib.rolsac2.back.controller.maestras;

import es.caib.rolsac2.back.controller.AbstractController;
import es.caib.rolsac2.back.model.DialogResult;
import es.caib.rolsac2.back.utils.UtilJSF;
import es.caib.rolsac2.service.facade.CuadroMandoServiceFacade;
import es.caib.rolsac2.service.facade.NormativaServiceFacade;
import es.caib.rolsac2.service.model.Constantes;
import es.caib.rolsac2.service.model.CuadroControlDTO;
import es.caib.rolsac2.service.model.UnidadAdministrativaDTO;
import es.caib.rolsac2.service.model.auditoria.AuditoriaCMGridDTO;
import es.caib.rolsac2.service.model.auditoria.EstadisticaCMDTO;
import es.caib.rolsac2.service.model.filtro.CuadroMandoFiltro;
import es.caib.rolsac2.service.model.types.TypeAccionAuditoria;
import es.caib.rolsac2.service.model.types.TypeModoAcceso;
import es.caib.rolsac2.service.model.types.TypeParametroVentana;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.axes.cartesian.CartesianScales;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearAxes;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearTicks;
import org.primefaces.model.charts.bar.BarChartDataSet;
import org.primefaces.model.charts.bar.BarChartModel;
import org.primefaces.model.charts.bar.BarChartOptions;
import org.primefaces.model.charts.optionconfig.legend.Legend;
import org.primefaces.model.charts.optionconfig.legend.LegendLabel;
import org.primefaces.model.charts.optionconfig.title.Title;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.*;

@Named
@ViewScoped
public class ViewCuadroControl extends AbstractController implements Serializable {

    private static final long serialVersionUID = -7992474170848445700L;

    private static final Logger LOG = LoggerFactory.getLogger(ViewCuadroControl.class);

    @EJB
    private CuadroMandoServiceFacade cuadroMandoServiceFacade;

    @EJB
    private NormativaServiceFacade normativaServiceFacade;

    private UnidadAdministrativaDTO uaSeleccionada;

    private List<CuadroControlDTO> listaDtos;

    private CuadroControlDTO procedimientoDto;

    private CuadroControlDTO serviciosDto;

    private CuadroControlDTO normativaDto;

    private boolean buscarTodo;

    private List<AuditoriaCMGridDTO> procedimientosModificados;

    private List<AuditoriaCMGridDTO> serviciosModificados;

    private AuditoriaCMGridDTO procSeleccionado;

    private AuditoriaCMGridDTO servSeleccionado;

    private CuadroMandoFiltro filtroProcedimientos;

    private CuadroMandoFiltro filtroServicios;

    private CuadroMandoFiltro filtroUas;
    private BarChartModel altasChartModel;

    private BarChartModel bajasChartModel;

    private BarChartModel modificacionesChartModel;

    private List<String> aplicaciones;

    private List<BarChartModel> graficosEstadisticas;

    public void load() {
        LOG.debug("load");
        this.setearIdioma();
        uaSeleccionada = new UnidadAdministrativaDTO();
        listaDtos = new ArrayList<>();
        procedimientoDto = new CuadroControlDTO();
        serviciosDto = new CuadroControlDTO();
        normativaDto = new CuadroControlDTO();

        buscarTodo = false;

        uaSeleccionada = sessionBean.getUnidadActiva();
        cargarFiltroServicios();
        cargarFiltroProcedimientos();
        cargarFiltroUas();
        procedimientosModificados = cuadroMandoServiceFacade.findAuditoriasUltimaSemana(filtroProcedimientos);
        serviciosModificados = cuadroMandoServiceFacade.findAuditoriasUltimaSemana(filtroServicios);

        buscarNumeroContenidos();

        crearAltasBarModel();
        crearBajasBarModel();
        crearModificacionesBarModel();
        crearGraficaEstadisticas();
        String prueba = "";
    }

    public void cambioCheckTodasUa() {
        cargarFiltroServicios();
        cargarFiltroProcedimientos();
        cargarFiltroUas();
        buscarNumeroContenidos();
        crearAltasBarModel();
        crearBajasBarModel();
        crearModificacionesBarModel();
        crearGraficaEstadisticas();
    }

    public void buscarNumeroContenidos() {
        listaDtos = new ArrayList<>();
        if (!buscarTodo) {
            procedimientoDto.setTitulos("viewCuadroControl.procedimientos");
            Long[] totalProcedimientos = cuadroMandoServiceFacade.getProcedimientosByUa(uaSeleccionada.getCodigo(), Constantes.PROCEDIMIENTO, UtilJSF.getSessionBean().getLang());
            procedimientoDto.setNumeroVisible(totalProcedimientos[0].toString()); //(cuadroMandoServiceFacade.countProcEstadoByUa(uaSeleccionada.getCodigo(), "1").toString());
            procedimientoDto.setNumeroNoVisible(totalProcedimientos[1].toString()); //cuadroMandoServiceFacade.countProcEstadoByUa(uaSeleccionada.getCodigo(), "2").toString());
            procedimientoDto.setNumeroTotal(totalProcedimientos[2].toString()); //cuadroMandoServiceFacade.countProcedimientosByUa(uaSeleccionada.getCodigo()).toString());
            listaDtos.add(procedimientoDto);

            serviciosDto.setTitulos("viewCuadroControl.servicios");
            Long[] totalServicios = cuadroMandoServiceFacade.getProcedimientosByUa(uaSeleccionada.getCodigo(), Constantes.SERVICIO, UtilJSF.getSessionBean().getLang());
            serviciosDto.setNumeroVisible(totalServicios[0].toString()); //cuadroMandoServiceFacade.countServEstadoByUa(uaSeleccionada.getCodigo(), "1").toString());
            serviciosDto.setNumeroNoVisible(totalServicios[1].toString()); //cuadroMandoServiceFacade.countServEstadoByUa(uaSeleccionada.getCodigo(), "2").toString());
            serviciosDto.setNumeroTotal(totalServicios[2].toString()); //cuadroMandoServiceFacade.countServicioByUa(uaSeleccionada.getCodigo()).toString());
            listaDtos.add(serviciosDto);

            normativaDto.setTitulos("viewCuadroControl.normativas");
            normativaDto.setNumeroVisible("");
            normativaDto.setNumeroNoVisible("");
            normativaDto.setNumeroTotal(normativaServiceFacade.countByUa(uaSeleccionada.getCodigo()).toString());
            listaDtos.add(normativaDto);
        } else {
            procedimientoDto.setTitulos("viewCuadroControl.procedimientos");
            //procedimientoDto.setNumeroVisible(cuadroMandoServiceFacade.countProcEstadoByUa(uaSeleccionada.getCodigo(), "3").toString());
            //procedimientoDto.setNumeroNoVisible(cuadroMandoServiceFacade.countProcEstadoByUa(uaSeleccionada.getCodigo(), "4").toString());
            procedimientoDto.setNumeroTotal(cuadroMandoServiceFacade.countAllProcedimientos().toString());
            listaDtos.add(procedimientoDto);

            serviciosDto.setTitulos("viewCuadroControl.servicios");
            //serviciosDto.setNumeroVisible(cuadroMandoServiceFacade.countServEstadoByUa(uaSeleccionada.getCodigo(), "3").toString());
            //serviciosDto.setNumeroNoVisible(cuadroMandoServiceFacade.countServEstadoByUa(uaSeleccionada.getCodigo(), "4").toString());
            serviciosDto.setNumeroTotal(cuadroMandoServiceFacade.countAllServicio().toString());
            listaDtos.add(serviciosDto);

            normativaDto.setTitulos("viewCuadroControl.normativas");
            normativaDto.setNumeroVisible("");
            normativaDto.setNumeroNoVisible("");
            normativaDto.setNumeroTotal(normativaServiceFacade.countAll().toString());
            listaDtos.add(normativaDto);
        }


    }


    public void abrirVentanaUA() {
        final Map<String, String> params = new HashMap<>();

        params.put(TypeParametroVentana.MODO_ACCESO.toString(), TypeModoAcceso.EDICION.toString());
        String direccion = "/comun/dialogSeleccionarUA";

        UtilJSF.anyadirMochila("ua", uaSeleccionada);
        UtilJSF.openDialog(direccion, TypeModoAcceso.EDICION, params, true, 850, 575);
    }

    public void returnDialogoUA(final SelectEvent event) {
        final DialogResult respuesta = (DialogResult) event.getObject();

        // Verificamos si se ha modificado
        if (respuesta != null && !respuesta.isCanceled() && !TypeModoAcceso.CONSULTA.equals(respuesta.getModoAcceso())) {
            UnidadAdministrativaDTO ua = (UnidadAdministrativaDTO) respuesta.getResult();
            if (ua != null) {
                uaSeleccionada = ua;
                cargarFiltroServicios();
                cargarFiltroProcedimientos();
                cargarFiltroUas();
                buscarNumeroContenidos();
                crearAltasBarModel();
                crearBajasBarModel();
                crearModificacionesBarModel();
                crearGraficaEstadisticas();
            }
        }
    }

    public void consultarProcedimiento(Long id) {
        Map<String, String> params = new HashMap<>();
        params.put("ID", id.toString());
        UtilJSF.openDialog("/maestras/dialogProcedimiento", TypeModoAcceso.CONSULTA, params, true, 1010, 733);
    }

    public void consultarServicio(Long id) {
        Map<String, String> params = new HashMap<>();
        params.put("ID", id.toString());
        UtilJSF.openDialog("/maestras/dialogServicio", TypeModoAcceso.CONSULTA, params, true, 1010, 733);
    }

    public void crearAltasBarModel() {
        altasChartModel = new BarChartModel();
        ChartData data = new ChartData();

        //Añadimos los datos de para procedimientos
        BarChartDataSet dataSetProcedimientos = new BarChartDataSet();
        dataSetProcedimientos.setBackgroundColor("rgba(255, 99, 132, 0.2)");
        dataSetProcedimientos.setBorderColor("rgb(255, 99, 132)");
        dataSetProcedimientos.setBorderWidth(1);
        dataSetProcedimientos.setLabel(getLiteral("viewCuadroControl.procedimientos"));

        filtroProcedimientos.setAccion(TypeAccionAuditoria.ALTA.toString());
        EstadisticaCMDTO statsProcedimientos = cuadroMandoServiceFacade.countByFiltro(filtroProcedimientos);
        List<Number> valoresProcs = new ArrayList<>();
        valoresProcs.addAll(statsProcedimientos.getValores());

        dataSetProcedimientos.setData(valoresProcs);
        data.addChartDataSet(dataSetProcedimientos);

        //Añadimos los datos de altas para servicios
        BarChartDataSet dataSetServicios = new BarChartDataSet();
        dataSetServicios.setBackgroundColor("rgba(54, 162, 235, 0.2)");
        dataSetServicios.setBorderColor("rgb(54, 162, 235)");
        dataSetServicios.setBorderWidth(1);
        dataSetServicios.setLabel(getLiteral("viewCuadroControl.servicios"));

        filtroServicios.setAccion(TypeAccionAuditoria.ALTA.toString());
        EstadisticaCMDTO statsServicios = cuadroMandoServiceFacade.countByFiltro(filtroServicios);
        List<Number> valoresServicios = new ArrayList<>();
        valoresServicios.addAll(statsServicios.getValores());

        dataSetServicios.setData(valoresServicios);
        data.addChartDataSet(dataSetServicios);

        List<String> labels = new ArrayList<>();
        data.setLabels(statsProcedimientos.getDiasSemana());

        altasChartModel.setData(data);

        //Options
        BarChartOptions options = new BarChartOptions();
        CartesianScales cScales = new CartesianScales();
        CartesianLinearAxes linearAxes = new CartesianLinearAxes();
        CartesianLinearTicks ticks = new CartesianLinearTicks();
        Long valorMax = Math.max(Collections.max(statsProcedimientos.getValores()), Collections.max(statsServicios.getValores()));
        ticks.setMax(valorMax);
        ticks.setBeginAtZero(true);
        linearAxes.setTicks(ticks);
        cScales.addYAxesData(linearAxes);
        options.setScales(cScales);
        Title title = new Title();
        title.setText(getLiteral("viewCuadroControl.tituloAltas"));
        title.setDisplay(true);
        options.setTitle(title);

        Legend legend = new Legend();
        legend.setDisplay(true);
        legend.setPosition("top");
        LegendLabel legendLabels = new LegendLabel();
        legendLabels.setFontSize(12);
        legend.setLabels(legendLabels);
        options.setLegend(legend);

        altasChartModel.setOptions(options);
    }

    public void crearBajasBarModel() {
        bajasChartModel = new BarChartModel();
        ChartData data = new ChartData();

        //Añadimos los datos de para procedimientos
        BarChartDataSet dataSetProcedimientos = new BarChartDataSet();
        dataSetProcedimientos.setBackgroundColor("rgba(255, 99, 132, 0.2)");
        dataSetProcedimientos.setBorderColor("rgb(255, 99, 132)");
        dataSetProcedimientos.setBorderWidth(1);
        dataSetProcedimientos.setLabel(getLiteral("viewCuadroControl.procedimientos"));

        filtroProcedimientos.setAccion(TypeAccionAuditoria.BAJA.toString());
        EstadisticaCMDTO statsProcedimientos = cuadroMandoServiceFacade.countByFiltro(filtroProcedimientos);
        List<Number> valoresProcs = new ArrayList<>();
        valoresProcs.addAll(statsProcedimientos.getValores());

        dataSetProcedimientos.setData(valoresProcs);
        data.addChartDataSet(dataSetProcedimientos);

        //Añadimos los datos de altas para servicios
        BarChartDataSet dataSetServicios = new BarChartDataSet();
        dataSetServicios.setBackgroundColor("rgba(54, 162, 235, 0.2)");
        dataSetServicios.setBorderColor("rgb(54, 162, 235)");
        dataSetServicios.setBorderWidth(1);
        dataSetServicios.setLabel(getLiteral("viewCuadroControl.servicios"));

        filtroServicios.setAccion(TypeAccionAuditoria.BAJA.toString());
        EstadisticaCMDTO statsServicios = cuadroMandoServiceFacade.countByFiltro(filtroServicios);
        List<Number> valoresServicios = new ArrayList<>();
        valoresServicios.addAll(statsServicios.getValores());

        dataSetServicios.setData(valoresServicios);
        data.addChartDataSet(dataSetServicios);

        List<String> labels = new ArrayList<>();
        data.setLabels(statsProcedimientos.getDiasSemana());

        bajasChartModel.setData(data);

        //Options
        BarChartOptions options = new BarChartOptions();
        CartesianScales cScales = new CartesianScales();
        CartesianLinearAxes linearAxes = new CartesianLinearAxes();
        CartesianLinearTicks ticks = new CartesianLinearTicks();
        Long valorMax = Math.max(Collections.max(statsProcedimientos.getValores()), Collections.max(statsServicios.getValores()));
        ticks.setMax(valorMax);
        ticks.setBeginAtZero(true);
        linearAxes.setTicks(ticks);
        cScales.addYAxesData(linearAxes);

        options.setScales(cScales);

        Title title = new Title();
        title.setText(getLiteral("viewCuadroControl.tituloAltas"));
        title.setDisplay(true);
        options.setTitle(title);

        Legend legend = new Legend();
        legend.setDisplay(true);
        legend.setPosition("top");
        LegendLabel legendLabels = new LegendLabel();
        legendLabels.setFontSize(12);
        legend.setLabels(legendLabels);
        options.setLegend(legend);

        bajasChartModel.setOptions(options);
    }

    public void crearModificacionesBarModel() {
        modificacionesChartModel = new BarChartModel();
        ChartData data = new ChartData();

        //Añadimos los datos de para procedimientos
        BarChartDataSet dataSetProcedimientos = new BarChartDataSet();
        dataSetProcedimientos.setBackgroundColor("rgba(255, 99, 132, 0.2)");
        dataSetProcedimientos.setBorderColor("rgb(255, 99, 132)");
        dataSetProcedimientos.setBorderWidth(1);
        dataSetProcedimientos.setLabel(getLiteral("viewCuadroControl.procedimientos"));

        filtroProcedimientos.setAccion(TypeAccionAuditoria.MODIFICACION.toString());
        EstadisticaCMDTO statsProcedimientos = cuadroMandoServiceFacade.countByFiltro(filtroProcedimientos);
        List<Number> valoresProcs = new ArrayList<>();
        valoresProcs.addAll(statsProcedimientos.getValores());

        dataSetProcedimientos.setData(valoresProcs);
        data.addChartDataSet(dataSetProcedimientos);

        //Añadimos los datos de altas para servicios
        BarChartDataSet dataSetServicios = new BarChartDataSet();
        dataSetServicios.setBackgroundColor("rgba(54, 162, 235, 0.2)");
        dataSetServicios.setBorderColor("rgb(54, 162, 235)");
        dataSetServicios.setBorderWidth(1);
        dataSetServicios.setLabel(getLiteral("viewCuadroControl.servicios"));

        filtroServicios.setAccion(TypeAccionAuditoria.MODIFICACION.toString());
        EstadisticaCMDTO statsServicios = cuadroMandoServiceFacade.countByFiltro(filtroServicios);
        List<Number> valoresServicios = new ArrayList<>();
        valoresServicios.addAll(statsServicios.getValores());

        dataSetServicios.setData(valoresServicios);
        data.addChartDataSet(dataSetServicios);

        List<String> labels = new ArrayList<>();
        data.setLabels(statsProcedimientos.getDiasSemana());

        modificacionesChartModel.setData(data);

        //Options
        BarChartOptions options = new BarChartOptions();
        CartesianScales cScales = new CartesianScales();
        CartesianLinearAxes linearAxes = new CartesianLinearAxes();
        CartesianLinearTicks ticks = new CartesianLinearTicks();
        Long valorMax = Math.max(Collections.max(statsProcedimientos.getValores()), Collections.max(statsServicios.getValores()));
        ticks.setMax(valorMax);
        ticks.setBeginAtZero(true);
        linearAxes.setTicks(ticks);
        cScales.addYAxesData(linearAxes);

        options.setScales(cScales);

        Title title = new Title();
        title.setText(getLiteral("viewCuadroControl.tituloAltas"));
        title.setDisplay(true);
        options.setTitle(title);

        Legend legend = new Legend();
        legend.setDisplay(true);
        legend.setPosition("top");
        LegendLabel legendLabels = new LegendLabel();
        legendLabels.setFontSize(12);
        legend.setLabels(legendLabels);
        options.setLegend(legend);

        modificacionesChartModel.setOptions(options);
    }

    public void crearGraficaEstadisticas() {
        //Primero obtenemos las pestañas que van a crearse
        aplicaciones = new ArrayList<>();
        aplicaciones.add("General");
        aplicaciones.addAll(cuadroMandoServiceFacade.obtenerAplicacionesEstadistica());

        graficosEstadisticas = new ArrayList<>();
        for (String app : aplicaciones) {
            if (!app.equals("General")) {
                filtroProcedimientos.setIdApp(app);
                filtroUas.setIdApp(app);
                filtroServicios.setIdApp(app);
            } else {
                filtroProcedimientos.setIdApp(null);
                filtroUas.setIdApp(null);
                filtroServicios.setIdApp(null);
            }
            BarChartModel modelo = new BarChartModel();
            ChartData data = new ChartData();

            //Añadimos los datos para procedimientos
            EstadisticaCMDTO estadisticaProcedimientos = cuadroMandoServiceFacade.obtenerEstadisiticasMensuales(filtroProcedimientos);
            BarChartDataSet dataSetProcedimientos = new BarChartDataSet();
            dataSetProcedimientos.setBackgroundColor("rgba(255, 99, 132, 0.2)");
            dataSetProcedimientos.setBorderColor("rgb(255, 99, 132)");
            dataSetProcedimientos.setBorderWidth(1);
            dataSetProcedimientos.setLabel(getLiteral("viewCuadroControl.procedimientos"));

            List<Number> valoresProcs = new ArrayList<>();
            valoresProcs.addAll(estadisticaProcedimientos.getValores());

            dataSetProcedimientos.setData(valoresProcs);
            data.addChartDataSet(dataSetProcedimientos);

            //Añadimos los datos para servicios
            EstadisticaCMDTO estadisticaServicios = cuadroMandoServiceFacade.obtenerEstadisiticasMensuales(filtroServicios);
            BarChartDataSet dataSetServicios = new BarChartDataSet();
            dataSetServicios.setBackgroundColor("rgba(54, 162, 235, 0.2)");
            dataSetServicios.setBorderColor("rgb(54, 162, 235)");
            dataSetServicios.setBorderWidth(1);
            dataSetServicios.setLabel(getLiteral("viewCuadroControl.servicios"));

            List<Number> valoresServicios = new ArrayList<>();
            valoresServicios.addAll(estadisticaServicios.getValores());

            dataSetServicios.setData(valoresServicios);
            data.addChartDataSet(dataSetServicios);

            //Añadimos los datos para Uas
            EstadisticaCMDTO estadisticaUas = cuadroMandoServiceFacade.obtenerEstadisiticasMensuales(filtroUas);
            BarChartDataSet dataSetUas = new BarChartDataSet();
            dataSetUas.setBackgroundColor("rgba(75, 192, 192, 0.2)");
            dataSetUas.setBorderColor("rgb(75, 192, 192)");
            dataSetUas.setBorderWidth(1);
            dataSetUas.setLabel(getLiteral("viewCuadroControl.uas"));

            List<Number> valoresUa = new ArrayList<>();
            valoresUa.addAll(estadisticaUas.getValores());

            dataSetUas.setData(valoresUa);
            data.addChartDataSet(dataSetUas);

            List<String> labels = new ArrayList<>();
            data.setLabels(estadisticaUas.getMeses());

            modelo.setData(data);

            //Options
            BarChartOptions options = new BarChartOptions();
            CartesianScales cScales = new CartesianScales();
            CartesianLinearAxes linearAxes = new CartesianLinearAxes();
            CartesianLinearTicks ticks = new CartesianLinearTicks();
            Long valorMax = Math.max(Collections.max(estadisticaProcedimientos.getValores()), Collections.max(estadisticaServicios.getValores()));
            valorMax = Math.max(valorMax, Collections.max(estadisticaUas.getValores()));
            ticks.setMax(valorMax);
            ticks.setBeginAtZero(true);
            linearAxes.setTicks(ticks);
            cScales.addYAxesData(linearAxes);
            options.setScales(cScales);
            Title title = new Title();
            title.setText(getLiteral("viewCuadroControl." + app.toLowerCase()));
            title.setDisplay(true);
            options.setTitle(title);

            Legend legend = new Legend();
            legend.setDisplay(true);
            legend.setPosition("top");
            LegendLabel legendLabels = new LegendLabel();
            legendLabels.setFontSize(12);
            legend.setLabels(legendLabels);
            options.setLegend(legend);

            modelo.setOptions(options);
            graficosEstadisticas.add(modelo);
        }



    }

    private void cargarFiltroProcedimientos() {
        filtroProcedimientos = new CuadroMandoFiltro();
        filtroProcedimientos.setTipo("P");
        filtroProcedimientos.setIdioma(sessionBean.getLang());
        if (buscarTodo) {
            filtroProcedimientos.setIdEntidad(sessionBean.getEntidad().getCodigo());
        } else {
            filtroProcedimientos.setIdUa(uaSeleccionada.getCodigo());
        }
    }

    private void cargarFiltroServicios() {
        filtroServicios = new CuadroMandoFiltro();
        filtroServicios.setTipo("S");
        filtroServicios.setIdioma(sessionBean.getLang());
        if (buscarTodo) {
            filtroServicios.setIdEntidad(sessionBean.getEntidad().getCodigo());
        } else {
            filtroServicios.setIdUa(uaSeleccionada.getCodigo());
        }
    }

    private void cargarFiltroUas() {
        filtroUas = new CuadroMandoFiltro();
        filtroUas.setTipo("UA");
        filtroUas.setIdioma(sessionBean.getLang());
        if (buscarTodo) {
            filtroUas.setIdEntidad(sessionBean.getEntidad().getCodigo());
        } else {
            filtroUas.setIdUa(uaSeleccionada.getCodigo());
        }
    }

    public UnidadAdministrativaDTO getUaSeleccionada() {
        return uaSeleccionada;
    }

    public void setUaSeleccionada(UnidadAdministrativaDTO uaSeleccionada) {
        this.uaSeleccionada = uaSeleccionada;
    }

    public List<CuadroControlDTO> getListaDtos() {
        return listaDtos;
    }

    public void setListaDtos(List<CuadroControlDTO> listaDtos) {
        this.listaDtos = listaDtos;
    }

    public CuadroControlDTO getProcedimientoDto() {
        return procedimientoDto;
    }

    public void setProcedimientoDto(CuadroControlDTO procedimientoDto) {
        this.procedimientoDto = procedimientoDto;
    }

    public CuadroControlDTO getServiciosDto() {
        return serviciosDto;
    }

    public void setServiciosDto(CuadroControlDTO serviciosDto) {
        this.serviciosDto = serviciosDto;
    }

    public CuadroControlDTO getNormativaDto() {
        return normativaDto;
    }

    public void setNormativaDto(CuadroControlDTO normativaDto) {
        this.normativaDto = normativaDto;
    }

    public boolean isBuscarTodo() {
        return buscarTodo;
    }

    public void setBuscarTodo(boolean buscarTodo) {
        this.buscarTodo = buscarTodo;
    }

    public List<AuditoriaCMGridDTO> getProcedimientosModificados() {
        return procedimientosModificados;
    }

    public void setProcedimientosModificados(List<AuditoriaCMGridDTO> procedimientosModificados) {
        this.procedimientosModificados = procedimientosModificados;
    }

    public List<AuditoriaCMGridDTO> getServiciosModificados() {
        return serviciosModificados;
    }

    public void setServiciosModificados(List<AuditoriaCMGridDTO> serviciosModificados) {
        this.serviciosModificados = serviciosModificados;
    }

    public AuditoriaCMGridDTO getProcSeleccionado() {
        return procSeleccionado;
    }

    public void setProcSeleccionado(AuditoriaCMGridDTO procSeleccionado) {
        this.procSeleccionado = procSeleccionado;
    }

    public AuditoriaCMGridDTO getServSeleccionado() {
        return servSeleccionado;
    }

    public void setServSeleccionado(AuditoriaCMGridDTO servSeleccionado) {
        this.servSeleccionado = servSeleccionado;
    }

    public BarChartModel getAltasChartModel() {
        return altasChartModel;
    }

    public void setAltasChartModel(BarChartModel altasChartModel) {
        this.altasChartModel = altasChartModel;
    }

    public BarChartModel getBajasChartModel() {
        return bajasChartModel;
    }

    public void setBajasChartModel(BarChartModel bajasChartModel) {
        this.bajasChartModel = bajasChartModel;
    }

    public BarChartModel getModificacionesChartModel() {
        return modificacionesChartModel;
    }

    public void setModificacionesChartModel(BarChartModel modificacionesChartModel) {
        this.modificacionesChartModel = modificacionesChartModel;
    }

    public List<String> getAplicaciones() {
        return aplicaciones;
    }

    public void setAplicaciones(List<String> aplicaciones) {
        this.aplicaciones = aplicaciones;
    }

    public List<BarChartModel> getGraficosEstadisticas() {
        return graficosEstadisticas;
    }

    public void setGraficosEstadisticas(List<BarChartModel> graficosEstadisticas) {
        this.graficosEstadisticas = graficosEstadisticas;
    }
}



