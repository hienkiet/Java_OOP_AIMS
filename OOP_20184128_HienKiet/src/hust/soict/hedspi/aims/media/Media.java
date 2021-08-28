package hust.soict.hedspi.aims.media;
/**
* @author hienkietleog
*
*/
public abstract class Media implements Comparable<Object> {

    private int id;
    private String title;
    private String category;
    private float cost;
    
    // setter
    public void setCost(float cost) {
        this.cost = cost;
    }

    // constructor
    public Media(int id, String title) {
        this(id, title, "", 0);
    }
    
    public Media(int id, String title, String category) {
        this(id, title, category, 0);
    }

    public Media(int id, String title, String category, float cost) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.cost = cost;
    }

    // getter
    public void setId(int id) {
        this.id = id;
    }
    
    public int getId() {
        return id;
    }
    
    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public float getCost() {
        return cost;
    }
    
    // equals()
    @Override
    public boolean equals(Object obj) {
        boolean result = false;
        
        if (obj != null && obj instanceof Media) {
            Media tmp = (Media) obj;
            
            // Two media are equal if they have the same title and cost
            if (this.title.equals(tmp.title) && this.cost == tmp.cost) {
                result = true;
            }
        } else if (obj == null) {
            throw new NullPointerException("ERROR: The object hasn't been initialized!");
        } else {
            throw new ClassCastException("ERROR: The object can not be cast to Media!");
        }
        
        return result;
    }
    
    // compareTo()
    @Override
    public int compareTo(Object obj) {
        int result = 0;
        
        if (obj != null && obj instanceof Media) {
            Media tmp = (Media) obj;
            float c1 = round2DecimalPoints(this.cost);
            float c2 = round2DecimalPoints(tmp.cost);
            
            if (c1 > c2) { // sort by cost
                result = 1;
            } else if (c1 < c2) {
                result = -1;
            } else { // this.cost == tmp.cost -> sort by title
                result = this.title.compareTo(tmp.title);
            }   
        } else if (obj == null) {
            throw new NullPointerException("ERROR: The object hasn't been initialized!");
        } else {
            throw new ClassCastException("ERROR: The object can not be cast to Media!");
        }
        
        return result;
    }
    
    float round2DecimalPoints(float floatNumber) {
        return Math.round(floatNumber * 100.0f) / 100.0f;
    }
    
    // print()
    public void print() {
        System.out.printf("Media - %s - %s - %.2f$\n", this.getTitle(), this.getCategory(),this.getCost());
    }

}
