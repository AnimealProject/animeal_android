package com.amplifyframework.datastore.generated.model;


import androidx.core.util.ObjectsCompat;

import java.util.Objects;

/** This is an auto generated class representing the MedicationI18n type in your schema. */
public final class MedicationI18n {
  private final String name;
  public String getName() {
      return name;
  }
  
  private MedicationI18n(String name) {
    this.name = name;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      MedicationI18n medicationI18n = (MedicationI18n) obj;
      return ObjectsCompat.equals(getName(), medicationI18n.getName());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getName())
      .toString()
      .hashCode();
  }
  
  public static NameStep builder() {
      return new Builder();
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(name);
  }
  public interface NameStep {
    BuildStep name(String name);
  }
  

  public interface BuildStep {
    MedicationI18n build();
  }
  

  public static class Builder implements NameStep, BuildStep {
    private String name;
    @Override
     public MedicationI18n build() {
        
        return new MedicationI18n(
          name);
    }
    
    @Override
     public BuildStep name(String name) {
        Objects.requireNonNull(name);
        this.name = name;
        return this;
    }
  }
  

  public final class CopyOfBuilder extends Builder {
    private CopyOfBuilder(String name) {
      super.name(name);
    }
    
    @Override
     public CopyOfBuilder name(String name) {
      return (CopyOfBuilder) super.name(name);
    }
  }
  
}
