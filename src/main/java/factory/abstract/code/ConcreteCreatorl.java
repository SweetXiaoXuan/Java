package factory.abstract.code;

public class ConcreteCreatorl implements Creator {
    /**
     产 品 等 级 结 构 A 的工厂方法
    */
    public ProductA factoryA() {
        return new ProductA1();
    }

    /**
        产 品 等 级 结 构 B 的工厂方法
    */
    public ProductB factoryB() {
        return new ProductB1();
    }
}