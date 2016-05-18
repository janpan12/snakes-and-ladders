
public class MoveTest {
    private int move(int start, int value) {
        if (Math.ceil((double) start / 10) % 2 != 0) {
            return increment(start, value);
        } else {
            return decrement(start, value);
        }
    }
    
    private int increment(int start, int value) {
        int index = start;
        for (int i = 0; i < value + 1; i++) {
            if (index == 91) return -1;
            if (index % 10 == 0 && index != 0) {
                int remainder = Math.abs(value - i);
                index += 10;
                for (int j = 0; j < remainder - 1; j++) {
                    if (index == 91) return -1;
                    index--;
                }
                return index;
            }
            index++;
        }
        return index;
    }
    
    private int decrement(int start, int value) {
        int index = start;
        for (int i = 0; i < value + 1; i++) {
            if (index == 91) return -1;
            if (index % 10 == 0 && index != 0) {
                int remainder = Math.abs(value - i);
                index += 10;
                for (int j = 0; j < remainder - 1; j++) {
                    if (index == 91) return -1;
                    index++;
                }
                return index;
            }
            index--;
        }
        return index;
    }
    public static void main(String[] args) {
        MoveTest t = new MoveTest();
        int index = t.move(0, 1);
        System.out.println(index);
    }
}
