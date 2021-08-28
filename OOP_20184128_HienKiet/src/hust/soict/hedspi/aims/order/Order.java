package hust.soict.hedspi.aims.order;
/**
* @author hienkietleog
*
*/
import java.lang.Math;
/*
 * @leog_hienkiet
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.naming.LimitExceededException;

import hust.soict.hedspi.aims.media.*;
import hust.soict.hedspi.aims.utils.MyDate;


public class Order {
    
    public static final int MAX_NUMBER_ORDERED = 10;
    public static final int MAX_LIMITED_ORDERS = 5;
    public static final int PURCHASE_REMIND = 7;
    
    private static final int LUCKY_PROBABILITY = 8; // a number from 1~10 // the higher the lesser ther chance
    public static final int LUCKY_MIN_NUMBER_OF_ITEMS = 3;
    public static final float LUCKY_MIN_TOTAL_COST = 100.00f;
    
    private static final float LUCKY_LEVEL_1 = 300.00f;
    private static final float LUCKY_LEVEL_2 = 1000.00f;
    private static final float LUCKY_LEVEL_3 = 5000.00f;
    
    private static final float LUCKY_MIN_VALUE = 1.00f;
    private static final float LUCKY_MAX_VALUE_1 = 30.00f;
    private static final float LUCKY_MAX_VALUE_2 = 100.00f;
    private static final float LUCKY_MAX_VALUE_3 = 500.00f;
    private static final float LUCKY_MAX_VALUE_4 = 1000.00f;
    
    
    private static int nbOrders = 0;
    
    private int id;
    private List<Media> itemsOrdered;
    MyDate dateOrdered;
    
    // constructor
    public Order(int id) throws LimitExceededException {
        if (nbOrders < MAX_LIMITED_ORDERS) {
            this.id = id;
            this.dateOrdered = new MyDate();
            this.itemsOrdered = new ArrayList<Media>();
            nbOrders++;
            if (nbOrders == MAX_LIMITED_ORDERS) {
                System.out.println("\nThe Number of orders reached max! Please purchase your orders!\n");
            }
        } else {
            throw new LimitExceededException("ERROR: The number of orders has reached its limit!");
        }
    }
    
    // getter
    public List<Media> getItemsOrdered() {
        return itemsOrdered;
    }
    
    public MyDate getDateOrdered() {
        return dateOrdered;
    }

    public static int getNbOrders() {
        return nbOrders;
    }

    // addMedia()
    public boolean addMedia(Media media) throws LimitExceededException {
        if (this.itemsOrdered.size() < MAX_NUMBER_ORDERED)  {
            this.itemsOrdered.add(media);
            int currentOrderSize = this.itemsOrdered.size();
            
            System.out.println("Item \"" + media.getTitle() + "\" has been added to Order! " +
                               "The number of items in order now is " + currentOrderSize + ".");
            if (currentOrderSize >= PURCHASE_REMIND && currentOrderSize < MAX_NUMBER_ORDERED) {
                System.out.println("The Order is almost full!");
            } else if (currentOrderSize == MAX_NUMBER_ORDERED) {
                System.out.println("The Order is full! Lets purchase your Order!");
            }
            
            return true;
        } else {
            throw new LimitExceededException("ERROR: The number of items in the order has reached its limit!");
        }
    }

    public boolean addMedia(Media ... media) throws LimitExceededException {
        int currentOrderSize = this.itemsOrdered.size();
        int spaceLeft = MAX_NUMBER_ORDERED - currentOrderSize;
        
        if (spaceLeft != 0 && media.length <= spaceLeft) {
            for (int i = 0; i < media.length; i++) {
                try {
                    addMedia(media[i]);
                } catch (LimitExceededException e) {
                    throw new LimitExceededException("ERROR: The number of items in the order has reached its limit!");
                }
            }            
            return true;
        } else  {
            throw new LimitExceededException("ERROR: The number of orders has reached its limit!");
        }
    }
    
    // removeMedia()
    public Media removeMedia(Media media) throws IllegalStateException, IllegalArgumentException {
        if (this.itemsOrdered.size() != 0 && this.itemsOrdered.contains(media)) {
            System.out.println("input id : " + media.getId());
            System.out.println("size : " + this.itemsOrdered.size());
            System.out.println(this.itemsOrdered.contains(media));
            
            // reset id of the item(s) behind the removed item
            int removeMediaId = this.itemsOrdered.indexOf(media);
            for (int i = removeMediaId + 1; i < this.itemsOrdered.size(); i++) {
                this.itemsOrdered.get(i).setId(i - 1);
            }
            
            // get the to-be-removed media
            Media removedMedia = this.itemsOrdered.get(removeMediaId);
            
            // remove item
            this.itemsOrdered.remove(media);
            
            // inform the user
            System.out.println("Item \"" + media.getTitle() + "\" has been removed from Order! " +
                    "The number of items in order now is " + this.itemsOrdered.size() + ".");
            
            return removedMedia;
        } else if (this.itemsOrdered.size() == 0) {
            throw new IllegalStateException("ERROR: the order is empty!");
        } else {
            throw new IllegalArgumentException("ERROR: item not found!");
        }
    }
    
    public Media removeMedia(int mediaId) throws IllegalStateException, IllegalArgumentException {
        Media removedMedia = null;
        
        try {
            removedMedia = removeMedia(this.itemsOrdered.get(mediaId));
        } catch (IllegalStateException | IllegalArgumentException e) {
            throw e;
        }
        
        return removedMedia;
    }
    
    // totalCost()
    public float totalCost() {
        float total = 0;
        
        for (int i = 0; i < this.itemsOrdered.size(); i++) {
            total += this.itemsOrdered.get(i).getCost();
        }
        
        return total;
    }
    
    // getALuckyItem()
    public float getALuckyItem(int minItem, float minTotalCost) {
        // the conditions to get a lucky item
        if (this.itemsOrdered.size() < minItem) {
            return 0.00f;
        }
        
        // the condition can be adjusted but must be greater than the default condition
        if (minTotalCost < LUCKY_MIN_TOTAL_COST) {
            minTotalCost = LUCKY_MIN_TOTAL_COST;
        }
        
        if (this.totalCost() < minTotalCost) { 
            return 0.00f;
        }
        
        // try your luck if you could get a lucky item
        int luckyEnough = getRandomNumber(1, 10);
        
        if (luckyEnough < LUCKY_PROBABILITY) {
            // better luck next time
            return 0.00f;
        }
        
        // get the max value of lucky item
        float maxLuckyValue = 0.00f;
        if (this.totalCost() < LUCKY_LEVEL_1) {         // total cost = 100.00 ~ 299.99 $
            maxLuckyValue = LUCKY_MAX_VALUE_1;
        } else if (this.totalCost() < LUCKY_LEVEL_2) {  // total cost = 300.00 ~ 999.99 $
            maxLuckyValue = LUCKY_MAX_VALUE_2;
        } else if (this.totalCost() < LUCKY_LEVEL_3) {  // total cost = 1000.00 ~ 4999.99 $
            maxLuckyValue = LUCKY_MAX_VALUE_3;
        } else {                                        // total cost >= 5000.00 $
            maxLuckyValue = LUCKY_MAX_VALUE_4;
        }
        
        // random the lucky item value
        Random rand = new Random();
        float randomValue = LUCKY_MIN_VALUE + rand.nextFloat() * (maxLuckyValue - LUCKY_MIN_VALUE);
        float luckyItemValue = round2DecimalPoints(randomValue);
        
        // inform the user
        System.out.println("Congratulation! Your won a lucky item that costs " + luckyItemValue + "$ !");
        
        return luckyItemValue;
    }
    
    float round2DecimalPoints(float floatNumber) {
        return Math.round(floatNumber * 100.0f) / 100.0f;
    }
    
    private int getRandomNumber(int min, int max) {
        int range = (max - min) + 1;
        return (int)(Math.random() * range) + min;
    }
    
    // printOrder()
    public void printOrder() {
        if (this.itemsOrdered.size() == 0) {
            System.out.println("\nYour Order: is empty. Lets add something to your order!\n");
            return;
        }
        
        System.out.println("***********************************Order***********************************");
        System.out.println("Id: " + this.id);
        System.out.print("Date: ");
        dateOrdered.print();
        System.out.println("Ordered items:");
        for (int i = 0; i < this.itemsOrdered.size(); i++) {
            System.out.printf("%02d. ", this.itemsOrdered.get(i).getId() + 1);
            if (this.itemsOrdered.get(i) instanceof DigitalVideoDisc) {
                ((DigitalVideoDisc)this.itemsOrdered.get(i)).print();
            } else if (this.itemsOrdered.get(i) instanceof Book) {
                ((Book)this.itemsOrdered.get(i)).print();
            } else {
                this.itemsOrdered.get(i).print();
            }
        }
        System.out.println("Total cost: " + this.totalCost() + "$");
        System.out.println("***************************************************************************");
    }
    
}
