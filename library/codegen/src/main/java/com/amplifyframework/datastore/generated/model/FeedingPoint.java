package com.amplifyframework.datastore.generated.model;

import static com.amplifyframework.core.model.query.predicate.QueryField.field;

import androidx.core.util.ObjectsCompat;

import com.amplifyframework.core.model.AuthStrategy;
import com.amplifyframework.core.model.Model;
import com.amplifyframework.core.model.ModelOperation;
import com.amplifyframework.core.model.annotations.AuthRule;
import com.amplifyframework.core.model.annotations.HasMany;
import com.amplifyframework.core.model.annotations.HasOne;
import com.amplifyframework.core.model.annotations.ModelConfig;
import com.amplifyframework.core.model.annotations.ModelField;
import com.amplifyframework.core.model.query.predicate.QueryField;
import com.amplifyframework.core.model.temporal.Temporal;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

/** This is an auto generated class representing the FeedingPoint type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "FeedingPoints", authRules = {
  @AuthRule(allow = AuthStrategy.GROUPS, groupClaim = "cognito:groups", groups = { "Administrator" }, provider = "userPools", operations = { ModelOperation.CREATE, ModelOperation.READ, ModelOperation.UPDATE, ModelOperation.DELETE }),
  @AuthRule(allow = AuthStrategy.GROUPS, groupClaim = "cognito:groups", groups = { "Moderator" }, provider = "userPools", operations = { ModelOperation.CREATE, ModelOperation.READ, ModelOperation.UPDATE, ModelOperation.DELETE }),
  @AuthRule(allow = AuthStrategy.GROUPS, groupClaim = "cognito:groups", groups = { "Volunteer" }, provider = "userPools", operations = { ModelOperation.READ }),
  @AuthRule(allow = AuthStrategy.OWNER, ownerField = "owner", identityClaim = "cognito:username", provider = "userPools", operations = { ModelOperation.CREATE, ModelOperation.READ, ModelOperation.UPDATE, ModelOperation.DELETE }),
  @AuthRule(allow = AuthStrategy.PUBLIC, provider = "apiKey", operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ }),
  @AuthRule(allow = AuthStrategy.PRIVATE, operations = { ModelOperation.READ })
})
public final class FeedingPoint implements Model {
  public static final QueryField ID = field("FeedingPoint", "id");
  public static final QueryField NAME = field("FeedingPoint", "name");
  public static final QueryField DESCRIPTION = field("FeedingPoint", "description");
  public static final QueryField CITY = field("FeedingPoint", "city");
  public static final QueryField STREET = field("FeedingPoint", "street");
  public static final QueryField ADDRESS = field("FeedingPoint", "address");
  public static final QueryField IMAGES = field("FeedingPoint", "images");
  public static final QueryField POINT = field("FeedingPoint", "point");
  public static final QueryField LOCATION = field("FeedingPoint", "location");
  public static final QueryField REGION = field("FeedingPoint", "region");
  public static final QueryField NEIGHBORHOOD = field("FeedingPoint", "neighborhood");
  public static final QueryField DISTANCE = field("FeedingPoint", "distance");
  public static final QueryField STATUS = field("FeedingPoint", "status");
  public static final QueryField I18N = field("FeedingPoint", "i18n");
  public static final QueryField STATUS_UPDATED_AT = field("FeedingPoint", "statusUpdatedAt");
  public static final QueryField CREATED_AT = field("FeedingPoint", "createdAt");
  public static final QueryField UPDATED_AT = field("FeedingPoint", "updatedAt");
  public static final QueryField CREATED_BY = field("FeedingPoint", "createdBy");
  public static final QueryField UPDATED_BY = field("FeedingPoint", "updatedBy");
  public static final QueryField OWNER = field("FeedingPoint", "owner");
  public static final QueryField COVER = field("FeedingPoint", "cover");
  public static final QueryField FEEDING_POINT_CATEGORY_ID = field("FeedingPoint", "feedingPointCategoryId");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String name;
  private final @ModelField(targetType="String", isRequired = true) String description;
  private final @ModelField(targetType="String", isRequired = true) String city;
  private final @ModelField(targetType="String", isRequired = true) String street;
  private final @ModelField(targetType="String", isRequired = true) String address;
  private final @ModelField(targetType="String") List<String> images;
  private final @ModelField(targetType="Point", isRequired = true) Point point;
  private final @ModelField(targetType="Location", isRequired = true) Location location;
  private final @ModelField(targetType="String", isRequired = true) String region;
  private final @ModelField(targetType="String", isRequired = true) String neighborhood;
  private final @ModelField(targetType="Float", isRequired = true) Double distance;
  private final @ModelField(targetType="FeedingPointStatus", isRequired = true) FeedingPointStatus status;
  private final @ModelField(targetType="FeedingPointI18n") List<FeedingPointI18n> i18n;
  private final @ModelField(targetType="AWSDateTime", isRequired = true) Temporal.DateTime statusUpdatedAt;
  private final @ModelField(targetType="AWSDateTime", isRequired = true) Temporal.DateTime createdAt;
  private final @ModelField(targetType="AWSDateTime", isRequired = true) Temporal.DateTime updatedAt;
  private final @ModelField(targetType="String") String createdBy;
  private final @ModelField(targetType="String") String updatedBy;
  private final @ModelField(targetType="String") String owner;
  private final @ModelField(targetType="RelationPetFeedingPoint") @HasMany(associatedWith = "feedingPoint", type = RelationPetFeedingPoint.class) List<RelationPetFeedingPoint> pets = null;
  private final @ModelField(targetType="Category") @HasOne(associatedWith = "id", type = Category.class) Category category = null;
  private final @ModelField(targetType="RelationUserFeedingPoint") @HasMany(associatedWith = "feedingPoint", type = RelationUserFeedingPoint.class) List<RelationUserFeedingPoint> users = null;
  private final @ModelField(targetType="Feeding") @HasMany(associatedWith = "feedingPoint", type = Feeding.class) List<Feeding> feedings = null;
  private final @ModelField(targetType="String") String cover;
  private final @ModelField(targetType="ID") String feedingPointCategoryId;
  public String getId() {
      return id;
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
  
  public List<String> getImages() {
      return images;
  }
  
  public Point getPoint() {
      return point;
  }
  
  public Location getLocation() {
      return location;
  }
  
  public String getRegion() {
      return region;
  }
  
  public String getNeighborhood() {
      return neighborhood;
  }
  
  public Double getDistance() {
      return distance;
  }
  
  public FeedingPointStatus getStatus() {
      return status;
  }
  
  public List<FeedingPointI18n> getI18n() {
      return i18n;
  }
  
  public Temporal.DateTime getStatusUpdatedAt() {
      return statusUpdatedAt;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  public String getCreatedBy() {
      return createdBy;
  }
  
  public String getUpdatedBy() {
      return updatedBy;
  }
  
  public String getOwner() {
      return owner;
  }
  
  public List<RelationPetFeedingPoint> getPets() {
      return pets;
  }
  
  public Category getCategory() {
      return category;
  }
  
  public List<RelationUserFeedingPoint> getUsers() {
      return users;
  }
  
  public List<Feeding> getFeedings() {
      return feedings;
  }
  
  public String getCover() {
      return cover;
  }
  
  public String getFeedingPointCategoryId() {
      return feedingPointCategoryId;
  }
  
  private FeedingPoint(String id, String name, String description, String city, String street, String address, List<String> images, Point point, Location location, String region, String neighborhood, Double distance, FeedingPointStatus status, List<FeedingPointI18n> i18n, Temporal.DateTime statusUpdatedAt, Temporal.DateTime createdAt, Temporal.DateTime updatedAt, String createdBy, String updatedBy, String owner, String cover, String feedingPointCategoryId) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.city = city;
    this.street = street;
    this.address = address;
    this.images = images;
    this.point = point;
    this.location = location;
    this.region = region;
    this.neighborhood = neighborhood;
    this.distance = distance;
    this.status = status;
    this.i18n = i18n;
    this.statusUpdatedAt = statusUpdatedAt;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.createdBy = createdBy;
    this.updatedBy = updatedBy;
    this.owner = owner;
    this.cover = cover;
    this.feedingPointCategoryId = feedingPointCategoryId;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      FeedingPoint feedingPoint = (FeedingPoint) obj;
      return ObjectsCompat.equals(getId(), feedingPoint.getId()) &&
              ObjectsCompat.equals(getName(), feedingPoint.getName()) &&
              ObjectsCompat.equals(getDescription(), feedingPoint.getDescription()) &&
              ObjectsCompat.equals(getCity(), feedingPoint.getCity()) &&
              ObjectsCompat.equals(getStreet(), feedingPoint.getStreet()) &&
              ObjectsCompat.equals(getAddress(), feedingPoint.getAddress()) &&
              ObjectsCompat.equals(getImages(), feedingPoint.getImages()) &&
              ObjectsCompat.equals(getPoint(), feedingPoint.getPoint()) &&
              ObjectsCompat.equals(getLocation(), feedingPoint.getLocation()) &&
              ObjectsCompat.equals(getRegion(), feedingPoint.getRegion()) &&
              ObjectsCompat.equals(getNeighborhood(), feedingPoint.getNeighborhood()) &&
              ObjectsCompat.equals(getDistance(), feedingPoint.getDistance()) &&
              ObjectsCompat.equals(getStatus(), feedingPoint.getStatus()) &&
              ObjectsCompat.equals(getI18n(), feedingPoint.getI18n()) &&
              ObjectsCompat.equals(getStatusUpdatedAt(), feedingPoint.getStatusUpdatedAt()) &&
              ObjectsCompat.equals(getCreatedAt(), feedingPoint.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), feedingPoint.getUpdatedAt()) &&
              ObjectsCompat.equals(getCreatedBy(), feedingPoint.getCreatedBy()) &&
              ObjectsCompat.equals(getUpdatedBy(), feedingPoint.getUpdatedBy()) &&
              ObjectsCompat.equals(getOwner(), feedingPoint.getOwner()) &&
              ObjectsCompat.equals(getCover(), feedingPoint.getCover()) &&
              ObjectsCompat.equals(getFeedingPointCategoryId(), feedingPoint.getFeedingPointCategoryId());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getName())
      .append(getDescription())
      .append(getCity())
      .append(getStreet())
      .append(getAddress())
      .append(getImages())
      .append(getPoint())
      .append(getLocation())
      .append(getRegion())
      .append(getNeighborhood())
      .append(getDistance())
      .append(getStatus())
      .append(getI18n())
      .append(getStatusUpdatedAt())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .append(getCreatedBy())
      .append(getUpdatedBy())
      .append(getOwner())
      .append(getCover())
      .append(getFeedingPointCategoryId())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("FeedingPoint {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("name=" + String.valueOf(getName()) + ", ")
      .append("description=" + String.valueOf(getDescription()) + ", ")
      .append("city=" + String.valueOf(getCity()) + ", ")
      .append("street=" + String.valueOf(getStreet()) + ", ")
      .append("address=" + String.valueOf(getAddress()) + ", ")
      .append("images=" + String.valueOf(getImages()) + ", ")
      .append("point=" + String.valueOf(getPoint()) + ", ")
      .append("location=" + String.valueOf(getLocation()) + ", ")
      .append("region=" + String.valueOf(getRegion()) + ", ")
      .append("neighborhood=" + String.valueOf(getNeighborhood()) + ", ")
      .append("distance=" + String.valueOf(getDistance()) + ", ")
      .append("status=" + String.valueOf(getStatus()) + ", ")
      .append("i18n=" + String.valueOf(getI18n()) + ", ")
      .append("statusUpdatedAt=" + String.valueOf(getStatusUpdatedAt()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()) + ", ")
      .append("createdBy=" + String.valueOf(getCreatedBy()) + ", ")
      .append("updatedBy=" + String.valueOf(getUpdatedBy()) + ", ")
      .append("owner=" + String.valueOf(getOwner()) + ", ")
      .append("cover=" + String.valueOf(getCover()) + ", ")
      .append("feedingPointCategoryId=" + String.valueOf(getFeedingPointCategoryId()))
      .append("}")
      .toString();
  }
  
  public static NameStep builder() {
      return new Builder();
  }
  
  /**
   * WARNING: This method should not be used to build an instance of this object for a CREATE mutation.
   * This is a convenience method to return an instance of the object with only its ID populated
   * to be used in the context of a parameter in a delete mutation or referencing a foreign key
   * in a relationship.
   * @param id the id of the existing item this instance will represent
   * @return an instance of this model with only ID populated
   */
  public static FeedingPoint justId(String id) {
    return new FeedingPoint(
      id,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      name,
      description,
      city,
      street,
      address,
      images,
      point,
      location,
      region,
      neighborhood,
      distance,
      status,
      i18n,
      statusUpdatedAt,
      createdAt,
      updatedAt,
      createdBy,
      updatedBy,
      owner,
      cover,
      feedingPointCategoryId);
  }
  public interface NameStep {
    DescriptionStep name(String name);
  }
  

  public interface DescriptionStep {
    CityStep description(String description);
  }
  

  public interface CityStep {
    StreetStep city(String city);
  }
  

  public interface StreetStep {
    AddressStep street(String street);
  }
  

  public interface AddressStep {
    PointStep address(String address);
  }
  

  public interface PointStep {
    LocationStep point(Point point);
  }
  

  public interface LocationStep {
    RegionStep location(Location location);
  }
  

  public interface RegionStep {
    NeighborhoodStep region(String region);
  }
  

  public interface NeighborhoodStep {
    DistanceStep neighborhood(String neighborhood);
  }
  

  public interface DistanceStep {
    StatusStep distance(Double distance);
  }
  

  public interface StatusStep {
    StatusUpdatedAtStep status(FeedingPointStatus status);
  }
  

  public interface StatusUpdatedAtStep {
    CreatedAtStep statusUpdatedAt(Temporal.DateTime statusUpdatedAt);
  }
  

  public interface CreatedAtStep {
    UpdatedAtStep createdAt(Temporal.DateTime createdAt);
  }
  

  public interface UpdatedAtStep {
    BuildStep updatedAt(Temporal.DateTime updatedAt);
  }
  

  public interface BuildStep {
    FeedingPoint build();
    BuildStep id(String id);
    BuildStep images(List<String> images);
    BuildStep i18n(List<FeedingPointI18n> i18n);
    BuildStep createdBy(String createdBy);
    BuildStep updatedBy(String updatedBy);
    BuildStep owner(String owner);
    BuildStep cover(String cover);
    BuildStep feedingPointCategoryId(String feedingPointCategoryId);
  }
  

  public static class Builder implements NameStep, DescriptionStep, CityStep, StreetStep, AddressStep, PointStep, LocationStep, RegionStep, NeighborhoodStep, DistanceStep, StatusStep, StatusUpdatedAtStep, CreatedAtStep, UpdatedAtStep, BuildStep {
    private String id;
    private String name;
    private String description;
    private String city;
    private String street;
    private String address;
    private Point point;
    private Location location;
    private String region;
    private String neighborhood;
    private Double distance;
    private FeedingPointStatus status;
    private Temporal.DateTime statusUpdatedAt;
    private Temporal.DateTime createdAt;
    private Temporal.DateTime updatedAt;
    private List<String> images;
    private List<FeedingPointI18n> i18n;
    private String createdBy;
    private String updatedBy;
    private String owner;
    private String cover;
    private String feedingPointCategoryId;
    @Override
     public FeedingPoint build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new FeedingPoint(
          id,
          name,
          description,
          city,
          street,
          address,
          images,
          point,
          location,
          region,
          neighborhood,
          distance,
          status,
          i18n,
          statusUpdatedAt,
          createdAt,
          updatedAt,
          createdBy,
          updatedBy,
          owner,
          cover,
          feedingPointCategoryId);
    }
    
    @Override
     public DescriptionStep name(String name) {
        Objects.requireNonNull(name);
        this.name = name;
        return this;
    }
    
    @Override
     public CityStep description(String description) {
        Objects.requireNonNull(description);
        this.description = description;
        return this;
    }
    
    @Override
     public StreetStep city(String city) {
        Objects.requireNonNull(city);
        this.city = city;
        return this;
    }
    
    @Override
     public AddressStep street(String street) {
        Objects.requireNonNull(street);
        this.street = street;
        return this;
    }
    
    @Override
     public PointStep address(String address) {
        Objects.requireNonNull(address);
        this.address = address;
        return this;
    }
    
    @Override
     public LocationStep point(Point point) {
        Objects.requireNonNull(point);
        this.point = point;
        return this;
    }
    
    @Override
     public RegionStep location(Location location) {
        Objects.requireNonNull(location);
        this.location = location;
        return this;
    }
    
    @Override
     public NeighborhoodStep region(String region) {
        Objects.requireNonNull(region);
        this.region = region;
        return this;
    }
    
    @Override
     public DistanceStep neighborhood(String neighborhood) {
        Objects.requireNonNull(neighborhood);
        this.neighborhood = neighborhood;
        return this;
    }
    
    @Override
     public StatusStep distance(Double distance) {
        Objects.requireNonNull(distance);
        this.distance = distance;
        return this;
    }
    
    @Override
     public StatusUpdatedAtStep status(FeedingPointStatus status) {
        Objects.requireNonNull(status);
        this.status = status;
        return this;
    }
    
    @Override
     public CreatedAtStep statusUpdatedAt(Temporal.DateTime statusUpdatedAt) {
        Objects.requireNonNull(statusUpdatedAt);
        this.statusUpdatedAt = statusUpdatedAt;
        return this;
    }
    
    @Override
     public UpdatedAtStep createdAt(Temporal.DateTime createdAt) {
        Objects.requireNonNull(createdAt);
        this.createdAt = createdAt;
        return this;
    }
    
    @Override
     public BuildStep updatedAt(Temporal.DateTime updatedAt) {
        Objects.requireNonNull(updatedAt);
        this.updatedAt = updatedAt;
        return this;
    }
    
    @Override
     public BuildStep images(List<String> images) {
        this.images = images;
        return this;
    }
    
    @Override
     public BuildStep i18n(List<FeedingPointI18n> i18n) {
        this.i18n = i18n;
        return this;
    }
    
    @Override
     public BuildStep createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }
    
    @Override
     public BuildStep updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }
    
    @Override
     public BuildStep owner(String owner) {
        this.owner = owner;
        return this;
    }
    
    @Override
     public BuildStep cover(String cover) {
        this.cover = cover;
        return this;
    }
    
    @Override
     public BuildStep feedingPointCategoryId(String feedingPointCategoryId) {
        this.feedingPointCategoryId = feedingPointCategoryId;
        return this;
    }
    
    /**
     * @param id id
     * @return Current Builder instance, for fluent method chaining
     */
    public BuildStep id(String id) {
        this.id = id;
        return this;
    }
  }
  

  public final class CopyOfBuilder extends Builder {
    private CopyOfBuilder(String id, String name, String description, String city, String street, String address, List<String> images, Point point, Location location, String region, String neighborhood, Double distance, FeedingPointStatus status, List<FeedingPointI18n> i18n, Temporal.DateTime statusUpdatedAt, Temporal.DateTime createdAt, Temporal.DateTime updatedAt, String createdBy, String updatedBy, String owner, String cover, String feedingPointCategoryId) {
      super.id(id);
      super.name(name)
        .description(description)
        .city(city)
        .street(street)
        .address(address)
        .point(point)
        .location(location)
        .region(region)
        .neighborhood(neighborhood)
        .distance(distance)
        .status(status)
        .statusUpdatedAt(statusUpdatedAt)
        .createdAt(createdAt)
        .updatedAt(updatedAt)
        .images(images)
        .i18n(i18n)
        .createdBy(createdBy)
        .updatedBy(updatedBy)
        .owner(owner)
        .cover(cover)
        .feedingPointCategoryId(feedingPointCategoryId);
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
     public CopyOfBuilder point(Point point) {
      return (CopyOfBuilder) super.point(point);
    }
    
    @Override
     public CopyOfBuilder location(Location location) {
      return (CopyOfBuilder) super.location(location);
    }
    
    @Override
     public CopyOfBuilder region(String region) {
      return (CopyOfBuilder) super.region(region);
    }
    
    @Override
     public CopyOfBuilder neighborhood(String neighborhood) {
      return (CopyOfBuilder) super.neighborhood(neighborhood);
    }
    
    @Override
     public CopyOfBuilder distance(Double distance) {
      return (CopyOfBuilder) super.distance(distance);
    }
    
    @Override
     public CopyOfBuilder status(FeedingPointStatus status) {
      return (CopyOfBuilder) super.status(status);
    }
    
    @Override
     public CopyOfBuilder statusUpdatedAt(Temporal.DateTime statusUpdatedAt) {
      return (CopyOfBuilder) super.statusUpdatedAt(statusUpdatedAt);
    }
    
    @Override
     public CopyOfBuilder createdAt(Temporal.DateTime createdAt) {
      return (CopyOfBuilder) super.createdAt(createdAt);
    }
    
    @Override
     public CopyOfBuilder updatedAt(Temporal.DateTime updatedAt) {
      return (CopyOfBuilder) super.updatedAt(updatedAt);
    }
    
    @Override
     public CopyOfBuilder images(List<String> images) {
      return (CopyOfBuilder) super.images(images);
    }
    
    @Override
     public CopyOfBuilder i18n(List<FeedingPointI18n> i18n) {
      return (CopyOfBuilder) super.i18n(i18n);
    }
    
    @Override
     public CopyOfBuilder createdBy(String createdBy) {
      return (CopyOfBuilder) super.createdBy(createdBy);
    }
    
    @Override
     public CopyOfBuilder updatedBy(String updatedBy) {
      return (CopyOfBuilder) super.updatedBy(updatedBy);
    }
    
    @Override
     public CopyOfBuilder owner(String owner) {
      return (CopyOfBuilder) super.owner(owner);
    }
    
    @Override
     public CopyOfBuilder cover(String cover) {
      return (CopyOfBuilder) super.cover(cover);
    }
    
    @Override
     public CopyOfBuilder feedingPointCategoryId(String feedingPointCategoryId) {
      return (CopyOfBuilder) super.feedingPointCategoryId(feedingPointCategoryId);
    }
  }
  
}
