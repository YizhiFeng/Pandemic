package pandemic_game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import pandemic_exception.InitializationException;
import pandemic_exception.InvalidActionException;
import pandemic_game.Constants.CubeColor;

public class City {

  private static ResourceBundle messages = ResourceBundle.getBundle("MessageBundle");
  
  private String cityName;
  private boolean researchStation;
  private List<City> adjacentCities;
  private Map<CubeColor, Integer> cubes;

  public City(String name) {
    cityName = name;
    adjacentCities = new ArrayList<City>();
    cubes = new HashMap<CubeColor, Integer>();
    cubes.put(CubeColor.RED, 0);
    cubes.put(CubeColor.YELLOW, 0);
    cubes.put(CubeColor.BLUE, 0);
    cubes.put(CubeColor.BLACK, 0);
  }

  public String getName() {
    return cityName;
  }

  public boolean hasResearchStation() {
    return researchStation;
  }

  public void addResearchStation() {
    researchStation = true;
  }

  public void removeResearchStation() {
    if (!researchStation) {
      throw new InvalidActionException(messages.getString("cityNoResearchStationException"));
    }
    researchStation = false;
  }

  public int getCubeCount(CubeColor color) {
    return cubes.get(color);
  }

  public void addCube(CubeColor color) {
    cubes.put(color, cubes.get(color) + 1);
  }

  public void removeCube(CubeColor color) {
    int cubeCount = this.getCubeCount(color);
    if (cubeCount == 0) {
      throw new InvalidActionException(messages.getString("cityNoCubeToRemoveException"));
    }
    cubes.put(color, cubeCount - 1);
  }

  public List<City> getAdjacentCities() {
    return adjacentCities;
  }

  public void addAdjacentCity(City adjacentCity) {
    if (adjacentCity.getName().equals(this.getName())) {
      throw new InitializationException(messages.getString("cityAddAdjacentCityException"));
    }
    if (!this.adjacentCities.contains(adjacentCity)) {
      adjacentCities.add(adjacentCity);
      adjacentCity.addAdjacentCity(this);
    }
  }

  @Override
  public String toString() {
    return cityName;
  }

}
