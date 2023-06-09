package es.caib.rolsac2.api.externa.v1.model.filters;

import javax.xml.bind.annotation.XmlRootElement;

import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.caib.rolsac2.api.externa.v1.model.EntidadJson;
import es.caib.rolsac2.api.externa.v1.utils.Constantes;
import es.caib.rolsac2.service.model.filtro.TemaFiltro;

@XmlRootElement
@Schema(name = "FiltroTema", type = SchemaType.STRING, description = "Filtro que permite buscar por diferentes campos")
public class FiltroTema extends EntidadJson<FiltroTema> {

	 private static final Logger LOG = LoggerFactory.getLogger(FiltroTema.class);

		public static final String SAMPLE = Constantes.SALTO_LINEA +
				"{"	+
				"\"texto\":\"string\"," + Constantes.SALTO_LINEA +
				"\"identificador\":\"string\"," + Constantes.SALTO_LINEA +
				"\"idEntidad\":0," + Constantes.SALTO_LINEA +
				"\"filtroPaginacion\":{\"page\":0,\"size\":10}" +
				"}";

		public static final String SAMPLE_JSON =
				"{" +
				"\"texto\":null," 						+
				"\"identificador\":null," 						+
				"\"idEntidad\":null," 						+
				"\"filtroPaginacion\":{\"page\":\"0\",\"size\":\"10\"}" +
				"}";

		/** texto. **/
		@Schema(name = "texto", description = "texto", type = SchemaType.STRING, required = false)
		private String texto;

		/** identificador. **/
		@Schema(name = "identificador", description = "identificador", type = SchemaType.STRING, required = false)
		private String identificador;

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

		public TemaFiltro toTemaFiltro() {
			TemaFiltro resultado = new TemaFiltro();

			if (this.texto != null && !this.texto.isEmpty()) {
				resultado.setTexto(texto);
			}

			if (this.identificador != null && !this.identificador.isEmpty()) {
				resultado.setIdentificador(identificador);
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

		public Long getIdEntidad() {
			return idEntidad;
		}

		public void setIdEntidad(Long idEntidad) {
			this.idEntidad = idEntidad;
		}
}