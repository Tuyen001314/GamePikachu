package com.example.gamepikachu;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    public EditText edtName, edtPass;
    String bo;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//
//        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "database-name").allowMainThreadQueries().build();
//        UserDao userDao = db.userDao();
//        List<User> users = userDao.getAll();
//        User user1 = new User(102,"tran","duc bo");
//        users.add(user1);
//        userDao.insertAll(user1);
//        userDao.getAll().forEach(new Consumer<User>() {
//            @Override
//            public void accept(User user) {
//                Log.d("bo", user.firstName);
//            }
//        });

        edtName = findViewById(R.id.editTextUsername);
        edtPass = findViewById(R.id.editTextPassword);



    }

    public void clickBtnStart(View view) {
        Intent intent = new Intent(this, MainActivity1.class);
        intent.putExtra("name", edtName.getText());
        startActivity(intent);
    }
}
