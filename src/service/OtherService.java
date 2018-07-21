package service;

import org.json.JSONObject;
import popup.PopupView;
import utils.Log;
import utils.request.builder.OnRequestFailListener;
import utils.request.builder.OnRequestSuccessListener;
import utils.request.builder.RequestBuilder;

import java.io.*;
import java.net.URL;

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
                extendedUrl = EXTENDED_URL_CHALLENGE;
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

    public static void getUserImage(String id, String imageUrl) {
        try {
            URL url = new URL(BASE_URL + imageUrl.substring(1));
            InputStream in = new BufferedInputStream(url.openStream());
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int n = 0;
            while (-1 != (n = in.read(buf))) {
                out.write(buf, 0, n);
            }
            out.close();
            in.close();
            byte[] response = out.toByteArray();

            File file = new File("res/" + imageUrl + ".jpg");
            file.mkdirs();

            FileOutputStream fos = new FileOutputStream("res/" + imageUrl);
            fos.write(response);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
