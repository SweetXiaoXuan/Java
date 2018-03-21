package mode.builder.code;

import mode.factory.method.code.Product;

/**
 * Created by 24593 on 2018/3/21.
 */
public abstract class Builder {
    /**
     * 产品零件建造方法
     */
    public abstract void buildPart1();

    /**
     * 产品零件建造方法
     */
    public abstract void buildPart2();

    /**
     * 产品返还方法
     * @return
     */
    public abstract Product retrieveResult();
}
