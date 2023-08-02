import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.*;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class TrainStation extends Application {
    private HashMap<String, String> myMap = new HashMap<>();
    private Passenger[] waitingRoom = new Passenger[42];
    private int waitingRoomIndex=0;
    private PassengerQueue passengerQueueNew = new PassengerQueue();
    private Passenger[] train = new Passenger[42];
    private int trainNext=0;

    public static void main(String[] args) {
        launch();
    }
    @Override
    public void start (Stage primaryStage) throws Exception{
        loadMap();

        for (String value : myMap.keySet()){
            Passenger passenger = new Passenger();
            passenger.setSeat(value);
            passenger.setName(myMap.get(value));
            waitingRoom[waitingRoomIndex++] = passenger;
        }

        menu:
        while (true){
            System.out.println("Press \"A\" to add customer to a seat");
            System.out.println("Press \"V\" to View all seats");
            System.out.println("Press \"D\" to Delete customer from seat");
            System.out.println("Press \"R\" to Run the simulation and produce report");
            System.out.println("Press \"S\" to Store program data in to file");
            System.out.println("Press \"L\" to Load the program data from file");
            System.out.println("Press \"Q\" to quit the programme");

            System.out.println("Your Option - ");
            Scanner sc = new Scanner(System.in);
            String input = sc.next();

            switch (input) {
                case "A":
                case "a":
                    addToQueue();
                    break;
                case "V":
                case "v":
                    viewQueue();
                    break;
                case "D":
                case "d":
                    deletePassenger();
                    break;
                case "R":
                case "r":
                    runSimulation();
                    break;
                case "S":
                case "s":
                    storeData();
                    break;
                case "L":
                case "l":
                    loadData();
                    break;
                case "Q":
                case "q":
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid Credentials ! .. Please Press again.\n");
                    break;
            }
        }
    }

    public void loadMap() throws IOException {
        File file = new File("data.txt"); //getting key value pairs from the cw1 data.txt file
        FileReader reader = new FileReader(file);
        BufferedReader br = new BufferedReader(reader);
        String ss = br.readLine();
        ss = ss.substring(1, ss.length() - 1);        //remove curly brackets
        String[] keyValuePairs = ss.split(",");
        for (String pair : keyValuePairs) {             //iterate over the pairs
            String[] entry = pair.split("=");     //split the pairs to get key and value
            myMap.put(entry[0].trim(), entry[1].trim());  //add them to the Hashmap and trim whitespaces
        }
    }
    //ADD TO QUEUE
    private void addToQueue() {
        Random random = new Random();
        int randomNum = random.nextInt(6)+1; //generate random number between 1 to 6

        if (randomNum>waitingRoomIndex){
            randomNum=waitingRoomIndex;
        }

        for (int i=0;i<randomNum;i++){
            passengerQueueNew.add(waitingRoom[i]);
        }

        for (int j=0; j<randomNum;j++) {
            for (int i = 0; i < waitingRoomIndex; i++) {
                waitingRoom[i] = waitingRoom[i + 1];
            }
        }
        waitingRoomIndex = waitingRoomIndex-randomNum;

        System.out.println("Train Queue");
        for (int i=0; i<passengerQueueNew.getLast(); i++){
            System.out.println( (i+1) + " - " + passengerQueueNew.getQueueArray()[i].getName());
        }
        System.out.println("------------------------------------------------------------------");

        //adding to queue gui part
        Label label = new Label("Train Queue");
        label.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        label.setTextFill(Color.CADETBLUE);
        label.setLayoutX(350);
        label.setLayoutY(50);

        VBox vBox = new VBox();
        vBox.setLayoutX(50);
        vBox.setLayoutY(70);

        VBox vBox1 = new VBox();
        vBox1.setLayoutX(400);
        vBox1.setLayoutY(70);

        for (int i=0; i<passengerQueueNew.getLast();i++){
            Label label1 = new Label();
            label1.setFont(Font.font("Arial", FontWeight.BOLD, 14));///
            label1.setTextFill(Color.LIGHTSKYBLUE);
            label1.setText(passengerQueueNew.getQueueArray()[i].getName());

            if (i>21){
                vBox1.getChildren().add(label1);
            } else {
                vBox.getChildren().add(label1);
            }

        }

        Pane pane = new Pane();
        pane.getChildren().addAll(label,vBox,vBox1);
        Stage stage = new Stage();
        stage.setScene(new Scene(pane, 800, 700));
        stage.showAndWait();


    }
    //SIMULATION REPORT
    private void runSimulation() {
        for (int i=0;i<passengerQueueNew.getLast();i++){
            train[trainNext++]=passengerQueueNew.getQueueArray()[i];
        }
        passengerQueueNew.setLast(0);
        //reportgui
    }
    //VIEW QUEUE
    private void viewQueue() {
        System.out.println("Waiting Room");
        for (int i=0; i<waitingRoomIndex;i++){
            System.out.println((i+1) + " - " + waitingRoom[i].getName());
        }
        System.out.println("------------------------------------------------------------------");

        System.out.println("Train Queue");
        for (int i=0; i<passengerQueueNew.getLast(); i++){
            System.out.println( (i+1) + " - " + passengerQueueNew.getQueueArray()[i].getName());
        }
        System.out.println("------------------------------------------------------------------");

        System.out.println("Train");
        for (int i=0; i<trainNext;i++){
            System.out.println((i+1) + " - " + train[i].getName());
        }
        //view queue gui part
        Label label = new Label("Train Queue");
        label.setFont(Font.font("Arial", FontWeight.BOLD, 18));///
        label.setTextFill(Color.CADETBLUE);
        label.setLayoutX(50);
        label.setLayoutY(50);

        VBox vBox = new VBox();
        vBox.setLayoutX(50);
        vBox.setLayoutY(70);

        VBox vBox1 = new VBox();
        vBox1.setLayoutX(200);
        vBox1.setLayoutY(70);

        for (int i=0; i<passengerQueueNew.getLast();i++){
            Label label1 = new Label();
            label1.setText(passengerQueueNew.getQueueArray()[i].getName());

            if (i>21){
                vBox1.getChildren().add(label1);
            } else {
                vBox.getChildren().add(label1);
            }

        }

        Pane pane = new Pane();
        pane.getChildren().addAll(label,vBox,vBox1);

        Label label1 = new Label("Waiting Room");
        label1.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        label1.setTextFill(Color.CADETBLUE);
        label1.setLayoutX(50);
        label1.setLayoutY(50);

        VBox vBox2 = new VBox();
        vBox2.setLayoutX(50);
        vBox2.setLayoutY(70);

        VBox vBox3 = new VBox();
        vBox3.setLayoutX(200);
        vBox3.setLayoutY(70);

        for (int i=0;i<waitingRoomIndex;i++){
            Label label2 = new Label();
            label2.setText(waitingRoom[i].getName());

            if (i>21){
                vBox3.getChildren().add(label2);
            } else {
                vBox2.getChildren().add(label2);
            }


        }

        Pane pane1 = new Pane();
        pane1.getChildren().addAll(label1,vBox2,vBox3);

        Label label2 = new Label("Train");
        label2.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        label2.setTextFill(Color.CADETBLUE);
        label2.setLayoutX(50);
        label2.setLayoutY(50);

        VBox vBox4 = new VBox();
        vBox4.setLayoutX(50);
        vBox4.setLayoutY(70);

        VBox vBox5 = new VBox();
        vBox5.setLayoutX(200);
        vBox5.setLayoutY(70);


        Button[] buttonArray = new Button[42];
        for (int i=0;i<42;i++){
            Button button = new Button((i+1) + " Empty");
            button.setId(String.valueOf(i));
            buttonArray[i] = button;

            if (i>21){
                vBox5.getChildren().add(button);
            } else {
                vBox4.getChildren().add(button);
            }


        }

        for (int i=0; i<trainNext;i++){
            buttonArray[Integer.valueOf(train[i].getSeat())-1].setText((train[i].getSeat()) + " " + train[i].getName());
        }

        Pane pane2 = new Pane();
        pane2.getChildren().addAll(label2,vBox4,vBox5);

        BorderPane borderPane = new BorderPane();
        borderPane.setLeft(pane1);
        borderPane.setCenter(pane);
        borderPane.setRight(pane2);

        Stage stage = new Stage();
        stage.setScene(new Scene(borderPane, 1000, 1000));
        stage.showAndWait();



    }




    //DELETE PASSENGER //NEED WORK
    private void deletePassenger() {
        Scanner sc1 = new Scanner(System.in);
        System.out.println("Enter the \"Seat Number\" You want to Delete !");
        String delSeat = sc1.next();
        if (myMap.containsKey(delSeat)) {
            myMap.remove(delSeat);
            System.out.println("Seat No: " + delSeat + " is Deleted !\n");
        } else {
            System.out.println("Seat No: \"" + delSeat + "\"" + " has not been Booked in Order to Delete ! \n");
        }

    }

    //STORE DATA //NEED WORK
    private void storeData() throws IOException {
        File file = new File("data2.txt");
        FileWriter writer = new FileWriter(file);
        BufferedWriter bw = new BufferedWriter(writer);
        bw.write(String.valueOf(passengerQueueNew));
        bw.flush();

    }

    //LOAD DATA //NEED WORK
    private void loadData() throws IOException {
        File file = new File("data2.txt");
        FileReader reader = new FileReader(file);
        BufferedReader br = new BufferedReader(reader);
        String ss = br.readLine();

        while (ss != null){
            System.out.println(ss);
            ss = br.readLine();

        }
    }

}







