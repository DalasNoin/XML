package aufgabe38_39_40.main;

import aufgabe38_39_40.core.Film;
import aufgabe38_39_40.core.FilmContainer;
import aufgabe38_39_40.core.IllegalInputException;
import aufgabe38_39_40.persistence.LoadSaveException;

public class Aufgabe40 {

    public static void main(String args[]) {
        FilmContainer fcon = FilmContainer.instance();
        try {
            fcon.connect("a40.bin");
            fcon.load();
            for (Film f : fcon)
                System.out.println(f);
            Film film = new Film("Pulp Fiction", 1994);
            fcon.link(film);
            film = new Film("Das weisse Band");
            fcon.link(film);
            fcon.save();
        } catch (LoadSaveException | IllegalInputException e) {
            System.err.println(e.getMessage());
        } finally {
            fcon.disconnect();
        }
    }

}
