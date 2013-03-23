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

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

//import org.jboss.tools.examples.data.MemberListProducer;
//import org.jboss.tools.examples.model.Member;
import org.jboss.tools.examples.model.PhotoAlbumList;
import org.jboss.tools.examples.model.PhotoAlbum;
//import org.richfaces.demo.common.data.RandomHelper;

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
   private int currentPicIndex;
 //  private Member editedCar;
   private PhotoAlbum editedPic;
   private int page = 1;

   private int clientRows;

   public void switchAjaxLoading(ValueChangeEvent event) {
       this.clientRows = (Boolean) event.getNewValue() ? CLIENT_ROWS_IN_AJAX_MODE : 0;
   }

   public void remove() {
       //allInventoryItems.remove(allInventoryItems.get(currentCarIndex)
    		   allPhotoAlbums.remove(allPhotoAlbums.get(currentPicIndex));
   }

   public void store() {
       //allInventoryItems.set(currentCarIndex, editedCar);
       allPhotoAlbums.set(currentPicIndex, editedPic);
   }

  /** public List<SelectItem> getVendorOptions() {
       List<SelectItem> result = new ArrayList<SelectItem>();
       result.add(new SelectItem("", ""));
       for (MemberListProducer vendorList : getInventoryVendorLists()) {
           result.add(new SelectItem(vendorList.getMembers().get(0)));
       }
       return result;
   }**/
   
  /** public List<SelectItem> getVendorOptions() {
       List<SelectItem> result = new ArrayList<SelectItem>();
       result.add(new SelectItem("", ""));
       for (PhotoAlbumList vendorList : getPhotoAlbumLists()) {
           result.add(new SelectItem(vendorList.getPhotoAlbums().get(0)));
       }
       return result;
   }**/

  /** public List<String> getAllVendors() {
       List<String> result = new ArrayList<String>();
       for (MemberListProducer vendorList : getInventoryVendorLists()) {
           result.add(vendorList.toString());
       }
       return result;
   }**/
   
   public List<String> getAllVendors() {
       List<String> result = new ArrayList<String>();
       for (PhotoAlbumList vendorList : getPhotoAlbumLists()) {
           result.add(vendorList.toString());
       }
       return result;
   }

   /**public List<MemberListProducer> getInventoryVendorLists() {
       synchronized (this) {
           if (inventoryVendorLists == null) {
               inventoryVendorLists = new ArrayList<MemberListProducer>();
               List<Member> inventoryItems = getAllInventoryItems();

               Collections.sort(inventoryItems, new Comparator<Member>() {
                   public int compare(Member o1,  Member o2) {
                       return o1.compareTo(o2);
                   }
               });
               Iterator<Member> iterator = inventoryItems.iterator();
               MemberListProducer vendorList = new MemberListProducer();
               vendorList.onMemberListChanged(inventoryItems.get(0));
               while (iterator.hasNext()) {
                   Member item = iterator.next();
                   Member newItem = new Member();
                   itemToVendorItem(item, newItem);
                   if (!item.equals(vendorList.getMembers())) {
                       inventoryVendorLists.add(vendorList);
                       vendorList = new MemberListProducer();
                       vendorList.onMemberListChanged(item);
                   }
                   vendorList.onMemberListChanged(newItem);
               }
               inventoryVendorLists.add(vendorList);
           }
       }
       return inventoryVendorLists;
   }**/
   
   public List<PhotoAlbumList> getPhotoAlbumLists() {
       synchronized (this) {
           if (photoAlbumLists == null) {
               photoAlbumLists = new ArrayList<PhotoAlbumList>();
               List<PhotoAlbum> inventoryItems = getAllPhotoAlbums();

               Collections.sort(inventoryItems, new Comparator<PhotoAlbum>() {
                   public int compare(PhotoAlbum o1,  PhotoAlbum o2) {
                       return o1.compareTo(o2);
                   }
               });
               Iterator<PhotoAlbum> iterator = inventoryItems.iterator();
               PhotoAlbumList vendorList = new PhotoAlbumList();
               vendorList.setName(inventoryItems.get(0).getName());
               while (iterator.hasNext()) {
                   PhotoAlbum item = iterator.next();
                   PhotoAlbum newItem = new PhotoAlbum();
                   itemToPhotoAlbumItem(item, newItem);
                   if (!item.getName().equals(vendorList.getName())) {
                       photoAlbumLists.add(vendorList);
                       vendorList = new PhotoAlbumList();
                       vendorList.setName(item.getName());
                   }
                   vendorList.getPhotoAlbums().add(newItem);
               }
               photoAlbumLists.add(vendorList);
           }
       }
       return photoAlbumLists;
   }

   /**private void itemToVendorItem(Member item, Member newItem) {
	   newItem.setEmail(item.getEmail());
	   newItem.setId(item.getId());
	   newItem.setPhoneNumber(item.getPhoneNumber());
   }**/
   
   private void itemToPhotoAlbumItem(PhotoAlbum item, PhotoAlbum newItem) {
	   newItem.setId(item.getId());
	   newItem.setName(item.getName());
	   newItem.setBeschreibung(item.getBeschreibung());
	   //newItem.setId(item.getId());
	  // newItem.setName(item.getName());
   }

   /**public List<Member> getAllInventoryItems() {
       synchronized (this) {
           if (allInventoryItems == null) {
               allInventoryItems = new ArrayList<Member>();

               for (int k = 0; k <= 5; k++) {
                   try {
                       switch (k) {
                           case 0:
                               allInventoryItems.addAll(createCar("Chevrolet", "Corvette", 5));
                               allInventoryItems.addAll(createCar("Chevrolet", "Malibu", 8));
                               allInventoryItems.addAll(createCar("Chevrolet", "Tahoe", 6));

                               break;

                           case 1:
                               allInventoryItems.addAll(createCar("Ford", "Taurus", 12));
                               allInventoryItems.addAll(createCar("Ford", "Explorer", 11));

                               break;

                           case 2:
                               allInventoryItems.addAll(createCar("Nissan", "Maxima", 9));
                               allInventoryItems.addAll(createCar("Nissan", "Frontier", 6));

                               break;

                           case 3:
                               allInventoryItems.addAll(createCar("Toyota", "4-Runner", 7));
                               allInventoryItems.addAll(createCar("Toyota", "Camry", 15));
                               allInventoryItems.addAll(createCar("Toyota", "Avalon", 13));

                               break;

                           case 4:
                               allInventoryItems.addAll(createCar("GMC", "Sierra", 8));
                               allInventoryItems.addAll(createCar("GMC", "Yukon", 10));

                               break;

                           case 5:
                               allInventoryItems.addAll(createCar("Infiniti", "G35", 6));
                               allInventoryItems.addAll(createCar("Infiniti", "EX35", 5));

                               break;

                           default:
                               break;
                       }
                   } catch (Exception e) {
                       e.printStackTrace();
                   }
               }
           }
       }
       return allInventoryItems;
   }**/

   public List<PhotoAlbum> getAllPhotoAlbums() {
       synchronized (this) {
           if (allPhotoAlbums == null) {
               allPhotoAlbums = new ArrayList<PhotoAlbum>();

               for (int k = 0; k <= 5; k++) {
                   try {
                       switch (k) {
                           case 0:
                               allPhotoAlbums.addAll(createPic("Chevrolet", "Corvette", 5));
                               //allInventoryItems.addAll(createPic("Che","Cha", 4));
                               allPhotoAlbums.addAll(createPic("Chevrolet", "Malibu", 8));
                               allPhotoAlbums.addAll(createPic("Chevrolet", "Tahoe", 6));

                               break;

                           case 1:
                               allPhotoAlbums.addAll(createPic("Ford", "Taurus", 12));
                               allPhotoAlbums.addAll(createPic("Ford", "Explorer", 11));

                               break;

                           case 2:
                               allPhotoAlbums.addAll(createPic("Nissan", "Maxima", 9));
                               allPhotoAlbums.addAll(createPic("Nissan", "Frontier", 6));

                               break;

                           case 3:
                               allPhotoAlbums.addAll(createPic("Toyota", "4-Runner", 7));
                               allPhotoAlbums.addAll(createPic("Toyota", "Camry", 15));
                               allPhotoAlbums.addAll(createPic("Toyota", "Avalon", 13));

                               break;

                           case 4:
                               allPhotoAlbums.addAll(createPic("GMC", "Sierra", 8));
                               allPhotoAlbums.addAll(createPic("GMC", "Yukon", 10));

                               break;

                           case 5:
                               allPhotoAlbums.addAll(createPic("Infiniti", "G35", 6));
                               allPhotoAlbums.addAll(createPic("Infiniti", "EX35", 5));

                               break;

                           default:
                               break;
                       }
                   } catch (Exception e) {
                       e.printStackTrace();
                   }
               }
           }
       }
       return allPhotoAlbums;
   }
  /** public List<Member> createCar(String vendor, String model, int count) {
       ArrayList<Member> iiList = null;

       try {
           int arrayCount = count;
           Member[] demoInventoryItemArrays = new Member[arrayCount];

           for (int j = 0; j < demoInventoryItemArrays.length; j++) {
               Member ii = new Member();

               ii.setId(1L);
               ii.setName("1");
               ii.setEmail("2");
               demoInventoryItemArrays[j] = ii;
           }

           iiList = new ArrayList<Member>(Arrays.asList(demoInventoryItemArrays));
       } catch (Exception e) {
           e.printStackTrace();
       }

       return iiList;
   }**/
   
   public List<PhotoAlbum> createPic(String name, String beschreibung,int count) {
       ArrayList<PhotoAlbum> iiList = null;

       try {
           int arrayCount = count;
           PhotoAlbum[] demoInventoryItemArrays = new PhotoAlbum[arrayCount];

           for (int j = 0; j < demoInventoryItemArrays.length; j++) {
               PhotoAlbum ii = new PhotoAlbum();

               ii.setId(1);
               ii.setName(name);
              // ii.setName(RandomHelper.randomstring());
               ii.setBeschreibung(beschreibung);
               
               demoInventoryItemArrays[j] = ii;
           }

           iiList = new ArrayList<PhotoAlbum>(Arrays.asList(demoInventoryItemArrays));
       } catch (Exception e) {
           e.printStackTrace();
       }

       return iiList;
   }

  /** public int getCurrentCarIndex() {
       return currentCarIndex;
   }

   public void setCurrentCarIndex(int currentCarIndex) {
       this.currentCarIndex = currentCarIndex;
   }

   public Member getEditedCar() {
       return editedCar;
   }

   public void setEditedCar(Member editedCar) {
       this.editedCar = editedCar;
   }**/
   
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
}