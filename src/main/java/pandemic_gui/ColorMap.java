package pandemic_gui;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

public class ColorMap {

  private Map<String, Color> descriptionMap = new HashMap<String, Color>();
  private Map<Color, String> colorMap = new HashMap<Color, String>();

  public void add(String description, Color color) {
    descriptionMap.put(description, color);
    colorMap.put(color, description);
  }

  public String get(Color color) {
    Set<Color> colors = colorMap.keySet();
    Color foundColor = null;
    for (Color comparison : colors) {
      if (colorsEqual(comparison, color)) {
        foundColor = comparison;
      }
    }
    if (foundColor != null) {
      return colorMap.get(foundColor);
    }
    throw new NoSuchElementException("Color not found.");
  }

  public Color get(String description) {
    return descriptionMap.get(description);
  }

  public static boolean colorsEqual(Color color1, Color color2) {
    return (Math.abs(color1.getBlue() - color2.getBlue()) < 3)
        && (Math.abs(color1.getRed() - color2.getRed()) < 3)
        && (Math.abs(color1.getGreen() - color2.getGreen()) < 3);
  }

  public boolean contains(String cityName) {
    return descriptionMap.containsKey(cityName);
  }

}
