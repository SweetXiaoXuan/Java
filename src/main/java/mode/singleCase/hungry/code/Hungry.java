package mode.singleCase.hungry.code;

/**
 * Created by 24593 on 2018/2/7.
 */
public class Hungry {
    private static final Hungry mjnstance = new Hungry();
    /**
     * 私有的默认构造子
     */
    private Hungry() { }

    /**
     * 静态工厂方法
     * @return
     */
    public static Hungry getlnstance() {
        return mjnstance;
    }
}
