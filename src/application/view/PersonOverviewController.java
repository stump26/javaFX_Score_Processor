package application.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import application.MainApp;
import application.model.Person;

public class PersonOverviewController {
    @FXML
    private TableView<Person> personTable;
    @FXML
    private TableColumn<Person, String> nameColumn;
    @FXML
    private TableColumn<Person, String> SIDColumn;

    @FXML
    private Label nameLabel;
    @FXML
    private Label SIDLabel;
    @FXML
    private Label majorLabel;

    @FXML
    private Label majorScoreLabel;
    @FXML
    private Label liberalScoreLabel;
    @FXML
    private Label crnafScoreLabel;

    @FXML
    private Label majorGradeLabel;
    @FXML
    private Label liberalGradeLabel;
    @FXML
    private Label crnafGradeLabel;

    @FXML
    private Label totalScoreSumLabel;
    @FXML
    private Label averageScoreLabel;

    // 메인 application ref
    private MainApp mainApp;

    public PersonOverviewController() {
    }

    @FXML
    private void initialize() {
        // 연락처 테이블의 두 열을 초기화한다.
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        SIDColumn.setCellValueFactory(cellData -> cellData.getValue().SIDProperty());

        // 연락처 정보를 지운다.
        showPersonDetails(null);

        // 선택을 감지하고 그 때마다 연락처의 자세한 정보를 보여준다.
        personTable.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> showPersonDetails(newValue)
        );
    }
    /**
     * 사용자가 'new'클릭시 호출.
     * 새로운 연락처 정보를 넣기 위해 dialog를 연다.
     */
    @FXML
    private void handleNewPerson(){
        Person tempPerson = new Person();
        boolean okClicked = mainApp.showPersonEditDialog(tempPerson);
        if (okClicked){
            mainApp.getPersonData().add(tempPerson);
        }
    }

    /**
     * 사용자가 'edit' 클릭시 호출.
     * 선택한 학생정보를 변경하기 위한 dialog를 연다.
     */
    @FXML
    private  void handleEditPerson(){
        Person selectedPerson = personTable.getSelectionModel().getSelectedItem();
        if (selectedPerson != null){
            boolean okClicked = mainApp.showPersonEditDialog(selectedPerson);
            if (okClicked){
                showPersonDetails(selectedPerson);
            }
        } else {
            // 아무것도 선택안했을떄,
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Person Selected");
            alert.setContentText("테이블에서 학생을 선택해주세요");

            alert.showAndWait();
        }
    }
    /**
     * 사용자가 삭제 버튼을 클릭하면 호출.
     */
    @FXML
    private void handleDeletePerson(){
        int selectedIndex = personTable.getSelectionModel().getSelectedIndex();
        if(selectedIndex >= 0){
            personTable.getItems().remove(selectedIndex);
        }else {
            // 아무것도 선택하지 않음.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Person Selected");
            alert.setContentText("Please select a person in the table");

            alert.showAndWait();
        }

    }

    /**
     * 참조를 다시 유지하기 위해 메인 애플리케이션이 호출한다.
     *
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        // 테이블에 observable 리스트 데이터를 추가한다.
        personTable.setItems(mainApp.getPersonData());
    }

    /*********
     * 학생의 자세한 정보를 보여주기 위해 모든 텍스트 필드를 채운다.
     * 만일 person이 null이면 모든 텍스트 필드가 지워진다.
     *
     * @pram person the person or null
     */
    private void showPersonDetails(Person person){
        if (person != null){
            //person 객체로 label에 정보를 채운다.
            nameLabel.setText(person.getName());
            SIDLabel.setText(person.getSID());
            majorLabel.setText(person.getMajor());
            majorScoreLabel.setText(Integer.toString(person.getMajorScore()));
            liberalScoreLabel.setText(Integer.toString(person.getLiberalScore()));
            crnafScoreLabel.setText(Integer.toString(person.getCrnafScore()));
            majorGradeLabel.setText(Character.toString(person.getMajorGrade()));
            liberalGradeLabel.setText(Character.toString(person.getLiberalGrade()));
            crnafGradeLabel.setText(Character.toString(person.getCrnafGrade()));
            totalScoreSumLabel.setText(Integer.toString(person.getTotalScore()));
            averageScoreLabel.setText(String.format("%.2f",person.getAverageScore()));
        } else {
            //person이 null이면 모든 텍스트를 지운다.
            nameLabel.setText("");
            SIDLabel.setText("");
            majorLabel.setText("");
            majorScoreLabel.setText(Integer.toString(0));
            liberalScoreLabel.setText(Integer.toString(0));
            crnafScoreLabel.setText(Integer.toString(0));
            majorGradeLabel.setText("-");
            liberalGradeLabel.setText("-");
            crnafGradeLabel.setText("-");
            totalScoreSumLabel.setText(Integer.toString(0));
            averageScoreLabel.setText(Double.toString(0));
        }
    }
}
