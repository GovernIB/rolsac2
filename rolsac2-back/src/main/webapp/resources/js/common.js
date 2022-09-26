function showMenuTipos() {
  document.getElementById("menuTipos").classList.toggle("show");
}

function showMenuPersonalizacion() {
	  document.getElementById("menuPersonalizacion").classList.toggle("show");
}

function showMenuUsuarios() {
  document.getElementById("menuUsuarios").classList.toggle("show");
}

function showMenuMonitorizacion() {
  document.getElementById("menuMonitorizacion").classList.toggle("show");
}

function showMenuGestionOrganigrama() {
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