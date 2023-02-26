package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.annotations.HasOne;
import com.amplifyframework.core.model.temporal.Temporal;

import java.util.List;
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

/** This is an auto generated class representing the Favourite type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Favourites", authRules = {
  @AuthRule(allow = AuthStrategy.GROUPS, groupClaim = "cognito:groups", groups = { "Administrator" }, provider = "userPools", operations = { ModelOperation.CREATE, ModelOperation.READ, ModelOperation.UPDATE, ModelOperation.DELETE }),
  @AuthRule(allow = AuthStrategy.GROUPS, groupClaim = "cognito:groups", groups = { "Moderator" }, provider = "userPools", operations = { ModelOperation.CREATE, ModelOperation.READ, ModelOperation.UPDATE, ModelOperation.DELETE }),
  @AuthRule(allow = AuthStrategy.GROUPS, groupClaim = "cognito:groups", groups = { "Volunteer" }, provider = "userPools", operations = { ModelOperation.CREATE, ModelOperation.READ, ModelOperation.UPDATE, ModelOperation.DELETE }),
  @AuthRule(allow = AuthStrategy.OWNER, ownerField = "owner", identityClaim = "cognito:username", provider = "userPools", operations = { ModelOperation.CREATE, ModelOperation.READ, ModelOperation.UPDATE, ModelOperation.DELETE }),
  @AuthRule(allow = AuthStrategy.PUBLIC, provider = "apiKey", operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ }),
  @AuthRule(allow = AuthStrategy.PRIVATE, operations = { ModelOperation.CREATE, ModelOperation.READ, ModelOperation.UPDATE, ModelOperation.DELETE })
})
@Index(name = "byUserId", fields = {"userId","feedingPointId"})
@Index(name = "byFeedingPointId", fields = {"feedingPointId","userId"})
public final class Favourite implements Model {
  public static final QueryField ID = field("Favourite", "id");
  public static final QueryField USER_ID = field("Favourite", "userId");
  public static final QueryField FEEDING_POINT_ID = field("Favourite", "feedingPointId");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="ID", isRequired = true) String userId;
  private final @ModelField(targetType="ID", isRequired = true) String feedingPointId;
  private final @ModelField(targetType="FeedingPoint", isRequired = true) @HasOne(associatedWith = "id", type = FeedingPoint.class) FeedingPoint feedingPoint = null;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  public String getId() {
      return id;
  }
  
  public String getUserId() {
      return userId;
  }
  
  public String getFeedingPointId() {
      return feedingPointId;
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
  
  private Favourite(String id, String userId, String feedingPointId) {
    this.id = id;
    this.userId = userId;
    this.feedingPointId = feedingPointId;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Favourite favourite = (Favourite) obj;
      return ObjectsCompat.equals(getId(), favourite.getId()) &&
              ObjectsCompat.equals(getUserId(), favourite.getUserId()) &&
              ObjectsCompat.equals(getFeedingPointId(), favourite.getFeedingPointId()) &&
              ObjectsCompat.equals(getCreatedAt(), favourite.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), favourite.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getUserId())
      .append(getFeedingPointId())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Favourite {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("userId=" + String.valueOf(getUserId()) + ", ")
      .append("feedingPointId=" + String.valueOf(getFeedingPointId()) + ", ")
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
  public static Favourite justId(String id) {
    return new Favourite(
      id,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      userId,
      feedingPointId);
  }
  public interface UserIdStep {
    FeedingPointIdStep userId(String userId);
  }
  

  public interface FeedingPointIdStep {
    BuildStep feedingPointId(String feedingPointId);
  }
  

  public interface BuildStep {
    Favourite build();
    BuildStep id(String id);
  }
  

  public static class Builder implements UserIdStep, FeedingPointIdStep, BuildStep {
    private String id;
    private String userId;
    private String feedingPointId;
    @Override
     public Favourite build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Favourite(
          id,
          userId,
          feedingPointId);
    }
    
    @Override
     public FeedingPointIdStep userId(String userId) {
        Objects.requireNonNull(userId);
        this.userId = userId;
        return this;
    }
    
    @Override
     public BuildStep feedingPointId(String feedingPointId) {
        Objects.requireNonNull(feedingPointId);
        this.feedingPointId = feedingPointId;
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
    private CopyOfBuilder(String id, String userId, String feedingPointId) {
      super.id(id);
      super.userId(userId)
        .feedingPointId(feedingPointId);
    }
    
    @Override
     public CopyOfBuilder userId(String userId) {
      return (CopyOfBuilder) super.userId(userId);
    }
    
    @Override
     public CopyOfBuilder feedingPointId(String feedingPointId) {
      return (CopyOfBuilder) super.feedingPointId(feedingPointId);
    }
  }
  
}
