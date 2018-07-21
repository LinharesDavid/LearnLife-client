package service;

import javafx.scene.Scene;
import model.Challenge;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;
import utils.Log;
import utils.request.builder.OnRequestFailListener;
import utils.request.builder.OnRequestSuccessListener;
import utils.request.builder.RequestBuilder;

import javax.swing.text.html.ImageView;

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

    public static void addChallenge(String name, String details, int points, int duration, JSONArray tags, JSONArray badge, int verified, OnRequestSuccessListener successListener, OnRequestFailListener failListener) {
        RequestBuilder.builder()
                .setUrl(BASE_URL + EXTENDED_URL_CHALLENGE)
                .setRequestMethod("POST")
                .addRequestBodyParameter(BODY_PARAMETER_NAME, name)
                .addRequestBodyParameter(JSON_ENTRY_KEY_CHALLENGE_DETAILS, details)
                .addRequestBodyParameter(JSON_ENTRY_KEY_CHALLENGE_TAGS, tags)
                .addRequestBodyParameter(JSON_ENTRY_KEY_CHALLENGE_BADGE, badge)
                .addRequestBodyParameter(JSON_ENTRY_KEY_CHALLENGE_DURATION, duration)
                .addRequestBodyParameter(JSON_ENTRY_KEY_CHALLENGE_VERIFIED, verified)
                .addRequestBodyParameter("pointsGiven", points)
                .setOnResponseSuccessListener(successListener)
                .setOnResponseFailListener(failListener)
                .build();
    }

    public static void setChallengeImage(String challengeId, File image){
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpPost uploadFile = new HttpPost(BASE_URL + EXTENDED_URL_CHALLENGE + challengeId + "/image");
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
