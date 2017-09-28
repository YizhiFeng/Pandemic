package pandemic_initialization;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.ResourceBundle;

import pandemic_exception.InitializationException;
import pandemic_game.City;

public class MainCityInitializer implements CityInitializer {

  private static ResourceBundle messages = ResourceBundle.getBundle("MessageBundle");
  
  private CityBuilder builder = new CityBuilder();

  @Override
  public Map<String, City> initializeCityMap() {
    return createCityCards();
  }

  private Map<String, City> createCityCards() {
    Map<String, City> cities = generateCityCards();
    return cities;
  }

  private Map<String, City> generateCityCards() {
    String path = "city/cities.txt";
    InputStreamReader inputReader = openInputReader(path);
    BufferedReader reader = new BufferedReader(inputReader);
    return builder.loadPandemicCities(reader);
  }

  private InputStreamReader openInputReader(String path) {
    try {
      return new InputStreamReader(getClass().getClassLoader().getResourceAsStream(path), "UTF-8");
    } catch (UnsupportedEncodingException e) {
      throw new InitializationException(messages.getString("unableToLoadCardsException"));
    }
  }
}
