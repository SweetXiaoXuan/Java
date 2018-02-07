package factory.method.code;

import factory.method.ConcreteProduct1;
import factory.method.Creator;
import factory.method.Product;

/**
 * Created by 24593 on 2018/1/31.
 */
public class ConcreteCreatorl implements Creator {
    public Product factory() {
        return new ConcreteProduct1();
    }
}
