package study.coco;

public class CmpStateVarResponseDecorator implements IResponseDecorator {
  private String stateVar;
  private int value;

  public CmpStateVarResponseDecorator(String stateVar, int value) {
    this.stateVar = stateVar;
    this.value = value;
  }

  @Override
  public void onChoose(Engine engine) {

  }

  @Override
  public boolean isValid(Engine engine) {
    return engine.getItemCount(stateVar) == value;
  }
}
