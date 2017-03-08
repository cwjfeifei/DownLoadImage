package cwj.downloadimage;

/**
 * Created by CWJ on 2017/3/8.
 */

public interface ImageCallBack {
    void onSuccess(String url);

    void onFailed();
}
