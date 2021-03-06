package org.jboss.tools.examples.model;

import java.io.Serializable;
import java.util.HashMap;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.ws.rs.DefaultValue;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * This class represents a PhotoAlbum
 * 
 * @author daniel rhein
 * 
 */
// TODO: Photoalbum testen; Name des Photoalbums festlegen.
@Entity
@XmlRootElement
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class PhotoAlbum implements Serializable, Comparable<PhotoAlbum> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String ERROR_NO_STICKER = "The Sticker can't be null.";
	private static final String ERROR_NO_PHOTO = "Das Photo can't be null.";
	
	
	@NotNull
	@Size(min = 2, max = 255)
	@NotEmpty
	private String name;
	
	@NotNull
	@Size(min = 2, max = 255)
	@NotEmpty
	private String beschreibung;
	
	@DefaultValue(value="0")
	private int anzahl;
	@DefaultValue(value="0")
	private int dateigroesse =0;
	
	@Id
	@GeneratedValue
	private int id;
	
	private HashMap<Integer, PhotoAlbumEntry> hashPhotoAlbumEntry;
	
	
	
	public PhotoAlbum() {
		
	}

	/**
	 * Constructor
	 */
	
	public PhotoAlbum(String name) throws IllegalArgumentException {
		hashPhotoAlbumEntry = new HashMap<Integer, PhotoAlbumEntry>();
		this.name = name;
	}

	/**
	 * Get the name of the photo album
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the photo album.
	 * 
	 * @param name
	 * @throws IllegalArgumentException
	 */
	public void setName(String name) throws IllegalArgumentException {
		this.name = name;
	}
	
	/**
	 * Get the description of the photo album
	 * 
	 * @return
	 */
    public String getBeschreibung() {
		return beschreibung;
	}
    
    /**
	 * Sets the description of the photo album.
	 * 
	 * @param name
	 * @throws IllegalArgumentException
	 */
     public void setBeschreibung(String beschreibung) throws IllegalArgumentException {
		this.beschreibung = beschreibung;
	}
     
     /**
 	 * Get the id of the photo album
 	 * 
 	 * @return
 	 */
     public int getId() {
 		return id;
 	}
 	
 	/**
 	 * Sets the id of the photo album.
 	 * 
 	 * @param name
 	 * @throws IllegalArgumentException
 	 */
     public void setId(int id) throws IllegalArgumentException {
 		this.id = id;
 	}
	
	/**
	 * Get the number of the photo album
	 * 
	 * @return
	 */
     public int getAnzahl() {
		return anzahl;
	}
     
     /**
 	 * Sets the number of the photo album.
 	 * 
 	 * @param name
 	 * @throws IllegalArgumentException
 	 */
     public void setAnzahl(int anzahl) throws IllegalArgumentException {
		this.anzahl = anzahl;
	}
	
	/**
	 * Get the size of the photo album
	 * 
	 * @return
	 */
    public int getDateigroesse() {
		return dateigroesse;
	}
    
    /**
	 * Sets the size of the photo album.
	 * 
	 * @param name
	 * @throws IllegalArgumentException
	 */
    public void setDateigroese(int dateigroesse) throws IllegalArgumentException {
		this.dateigroesse = dateigroesse;
	}
	
	/*
	 * Get the Entry
	 * @return entry, null if there's no entry
	 */
	public PhotoAlbumEntry getEntry(int id) {
		if (hashPhotoAlbumEntry.containsKey(id)) {
			return hashPhotoAlbumEntry.get(id);
		}
		return null;
	}

	/**
	 * Determine the entry of an Photoalbum
	 * 
	 * @param entry
	 * @param id
	 */
	public void setEntry(PhotoAlbumEntry entry, int id)
			throws IllegalArgumentException {
		hashPhotoAlbumEntry.put(id, entry);
	}

	/**
	 * Add an Entry to the Photoalbum
	 * 
	 * @param entry
	 * @param id
	 * @throws IllegalArgumentException
	 */
	public void addEntry(PhotoAlbumEntry entry, int id)
			throws IllegalArgumentException {
		hashPhotoAlbumEntry.put(id, entry);
	}

	/**
	 * Removes an Entry from the Photoalbum
	 * 
	 * @param id
	 * @throws IllegalArgumentException
	 */
	public void removeEntry(int id) throws IllegalArgumentException {
		hashPhotoAlbumEntry.remove(id);
	}

	/**
	 * Counts the Entries
	 * 
	 * @return AlbumEntry size
	 */
	public int getCountEntries() {
		return hashPhotoAlbumEntry.size();
	}
	
	public int compareTo(PhotoAlbum o) {
		if(this.id>o.id) return 1;
		if(this.id==o.id) return 0;
		if(this.id<o.id) return -1;
		return 0;
	}
}
