-keepclassmembers class type.CreateFavouriteInput {
    private java.lang.String userId;
    private java.lang.String feedingPointId;
}
-keepclassmembers class type.DeleteFavouriteInput {
    private java.lang.String id;
}
-keepclassmembers class SearchFeedingHistoriesQuery$Data {
    final SearchFeedingHistoriesQuery$SearchFeedingHistories searchFeedingHistories;
}
-keepclassmembers class SearchFeedingHistoriesQuery$SearchFeedingHistories {
    final java.util.List items;
 }
-keepclassmembers class SearchFeedingHistoriesQuery$Item { *; }
-keepclassmembers class SearchFeedingsQuery$Data {
    final SearchFeedingsQuery$SearchFeedings searchFeedings;
}
-keepclassmembers class SearchFeedingsQuery$SearchFeedings {
    final java.util.List items;
 }
-keepclassmembers class SearchFeedingsQuery$Item { *; }
-keepclassmembers class SearchQuestionsQuery$Data {
    final SearchQuestionsQuery$SearchQuestions searchQuestions;
}
-keepclassmembers class SearchQuestionsQuery$SearchQuestions {
    final java.util.List items;
 }
-keepclassmembers class SearchQuestionsQuery$Item { *; }
-keepclassmembers class OnCreateFeedingExtSubscription$Data {
    final OnCreateFeedingExtSubscription$OnCreateFeedingExt onCreateFeedingExt;
}
-keepclassmembers class OnCreateFeedingExtSubscription$OnCreateFeedingExt { *; }
-keepclassmembers class OnUpdateFeedingExtSubscription$Data {
    final OnUpdateFeedingExtSubscription$OnUpdateFeedingExt onUpdateFeedingExt;
}
-keepclassmembers class OnUpdateFeedingExtSubscription$OnUpdateFeedingExt { *; }
-keepclassmembers class OnDeleteFeedingExtSubscription$Data {
    final OnDeleteFeedingExtSubscription$OnDeleteFeedingExt onDeleteFeedingExt;
}
-keepclassmembers class OnDeleteFeedingExtSubscription$OnDeleteFeedingExt { *; }
-keepclassmembers class OnCreateFeedingHistoryExtSubscription$Data {
    final OnCreateFeedingHistoryExtSubscription$OnCreateFeedingHistoryExt onCreateFeedingHistoryExt;
}
-keepclassmembers class OnCreateFeedingHistoryExtSubscription$OnCreateFeedingHistoryExt { *; }