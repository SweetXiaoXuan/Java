package factory.simple;

/**
 * Created by 24593 on 2018/1/28.
 */
public class Test {
    public static void main(String[] args) {
        Car smallCar = CarFactory.createCar(2);
        smallCar.run();

        Car bigCar = CarFactory.createCar(1);
        bigCar.run();
    }
}
