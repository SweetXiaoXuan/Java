package factory.method.code;

/**
 * Created by 24593 on 2018/1/31.
 */
public class ConcreteCreator2 implements Creator {
    public Product factory() {
        return new ConcreteProduct2();
    }
}
