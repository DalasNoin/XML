package aufgabe38_39_40.persistence;

import aufgabe38_39_40.core.DatenContainer;

public interface Datenhaltung<T> {

    void add(T obj) throws LoadSaveException;
    void close();
    void delete(T obj) throws LoadSaveException;
    void load(DatenContainer<T> container) throws LoadSaveException;
    void modify(T obj) throws LoadSaveException;
    void save(DatenContainer<T> container) throws LoadSaveException;

}
