package mode.multipleCases.limitedMultipleCases.code;

import java.util.Date;
import java.util.Random;

/**
 * Created by 24593 on 2018/3/21.
 */
public class Die {
    private static Die diel = new Die();
    private static Die die2 = new Die();

    /**
     * 私有的构造子保证外界无法
     * 直接将此类实例化
     */
    private Die() {
    }

    /**
     * 工厂方法
     *
     * @param whichOne
     * @return
     */
    public static Die getlnstance(int whichOne) {
        if (whichOne == 1) {
            return diel;
        } else {
            return die2;
        }
    }

    /**
     * 掷骰子， 返还一个在 1 6 之间的随机数
     *
     * @return
     */
    public synchronized int dice() {
        Date d = new Date();
        Random r = new Random(d.getTime());
        int value = r.nextInt();
        value = Math.abs(value);
        value = value % 6;
        value += 1;
        return value;
    }
}
