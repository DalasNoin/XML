package aufgabe38_39_40.persistence;

import aufgabe38_39_40.core.DatenContainer;
import aufgabe38_39_40.core.Film;
import aufgabe38_39_40.core.IllegalInputException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class Textdatei_1 implements Datenhaltung<Film> {

    private static final String ERR_DATA_FORMAT = "Dateiformat nicht lesbar";
    private static final String ERR_UNEXPECTED_EOF = "Unerwartetes Dateiende";
    private static final String ERR_FILE_NOT_FOUND = "Datei nicht gefunden";
    private static final String ERR_IO = "Fataler I/O-Fehler";

    private static final String FORMAT_START = "START";
    private static final String FORMAT_END = "ENDE";
    private static final String FORMAT_NEXT_ENTRY = "NEU";

    private String filename;

    private Textdatei_1(String filename) {
        this.filename = filename;
    }

    public static Datenhaltung<Film> instance(String datasource) throws LoadSaveException {
        return new Textdatei_1(datasource);
    }

    @Override
    public void load(DatenContainer<Film> container) throws LoadSaveException {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(filename));
            if (!FORMAT_START.equals(reader.readLine()))
                throw new LoadSaveException(ERR_DATA_FORMAT, null);
            String line = reader.readLine();
            if (line == null)
                throw new LoadSaveException(ERR_UNEXPECTED_EOF, null);
            while (!FORMAT_END.equals(line)) {
                if (!FORMAT_NEXT_ENTRY.equals(line))
                    throw new LoadSaveException(ERR_DATA_FORMAT, null);
                try {
                    String titel = reader.readLine();
                    if (titel == null)
                        throw new LoadSaveException(ERR_UNEXPECTED_EOF, null);
                    line = reader.readLine();
                    if (line == null)
                        throw new LoadSaveException(ERR_UNEXPECTED_EOF, null);
                    int produktionsjahr = Integer.parseInt(line);
                    Film film;
                    if (produktionsjahr == 0)
                        film = new Film(titel);
                    else
                        film = new Film(titel, produktionsjahr);
                    container.link(film);
                } catch (IllegalInputException e) {
                    System.err.println("Film kann nicht geladen werden (ungueltige Daten): " +
                        e.getMessage());
                } catch (NumberFormatException e) {
                    System.err.println("Film kann nicht geladen werden (nicht lesbares Produktionsjahr): " +
                        e.getMessage());
                }
                line = reader.readLine();
                if (line == null)
                    throw new LoadSaveException(ERR_UNEXPECTED_EOF, null);
            }
        } catch (FileNotFoundException e) {
            throw new LoadSaveException(ERR_FILE_NOT_FOUND, e);
        } catch (IOException e) {
            throw new LoadSaveException(ERR_IO, e);
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
    public void save(DatenContainer<Film> container) throws
        LoadSaveException {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(filename);
            writer.println(FORMAT_START);
            for (Film film : container) {
                writer.println(FORMAT_NEXT_ENTRY);
                writer.println(film.getTitel());
                writer.println(film.getProduktionsjahr());
            }
            writer.println(FORMAT_END);
        } catch (FileNotFoundException e) {
            throw new LoadSaveException(ERR_FILE_NOT_FOUND, e);
        } finally {
            writer.close();
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
