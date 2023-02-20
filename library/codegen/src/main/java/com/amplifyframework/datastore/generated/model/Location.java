package com.amplifyframework.datastore.generated.model;


import androidx.core.util.ObjectsCompat;

import java.util.Objects;

/** This is an auto generated class representing the Location type in your schema. */
public final class Location {
  private final Double lat;
  private final Double lon;
  public Double getLat() {
      return lat;
  }
  
  public Double getLon() {
      return lon;
  }
  
  private Location(Double lat, Double lon) {
    this.lat = lat;
    this.lon = lon;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Location location = (Location) obj;
      return ObjectsCompat.equals(getLat(), location.getLat()) &&
              ObjectsCompat.equals(getLon(), location.getLon());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getLat())
      .append(getLon())
      .toString()
      .hashCode();
  }
  
  public static LatStep builder() {
      return new Builder();
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(lat,
      lon);
  }
  public interface LatStep {
    LonStep lat(Double lat);
  }
  

  public interface LonStep {
    BuildStep lon(Double lon);
  }
  

  public interface BuildStep {
    Location build();
  }
  

  public static class Builder implements LatStep, LonStep, BuildStep {
    private Double lat;
    private Double lon;
    @Override
     public Location build() {
        
        return new Location(
          lat,
          lon);
    }
    
    @Override
     public LonStep lat(Double lat) {
        Objects.requireNonNull(lat);
        this.lat = lat;
        return this;
    }
    
    @Override
     public BuildStep lon(Double lon) {
        Objects.requireNonNull(lon);
        this.lon = lon;
        return this;
    }
  }
  

  public final class CopyOfBuilder extends Builder {
    private CopyOfBuilder(Double lat, Double lon) {
      super.lat(lat)
        .lon(lon);
    }
    
    @Override
     public CopyOfBuilder lat(Double lat) {
      return (CopyOfBuilder) super.lat(lat);
    }
    
    @Override
     public CopyOfBuilder lon(Double lon) {
      return (CopyOfBuilder) super.lon(lon);
    }
  }
  
}
