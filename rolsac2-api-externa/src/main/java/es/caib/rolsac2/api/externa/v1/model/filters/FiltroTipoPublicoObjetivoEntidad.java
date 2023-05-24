package es.caib.rolsac2.api.externa.v1.model.filters;

import javax.xml.bind.annotation.XmlRootElement;

import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.caib.rolsac2.api.externa.v1.model.EntidadJson;
import es.caib.rolsac2.api.externa.v1.utils.Constantes;
import es.caib.rolsac2.service.model.filtro.TipoPublicoObjetivoEntidadFiltro;

@XmlRootElement
@Schema(name = "FiltroTipoPublicoObjetivoEntidad", type = SchemaType.STRING, description = "Filtro que permite buscar por diferentes campos")
public class FiltroTipoPublicoObjetivoEntidad extends EntidadJson<FiltroTipoPublicoObjetivoEntidad> {

	 private static final Logger LOG = LoggerFactory.getLogger(FiltroTipoPublicoObjetivoEntidad.class);

		public static final String SAMPLE = Constantes.SALTO_LINEA +
				"{"	+
				"\"texto\":\"string\"," + Constantes.SALTO_LINEA +
				"\"identificador\":\"string\"," + Constantes.SALTO_LINEA +
				"\"codigoPublicoObjetivoSia\":0," + Constantes.SALTO_LINEA +
				"\"idEntidad\":0," + Constantes.SALTO_LINEA +
				"\"traducciones\":\"string\"," + Constantes.SALTO_LINEA +
				"\"filtroPaginacion\":{\"page\":0,\"size\":10}" +
				"}";

		public static final String SAMPLE_JSON =
				"{" +
				"\"texto\":null," 						+
				"\"identificador\":null," 						+
				"\"codigoPublicoObjetivoSia\":null," 						+
				"\"idEntidad\":null," 						+
				"\"traducciones\":null," 						+
				"\"filtroPaginacion\":{\"page\":\"0\",\"size\":\"10\"}" +
				"}";

		/** traducciones. **/
		@Schema(name = "traducciones", description = "traducciones", type = SchemaType.STRING, required = false)
	    private String traducciones;

		/** texto. **/
		@Schema(name = "texto", description = "texto", type = SchemaType.STRING, required = false)
		private String texto;

		/** identificador. **/
		@Schema(name = "identificador", description = "identificador", type = SchemaType.STRING, required = false)
		private String identificador;

		/** codigoTipo. **/
		@Schema(name = "codigoPublicoObjetivoSia", description = "codigoPublicoObjetivoSia", type = SchemaType.INTEGER, required = false)
		private Long codigoPublicoObjetivoSia;

		/** idEntidad. **/
		@Schema(name = "idEntidad", description = "idEntidad", type = SchemaType.INTEGER, required = false)
		private Long idEntidad;

		/** FiltroPaginacion. **/
		@Schema(name = "filtroPaginacion", description = "filtroPaginacion", required = false)
		private FiltroPaginacion filtroPaginacion;

		/**
		 * @return the texto
		 */
		public String getTexto() {
			return texto;
		}

		/**
		 * @param texto the texto to set
		 */
		public void setTexto(String texto) {
			this.texto = texto;
		}

		public TipoPublicoObjetivoEntidadFiltro toTipoPublicoObjetivoEntidadFiltro() {
			TipoPublicoObjetivoEntidadFiltro resultado = new TipoPublicoObjetivoEntidadFiltro();

			if (this.texto != null && !this.texto.isEmpty()) {
				resultado.setTexto(texto);
			}

			if (this.identificador != null && !this.identificador.isEmpty()) {
				resultado.setIdentificador(identificador);
			}

			if (this.codigoPublicoObjetivoSia != null) {
				resultado.setCodigoTipo(codigoPublicoObjetivoSia);
			}

			if (this.traducciones != null && !this.traducciones.isEmpty()) {
				resultado.setTraducciones(traducciones);
			}

			if (this.idEntidad != null) {
				resultado.setIdEntidad(idEntidad);
			}

			return resultado;

		}

		public FiltroPaginacion getFiltroPaginacion() {
			return filtroPaginacion;
		}

		public void setFiltroPaginacion(FiltroPaginacion filtroPaginacion) {
			this.filtroPaginacion = filtroPaginacion;
		}

		public String getIdentificador() {
			return identificador;
		}

		public void setIdentificador(String identificador) {
			this.identificador = identificador;
		}

		public void setCodigoPublicoObjetivoSia(Long codigoTipo) {
			this.codigoPublicoObjetivoSia = codigoTipo;
		}

		public String getTraducciones() {
			return traducciones;
		}

		public void setTraducciones(String traducciones) {
			this.traducciones = traducciones;
		}

		public Long getCodigoPublicoObjetivoSia() {
			return codigoPublicoObjetivoSia;
		}

		public void setIdEntidad(Long idEntidad) {
			this.idEntidad = idEntidad;
		}

		public Long getIdEntidad() {
			return idEntidad;
		}
}
