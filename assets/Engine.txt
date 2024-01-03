public class Engine 
{
  private HashMap<String, int> stateVars;
  private HashMap<String, Item> items;
  private ScreenFactory screenFactory;
  private Screen activeScreen;

  public void loadScreen(String screenName) 
  {
    // Handle meta-screens that automatically forward to the next screen without user input.
    while (true)
    {
      try {
        activeScreen = screenFactory.loadScreen(this, screenName);
        break;
      } catch (StateTransitionException e) {
        screenName = e.getTargetScreen();
      }
    }

    clearScreen();
    activeScreen.print();
  }

  public void update()
  {
    while (true) {
      try {
        activeScreen.submitResponse(readResponse, this);
      }
      catch (ScreenTransitionException e) {
        loadScreen(e.getTargetScreen());
      }
      catch (InvalidResponseException e) {
        printErrorMessage();
      }
    }
  }
}

public class ScreenFactory
{
  private Screen assembleScreenFromMap(String screenName, Map<String, Object> parsedScreen)
  {

  }

  private Screen loadScreen(Enigne engine, String screenName)
  {
    // load file, parse file
    // return the new screen.
  }
}

public enum Color
{
  White,
  Red,
  Blue,
}

public class ColoredText
{
  private String text;
  private Color color;

  static void print(String text, Color color);
}

public class Screen
{
  List<ColoredText> text;
  List<Response> validResponses;

  public void print()
  {
    for (ColoredText c : text)
      c.print();

    ColoredText.print("\n\n", Color.White);

    for (Response r : validResponses)
      r.print();
  }

  private void submitResponse(String response, Engine engine)
  {
    boolean anyMatched = false;

    for (Response r : validResponses) {
      if (r.keywordMatches(response)) {
        anyMatched = true;
        r.performAction(engine);
        throw new StateTransitionException(r.targetScreen);
      }
    }

    if (!anyMatched)
      throw new InvalidResponseException();
  }
}

public interface IResponseDecorator
{
  public void onInit(Engine engine);
  public void onChoose(Engine engine);
}

public class Response
{
  private List<IResponseDecorator> decorators;
  private String targetScreen;

  public boolean keywordMatches(String keyword);
  
  public void initializeDecorators(Engine engine) throws OptionInvalidatedException { // gets called by screenFactory for every new Screen
    for (IResponseDecorator rd : decorators) {
      try {
        rd.onInit(engine); // deactivtes decorators
      } catch (ResponseChosenException e) {
        throw new ScreenTransitionException(targetScreen);
      }
    }
  }

  public void performAction(Engine engine) {
    for (IResponseDecorator rd : decorators)
      rd.onChoose(engine);
  }

  public void print(String text) {}
}