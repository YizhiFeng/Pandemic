package pandemic_game;

import pandemic_game.Constants.CardType;
import pandemic_game.Constants.CubeColor;

public class Card {
  
  private String name;
  private CubeColor color;
  private int population;
  private CardType type;

  public Card(CardType type, String name, CubeColor color, int population) {
    this.name = name;
    this.color = color;
    this.population = population;
    this.type = type;
  }

  public Card(CardType type, String name, CubeColor color) {
    this(type, name, color, 0);
  }

  public Card(CardType type, String name) {
    this(type, name, CubeColor.COLORLESS);
  }

  public Card(CardType type) {
    this(type, "");
  }

  public String getName() {
    return this.name;
  }

  public CubeColor getColor() {
    return this.color;
  }

  public int getPopulation() {
    return this.population;
  }

  public CardType getType() {
    return this.type;
  }

  @Override
  public String toString() {
    return type + "\t" + name + "\t" + color + "\t" + population;
  }

}
