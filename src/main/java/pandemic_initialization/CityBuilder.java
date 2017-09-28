package pandemic_initialization;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import pandemic_exception.InitializationException;
import pandemic_game.City;

public class CityBuilder {

  private static ResourceBundle messages = ResourceBundle.getBundle("MessageBundle");

  public Map<String, City> loadPandemicCities(BufferedReader reader) {
    try {
      String jsonString = readJsonString(reader);
      return createPandemicCities(jsonString);
    } catch (IOException e) {
      throw new InitializationException(messages.getString("unableToLoadCitiesException"));
    }
  }

  private String readJsonString(BufferedReader reader) throws IOException {
    StringBuilder sb = new StringBuilder();
    String line = reader.readLine();
    while (line != null) {
      sb.append(line);
      line = reader.readLine();
    }
    reader.close();
    return sb.toString();
  }

  private Map<String, City> createPandemicCities(String jsonString) {
    List<CityModel> models = createCityModels(jsonString);
    Map<String, City> cities = new HashMap<String, City>();
    for (CityModel model : models) {
      City city = createCityFromModel(model);
      cities.put(model.getName(), city);
    }
    for (CityModel model : models) {
      City city = cities.get(model.getName());
      for (String cityName : model.getAdjacent()) {
        city.addAdjacentCity(cities.get(cityName));
      }
      cities.put(model.getName(), city);
    }
    return cities;
  }

  private List<CityModel> createCityModels(String jsonString) {
    Type type = new TypeToken<List<CityModel>>() {}.getType();
    GsonBuilder builder = new GsonBuilder();
    List<CityModel> output = builder.create().fromJson(jsonString, type);
    return output;
  }

  public City createCityFromModel(CityModel model) {
    return new City(model.getName());
  }

}
