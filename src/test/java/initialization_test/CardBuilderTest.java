package initialization_test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static pandemic_game.Constants.CardType.City;
import static pandemic_game.Constants.CardType.Event;
import static pandemic_game.Constants.CardType.Infection;
import static pandemic_game.Constants.CubeColor.BLACK;
import static pandemic_game.Constants.CubeColor.BLUE;
import static pandemic_game.Constants.CubeColor.COLORLESS;
import static pandemic_game.Constants.CubeColor.RED;
import static pandemic_game.Constants.CubeColor.YELLOW;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.Test;

import pandemic_exception.InitializationException;
import pandemic_game.Card;
import pandemic_initialization.CardBuilder;
import pandemic_initialization.CardModel;

public class CardBuilderTest {
  @Test
  public void testCreateCityCardfromCardModel() {
    CardModel model = EasyMock.mock(CardModel.class);
    EasyMock.expect(model.getColor()).andReturn(4);
    EasyMock.expect(model.getPopulation()).andReturn(5000000);
    EasyMock.expect(model.getType()).andReturn(4);
    EasyMock.expect(model.getName()).andReturn("Fake City");
    EasyMock.replay(model);
    CardBuilder builder = new CardBuilder();
    Card result = builder.createCardFromModel(model);
    assertEquals(BLACK, result.getColor());
    assertEquals(City, result.getType());
    assertEquals(5000000, result.getPopulation());
    assertEquals("Fake City", result.getName());
    EasyMock.verify(model);
  }

  @Test
  public void testCreateInfectionCardfromCardModel() {
    CardModel model = EasyMock.mock(CardModel.class);
    EasyMock.expect(model.getColor()).andReturn(3);
    EasyMock.expect(model.getPopulation()).andReturn(0);
    EasyMock.expect(model.getType()).andReturn(3);
    EasyMock.expect(model.getName()).andReturn("Fake City");
    EasyMock.replay(model);
    CardBuilder builder = new CardBuilder();
    Card result = builder.createCardFromModel(model);
    assertEquals(BLUE, result.getColor());
    assertEquals(Infection, result.getType());
    assertEquals(0, result.getPopulation());
    assertEquals("Fake City", result.getName());
    EasyMock.verify(model);
  }

  @Test
  public void testCreateEventCardfromCardModel() {
    CardModel model = EasyMock.mock(CardModel.class);
    EasyMock.expect(model.getColor()).andReturn(0);
    EasyMock.expect(model.getPopulation()).andReturn(0);
    EasyMock.expect(model.getType()).andReturn(2);
    EasyMock.expect(model.getName()).andReturn("Air Lift");
    EasyMock.replay(model);
    CardBuilder builder = new CardBuilder();
    Card result = builder.createCardFromModel(model);
    assertEquals(COLORLESS, result.getColor());
    assertEquals(Event, result.getType());
    assertEquals(0, result.getPopulation());
    assertEquals("Air Lift", result.getName());
    EasyMock.verify(model);
  }

  @Test
  public void testCreateRedCityCardfromCardModel() {
    CardModel model = EasyMock.mock(CardModel.class);
    EasyMock.expect(model.getColor()).andReturn(2);
    EasyMock.expect(model.getPopulation()).andReturn(10000);
    EasyMock.expect(model.getType()).andReturn(4);
    EasyMock.expect(model.getName()).andReturn("Fake City");
    EasyMock.replay(model);
    CardBuilder builder = new CardBuilder();
    Card result = builder.createCardFromModel(model);
    assertEquals(RED, result.getColor());
    assertEquals(City, result.getType());
    assertEquals(10000, result.getPopulation());
    assertEquals("Fake City", result.getName());
    EasyMock.verify(model);
  }

  @Test
  public void testCreateYellowInfectionCardfromCardModel() {
    CardModel model = EasyMock.mock(CardModel.class);
    EasyMock.expect(model.getColor()).andReturn(1);
    EasyMock.expect(model.getPopulation()).andReturn(0);
    EasyMock.expect(model.getType()).andReturn(3);
    EasyMock.expect(model.getName()).andReturn("Fake City");
    EasyMock.replay(model);

    CardBuilder builder = new CardBuilder();
    Card result = builder.createCardFromModel(model);
    assertEquals(YELLOW, result.getColor());
    assertEquals(Infection, result.getType());
    assertEquals(0, result.getPopulation());
    assertEquals("Fake City", result.getName());
    EasyMock.verify(model);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCreateCardfromBadModelBadType() {
    CardModel model = EasyMock.mock(CardModel.class);
    EasyMock.expect(model.getColor()).andReturn(1);
    EasyMock.expect(model.getPopulation()).andReturn(0);
    EasyMock.expect(model.getType()).andReturn(40);
    EasyMock.expect(model.getName()).andReturn("Fake City");
    EasyMock.replay(model);
    CardBuilder builder = new CardBuilder();
    builder.createCardFromModel(model);
    EasyMock.verify(model);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCreateCardfromBadModelBadColor() {
    CardModel model = EasyMock.mock(CardModel.class);
    EasyMock.expect(model.getColor()).andReturn(100);
    EasyMock.expect(model.getPopulation()).andReturn(0);
    EasyMock.expect(model.getType()).andReturn(3);
    EasyMock.expect(model.getName()).andReturn("Fake City");
    EasyMock.replay(model);
    CardBuilder builder = new CardBuilder();
    builder.createCardFromModel(model);
    EasyMock.verify(model);
  }

  @Test(expected = InitializationException.class)
  public void testLoadingPandemicCardsError() {
    BufferedReader reader = EasyMock.mock(BufferedReader.class);
    try {
      EasyMock.expect(reader.readLine()).andThrow(new IOException());
    } catch (IOException e) {
      fail();
    }
    EasyMock.replay(reader);

    CardBuilder builder = new CardBuilder();
    builder.loadPandemicCards(reader);
  }

  @Test
  public void testLoadingPandemicCardsOneCard() {
    BufferedReader reader = EasyMock.mock(BufferedReader.class);
    try {
      EasyMock.expect(reader.readLine()).andReturn(
          "[{ \"name\" : \"Washington\", \"type\" : 3, " + "\"color\" : 3, \"population\" : 0 }]");
      EasyMock.expect(reader.readLine()).andReturn(null);
      reader.close();
    } catch (IOException e) {
      fail();
    }
    EasyMock.replay(reader);

    CardBuilder builder = new CardBuilder();
    List<Card> cards = builder.loadPandemicCards(reader);
    Card testCard = cards.get(0);

    assertEquals(BLUE, testCard.getColor());
    assertEquals("Washington", testCard.getName());
    assertEquals(Infection, testCard.getType());
    assertEquals(0, testCard.getPopulation());
    EasyMock.verify(reader);
  }
}