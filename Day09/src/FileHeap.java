import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FileHeap extends MinHeap<File> {
    @Override
    public void swap(int i, int j) {
        ((FileNode)values.get(i)).index = j;
        ((FileNode)values.get(j)).index = i;
        super.swap(i, j);
    }

    @Override
    public void insert(File element) {
        ((FileNode) element).index = values.size();
        super.insert(element);
    }

    public void remove(File element) {
        FileNode nodeElement = (FileNode) element;
        int index = nodeElement.index;
        swap(index, values.size()-1);
        values.removeLast();
        boolean down = true;
        if (index >= values.size()) return;
        while (parent(index) >= 0 && values.get(parent(index)).compareTo(values.get(index)) > 0) {
            down = false;
            swap(index, parent(index));
            index = parent(index);
        }
        if (down) {
            heapify(index);
        }
    }

    public static void main(String[] args) {
        List<FileNode> testList = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            FileNode file = new FileNode(0, 0);
            file.pos = i;
            testList.add(file);
        }
        FileHeap heap = new FileHeap();
        Collections.shuffle(testList);
        for (FileNode file : testList) {
            heap.insert(file);
        }
        if (!heap.isCorrect(0)) {
            System.out.println("Errore a monte");
            return;
        }
        //heap.remove(heap.values.get(500));
        heap.pop();
        if (!heap.isCorrect(0)) {
            System.out.println("Errore a valle");
        }
    }
}
