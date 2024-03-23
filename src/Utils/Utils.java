package Utils;
import java.util.List;
import java.util.Random;

public class Utils {
    /**
     * Hàm random 1 số trong [start;end]
     * @param start Giá trị đầu
     * @param end Giá trị cuối
     * @return Một số bất kì trong [start;end]
    */
    public static int nextInt(int start, int end){
        return new Random().nextInt(end - start + 1) + start;
    }
    
    /**
     * Hàm random theo tỉ lệ %
     * @param percent Phần trăm xảy ra sự kiện
     * @param ratio Tỉ lệ phần trăm tối đa
     * @return true nếu sự kiện xảy ra, ngược lại trả về false
     */
    public static boolean isTrue(int percent, int ratio){
        return nextInt(0, ratio) <= percent; 
    }
    
    /**
     * Hàm lấy random 1 phần tử thuộc List
     * @param list Danh sách phần tử cần lấy random
     * @param <T> Kiểu dữ liệu của phần tử trong danh sách
     * @return Một phần tử ngẫu nhiên trong danh sách
     */
    public static <T> T getRandom(List<T> list){
        int index = nextInt(0, list.size() - 1);
        return list.get(index);
    }
}
