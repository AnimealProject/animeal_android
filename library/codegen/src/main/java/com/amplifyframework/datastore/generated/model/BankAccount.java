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

/** This is an auto generated class representing the BankAccount type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "BankAccounts", authRules = {
  @AuthRule(allow = AuthStrategy.GROUPS, groupClaim = "cognito:groups", groups = { "Administrator" }, provider = "userPools", operations = { ModelOperation.CREATE, ModelOperation.READ, ModelOperation.UPDATE, ModelOperation.DELETE }),
  @AuthRule(allow = AuthStrategy.GROUPS, groupClaim = "cognito:groups", groups = { "Moderator" }, provider = "userPools", operations = { ModelOperation.CREATE, ModelOperation.READ, ModelOperation.UPDATE, ModelOperation.DELETE }),
  @AuthRule(allow = AuthStrategy.GROUPS, groupClaim = "cognito:groups", groups = { "Volunteer" }, provider = "userPools", operations = { ModelOperation.READ }),
  @AuthRule(allow = AuthStrategy.OWNER, ownerField = "owner", identityClaim = "cognito:username", provider = "userPools", operations = { ModelOperation.CREATE, ModelOperation.READ, ModelOperation.UPDATE, ModelOperation.DELETE }),
  @AuthRule(allow = AuthStrategy.PUBLIC, provider = "apiKey", operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ }),
  @AuthRule(allow = AuthStrategy.PRIVATE, operations = { ModelOperation.READ })
})
public final class BankAccount implements Model {
  public static final QueryField ID = field("BankAccount", "id");
  public static final QueryField NAME = field("BankAccount", "name");
  public static final QueryField VALUE = field("BankAccount", "value");
  public static final QueryField COVER = field("BankAccount", "cover");
  public static final QueryField IMAGES = field("BankAccount", "images");
  public static final QueryField ENABLED = field("BankAccount", "enabled");
  public static final QueryField CREATED_BY = field("BankAccount", "createdBy");
  public static final QueryField UPDATED_BY = field("BankAccount", "updatedBy");
  public static final QueryField OWNER = field("BankAccount", "owner");
  public static final QueryField CREATED_AT = field("BankAccount", "createdAt");
  public static final QueryField UPDATED_AT = field("BankAccount", "updatedAt");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String name;
  private final @ModelField(targetType="String", isRequired = true) String value;
  private final @ModelField(targetType="String", isRequired = true) String cover;
  private final @ModelField(targetType="String") List<String> images;
  private final @ModelField(targetType="Boolean", isRequired = true) Boolean enabled;
  private final @ModelField(targetType="String") String createdBy;
  private final @ModelField(targetType="String") String updatedBy;
  private final @ModelField(targetType="String") String owner;
  private final @ModelField(targetType="AWSDateTime", isRequired = true) Temporal.DateTime createdAt;
  private final @ModelField(targetType="AWSDateTime", isRequired = true) Temporal.DateTime updatedAt;
  public String getId() {
      return id;
  }
  
  public String getName() {
      return name;
  }
  
  public String getValue() {
      return value;
  }
  
  public String getCover() {
      return cover;
  }
  
  public List<String> getImages() {
      return images;
  }
  
  public Boolean getEnabled() {
      return enabled;
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
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private BankAccount(String id, String name, String value, String cover, List<String> images, Boolean enabled, String createdBy, String updatedBy, String owner, Temporal.DateTime createdAt, Temporal.DateTime updatedAt) {
    this.id = id;
    this.name = name;
    this.value = value;
    this.cover = cover;
    this.images = images;
    this.enabled = enabled;
    this.createdBy = createdBy;
    this.updatedBy = updatedBy;
    this.owner = owner;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      BankAccount bankAccount = (BankAccount) obj;
      return ObjectsCompat.equals(getId(), bankAccount.getId()) &&
              ObjectsCompat.equals(getName(), bankAccount.getName()) &&
              ObjectsCompat.equals(getValue(), bankAccount.getValue()) &&
              ObjectsCompat.equals(getCover(), bankAccount.getCover()) &&
              ObjectsCompat.equals(getImages(), bankAccount.getImages()) &&
              ObjectsCompat.equals(getEnabled(), bankAccount.getEnabled()) &&
              ObjectsCompat.equals(getCreatedBy(), bankAccount.getCreatedBy()) &&
              ObjectsCompat.equals(getUpdatedBy(), bankAccount.getUpdatedBy()) &&
              ObjectsCompat.equals(getOwner(), bankAccount.getOwner()) &&
              ObjectsCompat.equals(getCreatedAt(), bankAccount.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), bankAccount.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getName())
      .append(getValue())
      .append(getCover())
      .append(getImages())
      .append(getEnabled())
      .append(getCreatedBy())
      .append(getUpdatedBy())
      .append(getOwner())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("BankAccount {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("name=" + String.valueOf(getName()) + ", ")
      .append("value=" + String.valueOf(getValue()) + ", ")
      .append("cover=" + String.valueOf(getCover()) + ", ")
      .append("images=" + String.valueOf(getImages()) + ", ")
      .append("enabled=" + String.valueOf(getEnabled()) + ", ")
      .append("createdBy=" + String.valueOf(getCreatedBy()) + ", ")
      .append("updatedBy=" + String.valueOf(getUpdatedBy()) + ", ")
      .append("owner=" + String.valueOf(getOwner()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
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
  public static BankAccount justId(String id) {
    return new BankAccount(
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
      name,
      value,
      cover,
      images,
      enabled,
      createdBy,
      updatedBy,
      owner,
      createdAt,
      updatedAt);
  }
  public interface NameStep {
    ValueStep name(String name);
  }
  

  public interface ValueStep {
    CoverStep value(String value);
  }
  

  public interface CoverStep {
    EnabledStep cover(String cover);
  }
  

  public interface EnabledStep {
    CreatedAtStep enabled(Boolean enabled);
  }
  

  public interface CreatedAtStep {
    UpdatedAtStep createdAt(Temporal.DateTime createdAt);
  }
  

  public interface UpdatedAtStep {
    BuildStep updatedAt(Temporal.DateTime updatedAt);
  }
  

  public interface BuildStep {
    BankAccount build();
    BuildStep id(String id);
    BuildStep images(List<String> images);
    BuildStep createdBy(String createdBy);
    BuildStep updatedBy(String updatedBy);
    BuildStep owner(String owner);
  }
  

  public static class Builder implements NameStep, ValueStep, CoverStep, EnabledStep, CreatedAtStep, UpdatedAtStep, BuildStep {
    private String id;
    private String name;
    private String value;
    private String cover;
    private Boolean enabled;
    private Temporal.DateTime createdAt;
    private Temporal.DateTime updatedAt;
    private List<String> images;
    private String createdBy;
    private String updatedBy;
    private String owner;
    @Override
     public BankAccount build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new BankAccount(
          id,
          name,
          value,
          cover,
          images,
          enabled,
          createdBy,
          updatedBy,
          owner,
          createdAt,
          updatedAt);
    }
    
    @Override
     public ValueStep name(String name) {
        Objects.requireNonNull(name);
        this.name = name;
        return this;
    }
    
    @Override
     public CoverStep value(String value) {
        Objects.requireNonNull(value);
        this.value = value;
        return this;
    }
    
    @Override
     public EnabledStep cover(String cover) {
        Objects.requireNonNull(cover);
        this.cover = cover;
        return this;
    }
    
    @Override
     public CreatedAtStep enabled(Boolean enabled) {
        Objects.requireNonNull(enabled);
        this.enabled = enabled;
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
    private CopyOfBuilder(String id, String name, String value, String cover, List<String> images, Boolean enabled, String createdBy, String updatedBy, String owner, Temporal.DateTime createdAt, Temporal.DateTime updatedAt) {
      super.id(id);
      super.name(name)
        .value(value)
        .cover(cover)
        .enabled(enabled)
        .createdAt(createdAt)
        .updatedAt(updatedAt)
        .images(images)
        .createdBy(createdBy)
        .updatedBy(updatedBy)
        .owner(owner);
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
     public CopyOfBuilder cover(String cover) {
      return (CopyOfBuilder) super.cover(cover);
    }
    
    @Override
     public CopyOfBuilder enabled(Boolean enabled) {
      return (CopyOfBuilder) super.enabled(enabled);
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
  }
  
}
