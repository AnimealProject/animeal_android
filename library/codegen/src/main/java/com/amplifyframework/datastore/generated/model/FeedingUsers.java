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
import java.util.UUID;

/** This is an auto generated class representing the FeedingUsers type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "FeedingUsers", authRules = {
  @AuthRule(allow = AuthStrategy.GROUPS, groupClaim = "cognito:groups", groups = { "Administrator" }, provider = "userPools", operations = { ModelOperation.CREATE, ModelOperation.READ, ModelOperation.UPDATE, ModelOperation.DELETE }),
  @AuthRule(allow = AuthStrategy.GROUPS, groupClaim = "cognito:groups", groups = { "Moderator" }, provider = "userPools", operations = { ModelOperation.CREATE, ModelOperation.READ, ModelOperation.UPDATE, ModelOperation.DELETE }),
  @AuthRule(allow = AuthStrategy.GROUPS, groupClaim = "cognito:groups", groups = { "Volunteer" }, provider = "userPools", operations = { ModelOperation.READ }),
  @AuthRule(allow = AuthStrategy.OWNER, ownerField = "owner", identityClaim = "cognito:username", provider = "userPools", operations = { ModelOperation.CREATE, ModelOperation.READ, ModelOperation.UPDATE, ModelOperation.DELETE }),
  @AuthRule(allow = AuthStrategy.PUBLIC, provider = "apiKey", operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ }),
  @AuthRule(allow = AuthStrategy.PRIVATE, operations = { ModelOperation.READ })
})
public final class FeedingUsers implements Model {
  public static final QueryField ID = field("FeedingUsers", "id");
  public static final QueryField ATTRIBUTES = field("FeedingUsers", "attributes");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="UserAttribute") List<UserAttribute> attributes;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  public String getId() {
      return id;
  }
  
  public List<UserAttribute> getAttributes() {
      return attributes;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private FeedingUsers(String id, List<UserAttribute> attributes) {
    this.id = id;
    this.attributes = attributes;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      FeedingUsers feedingUsers = (FeedingUsers) obj;
      return ObjectsCompat.equals(getId(), feedingUsers.getId()) &&
              ObjectsCompat.equals(getAttributes(), feedingUsers.getAttributes()) &&
              ObjectsCompat.equals(getCreatedAt(), feedingUsers.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), feedingUsers.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getAttributes())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("FeedingUsers {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("attributes=" + String.valueOf(getAttributes()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static BuildStep builder() {
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
  public static FeedingUsers justId(String id) {
    return new FeedingUsers(
      id,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      attributes);
  }
  public interface BuildStep {
    FeedingUsers build();
    BuildStep id(String id);
    BuildStep attributes(List<UserAttribute> attributes);
  }
  

  public static class Builder implements BuildStep {
    private String id;
    private List<UserAttribute> attributes;
    @Override
     public FeedingUsers build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new FeedingUsers(
          id,
          attributes);
    }
    
    @Override
     public BuildStep attributes(List<UserAttribute> attributes) {
        this.attributes = attributes;
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
    private CopyOfBuilder(String id, List<UserAttribute> attributes) {
      super.id(id);
      super.attributes(attributes);
    }
    
    @Override
     public CopyOfBuilder attributes(List<UserAttribute> attributes) {
      return (CopyOfBuilder) super.attributes(attributes);
    }
  }
  
}
