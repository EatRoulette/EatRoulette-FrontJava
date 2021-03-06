package fr.eatroulette.ui.main;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.function.Consumer;

public class Router {

    private final Stage stage;

    public Router(final Stage stage) {
        this.stage = stage;
    }

    void goTo(final String viewName) {
        goTo(viewName, __ -> {});
    }

    public <T> void goTo(final String viewName, final Consumer<T> controllerConsumer) {
        final var view = loadView(viewName, controllerConsumer);
        stage.setScene(new Scene(view));
    }

    private <T> Parent loadView(final String viewName, final Consumer<T> controllerConsumer) {
        final var viewPath = String.format("/%sView.fxml", viewName);
        try {
            final var fxmlLoader = new FXMLLoader(this.getClass().getResource(viewPath));
            final Parent view = fxmlLoader.load();
            controllerConsumer.accept(fxmlLoader.getController());

            return view;
        } catch (IOException e) {
            throw new IllegalStateException(String.format("Unable to load view: %s", viewPath), e);
        }
    }

}