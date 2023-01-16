package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.temporal.Temporal;
import com.amplifyframework.core.model.annotations.HasMany;
import com.amplifyframework.core.model.annotations.HasOne;

import java.util.List;
import java.util.UUID;
import java.util.Objects;

import androidx.core.util.ObjectsCompat;

import com.amplifyframework.core.model.AuthStrategy;
import com.amplifyframework.core.model.Model;
import com.amplifyframework.core.model.ModelOperation;
import com.amplifyframework.core.model.annotations.AuthRule;
import com.amplifyframework.core.model.annotations.ModelConfig;
import com.amplifyframework.core.model.annotations.ModelField;
import com.amplifyframework.core.model.query.predicate.QueryField;

import static com.amplifyframework.core.model.query.predicate.QueryField.field;

/** This is an auto generated class representing the Pet type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Pets", authRules = {
  @AuthRule(allow = AuthStrategy.GROUPS, groupClaim = "cognito:groups", groups = { "Administrator" }, provider = "userPools", operations = { ModelOperation.CREATE, ModelOperation.READ, ModelOperation.UPDATE, ModelOperation.DELETE }),
  @AuthRule(allow = AuthStrategy.GROUPS, groupClaim = "cognito:groups", groups = { "Moderator" }, provider = "userPools", operations = { ModelOperation.CREATE, ModelOperation.READ, ModelOperation.UPDATE, ModelOperation.DELETE }),
  @AuthRule(allow = AuthStrategy.GROUPS, groupClaim = "cognito:groups", groups = { "Volunteer" }, provider = "userPools", operations = { ModelOperation.READ }),
  @AuthRule(allow = AuthStrategy.OWNER, ownerField = "owner", identityClaim = "cognito:username", provider = "userPools", operations = { ModelOperation.CREATE, ModelOperation.READ, ModelOperation.UPDATE, ModelOperation.DELETE }),
  @AuthRule(allow = AuthStrategy.PUBLIC, provider = "apiKey", operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ }),
  @AuthRule(allow = AuthStrategy.PRIVATE, operations = { ModelOperation.READ })
})
public final class Pet implements Model {
  public static final QueryField ID = field("Pet", "id");
  public static final QueryField NAME = field("Pet", "name");
  public static final QueryField IMAGES = field("Pet", "images");
  public static final QueryField BREED = field("Pet", "breed");
  public static final QueryField COLOR = field("Pet", "color");
  public static final QueryField CHIP_NUMBER = field("Pet", "chipNumber");
  public static final QueryField VACCINATED_AT = field("Pet", "vaccinatedAt");
  public static final QueryField YEAR_OF_BIRTH = field("Pet", "yearOfBirth");
  public static final QueryField CARETAKER = field("Pet", "caretaker");
  public static final QueryField I18N = field("Pet", "i18n");
  public static final QueryField CREATED_AT = field("Pet", "createdAt");
  public static final QueryField UPDATED_AT = field("Pet", "updatedAt");
  public static final QueryField CREATED_BY = field("Pet", "createdBy");
  public static final QueryField UPDATED_BY = field("Pet", "updatedBy");
  public static final QueryField OWNER = field("Pet", "owner");
  public static final QueryField COVER = field("Pet", "cover");
  public static final QueryField PET_CATEGORY_ID = field("Pet", "petCategoryId");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String name;
  private final @ModelField(targetType="String", isRequired = true) List<String> images;
  private final @ModelField(targetType="String", isRequired = true) String breed;
  private final @ModelField(targetType="String", isRequired = true) String color;
  private final @ModelField(targetType="String", isRequired = true) String chipNumber;
  private final @ModelField(targetType="AWSDateTime", isRequired = true) Temporal.DateTime vaccinatedAt;
  private final @ModelField(targetType="AWSDateTime", isRequired = true) Temporal.DateTime yearOfBirth;
  private final @ModelField(targetType="Caretaker") Caretaker caretaker;
  private final @ModelField(targetType="PetI18n") List<PetI18n> i18n;
  private final @ModelField(targetType="AWSDateTime", isRequired = true) Temporal.DateTime createdAt;
  private final @ModelField(targetType="AWSDateTime", isRequired = true) Temporal.DateTime updatedAt;
  private final @ModelField(targetType="String") String createdBy;
  private final @ModelField(targetType="String") String updatedBy;
  private final @ModelField(targetType="String") String owner;
  private final @ModelField(targetType="RelationPetFeedingPoint") @HasMany(associatedWith = "pet", type = RelationPetFeedingPoint.class) List<RelationPetFeedingPoint> feedingPoints = null;
  private final @ModelField(targetType="Category", isRequired = true) @HasOne(associatedWith = "id", type = Category.class) Category category = null;
  private final @ModelField(targetType="Medication") @HasMany(associatedWith = "pet", type = Medication.class) List<Medication> medications = null;
  private final @ModelField(targetType="RelationUserPet") @HasMany(associatedWith = "pet", type = RelationUserPet.class) List<RelationUserPet> users = null;
  private final @ModelField(targetType="String") String cover;
  private final @ModelField(targetType="ID", isRequired = true) String petCategoryId;
  public String getId() {
      return id;
  }
  
  public String getName() {
      return name;
  }
  
  public List<String> getImages() {
      return images;
  }
  
  public String getBreed() {
      return breed;
  }
  
  public String getColor() {
      return color;
  }
  
  public String getChipNumber() {
      return chipNumber;
  }
  
  public Temporal.DateTime getVaccinatedAt() {
      return vaccinatedAt;
  }
  
  public Temporal.DateTime getYearOfBirth() {
      return yearOfBirth;
  }
  
  public Caretaker getCaretaker() {
      return caretaker;
  }
  
  public List<PetI18n> getI18n() {
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
  
  public List<RelationPetFeedingPoint> getFeedingPoints() {
      return feedingPoints;
  }
  
  public Category getCategory() {
      return category;
  }
  
  public List<Medication> getMedications() {
      return medications;
  }
  
  public List<RelationUserPet> getUsers() {
      return users;
  }
  
  public String getCover() {
      return cover;
  }
  
  public String getPetCategoryId() {
      return petCategoryId;
  }
  
  private Pet(String id, String name, List<String> images, String breed, String color, String chipNumber, Temporal.DateTime vaccinatedAt, Temporal.DateTime yearOfBirth, Caretaker caretaker, List<PetI18n> i18n, Temporal.DateTime createdAt, Temporal.DateTime updatedAt, String createdBy, String updatedBy, String owner, String cover, String petCategoryId) {
    this.id = id;
    this.name = name;
    this.images = images;
    this.breed = breed;
    this.color = color;
    this.chipNumber = chipNumber;
    this.vaccinatedAt = vaccinatedAt;
    this.yearOfBirth = yearOfBirth;
    this.caretaker = caretaker;
    this.i18n = i18n;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.createdBy = createdBy;
    this.updatedBy = updatedBy;
    this.owner = owner;
    this.cover = cover;
    this.petCategoryId = petCategoryId;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Pet pet = (Pet) obj;
      return ObjectsCompat.equals(getId(), pet.getId()) &&
              ObjectsCompat.equals(getName(), pet.getName()) &&
              ObjectsCompat.equals(getImages(), pet.getImages()) &&
              ObjectsCompat.equals(getBreed(), pet.getBreed()) &&
              ObjectsCompat.equals(getColor(), pet.getColor()) &&
              ObjectsCompat.equals(getChipNumber(), pet.getChipNumber()) &&
              ObjectsCompat.equals(getVaccinatedAt(), pet.getVaccinatedAt()) &&
              ObjectsCompat.equals(getYearOfBirth(), pet.getYearOfBirth()) &&
              ObjectsCompat.equals(getCaretaker(), pet.getCaretaker()) &&
              ObjectsCompat.equals(getI18n(), pet.getI18n()) &&
              ObjectsCompat.equals(getCreatedAt(), pet.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), pet.getUpdatedAt()) &&
              ObjectsCompat.equals(getCreatedBy(), pet.getCreatedBy()) &&
              ObjectsCompat.equals(getUpdatedBy(), pet.getUpdatedBy()) &&
              ObjectsCompat.equals(getOwner(), pet.getOwner()) &&
              ObjectsCompat.equals(getCover(), pet.getCover()) &&
              ObjectsCompat.equals(getPetCategoryId(), pet.getPetCategoryId());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getName())
      .append(getImages())
      .append(getBreed())
      .append(getColor())
      .append(getChipNumber())
      .append(getVaccinatedAt())
      .append(getYearOfBirth())
      .append(getCaretaker())
      .append(getI18n())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .append(getCreatedBy())
      .append(getUpdatedBy())
      .append(getOwner())
      .append(getCover())
      .append(getPetCategoryId())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Pet {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("name=" + String.valueOf(getName()) + ", ")
      .append("images=" + String.valueOf(getImages()) + ", ")
      .append("breed=" + String.valueOf(getBreed()) + ", ")
      .append("color=" + String.valueOf(getColor()) + ", ")
      .append("chipNumber=" + String.valueOf(getChipNumber()) + ", ")
      .append("vaccinatedAt=" + String.valueOf(getVaccinatedAt()) + ", ")
      .append("yearOfBirth=" + String.valueOf(getYearOfBirth()) + ", ")
      .append("caretaker=" + String.valueOf(getCaretaker()) + ", ")
      .append("i18n=" + String.valueOf(getI18n()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()) + ", ")
      .append("createdBy=" + String.valueOf(getCreatedBy()) + ", ")
      .append("updatedBy=" + String.valueOf(getUpdatedBy()) + ", ")
      .append("owner=" + String.valueOf(getOwner()) + ", ")
      .append("cover=" + String.valueOf(getCover()) + ", ")
      .append("petCategoryId=" + String.valueOf(getPetCategoryId()))
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
  public static Pet justId(String id) {
    return new Pet(
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
      images,
      breed,
      color,
      chipNumber,
      vaccinatedAt,
      yearOfBirth,
      caretaker,
      i18n,
      createdAt,
      updatedAt,
      createdBy,
      updatedBy,
      owner,
      cover,
      petCategoryId);
  }
  public interface NameStep {
    ImagesStep name(String name);
  }
  

  public interface ImagesStep {
    BreedStep images(List<String> images);
  }
  

  public interface BreedStep {
    ColorStep breed(String breed);
  }
  

  public interface ColorStep {
    ChipNumberStep color(String color);
  }
  

  public interface ChipNumberStep {
    VaccinatedAtStep chipNumber(String chipNumber);
  }
  

  public interface VaccinatedAtStep {
    YearOfBirthStep vaccinatedAt(Temporal.DateTime vaccinatedAt);
  }
  

  public interface YearOfBirthStep {
    CreatedAtStep yearOfBirth(Temporal.DateTime yearOfBirth);
  }
  

  public interface CreatedAtStep {
    UpdatedAtStep createdAt(Temporal.DateTime createdAt);
  }
  

  public interface UpdatedAtStep {
    PetCategoryIdStep updatedAt(Temporal.DateTime updatedAt);
  }
  

  public interface PetCategoryIdStep {
    BuildStep petCategoryId(String petCategoryId);
  }
  

  public interface BuildStep {
    Pet build();
    BuildStep id(String id);
    BuildStep caretaker(Caretaker caretaker);
    BuildStep i18n(List<PetI18n> i18n);
    BuildStep createdBy(String createdBy);
    BuildStep updatedBy(String updatedBy);
    BuildStep owner(String owner);
    BuildStep cover(String cover);
  }
  

  public static class Builder implements NameStep, ImagesStep, BreedStep, ColorStep, ChipNumberStep, VaccinatedAtStep, YearOfBirthStep, CreatedAtStep, UpdatedAtStep, PetCategoryIdStep, BuildStep {
    private String id;
    private String name;
    private List<String> images;
    private String breed;
    private String color;
    private String chipNumber;
    private Temporal.DateTime vaccinatedAt;
    private Temporal.DateTime yearOfBirth;
    private Temporal.DateTime createdAt;
    private Temporal.DateTime updatedAt;
    private String petCategoryId;
    private Caretaker caretaker;
    private List<PetI18n> i18n;
    private String createdBy;
    private String updatedBy;
    private String owner;
    private String cover;
    @Override
     public Pet build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Pet(
          id,
          name,
          images,
          breed,
          color,
          chipNumber,
          vaccinatedAt,
          yearOfBirth,
          caretaker,
          i18n,
          createdAt,
          updatedAt,
          createdBy,
          updatedBy,
          owner,
          cover,
          petCategoryId);
    }
    
    @Override
     public ImagesStep name(String name) {
        Objects.requireNonNull(name);
        this.name = name;
        return this;
    }
    
    @Override
     public BreedStep images(List<String> images) {
        Objects.requireNonNull(images);
        this.images = images;
        return this;
    }
    
    @Override
     public ColorStep breed(String breed) {
        Objects.requireNonNull(breed);
        this.breed = breed;
        return this;
    }
    
    @Override
     public ChipNumberStep color(String color) {
        Objects.requireNonNull(color);
        this.color = color;
        return this;
    }
    
    @Override
     public VaccinatedAtStep chipNumber(String chipNumber) {
        Objects.requireNonNull(chipNumber);
        this.chipNumber = chipNumber;
        return this;
    }
    
    @Override
     public YearOfBirthStep vaccinatedAt(Temporal.DateTime vaccinatedAt) {
        Objects.requireNonNull(vaccinatedAt);
        this.vaccinatedAt = vaccinatedAt;
        return this;
    }
    
    @Override
     public CreatedAtStep yearOfBirth(Temporal.DateTime yearOfBirth) {
        Objects.requireNonNull(yearOfBirth);
        this.yearOfBirth = yearOfBirth;
        return this;
    }
    
    @Override
     public UpdatedAtStep createdAt(Temporal.DateTime createdAt) {
        Objects.requireNonNull(createdAt);
        this.createdAt = createdAt;
        return this;
    }
    
    @Override
     public PetCategoryIdStep updatedAt(Temporal.DateTime updatedAt) {
        Objects.requireNonNull(updatedAt);
        this.updatedAt = updatedAt;
        return this;
    }
    
    @Override
     public BuildStep petCategoryId(String petCategoryId) {
        Objects.requireNonNull(petCategoryId);
        this.petCategoryId = petCategoryId;
        return this;
    }
    
    @Override
     public BuildStep caretaker(Caretaker caretaker) {
        this.caretaker = caretaker;
        return this;
    }
    
    @Override
     public BuildStep i18n(List<PetI18n> i18n) {
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
    
    @Override
     public BuildStep cover(String cover) {
        this.cover = cover;
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
    private CopyOfBuilder(String id, String name, List<String> images, String breed, String color, String chipNumber, Temporal.DateTime vaccinatedAt, Temporal.DateTime yearOfBirth, Caretaker caretaker, List<PetI18n> i18n, Temporal.DateTime createdAt, Temporal.DateTime updatedAt, String createdBy, String updatedBy, String owner, String cover, String petCategoryId) {
      super.id(id);
      super.name(name)
        .images(images)
        .breed(breed)
        .color(color)
        .chipNumber(chipNumber)
        .vaccinatedAt(vaccinatedAt)
        .yearOfBirth(yearOfBirth)
        .createdAt(createdAt)
        .updatedAt(updatedAt)
        .petCategoryId(petCategoryId)
        .caretaker(caretaker)
        .i18n(i18n)
        .createdBy(createdBy)
        .updatedBy(updatedBy)
        .owner(owner)
        .cover(cover);
    }
    
    @Override
     public CopyOfBuilder name(String name) {
      return (CopyOfBuilder) super.name(name);
    }
    
    @Override
     public CopyOfBuilder images(List<String> images) {
      return (CopyOfBuilder) super.images(images);
    }
    
    @Override
     public CopyOfBuilder breed(String breed) {
      return (CopyOfBuilder) super.breed(breed);
    }
    
    @Override
     public CopyOfBuilder color(String color) {
      return (CopyOfBuilder) super.color(color);
    }
    
    @Override
     public CopyOfBuilder chipNumber(String chipNumber) {
      return (CopyOfBuilder) super.chipNumber(chipNumber);
    }
    
    @Override
     public CopyOfBuilder vaccinatedAt(Temporal.DateTime vaccinatedAt) {
      return (CopyOfBuilder) super.vaccinatedAt(vaccinatedAt);
    }
    
    @Override
     public CopyOfBuilder yearOfBirth(Temporal.DateTime yearOfBirth) {
      return (CopyOfBuilder) super.yearOfBirth(yearOfBirth);
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
     public CopyOfBuilder petCategoryId(String petCategoryId) {
      return (CopyOfBuilder) super.petCategoryId(petCategoryId);
    }
    
    @Override
     public CopyOfBuilder caretaker(Caretaker caretaker) {
      return (CopyOfBuilder) super.caretaker(caretaker);
    }
    
    @Override
     public CopyOfBuilder i18n(List<PetI18n> i18n) {
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
    
    @Override
     public CopyOfBuilder cover(String cover) {
      return (CopyOfBuilder) super.cover(cover);
    }
  }
  
}
