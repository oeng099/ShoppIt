package helpers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import database.DatabaseManager;
import database.dataModels.FoodItem;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SearchPopUpController implements Initializable {
	
	@FXML
	TextField searchTextField;
	@FXML
	Button searchButton;
	@FXML
	ListView<String> searchResultsList;
	@FXML
	GridPane searchGridPane;
	
	List<String> foodItemNames;
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	private FXMLLoader loader;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		//TO-DO: Need code to get all the food items from database and put in foodItems list.
		//Do this once retrieval method is implemented.
		
		
		/*DatabaseManager databaseManager = new DatabaseManager();
		//List<FoodItem> foodItems = databaseManager.getFoodItems();
		//for(FoodItem foodItem : foodItems) {
			//foodItemNames.add(foodItem.getProductName());
		}*/
		
		//Current foodItemNames for testing, will be replaced with list retrieved from database.
		foodItemNames = Arrays.asList("Orange", "Apple", "Pineapple");
		searchResultsList.getItems().addAll(foodItemNames);
		
		searchResultsList.getSelectionModel().selectedItemProperty().addListener( (ChangeListener<? super String>) new ChangeListener<Object>() {

			@Override
			public void changed(ObservableValue<?> arg0, Object arg1, Object arg2) {
				try {
					loader = new FXMLLoader(getClass().getResource("/fxml/DetailedItemPopUpSceneWithAdd.fxml"));
					root = (Parent) loader.load();
					
					DetailedItemPopUpController detailedItemPopUpController = loader.getController();
					detailedItemPopUpController.setDetailedItemTitle(arg2.toString());
					
					scene = new Scene(root);
					stage = new Stage();
					stage.setResizable(false);
					stage.setScene(scene);

					//Disables underlying window
					stage.initModality(Modality.APPLICATION_MODAL);
					stage.showAndWait();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}
				);
		
	}
	
	//Method to search list and return search results based on user query when search button is pressed 
	public void search(ActionEvent event) {
		String query = searchTextField.getText();
		searchResultsList.getItems().clear();
		
		//Checks if query input is null, if so return all items in the list.
		if(query.equals(" ")) {
			searchResultsList.getItems().addAll(foodItemNames);
		} else {
		List<String> filteredList = searchList(query,foodItemNames);
		searchResultsList.getItems().addAll(filteredList);
		}
	}
	
	//Method to return a list of all items that matches the user's query
	public List<String> searchList(String query, List<String> listOfStrings) {
		List<String> filteredList = new ArrayList<>();
		
		for(String item: listOfStrings) {
			//Sets both item and query to lowercase to avoid case issues when matching.
			if(item.toLowerCase().contains(query.toLowerCase())) {
				filteredList.add(item);
			}
		}
		
		return filteredList;
	}


	

}