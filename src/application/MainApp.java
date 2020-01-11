package application;

import java.io.File;
import java.io.IOException;
import java.util.prefs.Preferences;

import application.view.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import application.model.Person;
import application.model.PersonListWrapper;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;

    // 학생 ObservableList 리스트
    private ObservableList<Person> personData = FXCollections.observableArrayList();

    public MainApp() {
        // 샘플 data
        personData.add(new Person("Park"));
        personData.add(new Person("Kim"));
        personData.add(new Person("Choi"));
        personData.add(new Person("Seok"));
    }

    public ObservableList<Person> getPersonData() {
        return personData;
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("AddressApp");

        initRootLayout();

        showPersonOverview();
    }

    //레이아웃 초기화
    public void initRootLayout() {
        try {
            // fxml 파일에서 상위 레이아웃을 가져온다.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // 상위 레이아웃을 포함하는 scene을 보여준다.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);

            //컨트롤러한테 mainApp접근 권한 부여.
            RootLayoutController controller = loader.getController();
            controller.setMainApp(this);

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 마지막으로 열었던 연락처 파일을 가져온다.
        File file = getPersonFilePath();
        if (file != null) {
            loadPersonDataFromFile(file);
        }
    }

    /**
     * 상위 레이아웃 안에 연락처 요약(person overview)을 보여준다.
     */
    public void showPersonOverview() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/PersonOverview.fxml"));
            AnchorPane personOverview = (AnchorPane) loader.load();

            rootLayout.setCenter(personOverview);

            // 메인 애플리케이션이 컨트롤러를 이용할 수 있게 한다.
            PersonOverviewController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 메인 스테이지를 반환한다.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * person의 정보를 변경하기위한 dialog open.
     * 'ok'클릭시, person에 저장후 return true
     *
     * @param person the person object to be edited
     * @return true: the user Clicked 'OK', false: otherwise.
     */
    public boolean showPersonEditDialog(Person person){
        try{
            // fxml 파일을 로드하고 나서 새로운 스테이지를 만든다.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/PersonEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Make dialog stage
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Person");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set person controller
            PersonEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setPerson(person);

            dialogStage.showAndWait();

            return controller.isOkClicked();
        }catch (IOException e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 연락처 파일 환경설정을 반환한다.
     * 즉 파일은 마지막으로 열린 것이고, 환경설정은 OS 특정 레지스트리로부터 읽는다.
     * 만일 preference를 찾지 못하면 null을 반환한다.
     *
     * @return
     */
    public File getPersonFilePath() {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        String filePath = prefs.get("filePath", null);
        if (filePath != null) {
            return new File(filePath);
        } else {
            return null;
        }
    }

    /**
     * 현재 불러온 파일의 경로를 설정한다. 이 경로는 OS 특정 레지스트리에 저장된다.
     *
     * @param file the file or null to remove the path
     */
    public void setPersonFilePath(File file) {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        if (file != null) {
            prefs.put("filePath", file.getPath());

            // Stage 타이틀을 업데이트한다.
            primaryStage.setTitle("AddressApp - " + file.getName());
        } else {
            prefs.remove("filePath");

            // Stage 타이틀을 업데이트한다.
            primaryStage.setTitle("AddressApp");
        }
    }

    /**
     * 지정한 파일로부터 Person data를 불러온다.
     *
     * @param file
     */
    public void loadPersonDataFromFile(File file){
        try{
            JAXBContext context = JAXBContext.newInstance(PersonListWrapper.class);
            Unmarshaller um = context.createUnmarshaller();

            //파일로부터 xml을 읽은후, marshaling.
            PersonListWrapper wraper = (PersonListWrapper) um.unmarshal(file);

            personData.clear();
            personData.addAll(wraper.getPersons());

            // 파일 경로를 레지스트리에 저장한다.
            setPersonFilePath(file);
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not load data");
            alert.setContentText("Could not load data from file: \n" + file.getPath());

            alert.showAndWait();
        }
    }

    /**
     * 현재 데이터를 지정한 파일에 저장한다.
     *
     * @param file
     */
    public void savePersonDataToFile(File file){
        try{
            JAXBContext context = JAXBContext.newInstance(PersonListWrapper.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // 데이터를 감싼다.
            PersonListWrapper wrapper = new PersonListWrapper();
            wrapper.setPersons(personData);

            // marsharing 후 xml저장.
            m.marshal(wrapper,file);

            // 파일 경로를 레지스트리에 저장한다.
            setPersonFilePath(file);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("데이터를 저장할 수 없습니다.");
            alert.setContentText(file.getPath()+" 에 파일을 저장할 수 없습니다.");

            alert.showAndWait();
        }
    }
    /**
     * 통계를 보여주기 위해 다이얼로그를 연다.
     */
    public void showScoreStatistics(){
        try{
            // FXML 파일을불러와 팝업의 새로운 Stage를 생성.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/PersonsScoreGraph.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Score Statistics");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            //person을 컨트롤러에 설정.
            PersonScoreStatisticController controller = loader.getController();
            controller.setPersonData(personData);

            dialogStage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * 성적 순위를 보여주는 다이어로그.
     */
    public void showRankingStatistics(){
        try{
            // FXML 파일을불러와 팝업의 새로운 Stage를 생성.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/PersonRanking.fxml"));
            AnchorPane rankingPage = (AnchorPane) loader.load();
            Stage rankingDialogStage = new Stage();
            rankingDialogStage.setTitle("Ranking Statistics");
            rankingDialogStage.initModality(Modality.WINDOW_MODAL);
            rankingDialogStage.initOwner(primaryStage);
            Scene scene = new Scene(rankingPage);
            rankingDialogStage.setScene(scene);
            //person을 컨트롤러에 설정.
            PersonRankingController controller = loader.getController();
            controller.setPersonData(personData);

            rankingDialogStage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
