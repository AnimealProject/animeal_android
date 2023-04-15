package com.amplifyframework.datastore.generated.model;


import androidx.core.util.ObjectsCompat;

import java.util.List;
import java.util.Objects;

/** This is an auto generated class representing the Point type in your schema. */
public final class Point {
  private final String type;
  private final List<Double> coordinates;
  public String getType() {
      return type;
  }
  
  public List<Double> getCoordinates() {
      return coordinates;
  }
  
  private Point(String type, List<Double> coordinates) {
    this.type = type;
    this.coordinates = coordinates;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Point point = (Point) obj;
      return ObjectsCompat.equals(getType(), point.getType()) &&
              ObjectsCompat.equals(getCoordinates(), point.getCoordinates());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getType())
      .append(getCoordinates())
      .toString()
      .hashCode();
  }
  
  public static TypeStep builder() {
      return new Builder();
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(type,
      coordinates);
  }
  public interface TypeStep {
    CoordinatesStep type(String type);
  }
  

  public interface CoordinatesStep {
    BuildStep coordinates(List<Double> coordinates);
  }
  

  public interface BuildStep {
    Point build();
  }
  

  public static class Builder implements TypeStep, CoordinatesStep, BuildStep {
    private String type;
    private List<Double> coordinates;
    @Override
     public Point build() {
        
        return new Point(
          type,
          coordinates);
    }
    
    @Override
     public CoordinatesStep type(String type) {
        Objects.requireNonNull(type);
        this.type = type;
        return this;
    }
    
    @Override
     public BuildStep coordinates(List<Double> coordinates) {
        Objects.requireNonNull(coordinates);
        this.coordinates = coordinates;
        return this;
    }
  }
  

  public final class CopyOfBuilder extends Builder {
    private CopyOfBuilder(String type, List<Double> coordinates) {
      super.type(type)
        .coordinates(coordinates);
    }
    
    @Override
     public CopyOfBuilder type(String type) {
      return (CopyOfBuilder) super.type(type);
    }
    
    @Override
     public CopyOfBuilder coordinates(List<Double> coordinates) {
      return (CopyOfBuilder) super.coordinates(coordinates);
    }
  }
  
}
