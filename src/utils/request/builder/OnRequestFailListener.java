package utils.request.builder;

public interface OnRequestFailListener {
    void onRequestFail(int errCode, String res);
}
