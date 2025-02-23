import java.util.ArrayList;
import java.util.List;

public class File implements Comparable<File>{
    int id;
    int size;
    int pos;
    int spaceAfter;
    List<File> next;

    public File(int size, int id) {
        this.size = size;
        this.id = id;
        this.next = new ArrayList<>();
        this.spaceAfter = 0;
        this.pos = 0;
    }

    @Override
    public int compareTo(File o) {
        return Integer.compare(this.pos, o.pos);
    }

    @Override
    public String toString() {
        return "File{" +
                "id=" + id +
                ", size=" + size +
                ", pos=" + pos +
                ", spaceAfter=" + spaceAfter +
                '}';
    }
}
