package example;

// import java.util.ArrayList;
// import java.util.List;
import java.util.HashMap;

public class RequestClass {
  String key = "";
  HashMap<String,String> metadatas = new HashMap<String,String>();

  public String getKey(){
    return this.key;
  }

  public void setKey(String key){
    this.key = key;
  }

  public HashMap<String,String> getMetadatas(){
    return this.metadatas;
  }

  public void setMetadatas(HashMap<String,String> metadatas){
    this.metadatas = metadatas;
  }

  public RequestClass(String key, HashMap<String,String> metadatas) {
    this.key = key;
    this.metadatas = metadatas;
}

  public RequestClass() {
  }


}