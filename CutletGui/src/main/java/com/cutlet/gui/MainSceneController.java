/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cutlet.gui;

import com.cutlet.lib.model.Project;
import com.cutlet.lib.optimizer.OptimizationResult;
import com.cutlet.lib.optimizer.Optimizer;
import com.cutlet.lib.optimizer.SAOptimizationStrategy;
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
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author rmuehlba
 */
public class MainSceneController implements Initializable {

    @FXML
    private FlowPane flowPane;

    @FXML
    private Label statusBarLabel;

    private Project project;
    private ResourceBundle rb;
    private Stage stage;
    private final static Logger log = Logger.getLogger("MainSceneController");
    private final CutLayoutDrawer layoutDrawer = new CutLayoutDrawer();
    private File currentFile = null;
    private final Preferences prefs = Preferences.userNodeForPackage(MainSceneController.class);

    private final BooleanProperty noProjectLoaded = new SimpleBooleanProperty(true);
    private final BooleanProperty fileOpen = new SimpleBooleanProperty(false);
    private final BooleanProperty cantSaveFile = new SimpleBooleanProperty(true);

    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.rb = rb;
        cantSaveFile.bind(fileOpen.not());
        setCurrentFile(null);
    }

    private void renderLayout(OptimizationResult result) {
        assert (flowPane != null);
        flowPane.getChildren().clear();
        layoutDrawer.drawOptimizationResult(flowPane, result);
    }

    private void setupFileChooserExtensionFilter(FileChooser chooser) {
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter(i18n("CUTLET_PROJECT"), "*.cutlet")
        );

    }

    private void saveProject(Project project, File file) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(project);
            log.info("saved to " + file.getAbsolutePath());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private Project loadProject(File file) {
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
        return rb.getString(key);
    }

    private void resetCanvas() {
        flowPane.getChildren().clear();
        //GraphicsContext gc = canvas.getGraphicsContext2D();
        //gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

// <editor-fold defaultstate="expanded" desc="actions">
    @FXML
    protected void ex1(ActionEvent event) {
        setCurrentFile(null);
        setProject((new DataTable()).getData());
    }

    @FXML
    protected void ex2(ActionEvent event) {
        setCurrentFile(null);
        setProject((new DataRegal()).getData());
    }

    @FXML
    protected void ex3(ActionEvent event) {
        setCurrentFile(null);
        setProject((new RandomData(100)).getData());
    }

    @FXML
    protected void save(ActionEvent event) {
        if (currentFile == null) {
            saveAs(event);
        } else {
            saveProject(project, currentFile);
        }
    }

    @FXML
    protected void close(ActionEvent event) {
        setCurrentFile(null);
        setProject(null);
    }

    @FXML
    protected void saveAs(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(i18n("dlg.save_as.title"));
        setupFileChooserExtensionFilter(fileChooser);
        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            saveProject(project, file);
            setCurrentFile(file);
        }
    }

    @FXML
    protected void open(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(i18n("dlg.open.title"));
        setupFileChooserExtensionFilter(fileChooser);
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            final Project newProject = loadProject(file);
            setProject(newProject);
            setCurrentFile(file);
        }

    }

    @FXML
    public void processExit(ActionEvent event) {
        executor.shutdown();
        System.exit(0);
    }

    @FXML
    protected void optimize(ActionEvent event) {
        if (project == null) {
            log.info("tried to optimize without project set");
            return;
        }

        Task<OptimizationResult> task = new Task<OptimizationResult>() {
            @Override
            protected OptimizationResult call() throws Exception {
                updateMessage("running optimization");

                final Optimizer optimizer = new Optimizer();
                final OptimizationResult result = optimizer.optimize(project, new SAOptimizationStrategy());

                Platform.runLater(() -> {
                    renderLayout(result);
                    project.setOptimizationResult(result);
                });

                updateMessage("done");
                return result;
            }
        };

        statusBarLabel.textProperty().bind(task.messageProperty());
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

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        resetCanvas();
        this.project = project;
        noProjectLoaded.set(false);
    }

    public File getCurrentFile() {
        return currentFile;
    }

    public void setCurrentFile(File currentFile) {
        this.currentFile = currentFile;
        if (currentFile != null) {
            if (stage != null) {
                stage.setTitle(i18n("application.title") + " - " + currentFile.getName());
            }
            fileOpen.set(true);
        } else {
            if (stage != null) {
                stage.setTitle(i18n("application.title") + " - no open file");
            }
            fileOpen.set(false);
        }
    }

// </editor-fold>
}
