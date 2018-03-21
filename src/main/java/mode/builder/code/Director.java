package mode.builder.code;

/**
 * Created by 24593 on 2018/3/21.
 */
public class Director {
    private Builder builder;

    /**
     * 产品构造方法， 负责调用各个零件建造方法
     */
    public void construct() {
        builder = new ConcreteBuilder();
        builder.buildPart1();
        builder.buildPart2();
        builder.retrieveResult();
        //continue with other code
    }
}
