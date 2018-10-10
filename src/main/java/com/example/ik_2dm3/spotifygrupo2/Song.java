package com.example.ik_2dm3.spotifygrupo2;

import android.view.View;

import java.util.ArrayList;

public class Song  {
    private int id;
    private String title;
    private String artist;
    private String genero;

    public Song(int songID, String songTitle, String songArtist, String songGenero) {
        id = songID;
        title = songTitle;
        artist = songArtist;
        genero = songGenero;

    }

    public int getID(){return id;}
    public String getTitle(){return title;}
    public String getArtist(){return artist;}
    public String getGenero(){return genero;}


}
