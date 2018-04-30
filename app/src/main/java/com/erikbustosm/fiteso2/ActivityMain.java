package com.erikbustosm.fiteso2;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.erikbustosm.fiteso2.Beans.DetallesEjercicio;
import com.erikbustosm.fiteso2.Beans.Ejercicio;
import com.erikbustosm.fiteso2.Beans.RutinaDia;
import com.erikbustosm.fiteso2.Beans.RutinaSemanal;
import com.erikbustosm.fiteso2.Tools.Constants;
import com.erikbustosm.fiteso2.Tools.FragmentExplorar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.LinkedList;
import java.util.List;

public class ActivityMain extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private String drawerTitle;
    DatabaseReference firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        firebaseDatabase= FirebaseDatabase.getInstance().getReference();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);




        drawerTitle= "Profile";
        loadprofilefragment(drawerTitle);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_perfil) {

            Fragment fragment = FragmentProfile.newInstance();


            FragmentTransaction transaction= getFragmentManager().beginTransaction();
            transaction.replace(R.id.main_content,fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        } else if (id == R.id.nav_rutina) {
           // Intent intent2 = new Intent(ActivityProfile.this,
             //       ActivityRutina.class);
            //startActivity(intent2);

        } else if (id == R.id.nav_crear_rutina) {

        } else if (id == R.id.nav_explorar) {
            android.support.v4.app.Fragment explorar= new FragmentExplorar();
            android.support.v4.app.FragmentTransaction transaction= getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.main_content,explorar);
            transaction.addToBackStack(null);
            transaction.commit();
        } else if (id == R.id.nav_configuracion) {
            Fragment config= new FragmentConfiguracion();
            FragmentTransaction transaction= getFragmentManager().beginTransaction();
            transaction.replace(R.id.main_content,config);
            transaction.addToBackStack(null);
            transaction.commit();
            //Intent intent = new Intent(ActivityProfile.this, ActivityConfiguracion.class);
            //startActivity(intent);

        } else if (id == R.id.nav_cerrar_sesion) {
            logout();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void logout(){
        FirebaseAuth.getInstance().signOut();
        finish();
        //startActivity(new Intent(this,ActivityLogin.class));
    }

    public void  loadprofilefragment(String title){
        Bundle args= new Bundle();
        args.putString(FragmentProfile.ARG_SECTION_TITLE, title);
        Fragment fragment = FragmentProfile.newInstance();
        fragment.setArguments(args);

        FragmentTransaction transaction= getFragmentManager().beginTransaction();
        transaction.replace(R.id.main_content,fragment);
        transaction.addToBackStack(null);
        transaction.commit();
        /*
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager
                .beginTransaction()
                .replace(R.id.main_content, fragment)
                .commit();

        drawer.closeDrawers(); // Cerrar drawer*/

    }
    public void rutinaejemplo(){


        List<DetallesEjercicio> detallesEjercicios= new LinkedList<>();
        Ejercicio ejercicios[]=new Ejercicio[4];

        String idDetalleEjercicio1= firebaseDatabase.child("DetallesEjercicio").push().getKey();
        String idEjercicio1= firebaseDatabase.child("Ejercicio").push().getKey();

        detallesEjercicios.add(new DetallesEjercicio(idEjercicio1,15,3,50));
        ejercicios[0]= new Ejercicio(idEjercicio1,"Sentadilla","La sentadilla o squat es un movimiento que se inicia de pie, mirando al frente y con la espalda recta, mientras los pies se separan del ancho de los hombros.\n" +
                "La barra utilizada debe situarse justo encima de los trapecios, no debe apoyarse en el cuello.\n" +
                "Siempre mirando al frente y sin curvar la espalda, debemos descender los glúteos flexionando la rodilla y la cadera,  y cuidando que la rodilla no pase de la punta del pie ni sobrepase los 90 grados de flexión. Descendemos hasta que los muslos quedan paralelos al suelo y desde allí debemos elevarnos lentamente mientras exhalamos el aire inhalado al comenzar el descenso del cuerpo.\n" +
                "Si los muslos no llegan a estar paralelos al piso estaremos realizando una media sentadilla, mientras que si realizamos una flexión de rodilla que coloca los muslos paralelos al suelo se denominará sentadilla completa."
                ,"Pierna","https://i.blogs.es/93405c/sentadilla/1024_2000.jpg");

        String idDetalleEjercicio2= firebaseDatabase.child("DetallesEjercicio").push().getKey();
        String idEjercicio2= firebaseDatabase.child("Ejercicio").push().getKey();

        detallesEjercicios.add(new DetallesEjercicio(idEjercicio2,10,4,60));
        ejercicios[1]= new Ejercicio(idEjercicio2,"Lagartija","Las lagartijas te permiten fortalecer los hombros (deltoides), pectorales y los músculos serratos que son los que están a los costados del pecho, debajo de los brazos." +
                "  Se realizan boca abajo, apoyando las palmas de las manos separadas a una distancia entre sí similar al ancho de los hombros. Manteniendo la espalda recta y los pies juntos, " +
                "debes bajar todo el cuerpo valièndote de la fuerza de tus brazos.", Constants.PECHO,
                "https://www.musculaciontotal.com/wp-content/uploads/2015/09/Sentadillas-con-mancuernas-para-hombres.jpg");

        String idDetalleEjercicio3= firebaseDatabase.child("DetallesEjercicio").push().getKey();
        String idEjercicio3= firebaseDatabase.child("Ejercicio").push().getKey();

        detallesEjercicios.add(new DetallesEjercicio(idEjercicio3,8,4,45));
        ejercicios[2]= new Ejercicio(idEjercicio3,"Curl Biceps con Barra", "Para comenzar el movimiento debemos comenzar de pie, con la espalda recta, las rodillas ligeramente flexionadas y los pies separados del ancho de los hombros.\n" +
                "\n" +
                "Tomaremos una barra lisa con las manos, de manera que las palmas miren hacia arriba, es decir, con agarre en supinación. Las manos deben estar separadas en la barra un poco más allá de la anchura de los hombros.\n" +
                "\n" +
                "Desde esta posición, sin movilizar el tronco y manteniendo los codos a los lados del cuerpo, debemos inspirar y flexionar los codos mientras acercamos la barra al pecho y contraemos glúteos, abdominales y espinales para no mover el tronco.\n" +
                "\n" +
                "Espiramos al final del movimiento y descendemos lentamente la barra hasta la posición inicial.", Constants.BICEP,"https://viviendosanos.com/wp-content/uploads/2016/05/los-mejores-ejercicios-para-biceps-curl-barra.jpg" );

        String idDetalleEjercicio4= firebaseDatabase.child("DetallesEjercicio").push().getKey();
        String idEjercicio4= firebaseDatabase.child("Ejercicio").push().getKey();
        detallesEjercicios.add(new DetallesEjercicio(idEjercicio4,10,5, 60));
        ejercicios[3]= new Ejercicio(idEjercicio4,"Elevaciones Laterales","Para comenzar el ejercicio debemos colocarnos de pie, con las piernas ligeramente flexionadas y separadas del ancho de la cadera. La espalda debe permanecer recta y en cada mano debemos sujetar una mancuerna, mientras los brazos permanecen a los lados del cuerpo o delante de los muslos, levemente flexionados.\n" +
                "\n" +
                "Tomando aire elevamos las mancuernas hasta que los brazos queden alineados con los hombros y desde allí bajamos lentamente mientras exhalamos",Constants.HOMBRO,"https://uploads-cdn.thgblogs.com/wp-content/uploads/sites/450/2016/03/09031139/elevaciones-laterales-hombros.jpg");


        String idrutinaDía1= firebaseDatabase.child("RutinaDia").push().getKey();
        Log.e("tag",idrutinaDía1);


        List<String> dia1= new LinkedList<>();
        dia1.add(Constants.LUNES);
        dia1.add(Constants.MARTES);


        // String idEjercicios[]={idEjercicio1,idEjercicio2,idEjercicio3,idEjercicio4};
        List<String> idEjercicios= new LinkedList<>();
        idEjercicios.add(idEjercicio1);
        idEjercicios.add(idEjercicio2);
        idEjercicios.add(idEjercicio3);
        idEjercicios.add(idEjercicio4);
        RutinaDia rutinaDia1=new RutinaDia(idrutinaDía1, dia1 , detallesEjercicios);





        List<DetallesEjercicio> detallesEjerciciosdia2 =new LinkedList<>();
        Ejercicio ejerciciosdia2[]=new Ejercicio[4];

        String idDetalleEjerciciodia2_1= firebaseDatabase.child("DetallesEjercicio").push().getKey();
        String idEjerciciodia2_1= firebaseDatabase.child("Ejercicio").push().getKey();

        detallesEjerciciosdia2.add(new DetallesEjercicio(idEjerciciodia2_1,15,3,50));
        ejerciciosdia2[0]= new Ejercicio(idEjerciciodia2_1,"Sentadilla","La sentadilla o squat es un movimiento que se inicia de pie, mirando al frente y con la espalda recta, mientras los pies se separan del ancho de los hombros.\n" +
                "La barra utilizada debe situarse justo encima de los trapecios, no debe apoyarse en el cuello.\n" +
                "Siempre mirando al frente y sin curvar la espalda, debemos descender los glúteos flexionando la rodilla y la cadera,  y cuidando que la rodilla no pase de la punta del pie ni sobrepase los 90 grados de flexión. Descendemos hasta que los muslos quedan paralelos al suelo y desde allí debemos elevarnos lentamente mientras exhalamos el aire inhalado al comenzar el descenso del cuerpo.\n" +
                "Si los muslos no llegan a estar paralelos al piso estaremos realizando una media sentadilla, mientras que si realizamos una flexión de rodilla que coloca los muslos paralelos al suelo se denominará sentadilla completa."
                ,Constants.PIERNA,"https://i.blogs.es/93405c/sentadilla/1024_2000.jpg");

        String idDetalleEjerciciodia2_2= firebaseDatabase.child("DetallesEjercicio").push().getKey();
        String idEjerciciodia2_2= firebaseDatabase.child("Ejercicio").push().getKey();

        detallesEjerciciosdia2.add(new DetallesEjercicio(idEjerciciodia2_2,15,3,50));
        ejerciciosdia2[1]= new Ejercicio(idEjerciciodia2_2,"Elevaciones gemelos","para realizar el movimiento debemos comenzar de pie, con los pies separados aproximadamente de la anchura de los hombros y las rodillas ligeramente flexionadas debemos despegar los talones del suelo mientras realizamos una extensión de los pies para elevar el cuerpo mientras éste queda sostenido por la punta del pie.\n" +
                "\n" +
                "Es importante que la espalda esté bien recta y que el cuerpo se eleve por la flexión plantar o la elevación de los talones. Podemos realizar el movimiento con una barra sostenida sobre el torso, con mancuernas tomadas en ambas manos y/o sobre un banco, en el cual sólo apoyaremos los pies por sus puntas y tendremos la dificultad extra de que los talones realizan un recorrido mayor durante el ejercicio.",
                Constants.PIERNA,"data:image/jpeg;base64,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");


        String idDetalleEjerciciodia2_3= firebaseDatabase.child("DetallesEjercicio").push().getKey();
        String idEjerciciodia2_3= firebaseDatabase.child("Ejercicio").push().getKey();

        detallesEjerciciosdia2.add(new DetallesEjercicio(idEjerciciodia2_3,8,4,40));
        ejerciciosdia2[2]= new Ejercicio(idEjerciciodia2_3,"Dominadas","Para comenzar a hacer una dominada correctamente debes iniciar el movimiento colgándote de la barra de dominadas con ambas manos usando un banco y no saltando y colgándote ya que seguramente no te agarrarás como debes. Separa los brazos algo más de la anchura de tus hombros, aunque para mejorar y trabajar diferentes ángulos de tus dorsales y trapecios puedes variar la separación entre cada mano."
                +"\n"+"Inspira profundamente y aguanta la respiración mientras contraes los dorsales para elevarte hacia arriba lentamente, con los codos a los lados y hacia afuera, sin ayudarte con las piernas subiéndolas, así que mantenlas quietas todo el rato. Debes hacer la subida más rápido que la bajada e intenta hacer una pequeña parada en la posición más alta, que es cuando la barbilla sobrepasa la barra. No es una dominada completa si no la superas, así que debes realizar el movimiento completo para contarla como tal y sin echar la cabeza hacia atrás para que la barbilla pase antes."
                ,Constants.ESPALDA,"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTo4sPwKFngfLFA5hH-D86ZC9E181SGjwSH4V42thSbiKJrOE9PJw");

        String idDetalleEjerciciodia2_4= firebaseDatabase.child("DetallesEjercicio").push().getKey();
        String idEjerciciodia2_4= firebaseDatabase.child("Ejercicio").push().getKey();

        detallesEjerciciosdia2.add(new DetallesEjercicio(idEjerciciodia2_4,8,3,70));
        ejerciciosdia2[3]= new Ejercicio(idEjerciciodia2_4,"Press Militar","Este ejercicio se puede realizar de pie o sentado, para lo cual debemos flexionar levemente las rodillas y cuidar mucho la espalda, para no curvar demasiado la columna lumbar." +
                "\nInspiramos y elevamos la barra hacia arriba como si la estuviéramos empujando con las manos mientras los brazos se extienden, espiramos al final del movimiento y comenzamos el descenso hacia la posición inicial de manera controlada."
                ,Constants.HOMBRO,"https://i.pinimg.com/originals/2a/fe/9e/2afe9efc50a146939c5b8f6910ce52c8.jpg");

        String idrutinaDía2= firebaseDatabase.child("RutinaDia").push().getKey();
        List<String> dias2= new LinkedList<>();
        dias2.add(Constants.MARTES);
        dias2.add(Constants.VIERNES);

        List<String> idejeciciosdia2=new LinkedList<>();
        idejeciciosdia2.add(idDetalleEjerciciodia2_1);
        idejeciciosdia2.add(idDetalleEjerciciodia2_2);
        idejeciciosdia2.add(idDetalleEjerciciodia2_3);
        idejeciciosdia2.add(idDetalleEjerciciodia2_4);
        RutinaDia rutinaDia2=new RutinaDia(idrutinaDía2, dias2 ,detallesEjerciciosdia2);

        List<RutinaDia> rutinasdia=new LinkedList<>();
        rutinasdia.add(rutinaDia1);
        rutinasdia.add(rutinaDia2);

        String idRutinaSemanal=  firebaseDatabase.child("RutinaSemanal").push().getKey();
        RutinaSemanal rutinaSemanal=new RutinaSemanal(idRutinaSemanal,"Rutina de volumen de activación","Las rutinas para ganar masa muscular y volumen no se tienen porque limitar a entrenar en el gimnasio. Si dispones del equipo adecuado puedes realizar una rutina completa y aumentar tu masa muscular sin salir de tu propia casa, en tu ambiente, con tu música, etc.\n" +
                "\n" +
                "Este es un entrenamiento intenso, pocas series y un peso medio-alto con objetivo para fuerza-hipertrofia. El objetivo es intentar subir en fuerza poco a poco con una cantidad moderada de entrenamiento y que el aumento de fuerza nos traiga también el de nuestro volumen muscular."
                ,Constants.PUBLICA,rutinasdia,"https://www.cambiatufisico.com/wp-content/uploads/entrenamiento-volumen-casa-900x506.jpg");



        for(int i=0;i<ejercicios.length;i++)
            firebaseDatabase.child("Ejercicio").child(ejercicios[i].getId()).setValue(ejercicios[i]);
        for(int i=0;i<ejerciciosdia2.length;i++)
            firebaseDatabase.child("Ejercicio").child(ejerciciosdia2[i].getId()).setValue(ejerciciosdia2[i]);
        firebaseDatabase.child("RutinaSemanal").child(rutinaSemanal.getId()).setValue(rutinaSemanal);


    }
}
