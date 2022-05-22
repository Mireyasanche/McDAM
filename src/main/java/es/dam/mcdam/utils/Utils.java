package es.dam.mcdam.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

import java.io.IOException;
import java.util.Optional;

public class Utils {
    public static void openBrowser(String url) throws IOException {
        new ProcessBuilder("x-www-browser", url).start();
    }

    public static boolean isEmail(String email){
        String regex = "^([\\w-\\.]+){1,64}@([\\w&&[^_]]+){2,255}.[a-z]{2,}$";
        return email.matches(regex);
    }

    public static boolean isTarjetaCredito(String tarjeta){
        String regex = "^[0-9]{16}$";
        return tarjeta.matches(regex);
    }

    public static boolean isPassword(String password){
        String regex = ".{8,}";
        return password.matches(regex);
    }
    public static Optional<String> getFileExtension(String filename) {
        return Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".") + 1));
    }


    public static Alert getAlertErrorDetails(String title, String header, String content, String details) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(title);
            alert.setHeaderText(header);
            alert.setContentText(content);
            Label label = new Label("Los errores son:");

            TextArea textArea = new TextArea(details);
            textArea.setEditable(false);
            textArea.setWrapText(true);

            textArea.setMaxWidth(Double.MAX_VALUE);
            textArea.setMaxHeight(Double.MAX_VALUE);
            GridPane.setVgrow(textArea, Priority.ALWAYS);
            GridPane.setHgrow(textArea, Priority.ALWAYS);

            GridPane expContent = new GridPane();
            expContent.setMaxWidth(Double.MAX_VALUE);
            expContent.add(label, 0, 0);
            expContent.add(textArea, 0, 1);

            alert.getDialogPane().setExpandableContent(expContent);
            return alert;

    }
}
