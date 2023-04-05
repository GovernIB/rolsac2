package es.caib.rolsac2.commons.plugins.boletin.eboib;

import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.util.FileManager;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import es.caib.rolsac2.commons.plugins.boletin.api.BoletinErrorException;
import es.caib.rolsac2.commons.plugins.boletin.api.IPluginBoletin;
import es.caib.rolsac2.commons.plugins.boletin.api.model.*;
import org.fundaciobit.pluginsib.core.utils.AbstractPluginProperties;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.*;

public class EboibPlugin extends AbstractPluginProperties implements IPluginBoletin {

    public static final String EBOIB_URL_HACK = "eboibUrlHack";

    public static final String EBOIB_URL = "eboibUrl";

    public static final String TIPO_BOLETIN_PROPIEDAD = "tipoBoletin";

    private ParametrosEBoib parametrosEBoib;

    /**
     * Constructor del plugin de Eboib
     *
     * @param prefijoPropiedades
     * @param properties
     */
    public EboibPlugin(final String prefijoPropiedades, final Properties properties) {
        super(prefijoPropiedades, properties);
        parametrosEBoib = new ParametrosEBoib();
    }

    @Override
    public List<Edicto> listar(String numeroBoletin, String fechaBoletin, String numeroEdicto) throws BoletinErrorException {
        return this.makeSearch(numeroBoletin, fechaBoletin, numeroEdicto);
    }

    public Long obtenerBoletinPlugin() {
        return Long.valueOf(this.getProperty(TIPO_BOLETIN_PROPIEDAD));
    }


    /**********************************************************************************************
     * Funciones privadas
     ***********************************************************************************************/

    /**
     * Método utilizado para realizar una búsqueda RDF dados los siguientes parámetros.
     *
     * @param numeroBoletin
     * @param fechaBoletin
     * @param numeroEdicto
     */

    private List<Edicto> makeSearch(String numeroBoletin, String fechaBoletin, String numeroEdicto) throws BoletinErrorException {
        /*
         * 1.- buscar el BOIB por fecha o número en RSS - buscar por número:
         * /filtrerss.do?lang=ca&resultados=20&num_ini=1&num_fin=1&any_ini=2009&any_fin=
         * 2009 - buscar por fecha:
         * /filtrerss.do?lang=ca&resultados=20&fec_ini=01/01/2009&fec_fin=08/01/2009 2.-
         * dada la url RDF del BOIB, buscar los edictos. Si hay num reg en la búsqueda,
         * filtrar por él.
         */

        final List<ResultadoBoib> boibRdfUrls = this.getBoibRdfUrls(numeroBoletin, fechaBoletin);
        final String numRegBoib = numeroEdicto.isEmpty() ? "" : numeroEdicto;
        List<Edicto> listadoEdictos = new ArrayList<>();
        Normativa normativa = new Normativa();

        if (boibRdfUrls.size() < 1) {
            parametrosEBoib.setNumeroRegistros(-1);
            throw new BoletinErrorException("Els paràmetres de cerca tenen inconsistències.");
        }
        for (final ResultadoBoib rdf : boibRdfUrls) {
            populateResultadoBoib(rdf);
        }

        boolean abortar = false;
        for (final ResultadoBoib rdf : boibRdfUrls) {
            if (abortar) break;
            for (final String enviamentUrl : rdf.getEnviaments()) {
                if (abortar) break;
                try {
                    normativa = getEnviament(rdf, enviamentUrl);
                } catch (final Exception exception) {
                    // Error recuperando el enviamente a pesar de la url.
                    normativa = null;
                }

                if (normativa != null) {
                    if (numRegBoib.equals("")) {
                        // No estamos buscando por numeroBoib
                        Edicto edicto = crearEdicto(normativa);
                        listadoEdictos.add(edicto);
                    } else {
                        // Estamos buscando por numeroBoib
                        if (normativa.getValorRegistro().equals(numRegBoib)) {
                            //TODO: Meter lógica para comprobar si el número de boletín está en la normativa
                            // Registro encontrado - método que se utiliza para comprobar que ya está añadido en ROLSAC
                            /*final boolean estaInsertadoEnSac = isInsertSAC(Integer.parseInt(rdf.getNumBoib()),
                                    Integer.parseInt(numregboib));*/
                            /*if (estaInsertadoEnSac) {
                                numeroregistros = -1;
                                mensajeAviso.setCabecera("ERROR EN ELS PAR&Agrave;METRES");
                                mensajeAviso
                                        .setSubcabecera("El boib i el registre JA ESTAN introdu&iuml;ts en el SAC");
                            }*/
                            //normativa = normativa;
                            Edicto edicto = crearEdicto(normativa);
                            listadoEdictos.add(edicto);
                            abortar = true;
                        }
                    }
                }
            }
        }
        // Si todavía no está fijado, calculamos numeroregistros
        if (parametrosEBoib.getNumeroRegistros() == 0) {
            if (normativa == null) {
                parametrosEBoib.setNumeroRegistros(listadoEdictos.size());
            } else {
                parametrosEBoib.setNumeroRegistros(1);
            }
        }

        return listadoEdictos;

    }

    /**
     * Método que crea un objeto de Normativa ja partir de un RDF y de un nombre de fichero.
     *
     * @param rdf
     * @param inputFileName
     * @return
     */
    private Normativa getEnviament(final ResultadoBoib rdf, final String inputFileName) {

        final Normativa normativa = new Normativa();
        final Model m = loadRdf(inputFileName);

        // CATALA
        final Resource res = m.getResource(inputFileName);

        normativa.setNumeroBoib(rdf.getNumBoib());
        normativa.setIdBoletin("1");
        normativa.setNombreBoletin("BOIB");
        normativa.setValorRegistro("" + res.getProperty(RdfProperties.NUM_REGISTRE).getString());
        normativa.setIdTipoNormativa(extraerIdTipoNormativa(res.getProperty(RdfProperties.TIPUS_PUBLICACIO).getResource().getURI()));

        final java.text.SimpleDateFormat anyosMedia = new java.text.SimpleDateFormat("dd/MM/yyyy");
        try {
            normativa.setFechaBoletin(anyosMedia.parse(res.getProperty(RdfProperties.DATE).getString()));
        } catch (final ParseException e) {
            throw new IllegalArgumentException("Data: " + res.getProperty(RdfProperties.DATE).getString() + " incorrecta");
        }

        // CATALÁN
        normativa.getTitulos().put("ca", limpiaSumario(res.getProperty(RdfProperties.SUMARI).getString()));
        normativa.getApartados().put("ca", getSeccioCA(res.getProperty(RdfProperties.SECCIO).getResource().getURI()));
        normativa.getPaginaInicial().put("ca", res.getProperty(RdfProperties.NUM_PAG_INICIAL).getString());
        normativa.getPaginaFinal().put("ca", res.getProperty(RdfProperties.NUM_PAG_FINAL).getString());
        if (!rdf.isHistoric()) {
            normativa.getEnlaces().put("ca", res.getProperty(RdfProperties.HTML).getResource().getURI());
        }

        final String inputFileNameEs = inputFileName.replace("/ca/", "/es/");
        // CASTELLANO
        final Model mEs = loadRdf(inputFileNameEs);
        final ResIterator resIter = mEs.listResourcesWithProperty(RdfProperties.NUM_PAG_INICIAL);
        if (resIter.hasNext()) {
            final Resource resEs = resIter.nextResource();
            normativa.getTitulos().put("es", limpiaSumario(resEs.getProperty(RdfProperties.SUMARI).getString()));
            normativa.getApartados().put("es", resEs.getProperty(RdfProperties.SECCIO).getResource().getURI());
            normativa.getPaginaInicial().put("es", resEs.getProperty(RdfProperties.NUM_PAG_INICIAL).getString());
            normativa.getPaginaFinal().put("es", resEs.getProperty(RdfProperties.NUM_PAG_FINAL).getString());

            if (!rdf.isHistoric()) {
                normativa.getEnlaces().put("es", resEs.getProperty(RdfProperties.HTML).getResource().getURI());
            }
        }

        return normativa;

    }

    /**
     * A partir de un nombre de fichero creamos un Modelo RDF
     *
     * @param inputFileName
     * @return
     */
    @SuppressWarnings("unchecked")
    private Model loadRdf(final String inputFileName) {
        final Model m = ModelFactory.createDefaultModel();
        String url = inputFileName;
        if (isUrlHack()) {
            url = url.replace("intranet.caib.es", "www.caib.es");
        }

        final InputStream in = FileManager.get().open(url);
        if (in == null) {
            throw new IllegalArgumentException("Rdf: " + url + " no trobat");
        }
        // read the RDF/XML file
        m.read(in, "");
        return m;
    }

    /**
     * Buscar el BOIB por fecha o número en RSS<br/>
     * - buscar por número:
     * /filtrerss.do?lang=ca&resultados=20&num_ini=1&num_fin=1&any_ini=2009&any_fin=2009<br/>
     * - buscar por fecha:
     * /filtrerss.do?lang=ca&resultados=20&fec_ini=01/01/2009&fec_fin=08/01/2009<br/>
     *
     * @return
     */
    @SuppressWarnings("rawtypes")
    private List<ResultadoBoib> getBoibRdfUrls(String numeroBoletin, String fechaBoletin) {
        final List<ResultadoBoib> boibRdfUrls = new ArrayList<ResultadoBoib>();
        final StringBuilder feedUrl = new StringBuilder(getProperty(EBOIB_URL));
        feedUrl.append("/filtrerss.do?lang=ca&resultados=10");

        if (!numeroBoletin.isEmpty()) {

            String anyo, num;
            if (numeroBoletin.contains("/")) {
                anyo = numeroBoletin.split("/")[1];
                num = numeroBoletin.split("/")[0];
            } else {
                anyo = numeroBoletin.substring(0, 4);
                num = numeroBoletin.substring(4);
            }
            feedUrl.append("&any_ini=").append(anyo).append("&any_fin=").append(anyo);
            feedUrl.append("&num_ini=").append(num).append("&num_fin=").append(num);
        } else if (!fechaBoletin.isEmpty()) {
            feedUrl.append("&fec_ini=").append(fechaBoletin).append("&fec_fin=").append(fechaBoletin);
        }

        try {
            final SyndFeedInput input = new SyndFeedInput();
            final XmlReader reader = new XmlReader(new URL(feedUrl.toString()));
            final SyndFeed feed = input.build(reader);
            for (final Iterator i = feed.getEntries().iterator(); i.hasNext(); ) {
                final SyndEntry entry = (SyndEntry) i.next();
                final ResultadoBoib bResult = new ResultadoBoib();
                bResult.setUrl(entry.getLink());
                bResult.setRdfUrl(bResult.getUrl() + "/rdf");
                boibRdfUrls.add(bResult);
            }
        } catch (final IllegalArgumentException e) {
            e.printStackTrace();
        } catch (final MalformedURLException e) {
            e.printStackTrace();
        } catch (final FeedException e) {
            e.printStackTrace();
        } catch (final IOException e) {
            e.printStackTrace();
        }
        return boibRdfUrls;
    }

    /**
     * Método utilizado para añadir los parámetros necesarios al objeto ResultadoBoib
     *
     * @param rdf2
     */
    private void populateResultadoBoib(final ResultadoBoib rdf2) {
        rdf2.setEnviaments(new ArrayList<String>());
        final Model m = loadRdf(rdf2.getRdfUrl());

        // Obtenemos los datos de num butlletí
        final Resource but = m.getResource(rdf2.getUrl());
        rdf2.setNum(but.getProperty(RdfProperties.NUMERO).getString());
        final String dataPublicacio = but.getProperty(RdfProperties.DATA_PUBLICACIO).getString();
        rdf2.setAnyo(dataPublicacio.substring(0, 4));
        rdf2.setNumBoib(rdf2.getAnyo() + rdf2.getNum());
        final Statement historico = but.getProperty(RdfProperties.HISTORIC);
        if (historico != null && historico.getString().equals("S")) {
            rdf2.setHistoric(true);
        } else {
            rdf2.setHistoric(false);
        }

        final ResIterator iter = m.listResourcesWithProperty(RdfProperties.ACCES_RDF);
        while (iter.hasNext()) {
            final Resource rdf = iter.nextResource();
            // System.out.println(" " + rdf.getProperty(ACCES_RDF).getObject().toString() );
            // System.out.println(rdf.getProperty(ACCES_RDF).getResource().getURI());
            rdf2.getEnviaments().add(rdf.getProperty(RdfProperties.ACCES_RDF).getResource().getURI());
        }

    }

    /**
     * Se utiliza para limpiar el sumario del contenido del Edicto
     *
     * @param string
     * @return
     */

    private String limpiaSumario(final String string) {
        if (string.startsWith("![CDATA[")) {
            return string.substring(8, string.length() - 2);
        } else {
            return string;
        }
    }


    /**
     * Obtiene la id del tipo normativa.
     *
     * @param uri
     * @return
     */
    private String extraerIdTipoNormativa(final String uri) {
        String id = "";
        if (uri != null) {
            final String[] uriSplit = uri.split("#");
            if (uriSplit.length == 2) {
                id = uriSplit[1];
            }
        }
        return id;
    }


    /**
     * Obtiene el id de una sección en catalán
     *
     * @param rdfId
     * @return
     */
    private String getSeccioCA(final String rdfId) {
        if (parametrosEBoib.getSecciones().get("ca") == null) {
            parametrosEBoib.getSecciones().put("ca", loadRdf(getProperty(EBOIB_URL) + "ca/seccio"));
        }
        return getSeccio(parametrosEBoib.getSecciones().get("ca"), rdfId);
    }

    /**
     * Obtiene el id de una sección en español
     *
     * @param rdfId
     * @return
     */
    private String getSeccioES(final String rdfId) {
        if (parametrosEBoib.getSecciones().get("es") == null) {
            parametrosEBoib.getSecciones().put("es", loadRdf(getProperty(EBOIB_URL) + "es/seccio"));
        }
        return getSeccio(parametrosEBoib.getSecciones().get("es"), rdfId);
    }

    /**
     * Obtiene el id de una sección
     *
     * @param rdfId
     * @return
     */
    private String getSeccio(final Model seccions, final String rdfId) {

        final Resource seccio = seccions.getResource(rdfId);
        String nom = seccio.getProperty(RdfProperties.SECCIO_NOM).getString();
        final Statement idPare = seccio.getProperty(RdfProperties.SECCIO_ID_PARE);
        if (idPare != null) {
            nom = getSeccio(seccions, idPare.getResource().getURI()) + " | " + nom;
        }
        return nom;
    }

    /**
     * Obtiene el id de una publicación en catalán
     *
     * @param rdfId
     * @return
     */

    private String getTipoPublicacionCA(final String rdfId) {
        if (parametrosEBoib.getTipoPublicaciones().get("ca") == null) {
            parametrosEBoib.getTipoPublicaciones().put("ca", loadRdf(getProperty(EBOIB_URL) + "ca/tipus-publicacio"));
        }
        return getPublicacion(parametrosEBoib.getTipoPublicaciones().get("ca"), rdfId);
    }

    /**
     * Obtiene el id de una publicación en español
     *
     * @param rdfId
     * @return
     */

    private String getTipoPublicacionES(final String rdfId) {
        if (parametrosEBoib.getTipoPublicaciones().get("es") == null) {
            parametrosEBoib.getTipoPublicaciones().put("es", loadRdf(getProperty(EBOIB_URL) + "es/tipus-publicacio"));
        }
        return getPublicacion(parametrosEBoib.getTipoPublicaciones().get("es"), rdfId);
    }

    /**
     * Obtiene el id de una publicación en catalán
     *
     * @param rdfId
     * @return
     */
    private String getPublicacion(final Model publicacions, final String rdfId) {

        final Resource seccio = publicacions.getResource(rdfId);
        String nom;
        if (seccio == null || seccio.getProperty(RdfProperties.TIPUS_PUBLICACIO_NOM) == null) {
            nom = "";
        } else {
            nom = seccio.getProperty(RdfProperties.TIPUS_PUBLICACIO_NOM).getString();
            final Statement idPare = seccio.getProperty(RdfProperties.TIPUS_PUBLICACIO_ID_PARE);
            if (idPare != null) {
                nom = getPublicacion(publicacions, idPare.getResource().getURI()) + " | " + nom;
            }
        }
        return nom;

    }


    /**
     * Creamos objeto de tipo Edicto
     */
    private Edicto crearEdicto(Normativa normativa) {
        Edicto edicto = new Edicto();
        edicto.setBoletinFecha(normativa.getFechaBoletin());
        edicto.setBoletinTipo(normativa.getNombreBoletin());
        if (normativa.getNumeroBoib().length() == 7) {
            String numeroBoibFormateado = normativa.getNumeroBoib().substring(4, 7) + "/" + normativa.getNumeroBoib().substring(0, 4);
            edicto.setBoletinNumero(numeroBoibFormateado);
        } else {
            edicto.setBoletinNumero(normativa.getNumeroBoib());
        }
        edicto.setEdictoNumero(normativa.getValorRegistro());
        edicto.setEdictoTexto(normativa.getTitulos());
        edicto.setEdictoUrl(normativa.getEnlaces());
        return edicto;
    }

    /**
     * funciones para manejar informacion del SAC
     *
     * @param s_numeroboib
     * @param s_numeroregistro
     * @return
     */
    private boolean isInsertSAC(int s_numeroboib, int s_numeroregistro) {
        boolean retorno = false;

        Map<String, String> paramMap = new HashMap<>();
        Map<String, String> tradMap = new HashMap<String, String>();
        String TIPO_NORM = "local";

        // Parámetros generales
        paramMap.put("numero", "" + s_numeroboib);
        paramMap.put("registro", "" + s_numeroregistro);

        try {
            /*NormativaDelegate normativaDelegate = DelegateUtil.getNormativaDelegate();
            int numnormativas = normativaDelegate.buscarNormativas(paramMap, tradMap, TIPO_NORM).size();
            retorno = (numnormativas>=1);*/

        } catch (Exception e) {
            retorno = false;
        }
        return retorno;
    }

    public Integer getNumeroNormativas() {
        return parametrosEBoib.getNumeroRegistros();
    }

    private boolean isUrlHack() {
        if (getProperty(EBOIB_URL_HACK) != null) {
            return getProperty(EBOIB_URL_HACK).equals("true");
        } else {
            return false;
        }

    }

    public String getHost() {
        return getProperty(EBOIB_URL);
    }

}
