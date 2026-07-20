-- ====================================================================
-- Script para agregar CHAR a columnas VARCHAR2 sin especificación
-- Base de datos Oracle - ROLSAC2
-- Fecha: 2026-02-12
-- ====================================================================
--
-- Este script modifica las columnas VARCHAR2 que no tienen especificación
-- de CHAR o BYTE para que utilicen CHAR explícitamente.
--
-- IMPORTANTE:
-- - Ejecutar este script en la base de datos Oracle existente
-- - Se recomienda realizar un backup antes de ejecutar
-- - Revisar cada ALTER TABLE antes de ejecutar
-- ====================================================================

-- Tabla RS2_AYUDA
ALTER TABLE RS2_AYUDA MODIFY (AYU_IDENTIFICADOR VARCHAR2(100 CHAR));

-- Tabla RS2_CNFGLO
ALTER TABLE RS2_CNFGLO MODIFY (CFG_VALOR VARCHAR2(4000 CHAR));

-- Tabla RS2_FICEXT
ALTER TABLE RS2_FICEXT MODIFY (
    FIE_FICTIP VARCHAR2(50 CHAR),
    FIE_FILENAME VARCHAR2(150 CHAR)
);

-- Tabla RS2_IDSOLR
ALTER TABLE RS2_IDSOLR MODIFY (ISOL_TIPO VARCHAR2(3 CHAR));

-- Tabla RS2_IDSIA
ALTER TABLE RS2_IDSIA MODIFY (ISIA_TIPO VARCHAR2(3 CHAR));

-- Tabla RS2_NORMA
ALTER TABLE RS2_NORMA MODIFY (NORM_ESTADO VARCHAR2(1 CHAR));

-- Tabla RS2_PRAUDIT
-- Ya tiene CHAR en PRAU_ACCION (verificado)

-- Tabla RS2_PRCWF
ALTER TABLE RS2_PRCWF MODIFY (PROC_LOPDRESP VARCHAR2(400 CHAR));

-- Tabla RS2_PLUGIN
-- Ya tiene CHAR en PLUG_PREPRO (verificado)

-- Tabla RS2_PROCEX
ALTER TABLE RS2_PROCEX MODIFY (
    PROCEX_CODIGO VARCHAR2(20 CHAR),
    PROCEX_INSTAN VARCHAR2(50 CHAR)
);

-- Tabla RS2_TRAAYU
ALTER TABLE RS2_TRAAYU MODIFY (TAY_IDIOMA VARCHAR2(2 CHAR));

-- Tabla RS2_TRAPRWF
ALTER TABLE RS2_TRAPRWF MODIFY (TRPW_URLPDU VARCHAR2(500 CHAR));

-- Tabla RS2_UNAAUDIT
ALTER TABLE RS2_UNAAUDIT MODIFY (
    UAAU_USUPRF VARCHAR2(10 CHAR),
    UAAU_ACCION VARCHAR2(2 CHAR)
);

-- Tabla RS2_ENTIRAIZ
ALTER TABLE RS2_ENTIRAIZ MODIFY (
    ENTIRAIZ_USER VARCHAR2(100 CHAR),
    ENTIRAIZ_PWD VARCHAR2(100 CHAR)
);

-- ====================================================================
-- Verificación de cambios
-- ====================================================================
--
-- Para verificar que los cambios se aplicaron correctamente, ejecutar:
--
-- SELECT table_name, column_name, data_type, char_length, char_used
-- FROM user_tab_columns
-- WHERE table_name IN (
--     'RS2_AYUDA', 'RS2_CNFGLO', 'RS2_FICEXT', 'RS2_IDSOLR',
--     'RS2_IDSIA', 'RS2_NORMA', 'RS2_PRCWF', 'RS2_PROCEX',
--     'RS2_TRAAYU', 'RS2_TRAPRWF', 'RS2_UNAAUDIT', 'RS2_ENTIRAIZ'
-- )
-- AND data_type LIKE 'VARCHAR%'
-- ORDER BY table_name, column_name;
--
-- ====================================================================

COMMIT;
