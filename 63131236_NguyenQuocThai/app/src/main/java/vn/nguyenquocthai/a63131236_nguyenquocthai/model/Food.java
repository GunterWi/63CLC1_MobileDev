package vn.nguyenquocthai.a63131236_nguyenquocthai.model;

public class Food {
    private int foodFlag;
    private String foodName;
    private int price;
    private String description;

    public Food(int foodFlag, String foodName, int price, String description) {
        this.foodFlag = foodFlag;
        this.foodName = foodName;
        this.price = price;
        this.description = description;
    }

    public int getFoodFlag() {
        return foodFlag;
    }

    public void setFoodFlag(int foodFlag) {
        this.foodFlag = foodFlag;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
