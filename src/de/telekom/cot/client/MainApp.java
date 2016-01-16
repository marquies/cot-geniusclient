package de.telekom.cot.client;

import java.io.IOException;

import de.telekom.cot.client.view.RootLayoutController;
import de.telekom.cot.client.view.TabbedMainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainApp extends Application {
	private Stage primaryStage;
	private BorderPane rootLayout;
	
	private void initRootLayout() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));

			rootLayout = (BorderPane) loader.load();

			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
			
			 // Give the controller access to the main app.
	        RootLayoutController controller = (RootLayoutController) loader.getController();
	        controller.setMainApp(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Cloud of Things - Genius Client");
		
		initRootLayout();
		
		showPersonOverview();

	}
	
	public void showPersonOverview() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/TabbedMain.fxml"));
			AnchorPane tabbedMain = (AnchorPane) loader.load();

			rootLayout.setCenter(tabbedMain);
			
			 // Give the controller access to the main app.
	//        TabbedMainController controller = loader.getController();
//	        controller.setMainApp(this);

	        
		} catch (IOException e) {
			e.printStackTrace();
		}

	}


	public static void main(String[] args) {
		launch(args);
	}
}
