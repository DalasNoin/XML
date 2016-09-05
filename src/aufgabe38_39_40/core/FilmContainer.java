package aufgabe38_39_40.core;

import aufgabe38_39_40.persistence.Datenhaltung;
import aufgabe38_39_40.persistence.LoadSaveException;
import aufgabe38_39_40.persistence.Textdatei;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class FilmContainer extends Observable implements DatenContainer<Film> {

    private static final long serialVersionUID = 2384234234231L;

    protected static FilmContainer unique = null;

    private List<Film> allElements = new ArrayList<>();

    private boolean connected = false;
    private boolean isLoading = false;
    private Datenhaltung<Film> store = null;

    public static FilmContainer instance() {
        if (unique == null)
            unique = new FilmContainer();
        return unique;
    }

    private FilmContainer() {
        /* --- */
    }

    @Override
    public void clear() {
        allElements.clear();
        setChanged();
        notifyObservers();
    }

    @Override
    public void connect(String datasource) throws LoadSaveException {
        store = Textdatei.instance(datasource);
        if (store == null)
            throw new LoadSaveException("Keine Verbindung zur Datenquelle!", null);
        connected = true;
    }

    @Override
    public void disconnect() {
        if (store != null) {
            store.close();
            store = null;
            connected = false;
        }
    }

    public boolean isConnected() {
        return connected;
    }
    
    @Override 
    public void addObserver(Observer o){
        super.addObserver(o);
    }

    @Override
    public Iterator<Film> iterator() {
        return allElements.iterator();
    }

    public void link(Film obj) throws LoadSaveException, IllegalInputException {
        if (obj == null)
            throw new IllegalInputException("Ungueltiger Wert", "null");
        if (allElements.contains(obj))
            throw new IllegalInputException("Objekt schon vorhanden!", obj.toString());
        if (!isLoading) {
            if (store == null)
                throw new LoadSaveException("Keine Verbindung zur Datenquelle!", null);
            store.add(obj);
        }
        allElements.add(obj);
        setChanged();
        notifyObservers();
    }

    @Override
    public void load() throws LoadSaveException {
        if (store == null)
            throw new LoadSaveException("Keine Verbindung zur Datenquelle!", null);
        isLoading = true;
        store.load(this);
        isLoading = false;
    }

    @Override
    public void save() throws LoadSaveException {
        if (store == null)
            throw new LoadSaveException("Keine Verbindung zur Datenquelle!", null);
        store.save(this);
    }

    @Override
    public void unlink(Film obj) throws LoadSaveException, IllegalInputException {
        if (obj == null)
            throw new IllegalInputException("Ungueltiger Wert", "null");
        if (!allElements.contains(obj))
            throw new IllegalInputException("Objekt nicht vorhanden!", obj.toString());
        if (store == null)
            throw new LoadSaveException("Keine Verbindung zur Datenquelle!", null);
        store.delete(obj);
        allElements.remove(obj);
        setChanged();
        notifyObservers();
    }

}
