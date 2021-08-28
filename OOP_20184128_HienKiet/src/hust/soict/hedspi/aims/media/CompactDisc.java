package hust.soict.hedspi.aims.media;
 /**
 * @author hienkietleog
 *
 */
import java.util.ArrayList;
import java.util.List;

import hust.soict.hedspi.aims.PlayerException;

public class CompactDisc extends Disc implements Playable {

    private String artist;
    private List<Track> tracks = new ArrayList<Track>();

    // constructor
    public CompactDisc(int id, String title) {
        super(id, title);
    }

    public CompactDisc(int id, String title, String category) {
        super(id, title, category);
    }
    
    public CompactDisc(int id, String title, String category, String director) {
        super(id, title, category, director);
    }
    
    public CompactDisc(int id, String title, String category, String director, float cost) {
        super(id, title, category, director, cost);
    }
    
    public CompactDisc(int id, String title, String category, String director, float cost, String artist) {
        super(id, title, category, director, cost);
        this.artist = artist;
    }
    
    public CompactDisc(int id, String title, String category, String director, float cost, String artist,
                       List<Track> tracks) {
        super(id, title, category, director, cost);
        this.artist = artist;
        this.tracks = tracks;
    }

    // getter
    public String getArtist() {
        return artist;
    }
    
    public int getNumberOfTrack() {
        return this.tracks.size();
    }
    
    public List<Track> getTracks() {
        return tracks;
    }
    
    // addTrack()
    public boolean addTrack(Track newTrack) throws IllegalArgumentException {
        // check if the input track is already in the list of tracks
        if (this.searchTrack(newTrack) == -1) {
            this.tracks.add(newTrack);
            System.out.println("Track added!");
            return true;
        } else {
            throw new IllegalArgumentException("ERROR: Track already exists in tracks list!");
        }
    }
    
    // removeTrack()
    public boolean removeTrack(Track oldTrack) throws IllegalArgumentException {
        int indexOfOldTrack = this.searchTrack(oldTrack);
        
        // check if the input track is already in the list of tracks
        if (indexOfOldTrack != -1) {
            this.tracks.remove(indexOfOldTrack);
            System.out.println("Track removed!");
            return true;
        } else {
            throw new IllegalArgumentException("ERROR: Track not found!");
        }
    }
    
    // search track by title and length
    public int searchTrack(Track cmpTrack) {
        for (int i = 0; i < this.tracks.size(); i++) {
            if (this.tracks.get(i).isEqualAll(cmpTrack)) {
                return i;
            }
        }
        
        return -1;
    }
    
    // calculate length of the CD, which is the sum of the lengths of all its tracks
    public int getLength() {
        int sumOfTrackLength = 0;
        
        for (Track track : this.tracks) {
            sumOfTrackLength += track.getLength();
        }
        
        return sumOfTrackLength;
    }
    
    // compareTo()
    @Override
    public int compareTo(Object obj) {
        if (obj instanceof CompactDisc) {
            CompactDisc tmp = (CompactDisc) obj;
            // sort by tracks number -> cost -> title
            if (this.tracks.size() > tmp.tracks.size()) {   // sort by number of tracks
                return 1;
            } else if (this.tracks.size() < tmp.tracks.size()) {
                return -1;
            } else if (this.getCost() > tmp.getCost()) {    // sort by cost
                return 1;
            } else if (this.getCost() < tmp.getCost()) {
                return -1;
            } else {                                        // sort by title
                return this.getTitle().compareTo(tmp.getTitle());
            }
        }
        return -1;
    }
    
    // print()
    @Override
    public void print() {
        System.out.printf("CD - %s - %s - %d min - %.2f$\n",
                this.getTitle(), this.getCategory(), this.getLength(), this.getCost());
    }
    
    // play()
    @Override
    public String play() throws PlayerException {
        if (this.getLength() > 0) {
            StringBuffer stringBuffer = new StringBuffer();
            
            stringBuffer.append("Playing CD: " + this.getTitle() + "\n");
            stringBuffer.append("CD length: " + this.getLength() + "\n");
            
            for (Track track : this.getTracks()) {
                try {
                    stringBuffer.append(track.play());
                } catch (PlayerException e) {
                    throw e;
                }
            }
            
            return stringBuffer.toString();
        } else {
            throw new PlayerException("ERROR: CD length is non-positive!");
        }
    }
    
}
