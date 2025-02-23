import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main2 {
    public static void main(String[] args) throws IOException {
        List<FileNode> fileList = new ArrayList<>();
        boolean isFile = true;
        int id = 0;
        char c = (char) System.in.read();
        while (c != '\n') {
            int size = Character.getNumericValue(c);
            if (isFile) {
                fileList.add(new FileNode(size, id));
                id++;
            } else {
                fileList.getLast().spaceAfter = size;
            }
            isFile = !isFile;
            c = (char) System.in.read();
        }
        List<FileHeap> spaces = new ArrayList<>();
        for (int i = 1; i < fileList.size(); i++) {
            fileList.get(i).pos = fileList.get(i-1).pos + fileList.get(i-1).size +
                    fileList.get(i-1).spaceAfter;
        }
        for (int i = 0; i < 10; i++) {
            spaces.add(new FileHeap());
        }
        for (FileNode file : fileList) {
            spaces.get(file.spaceAfter).insert(file);
            //System.out.println("index = "+ file.index);
        }
        for (FileHeap heap : spaces) {
            if (!heap.isCorrect(0)) {
                System.out.println("Errore a monte");
                return;
            }
        }
        for (FileNode file : fileList.reversed()) {
            FileHeap heap = getMinHeap(file, spaces);
            if (heap != null) {
                spaces.get(file.spaceAfter).remove(file);
                if (!spaces.get(file.spaceAfter).isCorrect(0)) {
                    System.out.println("Brutto");
                    return;
                }
                FileNode min = (FileNode) heap.pop();
//                if (!heap.isCorrect(0)) {
//                    System.out.println("Brutto");
//                    return;
//                }
                file.spaceAfter = min.spaceAfter - file.size;
                file.pos = min.pos + min.size;
                min.spaceAfter = 0;
                spaces.get(file.spaceAfter).insert(file);
                if (!spaces.get(file.spaceAfter).isCorrect(0)) {
                    System.out.println("Brutto");
                    return;
                }
                spaces.get(min.spaceAfter).insert(min);
                if (!spaces.get(min.spaceAfter).isCorrect(0)) {
                    System.out.println("Brutto");
                    return;
                }
            }
        }
        FlexibleInt tot = new FlexibleInt(0);
        for (File file : fileList) {
            FlexibleInt increment = Utils.sumInt(file.pos, file.pos + file.size);
            increment.mul(file.id);
            tot.add(increment);
        }
        System.out.println("Tot = " + tot);
    }

    private static FileHeap getMinHeap(FileNode file, List<FileHeap> spaces) {
        FileHeap heap = null;
        for (int index = file.size; index < 10; index++) {
            FileHeap current = spaces.get(index);
            if (!current.isEmpty() && current.min().spaceAfter != index) {
                System.out.println("Something went wrong");
                return null;
            }
            if (!current.isEmpty() && current.min().compareTo(file) < 0 &&
                    (heap == null || current.min().compareTo(heap.min()) < 0))
                heap = current;
        }
        return heap;
    }
}
