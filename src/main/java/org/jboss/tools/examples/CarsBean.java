package org.jboss.tools.examples;

/**
*
*/


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

//import org.jboss.tools.examples.data.MemberListProducer;
//import org.jboss.tools.examples.model.Member;
import org.jboss.tools.examples.model.PhotoAlbumList;
import org.jboss.tools.examples.model.PhotoAlbum;
//import org.richfaces.demo.common.data.RandomHelper;
import org.jboss.tools.examples.service.PhotoAlbumRegistration;

@ManagedBean(name = "carsBean")
@ViewScoped

public class CarsBean implements Serializable {
   /**
    *
    */
   private static final long serialVersionUID = -3832235132261771583L;
   private static final int DECIMALS = 1;
   private static final int CLIENT_ROWS_IN_AJAX_MODE = 15;
   private static final int ROUNDING_MODE = BigDecimal.ROUND_HALF_UP;
 //  private List<Member> allInventoryItems = null;
 //  private List<MemberListProducer> inventoryVendorLists = null;
   private List<PhotoAlbum> allPhotoAlbums = null;
   private List<PhotoAlbumList> photoAlbumLists = null;
 //  private int currentCarIndex;
   private int currentPicIndex=1;
 //  private Member editedCar;
   private PhotoAlbum editedPic;
   private int page = 1;

   private int clientRows;
   
   @Inject
   private Logger log;

   @Inject
   private FacesContext facesContext;

   @Inject
   private PhotoAlbumRegistration photoAlbumRegistration;

   @Inject
   private Event<PhotoAlbum> photoAlbumEventSrc;

   
   @Inject
   private EntityManager em;
   
   private PhotoAlbum newPhotoAlbum;

   public void switchAjaxLoading(ValueChangeEvent event) {
       this.clientRows = (Boolean) event.getNewValue() ? CLIENT_ROWS_IN_AJAX_MODE : 0;
   }

   public void remove() {
	   synchronized (this) {//allInventoryItems.remove(allInventoryItems.get(currentCarIndex)
       log.info("Loesche Photoalbum mit der ID:"+ currentPicIndex);
       allPhotoAlbums.remove(currentPicIndex);
       showInfos(allPhotoAlbums);
	   }
   }

   public void store() {
       //allInventoryItems.set(currentCarIndex, editedCar);
       allPhotoAlbums.set(currentPicIndex, editedPic);
   }


   
   public List<String> getAllAlbumsName() {
       List<String> result = new ArrayList<String>();
       for (PhotoAlbumList albumsNameList : getPhotoAlbumLists()) {
           result.add(albumsNameList.toString());
       }
       return result;
   }

     
   public List<PhotoAlbumList> getPhotoAlbumLists() {
       synchronized (this) {
           if (photoAlbumLists == null) {
               photoAlbumLists = new ArrayList<PhotoAlbumList>();
               List<PhotoAlbum> photoAlbums = getAllPhotoAlbums();

               Collections.sort(photoAlbums, new Comparator<PhotoAlbum>() {
                   public int compare(PhotoAlbum o1,  PhotoAlbum o2) {
                       return o1.compareTo(o2);
                   }
               });
               Iterator<PhotoAlbum> iterator = photoAlbums.iterator();
               PhotoAlbumList albumsNameList = new PhotoAlbumList();
               albumsNameList.setName(photoAlbums.get(0).getName());
               while (iterator.hasNext()) {
                   PhotoAlbum item = iterator.next();
                   PhotoAlbum newItem = new PhotoAlbum();
                   itemToPhotoAlbumItem(item, newItem);
                   if (!item.getName().equals(albumsNameList.getName())) {
                       photoAlbumLists.add(albumsNameList);
                       albumsNameList = new PhotoAlbumList();
                       albumsNameList.setName(item.getName());
                   }
                   albumsNameList.getPhotoAlbums().add(newItem);
               }
               photoAlbumLists.add(albumsNameList);
           }
       }
       return photoAlbumLists;
   }

   
   private void itemToPhotoAlbumItem(PhotoAlbum item, PhotoAlbum newItem) {
	   newItem.setName(item.getName());
	   newItem.setBeschreibung(item.getBeschreibung());
	   newItem.setId(item.getId());
	   //newItem.setId(item.getId());
	  // newItem.setName(item.getName());
   }

  
   public List<PhotoAlbum> getAllPhotoAlbums() {
       synchronized (this) {
           if (allPhotoAlbums == null) {
               allPhotoAlbums = new ArrayList<PhotoAlbum>();

              /* for (int k = 0; k <= 5; k++) {
                   try {
                       switch (k) {
                           case 0:
                               allPhotoAlbums.add(createPic("Chevrolet", "Corvette", 5));
                               //allInventoryItems.addAll(createPic("Che","Cha", 4));
                               allPhotoAlbums.add(createPic("Chevrolet", "Malibu", 8));
                               allPhotoAlbums.add(createPic("Chevrolet", "Tahoe", 6));

                               break;

                           case 1:
                               allPhotoAlbums.add(createPic("Ford", "Taurus", 12));
                               allPhotoAlbums.add(createPic("Ford", "Explorer", 11));

                               break;

                           case 2:
                               allPhotoAlbums.add(createPic("Nissan", "Maxima", 9));
                               allPhotoAlbums.add(createPic("Nissan", "Frontier", 6));

                               break;

                           case 3:
                               allPhotoAlbums.add(createPic("Toyota", "4-Runner", 7));
                               allPhotoAlbums.add(createPic("Toyota", "Camry", 15));
                               allPhotoAlbums.add(createPic("Toyota", "Avalon", 13));

                               break;

                           case 4:
                               allPhotoAlbums.add(createPic("GMC", "Sierra", 8));
                               allPhotoAlbums.add(createPic("GMC", "Yukon", 10));

                               break;

                           case 5:
                               allPhotoAlbums.add(createPic("Infiniti", "G35", 6));
                               allPhotoAlbums.add(createPic("Infiniti", "EX35", 5));

                               break;

                           default:
                               break;
                       }
                   } catch (Exception e) {
                       e.printStackTrace();
                       log.info("Fehler:"+e.getLocalizedMessage());
                   }
               }*/
               
           }
       }
       //showInfos(allPhotoAlbums);
       return allPhotoAlbums;
   }
     
   private void showInfos(List<PhotoAlbum> list)
   {
	   for (PhotoAlbum pa : list)
	   {
		   log.info("Name:" + pa.getName() + " Beschreibung:" + pa.getBeschreibung() + " ID:" + pa.getAnzahl());
	   }
   }
   public PhotoAlbum createPic(String name, String beschreibung, int id) {
       //ArrayList<PhotoAlbum> iiList = null;

      // try {
    	//   int count = 0;
          // int arrayCount = count;
          // PhotoAlbum[] demoPhotoAlbumArrays = new PhotoAlbum[arrayCount];

           //for (int j = 0; j < demoPhotoAlbumArrays.length; j++) {
               PhotoAlbum ii = new PhotoAlbum();

               
               ii.setName(name);
               ii.setBeschreibung(beschreibung);
               ii.setId(id);
               
            //   demoPhotoAlbumArrays[j] = ii;
           //}

           //iiList = new ArrayList<PhotoAlbum>(Arrays.asList(demoPhotoAlbumArrays));
       //} catch (Exception e) {
         //  e.printStackTrace();
       //}

       return ii;
   }

     
   public int getCurrentPicIndex() {
       return currentPicIndex;
   }

   public void setCurrentPicIndex(int currentPicIndex) {
       this.currentPicIndex = currentPicIndex;
   }

   public PhotoAlbum getEditedPic() {
       return editedPic;
   }

   public void setEditedPic(PhotoAlbum editedPic) {
       this.editedPic = editedPic;
   }


   public int getPage() {
       return page;
   }

   public void setPage(int page) {
       this.page = page;
   }

   public int getClientRows() {
       return clientRows;
   }

   public void setClientRows(int clientRows) {
       this.clientRows = clientRows;
   }
   
   @Produces
   @Named
   public PhotoAlbum getNewPhotoAlbum() {
      return editedPic;
   }
   
   @PostConstruct
   public void initNewPhotoAlbum() {
	   editedPic = new PhotoAlbum();
   }
   
   
   
   public void register (){
	   try {
		   //log.info("Registering " + newPhotoAlbum.getName());
		     // log.info("Registering " + newPhotoAlbum.getBeschreibung());
		      //log.info("Registering " + newPhotoAlbum.getId());
		      //em.persist(newPhotoAlbum);
		      //photoAlbumEventSrc.fire(newPhotoAlbum);
		   allPhotoAlbums.add(createPic(editedPic.getName(), editedPic.getBeschreibung(), editedPic.getId())); 
           photoAlbumRegistration.register(editedPic);
           showInfos(allPhotoAlbums);
           photoAlbumEventSrc.fire(editedPic);
           initNewPhotoAlbum();
           FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registered!", "Registration successful");
           facesContext.addMessage(null, m);
       } catch (Exception e) {
           String errorMessage = getRootErrorMessage(e);
           FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Registration unsuccessful");
           facesContext.addMessage(null, m);
       }
   }

   
   private String getRootErrorMessage(Exception e) {
       // Default to general error message that registration failed.
       String errorMessage = "Registration failed. See server log for more information";
       if (e == null) {
           // This shouldn't happen, but return the default messages
           return errorMessage;
       }

       // Start with the exception and recurse to find the root cause
       Throwable t = e;
       while (t != null) {
           // Get the message from the Throwable class instance
           errorMessage = t.getLocalizedMessage();
           t = t.getCause();
       }
       // This is the root cause message
       return errorMessage;
   }  

}


