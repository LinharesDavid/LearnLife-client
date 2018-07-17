package service;

import org.json.JSONObject;
import popup.PopupView;
import utils.request.builder.OnRequestFailListener;
import utils.request.builder.OnRequestSuccessListener;
import utils.request.builder.RequestBuilder;

import javax.security.auth.login.FailedLoginException;

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
}
