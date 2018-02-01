package factory.method;

/**
 * Created by 24593 on 2018/1/31.
 */
public class Client {
    private static Creator creator1, creator2;
    private static Product prodl, prod2;
    public static void main(String[] args) {
        creator1 = new ConcreteCreatorl();
        prodl = creator1.factory();
        creator2 = new ConcreteCreator2();
        prod2 = creator2.factory();
    }
}
