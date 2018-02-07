package mode.singleCase.registration.code;

/**
 * Created by 24593 on 2018/2/7.
 */
public class RegSingletonChild extends RegSingleton {
    public RegSingletonChild() {}

    /**
     * 静 态 工 厂 方 法
     * @return
     */
    static public RegSingletonChild getlnstance() {
        return(RegSingletonChild) RegSingleton.getInstance("mode.singleCase.registration.code.RegSingletonChild");
    }

    /**
     * 一个示意性的商业方法
     */
    public String about() {
        return "Hello，I am RegSingletonChild.";
    }
}
