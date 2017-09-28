package initialization_test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import pandemic_initialization.CityModel;

public class CityModelTest {

  @Test
  public void testGetName() {
    CityModel cityModel = new CityModel();
    assertEquals("", cityModel.getName());
  }

  @Test
  public void testSetName() {
    CityModel cityModel = new CityModel();
    cityModel.setName("Atlanta");
    assertEquals("Atlanta", cityModel.getName());
  }

  @Test
  public void testGetColor() {
    CityModel cityModel = new CityModel();
    assertEquals(-1, cityModel.getColor());
  }

  @Test
  public void testSetColor() {
    CityModel cityModel = new CityModel();
    cityModel.setColor(0);
    assertEquals(0, cityModel.getColor());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetColorMax() {
    CityModel cityModel = new CityModel();
    cityModel.setColor(5);
  }

  @Test
  public void testSetColorValidMax() {
    CityModel cityModel = new CityModel();
    cityModel.setColor(4);
    assertEquals(4, cityModel.getColor());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetColorMin() {
    CityModel cityModel = new CityModel();
    cityModel.setColor(-1);
  }

  @Test
  public void testGetAdjacentCities() {
    CityModel cityModel = new CityModel();
    List<String> expected = Collections.emptyList();
    assertEquals(expected, cityModel.getAdjacent());
  }

  @Test
  public void testSetAdjacentCities() {
    CityModel cityModel = new CityModel();

    List<String> expected = new ArrayList<>();
    expected.add("Atlanta");
    expected.add("Chicago");

    cityModel.setAdjacent(expected);

    assertEquals(expected, cityModel.getAdjacent());
  }

}