package com.keegan.john.justcook;

/**
 * Created by Johnk on 17/02/2018.
 */

public class ListItem {
    private String head;
    private String imageUrl;
    private String ingred;

    public ListItem(String head, String imageUrl, String ingred) {
        this.head = head;

        this.imageUrl = imageUrl;

        this.ingred = ingred;
    }

    public String getHead() {
        return head;
    }

    public String getIngred(){
        return ingred;
    }


    public String getImageUrl() {
        return imageUrl;
    }
}
