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

/** This is an auto generated class representing the Language type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Languages", authRules = {
  @AuthRule(allow = AuthStrategy.GROUPS, groupClaim = "cognito:groups", groups = { "Administrator" }, provider = "userPools", operations = { ModelOperation.CREATE, ModelOperation.READ, ModelOperation.UPDATE, ModelOperation.DELETE }),
  @AuthRule(allow = AuthStrategy.GROUPS, groupClaim = "cognito:groups", groups = { "Moderator" }, provider = "userPools", operations = { ModelOperation.CREATE, ModelOperation.READ, ModelOperation.UPDATE, ModelOperation.DELETE }),
  @AuthRule(allow = AuthStrategy.GROUPS, groupClaim = "cognito:groups", groups = { "Volunteer" }, provider = "userPools", operations = { ModelOperation.READ }),
  @AuthRule(allow = AuthStrategy.OWNER, ownerField = "owner", identityClaim = "cognito:username", provider = "userPools", operations = { ModelOperation.CREATE, ModelOperation.READ, ModelOperation.UPDATE, ModelOperation.DELETE }),
  @AuthRule(allow = AuthStrategy.PUBLIC, provider = "apiKey", operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ }),
  @AuthRule(allow = AuthStrategy.PRIVATE, operations = { ModelOperation.READ })
})
public final class Language implements Model {
  public static final QueryField ID = field("Language", "id");
  public static final QueryField NAME = field("Language", "name");
  public static final QueryField CODE = field("Language", "code");
  public static final QueryField CREATED_AT = field("Language", "createdAt");
  public static final QueryField UPDATED_AT = field("Language", "updatedAt");
  public static final QueryField CREATED_BY = field("Language", "createdBy");
  public static final QueryField UPDATED_BY = field("Language", "updatedBy");
  public static final QueryField DIRECTION = field("Language", "direction");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String name;
  private final @ModelField(targetType="String", isRequired = true) String code;
  private final @ModelField(targetType="AWSDateTime", isRequired = true) Temporal.DateTime createdAt;
  private final @ModelField(targetType="AWSDateTime", isRequired = true) Temporal.DateTime updatedAt;
  private final @ModelField(targetType="String") String createdBy;
  private final @ModelField(targetType="String") String updatedBy;
  private final @ModelField(targetType="String", isRequired = true) String direction;
  public String getId() {
      return id;
  }
  
  public String getName() {
      return name;
  }
  
  public String getCode() {
      return code;
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
  
  public String getDirection() {
      return direction;
  }
  
  private Language(String id, String name, String code, Temporal.DateTime createdAt, Temporal.DateTime updatedAt, String createdBy, String updatedBy, String direction) {
    this.id = id;
    this.name = name;
    this.code = code;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.createdBy = createdBy;
    this.updatedBy = updatedBy;
    this.direction = direction;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Language language = (Language) obj;
      return ObjectsCompat.equals(getId(), language.getId()) &&
              ObjectsCompat.equals(getName(), language.getName()) &&
              ObjectsCompat.equals(getCode(), language.getCode()) &&
              ObjectsCompat.equals(getCreatedAt(), language.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), language.getUpdatedAt()) &&
              ObjectsCompat.equals(getCreatedBy(), language.getCreatedBy()) &&
              ObjectsCompat.equals(getUpdatedBy(), language.getUpdatedBy()) &&
              ObjectsCompat.equals(getDirection(), language.getDirection());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getName())
      .append(getCode())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .append(getCreatedBy())
      .append(getUpdatedBy())
      .append(getDirection())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Language {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("name=" + String.valueOf(getName()) + ", ")
      .append("code=" + String.valueOf(getCode()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()) + ", ")
      .append("createdBy=" + String.valueOf(getCreatedBy()) + ", ")
      .append("updatedBy=" + String.valueOf(getUpdatedBy()) + ", ")
      .append("direction=" + String.valueOf(getDirection()))
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
  public static Language justId(String id) {
    return new Language(
      id,
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
      code,
      createdAt,
      updatedAt,
      createdBy,
      updatedBy,
      direction);
  }
  public interface NameStep {
    CodeStep name(String name);
  }
  

  public interface CodeStep {
    CreatedAtStep code(String code);
  }
  

  public interface CreatedAtStep {
    UpdatedAtStep createdAt(Temporal.DateTime createdAt);
  }
  

  public interface UpdatedAtStep {
    DirectionStep updatedAt(Temporal.DateTime updatedAt);
  }
  

  public interface DirectionStep {
    BuildStep direction(String direction);
  }
  

  public interface BuildStep {
    Language build();
    BuildStep id(String id);
    BuildStep createdBy(String createdBy);
    BuildStep updatedBy(String updatedBy);
  }
  

  public static class Builder implements NameStep, CodeStep, CreatedAtStep, UpdatedAtStep, DirectionStep, BuildStep {
    private String id;
    private String name;
    private String code;
    private Temporal.DateTime createdAt;
    private Temporal.DateTime updatedAt;
    private String direction;
    private String createdBy;
    private String updatedBy;
    @Override
     public Language build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Language(
          id,
          name,
          code,
          createdAt,
          updatedAt,
          createdBy,
          updatedBy,
          direction);
    }
    
    @Override
     public CodeStep name(String name) {
        Objects.requireNonNull(name);
        this.name = name;
        return this;
    }
    
    @Override
     public CreatedAtStep code(String code) {
        Objects.requireNonNull(code);
        this.code = code;
        return this;
    }
    
    @Override
     public UpdatedAtStep createdAt(Temporal.DateTime createdAt) {
        Objects.requireNonNull(createdAt);
        this.createdAt = createdAt;
        return this;
    }
    
    @Override
     public DirectionStep updatedAt(Temporal.DateTime updatedAt) {
        Objects.requireNonNull(updatedAt);
        this.updatedAt = updatedAt;
        return this;
    }
    
    @Override
     public BuildStep direction(String direction) {
        Objects.requireNonNull(direction);
        this.direction = direction;
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
    private CopyOfBuilder(String id, String name, String code, Temporal.DateTime createdAt, Temporal.DateTime updatedAt, String createdBy, String updatedBy, String direction) {
      super.id(id);
      super.name(name)
        .code(code)
        .createdAt(createdAt)
        .updatedAt(updatedAt)
        .direction(direction)
        .createdBy(createdBy)
        .updatedBy(updatedBy);
    }
    
    @Override
     public CopyOfBuilder name(String name) {
      return (CopyOfBuilder) super.name(name);
    }
    
    @Override
     public CopyOfBuilder code(String code) {
      return (CopyOfBuilder) super.code(code);
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
     public CopyOfBuilder direction(String direction) {
      return (CopyOfBuilder) super.direction(direction);
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
