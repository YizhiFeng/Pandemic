package pandemic_test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import pandemic_exception.InitializationException;
import pandemic_exception.InvalidActionException;
import pandemic_game.City;
import pandemic_game.Constants.CubeColor;

public class CityTest {

  @Test
  public void testToString() {
    City city = new City("Atlanta");
    assertEquals("Atlanta", city.toString());
  }

  @Test
  public void testCity() {
    City city = new City("Atlanta");
    assertEquals("Atlanta", city.getName());
  }

  @Test
  public void testDifferentNames() {
    City atlanta = new City("Atlanta");
    City chicago = new City("Chicago");
    assertEquals("Atlanta", atlanta.getName());
    assertEquals("Chicago", chicago.getName());
  }

  @Test
  public void testHasResearchStation() {
    City atlanta = new City("Atlanta");
    assertFalse(atlanta.hasResearchStation());
  }

  @Test
  public void testAddReseachStation() {
    City atlanta = new City("Atlanta");
    assertFalse(atlanta.hasResearchStation());
    atlanta.addResearchStation();
    assertTrue(atlanta.hasResearchStation());
  }

  @Test
  public void testRemoveResearchStation() {
    City atlanta = new City("Atlanta");
    assertFalse(atlanta.hasResearchStation());
    atlanta.addResearchStation();
    assertTrue(atlanta.hasResearchStation());
    atlanta.removeResearchStation();
    assertFalse(atlanta.hasResearchStation());
  }

  @Test(expected = InvalidActionException.class)
  public void testRemoveResearchStationNone() {
    City atlanta = new City("Atlanta");
    atlanta.removeResearchStation();
  }

  @Test
  public void testCubeCountNone() {
    City atlanta = new City("Atlanta");
    assertEquals(0, atlanta.getCubeCount(CubeColor.RED));
    assertEquals(0, atlanta.getCubeCount(CubeColor.YELLOW));
    assertEquals(0, atlanta.getCubeCount(CubeColor.BLUE));
    assertEquals(0, atlanta.getCubeCount(CubeColor.BLACK));
  }

  @Test
  public void testAddCubeOneColor() {
    City atlanta = new City("Atlanta");
    assertEquals(0, atlanta.getCubeCount(CubeColor.RED));
    atlanta.addCube(CubeColor.RED);
    assertEquals(1, atlanta.getCubeCount(CubeColor.RED));
    assertEquals(0, atlanta.getCubeCount(CubeColor.YELLOW));
    assertEquals(0, atlanta.getCubeCount(CubeColor.BLUE));
    assertEquals(0, atlanta.getCubeCount(CubeColor.BLACK));
  }

  @Test
  public void testAddCubeMultiColors() {
    City atlanta = new City("Atlanta");
    atlanta.addCube(CubeColor.RED);
    atlanta.addCube(CubeColor.YELLOW);
    atlanta.addCube(CubeColor.BLUE);
    atlanta.addCube(CubeColor.BLACK);
    assertEquals(1, atlanta.getCubeCount(CubeColor.RED));
    assertEquals(1, atlanta.getCubeCount(CubeColor.YELLOW));
    assertEquals(1, atlanta.getCubeCount(CubeColor.BLUE));
    assertEquals(1, atlanta.getCubeCount(CubeColor.BLACK));
  }

  @Test
  public void testRemoveCubeOneColor() {
    City atlanta = new City("Atlanta");
    assertEquals(0, atlanta.getCubeCount(CubeColor.RED));
    atlanta.addCube(CubeColor.RED);
    assertEquals(1, atlanta.getCubeCount(CubeColor.RED));
    atlanta.removeCube(CubeColor.RED);
    assertEquals(0, atlanta.getCubeCount(CubeColor.RED));
  }

  @Test
  public void testRemoveCubeMultiColors() {
    City atlanta = new City("Atlanta");
    atlanta.addCube(CubeColor.RED);
    assertEquals(1, atlanta.getCubeCount(CubeColor.RED));
    atlanta.addCube(CubeColor.YELLOW);
    assertEquals(1, atlanta.getCubeCount(CubeColor.YELLOW));
    atlanta.addCube(CubeColor.BLUE);
    assertEquals(1, atlanta.getCubeCount(CubeColor.BLUE));
    atlanta.addCube(CubeColor.BLACK);
    assertEquals(1, atlanta.getCubeCount(CubeColor.BLACK));
    atlanta.removeCube(CubeColor.RED);
    assertEquals(0, atlanta.getCubeCount(CubeColor.RED));
    atlanta.removeCube(CubeColor.YELLOW);
    assertEquals(0, atlanta.getCubeCount(CubeColor.YELLOW));
    atlanta.removeCube(CubeColor.BLUE);
    assertEquals(0, atlanta.getCubeCount(CubeColor.BLUE));
    atlanta.removeCube(CubeColor.BLACK);
    assertEquals(0, atlanta.getCubeCount(CubeColor.BLACK));
  }

  @Test(expected = InvalidActionException.class)
  public void testRemoveCubeNone() {
    City atlanta = new City("Atlanta");
    atlanta.removeCube(CubeColor.RED);
  }

  @Test(expected = InvalidActionException.class)
  public void testRemoveCubeYellowNone() {
    City atlanta = new City("Atlanta");
    atlanta.removeCube(CubeColor.YELLOW);
  }

  @Test(expected = InvalidActionException.class)
  public void testRemoveCubeBlackNone() {
    City atlanta = new City("Atlanta");
    atlanta.removeCube(CubeColor.BLACK);
  }

  @Test(expected = InvalidActionException.class)
  public void testRemoveCubeBlueNone() {
    City atlanta = new City("Atlanta");
    atlanta.removeCube(CubeColor.BLUE);
  }

  @Test
  public void testGetAdjacentCities() {
    City atlanta = new City("Atlanta");
    assertEquals(new ArrayList<City>(), atlanta.getAdjacentCities());
  }

  @Test
  public void testAddAdjacentCity() {
    City atlanta = new City("Atlanta");
    City chicago = new City("Chicago");
    atlanta.addAdjacentCity(chicago);
    List<City> expectedAdjacent = new ArrayList<>();
    expectedAdjacent.add(chicago);
    assertEquals(expectedAdjacent, atlanta.getAdjacentCities());
  }

  @Test
  public void testAddAdjacentCityMany() {
    City atlanta = new City("Atlanta");
    City chicago = new City("Chicago");
    City washington = new City("Washington");
    atlanta.addAdjacentCity(chicago);
    atlanta.addAdjacentCity(washington);
    List<City> expectedAdjacent = new ArrayList<>();
    expectedAdjacent.add(chicago);
    expectedAdjacent.add(washington);
    assertEquals(expectedAdjacent, atlanta.getAdjacentCities());
  }

  @Test(expected = InitializationException.class)
  public void testAddAdjacentCitySameName() {
    City atlanta = new City("Atlanta");
    atlanta.addAdjacentCity(atlanta);
  }

  @Test
  public void testAddAdjacentCitySimultaneously() {
    City atlanta = new City("Atlanta");
    City chicago = new City("Chicago");
    atlanta.addAdjacentCity(chicago);
    assertTrue(atlanta.getAdjacentCities().contains(chicago));
    assertTrue(chicago.getAdjacentCities().contains(atlanta));
  }

}
