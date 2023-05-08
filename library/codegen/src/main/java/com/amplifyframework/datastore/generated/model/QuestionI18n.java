package com.amplifyframework.datastore.generated.model;


import androidx.core.util.ObjectsCompat;

import java.util.Objects;

/** This is an auto generated class representing the QuestionI18n type in your schema. */
public final class QuestionI18n {
  private final String locale;
  private final String value;
  private final String answer;
  public String getLocale() {
      return locale;
  }
  
  public String getValue() {
      return value;
  }
  
  public String getAnswer() {
      return answer;
  }
  
  private QuestionI18n(String locale, String value, String answer) {
    this.locale = locale;
    this.value = value;
    this.answer = answer;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      QuestionI18n questionI18n = (QuestionI18n) obj;
      return ObjectsCompat.equals(getLocale(), questionI18n.getLocale()) &&
              ObjectsCompat.equals(getValue(), questionI18n.getValue()) &&
              ObjectsCompat.equals(getAnswer(), questionI18n.getAnswer());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getLocale())
      .append(getValue())
      .append(getAnswer())
      .toString()
      .hashCode();
  }
  
  public static LocaleStep builder() {
      return new Builder();
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(locale,
      value,
      answer);
  }
  public interface LocaleStep {
    BuildStep locale(String locale);
  }
  

  public interface BuildStep {
    QuestionI18n build();
    BuildStep value(String value);
    BuildStep answer(String answer);
  }
  

  public static class Builder implements LocaleStep, BuildStep {
    private String locale;
    private String value;
    private String answer;
    @Override
     public QuestionI18n build() {
        
        return new QuestionI18n(
          locale,
          value,
          answer);
    }
    
    @Override
     public BuildStep locale(String locale) {
        Objects.requireNonNull(locale);
        this.locale = locale;
        return this;
    }
    
    @Override
     public BuildStep value(String value) {
        this.value = value;
        return this;
    }
    
    @Override
     public BuildStep answer(String answer) {
        this.answer = answer;
        return this;
    }
  }
  

  public final class CopyOfBuilder extends Builder {
    private CopyOfBuilder(String locale, String value, String answer) {
      super.locale(locale)
        .value(value)
        .answer(answer);
    }
    
    @Override
     public CopyOfBuilder locale(String locale) {
      return (CopyOfBuilder) super.locale(locale);
    }
    
    @Override
     public CopyOfBuilder value(String value) {
      return (CopyOfBuilder) super.value(value);
    }
    
    @Override
     public CopyOfBuilder answer(String answer) {
      return (CopyOfBuilder) super.answer(answer);
    }
  }
  
}
