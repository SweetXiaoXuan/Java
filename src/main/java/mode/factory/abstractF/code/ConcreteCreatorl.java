package mode.factory.abstractF.code;

public class ConcreteCreatorl implements Creator {
    /**
     产 品 等 级 结 构 A 的工厂方法
    */
    public ProductA factoryA() {
        return new ProductAl();
    }

    /**
        产 品 等 级 结 构 B 的工厂方法
    */
    public ProductBC factoryBC() {
        return new ProductBC1();
    }
}