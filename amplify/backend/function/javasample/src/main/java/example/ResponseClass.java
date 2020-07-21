package example;

public class ResponseClass {
  private Boolean processed;

  public Boolean getProcessed(){
    return this.processed;
  }

  public void setProcessed(Boolean processed) {
    this.processed = processed;
  }

  public ResponseClass(Boolean processed){
    this.processed = processed;
  }

  public ResponseClass(){
    
  }
}