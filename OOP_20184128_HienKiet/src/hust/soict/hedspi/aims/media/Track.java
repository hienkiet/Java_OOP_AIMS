package hust.soict.hedspi.aims.media;
 /**
 * @author hienkietleog
 *
 */
import hust.soict.hedspi.aims.PlayerException;

/**
* class Track models a track on a Compact disc
*/
public class Track implements Playable, Comparable<Object> {
    
    private String title;
    private int length;
    
    // constructor
    public Track(String title, int length) {
        this.title = title;
        this.length = length;
    }

    // getter
    public String getTitle() {
        return title;
    }

    public int getLength() {
        return length;
    }
    
    // isEqualTittle()
    public boolean isEqualAll(Track aTrack) {
        if (this.title.equals(aTrack.getTitle())) {
            if (this.length == aTrack.length) {
                return true;
            }
        }
        return false;
    }
    
    // equals()
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Track) {
            Track tmp = (Track) obj;
            if (this.getTitle() == tmp.getTitle() && this.getLength() == tmp.getLength()) {
                return true;
            }
        }
        return false;
    }
    
    // compareTo()
    @Override
    public int compareTo(Object obj) {
        if (obj instanceof Track) {
            Track tmp = (Track) obj;
            if (this.title.equals(tmp.title)) {
                return 1;
            }
        }
        return -1;
    }
    
    // play()
    @Override
    public String play() throws PlayerException {
        if (this.getLength() > 0) {
            StringBuffer stringBuffer = new StringBuffer();
            
            stringBuffer.append("Playing Track: " + this.getTitle() + "\n");
            stringBuffer.append("Track length: " + this.getLength() + "\n");
            
            return stringBuffer.toString();
        } else {
            throw new PlayerException("ERROR: Track length is non-positive!");
        }
    }

}
