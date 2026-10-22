package es.caib.rolsac2.back.controller.component;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponent;
import javax.faces.component.UIData;
import javax.faces.component.UIInput;
import javax.faces.component.UINamingContainer;
import javax.faces.context.FacesContext;

@FacesComponent("moduloLateral")
public class ModuloLateral extends UINamingContainer {

	/**
	 * Procesa las validaciones de los componentes y marca el módulo como
	 * inválido si es obligatorio y está incompleto.
	 */
	@Override
	public void processValidators(FacesContext context) {
		if (!isRendered()) {
			return;
		}
		super.processValidators(context);
		getStateHelper().put("invalido", isRequired() && !isCompleto());
	}

	private boolean isCompleto() {
		Object completo = getAttributes().get("completo");
		return completo != null ? Boolean.TRUE.equals(completo) : isContenidoCompleto(this);
	}

	private boolean isContenidoCompleto(UIComponent component) {
		if (!component.isRendered()) {
			return true;
		}
		if (component instanceof UIData) {
			return ((UIData) component).getRowCount() > 0;
		}
		if (component instanceof UIInput) {
			UIInput input = (UIInput) component;
			Object value = input.getSubmittedValue() != null ? input.getSubmittedValue() : input.getValue();
			return input.isValid() && !UIInput.isEmpty(value);
		}
		for (UIComponent child : component.getChildren()) {
			if (!isContenidoCompleto(child)) {
				return false;
			}
		}
		UIComponent implementation = component.getFacet(UIComponent.COMPOSITE_FACET_NAME);
		return implementation == null || isContenidoCompleto(implementation);
	}

	public String getTituloModulo() {
		String titulo = (String) getAttributes().get("titulo");
		return titulo + (isRequired() ? "*" : "");
	}

	public String getClasesModulo() {
		String styleClass = (String) getAttributes().get("styleClass");
		if (styleClass == null) {
			styleClass = "";
		}
		return styleClass + (isInvalido() ? " ui-state-error" : "");
	}

	public boolean isInvalido() {
		return Boolean.TRUE.equals(getStateHelper().eval("invalido", false));
	}

	private boolean isRequired() {
		return Boolean.TRUE.equals(getAttributes().get("required"));
	}
}