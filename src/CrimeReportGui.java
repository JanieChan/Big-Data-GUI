import java.io.*;
import java.io.IOException;
import java.sql.Time;
import java.time.*;
import java.util.*;
import java.util.List;
import java.util.Scanner;
import javafx.application.*;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.event.*;

public class CrimeReportGui extends Application{
	private List<CrimeReport> programList;
	private ComboBox<CrimeReport.Category>categoryBox;
	private Button seeProgramButton, resetButton;
	private TextArea resultArea;
	private Text categoryText;

	public void start(Stage primaryStage){
		programList = new ArrayList<>();
		fillList(programList);

		VBox mainVBox = new VBox();
		mainVBox.setStyle("-fx-background-color: linen");
		mainVBox.setAlignment(Pos.CENTER);
		mainVBox.setSpacing(10);
		
		GridPane gridPane = new GridPane();
		gridPane.setStyle("-fx-background-color: linen"); 
		gridPane.setHgap(10); 
		gridPane.setVgap(10); 
		mainVBox.getChildren().add(gridPane);
		
		categoryBox = new ComboBox<CrimeReport.Category>(FXCollections.observableArrayList(CrimeReport.Category.values()));
		gridPane.add(categoryBox, 1, 1);
		
		categoryText = new Text(); 
		categoryText.setText("Choose a Category: ");
		
		seeProgramButton = new Button("View Results");
		seeProgramButton.setOnAction(this::getResults);
		gridPane.add(seeProgramButton, 1, 3);
		
		resetButton = new Button("Reset Inputs and Output");
		resetButton.setOnAction(this::resetInputs);
		gridPane.add(resetButton, 1, 5);
		
		HBox categoryTextBox = new HBox(categoryText);
		gridPane.add(categoryTextBox, 1, 0 );
		categoryTextBox.setSpacing(10);
		
		resultArea = new TextArea();
		gridPane.add(resultArea, 1, 10);
		
		Scene scene = new Scene(mainVBox, 530, 400);
		
		primaryStage.setTitle("Crime Report Stat");
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
	private void getResults(ActionEvent event) {
		resultArea.clear();
		CrimeReport.Category category = categoryBox.getValue();
		if(category == null) {
			resultArea.appendText("Error: you must select a category");
		}else {
			List<CrimeReport> selectedProgramList = new ArrayList<>();
			for(CrimeReport program : programList) {
				if(program.getCategory().equals(category)) {
					selectedProgramList.add(program);
				}
			}
			resultArea.appendText(selectedProgramList.size() + " results");
			for(CrimeReport report : selectedProgramList) {
				resultArea.appendText("\r\n \r\n");
				resultArea.appendText(report.toString());
			}
			
		}
	}
	
	private void resetInputs(ActionEvent event) {
		categoryBox.setValue(null);
		resultArea.setText(" ");
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public static void fillList(List<CrimeReport> programList) {
		try(Scanner fileScan = new Scanner(new FileReader(new File("Police_Department_Incident_Reports__Historical_2003_to_May_2018.csv")))){
			String line = fileScan.nextLine();
			while(fileScan.hasNext()) {
				boolean day = false;
				line = fileScan.nextLine();
				Scanner lineScan = new Scanner(line);
				lineScan.useDelimiter(",");
				int incidentNum = Integer.parseInt(lineScan.next());
				String catTemp = lineScan.next();
				if(catTemp.contains("SEX OFFENSES")) {
					catTemp += lineScan.next();
				}
				CrimeReport.Category category = CrimeReport.Category.getCategory(catTemp.replace("/", " ").replace("\"", "").replace("-", " "));
				String description = lineScan.next();
				String dayOfWeek = null;
				String[] daysOfWeek = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
				String temp = lineScan.next();
				int count = 0;
				while(day == false) {
					for(String days : daysOfWeek) {
						if(temp.equals(days)) {
							dayOfWeek = days;
							day = true;
						} else if(count == 7){
							description += temp;
							temp = lineScan.next();
						}
						count++;
					}
				}
				String date = lineScan.next();
				String time = lineScan.next();
				String resTemp = lineScan.next();
				if(resTemp.contains("ARREST")) {
					resTemp += lineScan.next();
				}
				CrimeReport.Resolution resolution = CrimeReport.Resolution.getResolution(resTemp.replace("\"", ""));
				String address = lineScan.next();
				CrimeReport program = new CrimeReport(incidentNum, category, description, dayOfWeek, date, time, resolution, address);
				programList.add(program);
			}
			System.out.println("done");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}