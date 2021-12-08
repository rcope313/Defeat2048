package utility;

import javalib.worldimages.Posn;
import models.square.Square;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Utility {

    public static Square[] reverseArray(Square[] rowArray) {
        return rowArray;
    }

    public static ArrayList<Posn> createRandomPosns() {
        ArrayList<Posn> result;
        Random r = new Random();

        Posn p1 = new Posn(r.nextInt(4), r.nextInt(4));
        Posn p2 = new Posn(r.nextInt(4), r.nextInt(4));

        if (p1.equals(p2)) {
            result = createRandomPosns();
        } else {
            result = new ArrayList<>(Arrays.asList(p1, p2));
        }

        return result;
    }
}
