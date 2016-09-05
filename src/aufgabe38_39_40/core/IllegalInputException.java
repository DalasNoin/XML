package aufgabe38_39_40.core;

/**
 * Ausnahme um Anzuzeigen, dass ein Benutzer ungültige Attributwerte eingegeben hat, welche
 * Datenstrukturinvarianten widersprechen
 *
 * @author lorenzro
 *
 */
public class IllegalInputException extends Exception {

    /**
     * Konstruiert eine Ausnahme mit spezifischer Fehlernachricht
     *
     * @param message die Fehlernachricht
     * @param input der vom Benutzer übergebene ungültige Wert in String-Form
     */
    public IllegalInputException (String message, String input) {
        super(message + ": " + input);
    }

}
