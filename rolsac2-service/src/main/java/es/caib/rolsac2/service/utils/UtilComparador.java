package es.caib.rolsac2.service.utils;

import es.caib.rolsac2.service.model.*;
import es.caib.rolsac2.service.model.types.*;

import java.util.Date;

public class UtilComparador {

    public static int compareTo(Long dato, Long dato2) {

        if (dato == null && dato2 == null) {
            return 0;
        }
        if (dato == null && dato2 != null) {
            return -1;
        }
        if (dato != null && dato2 == null) {
            return 1;
        }
        return dato.compareTo(dato2);
    }

    public static int compareTo(Date dato, Date dato2) {
        if (dato == null && dato2 == null) {
            return 0;
        }
        if (dato == null && dato2 != null) {
            return -1;
        }
        if (dato != null && dato2 == null) {
            return 1;
        }
        return dato.compareTo(dato2);
    }

    public static int compareTo(Boolean dato, Boolean dato2) {
        if (dato == null && dato2 == null) {
            return 0;
        }
        if (dato == null && dato2 != null) {
            return -1;
        }
        if (dato != null && dato2 == null) {
            return 1;
        }
        return dato.compareTo(dato2);
    }

    public static int compareTo(String dato, String dato2) {
        if (dato == null && dato2 == null) {
            return 0;
        }
        if (dato == null && dato2 != null) {
            return -1;
        }
        if (dato != null && dato2 == null) {
            return 1;
        }
        return dato.compareTo(dato2);
    }

    public static int compareTo(Integer dato, Integer dato2) {
        if (dato == null && dato2 == null) {
            return 0;
        }
        if (dato == null && dato2 != null) {
            return -1;
        }
        if (dato != null && dato2 == null) {
            return 1;
        }
        return dato.compareTo(dato2);
    }

    public static int compareTo(PlatTramitElectronicaDTO dato, PlatTramitElectronicaDTO dato2) {
        if (dato == null && dato2 == null) {
            return 0;
        }
        if (dato == null && dato2 != null) {
            return -1;
        }
        if (dato != null && dato2 == null) {
            return 1;
        }
        return dato.compareTo(dato2);
    }

    public static int compareTo(TypeEstadoProceso dato, TypeEstadoProceso dato2) {
        if (dato == null && dato2 == null) {
            return 0;
        }
        if (dato == null && dato2 != null) {
            return -1;
        }
        if (dato != null && dato2 == null) {
            return 1;
        }
        return dato.compareTo(dato2);
    }

    public static int compareTo(TypeFicheroExterno dato, TypeFicheroExterno dato2) {
        if (dato == null && dato2 == null) {
            return 0;
        }
        if (dato == null && dato2 != null) {
            return -1;
        }
        if (dato != null && dato2 == null) {
            return 1;
        }
        return dato.compareTo(dato2);
    }

    public static int compareTo(TypeIdiomaFijo dato, TypeIdiomaFijo dato2) {
        if (dato == null && dato2 == null) {
            return 0;
        }
        if (dato == null && dato2 != null) {
            return -1;
        }
        if (dato != null && dato2 == null) {
            return 1;
        }
        return dato.compareTo(dato2);
    }

    public static int compareTo(TypeIdiomaOpcional dato, TypeIdiomaOpcional dato2) {
        if (dato == null && dato2 == null) {
            return 0;
        }
        if (dato == null && dato2 != null) {
            return -1;
        }
        if (dato != null && dato2 == null) {
            return 1;
        }
        return dato.compareTo(dato2);
    }

    public static int compareTo(TypeNivelGravedad dato, TypeNivelGravedad dato2) {
        if (dato == null && dato2 == null) {
            return 0;
        }
        if (dato == null && dato2 != null) {
            return -1;
        }
        if (dato != null && dato2 == null) {
            return 1;
        }
        return dato.compareTo(dato2);
    }

    public static int compareTo(TypePluginEntidad dato, TypePluginEntidad dato2) {
        if (dato == null && dato2 == null) {
            return 0;
        }
        if (dato == null && dato2 != null) {
            return -1;
        }
        if (dato != null && dato2 == null) {
            return 1;
        }
        return dato.compareTo(dato2);
    }

    public static int compareTo(TypeProcedimientoEstado dato, TypeProcedimientoEstado dato2) {
        if (dato == null && dato2 == null) {
            return 0;
        }
        if (dato == null && dato2 != null) {
            return -1;
        }
        if (dato != null && dato2 == null) {
            return 1;
        }
        return dato.compareTo(dato2);
    }

    public static int compareTo(TypePropiedadConfiguracion dato, TypePropiedadConfiguracion dato2) {
        if (dato == null && dato2 == null) {
            return 0;
        }
        if (dato == null && dato2 != null) {
            return -1;
        }
        if (dato != null && dato2 == null) {
            return 1;
        }
        return dato.compareTo(dato2);
    }

    public static int compareTo(TypeTipoProceso dato, TypeTipoProceso dato2) {
        if (dato == null && dato2 == null) {
            return 0;
        }
        if (dato == null && dato2 != null) {
            return -1;
        }
        if (dato != null && dato2 == null) {
            return 1;
        }
        return dato.compareTo(dato2);
    }


    public static int compareTo(TypeProcedimientoWorkflow dato, TypeProcedimientoWorkflow dato2) {
        if (dato == null && dato2 == null) {
            return 0;
        }
        if (dato == null && dato2 != null) {
            return -1;
        }
        if (dato != null && dato2 == null) {
            return 1;
        }
        return dato.compareTo(dato2);
    }


    public static int compareTo(TipoLegitimacionDTO dato, TipoLegitimacionDTO dato2) {
        if (dato == null && dato2 == null) {
            return 0;
        }
        if (dato == null && dato2 != null) {
            return -1;
        }
        if (dato != null && dato2 == null) {
            return 1;
        }
        return dato.compareTo(dato2);
    }

    public static int compareTo(TipoFormaInicioDTO dato, TipoFormaInicioDTO dato2) {
        if (dato == null && dato2 == null) {
            return 0;
        }
        if (dato == null && dato2 != null) {
            return -1;
        }
        if (dato != null && dato2 == null) {
            return 1;
        }
        return dato.compareTo(dato2);
    }

    public static int compareTo(TipoTramitacionDTO dato, TipoTramitacionDTO dato2) {
        if (dato == null && dato2 == null) {
            return 0;
        }
        if (dato == null && dato2 != null) {
            return -1;
        }
        if (dato != null && dato2 == null) {
            return 1;
        }
        return dato.compareTo(dato2);
    }


    public static int compareTo(UnidadAdministrativaDTO dato, UnidadAdministrativaDTO dato2) {
        if (dato == null && dato2 == null) {
            return 0;
        }
        if (dato == null && dato2 != null) {
            return -1;
        }
        if (dato != null && dato2 == null) {
            return 1;
        }
        return dato.compareTo(dato2);
    }

    public static int compareTo(TipoProcedimientoDTO dato, TipoProcedimientoDTO dato2) {
        if (dato == null && dato2 == null) {
            return 0;
        }
        if (dato == null && dato2 != null) {
            return -1;
        }
        if (dato != null && dato2 == null) {
            return 1;
        }
        return dato.compareTo(dato2);
    }

    public static int compareTo(TipoViaDTO dato, TipoViaDTO dato2) {
        if (dato == null && dato2 == null) {
            return 0;
        }
        if (dato == null && dato2 != null) {
            return -1;
        }
        if (dato != null && dato2 == null) {
            return 1;
        }
        return dato.compareTo(dato2);
    }


    public static int compareTo(TipoSilencioAdministrativoDTO dato, TipoSilencioAdministrativoDTO dato2) {
        if (dato == null && dato2 == null) {
            return 0;
        }
        if (dato == null && dato2 != null) {
            return -1;
        }
        if (dato != null && dato2 == null) {
            return 1;
        }
        return dato.compareTo(dato2);
    }


    public static int compareTo(Literal dato, Literal dato2) {
        if (dato == null && dato2 == null) {
            return 0;
        }
        if ((dato == null || dato.getTraducciones() == null) && (dato2 != null || dato2.getTraducciones() != null)) {
            return -1;
        }
        if ((dato != null && dato.getTraducciones() != null) && (dato2 == null || dato2.getTraducciones() == null)) {
            return 1;
        }

        if (dato.getTraducciones().size() > dato2.getTraducciones().size()) {
            return 1;
        } else if (dato2.getTraducciones().size() > dato.getTraducciones().size()) {
            return -1;
        } else {
            for (Traduccion traduccion : dato.getTraducciones()) {
                String trad = traduccion.getLiteral();
                String trad2 = dato2.getTraduccion(traduccion.getIdioma());
                if (trad == null && trad2 == null) {
                    continue;
                }
                if (trad == null && trad2 != null) {
                    return -1;
                }
                if (trad != null && trad2 == null) {
                    return 1;
                }
                int resultado = trad.compareTo(trad2);
                if (resultado != 0) {
                    return resultado;
                }
            }
        }
        return 0;
    }

    public static int compareTo(DocumentoMultiIdioma dato, DocumentoMultiIdioma dato2) {
        if (dato == null && dato2 == null) {
            return 0;
        }
        if ((dato == null || dato.getTraducciones() == null) && (dato2 != null || dato2.getTraducciones() != null)) {
            return -1;
        }
        if ((dato != null && dato.getTraducciones() != null) && (dato2 == null || dato2.getTraducciones() == null)) {
            return 1;
        }

        if (dato.getTraducciones().size() > dato2.getTraducciones().size()) {
            return 1;
        } else if (dato2.getTraducciones().size() > dato.getTraducciones().size()) {
            return -1;
        } else {
            for (DocumentoTraduccion traduccion : dato.getTraducciones()) {

                FicheroDTO trad = traduccion.getFicheroDTO();
                FicheroDTO trad2 = dato2.getTraduccion(traduccion.getIdioma());
                if (trad == null && trad2 == null) {
                    continue;
                }
                if (trad == null && trad2 != null) {
                    return -1;
                }
                if (trad != null && trad2 == null) {
                    return 1;
                }
                int resultado = trad.compareTo(trad2);
                if (resultado != 0) {
                    return resultado;
                }
            }
        }
        return 0;
    }

}
