/**
 @author Información mostrada en la documentación.
 */

package es.dam.mcdam;

import es.dam.mcdam.managers.DataBaseManager;
import es.dam.mcdam.managers.SceneManager;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

/**
 * Método encargado de iniciar la aplicación.
 */
public class AppMain extends Application {
    //static Logger logger = LogManager.getLogger(AppMain.class);
    @Override
    public void start(Stage stage) throws IOException, InterruptedException {
        SceneManager sceneManager = SceneManager.getInstance(AppMain.class);
        sceneManager.initSplash(stage);
    }

    /**
     * Método encargado de revisar que la base de datos funcione correctamente.
     */
    private static void checkServer() {
        System.out.println("Comprobamos la conexión al Servidor BD");
        DataBaseManager controller = DataBaseManager.getInstance();
        try {
            controller.open();
            Optional<ResultSet> rs = controller.select("SELECT 'Hello world'");
            if (rs.isPresent()) {
                rs.get().next();
                controller.close();
                //logger.info("Conexión correcta a la Base de Datos");
                System.out.println("Conexión correcta a la Base de Datos");
            }
        } catch (SQLException e) {
            //logger.error("Error al conectar al servidor de Base de Datos: " + e.getMessage());
            System.out.println("Error al conectar al servidor de Base de Datos: " + e.getMessage());
            System.exit(1);
        }
    }

    /**
     * Método MAIN de la aplicaicón.
     * @param args
     */
    public static void main(String[] args) {
        checkServer();
        launch();
    }
}