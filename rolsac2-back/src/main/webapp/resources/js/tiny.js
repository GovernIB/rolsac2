tinyMCE.init({
		   // General options
		    mode : "textareas",
		    plugins: "code image link lists paste textcolor",
		    toolbar: "bold italic | forecolor backcolor | link image | alignleft aligncenter alignright alignjustify | numlist bullist outdent indent | removeformat #{dialogTraduccionHTML.sourceCode}" ,
		    menubar: "",
		    paste_as_text : false,
		 });


    /** Organiza los tabs. **/
    function mostrar(idioma) {
    	mostrarTab(idioma);
    	mostrarButton(idioma);
    }

   /** Funcion que muestra el tab con la traducción de cada idioma ***/
   function mostrarButton(idioma) {
	    ocultarButton("formTraduccion:btnCa");
	    ocultarButton("formTraduccion:btnEs");
	    ocultarButton("formTraduccion:btnEn");
	    ocultarButton("formTraduccion:btnDe");

	   	if (idioma == 'ca') {
	   		document.getElementById("formTraduccion:btnCa").classList.add("marcado");
	   		return;
	   	}
	   	if (idioma == 'es') {
	   		document.getElementById("formTraduccion:btnEs").classList.add("marcado");
	   		return;
	   	}
   }

   /** Ocultar button. **/
   function ocultarButton(elId) {
	   	if (document.getElementById(elId) != null) {
	   		document.getElementById(elId).classList.remove("marcado");
	   	}
   }

    /** Funcion que muestra el tab con la traducción de cada idioma ***/
    function mostrarTab(idioma) {
    	ocultarTab("formTraduccion:traduccion_ca");
    	ocultarTab("formTraduccion:traduccion_es");
    	ocultarTab("formTraduccion:traduccion_en");
    	ocultarTab("formTraduccion:traduccion_de");
    	if (idioma == 'ca') {
    		document.getElementById("formTraduccion:traduccion_ca").parentNode.style.display="";
    		return;
    	}
    	if (idioma == 'es') {
    		document.getElementById("formTraduccion:traduccion_es").parentNode.style.display="";
    		return;
    	}
    	if (idioma == 'en') {
    		document.getElementById("formTraduccion:traduccion_en").parentNode.style.display="";
    		return;
    	}
    	if (idioma == 'de') {
    		document.getElementById("formTraduccion:traduccion_de").parentNode.style.display="";
    		return;
    	}
    }

    /** Ocultar tab. **/
    function ocultarTab(elId) {
    	if (document.getElementById(elId) != null) {
    		document.getElementById(elId).parentNode.style.display="none";
    	}
    }

    var visibleCa = true;
    var visibleEs = true;

    function guardar() {
    	if (visibleCa) {
    		if (!checkTiny("formTraduccion:traduccion_ca")) {
				document.getElementById("formTraduccion:btnErrorCa").click();
				document.getElementById("formTraduccion:btnCa").click();
    			return;
    		}
    	}
    	if (visibleEs) {
    		if (!checkTiny("formTraduccion:traduccion_es")) {
				document.getElementById("formTraduccion:btnErrorEs").click();
				document.getElementById("formTraduccion:btnEs").click();
    			return;
    		}
    	}
    	document.getElementById("formTraduccion:btnGuardar").click();

    }

    /** Comprueba si esta vacio, En caso de no estarlo, rellena el campo. **/
    function checkTiny(id) {
    	var correcto = false;
    	var contenido = tinyMCE.get(id).getContent();
    	if (isVacio(contenido)) {
    		correcto = false;
    	} else {
    		correcto = true;
    		document.getElementById(id).value = contenido;
    	}
    	return correcto;
    }

  //<![CDATA[
    function isVacio(texto)  {
    	while (texto.indexOf('&nbsp;') >= 0) {
    		texto = texto.replace('&nbsp;',"");
    	}
    	while (texto.indexOf('</p>') >= 0) {
    		texto = texto.replace('</p>',"");
    	}
    	while (texto.indexOf('<p>') >= 0) {
    		texto = texto.replace('<p>',"");
    	}
    	while (texto.indexOf('\n') >= 0) {
    		texto = texto.replace('\n',"");
    	}
    	texto = texto.trim();
    	if (texto == '') {
    		return true;
   		}
    	return false;
    }
  //]]>
