package service;

import model.Badge;
import model.Tag;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;
import org.json.JSONObject;
import popup.PopupView;
import utils.request.builder.OnRequestFailListener;
import utils.request.builder.OnRequestSuccessListener;
import utils.request.builder.RequestBuilder;

import javax.security.auth.login.FailedLoginException;

import java.io.File;
import java.io.FileInputStream;
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

    public static void addBadge(String name, String description, int points, OnRequestSuccessListener successListener, OnRequestFailListener failListener) {
        RequestBuilder.builder()
                .setUrl(BASE_URL + EXTENDED_URL_BADGE)
                .setRequestMethod("POST")
                .addRequestBodyParameter(BODY_PARAMETER_NAME, name)
                .addRequestBodyParameter(BODY_PARAMETER_DESCRIPTION, description)
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

    public static void setBadgeImage(String badgeId, File image){
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpPost uploadFile = new HttpPost(BASE_URL + EXTENDED_URL_BADGE + badgeId + "/image");
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();

            // This attaches the file to the POST:
            builder.addBinaryBody(
                    "image",
                    new FileInputStream(image),
                    ContentType.APPLICATION_OCTET_STREAM,
                    image.getName()
            );

            HttpEntity multipart = builder.build();
            uploadFile.setEntity(multipart);
            CloseableHttpResponse response = httpClient.execute(uploadFile);
            HttpEntity responseEntity = response.getEntity();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
