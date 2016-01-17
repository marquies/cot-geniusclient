package de.telekom.cot.client;

import java.io.IOException;

import de.telekom.cot.client.model.ManagedObject;
import de.telekom.cot.client.view.RootLayoutController;
import de.telekom.cot.client.view.TabbedMainController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainApp extends Application {
	private Stage primaryStage;
	private BorderPane rootLayout;
	private TreeItem<String> rootItem;
	private ObservableList<ManagedObject> deviceData = FXCollections.observableArrayList();
	private TabbedMainController tabbedMainController;

	public ObservableList<ManagedObject> getDeviceData() {
		return deviceData;
	}
	

	private void initRootLayout() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));

			rootLayout = (BorderPane) loader.load();

			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();

			RootLayoutController controller = (RootLayoutController) loader.getController();
			controller.setMainApp(this);
	
			rootItem = new TreeItem<>(new String("Devices"));

			rootItem.setExpanded(true);
			

			

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Cloud of Things - Genius Client");

		initRootLayout();

		showPersonOverview();

	}

	private void showPersonOverview() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/TabbedMain.fxml"));
			AnchorPane tabbedMain = (AnchorPane) loader.load();
			
			rootLayout.setCenter(tabbedMain);

			tabbedMainController = loader.getController();
			tabbedMainController.setMainApp(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public static void main(String[] args) {
		launch(args);
	}


	public void showDevice(ManagedObject newValue) {
		tabbedMainController.showDevice(newValue);
	}
}
