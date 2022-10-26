package es.caib.rolsac2.service.model.types;

/**
 * Tipos de rol
 */
public enum TypeProcedimientoWorfklow {

    /**
     * ESTADO PUBLICADO
     **/
    PUBLICADO(false),
    /**
     * <P>ADMINISTRADOR ENTIDAD</P>
     * <P>Responsable de la configuració del sistema a nivell d’entitat.</P>
     * <P>Puede mantener todos los contenidos de la entidad</P>
     **/
    MODIFICACION(true);

    boolean perfil;

    TypeProcedimientoWorfklow(boolean iPerfil) {
        perfil = iPerfil;
    }

    public static TypeProcedimientoWorfklow fromBoolean(boolean iPerfil) {
        TypeProcedimientoWorfklow tipo = null;
        for (TypeProcedimientoWorfklow typeRol : TypeProcedimientoWorfklow.values()) {
            if (typeRol.getValor() == iPerfil) {
                tipo = typeRol;
                break;
            }
        }
        return tipo;
    }


    public boolean isPublicado() {
        return perfil == TypeProcedimientoWorfklow.PUBLICADO.getValor();
    }

    public boolean isModificado() {
        return perfil == TypeProcedimientoWorfklow.MODIFICACION.getValor();
    }

    public boolean getValor() {
        return perfil;
    }
}
