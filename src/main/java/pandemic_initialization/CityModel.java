package pandemic_initialization;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CityModel {
  
  private static ResourceBundle messages = ResourceBundle.getBundle("MessageBundle");

  private String name;
  private int color;
  private List<String> adjacent;

  public CityModel() {
    name = "";
    color = -1;
    adjacent = new ArrayList<String>();
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getColor() {
    return color;
  }

  public void setColor(int color) {
    if (color > 4 || color < 0) {
      String message = messages.getString("cityModelColorException");
      throw new IllegalArgumentException(message);
    }
    this.color = color;
  }

  public List<String> getAdjacent() {
    return adjacent;
  }

  public void setAdjacent(List<String> expected) {
    for (String city : expected) {
      adjacent.add(city);
    }
  }

}