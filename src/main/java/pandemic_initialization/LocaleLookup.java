package pandemic_initialization;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;

import pandemic_exception.InitializationException;

public class LocaleLookup {

  private static ResourceBundle messages = ResourceBundle.getBundle("MessageBundle");

  private Map<String, Locale> locales = new HashMap<>();

  public LocaleLookup() {
    try {
      setupSupportedLocales();
    } catch (IOException except) {
      except.printStackTrace();
      locales.put("English", Locale.US);
    }
  }

  private void setupSupportedLocales() throws IOException {
    String[] files = findLanguageFilePaths();
    for (int i = 0; i < files.length; i++) {
      files[i] = parseOutLanguageCode(files[i]);
      addLocale(files[i]);
    }
  }

  private String[] findLanguageFilePaths() {
    File file = new File("./src/main/resources");
    File[] files = file.listFiles(new FilenameFilter() {
      public boolean accept(File dir, String name) {
        return name.startsWith("MessageBundle") && name.endsWith("properties");
      }
    });

    if (files == null) {
      throw new InitializationException(messages.getString("unableToLoadLangFiles"));
    }

    String[] paths = new String[files.length];
    for (int i = 0; i < paths.length; i++) {
      paths[i] = files[i].getPath();
    }

    return paths;
  }

  private String parseOutLanguageCode(String pathName) {
    if (pathName.contains("_")) {
      pathName = pathName.substring(pathName.indexOf("_") + 1, pathName.lastIndexOf('.'));
      return pathName;
    }
    return "en_us";
  }

  private void addLocale(String string) {
    String properLangCode = convertLangCode(string);
    Locale loc = Locale.forLanguageTag(properLangCode);
    String displayString = loc.getDisplayCountry(loc) + " " + loc.getDisplayLanguage(loc);
    locales.put(displayString, loc);
  }

  private String convertLangCode(String name) {
    return name.replace('_', '-');
  }

  public String[] getLocales() {
    String[] locs = new String[locales.size()];
    int index = 0;
    for (Entry<String, Locale> loc : locales.entrySet()) {
      locs[index] = loc.getKey();
      index++;
    }
    return locs;
  }

  public Locale getLocale(Object selectedItem) {
    return locales.get(selectedItem.toString());
  }

  public Locale getDefault() {
    return Locale.getDefault();
  }
}
