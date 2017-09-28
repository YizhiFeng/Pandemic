package pandemic_test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import pandemic_game.Card;
import pandemic_game.Constants.CardType;
import pandemic_game.Constants.CubeColor;

public class CardTest {
  @Test
  public void testCityName() {
    Card card = new Card(CardType.City, "Atlanta");
    assertEquals("Atlanta", card.getName());
  }

  @Test
  public void testDifferentCityNames() {
    Card atlanta = new Card(CardType.City, "Atlanta");
    Card chicago = new Card(CardType.City, "Chicago");
    assertEquals("Atlanta", atlanta.getName());
    assertEquals("Chicago", chicago.getName());
  }

  @Test
  public void testColor() {
    Card card = new Card(CardType.City, "Atlanta", CubeColor.BLUE);
    assertEquals(CubeColor.BLUE, card.getColor());
  }

  @Test
  public void testDifferentColors() {
    Card atlanta = new Card(CardType.City, "Atlanta", CubeColor.BLUE);
    Card beijing = new Card(CardType.City, "Beijing", CubeColor.RED);
    assertEquals(CubeColor.BLUE, atlanta.getColor());
    assertEquals(CubeColor.RED, beijing.getColor());
  }

  @Test
  public void testPopulation() {
    Card card = new Card(CardType.City, "Atlanta", CubeColor.BLUE, 1000000);
    assertEquals(1000000, card.getPopulation());
  }

  @Test
  public void testDifferentPopulations() {
    Card atlanta = new Card(CardType.City, "Atlanta", CubeColor.BLUE, 1000000);
    Card beijing = new Card(CardType.City, "Beijing", CubeColor.BLUE, 15000000);
    assertEquals(1000000, atlanta.getPopulation());
    assertEquals(15000000, beijing.getPopulation());
  }

  @Test
  public void testCreateCityCard() {
    Card cityCard = new Card(CardType.City, "atlanta", CubeColor.BLUE, 100);
    assertEquals(CardType.City, cityCard.getType());
    assertEquals("atlanta", cityCard.getName());
    assertEquals(CubeColor.BLUE, cityCard.getColor());
    assertEquals(100, cityCard.getPopulation());
  }

  @Test
  public void testCreateInfectionCard() {
    Card infectionCard = new Card(CardType.Infection, "atlanta", CubeColor.BLUE);
    assertEquals(CardType.Infection, infectionCard.getType());
    assertEquals("atlanta", infectionCard.getName());
    assertEquals(CubeColor.BLUE, infectionCard.getColor());
    assertEquals(0, infectionCard.getPopulation());
  }

  @Test
  public void testCreateEpidemicCard() {
    Card epidemicCard = new Card(CardType.Epidemic);
    assertEquals(CardType.Epidemic, epidemicCard.getType());
    assertEquals("", epidemicCard.getName());
    assertEquals(CubeColor.COLORLESS, epidemicCard.getColor());
    assertEquals(0, epidemicCard.getPopulation());
  }

  @Test
  public void testCreateEventCard() {
    Card eventCard = new Card(CardType.Event, "airlift");
    assertEquals(CardType.Event, eventCard.getType());
    assertEquals("airlift", eventCard.getName());
    assertEquals(CubeColor.COLORLESS, eventCard.getColor());
    assertEquals(0, eventCard.getPopulation());
  }

  @Test
  public void testEpidemicCardToString() {
    Card card = new Card(CardType.Epidemic);
    assertEquals("Epidemic\t\tCOLORLESS\t0", card.toString());
  }

  @Test
  public void testCityCardToString() {
    Card card = new Card(CardType.City, "city", CubeColor.BLACK, 10);
    assertEquals("City\tcity\tBLACK\t10", card.toString());
  }
}
