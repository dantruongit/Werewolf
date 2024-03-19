package Utils;
import java.util.Random;

public class Utils {
    public static int nextInt(int start, int end){
        return new Random().nextInt(end - start + 1) + start;
    }
}
