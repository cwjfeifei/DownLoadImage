package cwj.downloadimage;

import android.content.Context;
import android.os.Environment;
import android.os.storage.StorageManager;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by CWJ on 2017/3/8.
 */

public class DownFileUtils {
    public static final String IMAGES_DIR = "img";
    public static final String ROOT_DIR = "cwj";

    /**
     * @return String
     * @Title: getImagesDir
     * @Description: 获取icon目录
     */
    public static String getImagesDir(Context context, String time) {
        return getDir(IMAGES_DIR, time, context);
    }

    /**
     * @return boolean
     * @Title: isSDCardAvailable
     * @Description: 判断SD卡是否挂载
     */
    public static boolean isSDCardAvailable() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param name
     * @return String
     * @Title: getDir
     * @Description: 获取应用目录，当SD卡存在时，获取SD卡上的目录，当SD卡不存在时，获取应用的cache目录
     */
    public static String getDir(String name, String time, Context context) {
        StringBuilder sb = new StringBuilder();
        if (isSDCardAvailable()) {
            sb.append(getExternalStoragePath());
        } else {
            sb.append(getCachePath(context));
        }
        sb.append(name);
        sb.append(File.separator);
        sb.append(time);
        sb.append(File.separator);
        String path = sb.toString();
        if (createDirs(path)) {
            return path;
        } else {
            List<String> extSDCardPaths = getExtSDCardPath(context);
            if (extSDCardPaths != null && extSDCardPaths.size() >= 2) {
                sb = new StringBuilder();
                sb.append(getExtSDCardPath(context).get(1));
                sb.append(File.separator);
                sb.append(ROOT_DIR);
                sb.append(File.separator);
                sb.append(name);
                sb.append(File.separator);
                sb.append(time);
                sb.append(File.separator);
                path = sb.toString();
                if (createDirs(path)) {
                    return path;
                } else {
                    return null;
                }
            }
            return null;
        }
    }

    /**
     * 获取外置SD卡路径
     *
     * @return 应该就一条记录或空
     */
    public static List<String> getExtSDCardPath(Context context) {
        List<String> lResult = new ArrayList<String>();
        StorageManager storageManager = (StorageManager) context.getSystemService(Context.STORAGE_SERVICE);
        try {
            Class<?>[] paramClasses = {};
            Method getVolumePathsMethod = StorageManager.class.getMethod("getVolumePaths", paramClasses);
            getVolumePathsMethod.setAccessible(true);
            Object[] params = {};
            Object invoke = getVolumePathsMethod.invoke(storageManager, params);
            for (int i = 0; i < ((String[]) invoke).length; i++) {
                lResult.add(((String[]) invoke)[i]);
            }
        } catch (NoSuchMethodException e1) {
            e1.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return lResult;
    }

    /**
     * @param dirPath 文件夹创建的路径
     * @return boolean 是否创建成功
     * @Title: createDirs
     * @Description: 创建文件夹
     */
    public static boolean createDirs(String dirPath) {
        File file = new File(dirPath);
        if (!file.exists() || !file.isDirectory()) {
            return file.mkdirs();
        }
        return true;
    }

    /**
     * @return String
     * @Title: getExternalStoragePath
     * @Description: 获取SD下的应用目录
     */
    public static String getExternalStoragePath() {
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.getExternalStorageDirectory().getAbsolutePath());
        sb.append(File.separator);
        sb.append(ROOT_DIR);
        sb.append(File.separator);
        return sb.toString();
    }

    /**
     * @return String
     * @Title: getCachePath
     * @Description: 获取应用的cache目录
     */
    public static String getCachePath(Context context) {
        File f = context.getCacheDir();
        if (null == f) {
            return null;
        } else {
            return f.getAbsolutePath() + "/";
        }
    }
}
