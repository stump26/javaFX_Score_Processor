package application;

import java.io.IOException;

import application.view.PersonEditDialogController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import application.model.Person;
import application.view.PersonOverviewController;

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
        personData.add(new Person("Yoo"));
        personData.add(new Person("Lee"));
        personData.add(new Person("shin"));
        personData.add(new Person("Cha"));
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
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 상위 레이아웃 안에 연락처 요약(person overview)을 보여준다.
     */
    public void showPersonOverview() {
        try {
            // 연락처 요약을 가져온다.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/PersonOverview.fxml"));
            AnchorPane personOverview = (AnchorPane) loader.load();

            // 연락처 요약을 상위 레이아웃 가운데로 설정한다.
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
}
