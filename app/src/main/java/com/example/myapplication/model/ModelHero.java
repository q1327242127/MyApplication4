package com.example.myapplication.model;

public class ModelHero {
    private int hero_x;
    private int hero_y;
    private int map_x;
    private int map_y;
    private int orientation;
    private int mapCount;
    private int img_move;
    private String bitmap_hero;

    public ModelHero() {
    }

    public int getHero_x() {
        return hero_x;
    }

    public void setHero_x(int hero_x) {
        this.hero_x = hero_x;
    }

    public int getHero_y() {
        return hero_y;
    }

    public void setHero_y(int hero_y) {
        this.hero_y = hero_y;
    }

    public int getMap_x() {
        return map_x;
    }

    public void setMap_x(int map_x) {
        this.map_x = map_x;
    }

    public int getMap_y() {
        return map_y;
    }

    public void setMap_y(int map_y) {
        this.map_y = map_y;
    }

    public int getOrientation() {
        return orientation;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    public int getMapCount() {
        return mapCount;
    }

    public void setMapCount(int mapCount) {
        this.mapCount = mapCount;
    }

    public int getImg_move() {
        return img_move;
    }

    public void setImg_move(int img_move) {
        this.img_move = img_move;
    }

    public String getBitmap_hero() {
        return bitmap_hero;
    }

    public void setBitmap_hero(String bitmap_hero) {
        this.bitmap_hero = bitmap_hero;
    }

    @Override
    public String toString() {
        return "ModelHero{" +
                "hero_x=" + hero_x +
                ", hero_y=" + hero_y +
                ", map_x=" + map_x +
                ", map_y=" + map_y +
                ", orientation=" + orientation +
                ", mapCount=" + mapCount +
                ", img_move=" + img_move +
                ", bitmap_hero='" + bitmap_hero + '\'' +
                '}';
    }
}
