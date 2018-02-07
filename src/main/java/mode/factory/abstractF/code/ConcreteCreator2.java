package mode.factory.abstractF.code;

public class ConcreteCreator2 implements Creator {
    /**
     * 产 品 等 级 结 构 A 的工厂方法
     */
    public ProductA factoryA() {
        return new ProductA2();
    }

    /**
        产 品
        等 级
        结 构
        B 的工厂方法
    */
    public ProductBC factoryBC() {
        return new ProductBC2();
    }
}