package es.caib.rolsac2.back.config;

import javax.faces.application.Resource;
import javax.faces.application.ResourceHandler;
import javax.faces.application.ResourceHandlerWrapper;
import javax.faces.application.ResourceWrapper;

/**
 * ResourceHandler que añade un parámetro de versión basado en el timestamp de inicio de la
 * aplicación a todas las URLs de recursos estáticos (JS, CSS, imágenes...) gestionados por JSF.
 */
public class CacheBusterResourceHandler extends ResourceHandlerWrapper {

    private static final String VERSION = String.valueOf(System.currentTimeMillis());
    private final ResourceHandler wrapped;

    public CacheBusterResourceHandler(ResourceHandler wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public ResourceHandler getWrapped() {
        return wrapped;
    }

    @Override
    public Resource createResource(String resourceName) {
        return wrap(super.createResource(resourceName));
    }

    @Override
    public Resource createResource(String resourceName, String libraryName) {
        return wrap(super.createResource(resourceName, libraryName));
    }

    @Override
    public Resource createResource(String resourceName, String libraryName, String contentType) {
        return wrap(super.createResource(resourceName, libraryName, contentType));
    }

    private Resource wrap(Resource resource) {
        if (resource == null) {
            return null;
        }
        return new VersionedResource(resource);
    }


    private static class VersionedResource extends ResourceWrapper {

        private final Resource wrapped;

        VersionedResource(Resource wrapped) {
            this.wrapped = wrapped;
        }

        @Override
        public Resource getWrapped() {
            return wrapped;
        }

        /**
         * Añade el parámetro "v=<timestamp>" a la URL del recurso.
         */
        @Override
        public String getRequestPath() {
            String path = super.getRequestPath();
            if (path == null) {
                return null;
            }
            // Separar el fragmento (#...) si existe
            int hashIdx = path.indexOf('#');
            String fragment = "";
            if (hashIdx >= 0) {
                fragment = path.substring(hashIdx);
                path = path.substring(0, hashIdx);
            }
            // Añadir el parámetro de versión
            String separator = path.contains("?") ? "&" : "?";
            return path + separator + "v=" + VERSION + fragment;
        }
    }
}
