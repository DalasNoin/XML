package aufgabe38_39_40.main;

import aufgabe38_39_40.core.Film;
import aufgabe38_39_40.core.FilmContainer;
import aufgabe38_39_40.core.IllegalInputException;
import aufgabe38_39_40.persistence.LoadSaveException;

public class Aufgabe39 {

    public static void main(String args[]) {
        FilmContainer fcon = FilmContainer.instance();
        try {
            fcon.connect("a39.txt");
            fcon.load();
            for (Film f : fcon)
                System.out.println(f);
            Film film = new Film("No Country for Old Men", 2007);
            fcon.link(film);
            film = new Film("Barry Lyndon");
            fcon.link(film);
            fcon.save();
        } catch (LoadSaveException | IllegalInputException e) {
            e.printStackTrace();
        } finally {
            fcon.disconnect();
        }
    }

}
