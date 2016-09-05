package aufgabe38_39_40.persistence;

import aufgabe38_39_40.core.DatenContainer;
import aufgabe38_39_40.core.Film;
import aufgabe38_39_40.core.IllegalInputException;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

public class Objektdatei_1 implements Datenhaltung<Film> {

    private String filename;

    private Objektdatei_1(String filename) {
        this.filename = filename;
    }

    public static Datenhaltung<Film> instance(String datasource) throws LoadSaveException {
        return new Objektdatei_1(datasource);
    }

    @Override
    public void load(DatenContainer<Film> container) throws LoadSaveException {
        ObjectInputStream reader = null;
        try {
            reader = new ObjectInputStream(new FileInputStream(filename));
            Object data = reader.readObject();
            if (!(data instanceof DatenContainer))
                throw new LoadSaveException("Ausgewaehlte Datei kann nicht geladen werden", null);
            DatenContainer temp = (DatenContainer) data;
            for (Object tmp : temp) {
                if (!(tmp instanceof Film))
                    throw new LoadSaveException("Ausgewaehlte Datei kann nicht geladen werden", null);
                Film film = (Film) tmp;
                try {
                    container.link(film);
                } catch (IllegalInputException e) {
                    System.err.println("Film kann nicht geladen werden (ungültige Daten): " +
                        e.getMessage());
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new LoadSaveException("Fataler Fehler beim Dateizugriff!", e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    /* -- */
                }
            }
        }
    }

    @Override
    public void save(DatenContainer<Film> container) throws LoadSaveException {
        ObjectOutputStream writer = null;
        try {
            writer = new ObjectOutputStream(new FileOutputStream(filename));
            writer.writeObject(container);
        } catch (IOException ex) {
            throw new LoadSaveException("Speichern fehlgeschlagen", ex);
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch(IOException e) {
                    /* -- */
                }
            }
        }
    }

    @Override
    public void add(Film film) throws LoadSaveException {
        /* -- */
    }

    @Override
    public void delete(Film film) throws LoadSaveException {
        /* -- */
    }

    @Override
    public void modify(Film film) throws LoadSaveException {
        /* -- */
    }

    @Override
    public void close() {
        /* -- */
    }

}
