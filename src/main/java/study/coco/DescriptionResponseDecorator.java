package study.coco;

import java.util.List;

public class DescriptionResponseDecorator implements IResponseDecorator {
  private List<ColoredText> description;

  public DescriptionResponseDecorator(List<ColoredText> text) {
    description = text;
  }

  @Override
  public void onChoose(Engine engine) {

  }

  @Override
  public void onInit(Engine engine) {

  }
}
