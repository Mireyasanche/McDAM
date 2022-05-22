package es.dam.mcdam.managers;

import es.dam.mcdam.AppMain;
import es.dam.mcdam.controllers.AcercaDeViewController;
import es.dam.mcdam.controllers.McDAMController;
import es.dam.mcdam.controllers.EstadisticasViewController;
import es.dam.mcdam.controllers.McDAMController;
import es.dam.mcdam.controllers.PersonaEditarViewController;
import es.dam.mcdam.models.Producto;
import es.dam.mcdam.utils.Properties;
import es.dam.mcdam.utils.Resources;
import es.dam.mcdam.views.Views;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class SceneManager {
    private static SceneManager instance;
    private final Class<?> appClass;
    Logger logger = LogManager.getLogger(SceneManager.class);

    private Stage mainStage;

    private SceneManager(Class<?> appClass) {
        this.appClass = appClass;
        logger.info("SceneManager created");
        //System.out.println("SceneManager created");
    }

    public static SceneManager getInstance(Class<?> appClass) {
        if (instance == null) {
            instance = new SceneManager(appClass);
        }
        return instance;
    }

    public static SceneManager get() {
        return instance;
    }

    public void changeScene(Node node, Views view) throws IOException {
        logger.info("Loading scene " + view.get());
        Stage stage = (Stage) node.getScene().getWindow();
        //oldStage.hide(); // Oculto la anterior
        Parent root = FXMLLoader.load(Objects.requireNonNull(appClass.getResource(view.get())));
        Scene newScene = new Scene(root, Properties.APP_WIDTH, Properties.APP_HEIGHT);
        logger.info("Scene " + view.get() + " loaded");
        stage.setScene(newScene);
        stage.show();
    }

    public void initMain() throws IOException {
        logger.info("Iniciando Main");
        Platform.setImplicitExit(true);
        //logger.info("Loading scene " + Views.MAIN.get());
        FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(appClass.getResource(Views.MAIN.get())));
        Scene scene = new Scene(fxmlLoader.load(), Properties.APP_WIDTH, Properties.APP_HEIGHT);
        Stage stage = new Stage();
        stage.setResizable(true);
        stage.getIcons().add(new Image(Resources.get(AppMain.class, Properties.APP_ICON)));
        stage.setTitle(Properties.APP_TITLE);
        stage.initStyle(StageStyle.DECORATED);
        logger.info("Scene Main loaded");
        // Por si salimos
        stage.setOnCloseRequest(event -> {
            fxmlLoader.<McDAMController>getController().onSalirAction();
        });
        stage.setScene(scene);
        mainStage = stage;
        stage.show();
    }

    public void initSplash(Stage stage) throws IOException, InterruptedException {
        Platform.setImplicitExit(false);
        logger.info("Iniciando Splash");
        FXMLLoader fxmlLoader = new FXMLLoader(AppMain.class.getResource(Views.SPLASH.get()));
        Scene scene = new Scene(fxmlLoader.load(), Properties.SPLASH_WIDTH, Properties.SPLASH_HEIGHT);
        stage.getIcons().add(new Image(Resources.get(AppMain.class, Properties.APP_LOGO)));
        stage.setTitle(Properties.APP_TITLE);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        logger.info("Scene Splash loaded");
        stage.show();
    }

    public void initAcercaDe() throws IOException {
        logger.info("Iniciando AcercaDe");
        FXMLLoader fxmlLoader = new FXMLLoader(AppMain.class.getResource(Views.ACERCADE.get()));
        Scene scene = new Scene(fxmlLoader.load(), Properties.ACERCADE_WIDTH, Properties.ACERCADE_HEIGHT);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        //stage.initOwner(mainStage);
        stage.setTitle("Acerca de");
        stage.setResizable(false);
        // Le hacemos los setters a los elementos del controlador
        fxmlLoader.<AcercaDeViewController>getController().setDialogStage(stage);
        stage.setScene(scene);
        logger.info("Scene AcercaDe loaded");
        stage.showAndWait();
    }

    public boolean initPersonaEditar(boolean editarModo, Producto producto) throws IOException {
        logger.info("Iniciando PersonaEditar");
        FXMLLoader fxmlLoader = new FXMLLoader(AppMain.class.getResource(Views.PERSONAEDITAR.get()));
        Scene scene = new Scene(fxmlLoader.load(), Properties.PERSONAEDIT_WIDTH, Properties.PERSONAEDITAR_HEIGHT);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        //stage.initOwner(mainStage); // -importante con windows modal
        stage.setTitle(editarModo ? "Editar Persona" : "Nueva Persona");
        stage.setResizable(false);
        // Le hacemos los setters a los elementos del controlador
        PersonaEditarViewController controller = fxmlLoader.getController();
        controller.setDialogStage(stage);
        controller.setEditarModo(editarModo);
        controller.setPersona(persona);
        stage.setScene(scene);
        logger.info("Scene PersonaEditar loaded");
        stage.showAndWait();
        return controller.isAceptarClicked();
    }

    public void initEstadisticas(List<Producto> productos) throws IOException {
        logger.info("Iniciando Estadisticas");
        FXMLLoader fxmlLoader = new FXMLLoader(AppMain.class.getResource(Views.ESTADISTICAS.get()));
        Scene scene = new Scene(fxmlLoader.load(), Properties.ESTADISTICAS_WIDTH, Properties.ESTADISTICAS_HEIGHT);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(mainStage); // -importante con windows modal
        stage.setTitle("Estadisticas");
        stage.setResizable(false);
        // Le hacemos los setters a los elementos del controlador
        EstadisticasViewController controller = fxmlLoader.getController();
        controller.setPersonData(productos);
        stage.setScene(scene);
        logger.info("Scene Estadisticas loaded");
        stage.showAndWait();
    }
}
