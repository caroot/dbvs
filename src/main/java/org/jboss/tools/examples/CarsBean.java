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

import org.jboss.tools.examples.data.MemberListProducer;
import org.jboss.tools.examples.model.Member;

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
   private List<Member> allInventoryItems = null;
   private List<MemberListProducer> inventoryVendorLists = null;
   private int currentCarIndex;
   private Member editedCar;
   private int page = 1;

   private int clientRows;

   public void switchAjaxLoading(ValueChangeEvent event) {
       this.clientRows = (Boolean) event.getNewValue() ? CLIENT_ROWS_IN_AJAX_MODE : 0;
   }

   public void remove() {
       allInventoryItems.remove(allInventoryItems.get(currentCarIndex));
   }

   public void store() {
       allInventoryItems.set(currentCarIndex, editedCar);
   }

   public List<SelectItem> getVendorOptions() {
       List<SelectItem> result = new ArrayList<SelectItem>();
       result.add(new SelectItem("", ""));
       for (MemberListProducer vendorList : getInventoryVendorLists()) {
           result.add(new SelectItem(vendorList.getMembers().get(0)));
       }
       return result;
   }

   public List<String> getAllVendors() {
       List<String> result = new ArrayList<String>();
       for (MemberListProducer vendorList : getInventoryVendorLists()) {
           result.add(vendorList.toString());
       }
       return result;
   }

   public List<MemberListProducer> getInventoryVendorLists() {
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
   }

   private void itemToVendorItem(Member item, Member newItem) {
	   newItem.setEmail(item.getEmail());
	   newItem.setId(item.getId());
	   newItem.setPhoneNumber(item.getPhoneNumber());
   }

   public List<Member> getAllInventoryItems() {
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
   }

   public List<Member> createCar(String vendor, String model, int count) {
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
   }

   public int getCurrentCarIndex() {
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