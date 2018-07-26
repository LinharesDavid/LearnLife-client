package utils.request.builder;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.json.JSONObject;
import popup.PopupView;
import utils.Log;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static utils.Constants.*;

public class RequestBuilder {

    private String url;
    private Map<String, Object> requestBodyParameters = new HashMap<>();
    private Map<String, String> requestProperties = new HashMap<>();
    private String requestMethod;
    private OnRequestFailListener onResponseFailListener;
    private OnRequestSuccessListener onResponseSuccessListener;
    private JSONObject requestBodyParameterJson;
    private int connectTimeout = 1000;
    private int readTimeout = 1000;

    public static RequestBuilder builder() {
        return new RequestBuilder();
    }

    public void build() {
        try {
            JSONObject jsonObject = new JSONObject();
            if (!requestBodyParameters.isEmpty()) {
                requestBodyParameters.forEach(jsonObject::put);
            } else {
                jsonObject = requestBodyParameterJson;
            }
            URL url = new URL(this.url);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod(requestMethod);
            con.setDoInput(true);
            if (requestProperties.isEmpty())
                con.addRequestProperty(REQUEST_PROPERTY_CONTENT_TYPE, REQUEST_PROPERTY_CONTENT_TYPE_JSON);
            else
                requestProperties.forEach(con::addRequestProperty);
            con.setConnectTimeout(connectTimeout);
            con.setReadTimeout(readTimeout);
            Log.i("sending " + con.getRequestMethod() + " to " + con.getURL());
            if (this.requestMethod.equals("POST") || this.requestMethod.equals("PUT")) {
                con.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
                wr.write(jsonObject.toString());
                wr.flush();
                Log.i("with json parameter :\n" + prettify(jsonObject.toString()));
            }
            StringBuffer content;
            int errCode = con.getResponseCode();
            if (errCode == 200 || errCode == 201) {
                content = readResponse(con.getInputStream());
                assert content != null;
                Log.i(con.getRequestMethod() + " to " + con.getURL() + " succeed : " + errCode + "\n" + prettify(content.toString()));
                onResponseSuccessListener.onRequestSuccess(content == null ? ERR_READING_RESPONSE : content.toString());
            } else if ((errCode = con.getResponseCode()) != 200) {
                Log.e(con.getRequestMethod() + " to " + con.getURL() + " failed : " + errCode);
                content = readResponse(con.getErrorStream());
                assert content != null;
                Log.e(content.toString());
                onResponseFailListener.onRequestFail(errCode, content == null ? ERR_UNKNOWN : content.toString());
            } else
            con.disconnect();

        } catch (Exception e){
            e.printStackTrace();
            PopupView popupView = new PopupView();
            popupView.start(ERR, ERR_UNKNOWN, OK);
            popupView.addOnBtnOkListener(null);
        }

    }

    private StringBuffer readResponse(InputStream is) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(is));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            return content;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }


    }

    public RequestBuilder setUrl(String url) {
        this.url = url;
        return this;
    }

    public RequestBuilder addRequestBodyParameter(String key, Object value) {
        this.requestBodyParameters.put(key, value);
        return this;
    }

    public RequestBuilder addRequestProperty(String key, String value) {
        this.requestProperties.put(key, value);
        return this;
    }

    public RequestBuilder setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
        return this;
    }

    public RequestBuilder setOnResponseFailListener(OnRequestFailListener onResponseFailListener) {
        this.onResponseFailListener = onResponseFailListener;
        return this;
    }

    public RequestBuilder setOnResponseSuccessListener(OnRequestSuccessListener onResponseSuccessListener) {
        this.onResponseSuccessListener = onResponseSuccessListener;
        return this;
    }

    public RequestBuilder setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
        return this;
    }

    public RequestBuilder setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
        return this;
    }

    public RequestBuilder setRequestBodyParameterJson(JSONObject requestBodyParameterJson) {
        this.requestBodyParameterJson = requestBodyParameterJson;
        return this;
    }

    private String prettify(String entry) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(entry);
        return gson.toJson(je);
    }
}
