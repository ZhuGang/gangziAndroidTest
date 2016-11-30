package com.test.gangzi.retrofittest.activity;

import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.test.ApplicationTestCase;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.test.gangzi.retrofittest.AppApplication;
import com.test.gangzi.retrofittest.Model.Contributor;
import com.test.gangzi.retrofittest.R;
import com.test.gangzi.retrofittest.service.GitHub;
import com.xiaoka.android.common.utils.XKFileUtil;
import com.xiaoka.android.common.utils.XKToastUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity {

    private static final String API_URL = "https://api.github.com";

    private File mCameraPhotoFile;
    // 从相册中选择
    private static final int PHOTO_REQUEST_GALLERY = 1;
    // 拍照
    private static final int PHOTO_REQUEST_TAKEPHOTO = 2;
    // 本地行驶证路径
    private String mOwnerLicenseIconPath;

    public static final String DIRECTORY_NAME = "aaaaa";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = (TextView)findViewById(R.id.hello);

        //访问这个地址返回的是一个JsonArray,JsonArray的每一个元素都有login和contributions这2个key和其对应的value.提取出来封装进POJO对象中.
        AppApplication.getRestClient().getService(GitHub.class).contributors("square", "retrofit", new Callback<List<Contributor>>() {
            @Override
            public void success(List<Contributor> contributors, Response response) {
                for (Contributor contributor : contributors) {
                    Log.v("aaaaa: retrofit", contributor.getLogin() + " (" + contributor.getContributions() + ")");
                }
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });


        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showUploadImgDialog();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PHOTO_REQUEST_TAKEPHOTO:// 当选择拍照时调用
                // 当选择拍照时调用
                if(resultCode == RESULT_OK){
                    mOwnerLicenseIconPath = loadPictureToTakePhoto();
                    //uploadLicenseToCloud();
                }
                break;
            case PHOTO_REQUEST_GALLERY:// 当选择从本地获取图片时
                // 当选择从本地获取图片时
                if(resultCode == RESULT_OK){
                    mOwnerLicenseIconPath = loadPictureToGallery(data);
                    //uploadLicenseToCloud();
                }
                break;
        }
    }


    /**
     * <p>
     * <b>显示上传照片对话框</b>
     * </p>
     */
    private void showUploadImgDialog() {

//        if (mLicenseDialog == null) {
//            mLicenseDialog = new SelectGetLicenseImgMethodDialog();
//        }
//
//
//        mLicenseDialog.show(MainActivity.this, new SelectGetLicenseImgMethodDialog.ActionListener() {
//            @Override
//            public void onTakePhoto() {
//                callSystemCamera();
//            }
//
//            @Override
//            public void onCallGallery() {
//                callSystemGallery();
//            }
//        }, PictureBaseActivity.DIALOG_TYPE.DRIVING_LICENSE);

        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this)
                .setNegativeButton("相机", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        callSystemCamera();
                    }
                })
                .setPositiveButton("相册", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        callSystemGallery();
                    }
                })
                .create();
        alertDialog.show();
    }


    /**
     * 调用照相
     */
    private void callSystemCamera() {

        Intent _cameraintent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        mCameraPhotoFile = new File(XKFileUtil.getPhotoPath(this, DIRECTORY_NAME),
                getPhotoFileName());
        _cameraintent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(mCameraPhotoFile));
        startActivityForResult(_cameraintent,
                PHOTO_REQUEST_TAKEPHOTO);
    }

    /**
     * 拍照图片压缩路径
     * @return
     */
    public String loadPictureToTakePhoto() {
        String _compressPicPath = null;
        if (null != mCameraPhotoFile && mCameraPhotoFile.length() > 0)
            _compressPicPath = compressPic(mCameraPhotoFile.getPath());
        return _compressPicPath;

    }

    /**
     * 压缩图片,并上传至云盘
     *
     * @param path 原图片的路径
     * @return 压缩后图片的路径
     */
    private String compressPic(String path) {
        WindowManager _wm = MainActivity.this.getWindowManager();
        int _screenWidth = /*wm.getDefaultDisplay().getWidth()*/ 720;
        int _screenHeight = /*wm.getDefaultDisplay().getHeight()*/ 1080;
        System.out.println("屏幕宽高：" + _screenWidth + "-" + _screenHeight);

        //2.得到图片的宽高。
        BitmapFactory.Options _opts = new BitmapFactory.Options();//解析位图的附加条件
        _opts.inJustDecodeBounds = true;//不去解析真实的位图，只是获取这个位图的头文件信息
        Bitmap _bitmap = BitmapFactory.decodeFile(path, _opts);
        int _bitmapWidth = _opts.outWidth;
        int _bitmapHeight = _opts.outHeight;
        System.out.println("图片宽高： " + _bitmapWidth + "-" + _bitmapHeight);

        //3.计算缩放比例
        int _dx = _bitmapWidth / _screenWidth;
        int _dy = _bitmapHeight / _screenHeight;
        int _scale = 1;

        if (_dx >= _dy) {
            _scale = _dx;
        }

        if (_dy > _dx) {
            _scale = _dy;
        }


        //4.缩放加载图片到内存。
        _opts.inSampleSize = _scale;
        //真正的去解析这个位图。
        _opts.inJustDecodeBounds = false;
        _bitmap = BitmapFactory.decodeFile(path, _opts);


        File _destPath = new File(XKFileUtil.getPhotoPath(MainActivity.this, DIRECTORY_NAME), getPhotoFileName("_s"));
        FileOutputStream _fOut = null;
        try {
            _destPath.createNewFile();
            _fOut = new FileOutputStream(_destPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        _bitmap.compress(Bitmap.CompressFormat.JPEG, 50, _fOut);

        try {
            _bitmap.recycle();
            _fOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            _fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return _destPath.getPath();

    }

    /**
     * 使用系统当前日期加以调整作为照片的名称
     * @return
     */
    private String getPhotoFileName() {
        Date _date = new Date(System.currentTimeMillis());
        SimpleDateFormat _dateFormat = new SimpleDateFormat(
                "'IMG'_yyyyMMdd_HHmmss");
        return _dateFormat.format(_date) + ".jpg";
    }
    /**
     * 得到照片的名称
     * @return
     */
    private String getPhotoFileName(String pFilePostfix) {
        Date _date = new Date(System.currentTimeMillis());
        SimpleDateFormat _dateFormat = new SimpleDateFormat(
                "'IMG'_yyyyMMdd_HHmmss");
        return _dateFormat.format(_date) + pFilePostfix + ".jpg";
    }

    /**
     * 调用选择相册中的图片
     * @return
     */
    private void callSystemGallery() {
        Intent _intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        _intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(_intent, PHOTO_REQUEST_GALLERY);
    }

    /**
     * 调用相机获取图片时，加裁图片的方法
     *
     * @return
     */
    public String loadPictureToGallery(Intent data) {
        Cursor _cursorDriving = null;
        String _compressPicPath = null;
        try {
            String _picturePath = getPhotoPathByLocalUri(MainActivity.this, data);
            _compressPicPath = compressPic(_picturePath);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (_cursorDriving != null) {
                _cursorDriving.close();
            }
        }
        if (TextUtils.isEmpty(_compressPicPath)) {
            try {

                _compressPicPath = compressPic(data.getData().getPath());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (TextUtils.isEmpty(_compressPicPath)) {
            XKToastUtil.showToast("从相册中选择图片失败！",MainActivity.this);
        }

        return _compressPicPath;

    }

    /**
     * 通过本地的Uri得到照片路径
     * @param context
     * @param data
     * @return
     */
    public String getPhotoPathByLocalUri(Context context, Intent data) {
        Uri _selectedImage = data.getData();
        String[] _filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor _cursor = context.getContentResolver().query(_selectedImage,
                _filePathColumn, null, null, null);
        _cursor.moveToFirst();
        int _columnIndex = _cursor.getColumnIndex(_filePathColumn[0]);
        String _picturePath = _cursor.getString(_columnIndex);
        _cursor.close();
        return _picturePath;
    }

}
