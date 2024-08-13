package utils;

import java.util.GregorianCalendar;
import java.util.Random;

public class CommonUtils {
    private static final String CHAR_LIST = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String NUMBER_LIST = "0123456789";

    public static String getRandomFiveCharsString() {

        StringBuilder rndString = new StringBuilder();
        while (rndString.length() < 5) {
            int index = new Random().nextInt(CHAR_LIST.length());
            rndString.append(CHAR_LIST.charAt(index));
        }
        return rndString.toString();
    }

    public static String getRandomFiveCharsString(int length) {

        StringBuilder rndString = new StringBuilder();
        while (rndString.length() < length) {
            int index = new Random().nextInt(CHAR_LIST.length());
            rndString.append(CHAR_LIST.charAt(index));
        }
        return rndString.toString().toString();
    }

    public static String getRandomLimitedSpecialCharacter(int length) {
        String specialChars = "!@#$%^&";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; ++i) {
            sb.append(specialChars.charAt(random.nextInt(specialChars.length())));
        }
        return sb.toString();
    }

    public static String getRandomAllSpecialCharacter(int length) {
        String specialChars = "!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; ++i) {
            sb.append(specialChars.charAt(random.nextInt(specialChars.length())));
        }
        return sb.toString();
    }


    public static String getRandomNumber(int length) {

        String candidateChars = "0123456789";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(candidateChars.charAt(random.nextInt(candidateChars
                    .length())));
        }

        return sb.toString();
    }

    public static String getRandomBirthDate() {

        GregorianCalendar gc = new GregorianCalendar();

        int year = randBetween(1900, 2010);
        gc.set(gc.YEAR, year);
        int dayOfYear = randBetween(1, gc.getActualMaximum(gc.DAY_OF_YEAR));
        gc.set(gc.DAY_OF_YEAR, dayOfYear);
        return gc.get(gc.YEAR) + "-" + (gc.get(gc.MONTH) + 1) + "-" + gc.get(gc.DAY_OF_MONTH);
    }
    private static int randBetween(int start, int end) {
        return start + (int) Math.round(Math.random() * (end - start));
    }
}
