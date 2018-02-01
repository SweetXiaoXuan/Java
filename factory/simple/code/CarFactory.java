package factory.simple;

/**
 * Created by 24593 on 2018/1/28.
 */
public class CarFactory {
    public static Car createCar(int type){
        if (type==1) {
            return new BigCar();
        }
        if (type==2) {
            return new SmallCar();
        }
        return null;
    }
}
