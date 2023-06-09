package es.caib.rolsac2.persistence.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import es.caib.rolsac2.persistence.converter.ProcedimientoTramiteConverter;
import es.caib.rolsac2.persistence.converter.TipoTramitacionConverter;
import es.caib.rolsac2.persistence.model.JProcedimientoTramite;
import es.caib.rolsac2.service.model.ProcedimientoTramiteDTO;
import es.caib.rolsac2.service.model.TipoTramitacionDTO;
import es.caib.rolsac2.service.model.filtro.ProcedimientoTramiteFiltro;

/**
 * Implementación del repositorio de Personal.
 *
 * @author areus
 */
@Stateless
@Local(ProcedimientoRepository.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class ProcedimientoTramiteRepositoryBean extends AbstractCrudRepository<JProcedimientoTramite, Long>
		implements ProcedimientoTramiteRepository {

	@Inject
	private ProcedimientoTramiteConverter converter;

    @Inject
    TipoTramitacionConverter tipoTramitacionConverter;

	protected ProcedimientoTramiteRepositoryBean() {
		super(JProcedimientoTramite.class);
	}

	@Override
	public Optional<JProcedimientoTramite> findById(String id) {
		TypedQuery<JProcedimientoTramite> query = entityManager.createNamedQuery(JProcedimientoTramite.FIND_BY_ID,
				JProcedimientoTramite.class);
		query.setParameter("id", id);
		List<JProcedimientoTramite> result = query.getResultList();
		return Optional.ofNullable(result.isEmpty() ? null : result.get(0));
	}

	@Override
	public List<JProcedimientoTramite> findByProcedimientoId(Long id) {
		TypedQuery<JProcedimientoTramite> query = entityManager.createNamedQuery(JProcedimientoTramite.FIND_BY_PROC_ID,
				JProcedimientoTramite.class);
		query.setParameter("id", id);
		return query.getResultList();
	}

	@Override
	public List<ProcedimientoTramiteDTO> findPagedByFiltroRest(ProcedimientoTramiteFiltro filtro) {
		Query query = getQuery(false, filtro, true);
		query.setFirstResult(filtro.getPaginaFirst());
		query.setMaxResults(filtro.getPaginaTamanyo());

		List<JProcedimientoTramite> jentidades = query.getResultList();
		List<ProcedimientoTramiteDTO> entidades = new ArrayList<>();
		if (jentidades != null) {
			for (JProcedimientoTramite jtramite : jentidades) {
                ProcedimientoTramiteDTO tramite = converter.createDTO(jtramite);
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
//                if (jtramite.getListaDocumentos() != null) {
//                    tramite.setListaDocumentos(this.getDocumentosByListaDocumentos(jtramite.getListaDocumentos()));
//                }
//                if (jtramite.getListaModelos() != null) {
//                    tramite.setListaModelos(this.getDocumentosByListaDocumentos(jtramite.getListaModelos()));
//                }
                entidades.add(tramite);
            }
		}
		return entidades;
	}

	@Override
	public long countByFiltro(ProcedimientoTramiteFiltro filtro) {
		return (long) getQuery(true, filtro, false).getSingleResult();
	}

	private Query getQuery(boolean isTotal, ProcedimientoTramiteFiltro filtro, boolean isRest) {
		StringBuilder sql;
		if (isTotal) {
			sql = new StringBuilder(
					"select count(j) from JProcedimientoTramite j LEFT OUTER JOIN j.procedimiento p ON p.workflow = true or p.workflow = false LEFT OUTER JOIN j.traducciones t ON t.idioma=:idioma where 1 = 1 ");
		} else if (isRest) {
			if (filtro.isRellenoEstadoWF()) {
				if (filtro.getEstadoWF().equals("D")) {
					sql = new StringBuilder(
							"SELECT j from JProcedimientoTramite j INNER JOIN j.procedimiento p ON p.workflow = false LEFT OUTER JOIN j.traducciones t ON t.idioma=:idioma where 1 = 1 ");
				} else if (filtro.getEstadoWF().equals("M")) {
					sql = new StringBuilder(
							"SELECT j from JProcedimientoTramite j INNER JOIN j.procedimiento p ON p.workflow = true LEFT OUTER JOIN j.traducciones t ON t.idioma=:idioma where 1 = 1 ");
				 } else if (filtro.getEstadoWF().equals("T")) {
					 sql = new StringBuilder(
								"SELECT j from JProcedimientoTramite j LEFT JOIN j.procedimiento p ON p.workflow = true or p.workflow is null LEFT JOIN j.procedimiento p2 ON p2.workflow = false or p2.workflow is null LEFT OUTER JOIN j.traducciones t ON t.idioma=:idioma where 1 = 1 ");
				 } else {
					 sql = new StringBuilder(
								"SELECT j from JProcedimientoTramite j LEFT OUTER JOIN j.procedimiento p ON p.workflow = true or p.workflow = false LEFT OUTER JOIN j.traducciones t ON t.idioma=:idioma where 1 = 1 ");

				 }
			} else {
				sql = new StringBuilder(
						"SELECT j from JProcedimientoTramite j LEFT OUTER JOIN j.procedimiento p ON p.workflow = true or p.workflow = false LEFT OUTER JOIN j.traducciones t ON t.idioma=:idioma where 1 = 1 ");
			}
		} else {
			sql = new StringBuilder(
					"SELECT j from JProcedimientoTramite j LEFT OUTER JOIN j.procedimiento p ON p.workflow = true or p.workflow = false LEFT OUTER JOIN j.traducciones t ON t.idioma=:idioma where 1 = 1 ");
		}

		if (filtro.isRellenoTexto()) {
			sql.append(" and (LOWER(t.requisitos) LIKE :filtro " + "OR LOWER(t.nombre) LIKE :filtro "
					+ "OR LOWER(t.documentacion) LIKE :filtro " + "OR LOWER(t.observacion) LIKE :filtro "
					+ "OR LOWER(t.terminoMaximo) LIKE :filtro ");
		}

		if (filtro.isRellenoCodigo()) {
			sql.append(" and j.codigo = :codigo ");
		}

		if (filtro.isRellenoOrden()) {
			sql.append(" and j.orden = :orden ");
		}

		if (filtro.isRellenoFase()) {
			sql.append(" and j.fase = :fase ");
		}

		if (filtro.isRellenoUnidadAdministrativa()) {
			sql.append(" and j.unidadAdministrativa.codigo = :unidadAdministrativa ");
		}

		if (filtro.isRellenoProcedimiento()) {
			sql.append(" and p.procedimiento.codigo = :procedimiento ");
		}

		if (filtro.isRellenoTipoTramitacion()) {
			sql.append(" and j.tipoTramitacion.codigo = :tipoTramitacion ");
		}

		if (filtro.isRellenoFechaPublicacion()) {
			sql.append(" and j.fechaPublicacion = :fechaPublicacion ");
		}

		if (filtro.isRellenoFechaInicio()) {
			sql.append(" and j.fechaInicio = :fechaInicio ");
		}

		if (filtro.isRellenoFechaCierre()) {
			sql.append(" and j.fechaCierre = :fechaCierre ");
		}

		if (filtro.getOrderBy() != null) {
			sql.append(" order by ").append(getOrden(filtro.getOrderBy()));
			sql.append(filtro.isAscendente() ? " asc " : " desc ");
		}

		Query query = entityManager.createQuery(sql.toString());

		if (filtro.isRellenoTexto()) {
			query.setParameter("filtro", "%" + filtro.getTexto().toLowerCase() + "%");
		}
		if (filtro.isRellenoIdioma()) {
			query.setParameter("idioma", filtro.getIdioma());
		}

		if (filtro.isRellenoCodigo()) {
			query.setParameter("codigo", filtro.getCodigo());
		}

		if (filtro.isRellenoOrden()) {
			query.setParameter("orden", filtro.getOrden());
		}

		if (filtro.isRellenoFase()) {
			query.setParameter("fase", filtro.getFase());
		}

		if (filtro.isRellenoUnidadAdministrativa()) {
			query.setParameter("unidadAdministrativa", filtro.getUnidadAdministrativa().getCodigo());
		}

		if (filtro.isRellenoProcedimiento()) {
			query.setParameter("procedimiento", filtro.getProcedimiento().getCodigo());
		}

		if (filtro.isRellenoTipoTramitacion()) {
			query.setParameter("tipoTramitacion", filtro.getTipoTramitacion().getCodigo());
		}

		if (filtro.isRellenoFechaPublicacion()) {
			query.setParameter("fechaPublicacion", filtro.getFechaPublicacion());
		}

		if (filtro.isRellenoFechaInicio()) {
			query.setParameter("fechaInicio", filtro.getFechaInicio());
		}

		if (filtro.isRellenoFechaCierre()) {
			query.setParameter("fechaCierre", filtro.getFechaCierre());
		}

		if (filtro.getOrderBy() != null) {
			sql.append(" order by ").append(getOrden(filtro.getOrderBy()));
			sql.append(filtro.isAscendente() ? " asc " : " desc ");
		}

		return query;
	}

	private String getOrden(String order) {
		// Se puede hacer un switch/if pero en este caso, con j.+order sobra
		return "j." + order;
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
    public String getEnlaceTelematico(ProcedimientoTramiteFiltro filtro) {
        List<ProcedimientoTramiteDTO> lista = findPagedByFiltroRest(filtro);
        String res = "";
        if (lista != null && !lista.isEmpty()) {
        	res = montarUrl(lista.get(0), filtro.getIdioma());
        }

        return res;
    }

	private String montarUrl(ProcedimientoTramiteDTO serv, String lang) {
		TipoTramitacionDTO servTr = serv.getTipoTramitacion();

		String res = "";

		if (servTr != null) {

			try {
				final String idTramite = servTr.getTramiteId();
				final String numVersion = servTr.getTramiteVersion() == null ? "" : servTr.getTramiteVersion().toString();
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
		}

		return res;
	}
}
