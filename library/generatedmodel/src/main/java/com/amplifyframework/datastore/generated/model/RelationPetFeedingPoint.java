package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.annotations.BelongsTo;
import com.amplifyframework.core.model.temporal.Temporal;

import java.util.UUID;
import java.util.Objects;

import androidx.core.util.ObjectsCompat;

import com.amplifyframework.core.model.AuthStrategy;
import com.amplifyframework.core.model.Model;
import com.amplifyframework.core.model.ModelOperation;
import com.amplifyframework.core.model.annotations.AuthRule;
import com.amplifyframework.core.model.annotations.Index;
import com.amplifyframework.core.model.annotations.ModelConfig;
import com.amplifyframework.core.model.annotations.ModelField;
import com.amplifyframework.core.model.query.predicate.QueryField;

import static com.amplifyframework.core.model.query.predicate.QueryField.field;

/** This is an auto generated class representing the RelationPetFeedingPoint type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "RelationPetFeedingPoints", authRules = {
  @AuthRule(allow = AuthStrategy.GROUPS, groupClaim = "cognito:groups", groups = { "Administrator" }, provider = "userPools", operations = { ModelOperation.CREATE, ModelOperation.READ, ModelOperation.UPDATE, ModelOperation.DELETE }),
  @AuthRule(allow = AuthStrategy.GROUPS, groupClaim = "cognito:groups", groups = { "Moderator" }, provider = "userPools", operations = { ModelOperation.CREATE, ModelOperation.READ, ModelOperation.UPDATE, ModelOperation.DELETE }),
  @AuthRule(allow = AuthStrategy.GROUPS, groupClaim = "cognito:groups", groups = { "Volunteer" }, provider = "userPools", operations = { ModelOperation.READ }),
  @AuthRule(allow = AuthStrategy.OWNER, ownerField = "owner", identityClaim = "cognito:username", provider = "userPools", operations = { ModelOperation.CREATE, ModelOperation.READ, ModelOperation.UPDATE, ModelOperation.DELETE }),
  @AuthRule(allow = AuthStrategy.PUBLIC, provider = "apiKey", operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ }),
  @AuthRule(allow = AuthStrategy.PRIVATE, operations = { ModelOperation.READ })
})
@Index(name = "byPetId", fields = {"petId","feedingPointId"})
@Index(name = "byFeedingPointId", fields = {"feedingPointId","petId"})
public final class RelationPetFeedingPoint implements Model {
  public static final QueryField ID = field("RelationPetFeedingPoint", "id");
  public static final QueryField PET = field("RelationPetFeedingPoint", "petId");
  public static final QueryField FEEDING_POINT = field("RelationPetFeedingPoint", "feedingPointId");
  public static final QueryField CREATED_AT = field("RelationPetFeedingPoint", "createdAt");
  public static final QueryField UPDATED_AT = field("RelationPetFeedingPoint", "updatedAt");
  public static final QueryField OWNER = field("RelationPetFeedingPoint", "owner");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="Pet", isRequired = true) @BelongsTo(targetName = "petId", type = Pet.class) Pet pet;
  private final @ModelField(targetType="FeedingPoint", isRequired = true) @BelongsTo(targetName = "feedingPointId", type = FeedingPoint.class) FeedingPoint feedingPoint;
  private final @ModelField(targetType="AWSDateTime", isRequired = true) Temporal.DateTime createdAt;
  private final @ModelField(targetType="AWSDateTime", isRequired = true) Temporal.DateTime updatedAt;
  private final @ModelField(targetType="String") String owner;
  public String getId() {
      return id;
  }
  
  public Pet getPet() {
      return pet;
  }
  
  public FeedingPoint getFeedingPoint() {
      return feedingPoint;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  public String getOwner() {
      return owner;
  }
  
  private RelationPetFeedingPoint(String id, Pet pet, FeedingPoint feedingPoint, Temporal.DateTime createdAt, Temporal.DateTime updatedAt, String owner) {
    this.id = id;
    this.pet = pet;
    this.feedingPoint = feedingPoint;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.owner = owner;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      RelationPetFeedingPoint relationPetFeedingPoint = (RelationPetFeedingPoint) obj;
      return ObjectsCompat.equals(getId(), relationPetFeedingPoint.getId()) &&
              ObjectsCompat.equals(getPet(), relationPetFeedingPoint.getPet()) &&
              ObjectsCompat.equals(getFeedingPoint(), relationPetFeedingPoint.getFeedingPoint()) &&
              ObjectsCompat.equals(getCreatedAt(), relationPetFeedingPoint.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), relationPetFeedingPoint.getUpdatedAt()) &&
              ObjectsCompat.equals(getOwner(), relationPetFeedingPoint.getOwner());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getPet())
      .append(getFeedingPoint())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .append(getOwner())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("RelationPetFeedingPoint {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("pet=" + String.valueOf(getPet()) + ", ")
      .append("feedingPoint=" + String.valueOf(getFeedingPoint()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()) + ", ")
      .append("owner=" + String.valueOf(getOwner()))
      .append("}")
      .toString();
  }
  
  public static PetStep builder() {
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
  public static RelationPetFeedingPoint justId(String id) {
    return new RelationPetFeedingPoint(
      id,
      null,
      null,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      pet,
      feedingPoint,
      createdAt,
      updatedAt,
      owner);
  }
  public interface PetStep {
    FeedingPointStep pet(Pet pet);
  }
  

  public interface FeedingPointStep {
    CreatedAtStep feedingPoint(FeedingPoint feedingPoint);
  }
  

  public interface CreatedAtStep {
    UpdatedAtStep createdAt(Temporal.DateTime createdAt);
  }
  

  public interface UpdatedAtStep {
    BuildStep updatedAt(Temporal.DateTime updatedAt);
  }
  

  public interface BuildStep {
    RelationPetFeedingPoint build();
    BuildStep id(String id);
    BuildStep owner(String owner);
  }
  

  public static class Builder implements PetStep, FeedingPointStep, CreatedAtStep, UpdatedAtStep, BuildStep {
    private String id;
    private Pet pet;
    private FeedingPoint feedingPoint;
    private Temporal.DateTime createdAt;
    private Temporal.DateTime updatedAt;
    private String owner;
    @Override
     public RelationPetFeedingPoint build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new RelationPetFeedingPoint(
          id,
          pet,
          feedingPoint,
          createdAt,
          updatedAt,
          owner);
    }
    
    @Override
     public FeedingPointStep pet(Pet pet) {
        Objects.requireNonNull(pet);
        this.pet = pet;
        return this;
    }
    
    @Override
     public CreatedAtStep feedingPoint(FeedingPoint feedingPoint) {
        Objects.requireNonNull(feedingPoint);
        this.feedingPoint = feedingPoint;
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
     public BuildStep owner(String owner) {
        this.owner = owner;
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
    private CopyOfBuilder(String id, Pet pet, FeedingPoint feedingPoint, Temporal.DateTime createdAt, Temporal.DateTime updatedAt, String owner) {
      super.id(id);
      super.pet(pet)
        .feedingPoint(feedingPoint)
        .createdAt(createdAt)
        .updatedAt(updatedAt)
        .owner(owner);
    }
    
    @Override
     public CopyOfBuilder pet(Pet pet) {
      return (CopyOfBuilder) super.pet(pet);
    }
    
    @Override
     public CopyOfBuilder feedingPoint(FeedingPoint feedingPoint) {
      return (CopyOfBuilder) super.feedingPoint(feedingPoint);
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
     public CopyOfBuilder owner(String owner) {
      return (CopyOfBuilder) super.owner(owner);
    }
  }
  
}
