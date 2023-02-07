package es.caib.rolsac2.service.model.types;

/**
 * Tipos de rol
 */
public enum TypeProcedimientoWorkflow {

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

    TypeProcedimientoWorkflow(boolean iPerfil) {
        perfil = iPerfil;
    }

    public static TypeProcedimientoWorkflow fromBoolean(boolean iPerfil) {
        TypeProcedimientoWorkflow tipo = null;
        for (TypeProcedimientoWorkflow typeRol : TypeProcedimientoWorkflow.values()) {
            if (typeRol.getValor() == iPerfil) {
                tipo = typeRol;
                break;
            }
        }
        return tipo;
    }


    public boolean isPublicado() {
        return perfil == TypeProcedimientoWorkflow.PUBLICADO.getValor();
    }

    public boolean isModificado() {
        return perfil == TypeProcedimientoWorkflow.MODIFICACION.getValor();
    }

    public boolean getValor() {
        return perfil;
    }
}
