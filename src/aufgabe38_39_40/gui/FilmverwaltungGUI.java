package aufgabe38_39_40.gui;

import aufgabe38_39_40.core.DatenContainer;
import aufgabe38_39_40.core.Film;
import aufgabe38_39_40.core.FilmContainer;
import aufgabe38_39_40.persistence.LoadSaveException;

import java.awt.BorderLayout;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FilmverwaltungGUI extends Frame implements ActionListener, Observer {

    private static final String CMD_QUIT = "Beenden";
    private static final String CMD_CONTAINER_NEW = "Neue Filmliste beginnen";
    private static final String CMD_FILM_NEW = "Neuen Film anlegen";
    private static final String CMD_FILM_LOAD = "Filme laden";
    private static final String CMD_FILM_SAVE = "Filme speichern";
    private static final String CMD_FILM_SAVE_AS = "Filme speichern unter";
    private static final String CMD_FILM_LIST = "Alle Filme anzeigen";

    private static final String LBL_ANZEIGE_INIT = "Daten laden oder mit neuer Datei starten";
    private static final String LBL_ANZEIGE_SAVED = "Daten aktuell gespeichert";
    private static final String LBL_ANZEIGE_NOT_SAVED = "Änderungen aktuell NICHT gespeichert";
    private static final String LBL_ANZEIGE_NO_DATA = "Keine Daten vorhanden";
    private static final String LBL_ANZEIGE_NO_FILE = "Keine Datei ausgewählt";

    private boolean saved = true;
    private String filename;
    private FilmContainer container;

    private Label lblAnzeige;

    public FilmverwaltungGUI() {
        super("Filmverwaltung");
        MenuBar menuBar = new MenuBar();
        this.setMenuBar(menuBar);

        Menu mNeu = new Menu("Neu");
        menuBar.add(mNeu);
        MenuItem miAnlegen = new MenuItem(CMD_FILM_NEW);
        miAnlegen.addActionListener(this);
        mNeu.add(miAnlegen);
        MenuItem miList = new MenuItem(CMD_FILM_LIST);
        miList.addActionListener(this);
        mNeu.add(miList);

        Menu mDatei = new Menu("Datei");
        menuBar.add(mDatei);
        MenuItem miLaden = new MenuItem(CMD_FILM_LOAD);
        miLaden.addActionListener(this);
        mDatei.add(miLaden);
        MenuItem miSpeichern = new MenuItem(CMD_FILM_SAVE);
        miSpeichern.addActionListener(this);
        mDatei.add(miSpeichern);
        MenuItem miSpeichernUnter = new MenuItem(CMD_FILM_SAVE_AS);
        miSpeichernUnter.addActionListener(this);
        mDatei.add(miSpeichernUnter);
        MenuItem miNeueFilmliste = new MenuItem(CMD_CONTAINER_NEW);
        miNeueFilmliste.addActionListener(this);
        mDatei.add(miNeueFilmliste);

        lblAnzeige = new Label("Filmverwaltung");
        lblAnzeige.setText(LBL_ANZEIGE_INIT);
        this.add(lblAnzeige, BorderLayout.CENTER);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                onBeenden();
            }
        });

        pack();
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case CMD_FILM_NEW:
                onErfassen();
                break;
            case CMD_CONTAINER_NEW:
                onNeu();
                break;
            case CMD_FILM_LOAD:
                onLaden();
                break;
            case CMD_FILM_SAVE:
                onSpeichern();
                break;
            case CMD_FILM_SAVE_AS:
                onSpeichernUnter();
                break;
            case CMD_FILM_LIST:
                onListe();
                break;
            case CMD_QUIT:
                onBeenden();
                break;
        }
    }

    public void update(Observable o, Object arg) {
        lblAnzeige.setText(LBL_ANZEIGE_NOT_SAVED);
        saved = false;
    }

    private void onErfassen() {
        /*
        if (container != null)
            new FilmAnlegenGUI(this);
        else
         */
        lblAnzeige.setText(LBL_ANZEIGE_NO_DATA);
    }

    private void onListe() {
        /*
        if (container != null)
            new FilmListeGUI(this);
        else
         */
        lblAnzeige.setText(LBL_ANZEIGE_NO_DATA);
    }

    private void onBeenden() {
        if (container != null && !saved) {
            lblAnzeige.setText(LBL_ANZEIGE_NOT_SAVED);
            onSpeichern();
        }
        dispose();
        System.exit(0);
    }

    private void onLaden() {
        if(!saved && container != null){
            lblAnzeige.setText(LBL_ANZEIGE_NOT_SAVED);
            return;
        } 
        if(container == null){
            container = FilmContainer.instance();
            container.addObserver(this);
        }
        container.clear();
        FileDialog fd = new FileDialog(this,"Laden",FileDialog.LOAD);
        fd.setVisible(true);
        if(fd.getFile() != null){
            filename = fd.getDirectory() + fd.getFile();
            try {
                container.connect(filename);
                container.load();
                lblAnzeige.setText(this.LBL_ANZEIGE_SAVED);
                saved = true;
            } catch (LoadSaveException ex) {
                lblAnzeige.setText(ex.getMessage() + ex.details());
            }
        } else {
            lblAnzeige.setText(this.LBL_ANZEIGE_NO_FILE);
        }
        
        
    }

    private void onSpeichern() {
        if (filename == null || container == null) {
            onSpeichernUnter();
        } else {
            save();
        }
    }

    private void onSpeichernUnter() {
        if (container == null) {
            lblAnzeige.setText(LBL_ANZEIGE_NO_DATA);
            return;
        }

        FileDialog dialog = new FileDialog(this, "Laden", FileDialog.LOAD);

        dialog.setVisible(true);
        if (dialog.getFile() == null) {
            lblAnzeige.setText(LBL_ANZEIGE_NOT_SAVED);

        } else {
            filename = dialog.getDirectory() + dialog.getFile();
            save();
        }

    }

    private void onNeu() {
        if (container != null && !saved) {
            lblAnzeige.setText(LBL_ANZEIGE_NOT_SAVED);
            onSpeichern();
        } else {
            if (container == null) {
                container = FilmContainer.instance();
                ((FilmContainer) container).addObserver(this);
            }
            container.clear();
            lblAnzeige.setText(LBL_ANZEIGE_SAVED);
            saved = true;
            filename = null;
        }
    }

    private void save() {
        try {
            container.connect(filename);
            container.save();
            lblAnzeige.setText(LBL_ANZEIGE_SAVED);
            saved = true;
        } catch (LoadSaveException e) {
            lblAnzeige.setText(e.getMessage() + e.details());
        }
    }
}
