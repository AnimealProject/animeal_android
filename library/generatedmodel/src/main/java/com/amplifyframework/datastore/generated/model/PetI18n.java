package com.amplifyframework.datastore.generated.model;


import androidx.core.util.ObjectsCompat;

import java.util.Objects;

/** This is an auto generated class representing the PetI18n type in your schema. */
public final class PetI18n {
  private final String locale;
  private final String name;
  private final String breed;
  private final String color;
  public String getLocale() {
      return locale;
  }
  
  public String getName() {
      return name;
  }
  
  public String getBreed() {
      return breed;
  }
  
  public String getColor() {
      return color;
  }
  
  private PetI18n(String locale, String name, String breed, String color) {
    this.locale = locale;
    this.name = name;
    this.breed = breed;
    this.color = color;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      PetI18n petI18n = (PetI18n) obj;
      return ObjectsCompat.equals(getLocale(), petI18n.getLocale()) &&
              ObjectsCompat.equals(getName(), petI18n.getName()) &&
              ObjectsCompat.equals(getBreed(), petI18n.getBreed()) &&
              ObjectsCompat.equals(getColor(), petI18n.getColor());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getLocale())
      .append(getName())
      .append(getBreed())
      .append(getColor())
      .toString()
      .hashCode();
  }
  
  public static LocaleStep builder() {
      return new Builder();
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(locale,
      name,
      breed,
      color);
  }
  public interface LocaleStep {
    BuildStep locale(String locale);
  }
  

  public interface BuildStep {
    PetI18n build();
    BuildStep name(String name);
    BuildStep breed(String breed);
    BuildStep color(String color);
  }
  

  public static class Builder implements LocaleStep, BuildStep {
    private String locale;
    private String name;
    private String breed;
    private String color;
    @Override
     public PetI18n build() {
        
        return new PetI18n(
          locale,
          name,
          breed,
          color);
    }
    
    @Override
     public BuildStep locale(String locale) {
        Objects.requireNonNull(locale);
        this.locale = locale;
        return this;
    }
    
    @Override
     public BuildStep name(String name) {
        this.name = name;
        return this;
    }
    
    @Override
     public BuildStep breed(String breed) {
        this.breed = breed;
        return this;
    }
    
    @Override
     public BuildStep color(String color) {
        this.color = color;
        return this;
    }
  }
  

  public final class CopyOfBuilder extends Builder {
    private CopyOfBuilder(String locale, String name, String breed, String color) {
      super.locale(locale)
        .name(name)
        .breed(breed)
        .color(color);
    }
    
    @Override
     public CopyOfBuilder locale(String locale) {
      return (CopyOfBuilder) super.locale(locale);
    }
    
    @Override
     public CopyOfBuilder name(String name) {
      return (CopyOfBuilder) super.name(name);
    }
    
    @Override
     public CopyOfBuilder breed(String breed) {
      return (CopyOfBuilder) super.breed(breed);
    }
    
    @Override
     public CopyOfBuilder color(String color) {
      return (CopyOfBuilder) super.color(color);
    }
  }
  
}
