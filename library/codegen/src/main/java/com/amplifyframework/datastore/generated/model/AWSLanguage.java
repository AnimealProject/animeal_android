package com.amplifyframework.datastore.generated.model;


import androidx.core.util.ObjectsCompat;

/** This is an auto generated class representing the AWSLanguage type in your schema. */
public final class AWSLanguage {
  private final String code;
  private final String name;
  public String getCode() {
      return code;
  }
  
  public String getName() {
      return name;
  }
  
  private AWSLanguage(String code, String name) {
    this.code = code;
    this.name = name;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      AWSLanguage awsLanguage = (AWSLanguage) obj;
      return ObjectsCompat.equals(getCode(), awsLanguage.getCode()) &&
              ObjectsCompat.equals(getName(), awsLanguage.getName());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getCode())
      .append(getName())
      .toString()
      .hashCode();
  }
  
  public static BuildStep builder() {
      return new Builder();
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(code,
      name);
  }
  public interface BuildStep {
    AWSLanguage build();
    BuildStep code(String code);
    BuildStep name(String name);
  }
  

  public static class Builder implements BuildStep {
    private String code;
    private String name;
    @Override
     public AWSLanguage build() {
        
        return new AWSLanguage(
          code,
          name);
    }
    
    @Override
     public BuildStep code(String code) {
        this.code = code;
        return this;
    }
    
    @Override
     public BuildStep name(String name) {
        this.name = name;
        return this;
    }
  }
  

  public final class CopyOfBuilder extends Builder {
    private CopyOfBuilder(String code, String name) {
      super.code(code)
        .name(name);
    }
    
    @Override
     public CopyOfBuilder code(String code) {
      return (CopyOfBuilder) super.code(code);
    }
    
    @Override
     public CopyOfBuilder name(String name) {
      return (CopyOfBuilder) super.name(name);
    }
  }
  
}
