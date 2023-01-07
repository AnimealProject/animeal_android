package com.amplifyframework.datastore.generated.model;


import androidx.core.util.ObjectsCompat;

import java.util.Objects;

/** This is an auto generated class representing the CategoryI18n type in your schema. */
public final class CategoryI18n {
  private final String locale;
  private final String name;
  public String getLocale() {
      return locale;
  }
  
  public String getName() {
      return name;
  }
  
  private CategoryI18n(String locale, String name) {
    this.locale = locale;
    this.name = name;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      CategoryI18n categoryI18n = (CategoryI18n) obj;
      return ObjectsCompat.equals(getLocale(), categoryI18n.getLocale()) &&
              ObjectsCompat.equals(getName(), categoryI18n.getName());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getLocale())
      .append(getName())
      .toString()
      .hashCode();
  }
  
  public static LocaleStep builder() {
      return new Builder();
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(locale,
      name);
  }
  public interface LocaleStep {
    BuildStep locale(String locale);
  }
  

  public interface BuildStep {
    CategoryI18n build();
    BuildStep name(String name);
  }
  

  public static class Builder implements LocaleStep, BuildStep {
    private String locale;
    private String name;
    @Override
     public CategoryI18n build() {
        
        return new CategoryI18n(
          locale,
          name);
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
  }
  

  public final class CopyOfBuilder extends Builder {
    private CopyOfBuilder(String locale, String name) {
      super.locale(locale)
        .name(name);
    }
    
    @Override
     public CopyOfBuilder locale(String locale) {
      return (CopyOfBuilder) super.locale(locale);
    }
    
    @Override
     public CopyOfBuilder name(String name) {
      return (CopyOfBuilder) super.name(name);
    }
  }
  
}
