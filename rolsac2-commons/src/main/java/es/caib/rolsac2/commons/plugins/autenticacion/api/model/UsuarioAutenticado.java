package es.caib.rolsac2.commons.plugins.autenticacion.api.model;

import java.util.Set;

public class UsuarioAutenticado {

    private String nombre;

    private String apellido1;

    private String apellido2;

    private String nif;

    private String email;

    private String telefono;

    private Set<String> roles;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "UsuarioAutenticado {\n" +
                "nombre='" + nombre + "\'" +
                ",\n apellido1='" + apellido1 + "\'" +
                ",\n apellido2='" + apellido2 + "\'" +
                ",\n nif='" + nif + "\'" +
                ",\n telefono='" + telefono + "\'" +
                ",\n email='" + email + "\'" +
                ",\n roles=" + roles +
                '}';
    }
}
