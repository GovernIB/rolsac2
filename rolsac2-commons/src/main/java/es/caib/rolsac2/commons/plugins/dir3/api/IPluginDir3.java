package es.caib.rolsac2.commons.plugins.dir3.api;

import es.caib.rolsac2.commons.plugins.dir3.api.model.ParametrosDir3;
import es.caib.rolsac2.commons.plugins.dir3.api.model.UnidadOrganica;

import java.util.List;

public interface IPluginDir3 {

    public List<UnidadOrganica> obtenerArbolUnidades(final ParametrosDir3 parametros) throws Dir3ErrorException;

    public List<UnidadOrganica> obtenerHistoricosFinales(final ParametrosDir3 parametros) throws Dir3ErrorException;
}
