package com.amplifyframework.datastore.generated.model;

import static com.amplifyframework.core.model.query.predicate.QueryField.field;

import androidx.core.util.ObjectsCompat;

import com.amplifyframework.core.model.AuthStrategy;
import com.amplifyframework.core.model.Model;
import com.amplifyframework.core.model.ModelOperation;
import com.amplifyframework.core.model.annotations.AuthRule;
import com.amplifyframework.core.model.annotations.ModelConfig;
import com.amplifyframework.core.model.annotations.ModelField;
import com.amplifyframework.core.model.query.predicate.QueryField;
import com.amplifyframework.core.model.temporal.Temporal;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

/** This is an auto generated class representing the FeedingHistory type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "FeedingHistories", authRules = {
  @AuthRule(allow = AuthStrategy.GROUPS, groupClaim = "cognito:groups", groups = { "Administrator" }, provider = "userPools", operations = { ModelOperation.CREATE, ModelOperation.READ, ModelOperation.UPDATE, ModelOperation.DELETE }),
  @AuthRule(allow = AuthStrategy.GROUPS, groupClaim = "cognito:groups", groups = { "Moderator" }, provider = "userPools", operations = { ModelOperation.CREATE, ModelOperation.READ, ModelOperation.UPDATE, ModelOperation.DELETE }),
  @AuthRule(allow = AuthStrategy.GROUPS, groupClaim = "cognito:groups", groups = { "Volunteer" }, provider = "userPools", operations = { ModelOperation.READ }),
  @AuthRule(allow = AuthStrategy.OWNER, ownerField = "owner", identityClaim = "cognito:username", provider = "userPools", operations = { ModelOperation.CREATE, ModelOperation.READ, ModelOperation.UPDATE, ModelOperation.DELETE }),
  @AuthRule(allow = AuthStrategy.PUBLIC, provider = "apiKey", operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ }),
  @AuthRule(allow = AuthStrategy.PRIVATE, operations = { ModelOperation.READ })
})
public final class FeedingHistory implements Model {
  public static final QueryField ID = field("FeedingHistory", "id");
  public static final QueryField USER_ID = field("FeedingHistory", "userId");
  public static final QueryField IMAGES = field("FeedingHistory", "images");
  public static final QueryField CREATED_AT = field("FeedingHistory", "createdAt");
  public static final QueryField UPDATED_AT = field("FeedingHistory", "updatedAt");
  public static final QueryField CREATED_BY = field("FeedingHistory", "createdBy");
  public static final QueryField UPDATED_BY = field("FeedingHistory", "updatedBy");
  public static final QueryField OWNER = field("FeedingHistory", "owner");
  public static final QueryField FEEDING_POINT_ID = field("FeedingHistory", "feedingPointId");
  public static final QueryField STATUS = field("FeedingHistory", "status");
  public static final QueryField REASON = field("FeedingHistory", "reason");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String userId;
  private final @ModelField(targetType="String", isRequired = true) List<String> images;
  private final @ModelField(targetType="AWSDateTime", isRequired = true) Temporal.DateTime createdAt;
  private final @ModelField(targetType="AWSDateTime", isRequired = true) Temporal.DateTime updatedAt;
  private final @ModelField(targetType="String") String createdBy;
  private final @ModelField(targetType="String") String updatedBy;
  private final @ModelField(targetType="String") String owner;
  private final @ModelField(targetType="String", isRequired = true) String feedingPointId;
  private final @ModelField(targetType="FeedingStatus") FeedingStatus status;
  private final @ModelField(targetType="String") String reason;
  public String getId() {
      return id;
  }
  
  public String getUserId() {
      return userId;
  }
  
  public List<String> getImages() {
      return images;
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
  
  public String getFeedingPointId() {
      return feedingPointId;
  }
  
  public FeedingStatus getStatus() {
      return status;
  }
  
  public String getReason() {
      return reason;
  }
  
  private FeedingHistory(String id, String userId, List<String> images, Temporal.DateTime createdAt, Temporal.DateTime updatedAt, String createdBy, String updatedBy, String owner, String feedingPointId, FeedingStatus status, String reason) {
    this.id = id;
    this.userId = userId;
    this.images = images;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.createdBy = createdBy;
    this.updatedBy = updatedBy;
    this.owner = owner;
    this.feedingPointId = feedingPointId;
    this.status = status;
    this.reason = reason;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      FeedingHistory feedingHistory = (FeedingHistory) obj;
      return ObjectsCompat.equals(getId(), feedingHistory.getId()) &&
              ObjectsCompat.equals(getUserId(), feedingHistory.getUserId()) &&
              ObjectsCompat.equals(getImages(), feedingHistory.getImages()) &&
              ObjectsCompat.equals(getCreatedAt(), feedingHistory.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), feedingHistory.getUpdatedAt()) &&
              ObjectsCompat.equals(getCreatedBy(), feedingHistory.getCreatedBy()) &&
              ObjectsCompat.equals(getUpdatedBy(), feedingHistory.getUpdatedBy()) &&
              ObjectsCompat.equals(getOwner(), feedingHistory.getOwner()) &&
              ObjectsCompat.equals(getFeedingPointId(), feedingHistory.getFeedingPointId()) &&
              ObjectsCompat.equals(getStatus(), feedingHistory.getStatus()) &&
              ObjectsCompat.equals(getReason(), feedingHistory.getReason());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getUserId())
      .append(getImages())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .append(getCreatedBy())
      .append(getUpdatedBy())
      .append(getOwner())
      .append(getFeedingPointId())
      .append(getStatus())
      .append(getReason())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("FeedingHistory {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("userId=" + String.valueOf(getUserId()) + ", ")
      .append("images=" + String.valueOf(getImages()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()) + ", ")
      .append("createdBy=" + String.valueOf(getCreatedBy()) + ", ")
      .append("updatedBy=" + String.valueOf(getUpdatedBy()) + ", ")
      .append("owner=" + String.valueOf(getOwner()) + ", ")
      .append("feedingPointId=" + String.valueOf(getFeedingPointId()) + ", ")
      .append("status=" + String.valueOf(getStatus()) + ", ")
      .append("reason=" + String.valueOf(getReason()))
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
  public static FeedingHistory justId(String id) {
    return new FeedingHistory(
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
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      userId,
      images,
      createdAt,
      updatedAt,
      createdBy,
      updatedBy,
      owner,
      feedingPointId,
      status,
      reason);
  }
  public interface UserIdStep {
    ImagesStep userId(String userId);
  }
  

  public interface ImagesStep {
    CreatedAtStep images(List<String> images);
  }
  

  public interface CreatedAtStep {
    UpdatedAtStep createdAt(Temporal.DateTime createdAt);
  }
  

  public interface UpdatedAtStep {
    FeedingPointIdStep updatedAt(Temporal.DateTime updatedAt);
  }
  

  public interface FeedingPointIdStep {
    BuildStep feedingPointId(String feedingPointId);
  }
  

  public interface BuildStep {
    FeedingHistory build();
    BuildStep id(String id);
    BuildStep createdBy(String createdBy);
    BuildStep updatedBy(String updatedBy);
    BuildStep owner(String owner);
    BuildStep status(FeedingStatus status);
    BuildStep reason(String reason);
  }
  

  public static class Builder implements UserIdStep, ImagesStep, CreatedAtStep, UpdatedAtStep, FeedingPointIdStep, BuildStep {
    private String id;
    private String userId;
    private List<String> images;
    private Temporal.DateTime createdAt;
    private Temporal.DateTime updatedAt;
    private String feedingPointId;
    private String createdBy;
    private String updatedBy;
    private String owner;
    private FeedingStatus status;
    private String reason;
    @Override
     public FeedingHistory build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new FeedingHistory(
          id,
          userId,
          images,
          createdAt,
          updatedAt,
          createdBy,
          updatedBy,
          owner,
          feedingPointId,
          status,
          reason);
    }
    
    @Override
     public ImagesStep userId(String userId) {
        Objects.requireNonNull(userId);
        this.userId = userId;
        return this;
    }
    
    @Override
     public CreatedAtStep images(List<String> images) {
        Objects.requireNonNull(images);
        this.images = images;
        return this;
    }
    
    @Override
     public UpdatedAtStep createdAt(Temporal.DateTime createdAt) {
        Objects.requireNonNull(createdAt);
        this.createdAt = createdAt;
        return this;
    }
    
    @Override
     public FeedingPointIdStep updatedAt(Temporal.DateTime updatedAt) {
        Objects.requireNonNull(updatedAt);
        this.updatedAt = updatedAt;
        return this;
    }
    
    @Override
     public BuildStep feedingPointId(String feedingPointId) {
        Objects.requireNonNull(feedingPointId);
        this.feedingPointId = feedingPointId;
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
     public BuildStep status(FeedingStatus status) {
        this.status = status;
        return this;
    }
    
    @Override
     public BuildStep reason(String reason) {
        this.reason = reason;
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
    private CopyOfBuilder(String id, String userId, List<String> images, Temporal.DateTime createdAt, Temporal.DateTime updatedAt, String createdBy, String updatedBy, String owner, String feedingPointId, FeedingStatus status, String reason) {
      super.id(id);
      super.userId(userId)
        .images(images)
        .createdAt(createdAt)
        .updatedAt(updatedAt)
        .feedingPointId(feedingPointId)
        .createdBy(createdBy)
        .updatedBy(updatedBy)
        .owner(owner)
        .status(status)
        .reason(reason);
    }
    
    @Override
     public CopyOfBuilder userId(String userId) {
      return (CopyOfBuilder) super.userId(userId);
    }
    
    @Override
     public CopyOfBuilder images(List<String> images) {
      return (CopyOfBuilder) super.images(images);
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
     public CopyOfBuilder feedingPointId(String feedingPointId) {
      return (CopyOfBuilder) super.feedingPointId(feedingPointId);
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
     public CopyOfBuilder status(FeedingStatus status) {
      return (CopyOfBuilder) super.status(status);
    }
    
    @Override
     public CopyOfBuilder reason(String reason) {
      return (CopyOfBuilder) super.reason(reason);
    }
  }
  
}
