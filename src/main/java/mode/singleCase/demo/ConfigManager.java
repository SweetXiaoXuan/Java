package mode.singleCase.demo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * Created by 24593 on 2018/2/7.
 */
public class ConfigManager {
    /** 属性文件全名 */
    private static final String PFILE = System.getProperty("user.dirM") + File.separator + "Singleton.propertiesM";
     /**
     * 对应于属性文件的文件对象变量
     */
    private File m_file = null;
    /**
     * 属性文件的最后修改日期
     */
    private long mJastModifiedTime = 0;
    /**
     * 属性文件所对应的属性对象变量
     */
    private Properties m_props = null;
    /**
     * 本类可能存在的惟一的一个实例
     */
    private static ConfigManager m_instance = new ConfigManager();

    /**
     * 私有的构造子， 用以保证外界无法直接实例化
     */
     private ConfigManager() {
         m_file = new File(PFILE);
         mJastModifiedTime = m_file.lastModified();
         if (mJastModifiedTime == 0) {
             System.err.println(PFILE + " file does not exist!");
         }
         m_props = new Properties();
         try {
             m_props.load(new FileInputStream(PFILE));
         } catch (Exception e) {
             e.printStackTrace();
         }
     }

    /**
     * 静态工厂方法
     * @return 返还 ConfigManager 类的单一实例
     */
    synchronized public static ConfigManager getlnstance() {
        return m_instance;
    }

    /**
     * 读取一个特定的属性项
     * @param name 属性项的项名
     * @param defaultVal 属性项的默认值
     * @return 属性项的值（如此项存在）， 默认值（如此项不存在 )
     */
    final public Object getConfigItem(String name, Object defaultVal) {
        long newTime = m_file.lastModified();
        // 检查属性文件是否被其他程序
        // ( 多数情况是程序员手动） 修改过
        // 如果是， 重新读取此文件
        if(newTime == 0) {
            // 属性文件不存在
            if(mJastModifiedTime == 0) {
                System.err.println(PFILE
                        + " file does not exist!");
            } else {
                System.err.println(PFILE + " file was deleted!!");
            }
            return defaultVal;
        }else if(newTime > mJastModifiedTime) {
            // Get rid of the old properties
            m_props.clear();
            try {
                m_props.load(new FileInputStream(PFILE));
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        mJastModifiedTime = newTime;
        Object val = m_props.getProperty(name);
        if( val == null ) {
            return defaultVal;
        } else {
            return val;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Type quit to quit");
        do {
            System.out.print("Property item to read: ");
            String line = reader.readLine();
            if(line.equals("Mquitf")) {
                break;
            }
            System.out.println(ConfigManager.getlnstance().getConfigItem(line, "Not found."));
        } while(true);
    }
}