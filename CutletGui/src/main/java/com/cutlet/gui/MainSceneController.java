/*
 * Copyright (C) 2017 rudolf.muehlbauer@gmail.com
 */
package com.cutlet.gui;

import com.cutlet.lib.errors.OptimizationFailedException;
import com.cutlet.lib.model.Panel;
import com.cutlet.lib.model.Project;
import com.cutlet.lib.optimizer.GAOptimizationStrategy;
import com.cutlet.lib.optimizer.OptimizationResult;
import com.cutlet.lib.optimizer.Optimizer;
import com.cutlet.lib.testing.DataRegal;
import com.cutlet.lib.testing.DataTable;
import com.cutlet.lib.testing.RandomData;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.MissingResourceException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.FlowPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lombok.NonNull;

/**
 * FXML Controller class
 *
 * @author rmuehlba
 */
public class MainSceneController implements Initializable {

    @FXML
    private TableColumn panelTableName, panelTableCount, panelTableLength, panelTableWidth;

    @FXML
    private TableView<PanelAdapter> panelTable;

    @FXML
    private FlowPane flowPane;

    @FXML
    private Label statusBarLabel;

    private Optional<Project> project = Optional.empty();
    private ResourceBundle rb;
    private Stage stage;
    private final static Logger log = Logger.getLogger("MainSceneController");
    private final CutLayoutDrawer layoutDrawer = new CutLayoutDrawer();
    private Optional<File> currentFile = Optional.empty();
    private final Preferences prefs = Preferences.userNodeForPackage(MainSceneController.class);

    private final BooleanProperty noProjectLoaded = new SimpleBooleanProperty(true);
    private final BooleanProperty fileOpen = new SimpleBooleanProperty(false);
    private final BooleanProperty cantSaveFile = new SimpleBooleanProperty(true);

    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    private static final String STATUS_OPTIMIZATION_FAILED = "status.optimization.failed";
    private static final String STATUS_OPTIMIZATION_FINISHED_SUCCESS = "status.optimization.finished_success";
    private static final String STATUS_OPTIMIZATION_IN_PROGRESS = "status.optimization.in_progress";
    private static final String DLG_OPEN_TITLE = "dlg.open.title";
    private static final String CUTLET_PROJECT = "CUTLET_PROJECT";
    private static final String DLG_SAVE_AS_TITLE = "dlg.save_as.title";

    private final ObservableList<PanelAdapter> panelTableData
            = FXCollections.observableArrayList(
                    new PanelAdapter("Jacob", 1, 1, 1),
                    new PanelAdapter("Isabella", 10, 100, 100)
            );

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.rb = rb;
        cantSaveFile.bind(fileOpen.not());
        assert (flowPane != null);
        assert (panelTable != null);

        panelTableName.setCellValueFactory(new PropertyValueFactory<>("name"));
        panelTableCount.setCellValueFactory(new PropertyValueFactory<PanelAdapter, Integer>("count"));
        panelTableLength.setCellValueFactory(new PropertyValueFactory<PanelAdapter, Double>("length"));
        panelTableWidth.setCellValueFactory(new PropertyValueFactory<PanelAdapter, Double>("width"));

        panelTable.setItems(panelTableData);
    }

    private void renderLayout(OptimizationResult result) {
        flowPane.getChildren().clear();
        layoutDrawer.drawOptimizationResult(flowPane, result);
    }

    private void setupFileChooserExtensionFilter(@NonNull final FileChooser chooser) {
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter(i18n(CUTLET_PROJECT), "*.cutlet")
        );
    }

    private void saveProject(@NonNull final Project project, @NonNull final File file) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(project);
            log.info("saved to " + file.getAbsolutePath());
        } catch (Exception ex) {
            log.log(Level.SEVERE, "failed to save project", ex);
            ex.printStackTrace();
        }
    }

    private Project loadProject(@NonNull final File file) {
        try (ObjectInput input = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)));) {
            //deserialize the List
            final Project newProject = (Project) input.readObject();
            log.info("loaded " + file.getAbsolutePath());
            return newProject;
        } catch (ClassNotFoundException ex) {
            log.log(Level.SEVERE, "Cannot perform input. Class not found.", ex);
        } catch (IOException ex) {
            log.log(Level.SEVERE, "Cannot perform input.", ex);
        }
        return null;
    }

    private String i18n(String key) {
        try {
            return rb.getString(key);
        } catch (MissingResourceException ex) {
            log.info("failed to i18n '" + key + "'");
            return "i18n(" + key + ")";
        }
    }

    private void resetCanvas() {
        flowPane.getChildren().clear();
    }

// <editor-fold defaultstate="expanded" desc="actions">
    @FXML
    protected void ex1(ActionEvent event) {
        setCurrentFile(Optional.empty());
        setProject(Optional.of((new DataTable()).getData()));
    }

    @FXML
    protected void ex2(ActionEvent event) {
        setCurrentFile(Optional.empty());
        setProject(Optional.of((new DataRegal()).getData()));
    }

    @FXML
    protected void ex3(ActionEvent event) {
        setCurrentFile(Optional.empty());
        setProject(Optional.of((new RandomData(100)).getData()));
    }

    @FXML
    protected void save(ActionEvent event) {
        if (!project.isPresent()) {
            return;
        }
        if (currentFile.isPresent()) {
            saveProject(project.get(), currentFile.get());
        } else {
            saveAs(event);
        }
    }

    @FXML
    protected void close(ActionEvent event) {
        setCurrentFile(null);
        setProject(null);
    }

    @FXML
    protected void saveAs(ActionEvent event) {
        if (!project.isPresent()) {
            return;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(i18n(DLG_SAVE_AS_TITLE));
        setupFileChooserExtensionFilter(fileChooser);
        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            saveProject(project.get(), file);
            setCurrentFile(Optional.of(file));
        }
    }

    @FXML
    protected void open(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(i18n(DLG_OPEN_TITLE));
        setupFileChooserExtensionFilter(fileChooser);
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            final Project newProject = loadProject(file);
            setProject(Optional.of(newProject));
            setCurrentFile(Optional.of(file));
        }

    }

    @FXML
    public void processExit(ActionEvent event) {
        executor.shutdown();
        System.exit(0);
    }

    @FXML
    protected void optimize(ActionEvent event) {
        if (!project.isPresent()) {
            log.info("tried to optimize without project set");
            return;
        }

        final Task<Optional<OptimizationResult>> task;
        task = new Task<Optional<OptimizationResult>>() {
            @Override
            protected Optional<OptimizationResult> call() throws Exception {
                updateMessage(STATUS_OPTIMIZATION_IN_PROGRESS);

                final Optimizer optimizer = new Optimizer();
                try {
                    final OptimizationResult result = optimizer.optimize(project.get(), new GAOptimizationStrategy());
                    updateMessage(STATUS_OPTIMIZATION_FINISHED_SUCCESS);
                    return Optional.of(result);

                } catch (OptimizationFailedException ex) {
                    log.log(Level.SEVERE, "Optimization failed", ex);
                    updateMessage(STATUS_OPTIMIZATION_FAILED);
                    return Optional.empty();
                }
            }
        };

        task.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isPresent()) {
                renderLayout(newValue.get());
            }
            project.get().setOptimizationResult(newValue);
        });

        statusBarLabel.textProperty().bind(new I18nBinding(task.messageProperty()));
        executor.submit(task);

    }

// </editor-fold>
// <editor-fold defaultstate="expanded" desc="accessors">
    // needed to bind in XML: http://stackoverflow.com/questions/19822717/binding-a-labels-text-property-in-an-fxml-file-to-an-integerproperty-in-a-co
    public BooleanProperty cantSaveFileProperty() {
        return cantSaveFile;
    }

    public boolean getCantSaveFile() {
        return cantSaveFile.get();
    }

    public void setCantSaveFile(boolean value) {
        cantSaveFile.set(value);
    }

    public BooleanProperty noProjectLoadedProperty() {
        return noProjectLoaded;
    }

    public boolean getNoProjectLoaded() {
        return noProjectLoaded.get();
    }

    public void setNoProjectLoaded(boolean value) {
        noProjectLoaded.set(value);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setProject(@NonNull final Optional<Project> project) {
        resetCanvas();
        this.project = project;
        noProjectLoaded.set(false);
        panelTableData.clear();
        if (project.isPresent()) {
            for (Panel p : project.get().getPanels()) {
                panelTableData.add(new PanelAdapter(p.getTitle(), 1, p.getDimension().getLength(), p.getDimension().getWidth()));
            }
        }
    }

    public void setCurrentFile(@NonNull final Optional<File> currentFile) {
        this.currentFile = currentFile;
        String title = currentFile.isPresent() ? currentFile.get().getName() : i18n("application.title.no_file_open");
        fileOpen.set(currentFile.isPresent());
        if (stage != null) {
            stage.setTitle(i18n("application.title") + " - " + title);
        }
    }

// </editor-fold>
    class I18nBinding extends StringBinding {

        private final ReadOnlyStringProperty prop;

        public I18nBinding(ReadOnlyStringProperty prop) {
            super.bind(prop);
            this.prop = prop;
        }

        @Override
        protected String computeValue() {
            return i18n(prop.get());
        }
    }
}
