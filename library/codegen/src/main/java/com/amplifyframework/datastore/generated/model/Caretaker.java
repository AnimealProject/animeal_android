package com.amplifyframework.datastore.generated.model;


import androidx.core.util.ObjectsCompat;

/** This is an auto generated class representing the Caretaker type in your schema. */
public final class Caretaker {
  private final String fullName;
  private final String email;
  private final String phoneNumber;
  public String getFullName() {
      return fullName;
  }
  
  public String getEmail() {
      return email;
  }
  
  public String getPhoneNumber() {
      return phoneNumber;
  }
  
  private Caretaker(String fullName, String email, String phoneNumber) {
    this.fullName = fullName;
    this.email = email;
    this.phoneNumber = phoneNumber;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Caretaker caretaker = (Caretaker) obj;
      return ObjectsCompat.equals(getFullName(), caretaker.getFullName()) &&
              ObjectsCompat.equals(getEmail(), caretaker.getEmail()) &&
              ObjectsCompat.equals(getPhoneNumber(), caretaker.getPhoneNumber());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getFullName())
      .append(getEmail())
      .append(getPhoneNumber())
      .toString()
      .hashCode();
  }
  
  public static BuildStep builder() {
      return new Builder();
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(fullName,
      email,
      phoneNumber);
  }
  public interface BuildStep {
    Caretaker build();
    BuildStep fullName(String fullName);
    BuildStep email(String email);
    BuildStep phoneNumber(String phoneNumber);
  }
  

  public static class Builder implements BuildStep {
    private String fullName;
    private String email;
    private String phoneNumber;
    @Override
     public Caretaker build() {
        
        return new Caretaker(
          fullName,
          email,
          phoneNumber);
    }
    
    @Override
     public BuildStep fullName(String fullName) {
        this.fullName = fullName;
        return this;
    }
    
    @Override
     public BuildStep email(String email) {
        this.email = email;
        return this;
    }
    
    @Override
     public BuildStep phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }
  }
  

  public final class CopyOfBuilder extends Builder {
    private CopyOfBuilder(String fullName, String email, String phoneNumber) {
      super.fullName(fullName)
        .email(email)
        .phoneNumber(phoneNumber);
    }
    
    @Override
     public CopyOfBuilder fullName(String fullName) {
      return (CopyOfBuilder) super.fullName(fullName);
    }
    
    @Override
     public CopyOfBuilder email(String email) {
      return (CopyOfBuilder) super.email(email);
    }
    
    @Override
     public CopyOfBuilder phoneNumber(String phoneNumber) {
      return (CopyOfBuilder) super.phoneNumber(phoneNumber);
    }
  }
  
}
