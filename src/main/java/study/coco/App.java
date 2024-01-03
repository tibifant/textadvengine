package study.coco;

import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class App {

  public static void main(String[] args) {

    try {
      InputStream inputStream = new FileInputStream(new File("assets/example.yaml"));

      Yaml yaml = new Yaml();
      Map<String, Object> allScreens = yaml.load(inputStream);

      for (String key : allScreens.keySet()) {
        System.out.println(key);
        Map<String, Object> innerMap = (HashMap)allScreens.get(key);
        for (Object o : innerMap.keySet())
          System.out.println(allScreens.get(key));
      }
    } catch (Exception e) {
      System.out.println("Exception thrown: " + e.toString());
    }
  }
}
