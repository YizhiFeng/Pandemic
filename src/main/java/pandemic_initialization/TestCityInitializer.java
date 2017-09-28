package pandemic_initialization;

import java.util.HashMap;
import java.util.Map;

import pandemic_game.City;

public class TestCityInitializer implements CityInitializer {
  
  @Override
  public Map<String, City> initializeCityMap() {
    City altanta = new City("Atlanta");
    City chicago = new City("Chicago");
    City washington = new City("Washington");
    City miami = new City("Miami");
    altanta.addAdjacentCity(chicago);
    altanta.addAdjacentCity(washington);
    altanta.addAdjacentCity(miami);
    City montreal = new City("Montreal");
    City newyork = new City("New York");
    montreal.addAdjacentCity(chicago);
    montreal.addAdjacentCity(washington);
    montreal.addAdjacentCity(newyork);
    City london = new City("London");
    City madrid = new City("Madrid");
    newyork.addAdjacentCity(washington);
    newyork.addAdjacentCity(london);
    newyork.addAdjacentCity(madrid);
    washington.addAdjacentCity(miami);
    City sanFrancisco = new City("San Francisco");
    City mexicoCity = new City("Mexico City");
    City la = new City("Los Angeles");
    chicago.addAdjacentCity(mexicoCity);
    chicago.addAdjacentCity(sanFrancisco);
    chicago.addAdjacentCity(la);
    london.addAdjacentCity(madrid);
    City bogota = new City("Bogota");
    City lima = new City("Lima");
    mexicoCity.addAdjacentCity(la);
    mexicoCity.addAdjacentCity(miami);
    mexicoCity.addAdjacentCity(bogota);
    mexicoCity.addAdjacentCity(lima);
    miami.addAdjacentCity(bogota);
    City saopaulo = new City("Sao Paulo");
    bogota.addAdjacentCity(lima);
    bogota.addAdjacentCity(saopaulo);
    City santiago = new City("Santiago");
    lima.addAdjacentCity(santiago);
    saopaulo.addAdjacentCity(madrid);
    Map<String, City> cityMap = new HashMap<>();
    cityMap.put("Atlanta", altanta);
    cityMap.put("Chicago", chicago);
    cityMap.put("Montreal", montreal);
    cityMap.put("New York", newyork);
    cityMap.put("Washington", washington);
    cityMap.put("London", london);
    cityMap.put("San Francisco", sanFrancisco);
    cityMap.put("Miami", miami);
    cityMap.put("Mexico City", mexicoCity);
    cityMap.put("Los Angeles", la);
    cityMap.put("Bogota", bogota);
    cityMap.put("Lima", lima);
    cityMap.put("Santiago", santiago);
    cityMap.put("Sao Paulo", saopaulo);
    cityMap.put("Madrid", madrid);
    return cityMap;
  }
}
