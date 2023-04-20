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

/** This is an auto generated class representing the Question type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Questions", authRules = {
  @AuthRule(allow = AuthStrategy.GROUPS, groupClaim = "cognito:groups", groups = { "Administrator" }, provider = "userPools", operations = { ModelOperation.CREATE, ModelOperation.READ, ModelOperation.UPDATE, ModelOperation.DELETE }),
  @AuthRule(allow = AuthStrategy.GROUPS, groupClaim = "cognito:groups", groups = { "Moderator" }, provider = "userPools", operations = { ModelOperation.CREATE, ModelOperation.READ, ModelOperation.UPDATE, ModelOperation.DELETE }),
  @AuthRule(allow = AuthStrategy.GROUPS, groupClaim = "cognito:groups", groups = { "Volunteer" }, provider = "userPools", operations = { ModelOperation.READ }),
  @AuthRule(allow = AuthStrategy.OWNER, ownerField = "owner", identityClaim = "cognito:username", provider = "userPools", operations = { ModelOperation.CREATE, ModelOperation.READ, ModelOperation.UPDATE, ModelOperation.DELETE }),
  @AuthRule(allow = AuthStrategy.PUBLIC, provider = "apiKey", operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ }),
  @AuthRule(allow = AuthStrategy.PRIVATE, operations = { ModelOperation.READ })
})
public final class Question implements Model {
  public static final QueryField ID = field("Question", "id");
  public static final QueryField VALUE = field("Question", "value");
  public static final QueryField ANSWER = field("Question", "answer");
  public static final QueryField I18N = field("Question", "i18n");
  public static final QueryField CREATED_AT = field("Question", "createdAt");
  public static final QueryField UPDATED_AT = field("Question", "updatedAt");
  public static final QueryField CREATED_BY = field("Question", "createdBy");
  public static final QueryField UPDATED_BY = field("Question", "updatedBy");
  public static final QueryField OWNER = field("Question", "owner");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String") String value;
  private final @ModelField(targetType="String") String answer;
  private final @ModelField(targetType="QuestionI18n") List<QuestionI18n> i18n;
  private final @ModelField(targetType="AWSDateTime", isRequired = true) Temporal.DateTime createdAt;
  private final @ModelField(targetType="AWSDateTime", isRequired = true) Temporal.DateTime updatedAt;
  private final @ModelField(targetType="String") String createdBy;
  private final @ModelField(targetType="String") String updatedBy;
  private final @ModelField(targetType="String") String owner;
  public String getId() {
      return id;
  }
  
  public String getValue() {
      return value;
  }
  
  public String getAnswer() {
      return answer;
  }
  
  public List<QuestionI18n> getI18n() {
      return i18n;
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
  
  private Question(String id, String value, String answer, List<QuestionI18n> i18n, Temporal.DateTime createdAt, Temporal.DateTime updatedAt, String createdBy, String updatedBy, String owner) {
    this.id = id;
    this.value = value;
    this.answer = answer;
    this.i18n = i18n;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.createdBy = createdBy;
    this.updatedBy = updatedBy;
    this.owner = owner;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Question question = (Question) obj;
      return ObjectsCompat.equals(getId(), question.getId()) &&
              ObjectsCompat.equals(getValue(), question.getValue()) &&
              ObjectsCompat.equals(getAnswer(), question.getAnswer()) &&
              ObjectsCompat.equals(getI18n(), question.getI18n()) &&
              ObjectsCompat.equals(getCreatedAt(), question.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), question.getUpdatedAt()) &&
              ObjectsCompat.equals(getCreatedBy(), question.getCreatedBy()) &&
              ObjectsCompat.equals(getUpdatedBy(), question.getUpdatedBy()) &&
              ObjectsCompat.equals(getOwner(), question.getOwner());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getValue())
      .append(getAnswer())
      .append(getI18n())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .append(getCreatedBy())
      .append(getUpdatedBy())
      .append(getOwner())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Question {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("value=" + String.valueOf(getValue()) + ", ")
      .append("answer=" + String.valueOf(getAnswer()) + ", ")
      .append("i18n=" + String.valueOf(getI18n()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()) + ", ")
      .append("createdBy=" + String.valueOf(getCreatedBy()) + ", ")
      .append("updatedBy=" + String.valueOf(getUpdatedBy()) + ", ")
      .append("owner=" + String.valueOf(getOwner()))
      .append("}")
      .toString();
  }

  /**
   * WARNING: This method should not be used to build an instance of this object for a CREATE mutation.
   * This is a convenience method to return an instance of the object with only its ID populated
   * to be used in the context of a parameter in a delete mutation or referencing a foreign key
   * in a relationship.
   * @param id the id of the existing item this instance will represent
   * @return an instance of this model with only ID populated
   */
  public static Question justId(String id) {
    return new Question(
      id,
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
      value,
      answer,
      i18n,
      createdAt,
      updatedAt,
      createdBy,
      updatedBy,
      owner);
  }
  public interface CreatedAtStep {
    UpdatedAtStep createdAt(Temporal.DateTime createdAt);
  }
  

  public interface UpdatedAtStep {
    BuildStep updatedAt(Temporal.DateTime updatedAt);
  }
  

  public interface BuildStep {
    Question build();
    BuildStep id(String id);
    BuildStep value(String value);
    BuildStep answer(String answer);
    BuildStep i18n(List<QuestionI18n> i18n);
    BuildStep createdBy(String createdBy);
    BuildStep updatedBy(String updatedBy);
    BuildStep owner(String owner);
  }
  

  public static class Builder implements CreatedAtStep, UpdatedAtStep, BuildStep {
    private String id;
    private Temporal.DateTime createdAt;
    private Temporal.DateTime updatedAt;
    private String value;
    private String answer;
    private List<QuestionI18n> i18n;
    private String createdBy;
    private String updatedBy;
    private String owner;
    @Override
     public Question build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Question(
          id,
          value,
          answer,
          i18n,
          createdAt,
          updatedAt,
          createdBy,
          updatedBy,
          owner);
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
     public BuildStep value(String value) {
        this.value = value;
        return this;
    }
    
    @Override
     public BuildStep answer(String answer) {
        this.answer = answer;
        return this;
    }
    
    @Override
     public BuildStep i18n(List<QuestionI18n> i18n) {
        this.i18n = i18n;
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
    private CopyOfBuilder(String id, String value, String answer, List<QuestionI18n> i18n, Temporal.DateTime createdAt, Temporal.DateTime updatedAt, String createdBy, String updatedBy, String owner) {
      super.id(id);
      super.createdAt(createdAt)
        .updatedAt(updatedAt)
        .value(value)
        .answer(answer)
        .i18n(i18n)
        .createdBy(createdBy)
        .updatedBy(updatedBy)
        .owner(owner);
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
     public CopyOfBuilder value(String value) {
      return (CopyOfBuilder) super.value(value);
    }
    
    @Override
     public CopyOfBuilder answer(String answer) {
      return (CopyOfBuilder) super.answer(answer);
    }
    
    @Override
     public CopyOfBuilder i18n(List<QuestionI18n> i18n) {
      return (CopyOfBuilder) super.i18n(i18n);
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
