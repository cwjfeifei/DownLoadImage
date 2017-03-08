package cwj.downloadimage;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by CWJ on 2017/3/8.
 */

public class DownImageUtil {
    /**
     * 单线程列队执行
     */
    private static ExecutorService singleExecutor = null;
    private Context mContext;
    private ImageCallBack mImageCallBack;

    public DownImageUtil(Context context) {
        mContext = context;

    }

    public void setImageCallBack(ImageCallBack imageCallBack) {
        mImageCallBack = imageCallBack;
    }

    /**
     * 执行单线程列队执行
     */
    public void runOnQueue(Runnable runnable) {
        if (singleExecutor == null) {
            singleExecutor = Executors.newSingleThreadExecutor();
        }
        singleExecutor.submit(runnable);
    }

    /**
     * 启动图片下载线程
     *
     * @param url  地址
     * @param i    标识
     * @param sort 目录名
     */
    public void onDownLoad(String url, final int i, final String sort) {
        DownLoadImageService service = new DownLoadImageService(mContext,
                url,
                new ImageDownLoadCallBack() {
                    @Override
                    public void onDownLoadSuccess(File file) {
                       final String desPath = DownFileUtils.getImagesDir(mContext, sort) + i + ".png";
                        savePhotoToSDCard(file.getPath(), desPath);
                        ((Activity)mContext).runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                mImageCallBack.onSuccess(desPath);
                            }
                        });

                    }

                    @Override
                    public void onDownLoadFailed() {
                        mImageCallBack.onFailed();
                    }
                });
        //启动图片下载线程
        runOnQueue(service);
    }

    /**
     * 保存到SD卡
     *
     * @param path
     * @param desPath
     */
    public static void savePhotoToSDCard(String path, String desPath) {
        Bitmap bitmap = null;
        try {
            File file = new File(path);
            if (!file.exists()) {
                return;
            }
            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, opts);
            opts.inJustDecodeBounds = false;
            bitmap = BitmapFactory.decodeFile(path, opts);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int options = 100;
            bitmap.compress(Bitmap.CompressFormat.PNG, options, baos);
            File file2 = new File(desPath);
            FileOutputStream fOut = new FileOutputStream(file2);
            fOut.write(baos.toByteArray());
            fOut.flush();
            fOut.close();
            baos.flush();
            baos.close();
            bitmap.recycle();
            bitmap = null;
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }
}
