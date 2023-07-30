package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.temporal.Temporal;

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

/** This is an auto generated class representing the Feeding type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Feedings", authRules = {
        @AuthRule(allow = AuthStrategy.GROUPS, groupClaim = "cognito:groups", groups = { "Administrator" }, provider = "userPools", operations = { ModelOperation.CREATE, ModelOperation.READ, ModelOperation.UPDATE, ModelOperation.DELETE }),
        @AuthRule(allow = AuthStrategy.GROUPS, groupClaim = "cognito:groups", groups = { "Moderator" }, provider = "userPools", operations = { ModelOperation.CREATE, ModelOperation.READ, ModelOperation.UPDATE, ModelOperation.DELETE }),
        @AuthRule(allow = AuthStrategy.GROUPS, groupClaim = "cognito:groups", groups = { "Volunteer" }, provider = "userPools", operations = { ModelOperation.READ }),
        @AuthRule(allow = AuthStrategy.OWNER, ownerField = "owner", identityClaim = "cognito:username", provider = "userPools", operations = { ModelOperation.CREATE, ModelOperation.READ, ModelOperation.UPDATE, ModelOperation.DELETE }),
        @AuthRule(allow = AuthStrategy.PUBLIC, provider = "apiKey", operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ }),
        @AuthRule(allow = AuthStrategy.PRIVATE, operations = { ModelOperation.READ })
})
public final class Feeding implements Model {
    public static final QueryField ID = field("Feeding", "id");
    public static final QueryField USER_ID = field("Feeding", "userId");
    public static final QueryField IMAGES = field("Feeding", "images");
    public static final QueryField STATUS = field("Feeding", "status");
    public static final QueryField CREATED_AT = field("Feeding", "createdAt");
    public static final QueryField UPDATED_AT = field("Feeding", "updatedAt");
    public static final QueryField CREATED_BY = field("Feeding", "createdBy");
    public static final QueryField UPDATED_BY = field("Feeding", "updatedBy");
    public static final QueryField OWNER = field("Feeding", "owner");
    public static final QueryField FEEDING_POINT_DETAILS = field("Feeding", "feedingPointDetails");
    public static final QueryField FEEDING_POINT_FEEDINGS_ID = field("Feeding", "feedingPointFeedingsId");
    public static final QueryField EXPIRE_AT = field("Feeding", "expireAt");
    public static final QueryField ASSIGNED_MODERATORS = field("Feeding", "assignedModerators");
    public static final QueryField MODERATED_BY = field("Feeding", "moderatedBy");
    public static final QueryField MODERATED_AT = field("Feeding", "moderatedAt");
    private final @ModelField(targetType="ID", isRequired = true) String id;
    private final @ModelField(targetType="String", isRequired = true) String userId;
    private final @ModelField(targetType="String", isRequired = true) List<String> images;
    private final @ModelField(targetType="FeedingStatus", isRequired = true) FeedingStatus status;
    private final @ModelField(targetType="AWSDateTime", isRequired = true) Temporal.DateTime createdAt;
    private final @ModelField(targetType="AWSDateTime", isRequired = true) Temporal.DateTime updatedAt;
    private final @ModelField(targetType="String") String createdBy;
    private final @ModelField(targetType="String") String updatedBy;
    private final @ModelField(targetType="String") String owner;
    private final @ModelField(targetType="FeedingPointDetails") FeedingPointDetails feedingPointDetails;
    private final @ModelField(targetType="String", isRequired = true) String feedingPointFeedingsId;
    private final @ModelField(targetType="AWSTimestamp", isRequired = true) Temporal.Timestamp expireAt;
    private final @ModelField(targetType="String") List<String> assignedModerators;
    private final @ModelField(targetType="String") String moderatedBy;
    private final @ModelField(targetType="AWSDateTime") Temporal.DateTime moderatedAt;
    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public List<String> getImages() {
        return images;
    }

    public FeedingStatus getStatus() {
        return status;
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

    public FeedingPointDetails getFeedingPointDetails() {
        return feedingPointDetails;
    }

    public String getFeedingPointFeedingsId() {
        return feedingPointFeedingsId;
    }

    public Temporal.Timestamp getExpireAt() {
        return expireAt;
    }

    public List<String> getAssignedModerators() {
        return assignedModerators;
    }

    public String getModeratedBy() {
        return moderatedBy;
    }

    public Temporal.DateTime getModeratedAt() {
        return moderatedAt;
    }

    private Feeding(String id, String userId, List<String> images, FeedingStatus status, Temporal.DateTime createdAt, Temporal.DateTime updatedAt, String createdBy, String updatedBy, String owner, FeedingPointDetails feedingPointDetails, String feedingPointFeedingsId, Temporal.Timestamp expireAt, List<String> assignedModerators, String moderatedBy, Temporal.DateTime moderatedAt) {
        this.id = id;
        this.userId = userId;
        this.images = images;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.owner = owner;
        this.feedingPointDetails = feedingPointDetails;
        this.feedingPointFeedingsId = feedingPointFeedingsId;
        this.expireAt = expireAt;
        this.assignedModerators = assignedModerators;
        this.moderatedBy = moderatedBy;
        this.moderatedAt = moderatedAt;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if(obj == null || getClass() != obj.getClass()) {
            return false;
        } else {
            Feeding feeding = (Feeding) obj;
            return ObjectsCompat.equals(getId(), feeding.getId()) &&
                    ObjectsCompat.equals(getUserId(), feeding.getUserId()) &&
                    ObjectsCompat.equals(getImages(), feeding.getImages()) &&
                    ObjectsCompat.equals(getStatus(), feeding.getStatus()) &&
                    ObjectsCompat.equals(getCreatedAt(), feeding.getCreatedAt()) &&
                    ObjectsCompat.equals(getUpdatedAt(), feeding.getUpdatedAt()) &&
                    ObjectsCompat.equals(getCreatedBy(), feeding.getCreatedBy()) &&
                    ObjectsCompat.equals(getUpdatedBy(), feeding.getUpdatedBy()) &&
                    ObjectsCompat.equals(getOwner(), feeding.getOwner()) &&
                    ObjectsCompat.equals(getFeedingPointDetails(), feeding.getFeedingPointDetails()) &&
                    ObjectsCompat.equals(getFeedingPointFeedingsId(), feeding.getFeedingPointFeedingsId()) &&
                    ObjectsCompat.equals(getExpireAt(), feeding.getExpireAt()) &&
                    ObjectsCompat.equals(getAssignedModerators(), feeding.getAssignedModerators()) &&
                    ObjectsCompat.equals(getModeratedBy(), feeding.getModeratedBy()) &&
                    ObjectsCompat.equals(getModeratedAt(), feeding.getModeratedAt());
        }
    }

    @Override
    public int hashCode() {
        return new StringBuilder()
                .append(getId())
                .append(getUserId())
                .append(getImages())
                .append(getStatus())
                .append(getCreatedAt())
                .append(getUpdatedAt())
                .append(getCreatedBy())
                .append(getUpdatedBy())
                .append(getOwner())
                .append(getFeedingPointDetails())
                .append(getFeedingPointFeedingsId())
                .append(getExpireAt())
                .append(getAssignedModerators())
                .append(getModeratedBy())
                .append(getModeratedAt())
                .toString()
                .hashCode();
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("Feeding {")
                .append("id=" + String.valueOf(getId()) + ", ")
                .append("userId=" + String.valueOf(getUserId()) + ", ")
                .append("images=" + String.valueOf(getImages()) + ", ")
                .append("status=" + String.valueOf(getStatus()) + ", ")
                .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
                .append("updatedAt=" + String.valueOf(getUpdatedAt()) + ", ")
                .append("createdBy=" + String.valueOf(getCreatedBy()) + ", ")
                .append("updatedBy=" + String.valueOf(getUpdatedBy()) + ", ")
                .append("owner=" + String.valueOf(getOwner()) + ", ")
                .append("feedingPointDetails=" + String.valueOf(getFeedingPointDetails()) + ", ")
                .append("feedingPointFeedingsId=" + String.valueOf(getFeedingPointFeedingsId()) + ", ")
                .append("expireAt=" + String.valueOf(getExpireAt()) + ", ")
                .append("assignedModerators=" + String.valueOf(getAssignedModerators()) + ", ")
                .append("moderatedBy=" + String.valueOf(getModeratedBy()) + ", ")
                .append("moderatedAt=" + String.valueOf(getModeratedAt()))
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
    public static Feeding justId(String id) {
        return new Feeding(
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
                null
        );
    }

    public CopyOfBuilder copyOfBuilder() {
        return new CopyOfBuilder(id,
                userId,
                images,
                status,
                createdAt,
                updatedAt,
                createdBy,
                updatedBy,
                owner,
                feedingPointDetails,
                feedingPointFeedingsId,
                expireAt,
                assignedModerators,
                moderatedBy,
                moderatedAt);
    }
    public interface UserIdStep {
        ImagesStep userId(String userId);
    }


    public interface ImagesStep {
        StatusStep images(List<String> images);
    }


    public interface StatusStep {
        CreatedAtStep status(FeedingStatus status);
    }


    public interface CreatedAtStep {
        UpdatedAtStep createdAt(Temporal.DateTime createdAt);
    }


    public interface UpdatedAtStep {
        FeedingPointFeedingsIdStep updatedAt(Temporal.DateTime updatedAt);
    }


    public interface FeedingPointFeedingsIdStep {
        ExpireAtStep feedingPointFeedingsId(String feedingPointFeedingsId);
    }


    public interface ExpireAtStep {
        BuildStep expireAt(Temporal.Timestamp expireAt);
    }


    public interface BuildStep {
        Feeding build();
        BuildStep id(String id);
        BuildStep createdBy(String createdBy);
        BuildStep updatedBy(String updatedBy);
        BuildStep owner(String owner);
        BuildStep feedingPointDetails(FeedingPointDetails feedingPointDetails);
        BuildStep assignedModerators(List<String> assignedModerators);
        BuildStep moderatedBy(String moderatedBy);
        BuildStep moderatedAt(Temporal.DateTime moderatedAt);
    }


    public static class Builder implements UserIdStep, ImagesStep, StatusStep, CreatedAtStep, UpdatedAtStep, FeedingPointFeedingsIdStep, ExpireAtStep, BuildStep {
        private String id;
        private String userId;
        private List<String> images;
        private FeedingStatus status;
        private Temporal.DateTime createdAt;
        private Temporal.DateTime updatedAt;
        private String feedingPointFeedingsId;
        private Temporal.Timestamp expireAt;
        private String createdBy;
        private String updatedBy;
        private String owner;
        private FeedingPointDetails feedingPointDetails;
        private List<String> assignedModerators;
        private String moderatedBy;
        private Temporal.DateTime moderatedAt;
        @Override
        public Feeding build() {
            String id = this.id != null ? this.id : UUID.randomUUID().toString();

            return new Feeding(
                    id,
                    userId,
                    images,
                    status,
                    createdAt,
                    updatedAt,
                    createdBy,
                    updatedBy,
                    owner,
                    feedingPointDetails,
                    feedingPointFeedingsId,
                    expireAt,
                    assignedModerators,
                    moderatedBy,
                    moderatedAt);
        }

        @Override
        public ImagesStep userId(String userId) {
            Objects.requireNonNull(userId);
            this.userId = userId;
            return this;
        }

        @Override
        public StatusStep images(List<String> images) {
            Objects.requireNonNull(images);
            this.images = images;
            return this;
        }

        @Override
        public CreatedAtStep status(FeedingStatus status) {
            Objects.requireNonNull(status);
            this.status = status;
            return this;
        }

        @Override
        public UpdatedAtStep createdAt(Temporal.DateTime createdAt) {
            Objects.requireNonNull(createdAt);
            this.createdAt = createdAt;
            return this;
        }

        @Override
        public FeedingPointFeedingsIdStep updatedAt(Temporal.DateTime updatedAt) {
            Objects.requireNonNull(updatedAt);
            this.updatedAt = updatedAt;
            return this;
        }

        @Override
        public ExpireAtStep feedingPointFeedingsId(String feedingPointFeedingsId) {
            Objects.requireNonNull(feedingPointFeedingsId);
            this.feedingPointFeedingsId = feedingPointFeedingsId;
            return this;
        }

        @Override
        public BuildStep expireAt(Temporal.Timestamp expireAt) {
            Objects.requireNonNull(expireAt);
            this.expireAt = expireAt;
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
        public BuildStep feedingPointDetails(FeedingPointDetails feedingPointDetails) {
            this.feedingPointDetails = feedingPointDetails;
            return this;
        }

        @Override
        public BuildStep assignedModerators(List<String> assignedModerators) {
            this.assignedModerators = assignedModerators;
            return this;
        }

        @Override
        public BuildStep moderatedBy(String moderatedBy) {
            this.moderatedBy = moderatedBy;
            return this;
        }

        @Override
        public BuildStep moderatedAt(Temporal.DateTime moderatedAt) {
            this.moderatedAt = moderatedAt;
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
        private CopyOfBuilder(String id, String userId, List<String> images, FeedingStatus status, Temporal.DateTime createdAt, Temporal.DateTime updatedAt, String createdBy, String updatedBy, String owner, FeedingPointDetails feedingPointDetails, String feedingPointFeedingsId, Temporal.Timestamp expireAt, List<String> assignedModerators, String moderatedBy, Temporal.DateTime moderatedAt) {
            super.id(id);
            super.userId(userId)
                    .images(images)
                    .status(status)
                    .createdAt(createdAt)
                    .updatedAt(updatedAt)
                    .feedingPointFeedingsId(feedingPointFeedingsId)
                    .expireAt(expireAt)
                    .createdBy(createdBy)
                    .updatedBy(updatedBy)
                    .owner(owner)
                    .feedingPointDetails(feedingPointDetails)
                    .assignedModerators(assignedModerators)
                    .moderatedBy(moderatedBy)
                    .moderatedAt(moderatedAt);
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
        public CopyOfBuilder status(FeedingStatus status) {
            return (CopyOfBuilder) super.status(status);
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
        public CopyOfBuilder feedingPointFeedingsId(String feedingPointFeedingsId) {
            return (CopyOfBuilder) super.feedingPointFeedingsId(feedingPointFeedingsId);
        }

        @Override
        public CopyOfBuilder expireAt(Temporal.Timestamp expireAt) {
            return (CopyOfBuilder) super.expireAt(expireAt);
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
        public CopyOfBuilder feedingPointDetails(FeedingPointDetails feedingPointDetails) {
            return (CopyOfBuilder) super.feedingPointDetails(feedingPointDetails);
        }

        @Override
        public CopyOfBuilder assignedModerators(List<String> assignedModerators) {
            return (CopyOfBuilder) super.assignedModerators(assignedModerators);
        }

        @Override
        public CopyOfBuilder moderatedBy(String moderatedBy) {
            return (CopyOfBuilder) super.moderatedBy(moderatedBy);
        }

        @Override
        public CopyOfBuilder moderatedAt(Temporal.DateTime moderatedAt) {
            return (CopyOfBuilder) super.moderatedAt(moderatedAt);
        }
    }

}
