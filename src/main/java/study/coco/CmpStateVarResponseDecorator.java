package study.coco;

public class CmpStateVarResponseDecorator implements IResponseDecorator {
  private String stateVar;
  private int cmpValue;

  public CmpStateVarResponseDecorator(String stateVar, int value) {
    this.stateVar = stateVar;
    this.cmpValue = value;
  }

  @Override
  public void onChoose(GameState engine) { }

  @Override
  public boolean isValid(GameState gameState) {
    return gameState.getItemCount(stateVar) == cmpValue;
  }
}
