package com.example.gamepikachu;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import java.util.List;
import java.util.function.Consumer;

public class MainActivity extends AppCompatActivity {

    public EditText edtName, edtPass;
    Button btnStart;
    String bo;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "database-user").allowMainThreadQueries().build();
        UserDao userDao = db.userDao();
        List<User> users = userDao.getAll();
        //User user1 = new User(null,"tran","duc bo");
        //users.add(user1);
        //userDao.insertAll(user1);
        userDao.getAll().forEach(new Consumer<User>() {
            @Override
            public void accept(User user) {
                //Log.d("bo", user.firstName);
            }
        });

        userDao.delete(users.get(1));
        edtName = findViewById(R.id.editTextUsername);
        edtPass = findViewById(R.id.editTextPassword);

        btnStart = findViewById(R.id.buttonLogin);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = new User(null,edtName.getText() + "",edtPass.getText() + "");
                userDao.insertAll(user);
                Intent intent = new Intent(MainActivity.this, MainActivity1.class);
                intent.putExtra("name", edtName.getText());
                startActivity(intent);
            }
        });

    }
}
