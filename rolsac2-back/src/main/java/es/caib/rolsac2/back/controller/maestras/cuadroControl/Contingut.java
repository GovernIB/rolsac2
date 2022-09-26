package es.caib.rolsac2.back.controller.maestras.cuadroControl;

import java.io.Serializable;

public class Contingut implements Serializable {

  public String id;
  public String brand;
  public int year;
  public String color;

  public Contingut() {
  }

  public Contingut(String id, int year, String brand, String color) {
    this.id = id;
    this.brand = brand;
    this.year = year;
    this.color = color;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getBrand() {
    return brand;
  }

  public void setBrand(String brand) {
    this.brand = brand;
  }

  public int getYear() {
    return year;
  }

  public void setYear(int year) {
    this.year = year;
  }

  public String getColor() {
    return color;
  }

  public void setColor(String color) {
    this.color = color;
  }

}
