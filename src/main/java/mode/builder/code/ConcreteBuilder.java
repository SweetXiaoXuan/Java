package mode.builder.code;

import mode.factory.method.code.Product;

/**
 * Created by 24593 on 2018/3/21.
 */
public class ConcreteBuilder extends Builder {
    private Product product = new Product();

    /**
     * 产品返还方法
     *
     * @return
     */
    @Override
    public Product retrieveResult() {
        return product;
    }

    /**
     * 产品零件建造方法
     */
    public void buildPart1() {
        //build the first part of the product
    }

    /**
     * 产品零件建造方法
     */
    public void buildPart2() {
        //build the second part of the product
    }
}
