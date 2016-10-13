package com.liangdekai.musicplayer.bean;

/**
 * Created by asus on 2016/9/11.
 */
public class MenuItem {
    private int imageId ;

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    private String menuText ;

    public String getMenuText() {
        return menuText;
    }

    public void setMenuText(String menuText) {
        this.menuText = menuText;
    }

    public MenuItem(int imageId , String menuText){
        this.imageId = imageId ;
        this.menuText = menuText ;
    }

}
