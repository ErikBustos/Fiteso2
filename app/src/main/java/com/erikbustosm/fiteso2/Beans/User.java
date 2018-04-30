package com.erikbustosm.fiteso2.Beans;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Erik Bustos M on 04/04/2018.
 */

public class User implements Parcelable {

    private String id;
    private String name;
    private int age;
    private String deporte;


    public User() {

    }

    public User(String id, String name, int age, String deporte) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.deporte = deporte;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getDeporte() {
        return deporte;
    }

    public void setDeporte(String deporte) {
        this.deporte = deporte;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeInt(this.age);
        dest.writeString(this.deporte);
    }

    protected User(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.age = in.readInt();
        this.deporte = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}






