package pandemic_initialization;

import java.util.Map;

import pandemic_game.City;

public interface CityInitializer {
  Map<String, City> initializeCityMap();
}
