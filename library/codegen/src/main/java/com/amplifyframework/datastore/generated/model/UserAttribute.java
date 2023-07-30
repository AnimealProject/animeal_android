package com.amplifyframework.datastore.generated.model;


import androidx.core.util.ObjectsCompat;

import java.util.Objects;

/** This is an auto generated class representing the UserAttribute type in your schema. */
public final class UserAttribute {
  private final String Name;
  private final String Value;
  public String getName() {
      return Name;
  }
  
  public String getValue() {
      return Value;
  }
  
  private UserAttribute(String Name, String Value) {
    this.Name = Name;
    this.Value = Value;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      UserAttribute userAttribute = (UserAttribute) obj;
      return ObjectsCompat.equals(getName(), userAttribute.getName()) &&
              ObjectsCompat.equals(getValue(), userAttribute.getValue());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getName())
      .append(getValue())
      .toString()
      .hashCode();
  }
  
  public static NameStep builder() {
      return new Builder();
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(Name,
      Value);
  }
  public interface NameStep {
    BuildStep name(String name);
  }
  

  public interface BuildStep {
    UserAttribute build();
    BuildStep value(String value);
  }
  

  public static class Builder implements NameStep, BuildStep {
    private String Name;
    private String Value;
    @Override
     public UserAttribute build() {
        
        return new UserAttribute(
          Name,
          Value);
    }
    
    @Override
     public BuildStep name(String name) {
        Objects.requireNonNull(name);
        this.Name = name;
        return this;
    }
    
    @Override
     public BuildStep value(String value) {
        this.Value = value;
        return this;
    }
  }
  

  public final class CopyOfBuilder extends Builder {
    private CopyOfBuilder(String name, String value) {
      super.name(name)
        .value(value);
    }
    
    @Override
     public CopyOfBuilder name(String name) {
      return (CopyOfBuilder) super.name(name);
    }
    
    @Override
     public CopyOfBuilder value(String value) {
      return (CopyOfBuilder) super.value(value);
    }
  }
  
}
