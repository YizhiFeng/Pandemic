package initialization_test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import pandemic_initialization.CardModel;

public class CardModelTest {
  @Test
  public void testGetNameEmpty() {
    CardModel card = new CardModel();
    assertEquals("", card.getName());
  }

  @Test
  public void testSetName() {
    CardModel card = new CardModel();
    card.setName("Atlanta");
    assertEquals("Atlanta", card.getName());
  }

  @Test
  public void testGetTypeEmpty() {
    CardModel card = new CardModel();
    assertEquals(-1, card.getType());
  }

  @Test
  public void testSetType() {
    CardModel card = new CardModel();
    card.setType(2);
    assertEquals(2, card.getType());
  }

  @Test
  public void testGetColor() {
    CardModel card = new CardModel();
    assertEquals(-1, card.getColor());
  }

  @Test
  public void testSetColor() {
    CardModel card = new CardModel();
    card.setColor(0);
    assertEquals(0, card.getColor());
  }

  @Test
  public void testGetPopulation() {
    CardModel card = new CardModel();
    assertEquals(-1, card.getPopulation());
  }

  @Test
  public void testSetPopulation() {
    CardModel card = new CardModel();
    card.setPopulation(0);
    assertEquals(0, card.getPopulation());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testColorValidInputs() {
    CardModel card = new CardModel();
    card.setColor(5);
  }

  @Test
  public void testColorVailidInputMax() {
    CardModel card = new CardModel();
    card.setColor(4);
    assertEquals(4, card.getColor());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testColorValidInputsNegative() {
    CardModel card = new CardModel();
    card.setColor(-1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testTypeValidInputs() {
    CardModel card = new CardModel();
    card.setType(5);
  }

  @Test
  public void testTypeVailidInputMax() {
    CardModel card = new CardModel();
    card.setType(4);
    assertEquals(4, card.getType());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testTypeValidInputsMin() {
    CardModel card = new CardModel();
    card.setType(0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPopulationValidInputs() {
    CardModel card = new CardModel();
    card.setPopulation(-1);
  }
}