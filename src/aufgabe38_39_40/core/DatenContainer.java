package aufgabe38_39_40.core;

import aufgabe38_39_40.persistence.LoadSaveException;

public interface DatenContainer<T> extends Iterable<T> {

    void clear();
    void connect(String datasource) throws LoadSaveException;
    void disconnect();
    boolean isConnected();
    void link(T obj) throws LoadSaveException, IllegalInputException;
    void load() throws LoadSaveException;
    void save() throws LoadSaveException;
    void unlink(T obj) throws LoadSaveException, IllegalInputException;

}
