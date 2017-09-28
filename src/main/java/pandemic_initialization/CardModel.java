package pandemic_initialization;

import java.util.ResourceBundle;

public class CardModel {
  
  private static ResourceBundle messages = ResourceBundle.getBundle("MessageBundle");
  
  private String name;
  private int type;
  private int color;
  private int population;

  public CardModel() {
    name = "";
    type = -1;
    color = -1;
    population = -1;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getType() {
    return type;
  }

  public void setType(int type) {
    if (type > 4 || type < 2) {
      String message = messages.getString("cardModelTypeException");
      throw new IllegalArgumentException(message);
    }
    this.type = type;
  }

  public int getColor() {
    return color;
  }

  public void setColor(int color) {
    if (color > 4 || color < 0) {
      String message = messages.getString("cardModelColorException");
      throw new IllegalArgumentException(message);
    }
    this.color = color;
  }

  public int getPopulation() {
    return population;
  }

  public void setPopulation(int pop) {
    if (pop < 0) {
      String message = messages.getString("cardModelPopulationException");
      throw new IllegalArgumentException(message);
    }
    this.population = pop;
  }

}