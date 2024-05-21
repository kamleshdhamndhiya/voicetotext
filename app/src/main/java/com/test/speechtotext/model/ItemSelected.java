package com.test.speechtotext.model;

public class ItemSelected {

    private String item_name;
    private String subcategory_name;
    private String error_message;

  /*  public ItemSelected(String item_name, String subcategory_name) {
        this.item_name = item_name;
        this.subcategory_name = subcategory_name;
    }*/

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getSubcategory_name() {
        return subcategory_name;
    }

    public void setSubcategory_name(String subcategory_name) {
        this.subcategory_name = subcategory_name;
    }

    public String getError_message() {
        return error_message;
    }

    public void setError_message(String error_message) {
        this.error_message = error_message;
    }
}
