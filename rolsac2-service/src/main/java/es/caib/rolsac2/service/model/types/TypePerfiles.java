package es.caib.rolsac2.service.model.types;

/**
 * Tipos de rol
 */
public enum TypePerfiles {

    /**
     * <P>REST API</P>
     * <P>Acceso desde el API REST Externo. </P>
     **/
    RESTAPI(TypePerfiles.RESTAPI_VALOR),
    /**
     * <P>SUPER ADMINISTRADOR</P>
     * <P>Responsable de la configuració dels paràmetres globals del sistema i gestió de la multientitat. </P>
     **/
    SUPER_ADMINISTRADOR(TypePerfiles.SUPER_ADMINISTRADOR_VALOR),
    /**
     * <P>ADMINISTRADOR ENTIDAD</P>
     * <P>Responsable de la configuració del sistema a nivell d’entitat.</P>
     * <P>Puede mantener todos los contenidos de la entidad</P>
     **/
    ADMINISTRADOR_ENTIDAD(TypePerfiles.ADMINISTRADOR_ENTIDAD_VALOR),
    /**
     * <P>ADMINISTRADOR CONTENIDOS</P>
     * <P>Responsable de la revisió y control de continguts previ a la publicació de la informació de la entitat.</P>
     * <P>Manteniment de procediments comuns</P>
     * <P>Manteniment de normatives </P>
     * <P>Manteniment de fitxes y seccions</P>
     * <P>Pot mantenir tots el continguts de la entitat </P>
     **/
    ADMINISTRADOR_CONTENIDOS(TypePerfiles.ADMINISTRADOR_CONTENIDOS_VALOR),
    /**
     * <P>GESTOR</P>
     * <P>Responsable de la introducció del continguts informatius de les ua a les que està assignat (dades ua, procediments, serveis i personal)</P>
     * <P>Una vegada introduïts els continguts els pot enviar a revisar per l’ADM de contingut. Per que ho publiqui</P>
     **/
    GESTOR(TypePerfiles.GESTOR_VALOR),
    /**
     * <P>INFORMADOR</P>
     * <P>Perfil de consulta de dades administratives (procedimets y serveis, ua, normatives i personal). </P>
     **/
    INFORMADOR(TypePerfiles.INFORMADOR_VALOR);

    String perfil;
    public static final String SUPER_ADMINISTRADOR_VALOR = "RS2_SUP";
    public static final String ADMINISTRADOR_ENTIDAD_VALOR = "RS2_ADE";
    public static final String ADMINISTRADOR_CONTENIDOS_VALOR = "RS2_ADC";
    public static final String GESTOR_VALOR = "RS2_GES";
    public static final String INFORMADOR_VALOR = "RS2_INF";
    public static final String RESTAPI_VALOR = "RS2_API";

    TypePerfiles(String iPerfil) {
        perfil = iPerfil;
    }

    public static TypePerfiles fromString(String iPerfil) {
        TypePerfiles tipo = null;
        if (iPerfil != null) {
            for (TypePerfiles typeRol : TypePerfiles.values()) {
                if (typeRol.toString().equals(iPerfil)) {
                    tipo = typeRol;
                    break;
                }
            }
        }
        return tipo;
    }


    public boolean isSuperadministrador() {
        return perfil.equals(TypePerfiles.SUPER_ADMINISTRADOR.toString());
    }

    public boolean isAdministradorEntidad() {
        return perfil.equals(TypePerfiles.ADMINISTRADOR_ENTIDAD.toString());
    }

    public boolean isAdministradorContenidos() {
        return perfil.equals(TypePerfiles.ADMINISTRADOR_CONTENIDOS.toString());
    }

    public boolean isGestor() {
        return perfil.equals(TypePerfiles.GESTOR.toString());
    }

    public boolean isInformador() {
        return perfil.equals(TypePerfiles.INFORMADOR.toString());
    }

    public String toString() {
        return perfil;
    }
}
