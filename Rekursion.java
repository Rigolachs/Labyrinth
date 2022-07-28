import java.io.*;
import java.util.Scanner;

/**
 * Die Klasse Rekursion arbeitet mit dem Backtracking-Verfahren, um einen Weg
 * ('.') aus einem Labyrinth zu finden oder um festzustellen, dass keiner
 * existiert. Das Labyrinth muss folgende Kritieren erfuellen: zusammenhaengend,
 * quadratisch (Zeilen gleichlang), durch '|' oder '-' umrandet, genau einen
 * Ziel- ('Z') und Startpunkt ('S')
 */
public class Rekursion {
    /**
     * Klassenvariable (String Array) fuer das Labyrinth (Standard-Groesse 100)
     */
    private static String[] feld = new String[100];
    /**
     * Spalte des Startpunktes
     */
    private static int cS;
    /**
     * Reihe des Startpunktes
     */
    private static int rS;
    /**
     * Gibt Variable Feld aus
     */
    private static void labyrinthAusgabe() {
        //Fuer Ausgabe wird '.' durch 'S' ersetzt
        labyrinthSetzen(cS, rS, 'S');
        for (int c = 0; c <= 8; c++) {
            System.out.println(feld[c]);
        }
        //'S' wird wieder zu '.'
        labyrinthSetzen(cS, rS, '.');
    }
    /**
     * Aendert ein Char im Feld
     * @param c integer column
     * @param r integer row
     * @param ch Zeichen, das an der Stelle [c][r] gesetzt werden soll
     */
    private static void labyrinthSetzen(int c, int r, char ch) {
        // 'Z' darf nicht uberschrieben werden
        if (feld[c].charAt(r) != 'Z') {
            char[] sC = feld[c].toCharArray();
            sC[r] = ch;
            feld[c] = String.valueOf(sC);
        }
    }
    /**
     * Rekursive Funktion, die das Labyrinth loest
     * @param c integer column
     * @param r integer row
     * @param feedback Gibt Rueckschluesse auf die Operationen
     * @return boolean ist wahr, wenn Labyrinth loesbar
     */
    public static boolean labyrinth(int c, int r, String feedback) {
        char position = feld[c].charAt(r); // Zwischenspeichern
        if (position == 'Z') {
            labyrinthAusgabe(); //Finale Ausgabe
            return true; // Loesung; auf False setzen fuer alle Loesungswege
        }
        System.out.println(feedback); //Rekursionsausgaben
        // Waende und bereits besuchte Felder werden gemieden
        if ((position != '|') && (position != '-') && (position != '.')) {
            labyrinthSetzen(c, r, '.'); // Besucht
            labyrinthAusgabe(); //Zwischenausgabe
            if (labyrinth(c + 1, r, "Nach unten")) {
                return true;
            }
            if (labyrinth(c, r + 1, "Nach rechts")) {
                return true;
            }
            if (labyrinth(c - 1, r, "Nach oben")) {
                return true;
            }
            if (labyrinth(c, r - 1, "Nach links")) {
                return true;
            }
            // Ab diesem Punkt wird systematisch zurueckgegangen bei Sackgassen
            System.out.println("Backtracking!");
            labyrinthSetzen(c, r, ' '); // Durch Backtracking verlassenen Punkt rueckgaening machen
            labyrinthAusgabe(); //Zwischenausgabe
        }
        return false;
    }
    /**
     * Main-Methode
     * @param args unbenutzt
     */
    public static void main(String[] args) {
        int zeilen = 0;        
        try {
        	FileInputStream file = new FileInputStream(System.getProperty("user.dir") + "\\labyrinth.txt");
            Scanner s = new Scanner(file);
            while (s.hasNextLine()) {
                String line = s.nextLine();
                feld[zeilen] = line;
                zeilen++;
            }
            file.close();
        } catch (IOException e) {
            System.out.println("Fehler");
        }
        for (int c = 1; c <= zeilen - 1; c++) {
            for (int r = 1; r < feld[c].length(); r++) {
                if (feld[c].charAt(r) == 'S') {
                    rS = c;
                    cS = r;
                    labyrinthSetzen(cS, rS, ' ');
                    System.out.println((labyrinth(cS, rS, "Loese labyrinth")) 
                            ? "Ziel Erreicht!" : "Keinen Weg gefunden!");
                }
            }
        }
    }
}
