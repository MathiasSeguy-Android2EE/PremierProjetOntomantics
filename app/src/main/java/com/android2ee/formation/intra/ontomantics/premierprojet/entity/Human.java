/**
 * <ul>
 * <li>Human</li>
 * <li>com.android2ee.formation.intra.ontomantics.premierprojet.entity</li>
 * <li>16/11/2015</li>
 * <p/>
 * <li>======================================================</li>
 * <p/>
 * <li>Projet : Mathias Seguy Project</li>
 * <li>Produit par MSE.</li>
 * <p/>
 * /**
 * <ul>
 * Android Tutorial, An <strong>Android2EE</strong>'s project.</br>
 * Produced by <strong>Dr. Mathias SEGUY</strong>.</br>
 * Delivered by <strong>http://android2ee.com/</strong></br>
 * Belongs to <strong>Mathias Seguy</strong></br>
 * ***************************************************************************************************************</br>
 * This code is free for any usage but can't be distribute.</br>
 * The distribution is reserved to the site <strong>http://android2ee.com</strong>.</br>
 * The intelectual property belongs to <strong>Mathias Seguy</strong>.</br>
 * <em>http://mathias-seguy.developpez.com/</em></br> </br>
 * <p/>
 * *****************************************************************************************************************</br>
 * Ce code est libre de toute utilisation mais n'est pas distribuable.</br>
 * Sa distribution est reservée au site <strong>http://android2ee.com</strong>.</br>
 * Sa propriété intellectuelle appartient à <strong>Mathias Seguy</strong>.</br>
 * <em>http://mathias-seguy.developpez.com/</em></br> </br>
 * *****************************************************************************************************************</br>
 */

package com.android2ee.formation.intra.ontomantics.premierprojet.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.android2ee.formation.intra.ontomantics.premierprojet.R;

/**
 * Created by Mathias Seguy - Android2EE on 16/11/2015.
 */
public class Human implements Parcelable {
    String name;
    String message;
    int picture;
    boolean isStupid;

    public boolean isStupid() {
        return isStupid;
    }

    public Human(String message,int position) {
        this.message = message;
        isStupid=Math.random()*10>4;
        if(isStupid){
            this.name="happy dummy guy";
            picture=R.drawable.ic_mood_black_48dp;
        }else{
            this.name="John doe";
            picture= R.mipmap.ic_human;
        }
    }

    public String getMessage() {
        return message;
    }

    public String getName() {
        return name;
    }

    public int getPicture() {
        return picture;
    }

    protected Human(Parcel in) {
        name = in.readString();
        message = in.readString();
        picture = in.readInt();
        isStupid = in.readByte() != 0x00;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(message);
        dest.writeInt(picture);
        dest.writeByte((byte) (isStupid ? 0x01 : 0x00));
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Human> CREATOR = new Parcelable.Creator<Human>() {
        @Override
        public Human createFromParcel(Parcel in) {
            return new Human(in);
        }

        @Override
        public Human[] newArray(int size) {
            return new Human[size];
        }
    };
}