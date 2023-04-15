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

/** This is an auto generated class representing the RelationUserFeedingPoint type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "RelationUserFeedingPoints", authRules = {
  @AuthRule(allow = AuthStrategy.GROUPS, groupClaim = "cognito:groups", groups = { "Administrator" }, provider = "userPools", operations = { ModelOperation.CREATE, ModelOperation.READ, ModelOperation.UPDATE, ModelOperation.DELETE }),
  @AuthRule(allow = AuthStrategy.GROUPS, groupClaim = "cognito:groups", groups = { "Moderator" }, provider = "userPools", operations = { ModelOperation.CREATE, ModelOperation.READ, ModelOperation.UPDATE, ModelOperation.DELETE }),
  @AuthRule(allow = AuthStrategy.GROUPS, groupClaim = "cognito:groups", groups = { "Volunteer" }, provider = "userPools", operations = { ModelOperation.READ }),
  @AuthRule(allow = AuthStrategy.OWNER, ownerField = "owner", identityClaim = "cognito:username", provider = "userPools", operations = { ModelOperation.CREATE, ModelOperation.READ, ModelOperation.UPDATE, ModelOperation.DELETE }),
  @AuthRule(allow = AuthStrategy.PUBLIC, provider = "apiKey", operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ }),
  @AuthRule(allow = AuthStrategy.PRIVATE, operations = { ModelOperation.READ })
})
@Index(name = "byUserId", fields = {"userId","feedingPointId"})
@Index(name = "byFeedingPointId", fields = {"feedingPointId","userId"})
public final class RelationUserFeedingPoint implements Model {
  public static final QueryField ID = field("RelationUserFeedingPoint", "id");
  public static final QueryField USER_ID = field("RelationUserFeedingPoint", "userId");
  public static final QueryField FEEDING_POINT = field("RelationUserFeedingPoint", "feedingPointId");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String userId;
  private final @ModelField(targetType="FeedingPoint", isRequired = true) @BelongsTo(targetName = "feedingPointId", type = FeedingPoint.class) FeedingPoint feedingPoint;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  public String getId() {
      return id;
  }
  
  public String getUserId() {
      return userId;
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
  
  private RelationUserFeedingPoint(String id, String userId, FeedingPoint feedingPoint) {
    this.id = id;
    this.userId = userId;
    this.feedingPoint = feedingPoint;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      RelationUserFeedingPoint relationUserFeedingPoint = (RelationUserFeedingPoint) obj;
      return ObjectsCompat.equals(getId(), relationUserFeedingPoint.getId()) &&
              ObjectsCompat.equals(getUserId(), relationUserFeedingPoint.getUserId()) &&
              ObjectsCompat.equals(getFeedingPoint(), relationUserFeedingPoint.getFeedingPoint()) &&
              ObjectsCompat.equals(getCreatedAt(), relationUserFeedingPoint.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), relationUserFeedingPoint.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getUserId())
      .append(getFeedingPoint())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("RelationUserFeedingPoint {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("userId=" + String.valueOf(getUserId()) + ", ")
      .append("feedingPoint=" + String.valueOf(getFeedingPoint()) + ", ")
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
  public static RelationUserFeedingPoint justId(String id) {
    return new RelationUserFeedingPoint(
      id,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      userId,
      feedingPoint);
  }
  public interface UserIdStep {
    FeedingPointStep userId(String userId);
  }
  

  public interface FeedingPointStep {
    BuildStep feedingPoint(FeedingPoint feedingPoint);
  }
  

  public interface BuildStep {
    RelationUserFeedingPoint build();
    BuildStep id(String id);
  }
  

  public static class Builder implements UserIdStep, FeedingPointStep, BuildStep {
    private String id;
    private String userId;
    private FeedingPoint feedingPoint;
    @Override
     public RelationUserFeedingPoint build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new RelationUserFeedingPoint(
          id,
          userId,
          feedingPoint);
    }
    
    @Override
     public FeedingPointStep userId(String userId) {
        Objects.requireNonNull(userId);
        this.userId = userId;
        return this;
    }
    
    @Override
     public BuildStep feedingPoint(FeedingPoint feedingPoint) {
        Objects.requireNonNull(feedingPoint);
        this.feedingPoint = feedingPoint;
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
    private CopyOfBuilder(String id, String userId, FeedingPoint feedingPoint) {
      super.id(id);
      super.userId(userId)
        .feedingPoint(feedingPoint);
    }
    
    @Override
     public CopyOfBuilder userId(String userId) {
      return (CopyOfBuilder) super.userId(userId);
    }
    
    @Override
     public CopyOfBuilder feedingPoint(FeedingPoint feedingPoint) {
      return (CopyOfBuilder) super.feedingPoint(feedingPoint);
    }
  }
  
}
