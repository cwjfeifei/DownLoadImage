package cwj.downloadimage;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {
    private DownImageUtil mDownImageUtil;
    private ImageView iv_down;
    private Button btn_down;
    private TextView tv_path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv_down = (ImageView) findViewById(R.id.iv_down);
        btn_down = (Button) findViewById(R.id.btn_down);
        tv_path = (TextView) findViewById(R.id.tv_path);
        mDownImageUtil = new DownImageUtil(MainActivity.this);
        btn_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //权限管理
                XPermissionUtils.requestPermissions(MainActivity.this, 1, new String[]{Manifest.permission
                        .READ_EXTERNAL_STORAGE, Manifest.permission
                        .WRITE_EXTERNAL_STORAGE}, new XPermissionUtils.OnPermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        //第一个参数为下载地址，第二个参数为图片名，第三个参数为目录名
                        //  地址为格式：/storage/emulated/0/cwj/img/Test/1.png
                        mDownImageUtil.onDownLoad(
                                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1488950363453&di=a6f3bd7d1b2461d2b6a8c1bb7fa9aeb7&imgtype=0&src=http%3A%2F%2Fc.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2Fe7cd7b899e510fb3bde5709ddb33c895d1430c3f.jpg",
                                1,
                                "Test");
                    }

                    @Override
                    public void onPermissionDenied() {
                        Toast.makeText(MainActivity.this, "请打开权限！", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
        //图片回调处理
        mDownImageUtil.setImageCallBack(new ImageCallBack() {
            @Override
            public void onSuccess(String url) {
                Glide.with(MainActivity.this).load(url).into(iv_down);
                tv_path.setText("文件存储地址为：\n" + url);
            }

            @Override
            public void onFailed() {

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[]
            grantResults) {
        XPermissionUtils.onRequestPermissionsResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
