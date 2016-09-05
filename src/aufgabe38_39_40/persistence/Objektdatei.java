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

public class Objektdatei implements Datenhaltung<Film> {

    private String filename;

    private Objektdatei(String filename) {
        this.filename = filename;
    }

    public static Datenhaltung instance(String datasource) throws LoadSaveException {
        return new Objektdatei(datasource);
    }

    @Override
    public void load(DatenContainer<Film> container) throws LoadSaveException {
        ObjectInputStream stream = null;
        try{
            stream = new ObjectInputStream(new FileInputStream(filename));
            Object data = stream.readObject();
            if(!(data instanceof DatenContainer))
                throw new LoadSaveException("Ausgewaehlte Datei kann nicht geladen werden", null);
            DatenContainer temp = (DatenContainer) data;
            for(Object tmp : temp){
                if(!(tmp instanceof Film))
                    throw new LoadSaveException("Ausgewaehlte Datei kann nicht geladen werden", null);
                Film film = (Film) tmp;
                try{
                    container.link(film);
                } catch (IllegalInputException e) {
                    System.err.println("Film kann nicht geladen werden (ungültige Daten): " +
                        e.getMessage());
                }
                
            }
        }catch (Exception e){
            
        } finally {
            if(stream != null)
                try{
                    stream.close();
                }catch(Exception e){
                    
                }
        }
    }

    @Override
    public void save(DatenContainer<Film> container) throws LoadSaveException {
        ObjectOutputStream stream = null;
        try{
            stream = new ObjectOutputStream(new FileOutputStream(filename));
            stream.writeObject(container);
            
        } catch (Exception e){
            throw new LoadSaveException("Speichern fehlgeschlagen",e);
        } finally{
            if(stream != null)
                try{
                    stream.close();
                } catch(Exception e){
                    
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
