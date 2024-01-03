package study.coco;

import java.util.List;
import java.util.Map;

public class Response {
  private List<IResponseDecorator> decorators;
  private String targetScreen;

  public Response(Map<String, Object> response) {

  }
}
