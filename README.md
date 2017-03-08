# DownImageUtil
基于Glide的图片下载工具类，简单、高效

预览图：

<img src="https://github.com/cwjfeifei/DownLoadImage/blob/master/preview/download.gif" width="30%" height="30%">


How to use
--------
第一句 , 实例化:
```
 mDownImageUtil = new DownImageUtil(MainActivity.this);
```
第二句 , 下载图片:
```
  //第一个参数为下载地址，第二个参数为图片名，第三个参数为目录名
   //  地址为格式：/storage/emulated/0/cwj/img/Test/1.png
  mDownImageUtil.onDownLoad("url",1,"Test");;
```
第三句 , 设置回调，显示图片:
```
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
```
 
# License

    Copyright 2016 cwjfeifei

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
    
        http://www.apache.org/licenses/LICENSE-2.0
    
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
