package pandemic_initialization;

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
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import pandemic_exception.InitializationException;
import pandemic_game.Card;
import pandemic_game.Constants.CardType;
import pandemic_game.Constants.CubeColor;

public class CardBuilder {

  private static ResourceBundle messages = ResourceBundle.getBundle("MessageBundle");

  public List<Card> loadPandemicCards(BufferedReader reader) {
    try {
      String jsonString = readJsonString(reader);
      return createPandemicCards(jsonString);
    } catch (IOException e) {
      throw new InitializationException(messages.getString("unableToLoadCardsException"));
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

  private List<Card> createPandemicCards(String jsonString) {
    List<CardModel> models = createCardModels(jsonString);
    List<Card> cards = new ArrayList<Card>();
    for (CardModel model : models) {
      cards.add(createCardFromModel(model));
    }
    return cards;
  }

  private List<CardModel> createCardModels(String jsonString) {
    Type type = new TypeToken<List<CardModel>>() {}.getType();
    GsonBuilder builder = new GsonBuilder();
    List<CardModel> output = builder.create().fromJson(jsonString, type);
    return output;
  }

  public Card createCardFromModel(CardModel model) {
    CubeColor color = convertToCubeColor(model.getColor());
    CardType type = convertToCardType(model.getType());
    return new Card(type, model.getName(), color, model.getPopulation());
  }

  private CardType convertToCardType(int num) {
    CardType type = null;
    switch (num) {
      case (2):
        type = Event;
        break;
      case (3):
        type = Infection;
        break;
      case (4):
        type = City;
        break;
      default:
        throw new IllegalArgumentException(messages.getString("badCardTypeNumberException"));
    }
    return type;
  }

  private CubeColor convertToCubeColor(int num) {
    CubeColor color = null;
    switch (num) {
      case (0):
        color = COLORLESS;
        break;
      case (1):
        color = YELLOW;
        break;
      case (2):
        color = RED;
        break;
      case (3):
        color = BLUE;
        break;
      case (4):
        color = BLACK;
        break;
      default:
        throw new IllegalArgumentException(messages.getString("badCardColorNumberException"));
    }
    return color;
  }

}