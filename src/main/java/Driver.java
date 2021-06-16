import java.lang.reflect.Field;
import java.util.Scanner;

public class Driver {
    public static void shop(String cola, String bisli) {
        System.out.println("1) " + cola);
        System.out.println("2) " + bisli);
        Scanner scan = new Scanner(System.in);
        System.out.println("1, 2 ?");
        int selection = scan.nextInt();
        if (selection == 1)
            System.out.println(cola);
        else if (selection == 2)
            System.out.println(bisli);
        else
            System.out.println("you dont select anything!");
    }

    public static void main(String[] args) {
        shop("cola","bisli");
    }
}
