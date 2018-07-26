package service;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;
import utils.request.builder.OnRequestFailListener;
import utils.request.builder.OnRequestSuccessListener;
import utils.request.builder.RequestBuilder;

import java.io.File;
import java.io.FileInputStream;

import static utils.Constants.*;

public class ChallengeService {

    public static void getAllChallenges(OnRequestSuccessListener successListener, OnRequestFailListener failListener) {
        RequestBuilder.builder()
                .setUrl(BASE_URL + EXTENDED_URL_CHALLENGE)
                .setRequestMethod("GET")
                .addRequestProperty(REQUEST_PROPERTY_CONTENT_TYPE, REQUEST_PROPERTY_CONTENT_TYPE_JSON)
                .setOnResponseSuccessListener(successListener)
                .setOnResponseFailListener(failListener)
                .build();
    }

    public static void deleteChallenge(String challengeId, OnRequestSuccessListener successListener, OnRequestFailListener failListener) {
        RequestBuilder.builder()
                .setUrl(BASE_URL + EXTENDED_URL_CHALLENGE + challengeId)
                .setRequestMethod("DELETE")
                .addRequestProperty(REQUEST_PROPERTY_CONTENT_TYPE, REQUEST_PROPERTY_CONTENT_TYPE_JSON)
                .setOnResponseSuccessListener(successListener)
                .setOnResponseFailListener(failListener)
                .build();
    }

    public static void addChallenge(String name, String details, int points, int duration, JSONArray tags, String badge, int verified, OnRequestSuccessListener successListener, OnRequestFailListener failListener) {
        RequestBuilder requestBuilder = RequestBuilder.builder();
        if (badge != null) {
            requestBuilder
                .addRequestBodyParameter(KEY_CHALLENGE_BADGE, badge);
        }
        requestBuilder
                .setUrl(BASE_URL + EXTENDED_URL_CHALLENGE)
                .setRequestMethod("POST")
                .addRequestBodyParameter(KEY_CHALLENGE_NAME, name)
                .addRequestBodyParameter(KEY_CHALLENGE_DETAILS, details)
                .addRequestBodyParameter(KEY_CHALLENGE_TAGS, tags)
                .addRequestBodyParameter(KEY_CHALLENGE_DURATION, duration)
                .addRequestBodyParameter(KEY_CHALLENGE_VERIFIED, verified)
                .addRequestBodyParameter(KEY_CHALLENGE_POINTS_GIVEN, points)
                .setOnResponseSuccessListener(successListener)
                .setOnResponseFailListener(failListener)
                .build();
    }

    public static void setChallengeImage(String challengeId, File image){
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpPost uploadFile = new HttpPost(BASE_URL + EXTENDED_URL_CHALLENGE + challengeId + EXTENDED_URL_IMAGE);
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();

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
