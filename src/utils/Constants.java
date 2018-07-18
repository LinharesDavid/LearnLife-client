package utils;

public final class Constants {

    public static final String BASE_URL = "http://192.168.1.29:8080/";

    public static final String MAIN_WINDOW_TITLE = "Learn Life";

    public static final String EXTENDED_URL_LOGIN = "auth/login";
    public static final String EXTENDED_URL_USERS = "users/";
    public static final String EXTENDED_URL_CATEGORY = "categories/";
    public static final String EXTENDED_URL_TAG = "tags/";
    public static final String EXTENDED_URL_CHALLENGE = "challenges/";
    public static final String EXTENDED_URL_BADGE = "badges/";


    public static final String BODY_PARAMETER_EMAIL = "email";
    public static final String BODY_PARAMETER_PASSWORD = "password";
    public static final String BODY_PARAMETER_FIRSTNAME = "firstname";
    public static final String BODY_PARAMETER_LASTNAME = "lastname";
    public static final String BODY_PARAMETER_ROLE = "role";
    public static final String BODY_PARAMETER_NAME = "name";
    public static final String BODY_PARAMETER_CATEGORY = "category";
    public static final String BODY_PARAMETER_TAG_ASSOCIATED = "tagAssociated";
    public static final String BODY_PARAMETER_THUMBNAIL_URL = "thumbnailUrl";
    public static final String BODY_PARAMETER_DESCRIPTION = "description";
    public static final String BODY_PARAMETER_POINTS = "achievementPoints";


    public static final String REQUEST_PROPERTY_CONTENT_TYPE ="Content-Type";
    public static final String REQUEST_PROPERTY_CONTENT_TYPE_JSON ="application/json";

    public static final String RESPONSE_FIELD_TOKEN = "token";

    public static final String MODEL_NAME_BADGE = "badge";
    public static final String MODEL_NAME_CATEGORY = "category";
    public static final String MODEL_NAME_CHALLENGE = "challenge";
    public static final String MODEL_NAME_TAG = "tag";
    public static final String MODEL_NAME_USER = "user";

    // *************************** JSON keys ***************************

    public static final String JSON_ENTRY_KEY_ID = "_id";
    public static final String JSON_ENTRY_KEY_RAW_JSON = "rawjson";
    //User
    public static final String JSON_ENTRY_KEY_USER_EMAIL = "email";
    public static final String JSON_ENTRY_KEY_USER_PASSWORD = "password";
    public static final String JSON_ENTRY_KEY_USER_FIRSTNAME = "firstname";
    public static final String JSON_ENTRY_KEY_USER_LASTNAME = "lastname";
    public static final String JSON_ENTRY_KEY_USER_AGE = "age";
    public static final String JSON_ENTRY_KEY_USER_BADGES = "badges";
    public static final String JSON_ENTRY_KEY_USER_POINTS = "points";
    public static final String JSON_ENTRY_KEY_USER_TAGS = "tags";
    public static final String JSON_ENTRY_KEY_USER_THUMBNAIL = "thumbnail";
    public static final String JSON_ENTRY_KEY_USER_ROLE = "role";
    //Badge
    public static final String JSON_ENTRY_KEY_BADGE_NAME = "name";
    public static final String JSON_ENTRY_KEY_BADGE_DESCRIPTION = "description";
    public static final String JSON_ENTRY_KEY_BADGE_THUMBNAIL = "thumbnail";
    public static final String JSON_ENTRY_KEY_BADGE_ACHIEVEMENT_POINTS = "achievementPoints";
    //Category
    public static final String JSON_ENTRY_KEY_CATEGORY_NAME = "name";
    //Challenge
    public static final String JSON_ENTRY_KEY_CHALLENGE_NAME = "name";
    public static final String JSON_ENTRY_KEY_CHALLENGE_DETAILS = "details";
    public static final String JSON_ENTRY_KEY_CHALLENGE_IMAGE_URL = "imageUrl";
    public static final String JSON_ENTRY_KEY_CHALLENGE_POINTS_GIVEN = "pointsGiven";
    public static final String JSON_ENTRY_KEY_CHALLENGE_START_DATE = "startDate";
    public static final String JSON_ENTRY_KEY_CHALLENGE_END_DATE = "endDate";
    public static final String JSON_ENTRY_KEY_CHALLENGE_DURATION = "duration";
    public static final String JSON_ENTRY_KEY_CHALLENGE_TAGS = "tags";
    public static final String JSON_ENTRY_KEY_CHALLENGE_BADGE = "badge";
    public static final String JSON_ENTRY_KEY_CHALLENGE_USER = "user";
    public static final String JSON_ENTRY_KEY_CHALLENGE_VERIFIED = "verified";
    //Tag
    public static final String JSON_ENTRY_KEY_TAG_NAME = "name";
    public static final String JSON_ENTRY_KEY_TAG_TAG_ASSOSCIATED = "tagAssociated";
    public static final String JSON_ENTRY_KEY_TAG_CATEGORY = "category";

    // **************************** WORDING ****************************

    public static final String ERR_UNKNOWN = "Unknown error";
    public static final String ERR_READING_RESPONSE = "Error while reading response";
    public static final String DELETE_SELECTED_ITEM = "Delete Selected Item";
    public static final String ADD_ITEM = "Add Item";

    public static final String NO_VALUE_FOR_THIS_KEY_STRING = "N/A";
    public static final int NO_VALUE_FOR_THIS_KEY_INT = -1;

    public static final String ADD_ITEM_WINDOW_TITLE = "Add ";
    public static final String EDIT_ITEM_WINDOW_TITLE = "Edit ";

    public static final String LEFT_PANNEL_TABLE_NAME_BADGE = "Badges";
    public static final String LEFT_PANNEL_TABLE_NAME_CATEGORY = "Categories";
    public static final String LEFT_PANNEL_TABLE_NAME_CHALLENGE = "Challenges";
    public static final String LEFT_PANNEL_TABLE_NAME_TAG = "Tags";
    public static final String LEFT_PANNEL_TABLE_NAME_USER = "Users";

}
