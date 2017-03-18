package com.afyaplan.afya_plan;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by dpizarro
 */
public class Person implements Parcelable {

    String name;
    String age;
    Bitmap photo;
    private boolean selected;

    public Person(String name, String age, Bitmap photo, boolean selected) {
        this.name = name;
        this.age = age;
        this.photo = photo;
        this.selected = selected;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getName() {
        return name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.age);
        dest.writeValue(this.photo);
        dest.writeByte(selected ? (byte) 1 : (byte) 0);
    }

    private Person(Parcel in) {
        this.name = in.readString();
        this.age = in.readString();
        this.photo = (Bitmap) in.readValue(getClass().getClassLoader());
        this.selected = in.readByte() != 0;
    }

    public static final Creator<Person> CREATOR = new Creator<Person>() {
        public Person createFromParcel(Parcel source) {
            return new Person(source);
        }

        public Person[] newArray(int size) {
            return new Person[size];
        }
    };
}
