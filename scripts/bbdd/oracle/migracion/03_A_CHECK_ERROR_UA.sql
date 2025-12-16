CREATE OR REPLACE FUNCTION CHECK_ERROR_UA (
	p_unad_codigo NUMBER,
	p_unad_inicial   NUMBER,
	nombre       VARCHAR2
) RETURN VARCHAR2 IS
	v_dir3   R1_UNIADM.UNA_CODDR3%TYPE;
	v_padre  R1_UNIADM.UNA_CODUNA%TYPE;
	v_valida R1_UNIADM.UNA_VALIDA%TYPE;

BEGIN

	/** Comprobar la UA en la tabla r1_uniadm , hay que comprobar si
		la UA que se pasa (tiene dir3), si no tiene dir3 devolver el mensaje  'La unitat no s ha migrat perque no te dir3' , si tiene hay que mirar si algún padre NO tiene dir3. En caso de encontrarlo, devolver el error  'La unitat no s ha migrat perque no te dir3'.
		Si subiendo por los padres todos tienen dir3, devolver 'La UA no existe.'
	**/

	IF p_unad_codigo IS null
	THEN
		/** SI EL PARAMETRO QUE SE PASA ES NULO **/
		RETURN 'La UA ' || p_unad_inicial || ' "' || nombre || '" no existe su padre.';
	END IF;

	SELECT UNA_CODDR3, UNA_CODUNA, UNA_VALIDA
	INTO v_dir3, v_padre, v_valida
	FROM r1_uniadm
	WHERE UNA_CODI = p_unad_codigo;

	-- Si la unidad NO tiene DIR3, devolver el mensaje de error
	IF v_dir3 IS NULL
	THEN
		RETURN 'La UA ' || p_unad_inicial || ' "' || nombre || '" no sha migrat perque no te dir3 un ascendent.';
	ELSIF v_valida != 1
	THEN
		RETURN 'La UA ' || p_unad_inicial || ' "' || nombre || '" no sha migrat perque no es troba públic un ascendent.';
	ELSE
		-- Si no tiene padre, no hay más que buscar
		IF v_padre IS NULL THEN
			RETURN 'La UA ' || p_unad_inicial || ' "' || nombre || '" no existe su padre..';
		END IF;

		-- buscar en el padre
		RETURN CHECK_ERROR_UA(v_padre, p_unad_inicial, nombre);
	END IF;

EXCEPTION
	WHEN NO_DATA_FOUND THEN
		rollback;
		return 'Error al comprobar la unidad administrativa con código: ' || p_unad_inicial || ' CODE:' || SQLCODE || '  MSG:' || SQLERRM ;
	WHEN     OTHERS THEN
		rollback;
		return 'SE HA PRODUCIDO UN ERROR\n' || 'El error. CODE:' || SQLCODE || '  MSG:' || SQLERRM || '. \n';
END;
