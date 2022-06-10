package com.example.gamepikachu;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity
public class User {

        @PrimaryKey(autoGenerate = true)
        public Integer uid;

        @ColumnInfo(name = "name")
        public String nameUser;

        @ColumnInfo(name = "pass")
        public String passUser;

        public User(Integer uid, String nameUser, String passUser) {
                this.uid = uid;
                this.nameUser = nameUser;
                this.passUser = passUser;
        }
}
