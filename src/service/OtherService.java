package service;

import org.json.JSONObject;
import popup.PopupView;
import utils.request.builder.OnRequestFailListener;
import utils.request.builder.OnRequestSuccessListener;
import utils.request.builder.RequestBuilder;

import static utils.Constants.*;

public class OtherService {

    public static void editModel(String json, String type, OnRequestSuccessListener successListener, OnRequestFailListener failListener) {

        JSONObject jsonObject = new JSONObject(json);
        String user_id = jsonObject.getString("_id");
        String extendedUrl = "-1";
        switch (type) {
            case "badge":
                extendedUrl = EXTENDED_URL_USERS;
                break;
            case "category":
                extendedUrl = EXTENDED_URL_CATEGORY;
                break;
            case "challenge":
                extendedUrl = EXTENDED_URL_USERS;
                break;
            case "tag":
                extendedUrl = EXTENDED_URL_TAG;
                break;
            case "user":
                extendedUrl = EXTENDED_URL_USERS;
                break;
        }

        RequestBuilder.builder()
                .setUrl(BASE_URL + extendedUrl + user_id)
                .setRequestMethod("PUT")
                .setRequestBodyParameterJson(jsonObject)
                .setOnResponseSuccessListener(successListener)
                .setOnResponseFailListener(failListener)
                .build();
    }
}
