package application.view;

import application.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;

import java.io.File;
import java.nio.channels.FileChannel;


public class RootLayoutController {
    // 메인 application ref
    private MainApp mainApp;

    /**
     * 참조를 다시 유지하기 위해 메인 애플리케이션이 호출한다.
     *
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    /**
     * 비어있는 주소록 생성.
     */
    @FXML
    private void handleNew(){
        mainApp.getPersonData().clear();
        mainApp.setPersonFilePath(null);
    }

    /**
     * FileChooser호출, 사용자가 가져올 파일선택.
     */
    @FXML
    private void handleOpen(){
        FileChooser fileChooser = new FileChooser();

        // 확장자 필터를 설정.
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml"
        );
        fileChooser.getExtensionFilters().add(extFilter);

        // Save File Dialog
        File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());

        if(file != null){
            mainApp.loadPersonDataFromFile(file);
        }
    }

    /**
     * 현재 열려있는 파일에 저장.
     * 만일 없다면 'Save as'를 보여줌.
     */
    @FXML
    private void handleSave(){
        File personFile = mainApp.getPersonFilePath();
        if (personFile != null){
            mainApp.savePersonDataToFile(personFile);
        }else{
            handleSaveAs();
        }
    }

    /**
     * FileChoose를 열어 저장할 파일을 선택하게 한다.
     */
    @FXML
    private void handleSaveAs(){
        FileChooser fileChooser = new FileChooser();

        // 확장자 필터를 설정한다.
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml"
        );
        fileChooser.getExtensionFilters().add(extFilter);

        // Save File Dialog를 보여준다.
        File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());

        if(file != null){
            //확장자 체크.
            if (!file.getPath().endsWith(".xml")){
                file = new File(file.getPath() + ".xml");
            }
            mainApp.savePersonDataToFile(file);
        }
    }

    /**
     * About Dialog
     */
    @FXML
    private void handleAbout(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Stumpark`s Score Processor");
        alert.setHeaderText("About");
        alert.setContentText("© Stumpark, SangUk Park\n Website: https://blog.stumpark.com\n");

        alert.showAndWait();
    }
    @FXML
    private void handleExit(){
        System.exit(0);
    }
    /**
     * open Graph
     */
    @FXML
    private void handleShowScoreStatistics(){
        mainApp.showScoreStatistics();
    }
    @FXML
    private void handleShowRankingStatistics(){
        mainApp.showRankingStatistics();
    }
}
