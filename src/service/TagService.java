package service;

import model.Tag;
import org.json.JSONArray;
import popup.PopupView;
import utils.Log;
import utils.request.builder.OnRequestFailListener;
import utils.request.builder.OnRequestSuccessListener;
import utils.request.builder.RequestBuilder;

import static utils.Constants.*;

public class TagService {

    public static void getAllTags(OnRequestSuccessListener successListener, OnRequestFailListener failListener) {
        RequestBuilder.builder()
                .setUrl(BASE_URL + EXTENDED_URL_TAG)
                .setRequestMethod("GET")
                .addRequestProperty(REQUEST_PROPERTY_CONTENT_TYPE, REQUEST_PROPERTY_CONTENT_TYPE_JSON)
                .setOnResponseSuccessListener(successListener)
                .setOnResponseFailListener(failListener)
                .build();
    }

    public static void addTag(String name, JSONArray tags, String category, OnRequestSuccessListener successListener, OnRequestFailListener failListener){
        RequestBuilder.builder()
                .setUrl(BASE_URL + EXTENDED_URL_TAG)
                .setRequestMethod("POST")
                .addRequestBodyParameter(BODY_PARAMETER_NAME, name)
                .addRequestBodyParameter(BODY_PARAMETER_TAG_ASSOCIATED, tags)
                .addRequestBodyParameter(BODY_PARAMETER_CATEGORY, category)
                .setOnResponseSuccessListener(successListener)
                .setOnResponseFailListener(failListener)
                .build();
    }
}
