package com.example.lenovo.auicideball.rendering;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.auicideball.R;
import com.example.lenovo.auicideball.backstage.Remember_User;
import com.example.lenovo.auicideball.backstage.User_data;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;
import org.w3c.dom.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class MainActivity_personal_information extends AppCompatActivity {

    private ImageView mUser_imageView;
    private TextView mUser_name;
    private ImageView mUser_game_imageView;
    String path = null;
    public static final int CHOOSE_PHOTO = 2;
    public static final int TAKE_PHOTO = 1;
    private Uri imageUri;
    String[] items = new String[]{"拍照","相册"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_personal_information);
        LitePal.getDatabase();
//        mUser_game_imageView = (ImageView) findViewById(R.id.user_game_imageView);
        mUser_imageView = (ImageView) findViewById(R.id.user_imageView);

        /*查找数据库是否存有照片，并显示照片*/
        Remember_User first1 = DataSupport.findFirst(Remember_User.class);
        List<User_data> user_datas = DataSupport.findAll(User_data.class);
        for (User_data user_data_1:user_datas){
            if (user_data_1.getUser_name().equals(first1.getUser_name())){
                Bitmap bitmap = BitmapFactory.decodeFile(user_data_1.getHead_portrait());
                mUser_imageView.setImageBitmap(bitmap);
            }
        }

        /*点击昵称切换账号*/
        mUser_name = (TextView) findViewById(R.id.user_name);
        mUser_name.setText(first1.getUser_name());
        mUser_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity_personal_information.this,MainActivity_Login_page.class);
                startActivity(intent);
            }
        });

        /*点击照片编辑照片*/
        mUser_imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(MainActivity_personal_information.this).setTitle("选择获取图片方式").setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        switch (which){
                            case 0:
                                File outputImage = new File(getExternalCacheDir(),"output_image.jpg");
                                try {
                                    if (outputImage.exists()){
                                        outputImage.delete();
                                    }
                                    outputImage.createNewFile();
                                }catch (IOException e){
                                    e.printStackTrace();
                                }
                                imageUri = Uri.fromFile(outputImage);
                                path = imageUri.getPath();
                                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                                intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                                startActivityForResult(intent,TAKE_PHOTO);
                                break;
                            case 1:
                                if (ContextCompat.checkSelfPermission(MainActivity_personal_information.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                                    ActivityCompat.requestPermissions(MainActivity_personal_information.this,
                                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                                }else {
                                    openAlbum();
                                }
                                break;
                                default:
                                    break;
                        }
                    }
                }).show();
            }
        });

        //TODO 添加小球皮肤 recyclerView 横向滑动点击显示

        /*退出程序保存数据*/
        Button exit = (Button)findViewById(R.id.exit_button);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Remember_User first = DataSupport.findFirst(Remember_User.class);
                User_data user_data = new User_data();
                user_data.setHead_portrait(path);
                user_data.updateAll("user_name = ?",first.getUser_name());
                finish();
            }
        });
    }
    private void openAlbum(){
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent,CHOOSE_PHOTO);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    openAlbum();
                }else {
                    Toast.makeText(this,"you denide the permission",Toast.LENGTH_SHORT).show();
                }
                break;
                default:
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK){
                    try {
                        //将拍摄的照片显示处理
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        mUser_imageView.setImageBitmap(bitmap);
                    }catch (FileNotFoundException e){
                        e.printStackTrace();
                    }
                }
                break;
            case  CHOOSE_PHOTO:
                handleImageOnKitKat(data);
                break;
            default:
                break;
        }
    }
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void handleImageOnKitKat(Intent data){
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this,uri)){           //TODO
            //如果是document类型的uri，则通过document id 处理
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())){
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);
            }else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())){
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),Long.valueOf(docId));
                imagePath = getImagePath(contentUri,null);
            }
        }else if ("content".equalsIgnoreCase(uri.getScheme())){
            //如果是content类型的Uri ，则使用普通方式处理
            imagePath = getImagePath(uri,null);
        }else if ("file".equalsIgnoreCase(uri.getScheme())){
            //如果是file类型的uri，直接获取图片路径即可
            imagePath = uri.getPath();
        }
        displayImage(imagePath);   //根据图片路径显示图片
    }

    private String getImagePath(Uri uri,String selection){
        String path = null;
        // 通过uri和selection来过去真是的图片路径
        Cursor cursor = getContentResolver().query(uri,null,selection,null,null);
        if (cursor!=null){
            if (cursor.moveToFirst()){
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }
    private void displayImage(String imagePath){
        path=imagePath;
        if (imagePath!=null){
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            mUser_imageView.setImageBitmap(bitmap);
        }else {
            Toast.makeText(this,"failed to get image",Toast.LENGTH_SHORT).show();
        }
    }
}
