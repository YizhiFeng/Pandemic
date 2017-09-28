package pandemic_gui;

import java.awt.Color;

public class ColorMapper {

  private static final ColorMap colorMap = new ColorMap();

  static {
    colorMap.add("InfectionDeck", new Color(155, 255, 255));
    colorMap.add("InfectionDiscard", new Color(155, 155, 255));

    colorMap.add("PlayerDeck", new Color(100, 75, 75));
    colorMap.add("PlayerDiscard", new Color(100, 75, 100));

    colorMap.add("InfectionRate1", new Color(155, 24, 180));
    colorMap.add("InfectionRate2", new Color(200, 155, 0));
    colorMap.add("InfectionRate3", new Color(200, 200, 155));
    colorMap.add("InfectionRate4", new Color(200, 200, 20));
    colorMap.add("InfectionRate5", new Color(200, 200, 100));
    colorMap.add("InfectionRate6", new Color(200, 100, 0));
    colorMap.add("InfectionRate7", new Color(100, 100, 225));

    colorMap.add("outbreak0", new Color(50, 100, 100));
    colorMap.add("outbreak1", new Color(50, 50, 100));
    colorMap.add("outbreak2", new Color(210, 222, 38));
    colorMap.add("outbreak3", new Color(50, 100, 50));
    colorMap.add("outbreak4", new Color(100, 50, 50));
    colorMap.add("outbreak5", new Color(100, 50, 100));
    colorMap.add("outbreak6", new Color(100, 100, 50));
    colorMap.add("outbreak7", new Color(218, 125, 179));
    colorMap.add("outbreak8", new Color(2, 85, 164));

    colorMap.add("CureYELLOW", new Color(100, 100, 75));
    colorMap.add("CureRED", new Color(75, 75, 100));
    colorMap.add("CureBLUE", new Color(75, 100, 100));
    colorMap.add("CureBLACK", new Color(75, 100, 75));

    colorMap.add("Atlanta", new Color(25, 125, 25));
    colorMap.add("Chicago", new Color(25, 50, 25));
    colorMap.add("San Francisco", new Color(25, 25, 25));
    colorMap.add("Montreal", new Color(25, 75, 25));
    colorMap.add("New York", new Color(25, 100, 25));
    colorMap.add("Washington", new Color(25, 150, 25));
    colorMap.add("Los Angeles", new Color(25, 175, 25));
    colorMap.add("Mexico City", new Color(25, 200, 25));
    colorMap.add("Miami", new Color(50, 25, 25));
    colorMap.add("Bogota", new Color(75, 25, 25));
    colorMap.add("Lima", new Color(100, 25, 25));
    colorMap.add("Santiago", new Color(125, 25, 25));
    colorMap.add("Buenos Aires", new Color(150, 25, 25));
    colorMap.add("Sao Paulo", new Color(175, 25, 25));
    colorMap.add("London", new Color(200, 25, 25));
    colorMap.add("Madrid", new Color(25, 25, 50));
    colorMap.add("Essen", new Color(25, 25, 75));
    colorMap.add("Paris", new Color(25, 25, 100));
    colorMap.add("St. Petersburg", new Color(25, 25, 125));
    colorMap.add("Milan", new Color(25, 25, 150));
    colorMap.add("Algiers", new Color(25, 25, 175));
    colorMap.add("Lagos", new Color(25, 25, 200));
    colorMap.add("Kinshasa", new Color(115, 0, 115));
    colorMap.add("Johannesburg", new Color(115, 20, 115));
    colorMap.add("Khartoum", new Color(115, 40, 115));
    colorMap.add("Cairo", new Color(115, 60, 115));
    colorMap.add("Istanbul", new Color(115, 80, 115));
    colorMap.add("Moscow", new Color(115, 100, 115));
    colorMap.add("Tehran", new Color(115, 120, 115));
    colorMap.add("Baghdad", new Color(255, 242, 151));
    colorMap.add("Riyadh", new Color(115, 160, 0));
    colorMap.add("Karachi", new Color(115, 180, 115));
    colorMap.add("Delhi", new Color(115, 200, 115));
    colorMap.add("Mumbai", new Color(130, 10, 130));
    colorMap.add("Chennai", new Color(0, 30, 130));
    colorMap.add("Kolkata", new Color(15, 225, 130));
    colorMap.add("Beijing", new Color(247, 145, 30));
    colorMap.add("Seoul", new Color(66, 20, 66));
    colorMap.add("Shanghai", new Color(66, 45, 66));
    colorMap.add("Tokyo", new Color(66, 78, 66));
    colorMap.add("Osaka", new Color(148, 110, 66));
    colorMap.add("Taipei", new Color(66, 160, 66));
    colorMap.add("Hong Kong", new Color(42, 42, 0));
    colorMap.add("Bangkok", new Color(42, 42, 190));
    colorMap.add("Jakarta", new Color(42, 42, 150));
    colorMap.add("Ho Chi Minh City", new Color(42, 42, 80));
    colorMap.add("Manila", new Color(63, 36, 225));
    colorMap.add("Sydney", new Color(63, 95, 6));
  }

  public static boolean colorsEqual(Color color1, Color color2) {
    return ColorMap.colorsEqual(color1, color2);
  }

  public static Color get(String description) {
    if (colorMap.contains(description)) {
      return colorMap.get(description);
    }
    return colorMap.get("outbreak8");
  }

  public static String get(Color color) {
    return colorMap.get(color);
  }

}
