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
    private Button register_ok_button,back_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__register_page);

        usernameEdit = (EditText)findViewById(R.id.user_name_register);
        passwordEdit = (EditText)findViewById(R.id.pass_word_register);
        re_passwordEdit = (EditText)findViewById(R.id.re_pass_word_register);

        /*注册*/
        register_ok_button = (Button)findViewById(R.id.Register_ok_button);
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
                                user_data.setHead_portrait("");
                                user_data.setScore(0);
                                user_data.save();
                                Remember_User remember_user = new Remember_User();
                                remember_user.setUser_name(username);
                                remember_user.setPassword(password);
                                remember_user.setHead_portrait("");
                                remember_user.setScore(0);
                                remember_user.save();
                                MainActivity_Dialog back_login = new MainActivity_Dialog(MainActivity_Register_page.this);


                                back_login.setTitle("注册成功");
                                back_login.setMessage("是否跳转登录界面");
                                back_login.setCancelable(false);
                                back_login.setYesOnclickListener("YES", new MainActivity_Dialog.onYesOnclickListener() {
                                    @Override
                                    public void onYesClick() {
                                        Intent login_intent = new Intent(MainActivity_Register_page.this,MainActivity_Login_page.class);
                                        startActivity(login_intent);
                                        finish();
                                    }
                                });
                                back_login.setNoOnclickListener("NO", new MainActivity_Dialog.onNoOnclickListener() {
                                    @Override
                                    public void onNoClick() {
                                        finish();
                                    }
                                });
                                back_login.show();
                                decide = true;
                            }else {
                                Toast.makeText(MainActivity_Register_page.this,"两次密码不同",Toast.LENGTH_SHORT).show();
                            }
                        }else {}
                    }else {
                        Toast.makeText(MainActivity_Register_page.this,"密码不能为空且密码长度小于16",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(MainActivity_Register_page.this,"用户名不能为空且长度小于8",Toast.LENGTH_SHORT).show();
                }
            }
        });

        /*返回按钮*/
        back_button = (Button)findViewById(R.id.Back_button);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}