package com.example.a51c;

public class NewsItem {

    private  String title,description;
    private  int imageResId;

    public NewsItem (String title,String description,int  imageResId){
        this.title = title;
        this.description =description;
        this.imageResId =imageResId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getImageResId() {
        return imageResId;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }
}
