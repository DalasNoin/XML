package aufgabe38_39_40.persistence;
/**
 * Ausnahme um Anzuzeigen, dass es beim Datenzugriff über
 * die Datenhaltungsschicht zu einem Fehler gekommen ist
 *
 * @author lorenzro
 *
 */
public class LoadSaveException extends Exception {

    /**
     * Konstruiert eine Ausnahme mit spezifischer Fehlernachricht
     *
     * @param message die Fehlernachricht
     * @param e ein ggf. vorhandenes Exception-Objekt, das die Ausnahme ausgelöst hat
     */
    public LoadSaveException (String message, Exception e) {
        super(message,e);
    }

    /**
     * Gibt eine detaillierte Fehlernachricht zu einem
     * ggf. vorhandenen Exception-Objekt, das die Ausnahme ausgelöst hat
     * und weitergereicht wurde, zurück
     *
     * @return eine detaillierte Fehlernachricht
     */
    public String details() {
        if (getCause() == null)
            return "no details";
        else
            return getCause().getClass() + ", " + getCause().getMessage();
    }
}