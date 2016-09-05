package aufgabe38_39_40.persistence;

import aufgabe38_39_40.core.DatenContainer;
import aufgabe38_39_40.core.Film;
import aufgabe38_39_40.core.IllegalInputException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Textdatei implements Datenhaltung<Film> {

    private static final String ERR_DATA_FORMAT = "Dateiformat nicht lesbar";
    private static final String ERR_UNEXPECTED_EOF = "Unerwartetes Dateiende";
    private static final String ERR_FILE_NOT_FOUND = "Datei nicht gefunden";
    private static final String ERR_IO = "Fataler I/O-Fehler";

    private static final String FORMAT_START = "START";
    private static final String FORMAT_END = "ENDE";
    private static final String FORMAT_NEXT_ENTRY = "NEU";

    private String filename;

    private Textdatei(String filename) {
        this.filename = filename;
    }

    public static Datenhaltung<Film> instance(String datasource) throws LoadSaveException {
        return new Textdatei(datasource);
    }

    @Override
    public void load(DatenContainer<Film> container) throws LoadSaveException {
        BufferedReader reader = null;
        try{
            reader = new BufferedReader(new FileReader(filename));
            if(!this.FORMAT_START.equals(reader.readLine()))
                throw new LoadSaveException(this.ERR_DATA_FORMAT,null);
            String line = reader.readLine();
            String titel;
            String produktionsjahr;
            if (line == null)
                throw new LoadSaveException(ERR_UNEXPECTED_EOF, null);
            while(!this.FORMAT_END.equals(line)){
                try{
                if((titel = reader.readLine())== null)
                    throw new LoadSaveException(ERR_UNEXPECTED_EOF,null);
                if((produktionsjahr = reader.readLine()) == null)
                    throw new LoadSaveException(ERR_UNEXPECTED_EOF,null);
                int jahr = Integer.parseInt(produktionsjahr);
                Film film;
                if(jahr == 0)
                    film = new Film(titel);
                else
                    film = new Film(titel, jahr);
                container.link(film);
                } catch(IllegalInputException e){
                    throw new LoadSaveException("",null);
                } catch (NumberFormatException e) {
                    System.err.println("Film kann nicht geladen werden (nicht lesbares Produktionsjahr): " +
                        e.getMessage());
                }
            }
            
        } catch (FileNotFoundException e) {
            throw new LoadSaveException(ERR_FILE_NOT_FOUND, e);
        } catch (IOException e) {
            throw new LoadSaveException(ERR_IO, e);
        } finally {
            if(reader != null){
                try {
                    reader.close();
                } catch (IOException ex) {
                    
                }
            }
        }
    }

    @Override
    public void save(DatenContainer<Film> container) throws LoadSaveException {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(filename);
            writer.println(this.FORMAT_START);
            for (Film film : container) {
                writer.println(this.FORMAT_NEXT_ENTRY);
                writer.println(film.getTitel());
                writer.println(film.getProduktionsjahr());
            }
            writer.println(this.FORMAT_END);
        } catch (Exception e) {
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
