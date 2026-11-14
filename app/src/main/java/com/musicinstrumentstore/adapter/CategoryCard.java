package com.musicinstrumentstore.adapter;

public class CategoryCard {
    private int image_flag;
    private String category_name;

    public CategoryCard(int image_flag, String category_name) {
        this.image_flag = image_flag;
        this.category_name = category_name;
    }

    public int getImage_flag() {
        return image_flag;
    }

    public void setImage_flag(int image_flag) {
        this.image_flag = image_flag;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }
}
