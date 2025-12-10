CREATE OR REPLACE FUNCTION obtenerUAconDIR3 (
    p_unad_codigo RS2_UNIADM.UNAD_CODIGO%TYPE
) RETURN RS2_UNIADM.UNAD_CODIGO%TYPE IS

    v_dir3   RS2_UNIADM.UNAD_DIR3%TYPE;
    v_padre  RS2_UNIADM.UNAD_UNADPADRE%TYPE;

BEGIN
        IF p_unad_codigo IS null
                THEN
                        /** SI EL PARAMETRO QUE SE PASA ES NULO, DEVOLVER NULO **/
                        RETURN NULL;
        END IF;

        SELECT UNAD_DIR3, UNAD_UNADPADRE
        INTO v_dir3, v_padre
        FROM RS2_UNIADM
        WHERE UNAD_CODIGO = p_unad_codigo;

        -- Si la unidad tiene DIR3, devolver su código
        IF v_dir3 IS NOT NULL THEN
                RETURN p_unad_codigo;
        END IF;

        -- Si no tiene padre, no hay más que buscar
        IF v_padre IS NULL THEN
                RETURN NULL;
        END IF;

    -- buscar en el padre
    RETURN obtenerUAconDIR3(v_padre);

EXCEPTION
    WHEN NO_DATA_FOUND THEN
      RAISE_APPLICATION_ERROR(-20001,
            'No se encontró la unidad administrativa con código: ' || p_unad_codigo || ' seguramente porque no existe (al migrarse, solo se migran publicas)');
END;
