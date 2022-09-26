package es.caib.rolsac2.back.controller.maestras.cuadroControl;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Named
@ApplicationScoped
public class ContingutService {

  private final static String[] colors;

  private final static String[] brands;

  private final static String[] tipus;

  static {
    colors = new String[3];
    colors[0] = "Black";
    colors[1] = "White";
    colors[2] = "Green";

    tipus = new String[3];
    tipus[0] = "Procediments";
    tipus[1] = "Normatives";
    tipus[2] = "Fitxes informatives";

    brands = new String[3];
    brands[0] = "BMW";
    brands[1] = "Mercedes";
    brands[2] = "Volvo";
  }

  public List<Contingut> createContingut(int size) {
    List<Contingut> list = new ArrayList<Contingut>();
    for (int i = 0; i < size; i++) {
      list.add(new Contingut(getRandomTipus(i), getRandomYear(), getRandomBrand(), getRandomColor()));
    }

    return list;
  }

  private String getRandomTipus(int size) {
    return tipus[size];
  }

  private int getRandomYear() {
    return (int) (Math.random() * 50 + 1960);
  }

  private String getRandomColor() {
    return colors[(int) (Math.random() * 3)];
  }

  private String getRandomBrand() {
    return brands[(int) (Math.random() * 3)];
  }

  public List<String> getColors() {
    return Arrays.asList(colors);
  }

  public List<String> getBrands() {
    return Arrays.asList(brands);
  }
}
