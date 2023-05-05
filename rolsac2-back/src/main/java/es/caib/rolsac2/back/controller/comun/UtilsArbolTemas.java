package es.caib.rolsac2.back.controller.comun;

import es.caib.rolsac2.service.facade.TemaServiceFacade;
import es.caib.rolsac2.service.model.TemaGridDTO;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UtilsArbolTemas {


    public static void construirArbol(List<TreeNode> roots, List<TemaGridDTO> temasPadre,
                                      List<TemaGridDTO> temasPadreAnyadidos, List<TemaGridDTO> temasRelacionados,
                                      TemaServiceFacade temaServiceFacade) {
        if (roots == null) {
            roots = new ArrayList<>();
        }
        for (TemaGridDTO temaPadre : temasPadre) {
            TreeNode root = new DefaultTreeNode(temaPadre, null);
            construirArbolDesdeHoja(temaPadre, root, temasPadreAnyadidos, temasRelacionados, temaServiceFacade);
            roots.add(root);
        }
    }

    /**
     * Cosntruye los diferentes árboles que se mostrarán a partir de los temas seleccionados.
     *
     * @param hoja
     * @param arbol
     */
    private static void construirArbolDesdeHoja(TemaGridDTO hoja, TreeNode arbol,
                                                List<TemaGridDTO> temasPadreAnyadidos, List<TemaGridDTO> temasRelacionados,
                                                TemaServiceFacade temaServiceFacade) {
        DefaultTreeNode nodo = null;
        if (hoja.getMathPath() == null) {
            for (TemaGridDTO tema : temasRelacionados) {
                if (tema.getMathPath() != null) {
                    if (tema.getMathPath().equals(hoja.getCodigo().toString())) {
                        if (temasPadreAnyadidos.isEmpty()) {
                            nodo = new DefaultTreeNode(tema, arbol);
                            temasPadreAnyadidos.add(tema);
                            arbol.setExpanded(true);
                            construirArbolDesdeHoja(tema, nodo, temasPadreAnyadidos, temasRelacionados, temaServiceFacade);
                        } else if (!temasPadreAnyadidos.contains(tema)) {
                            nodo = new DefaultTreeNode(tema, arbol);
                            temasPadreAnyadidos.add(tema);
                            arbol.setExpanded(true);
                            construirArbolDesdeHoja(tema, nodo, temasPadreAnyadidos, temasRelacionados, temaServiceFacade);
                        }

                    } else if (Arrays.asList(tema.getMathPath().split(";")).contains(hoja.getCodigo().toString())) {
                        String[] niveles = tema.getMathPath().split(";");
                        String idPadre = niveles[1];
                        TemaGridDTO temaPadre = temaServiceFacade.findGridById(Long.valueOf(idPadre));
                        if (!temasPadreAnyadidos.contains(temaPadre) && !temasRelacionados.contains(temaPadre)) {
                            temasPadreAnyadidos.add(temaPadre);
                            temaPadre.setRelacionado(true);
                            nodo = new DefaultTreeNode(temaPadre, arbol);
                            arbol.setExpanded(true);
                            construirArbolDesdeHoja(temaPadre, nodo, temasPadreAnyadidos, temasRelacionados, temaServiceFacade);
                        }
                    }
                }
            }
        } else {
            Integer nivel = hoja.getMathPath().split(";").length + 1;
            for (TemaGridDTO tema : temasRelacionados) {
                Integer nivelHijo = tema.getMathPath().split(";").length;

                if (Arrays.asList(tema.getMathPath().split(";")).contains(hoja.getCodigo().toString()) && nivelHijo == nivel) {
                    if (temasPadreAnyadidos.isEmpty()) {
                        nodo = new DefaultTreeNode(tema, arbol);
                        arbol.setExpanded(true);
                        construirArbolDesdeHoja(tema, nodo, temasPadreAnyadidos, temasRelacionados, temaServiceFacade);
                    } else if (!temasPadreAnyadidos.contains(tema)) {
                        nodo = new DefaultTreeNode(tema, arbol);
                        arbol.setExpanded(true);
                        construirArbolDesdeHoja(tema, nodo, temasPadreAnyadidos, temasRelacionados, temaServiceFacade);
                    }
                } else if (Arrays.asList(tema.getMathPath().split(";")).contains(hoja.getCodigo().toString())) {
                    String[] niveles = tema.getMathPath().split(";");
                    String idPadre = niveles[nivel];
                    TemaGridDTO temaPadre = temaServiceFacade.findGridById(Long.valueOf(idPadre));
                    if (!temasPadreAnyadidos.contains(temaPadre) && !temasRelacionados.contains(temaPadre)) {
                        temasPadreAnyadidos.add(temaPadre);
                        temaPadre.setRelacionado(true);
                        nodo = new DefaultTreeNode(temaPadre, arbol);
                        arbol.setExpanded(true);
                        construirArbolDesdeHoja(temaPadre, nodo, temasPadreAnyadidos, temasRelacionados, temaServiceFacade);
                    }
                }

            }
        }
    }
}
