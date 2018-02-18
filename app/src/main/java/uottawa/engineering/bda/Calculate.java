package uottawa.engineering.bda;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Daviiiiid on 2018-02-18.
 * Just put all calculations here
 */
public class Calculate {
    /**
     * Retourne le temps estimé total
     * @param ticketNo Le billet de l'utilisateur
     * @param current Le numéro en train de se faire servir
     * @param averageTime Le temps d'attente moyen d'un individu.
     * @return
     */
    public static String getTime (String ticketNo, String current, WaitTime averageTime){
        String time = "0:00";
        char[] ticketLetter = reverseCharArray(ticketNo.replaceAll("\\d", "").toCharArray());
        char[] currentLetter = reverseCharArray(current.replaceAll("\\d", "").toCharArray());
        // trouve les numéros d'extra entre les lettres
        int letter = charDiffrence(currentLetter, ticketLetter, ticketNo.length() - ticketLetter.length);
        // Ajout de 0 pour ne pas avoir un string vide et on divise par 10 ensuite.
        int ticketDigit = Integer.parseInt(ticketNo.replaceAll("\\D","") + "0") / 10 + letter;
        int currentDigit = Integer.parseInt(current.replaceAll("\\D","") + "0") / 10;
        int numberOfTickets = ticketDigit - currentDigit;
        int minutes = (averageTime * numberOfTickets)%60;
        int heures =averageTime * numberOfTickets + (averageTime * numberOfTickets)/60;
        time = heures + ":" + minutes;
        return time;
    }

    public static char[] reverseCharArray(char[] c) {
        char[] reversed = new char[c.length];
        for (int i = 0; i<c.length; i++) {
            reversed[c.length - i - 1] = c[i];
        }
        return reversed;
    }

    public static int charDiffrence (char[] low, char[] high, int multiplier) {
        int num = 0;
        for (int i = 0; i < high.length; i++) {
            num += (high[i]-low[i]) * (int)Math.pow(10,i);
        }
        return num * multiplier;
    }
}