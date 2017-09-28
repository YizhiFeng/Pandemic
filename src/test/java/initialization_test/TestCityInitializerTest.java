package initialization_test;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import pandemic_game.City;
import pandemic_initialization.CityInitializer;
import pandemic_initialization.TestCityInitializer;

public class TestCityInitializerTest {

  @Test
  public void testAdjacentCityForAtlanta() {
    CityInitializer init = new TestCityInitializer();
    Map<String, City> cityMap = init.initializeCityMap();
    assertTrue(cityMap.containsKey("Atlanta"));
    City chicago = cityMap.get("Chicago");
    City atlanta = cityMap.get("Atlanta");
    City washington = cityMap.get("Washington");
    City miami = cityMap.get("Miami");
    List<City> atlantaAdjacents = atlanta.getAdjacentCities();
    assertTrue(atlantaAdjacents.contains(chicago));
    assertTrue(atlantaAdjacents.contains(washington));
    assertTrue(atlantaAdjacents.contains(miami));
  }

  @Test
  public void testAdjacentCityForMontreal() {
    CityInitializer init = new TestCityInitializer();
    Map<String, City> cityMap = init.initializeCityMap();
    assertTrue(cityMap.containsKey("Montreal"));
    City montreal = cityMap.get("Montreal");
    City chicago = cityMap.get("Chicago");
    City washington = cityMap.get("Washington");
    City ny = cityMap.get("New York");
    List<City> atlantaAdjacents = montreal.getAdjacentCities();
    assertTrue(atlantaAdjacents.contains(chicago));
    assertTrue(atlantaAdjacents.contains(washington));
    assertTrue(atlantaAdjacents.contains(ny));
  }

  @Test
  public void testAdjacentCityForNewYork() {
    CityInitializer init = new TestCityInitializer();
    Map<String, City> cityMap = init.initializeCityMap();
    assertTrue(cityMap.containsKey("New York"));
    City ny = cityMap.get("New York");
    City montreal = cityMap.get("Montreal");
    City washington = cityMap.get("Washington");
    City madrid = cityMap.get("Madrid");
    List<City> atlantaAdjacents = ny.getAdjacentCities();
    assertTrue(atlantaAdjacents.contains(washington));
    assertTrue(atlantaAdjacents.contains(montreal));
    assertTrue(atlantaAdjacents.contains(madrid));
    City london = cityMap.get("London");
    assertTrue(atlantaAdjacents.contains(london));
  }

  @Test
  public void testAdjacentCityForWashington() {
    CityInitializer init = new TestCityInitializer();
    Map<String, City> cityMap = init.initializeCityMap();
    assertTrue(cityMap.containsKey("Washington"));
    City washington = cityMap.get("Washington");
    City ny = cityMap.get("New York");
    City montreal = cityMap.get("Montreal");
    City atlanta = cityMap.get("Atlanta");
    List<City> atlantaAdjacents = washington.getAdjacentCities();
    assertTrue(atlantaAdjacents.contains(montreal));
    assertTrue(atlantaAdjacents.contains(atlanta));
    assertTrue(atlantaAdjacents.contains(ny));
    City miami = cityMap.get("Miami");
    assertTrue(atlantaAdjacents.contains(miami));
  }

  @Test
  public void testAdjacentCityForChicago() {
    CityInitializer init = new TestCityInitializer();
    Map<String, City> cityMap = init.initializeCityMap();
    assertTrue(cityMap.containsKey("Chicago"));
    City chicago = cityMap.get("Chicago");
    City montreal = cityMap.get("Montreal");
    City atlanta = cityMap.get("Atlanta");

    List<City> atlantaAdjacents = chicago.getAdjacentCities();
    assertTrue(atlantaAdjacents.contains(montreal));
    assertTrue(atlantaAdjacents.contains(atlanta));
    City mexicoCity = cityMap.get("Mexico City");
    City la = cityMap.get("Los Angeles");
    assertTrue(atlantaAdjacents.contains(mexicoCity));
    assertTrue(atlantaAdjacents.contains(la));
    City sanFrancisco = cityMap.get("San Francisco");
    assertTrue(atlantaAdjacents.contains(sanFrancisco));
  }

  @Test
  public void testAdjacentCityForLondon() {
    CityInitializer init = new TestCityInitializer();
    Map<String, City> cityMap = init.initializeCityMap();
    assertTrue(cityMap.containsKey("London"));
    City ny = cityMap.get("New York");
    City london = cityMap.get("London");
    City madrid = cityMap.get("Madrid");

    List<City> atlantaAdjacents = london.getAdjacentCities();
    assertTrue(atlantaAdjacents.contains(ny));
    assertTrue(atlantaAdjacents.contains(madrid));
  }

  @Test
  public void testAdjacentCityForMexicoCity() {
    CityInitializer init = new TestCityInitializer();
    Map<String, City> cityMap = init.initializeCityMap();
    assertTrue(cityMap.containsKey("Mexico City"));
    City chicago = cityMap.get("Chicago");
    City bogota = cityMap.get("Bogota");
    City mexicoCity = cityMap.get("Mexico City");
    List<City> atlantaAdjacents = mexicoCity.getAdjacentCities();
    
    assertTrue(atlantaAdjacents.contains(chicago));
    assertTrue(atlantaAdjacents.contains(bogota));
    City miami = cityMap.get("Miami");
    City la = cityMap.get("Los Angeles");
    City lima = cityMap.get("Lima");
    assertTrue(atlantaAdjacents.contains(miami));
    assertTrue(atlantaAdjacents.contains(la));
    assertTrue(atlantaAdjacents.contains(lima));
  }
  
  @Test
  public void testAdjacentCityForMiami() {
    CityInitializer init = new TestCityInitializer();
    Map<String, City> cityMap = init.initializeCityMap();
    assertTrue(cityMap.containsKey("Miami"));
    City bogota = cityMap.get("Bogota");
    City mexicoCity = cityMap.get("Mexico City");
    City miami = cityMap.get("Miami");
    City atlanta = cityMap.get("Atlanta");
    List<City> adjacents = miami.getAdjacentCities();

    assertTrue(adjacents.contains(mexicoCity));
    assertTrue(adjacents.contains(atlanta));
    assertTrue(adjacents.contains(bogota));
    City washington = cityMap.get("Washington");
    assertTrue(adjacents.contains(washington));
  }
  
  @Test
  public void testAdjacentCityForBogota() {
    CityInitializer init = new TestCityInitializer();
    Map<String, City> cityMap = init.initializeCityMap();
    assertTrue(cityMap.containsKey("Bogota"));
    City bogota = cityMap.get("Bogota");
    City mexicoCity = cityMap.get("Mexico City");
    City miami = cityMap.get("Miami");
    City saopaulo = cityMap.get("Sao Paulo");
    List<City> adjacents = bogota.getAdjacentCities();

    assertTrue(adjacents.contains(mexicoCity));
    assertTrue(adjacents.contains(saopaulo));
    assertTrue(adjacents.contains(miami));
    City lima = cityMap.get("Lima");
    assertTrue(adjacents.contains(lima));
  }
  
  @Test
  public void testAdjacentCityForLima() {
    CityInitializer init = new TestCityInitializer();
    Map<String, City> cityMap = init.initializeCityMap();
    assertTrue(cityMap.containsKey("Lima"));
    City mexicoCity = cityMap.get("Mexico City");
    City lima = cityMap.get("Lima");
    List<City> adjacents = lima.getAdjacentCities();

    assertTrue(adjacents.contains(mexicoCity));
    City bogota = cityMap.get("Bogota");
    City santiago = cityMap.get("Santiago");
    assertTrue(adjacents.contains(bogota));
    assertTrue(adjacents.contains(santiago));
  }
  
  @Test
  public void testAdjacentCityForSaoPaulo() {
    CityInitializer init = new TestCityInitializer();
    Map<String, City> cityMap = init.initializeCityMap();
    assertTrue(cityMap.containsKey("Sao Paulo"));
    City saopaulo = cityMap.get("Sao Paulo");
    List<City> adjacents = saopaulo.getAdjacentCities();

    City madrid = cityMap.get("Madrid");
    City bogota = cityMap.get("Bogota");
    assertTrue(adjacents.contains(madrid));
    assertTrue(adjacents.contains(bogota));
  }
}
