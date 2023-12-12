package es.caib.rolsac2.service.model.types;

/**
 * Tipos de rol
 */
public enum TypeProcedimientoWorkflow {

    /**
     * ESTADO PUBLICADO
     **/
    DEFINITIVO(false),
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
        return perfil == TypeProcedimientoWorkflow.DEFINITIVO.getValor();
    }

    public boolean isModificado() {
        return perfil == TypeProcedimientoWorkflow.MODIFICACION.getValor();
    }

    /**
     * Devuelve el literal
     *
     * @return literal
     **/
    public String getLiteral() {
        String literal = null;
        switch (this) {
            case DEFINITIVO:
                literal = "dict.wf.1";
                break;
            case MODIFICACION:
                literal = "dict.wf.0";
                break;
        }
        return literal;
    }

    public boolean getValor() {
        return perfil;
    }
}
