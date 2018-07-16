package service;

import model.Challenge;
import utils.Log;
import utils.request.builder.OnRequestFailListener;
import utils.request.builder.OnRequestSuccessListener;
import utils.request.builder.RequestBuilder;

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
}
