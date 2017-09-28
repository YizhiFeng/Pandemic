package initialization_test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.Test;

import pandemic_game.City;
import pandemic_initialization.CityInitializer;
import pandemic_initialization.MainCityInitializer;

public class MainCityInitializerTest {

  @Test
  public void test48Cities() {
    CityInitializer cityInit = new MainCityInitializer();
    Map<String, City> cityMap = cityInit.initializeCityMap();
    assertEquals(48, cityMap.size());
  }

  @Test
  public void testAtlantaCityInMap() {
    CityInitializer cityInit = new MainCityInitializer();
    Map<String, City> cityMap = cityInit.initializeCityMap();
    assertTrue(cityMap.containsKey("Atlanta"));
    assertFalse(cityMap.get("Atlanta").getAdjacentCities().isEmpty());
  }
}
