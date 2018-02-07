package mode.singleCase.registration.code;

import java.util.HashMap;

/**
 * Created by 24593 on 2018/2/7.
 */
public class RegSingleton {
    static private HashMap m_registry = new HashMap();
    static {
        RegSingleton x = new RegSingleton();
        m_registry.put(x.getClass().getName() , x);
    }

    /**
     * 保护的默认构造子
     */
    protected RegSingleton() { }

    /**
     * 静态工厂方法， 返还此类惟一的实例
     * @param name
     * @return
     */
    static public RegSingleton getInstance(String name) {
        if (name == null) {
            name = "mode.singleCase.registration.code.RegSingleton";
        }
        if (m_registry.get(name) == null) {
            try {
                m_registry.put(name, Class.forName(name).newInstance() ) ;
            } catch(Exception e) {
                System.out.println("Error happened.");
            }
        }
        return (RegSingleton) (m_registry.get(name));
    }

    /**
     * —个示意性的商业方法
     * @return
     */
    public String about() {
        return "Hello, I am RegSingleton.";
    }
}
