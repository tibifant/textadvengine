package study.coco;

public interface IResponseDecorator {
  public boolean isValid(Engine engine);
  public void onChoose(Engine engine);
}
