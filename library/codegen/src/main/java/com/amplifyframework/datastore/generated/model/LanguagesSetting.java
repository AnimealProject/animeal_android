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

/** This is an auto generated class representing the LanguagesSetting type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "LanguagesSettings", authRules = {
  @AuthRule(allow = AuthStrategy.GROUPS, groupClaim = "cognito:groups", groups = { "Administrator" }, provider = "userPools", operations = { ModelOperation.CREATE, ModelOperation.READ, ModelOperation.UPDATE, ModelOperation.DELETE }),
  @AuthRule(allow = AuthStrategy.GROUPS, groupClaim = "cognito:groups", groups = { "Moderator" }, provider = "userPools", operations = { ModelOperation.CREATE, ModelOperation.READ, ModelOperation.UPDATE, ModelOperation.DELETE }),
  @AuthRule(allow = AuthStrategy.GROUPS, groupClaim = "cognito:groups", groups = { "Volunteer" }, provider = "userPools", operations = { ModelOperation.READ }),
  @AuthRule(allow = AuthStrategy.OWNER, ownerField = "owner", identityClaim = "cognito:username", provider = "userPools", operations = { ModelOperation.CREATE, ModelOperation.READ, ModelOperation.UPDATE, ModelOperation.DELETE }),
  @AuthRule(allow = AuthStrategy.PUBLIC, provider = "apiKey", operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ }),
  @AuthRule(allow = AuthStrategy.PRIVATE, operations = { ModelOperation.READ })
})
public final class LanguagesSetting implements Model {
  public static final QueryField ID = field("LanguagesSetting", "id");
  public static final QueryField NAME = field("LanguagesSetting", "name");
  public static final QueryField VALUE = field("LanguagesSetting", "value");
  public static final QueryField CREATED_AT = field("LanguagesSetting", "createdAt");
  public static final QueryField UPDATED_AT = field("LanguagesSetting", "updatedAt");
  public static final QueryField CREATED_BY = field("LanguagesSetting", "createdBy");
  public static final QueryField UPDATED_BY = field("LanguagesSetting", "updatedBy");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String name;
  private final @ModelField(targetType="String", isRequired = true) String value;
  private final @ModelField(targetType="AWSDateTime", isRequired = true) Temporal.DateTime createdAt;
  private final @ModelField(targetType="AWSDateTime", isRequired = true) Temporal.DateTime updatedAt;
  private final @ModelField(targetType="String") String createdBy;
  private final @ModelField(targetType="String") String updatedBy;
  public String getId() {
      return id;
  }
  
  public String getName() {
      return name;
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
  
  public String getCreatedBy() {
      return createdBy;
  }
  
  public String getUpdatedBy() {
      return updatedBy;
  }
  
  private LanguagesSetting(String id, String name, String value, Temporal.DateTime createdAt, Temporal.DateTime updatedAt, String createdBy, String updatedBy) {
    this.id = id;
    this.name = name;
    this.value = value;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.createdBy = createdBy;
    this.updatedBy = updatedBy;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      LanguagesSetting languagesSetting = (LanguagesSetting) obj;
      return ObjectsCompat.equals(getId(), languagesSetting.getId()) &&
              ObjectsCompat.equals(getName(), languagesSetting.getName()) &&
              ObjectsCompat.equals(getValue(), languagesSetting.getValue()) &&
              ObjectsCompat.equals(getCreatedAt(), languagesSetting.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), languagesSetting.getUpdatedAt()) &&
              ObjectsCompat.equals(getCreatedBy(), languagesSetting.getCreatedBy()) &&
              ObjectsCompat.equals(getUpdatedBy(), languagesSetting.getUpdatedBy());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getName())
      .append(getValue())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .append(getCreatedBy())
      .append(getUpdatedBy())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("LanguagesSetting {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("name=" + String.valueOf(getName()) + ", ")
      .append("value=" + String.valueOf(getValue()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()) + ", ")
      .append("createdBy=" + String.valueOf(getCreatedBy()) + ", ")
      .append("updatedBy=" + String.valueOf(getUpdatedBy()))
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
  public static LanguagesSetting justId(String id) {
    return new LanguagesSetting(
      id,
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
      value,
      createdAt,
      updatedAt,
      createdBy,
      updatedBy);
  }
  public interface NameStep {
    ValueStep name(String name);
  }
  

  public interface ValueStep {
    CreatedAtStep value(String value);
  }
  

  public interface CreatedAtStep {
    UpdatedAtStep createdAt(Temporal.DateTime createdAt);
  }
  

  public interface UpdatedAtStep {
    BuildStep updatedAt(Temporal.DateTime updatedAt);
  }
  

  public interface BuildStep {
    LanguagesSetting build();
    BuildStep id(String id);
    BuildStep createdBy(String createdBy);
    BuildStep updatedBy(String updatedBy);
  }
  

  public static class Builder implements NameStep, ValueStep, CreatedAtStep, UpdatedAtStep, BuildStep {
    private String id;
    private String name;
    private String value;
    private Temporal.DateTime createdAt;
    private Temporal.DateTime updatedAt;
    private String createdBy;
    private String updatedBy;
    @Override
     public LanguagesSetting build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new LanguagesSetting(
          id,
          name,
          value,
          createdAt,
          updatedAt,
          createdBy,
          updatedBy);
    }
    
    @Override
     public ValueStep name(String name) {
        Objects.requireNonNull(name);
        this.name = name;
        return this;
    }
    
    @Override
     public CreatedAtStep value(String value) {
        Objects.requireNonNull(value);
        this.value = value;
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
     public BuildStep createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }
    
    @Override
     public BuildStep updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
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
    private CopyOfBuilder(String id, String name, String value, Temporal.DateTime createdAt, Temporal.DateTime updatedAt, String createdBy, String updatedBy) {
      super.id(id);
      super.name(name)
        .value(value)
        .createdAt(createdAt)
        .updatedAt(updatedAt)
        .createdBy(createdBy)
        .updatedBy(updatedBy);
    }
    
    @Override
     public CopyOfBuilder name(String name) {
      return (CopyOfBuilder) super.name(name);
    }
    
    @Override
     public CopyOfBuilder value(String value) {
      return (CopyOfBuilder) super.value(value);
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
     public CopyOfBuilder createdBy(String createdBy) {
      return (CopyOfBuilder) super.createdBy(createdBy);
    }
    
    @Override
     public CopyOfBuilder updatedBy(String updatedBy) {
      return (CopyOfBuilder) super.updatedBy(updatedBy);
    }
  }
  
}
