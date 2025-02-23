import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner input = new Scanner(System.in);
        List<File> fileList = new ArrayList<>();
        boolean isFile = true;
        int id = 0;
        char c = (char) System.in.read();
        while (c != '\n') {
            int size = Character.getNumericValue(c);
            if (isFile) {
                fileList.add(new File(size, id));
                id++;
            } else {
                fileList.getLast().spaceAfter = size;
            }
            isFile = !isFile;
            c = (char) System.in.read();
        }
        for (int i = 0; i < fileList.size(); i++) {
            File file = fileList.get(i);
            while (file.spaceAfter > 0 && fileList.size() > i+1) {
                File lastFile = fileList.getLast();
                int sizeMoved = Integer.min(file.spaceAfter, lastFile.size);
                lastFile.size -= sizeMoved;
                file.spaceAfter -= sizeMoved;
                file.next.add(new File(sizeMoved, lastFile.id));
                if (lastFile.size == 0) fileList.removeLast();
            }
        }
        fileList = fileList.stream().flatMap(file -> {
            List<File> flatList = new ArrayList<>();
            flatList.add(file);
            flatList.addAll(file.next);
            file.next.clear();
            return flatList.stream();
        }).toList();
        long tot = 0;
        int pos = 0;
        for (File file : fileList) {
            tot += file.id * Utils.sumAllInt(pos, pos + file.size);
            pos += file.size;
        }
        System.out.println("Tot = " + tot);
    }
}
