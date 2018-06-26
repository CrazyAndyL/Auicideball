package com.example.lenovo.auicideball.rendering;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.lenovo.auicideball.R;
import com.example.lenovo.auicideball.backstage.Remember_User;
import com.example.lenovo.auicideball.backstage.User_data;

import org.litepal.crud.DataSupport;

import java.util.List;

public class MainActivity_Login_page extends AppCompatActivity {

    private EditText usernameEdit;
    private EditText passwordEdit;
    private Boolean login_check=false;
    private EditText editText;
    private CheckBox checkBox;
    private Button login_button;
    private Button register_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__login_page);
        usernameEdit= (EditText) findViewById(R.id.user_name_login);
        Remember_User first = DataSupport.findFirst(Remember_User.class);
        usernameEdit.setText(first.getUser_name());
        passwordEdit.setText(first.getPassword());
        passwordEdit=(EditText)findViewById(R.id.pass_word_login);

        register_button = (Button)findViewById(R.id.Register_button);
        register_button.setOnClickListener(new View.OnClickListener() {      //跳转到注册界面
            @Override
            public void onClick(View v) {
                Intent register_intent = new Intent(MainActivity_Login_page.this,MainActivity_Register_page.class);
                startActivity(register_intent);
            }
        });
        login_button = (Button)findViewById(R.id.Login_ok_button);
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username=usernameEdit.getText().toString();
                String password=passwordEdit.getText().toString();
                if (username.length()==0){
                    Toast.makeText(MainActivity_Login_page.this,"请输入用户名",Toast.LENGTH_SHORT).show();
                }
                if (password.length()==0){
                    Toast.makeText(MainActivity_Login_page.this,"请输入密码",Toast.LENGTH_SHORT).show();
                }
                List<User_data> user_datas = DataSupport.findAll(User_data.class);
                for (User_data user_data1 : user_datas){
                    if(user_data1.getUser_name().equals(username)){
                        if (user_data1.getPassword().equals(password)){
                            DataSupport.deleteAll(Remember_User.class);
                            Remember_User remember_user = new Remember_User();
                            remember_user.setUser_name(username);
                            remember_user.setPassword(password);
                            remember_user.save();
                            login_check = true;
//                            Intent intent = new Intent(MainActivity_Login_page.this,Game_List.class);
//                            intent.putExtra("extra_data",username);
//                            startActivity(intent);                     //TODO  传递用户名给排行榜

                        }else {
                            Toast.makeText(MainActivity_Login_page.this,"密码不正确",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                if (login_check==false){
                    Toast.makeText(MainActivity_Login_page.this,"用户名不存在",Toast.LENGTH_SHORT).show();
                }
            }
        });

        editText = (EditText) findViewById(R.id.pass_word_login);
        checkBox = (CheckBox) findViewById(R.id.checkbox);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }else{
                    editText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });
    }
}
