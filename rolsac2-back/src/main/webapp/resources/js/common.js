function showMenuTipos() {
  ocultarMenus();
  document.getElementById("menuTipos").classList.toggle("show");
}

function ocultarMenus() {
  if (document.getElementById("menuTipos") != null) {
    document.getElementById("menuTipos").classList.remove('show');
  }
  if (document.getElementById("menuPersonalizacion") != null) {
    document.getElementById("menuPersonalizacion").classList.remove('show');
  }
  if (document.getElementById("menuUsuarios") != null) {
    document.getElementById("menuUsuarios").classList.remove('show');
  }
  if (document.getElementById("menuMonitorizacion") != null) {
    document.getElementById("menuMonitorizacion").classList.remove('show');
  }
   if (document.getElementById("menuGestionOrganigrama") != null) {
     document.getElementById("menuGestionOrganigrama").classList.remove('show');
   }
}

function showMenuPersonalizacion() {
  ocultarMenus();
  document.getElementById("menuPersonalizacion").classList.toggle("show");
}

function showMenuUsuarios() {
  ocultarMenus();
  document.getElementById("menuUsuarios").classList.toggle("show");
}

function showMenuMonitorizacion() {
  ocultarMenus();
  document.getElementById("menuMonitorizacion").classList.toggle("show");
}

function showMenuGestionOrganigrama() {
  ocultarMenus();
  document.getElementById("menuGestionOrganigrama").classList.toggle("show");
}

document.onclick = function(event) {
  if (!event.target.matches('.spanClick')) {
    var dropdowns = document.getElementsByClassName("menuTipos");
    var i;
    for (i = 0; i < dropdowns.length; i++) {
      var openDropdown = dropdowns[i];
      if (openDropdown.classList.contains('show')) {
        openDropdown.classList.remove('show');
      }
    }
  }
}