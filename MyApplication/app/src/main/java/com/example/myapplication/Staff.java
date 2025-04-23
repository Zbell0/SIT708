package com.example.myapplication;

public class Staff {
    private String name,position;
    private Integer id;
    public Staff(Integer id, String name , String position){
        this.id = id;
        this.name = name;
         this.position = position;
    }



    public Integer getId() {
        return id;
    }



    public String getName(){
        return name;
    }

    public String getPosition(){
        return position;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setPosition(String position){
        this.position = position;
    }
    public void setId(Integer id) {
        this.id = id;
    }
}
