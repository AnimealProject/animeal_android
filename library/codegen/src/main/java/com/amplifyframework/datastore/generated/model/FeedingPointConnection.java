package com.amplifyframework.datastore.generated.model;


import androidx.core.util.ObjectsCompat;

import java.util.List;
import java.util.Objects;

/** This is an auto generated class representing the FeedingPointConnection type in your schema. */
public final class FeedingPointConnection {
  private final List<FeedingPoint> items;
  private final Integer total;
  private final String nextToken;
  public List<FeedingPoint> getItems() {
      return items;
  }
  
  public Integer getTotal() {
      return total;
  }
  
  public String getNextToken() {
      return nextToken;
  }
  
  private FeedingPointConnection(List<FeedingPoint> items, Integer total, String nextToken) {
    this.items = items;
    this.total = total;
    this.nextToken = nextToken;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      FeedingPointConnection feedingPointConnection = (FeedingPointConnection) obj;
      return ObjectsCompat.equals(getItems(), feedingPointConnection.getItems()) &&
              ObjectsCompat.equals(getTotal(), feedingPointConnection.getTotal()) &&
              ObjectsCompat.equals(getNextToken(), feedingPointConnection.getNextToken());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getItems())
      .append(getTotal())
      .append(getNextToken())
      .toString()
      .hashCode();
  }
  
  public static ItemsStep builder() {
      return new Builder();
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(items,
      total,
      nextToken);
  }
  public interface ItemsStep {
    BuildStep items(List<FeedingPoint> items);
  }
  

  public interface BuildStep {
    FeedingPointConnection build();
    BuildStep total(Integer total);
    BuildStep nextToken(String nextToken);
  }
  

  public static class Builder implements ItemsStep, BuildStep {
    private List<FeedingPoint> items;
    private Integer total;
    private String nextToken;
    @Override
     public FeedingPointConnection build() {
        
        return new FeedingPointConnection(
          items,
          total,
          nextToken);
    }
    
    @Override
     public BuildStep items(List<FeedingPoint> items) {
        Objects.requireNonNull(items);
        this.items = items;
        return this;
    }
    
    @Override
     public BuildStep total(Integer total) {
        this.total = total;
        return this;
    }
    
    @Override
     public BuildStep nextToken(String nextToken) {
        this.nextToken = nextToken;
        return this;
    }
  }
  

  public final class CopyOfBuilder extends Builder {
    private CopyOfBuilder(List<FeedingPoint> items, Integer total, String nextToken) {
      super.items(items)
        .total(total)
        .nextToken(nextToken);
    }
    
    @Override
     public CopyOfBuilder items(List<FeedingPoint> items) {
      return (CopyOfBuilder) super.items(items);
    }
    
    @Override
     public CopyOfBuilder total(Integer total) {
      return (CopyOfBuilder) super.total(total);
    }
    
    @Override
     public CopyOfBuilder nextToken(String nextToken) {
      return (CopyOfBuilder) super.nextToken(nextToken);
    }
  }
  
}
