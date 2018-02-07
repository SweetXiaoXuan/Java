package mode.factory.abstractF.code;

/**
 * Created by 24593 on 2018/2/7.
 */
public class Test {
    private static Creator creator1,creator2;
    private static ProductA productA1, productA2;
    private static ProductBC productBC1, productBC2;
    public static void main(String[] args) {
        creator1 = new ConcreteCreatorl();
        creator2 = new ConcreteCreator2();

        productA1 = creator1.factoryA();
        productA2 = creator2.factoryA();

        productBC1 = creator1.factoryBC();
        productBC2 = creator2.factoryBC();
    }
}
