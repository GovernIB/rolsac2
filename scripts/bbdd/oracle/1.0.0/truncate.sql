-- =========================================================
-- TRUNCATE COMPLETO DEL ESQUEMA (SEGUR0 CON FOREIGN KEYS)
-- =========================================================

-- 1. Deshabilitar todas las foreign keys
BEGIN
FOR c IN (
SELECT table_name, constraint_name
FROM user_constraints
WHERE constraint_type = 'R'
) LOOP
EXECUTE IMMEDIATE
'ALTER TABLE ' || c.table_name ||
' DISABLE CONSTRAINT ' || c.constraint_name;
END LOOP;
END;
/

-- 2. Truncar todas las tablas del esquema
BEGIN
FOR t IN (
SELECT table_name
FROM user_tables
) LOOP
EXECUTE IMMEDIATE
'TRUNCATE TABLE ' || t.table_name;
END LOOP;
END;
/

-- 3. Volver a habilitar las foreign keys
BEGIN
FOR c IN (
SELECT table_name, constraint_name
FROM user_constraints
WHERE constraint_type = 'R'
) LOOP
EXECUTE IMMEDIATE
'ALTER TABLE ' || c.table_name ||
' ENABLE CONSTRAINT ' || c.constraint_name;
END LOOP;
END;
/
