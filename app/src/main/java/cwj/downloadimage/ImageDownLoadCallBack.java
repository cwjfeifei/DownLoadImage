package cwj.downloadimage;

import java.io.File;

/**
 * Created by CWJ on 2017/3/8.
 */

public interface ImageDownLoadCallBack {
    void onDownLoadSuccess(File file);

    void onDownLoadFailed();
}
