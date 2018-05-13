package com.erikbustosm.fiteso2.Beans;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Erik Bustos M on 04/04/2018.
 */

public class Rutina implements Parcelable {
    private String id;
    private String idDetalles;
    private String nombre;
    private String categoria;
    private String descripcion;
    private String publico;
    private Ejercicio[] ejercicios;
    private DetallesEjercicio[] detallesEjercicios;
    private String imageURL;

    public Rutina(String id, String nombre, String categoria, String descripcion, String publico, String imageURL, Ejercicio[] ejercicios, DetallesEjercicio[] detallesEjercicios) {
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.descripcion = descripcion;
        this.publico = publico;
        this.ejercicios = ejercicios;
        this.detallesEjercicios = detallesEjercicios;
        this.imageURL= imageURL;
    }

    public String getIdDetalles() {
        return idDetalles;
    }

    public void setIdDetalles(String idDetalles) {
        this.idDetalles = idDetalles;
    }

    public Rutina(String id, String nombre, String categoria, String descripcion, String publico, String imageURL) {
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.descripcion = descripcion;
        this.publico = publico;
        this.imageURL = imageURL;
    }

    public Rutina() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPublico() {
        return publico;
    }

    public void setPublico(String publico) {
        this.publico = publico;
    }

    public Ejercicio[] getEjercicios() {
        return ejercicios;
    }

    public void setEjercicios(Ejercicio[] ejercicios) {
        this.ejercicios = ejercicios;
    }

    public DetallesEjercicio[] getDetallesEjercicios() {
        return detallesEjercicios;
    }

    public void setDetallesEjercicios(DetallesEjercicio[] detallesEjercicios) {
        this.detallesEjercicios = detallesEjercicios;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.idDetalles);
        dest.writeString(this.nombre);
        dest.writeString(this.categoria);
        dest.writeString(this.descripcion);
        dest.writeString(this.publico);
        dest.writeTypedArray(this.ejercicios, flags);
        dest.writeTypedArray(this.detallesEjercicios, flags);
        dest.writeString(this.imageURL);
    }

    protected Rutina(Parcel in) {
        this.id = in.readString();
        this.idDetalles = in.readString();
        this.nombre = in.readString();
        this.categoria = in.readString();
        this.descripcion = in.readString();
        this.publico = in.readString();
        this.ejercicios = in.createTypedArray(Ejercicio.CREATOR);
        this.detallesEjercicios = in.createTypedArray(DetallesEjercicio.CREATOR);
        this.imageURL = in.readString();
    }

    public static final Creator<Rutina> CREATOR = new Creator<Rutina>() {
        @Override
        public Rutina createFromParcel(Parcel source) {
            return new Rutina(source);
        }

        @Override
        public Rutina[] newArray(int size) {
            return new Rutina[size];
        }
    };
}
