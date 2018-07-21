package service;

import model.Tag;
import org.json.JSONArray;
import org.json.JSONObject;
import popup.PopupView;
import utils.Log;
import utils.request.builder.OnRequestFailListener;
import utils.request.builder.OnRequestSuccessListener;
import utils.request.builder.RequestBuilder;

import java.util.ArrayList;

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

    public static void deleteTag(String tagId, OnRequestSuccessListener successListener, OnRequestFailListener failListener) {
        RequestBuilder.builder()
                .setUrl(BASE_URL + EXTENDED_URL_TAG + tagId)
                .setRequestMethod("DELETE")
                .addRequestProperty(REQUEST_PROPERTY_CONTENT_TYPE, REQUEST_PROPERTY_CONTENT_TYPE_JSON)
                .setOnResponseSuccessListener(successListener)
                .setOnResponseFailListener(failListener)
                .build();
    }

    public static void addTag(String name, JSONArray tags, String category, OnRequestSuccessListener successListener, OnRequestFailListener failListener){
        RequestBuilder.builder()
                .setUrl(BASE_URL + EXTENDED_URL_TAG)
                .setRequestMethod("POST")
                .addRequestBodyParameter(KEY_TAG_NAME, name)
                .addRequestBodyParameter(KEY_TAG_TAG_ASSOSCIATED, tags)
                .addRequestBodyParameter(KEY_TAG_CATEGORY, category)
                .setOnResponseSuccessListener(successListener)
                .setOnResponseFailListener(failListener)
                .build();
    }

    public static ArrayList<Tag> parseTags(String response) {
        ArrayList<Tag> tags = new ArrayList<>();
        JSONArray jsonArrayTag = new JSONArray(response);
        for (int i = 0; i < jsonArrayTag.length(); i++) {
            JSONObject jsonObjectTag = jsonArrayTag.getJSONObject(i);
            Tag tag = new Tag();
            tag.set_id(jsonObjectTag.getString(KEY_GENERIC_ID));
            tag.setName(jsonObjectTag.getString(KEY_TAG_NAME));

            tags.add(tag);
        }
        return tags;
    }
}
