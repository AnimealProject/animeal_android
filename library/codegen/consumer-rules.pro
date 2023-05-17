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