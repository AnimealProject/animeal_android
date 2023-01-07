package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.annotations.BelongsTo;
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

/** This is an auto generated class representing the Medication type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Medications", authRules = {
  @AuthRule(allow = AuthStrategy.GROUPS, groupClaim = "cognito:groups", groups = { "Administrator" }, provider = "userPools", operations = { ModelOperation.CREATE, ModelOperation.READ, ModelOperation.UPDATE, ModelOperation.DELETE }),
  @AuthRule(allow = AuthStrategy.GROUPS, groupClaim = "cognito:groups", groups = { "Moderator" }, provider = "userPools", operations = { ModelOperation.CREATE, ModelOperation.READ, ModelOperation.UPDATE, ModelOperation.DELETE }),
  @AuthRule(allow = AuthStrategy.GROUPS, groupClaim = "cognito:groups", groups = { "Volunteer" }, provider = "userPools", operations = { ModelOperation.READ }),
  @AuthRule(allow = AuthStrategy.OWNER, ownerField = "owner", identityClaim = "cognito:username", provider = "userPools", operations = { ModelOperation.CREATE, ModelOperation.READ, ModelOperation.UPDATE, ModelOperation.DELETE }),
  @AuthRule(allow = AuthStrategy.PUBLIC, provider = "apiKey", operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ }),
  @AuthRule(allow = AuthStrategy.PRIVATE, operations = { ModelOperation.READ })
})
@Index(name = "byPetId", fields = {"petId","date"})
public final class Medication implements Model {
  public static final QueryField ID = field("Medication", "id");
  public static final QueryField NAME = field("Medication", "name");
  public static final QueryField PET = field("Medication", "petId");
  public static final QueryField DATE = field("Medication", "date");
  public static final QueryField I18N = field("Medication", "i18n");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String name;
  private final @ModelField(targetType="Pet", isRequired = true) @BelongsTo(targetName = "petId", type = Pet.class) Pet pet;
  private final @ModelField(targetType="AWSDateTime", isRequired = true) Temporal.DateTime date;
  private final @ModelField(targetType="MedicationI18n") List<MedicationI18n> i18n;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  public String getId() {
      return id;
  }
  
  public String getName() {
      return name;
  }
  
  public Pet getPet() {
      return pet;
  }
  
  public Temporal.DateTime getDate() {
      return date;
  }
  
  public List<MedicationI18n> getI18n() {
      return i18n;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private Medication(String id, String name, Pet pet, Temporal.DateTime date, List<MedicationI18n> i18n) {
    this.id = id;
    this.name = name;
    this.pet = pet;
    this.date = date;
    this.i18n = i18n;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Medication medication = (Medication) obj;
      return ObjectsCompat.equals(getId(), medication.getId()) &&
              ObjectsCompat.equals(getName(), medication.getName()) &&
              ObjectsCompat.equals(getPet(), medication.getPet()) &&
              ObjectsCompat.equals(getDate(), medication.getDate()) &&
              ObjectsCompat.equals(getI18n(), medication.getI18n()) &&
              ObjectsCompat.equals(getCreatedAt(), medication.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), medication.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getName())
      .append(getPet())
      .append(getDate())
      .append(getI18n())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Medication {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("name=" + String.valueOf(getName()) + ", ")
      .append("pet=" + String.valueOf(getPet()) + ", ")
      .append("date=" + String.valueOf(getDate()) + ", ")
      .append("i18n=" + String.valueOf(getI18n()) + ", ")
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
  public static Medication justId(String id) {
    return new Medication(
      id,
      null,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      name,
      pet,
      date,
      i18n);
  }
  public interface NameStep {
    PetStep name(String name);
  }
  

  public interface PetStep {
    DateStep pet(Pet pet);
  }
  

  public interface DateStep {
    BuildStep date(Temporal.DateTime date);
  }
  

  public interface BuildStep {
    Medication build();
    BuildStep id(String id);
    BuildStep i18n(List<MedicationI18n> i18n);
  }
  

  public static class Builder implements NameStep, PetStep, DateStep, BuildStep {
    private String id;
    private String name;
    private Pet pet;
    private Temporal.DateTime date;
    private List<MedicationI18n> i18n;
    @Override
     public Medication build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Medication(
          id,
          name,
          pet,
          date,
          i18n);
    }
    
    @Override
     public PetStep name(String name) {
        Objects.requireNonNull(name);
        this.name = name;
        return this;
    }
    
    @Override
     public DateStep pet(Pet pet) {
        Objects.requireNonNull(pet);
        this.pet = pet;
        return this;
    }
    
    @Override
     public BuildStep date(Temporal.DateTime date) {
        Objects.requireNonNull(date);
        this.date = date;
        return this;
    }
    
    @Override
     public BuildStep i18n(List<MedicationI18n> i18n) {
        this.i18n = i18n;
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
    private CopyOfBuilder(String id, String name, Pet pet, Temporal.DateTime date, List<MedicationI18n> i18n) {
      super.id(id);
      super.name(name)
        .pet(pet)
        .date(date)
        .i18n(i18n);
    }
    
    @Override
     public CopyOfBuilder name(String name) {
      return (CopyOfBuilder) super.name(name);
    }
    
    @Override
     public CopyOfBuilder pet(Pet pet) {
      return (CopyOfBuilder) super.pet(pet);
    }
    
    @Override
     public CopyOfBuilder date(Temporal.DateTime date) {
      return (CopyOfBuilder) super.date(date);
    }
    
    @Override
     public CopyOfBuilder i18n(List<MedicationI18n> i18n) {
      return (CopyOfBuilder) super.i18n(i18n);
    }
  }
  
}
