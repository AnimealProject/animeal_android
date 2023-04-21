package com.amplifyframework.datastore.generated.model;

import static com.amplifyframework.core.model.query.predicate.QueryField.field;

import androidx.core.util.ObjectsCompat;

import com.amplifyframework.core.model.AuthStrategy;
import com.amplifyframework.core.model.Model;
import com.amplifyframework.core.model.ModelOperation;
import com.amplifyframework.core.model.annotations.AuthRule;
import com.amplifyframework.core.model.annotations.BelongsTo;
import com.amplifyframework.core.model.annotations.Index;
import com.amplifyframework.core.model.annotations.ModelConfig;
import com.amplifyframework.core.model.annotations.ModelField;
import com.amplifyframework.core.model.query.predicate.QueryField;
import com.amplifyframework.core.model.temporal.Temporal;

import java.util.Objects;
import java.util.UUID;

/** This is an auto generated class representing the RelationUserPet type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "RelationUserPets", authRules = {
  @AuthRule(allow = AuthStrategy.GROUPS, groupClaim = "cognito:groups", groups = { "Administrator" }, provider = "userPools", operations = { ModelOperation.CREATE, ModelOperation.READ, ModelOperation.UPDATE, ModelOperation.DELETE }),
  @AuthRule(allow = AuthStrategy.GROUPS, groupClaim = "cognito:groups", groups = { "Moderator" }, provider = "userPools", operations = { ModelOperation.CREATE, ModelOperation.READ, ModelOperation.UPDATE, ModelOperation.DELETE }),
  @AuthRule(allow = AuthStrategy.GROUPS, groupClaim = "cognito:groups", groups = { "Volunteer" }, provider = "userPools", operations = { ModelOperation.READ }),
  @AuthRule(allow = AuthStrategy.OWNER, ownerField = "owner", identityClaim = "cognito:username", provider = "userPools", operations = { ModelOperation.CREATE, ModelOperation.READ, ModelOperation.UPDATE, ModelOperation.DELETE }),
  @AuthRule(allow = AuthStrategy.PUBLIC, provider = "apiKey", operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ }),
  @AuthRule(allow = AuthStrategy.PRIVATE, operations = { ModelOperation.READ })
})
@Index(name = "byUserId", fields = {"userId","petId"})
@Index(name = "byPetId", fields = {"petId","userId"})
public final class RelationUserPet implements Model {
  public static final QueryField ID = field("RelationUserPet", "id");
  public static final QueryField USER_ID = field("RelationUserPet", "userId");
  public static final QueryField PET = field("RelationUserPet", "petId");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String userId;
  private final @ModelField(targetType="Pet", isRequired = true) @BelongsTo(targetName = "petId", type = Pet.class) Pet pet;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  public String getId() {
      return id;
  }
  
  public String getUserId() {
      return userId;
  }
  
  public Pet getPet() {
      return pet;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private RelationUserPet(String id, String userId, Pet pet) {
    this.id = id;
    this.userId = userId;
    this.pet = pet;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      RelationUserPet relationUserPet = (RelationUserPet) obj;
      return ObjectsCompat.equals(getId(), relationUserPet.getId()) &&
              ObjectsCompat.equals(getUserId(), relationUserPet.getUserId()) &&
              ObjectsCompat.equals(getPet(), relationUserPet.getPet()) &&
              ObjectsCompat.equals(getCreatedAt(), relationUserPet.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), relationUserPet.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getUserId())
      .append(getPet())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("RelationUserPet {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("userId=" + String.valueOf(getUserId()) + ", ")
      .append("pet=" + String.valueOf(getPet()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static UserIdStep builder() {
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
  public static RelationUserPet justId(String id) {
    return new RelationUserPet(
      id,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      userId,
      pet);
  }
  public interface UserIdStep {
    PetStep userId(String userId);
  }
  

  public interface PetStep {
    BuildStep pet(Pet pet);
  }
  

  public interface BuildStep {
    RelationUserPet build();
    BuildStep id(String id);
  }
  

  public static class Builder implements UserIdStep, PetStep, BuildStep {
    private String id;
    private String userId;
    private Pet pet;
    @Override
     public RelationUserPet build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new RelationUserPet(
          id,
          userId,
          pet);
    }
    
    @Override
     public PetStep userId(String userId) {
        Objects.requireNonNull(userId);
        this.userId = userId;
        return this;
    }
    
    @Override
     public BuildStep pet(Pet pet) {
        Objects.requireNonNull(pet);
        this.pet = pet;
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
    private CopyOfBuilder(String id, String userId, Pet pet) {
      super.id(id);
      super.userId(userId)
        .pet(pet);
    }
    
    @Override
     public CopyOfBuilder userId(String userId) {
      return (CopyOfBuilder) super.userId(userId);
    }
    
    @Override
     public CopyOfBuilder pet(Pet pet) {
      return (CopyOfBuilder) super.pet(pet);
    }
  }
  
}
