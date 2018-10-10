package com.example.ik_2dm3.spotifygrupo2;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import com.google.android.gms.ads.AdListener;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;


public class MainActivity extends AppCompatActivity {
    //private AdView mAdView;

    private ArrayList<Song> songList;
    private String[] titulo;
    private String[] genero;
    private String[] artista;
    private String[] artistas_canciones;
    private String[] canciones;
    private ListView songView;
    private ListView generoView;
    private ListView dentrogeneroView;
    private ListView artistaView;
    private ListView dentroartistaView;
    private Boolean isEstaEnArray;

    ImageButton botonplay, botonsig, botonprev, botonrepr, shuffle;

    MediaPlayer mp;
    Context cont = this;
    int i = 0;
    int pag = 1;
    int modorepr = 0;



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    ListarCanciones();
                    return true;
                case R.id.navigation_dashboard:
                    isEstaEnArray = false;
                    ListarGeneros();
                    return true;
                case R.id.navigation_notifications:
                    isEstaEnArray = false;
                    ListarArtistas();
                    return true;

            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        songView = (ListView)findViewById(R.id.song_list);
        generoView = (ListView)findViewById(R.id.genero_lista);
        artistaView = (ListView)findViewById(R.id.artista_lista);
        dentrogeneroView = (ListView)findViewById(R.id.dentrogenero_lista);
        dentroartistaView = (ListView)findViewById(R.id.dentrogenero_lista);

        botonplay = (ImageButton) findViewById(R.id.botonplay);
        botonprev = (ImageButton) findViewById(R.id.botonprev);
        botonsig = (ImageButton) findViewById(R.id.botonsig);
        botonrepr = (ImageButton) findViewById(R.id.botonrepr);

        songList = new ArrayList<Song>();

        Song s1 = new Song (R.raw.s1,"Cancion 1","ccc", "Blues");
        Song s2 = new Song (R.raw.s2,"Cancion 2","bbb", "Jazz");
        Song s3 = new Song (R.raw.s3,"Cancion 3","bbb", "Rap");
        Song s4 = new Song (R.raw.s4,"Cancion 4","aaa", "Blues");
        Song s5 = new Song (R.raw.s5,"Cancion 5","aaa", "Blues");

        songList.add(s1);
        songList.add(s2);
        songList.add(s3);
        songList.add(s4);
        songList.add(s5);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        isEstaEnArray = false;

        ListarCanciones();

        //ACCIONES AL ACABAR CANCION//
        mp = MediaPlayer.create(this, songList.get(0).getID());
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                i++;
                if(modorepr==2) {
                    i--;
                }

                Log.e("myTag", "Se ha sumado 1 y i es " + i);

                if (i == songList.size()) {
                    i=0;
                    Log.e("myTag", "Se ha llegado al final del array y la i es " + i);
                    return;
                }

                if(modorepr==1){
                    int rn = 0;
                    rn = (int)(Math.random() * ((songList.size() - 0))) + 0;
                    i = rn;
                }

                AssetFileDescriptor afd = getBaseContext().getResources().openRawResourceFd(songList.get(i).getID());

                try {
                    mp.reset();
                    mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getDeclaredLength());
                    mp.prepare();
                    mp.start();
                    afd.close();
                }
                catch (IllegalArgumentException e) {
                    Log.e("myTag", "IllegalArgumentException Unable to play audio : " + e.getMessage());
                }
                catch (IllegalStateException e) {
                    Log.e("myTag", "IllegalStateException Unable to play audio : " + e.getMessage());
                }
                catch (IOException e) {
                    Log.e("myTag", "IOException Unable to play audio : " + e.getMessage());
                }

            }

        });

        //MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mAdView.setVisibility(View.VISIBLE);

        /*mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when when the user is about to return
                // to the app after tapping on an ad.
            }
        });*/

    }

    public void ListarCanciones(){
        OcultarMostrarListView(1);

        //genero.clear();
        titulo = new String [songList.size()];

        for (int i = 0; i<titulo.length; i++){
            titulo[i] = songList.get(i).getTitle();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, titulo);
        songView.setAdapter(adapter);

        songView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                int item = position;
                String itemval = (String)songView.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(), "Position: "+ item+" - Valor: "+itemval, Toast.LENGTH_LONG).show();

                //Intent i = startActivity();
            }

        });
    }

    public void ListarGeneros(){
        OcultarMostrarListView(2);

        ArrayList<String> Gen;
        Gen = new ArrayList<String>();

        Gen.add(songList.get(0).getGenero());

       for (int i = 0; i<songList.size();i++)
        {
            for (int e = 0; e<Gen.size();e++) {
                if (songList.get(i).getGenero() == Gen.get(e)) {
                    isEstaEnArray = true;
                    break;
                }
                else
                {
                    isEstaEnArray = false;
                    //Gen.add(songList.get(i).getGenero());
                    //break;
                }
            }
            if (!isEstaEnArray){
                Gen.add(songList.get(i).getGenero());
            }
        }

        genero = new String[Gen.size()];
        for (int i = 0; i<Gen.size(); i++){
            genero[i] = Gen.get(i);
        }
        Arrays.sort(genero);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, genero);
        generoView.setAdapter(adapter);

        generoView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                OcultarMostrarListView(0);
                dentrogeneroView.setVisibility(View.VISIBLE);
                int item = position;
                String itemval = (String)generoView.getItemAtPosition(position);
                //Toast.makeText(getApplicationContext(), "Position: "+ item+" - Valor: "+itemval, Toast.LENGTH_LONG).show();

                ArrayList<String> Detallegenero;
                Detallegenero = new ArrayList<String>();
                //Detallegenero.add(songList.get(0).getArtist());

                for (int i = 0; i<songList.size();i++)
                {
                    if(songList.get(i).getGenero().equals(itemval)){
                        Detallegenero.add(songList.get(i).getTitle());
                    }
                }

                canciones = new String[Detallegenero.size()];
                for (int i = 0; i<Detallegenero.size(); i++){
                    canciones[i] = Detallegenero.get(i);
                }
                Arrays.sort(canciones);

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(cont, android.R.layout.simple_list_item_1, canciones);
                dentrogeneroView.setAdapter(adapter);

                dentrogeneroView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                        int item = position;
                        String itemval = (String)dentrogeneroView.getItemAtPosition(position);
                        Toast.makeText(getApplicationContext(), "Position: "+ item+" - Valor: "+itemval, Toast.LENGTH_LONG).show();

                        mp.start();
                        //reproducir(cont, item);
                    }

                });

               //reproducir(cont, item);
            }

        });
    }

    public void ListarArtistas(){
        OcultarMostrarListView(3);
        //String gen = songList.get(0).getGenero();
        //int cont = 0;

        ArrayList<String> Art;
        Art = new ArrayList<String>();
        Art.add(songList.get(0).getArtist());

        for (int i = 0; i<songList.size();i++)
        {
            for (int e = 0; e<Art.size();e++) {
                if (songList.get(i).getArtist() == Art.get(e)) {
                    isEstaEnArray = true;
                    break;
                }
                else
                {
                    isEstaEnArray = false;
                    //Art.add(songList.get(i).getArtist());
                    //break;
                }
            }
            if (!isEstaEnArray){
                Art.add(songList.get(i).getArtist());
            }
        }

        artista = new String[Art.size()];
        for (int i = 0; i<Art.size(); i++){
            artista[i] = Art.get(i);
        }
        Arrays.sort(artista);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, artista);
        artistaView.setAdapter(adapter);

        artistaView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                OcultarMostrarListView(0);
                dentroartistaView.setVisibility(View.VISIBLE);
                int item = position;
                String itemval = (String)artistaView.getItemAtPosition(position);
                //Toast.makeText(getApplicationContext(), "Position: "+ item+" - Valor: "+itemval, Toast.LENGTH_LONG).show();

                ArrayList<String> Detalleartista;
                Detalleartista = new ArrayList<String>();
                //Detallegenero.add(songList.get(0).getArtist());

                for (int i = 0; i<songList.size();i++)
                {
                    if(songList.get(i).getArtist().equals(itemval)){
                        Detalleartista.add(songList.get(i).getTitle());
                    }
                }

                artistas_canciones = new String[Detalleartista.size()];
                for (int i = 0; i<Detalleartista.size(); i++){
                    artistas_canciones[i] = Detalleartista.get(i);
                }
                Arrays.sort(artistas_canciones);

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(cont, android.R.layout.simple_list_item_1, artistas_canciones);
                dentroartistaView.setAdapter(adapter);

                dentroartistaView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                        int item = position;
                        String itemval = (String)dentroartistaView.getItemAtPosition(position);
                        Toast.makeText(getApplicationContext(), "Position: "+ item+" - Valor: "+itemval, Toast.LENGTH_LONG).show();

                        //reproducir(cont, item);
                    }

                });
            }

        });
    }

    public void OcultarMostrarListView (int i){
        songView.setVisibility(View.INVISIBLE);
        generoView.setVisibility(View.INVISIBLE);
        artistaView.setVisibility(View.INVISIBLE);
        dentrogeneroView.setVisibility(View.INVISIBLE);
        dentroartistaView.setVisibility(View.INVISIBLE);

        switch (i){
            case 0:
                songView.setVisibility(View.INVISIBLE);
                generoView.setVisibility(View.INVISIBLE);
                artistaView.setVisibility(View.INVISIBLE);
                dentrogeneroView.setVisibility(View.INVISIBLE);
                dentroartistaView.setVisibility(View.INVISIBLE);
                break;
            case (1):
                songView.setVisibility(View.VISIBLE);
                break;
            case (2):
                generoView.setVisibility(View.VISIBLE);
                break;
            case (3):
                artistaView.setVisibility(View.VISIBLE);
                break;
        }
    }

    /*public void reproducir (Context cont, int pos){
        mp = MediaPlayer.create(cont, songList.get(pos).getID());
        mp.start();
    }*/

    //ACCIONES DE REPRODUCCION//

    public void PlayPause(View v){


        AssetFileDescriptor afd = getBaseContext().getResources().openRawResourceFd(songList.get(0).getID());

        if (mp.isPlaying()) {

            mp.pause();
            Log.e("myTag", "Has pausado la cancion en el " + i);

        } else {

            try {
                mp.reset();
                mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getDeclaredLength());
                mp.prepare();
                mp.start();
                afd.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void NextSong(View v){

        i++;
        Log.e("myTag", "Has saltado a la cancion" + i);

        if(modorepr==1){
            int rn = 0;
            rn = (int)(Math.random() * ((songList.size() - 0))) + 0;
            i = rn;
        }

        //Si llega al final, vuelve al principio
        if (i == songList.size()) {
            i=0;
            Log.e("myTag", "Se ha llegado al final del array y la i es " + i);
            return;
        }

        AssetFileDescriptor afd = getBaseContext().getResources().openRawResourceFd(songList.get(i).getID());

        try {
            mp.reset();
            mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getDeclaredLength());
            mp.prepare();
            mp.start();
            afd.close();
        }
        catch (IOException ex) {
            Log.e("myTag", "IllegalArgumentException Unable to play audio : " + ex.getMessage());
        }

    }

    public void PrevSong(View v) {
        if(i==0){
            Toast.makeText(getApplicationContext(), "Estas en la primera cancion, no se puede ir atras.", Toast.LENGTH_LONG).show();
        }
        else{
            i--;
            Log.e("myTag", "Has ido atras a la cancion" + i);

            AssetFileDescriptor afd = getBaseContext().getResources().openRawResourceFd(songList.get(i).getID());

            try {
                mp.reset();
                mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getDeclaredLength());
                mp.prepare();
                mp.start();
                afd.close();
            } catch (IOException ex) {
                Log.e("myTag", "IllegalArgumentException Unable to play audio : " + ex.getMessage());
            }
        }
    }

    public void ModoRepr(View v){
        botonrepr = (ImageButton) findViewById(R.id.botonrepr);
        modorepr++;
        if(modorepr == 1){
            botonrepr.setImageResource(R.drawable.shuffle);
        }
        if(modorepr == 2){
            botonrepr.setImageResource(R.drawable.loop);
        }
        if(modorepr == 3){
            modorepr = 0;
            botonrepr.setImageResource(R.drawable.playlist);
        }

    }

    //Funcion para que cargue la primera pagina bien cuando le das atras.
    @Override
    public void onBackPressed() {

        if (pag == 2) {
            pag = 1;
            setContentView(R.layout.activity_main);

            songView = (ListView)findViewById(R.id.song_list);
            generoView = (ListView)findViewById(R.id.genero_lista);
            artistaView = (ListView)findViewById(R.id.artista_lista);
            botonplay = (ImageButton) findViewById(R.id.botonplay);
            botonprev = (ImageButton) findViewById(R.id.botonprev);
            botonsig = (ImageButton) findViewById(R.id.botonsig);
            dentrogeneroView = (ListView)findViewById(R.id.dentrogenero_lista);
            dentroartistaView = (ListView)findViewById(R.id.dentrogenero_lista);


            songList = new ArrayList<Song>();

            Song s1 = new Song (R.raw.s1,"Cancion 1","ccc", "Blues");
            Song s2 = new Song (R.raw.s2,"Cancion 2","bbb", "Jazz");
            Song s3 = new Song (R.raw.s3,"Cancion 3","bbb", "Rap");
            Song s4 = new Song (R.raw.s4,"Cancion 4","aaa", "Blues");
            Song s5 = new Song (R.raw.s5,"Cancion 5","aaa", "Blues");

            songList.add(s1);
            songList.add(s2);
            songList.add(s3);
            songList.add(s4);
            songList.add(s5);

            BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
            navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

            isEstaEnArray = false;

            ListarCanciones();


        } else {
            super.onBackPressed();
        }
    }

}

