package com.erikbustosm.fiteso2.Beans;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class RutinaSemanal implements Parcelable {
    private String id;
    private String Nombre;
    private String Descripción;
    private String publica;
    private List<RutinaDia> rutinaDia;
    private String imageURL;

    public RutinaSemanal(String id, String nombre, String descripción, String publica, List<RutinaDia> rutinaDia, String imageURL) {
        this.id = id;
        Nombre = nombre;
        Descripción = descripción;
        this.publica = publica;
        this.rutinaDia = rutinaDia;
        this.imageURL = imageURL;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getDescripción() {
        return Descripción;
    }

    public void setDescripción(String descripción) {
        Descripción = descripción;
    }

    public String getPublica() {
        return publica;
    }

    public void setPublica(String publica) {
        this.publica = publica;
    }

    public List<RutinaDia> getRutinaDia() {
        return rutinaDia;
    }

    public void setRutinaDia(List<RutinaDia> rutinaDia) {
        this.rutinaDia = rutinaDia;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.Nombre);
        dest.writeString(this.Descripción);
        dest.writeString(this.publica);
        dest.writeTypedList(this.rutinaDia);
        dest.writeString(this.imageURL);
    }

    protected RutinaSemanal(Parcel in) {
        this.id = in.readString();
        this.Nombre = in.readString();
        this.Descripción = in.readString();
        this.publica = in.readString();
        this.rutinaDia = in.createTypedArrayList(RutinaDia.CREATOR);
        this.imageURL = in.readString();
    }

    public static final Creator<RutinaSemanal> CREATOR = new Creator<RutinaSemanal>() {
        @Override
        public RutinaSemanal createFromParcel(Parcel source) {
            return new RutinaSemanal(source);
        }

        @Override
        public RutinaSemanal[] newArray(int size) {
            return new RutinaSemanal[size];
        }
    };
}
