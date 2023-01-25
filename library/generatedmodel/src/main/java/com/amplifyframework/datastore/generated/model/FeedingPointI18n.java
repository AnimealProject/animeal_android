package com.amplifyframework.datastore.generated.model;


import androidx.core.util.ObjectsCompat;

import java.util.Objects;

/** This is an auto generated class representing the FeedingPointI18n type in your schema. */
public final class FeedingPointI18n {
  private final String locale;
  private final String name;
  private final String description;
  private final String city;
  private final String street;
  private final String address;
  private final String region;
  private final String neighborhood;
  public String getLocale() {
      return locale;
  }
  
  public String getName() {
      return name;
  }
  
  public String getDescription() {
      return description;
  }
  
  public String getCity() {
      return city;
  }
  
  public String getStreet() {
      return street;
  }
  
  public String getAddress() {
      return address;
  }
  
  public String getRegion() {
      return region;
  }
  
  public String getNeighborhood() {
      return neighborhood;
  }
  
  private FeedingPointI18n(String locale, String name, String description, String city, String street, String address, String region, String neighborhood) {
    this.locale = locale;
    this.name = name;
    this.description = description;
    this.city = city;
    this.street = street;
    this.address = address;
    this.region = region;
    this.neighborhood = neighborhood;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      FeedingPointI18n feedingPointI18n = (FeedingPointI18n) obj;
      return ObjectsCompat.equals(getLocale(), feedingPointI18n.getLocale()) &&
              ObjectsCompat.equals(getName(), feedingPointI18n.getName()) &&
              ObjectsCompat.equals(getDescription(), feedingPointI18n.getDescription()) &&
              ObjectsCompat.equals(getCity(), feedingPointI18n.getCity()) &&
              ObjectsCompat.equals(getStreet(), feedingPointI18n.getStreet()) &&
              ObjectsCompat.equals(getAddress(), feedingPointI18n.getAddress()) &&
              ObjectsCompat.equals(getRegion(), feedingPointI18n.getRegion()) &&
              ObjectsCompat.equals(getNeighborhood(), feedingPointI18n.getNeighborhood());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getLocale())
      .append(getName())
      .append(getDescription())
      .append(getCity())
      .append(getStreet())
      .append(getAddress())
      .append(getRegion())
      .append(getNeighborhood())
      .toString()
      .hashCode();
  }
  
  public static LocaleStep builder() {
      return new Builder();
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(locale,
      name,
      description,
      city,
      street,
      address,
      region,
      neighborhood);
  }
  public interface LocaleStep {
    BuildStep locale(String locale);
  }
  

  public interface BuildStep {
    FeedingPointI18n build();
    BuildStep name(String name);
    BuildStep description(String description);
    BuildStep city(String city);
    BuildStep street(String street);
    BuildStep address(String address);
    BuildStep region(String region);
    BuildStep neighborhood(String neighborhood);
  }
  

  public static class Builder implements LocaleStep, BuildStep {
    private String locale;
    private String name;
    private String description;
    private String city;
    private String street;
    private String address;
    private String region;
    private String neighborhood;
    @Override
     public FeedingPointI18n build() {
        
        return new FeedingPointI18n(
          locale,
          name,
          description,
          city,
          street,
          address,
          region,
          neighborhood);
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
     public BuildStep description(String description) {
        this.description = description;
        return this;
    }
    
    @Override
     public BuildStep city(String city) {
        this.city = city;
        return this;
    }
    
    @Override
     public BuildStep street(String street) {
        this.street = street;
        return this;
    }
    
    @Override
     public BuildStep address(String address) {
        this.address = address;
        return this;
    }
    
    @Override
     public BuildStep region(String region) {
        this.region = region;
        return this;
    }
    
    @Override
     public BuildStep neighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
        return this;
    }
  }
  

  public final class CopyOfBuilder extends Builder {
    private CopyOfBuilder(String locale, String name, String description, String city, String street, String address, String region, String neighborhood) {
      super.locale(locale)
        .name(name)
        .description(description)
        .city(city)
        .street(street)
        .address(address)
        .region(region)
        .neighborhood(neighborhood);
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
     public CopyOfBuilder description(String description) {
      return (CopyOfBuilder) super.description(description);
    }
    
    @Override
     public CopyOfBuilder city(String city) {
      return (CopyOfBuilder) super.city(city);
    }
    
    @Override
     public CopyOfBuilder street(String street) {
      return (CopyOfBuilder) super.street(street);
    }
    
    @Override
     public CopyOfBuilder address(String address) {
      return (CopyOfBuilder) super.address(address);
    }
    
    @Override
     public CopyOfBuilder region(String region) {
      return (CopyOfBuilder) super.region(region);
    }
    
    @Override
     public CopyOfBuilder neighborhood(String neighborhood) {
      return (CopyOfBuilder) super.neighborhood(neighborhood);
    }
  }
  
}
