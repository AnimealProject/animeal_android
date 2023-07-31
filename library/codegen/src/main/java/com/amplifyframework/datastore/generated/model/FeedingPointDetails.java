package com.amplifyframework.datastore.generated.model;


import androidx.core.util.ObjectsCompat;

import java.util.Objects;

/** This is an auto generated class representing the FeedingPointDetails type in your schema. */
public final class FeedingPointDetails {
  private final String address;
  public String getAddress() {
      return address;
  }
  
  private FeedingPointDetails(String address) {
    this.address = address;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      FeedingPointDetails feedingPointDetails = (FeedingPointDetails) obj;
      return ObjectsCompat.equals(getAddress(), feedingPointDetails.getAddress());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getAddress())
      .toString()
      .hashCode();
  }
  
  public static AddressStep builder() {
      return new Builder();
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(address);
  }
  public interface AddressStep {
    BuildStep address(String address);
  }
  

  public interface BuildStep {
    FeedingPointDetails build();
  }
  

  public static class Builder implements AddressStep, BuildStep {
    private String address;
    @Override
     public FeedingPointDetails build() {
        
        return new FeedingPointDetails(
          address);
    }
    
    @Override
     public BuildStep address(String address) {
        Objects.requireNonNull(address);
        this.address = address;
        return this;
    }
  }
  

  public final class CopyOfBuilder extends Builder {
    private CopyOfBuilder(String address) {
      super.address(address);
    }
    
    @Override
     public CopyOfBuilder address(String address) {
      return (CopyOfBuilder) super.address(address);
    }
  }
  
}
