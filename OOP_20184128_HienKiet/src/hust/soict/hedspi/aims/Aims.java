package hust.soict.hedspi.aims;

 /**
 * @author hienkietleog
 *
 */
 

import java.util.ArrayList;
import java.util.List;

import javax.naming.LimitExceededException;

import hust.soict.hedspi.aims.media.*;
import hust.soict.hedspi.aims.order.*;

public class Aims {
    
    private List<Order> orders = new ArrayList<Order>();
    
    // getter
    public List<Order> getOrders() {
        return orders;
    }
    
    // 1. Create new order
    public void createOrder() throws LimitExceededException {
        int newOrderId = orders.size();
        
        try {
            orders.add(new Order(newOrderId));
        } catch (LimitExceededException e) {
            throw e;
        }
    }
    
    // 2. Add an item to a order
    public void addItemToOrder(int orderId, Media newMedia) throws LimitExceededException {
        try {
            this.orders.get(orderId).addMedia(newMedia);
        } catch (LimitExceededException e) {
            throw e;
        }
    }
    
    // 3. Delete an item by id
    public Media deleteItemById(int orderId, int itemId) throws IllegalStateException, IllegalArgumentException {
        Media removedMedia = null;
        
        try {
            removedMedia = this.orders.get(orderId).removeMedia(itemId);
        } catch (IllegalStateException | IllegalArgumentException e) {
            throw e;
        }
        
        return removedMedia;
    }
    
}
