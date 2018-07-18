package service;

import model.Badge;
import model.Tag;
import org.json.JSONArray;
import org.json.JSONObject;
import popup.PopupView;
import utils.request.builder.OnRequestFailListener;
import utils.request.builder.OnRequestSuccessListener;
import utils.request.builder.RequestBuilder;

import javax.security.auth.login.FailedLoginException;

import java.util.ArrayList;

import static utils.Constants.*;

public class BadgeService {

    public static void getAllBadges(OnRequestSuccessListener successListener, OnRequestFailListener failListener) {
        RequestBuilder.builder()
                .setUrl(BASE_URL + EXTENDED_URL_BADGE)
                .setRequestMethod("GET")
                .addRequestProperty(REQUEST_PROPERTY_CONTENT_TYPE, REQUEST_PROPERTY_CONTENT_TYPE_JSON)
                .setOnResponseSuccessListener(successListener)
                .setOnResponseFailListener(failListener)
                .build();
    }

    public static void deleteBadge(String badgeId, OnRequestSuccessListener successListener, OnRequestFailListener failListener) {
        RequestBuilder.builder()
                .setUrl(BASE_URL + EXTENDED_URL_BADGE + badgeId)
                .setRequestMethod("DELETE")
                .addRequestProperty(REQUEST_PROPERTY_CONTENT_TYPE, REQUEST_PROPERTY_CONTENT_TYPE_JSON)
                .setOnResponseSuccessListener(successListener)
                .setOnResponseFailListener(failListener)
                .build();
    }

    public static void addBadge(String name, String description, String image, int points, OnRequestSuccessListener successListener, OnRequestFailListener failListener) {
        RequestBuilder.builder()
                .setUrl(BASE_URL + EXTENDED_URL_BADGE)
                .setRequestMethod("POST")
                .addRequestBodyParameter(BODY_PARAMETER_NAME, name)
                .addRequestBodyParameter(BODY_PARAMETER_DESCRIPTION, description)
                .addRequestBodyParameter(BODY_PARAMETER_THUMBNAIL_URL, image)
                .addRequestBodyParameter(BODY_PARAMETER_POINTS, points)
                .setOnResponseSuccessListener(successListener)
                .setOnResponseFailListener(failListener)
                .build();
    }

    public static ArrayList<Badge> parseBadges(String response) {
        ArrayList<Badge> badges = new ArrayList<>();
        JSONArray jsonArrayBadge = new JSONArray(response);
        for (int i = 0; i < jsonArrayBadge.length(); i++) {
            JSONObject jsonObjectBadge = jsonArrayBadge.getJSONObject(i);
            Badge badge = new Badge();
            badge.set_id(jsonObjectBadge.getString(JSON_ENTRY_KEY_ID));
            badge.setName(jsonObjectBadge.getString(JSON_ENTRY_KEY_TAG_NAME));

            badges.add(badge);
        }
        return badges;
    }
}
