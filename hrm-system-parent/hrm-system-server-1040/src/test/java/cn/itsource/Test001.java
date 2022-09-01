package cn.itsource;

public class Test001 {

    /**
     * 装箱：int - Double valueOf
     * 拆箱：Double - int
     */
    public static void main(String[] args) {
        Double i1 = 100.00;//[-127,128]
        Double i2 = 100.00;
        Double i3 = 200.00;
        Double i4 = 200.00;

        System.out.println(i1 == i2);//false true
        System.out.println(i3 == i4);//false false
    }
}