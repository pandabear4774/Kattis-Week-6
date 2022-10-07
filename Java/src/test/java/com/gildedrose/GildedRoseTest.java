package com.gildedrose;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GildedRoseTest {

    @Test
    void setsNameCorrectly() {
    	//this test makes sure that items are initialized with a name once they are created
    	//the test also makes sure that the items keep their name after the quality is updated
        Item[] items = new Item[] { new Item("foo", 0, 0) };
        GildedRose app = new GildedRose(items);
        assertEquals("foo", app.items[0].name);
        app.updateQuality();
        assertEquals("foo", app.items[0].name);
    }
    
    @Test
    void testValidName() {
    	//makes sure that each item has a name longer than 0 because an item with the name of an empty string is hard to identify in a shop
        Item[] items = new Item[] { new Item("", 0, 0), new Item("H", 0, 0) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(false, app.items[0].validName());
        assertEquals(true, app.items[1].validName());
    }
    
    @Test
    void setsSellPriceCorrectly() {
    	//this test makes sure that items are initialized with a sell in number.
    	//this test uses happy path testing and doesn't try any weird inputs as there are other tests for that
        Item[] items = new Item[] { new Item("foo", 87, 0), new Item("item2",0,0) };
        GildedRose app = new GildedRose(items);
        assertEquals(87, app.items[0].sellIn);
        assertEquals(0, app.items[1].sellIn);
    }
    
    @Test
    void setsQualityCorrectly() {
    	//this test makes sure that items are initialized with a quality number when the item is created. 
    	//this test uses happy path testing and doesn't try any weird inputs as there are other tests for that
        Item[] items = new Item[] { new Item("foo", 0, 0), new Item("item2",0,50) };
        GildedRose app = new GildedRose(items);
        assertEquals(0, app.items[0].quality);
        assertEquals(50, app.items[1].quality);
    }
    
    @Test
    void normalItemNotExpiredQualityDegradesTest() {
    	//tests if the item quality decreased by 1 each day
    	//these items are not expired so they only decrease by 1 each day instead of 2
        Item[] items = new Item[] { new Item("normalItem", 100, 10) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(9, app.items[0].quality);
        
        app.updateQuality();
        assertEquals(8, app.items[0].quality);
        
        app.updateQuality();
        assertEquals(7, app.items[0].quality);
        
        app.updateQuality();
        assertEquals(6, app.items[0].quality);
        
    }
    
    @Test
    void normalItemExpiredQualityDegradesTest() {
    	//tests to make sure that items that have expired decrease in quality by 2 since expired items quality decreases twice as fast. 
        Item[] items = new Item[] { new Item("normalItem", 1, 10) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(9, app.items[0].quality);
        
        //notice that quality is expected to decrease by 2 here in this test
        app.updateQuality();
        assertEquals(7, app.items[0].quality);
        
        app.updateQuality();
        assertEquals(5, app.items[0].quality);
        
        app.updateQuality();
        assertEquals(3, app.items[0].quality);
    }
    
    @Test
    void specialItemQualityDegredesTest() {
    	//tests whether the special item aged brie increases in quality and the special item sulfuras keep the same quality of 80 even after items are updated
        Item[] items = new Item[] { new Item("Aged Brie", 10, 1) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(2, app.items[0].quality);
        
        items = new Item[] { new Item("Sulfuras, Hand of Ragnaros", 50, 80) };
        app = new GildedRose(items);
        app.updateQuality();
        app.updateQuality();
        app.updateQuality();
        
        assertEquals(80, app.items[0].quality);
    }
    
    @Test
    void passIncreaseQualityTest() {
    	//tests that the quality of passes increases over time
    	//three items are put in one that has one day left, one that expires, and one that has 10 days left
    	//this should check all the special cases for the passes where one day left increases by 3, 0 days left makes the quality 0 and 10 days left increases quality by 2
        Item[] items = new Item[] { new Item("Backstage passes to a TAFKAL80ETC concert", 1, 10), new Item("Backstage passes to a TAFKAL80ETC concert", 0, 10), new Item("Backstage passes to a TAFKAL80ETC concert", 6, 10)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(13, app.items[0].quality);
        assertEquals(0, app.items[1].quality);
        assertEquals(12, app.items[2].quality);
    }
    
    @Test
    void priceNotNegativeTest() {
    	//check a negative value for the days left in an item because items should not start with a negative 
        Item[] items = new Item[] { new Item("zero", 0, 0), new Item("positive", 500, 0), new Item("negative", -1, 0) };
        GildedRose app = new GildedRose(items);
        assertEquals(true, app.items[0].checkPriceLimits());
        assertEquals(true, app.items[1].checkPriceLimits());
        assertEquals(false, app.items[2].checkPriceLimits());
    }
    
    @Test
    void qualityBoundsTest() {
    	//check for the lowest bounds and the highest bounds for the quality of an item as that is where an error is most likely to occur
        Item[] items = new Item[] { new Item("lowerLimit", 0, 0), new Item("negative", 0, -1), new Item("upperLimit", 0, 50), new Item("positive", 0, 51) };
        GildedRose app = new GildedRose(items);
        assertEquals(true, app.items[0].checkItemQualityLimit());
        assertEquals(false, app.items[1].checkItemQualityLimit());
        assertEquals(true, app.items[2].checkItemQualityLimit());
        assertEquals(false, app.items[3].checkItemQualityLimit());
    }

}
