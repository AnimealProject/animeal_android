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

import java.util.Objects;
import java.util.UUID;

/** This is an auto generated class representing the Settings type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Settings", authRules = {
  @AuthRule(allow = AuthStrategy.GROUPS, groupClaim = "cognito:groups", groups = { "Administrator" }, provider = "userPools", operations = { ModelOperation.CREATE, ModelOperation.READ, ModelOperation.UPDATE, ModelOperation.DELETE }),
  @AuthRule(allow = AuthStrategy.GROUPS, groupClaim = "cognito:groups", groups = { "Moderator" }, provider = "userPools", operations = { ModelOperation.CREATE, ModelOperation.READ, ModelOperation.UPDATE, ModelOperation.DELETE }),
  @AuthRule(allow = AuthStrategy.GROUPS, groupClaim = "cognito:groups", groups = { "Volunteer" }, provider = "userPools", operations = { ModelOperation.READ }),
  @AuthRule(allow = AuthStrategy.OWNER, ownerField = "owner", identityClaim = "cognito:username", provider = "userPools", operations = { ModelOperation.CREATE, ModelOperation.READ, ModelOperation.UPDATE, ModelOperation.DELETE }),
  @AuthRule(allow = AuthStrategy.PUBLIC, provider = "apiKey", operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ }),
  @AuthRule(allow = AuthStrategy.PRIVATE, operations = { ModelOperation.READ })
})
public final class Settings implements Model {
  public static final QueryField ID = field("Settings", "id");
  public static final QueryField KEY = field("Settings", "key");
  public static final QueryField VALUE = field("Settings", "value");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String key;
  private final @ModelField(targetType="String", isRequired = true) String value;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  public String getId() {
      return id;
  }
  
  public String getKey() {
      return key;
  }
  
  public String getValue() {
      return value;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private Settings(String id, String key, String value) {
    this.id = id;
    this.key = key;
    this.value = value;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Settings settings = (Settings) obj;
      return ObjectsCompat.equals(getId(), settings.getId()) &&
              ObjectsCompat.equals(getKey(), settings.getKey()) &&
              ObjectsCompat.equals(getValue(), settings.getValue()) &&
              ObjectsCompat.equals(getCreatedAt(), settings.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), settings.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getKey())
      .append(getValue())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Settings {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("key=" + String.valueOf(getKey()) + ", ")
      .append("value=" + String.valueOf(getValue()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static KeyStep builder() {
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
  public static Settings justId(String id) {
    return new Settings(
      id,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      key,
      value);
  }
  public interface KeyStep {
    ValueStep key(String key);
  }
  

  public interface ValueStep {
    BuildStep value(String value);
  }
  

  public interface BuildStep {
    Settings build();
    BuildStep id(String id);
  }
  

  public static class Builder implements KeyStep, ValueStep, BuildStep {
    private String id;
    private String key;
    private String value;
    @Override
     public Settings build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Settings(
          id,
          key,
          value);
    }
    
    @Override
     public ValueStep key(String key) {
        Objects.requireNonNull(key);
        this.key = key;
        return this;
    }
    
    @Override
     public BuildStep value(String value) {
        Objects.requireNonNull(value);
        this.value = value;
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
    private CopyOfBuilder(String id, String key, String value) {
      super.id(id);
      super.key(key)
        .value(value);
    }
    
    @Override
     public CopyOfBuilder key(String key) {
      return (CopyOfBuilder) super.key(key);
    }
    
    @Override
     public CopyOfBuilder value(String value) {
      return (CopyOfBuilder) super.value(value);
    }
  }
  
}
