package com.example.lenovo.auicideball.rendering;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Context;
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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.lenovo.auicideball.R;
import com.example.lenovo.auicideball.backstage.Remember_User;
import com.example.lenovo.auicideball.backstage.User_data;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class MainActivity_Register_page extends AppCompatActivity {

    private EditText usernameEdit;
    private EditText passwordEdit;
    private EditText re_passwordEdit;
    private Boolean decide=true;
//    private ImageView head_portrait;
//    String path = null;
//    public static final int CHOOSE_PHOTO = 2;
//    public static final int TAKE_PHOTO = 1;
//    private Uri imageUri;
//    String[] items = new String[]{"拍照","相册"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__register_page);
        usernameEdit = (EditText)findViewById(R.id.user_name_register);
        passwordEdit = (EditText)findViewById(R.id.pass_word_register);
        re_passwordEdit = (EditText)findViewById(R.id.re_pass_word_register);
//        head_portrait = (ImageView) findViewById(R.id.head_portrait_imageView);
        Button register_ok_button = (Button)findViewById(R.id.Register_ok_button);
        register_ok_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LitePal.getDatabase();
                String username = usernameEdit.getText().toString();
                String password = passwordEdit.getText().toString();
                String re_password = re_passwordEdit.getText().toString();
                if (username.length()<8 && username.length()>0){
                    if (password.length()<16 && password.length()>0){
                        List<User_data> user_datas = DataSupport.findAll(User_data.class);
                        for (User_data user_data:user_datas){
                            if (user_data.getUser_name().equals(username)){
                                decide = false;
                                Toast.makeText(MainActivity_Register_page.this,"改用户名已存在",Toast.LENGTH_SHORT).show();
                            }
                        }
                        if (decide){
                            if (password.equals(re_password)){
                                User_data user_data = new User_data();
                                user_data.setUser_name(username);
                                user_data.setPassword(password);
//                                user_data.setHead_portrait(path);
                                user_data.setScore(0);
                                user_data.save();
                                Remember_User remember_user = new Remember_User();
                                remember_user.setUser_name(username);
                                remember_user.setPassword(password);
                                remember_user.save();
                                final AlertDialog.Builder back_login = new AlertDialog.Builder(MainActivity_Register_page.this);
                                back_login.setTitle("注册成功");
                                back_login.setMessage("是否跳转登录界面");
                                back_login.setCancelable(false);
                                back_login.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
//                                        Intent login_intent = new Intent(MainActivity_Register_page.this)
//                                        startActivity(login_intent);
                                    }
                                });
                                back_login.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
                                back_login.show();
                                decide = true;
                            }else {
                                Toast.makeText(MainActivity_Register_page.this,"两次密码不同",Toast.LENGTH_SHORT).show();
                            }
                        }else {

                        }
                    }else {
                        Toast.makeText(MainActivity_Register_page.this,"密码不能为空且密码长度小于16",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(MainActivity_Register_page.this,"用户名不能为空且长度小于8",Toast.LENGTH_SHORT).show();
                }
            }
        });
        Button back_button = (Button)findViewById(R.id.Back_button);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
//        head_portrait.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new AlertDialog.Builder(MainActivity_Register_page.this).setTitle("选择获取图片方式").setItems(items, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                        switch (which){
//                            case 0:
//                                File outputImage = new File(getExternalCacheDir(),"output_image.jpg");
//                                try {
//                                    if (outputImage.exists()){
//                                        outputImage.delete();
//                                    }
//                                    outputImage.createNewFile();
//                                }catch (IOException e){
//                                    e.printStackTrace();
//                                }
//                                imageUri = Uri.fromFile(outputImage);
//                                path = imageUri.getPath();
//                                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
//                                intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
//                                startActivityForResult(intent,TAKE_PHOTO);
//                                break;
//                            case 1:
//                                if (ContextCompat.checkSelfPermission(MainActivity_Register_page.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
//                                    ActivityCompat.requestPermissions(MainActivity_Register_page.this,
//                                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
//                                }else {
//                                    openAlbum();
//                                }
//                                break;
//                                default:
//                                    break;
//                        }
//                    }
//                }).show();
//            }
//        });
//    }
//    private void openAlbum(){
//        Intent intent = new Intent("android.intent.action.GET_CONTENT");
//        intent.setType("image/*");
//        startActivityForResult(intent,CHOOSE_PHOTO);
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        switch (requestCode){
//            case 1:
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
//                    openAlbum();
//                }else {
//                    Toast.makeText(this,"you denide the permission",Toast.LENGTH_SHORT).show();
//                }
//                break;
//                default:
//        }
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        switch (requestCode){
//            case TAKE_PHOTO:
//                if (resultCode == RESULT_OK){
//                    try {
//                        //将拍摄的照片显示处理
//                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
//                        head_portrait.setImageBitmap(bitmap);
//                    }catch (FileNotFoundException e){
//                        e.printStackTrace();
//                    }
//                }
//                break;
//            case  CHOOSE_PHOTO:
//                handleImageOnKitKat(data);
//                break;
//            default:
//                break;
//        }
//    }
//    @TargetApi(Build.VERSION_CODES.KITKAT)
//    private void handleImageOnKitKat(Intent data){
//        String imagePath = null;
//        Uri uri = data.getData();
//        if (DocumentsContract.isDocumentUri(this,uri)){           //TODO
//            //如果是document类型的uri，则通过document id 处理
//            String docId = DocumentsContract.getDocumentId(uri);
//            if ("com.android.providers.media.documents".equals(uri.getAuthority())){
//                String id = docId.split(":")[1];
//                String selection = MediaStore.Images.Media._ID + "=" + id;
//                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);
//            }else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())){
//                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),Long.valueOf(docId));
//                imagePath = getImagePath(contentUri,null);
//            }
//        }else if ("content".equalsIgnoreCase(uri.getScheme())){
//            //如果是content类型的Uri ，则使用普通方式处理
//            imagePath = getImagePath(uri,null);
//        }else if ("file".equalsIgnoreCase(uri.getScheme())){
//            //如果是file类型的uri，直接获取图片路径即可
//            imagePath = uri.getPath();
//        }
//        displayImage(imagePath);   //根据图片路径显示图片
//    }
//
//    private String getImagePath(Uri uri,String selection){
//        String path = null;
//        // 通过uri和selection来过去真是的图片路径
//        Cursor cursor = getContentResolver().query(uri,null,selection,null,null);
//        if (cursor!=null){
//            if (cursor.moveToFirst()){
//                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
//            }
//            cursor.close();
//        }
//        return path;
//    }
//    private void displayImage(String imagePath){
//        path=imagePath;
//        if (imagePath!=null){
//            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
//            head_portrait.setImageBitmap(bitmap);
//        }else {
//            Toast.makeText(this,"failed to get image",Toast.LENGTH_SHORT).show();
//        }
    }
}
