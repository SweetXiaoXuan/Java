package mode.singleCase.lazy.code;

/**
 * Created by 24593 on 2018/2/7.
 */
public class LazySingleton {
    private static LazySingleton m_instance = null;

    /**
     * 私有的默认构造子， 保证外界无法直接实例化
     */
    private LazySingleton() { }

    /**
     * 静态工厂方法， 返还此类的惟一实例
     * @return
     */
    synchronized public static LazySingleton getlnstance() {
        if (m_instance == null) {
            m_instance = new LazySingleton();
        }
        return m_instance;
    }
}
