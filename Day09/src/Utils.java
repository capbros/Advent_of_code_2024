public abstract class Utils {
    public static FlexibleInt sumInt(long start, long end) {
        long other;
        FlexibleInt flexibleInt;
        FlexibleInt res;
        if (end % 2 == 0) {
            res = new FlexibleInt(end-1);
            res.mul(end/2);
        } else {
            res = new FlexibleInt(end);
            res.mul((end-1)/2);
        } if (start % 2 == 0) {
            flexibleInt = new FlexibleInt(start-1);
            flexibleInt.mul(start/2);
        } else {
            flexibleInt = new FlexibleInt(start);
            flexibleInt.mul((start-1)/2);
        }
        res.sub(flexibleInt);
        return res;
    }
    public static long sumAllInt(long start, long end) {
        return (end*(end-1))/2 - (start*(start-1))/2;
    }
}
