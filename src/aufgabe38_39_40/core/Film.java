package aufgabe38_39_40.core;

import java.io.Serializable;

public class Film {

    private static final long serialVersionUID = 23842340987523L;

    private int produktionsjahr;
    private String titel;

    public Film(String titel) throws IllegalInputException {
        setTitel(titel);
    }

    public Film(String titel, int produktionsjahr) throws IllegalInputException {
        this(titel);
        setProduktionsjahr(produktionsjahr);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (o == null)
            return false;
        if (!o.getClass().equals(this.getClass()))
            return false;
        Film tmp = (Film) o;
        return (tmp.getTitel().equals(titel) &&
            tmp.getProduktionsjahr() == produktionsjahr);
    }

    public int getProduktionsjahr() {
        return produktionsjahr;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) throws IllegalInputException {
        if (titel == null || titel.length() > 40)
            throw new IllegalInputException("Filmtitel darf nicht mehr als 20 Zeichen haben!", titel);
        this.titel = titel;
    }

    public void setProduktionsjahr(int produktionsjahr) throws IllegalInputException {
        if (produktionsjahr <= 0)
            throw new IllegalInputException("Produktionsjahr darf nicht kleiner oder gleich 0 sein!",
                String.valueOf(produktionsjahr));
        this.produktionsjahr = produktionsjahr;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(titel + " (");
        if (produktionsjahr == 0)
            sb.append("?");
        else
            sb.append(produktionsjahr);
        sb.append(")");
        return sb.toString();
    }

}
