package org.jboss.tools.examples.model;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PhotoAlbumList implements Serializable {
    private static final long serialVersionUID = -6547391197128734913L;
    private String name;
    private String beschreibung;
    private List<PhotoAlbum> photoAlbums;

    public PhotoAlbumList() {
        photoAlbums = new ArrayList<PhotoAlbum>();
    }

    public long getCount() {
        if (photoAlbums != null) {
            return photoAlbums.size();
        } else {
            return 0;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public List<PhotoAlbum> getPhotoAlbums() {
        return photoAlbums;
    }

    public void setPhotoAlbums(List<PhotoAlbum> photoAlbums) {
        this.photoAlbums = photoAlbums;
    }
}
