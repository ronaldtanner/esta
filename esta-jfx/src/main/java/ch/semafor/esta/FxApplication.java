package ch.semafor.esta;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * Created by tar on 26.12.16.
 */
@SpringBootApplication
/*
@Configuration
@ComponentScan("ch.semafor.esta")
@EnableAutoConfiguration
@EnableJpaRepositories("ch.semafor.esta.core") */
public class FxApplication extends Application{
    private static String[] args;
    private ApplicationContext context;

    @Override
    public void start(Stage stage) throws Exception {
       // Bootstrap Spring context here.
        context = new SpringApplicationBuilder(FxApplication.class).web(false).run(args);

        // Create a Scene
        Parent root = loadController("/views/Students.fxml");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("ESTA Application");
        stage.show();
    }
    public static void main(String [] args){
        FxApplication.args = args;
        launch(args);
    }

    private Parent loadController(String url) throws IOException {
        try (InputStream fxmlStream = getClass().getResourceAsStream(url)) {
            FXMLLoader loader = new FXMLLoader();
            loader.setControllerFactory(
                new Callback<Class<?>, Object>() {
            	    @Override
            	    public Object call(Class<?> clazz) {
            	    	return context.getBean(clazz);
            	    }
            });

            return loader.load(fxmlStream);
        }
    }
}
