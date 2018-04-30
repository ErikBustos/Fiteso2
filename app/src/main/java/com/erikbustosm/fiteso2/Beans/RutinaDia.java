package com.erikbustosm.fiteso2.Beans;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class RutinaDia implements Parcelable {
    private String id;
    private List<String> dias;

    private List<DetallesEjercicio> detallesEjercicio;

    public RutinaDia(String id, List<String> dias, List<DetallesEjercicio> detallesEjercicio) {
        this.id = id;
        this.dias = dias;
        this.detallesEjercicio = detallesEjercicio;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getDias() {
        return dias;
    }

    public void setDias(List<String> dias) {
        this.dias = dias;
    }

    public List<DetallesEjercicio> getDetallesEjercicio() {
        return detallesEjercicio;
    }

    public void setDetallesEjercicio(List<DetallesEjercicio> detallesEjercicio) {
        this.detallesEjercicio = detallesEjercicio;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeStringList(this.dias);
        dest.writeTypedList(this.detallesEjercicio);
    }

    protected RutinaDia(Parcel in) {
        this.id = in.readString();
        this.dias = in.createStringArrayList();
        this.detallesEjercicio = in.createTypedArrayList(DetallesEjercicio.CREATOR);
    }

    public static final Creator<RutinaDia> CREATOR = new Creator<RutinaDia>() {
        @Override
        public RutinaDia createFromParcel(Parcel source) {
            return new RutinaDia(source);
        }

        @Override
        public RutinaDia[] newArray(int size) {
            return new RutinaDia[size];
        }
    };
}
