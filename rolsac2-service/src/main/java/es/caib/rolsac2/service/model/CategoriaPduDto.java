package es.caib.rolsac2.service.model;


import java.util.Objects;
public class CategoriaPduDto extends ModelApi implements Cloneable{


        private Long codigo;
        private Integer orden;
        private String identificador;
        private String descripcion;

        // Default constructor
        public CategoriaPduDto() {
        }

        // Parameterized constructor
        public CategoriaPduDto(Long codigo, Integer orden, String identificador, String descripcion) {
            this.codigo = codigo;
            this.orden = orden;
            this.identificador = identificador;
            this.descripcion = descripcion;
        }

        // Getters and Setters
        public Long getCodigo() {
            return codigo;
        }

        public void setCodigo(Long codigo) {
            this.codigo = codigo;
        }

        public Integer getOrden() {
            return orden;
        }

        public void setOrden(Integer orden) {
            this.orden = orden;
        }

        public String getIdentificador() {
            return identificador;
        }

        public void setIdentificador(String identificador) {
            this.identificador = identificador;
        }

        public String getDescripcion() {
            return descripcion;
        }

        public void setDescripcion(String descripcion) {
            this.descripcion = descripcion;
        }

        @Override
        public String toString() {
            return "JCategoriaPduDTO{" +
                    "codigo=" + codigo +
                    ", orden=" + orden +
                    ", identif='" + identificador + '\'' +
                    ", descri='" + descripcion + '\'' +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            CategoriaPduDto that = (CategoriaPduDto) o;
            return Objects.equals(codigo, that.codigo) &&
                    Objects.equals(orden, that.orden) &&
                    Objects.equals(identificador, that.identificador) &&
                    Objects.equals(descripcion, that.descripcion);
        }

        @Override
        public int hashCode() {
            return Objects.hash(codigo, orden, identificador, descripcion);
        }

}
