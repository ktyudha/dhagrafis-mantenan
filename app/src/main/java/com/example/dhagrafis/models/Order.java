package com.example.dhagrafis.models;

public class Order {
   public String nameOrder;
   public String phoneOrder;
   public String paketOrder;
   public String datetimeOrder;
   public String locationOrder;
   public int priceOrder;

   public String noteOrder;

   public Order(){

   }

   public String getNameOrder() {
      return nameOrder;
   }

   public String getPhoneOrder() {
      return phoneOrder;
   }

   public String getPaketOrder() {
      return paketOrder;
   }

   public String getDatetimeOrder() {
      return datetimeOrder;
   }

   public String getLocationOrder() {
      return locationOrder;
   }

   public int getPriceOrder() {
      return priceOrder;
   }

   public String getNoteOrder() {
      return noteOrder;
   }

   public void setNameOrder(String nameOrder) {
      this.nameOrder = nameOrder;
   }

   public void setPhoneOrder(String phoneOrder) {
      this.phoneOrder = phoneOrder;
   }

   public void setPaketOrder(String paketOrder) {
      this.paketOrder = paketOrder;
   }

   public void setDatetimeOrder(String datetimeOrder) {
      this.datetimeOrder = datetimeOrder;
   }

   public void setLocationOrder(String locationOrder) {
      this.locationOrder = locationOrder;
   }

   public void setPriceOrder(int priceOrder) {
      this.priceOrder = priceOrder;
   }

   public void setNoteOrder(String noteOrder) {
      this.noteOrder = noteOrder;
   }
}
