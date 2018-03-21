package mode.multipleCases.limitedMultipleCases.code;

/**
 * Created by 24593 on 2018/3/21.
 */
public class Client {
    private static Die diel, die2;

    public static void main(String[] args) {
        diel = Die.getlnstance(1);
        die2 = Die.getlnstance(2);
        diel.dice();
        die2.dice();
    }
}
