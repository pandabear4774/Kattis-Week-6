package com.gildedrose;

public class Item {

    public String name;

    public int sellIn;

    public int quality;

    public Item(String name, int sellIn, int quality) {
        this.name = name;
        this.sellIn = sellIn;
        this.quality = quality;
    }
    
    public boolean validName() {
    	if(name.length() > 0) {
    		return true;
    	}
    	return false;
    }
    
    public boolean checkPriceLimits() {
    	if(sellIn >= 0) {
    		return true;
    	}
    	return false;
    }
    public boolean checkItemQualityLimit() {
    	if(quality >= 0 && (quality <= 50 && !name.equals("Sulfuras"))) {
    		return true;
    	}
    	return false;
    }
    

   @Override
   public String toString() {
        return this.name + ", " + this.sellIn + ", " + this.quality;
    }
   
   
}
