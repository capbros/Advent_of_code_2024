import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public abstract class Utils {
    public static Scanner open_input(int day) {
        Scanner userInput = new Scanner(System.in);
        while (true) {
            System.out.println("Enter file name: ");
            String line = userInput.nextLine();
            String filePath = "Day"+day+"/"+line;
            try {
                if (line.equalsIgnoreCase("quit") || line.equalsIgnoreCase("q")) {
                    System.out.println("Quitting...");
                    return null;
                }
                return new Scanner(new File(filePath));
            } catch (FileNotFoundException e) {
                System.out.println("File with path \""+ filePath +"\" does not exist" +
                        "\nTry again");
            }
        }
    }
}
