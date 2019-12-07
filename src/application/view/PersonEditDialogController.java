package application.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import application.model.Person;

/**
 * 연락처 정보를 변경하는 다이얼로그
 */
public class PersonEditDialogController {
    @FXML
    private TextField nameField;
    @FXML
    private TextField SIDField;
    @FXML
    private TextField majorField;
    @FXML
    private TextField majorScoreField;
    @FXML
    private TextField liberalScoreField;
    @FXML
    private TextField crnafScoreField;

    private Stage dialogStage;
    private Person person;
    private boolean okClicked = false;

    /**
     * 컨트롤러 클래스를 초기화 한다.
     */
    @FXML
    private void initialize(){
    }

    /**
     * dialog의 stage설정.
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage){
        this.dialogStage = dialogStage;
    }

    /**
     * dialog에서 변경될 연락처를 설정.
     * @param person
     */
    public void setPerson(Person person){
        this.person = person;

        nameField.setText(person.getName());
        SIDField.setText(person.getSID());
        majorField.setText(person.getMajor());
        majorScoreField.setText(Integer.toString(person.getMajorScore()));
        liberalScoreField.setText(Integer.toString(person.getLiberalScore()));
        crnafScoreField.setText(Integer.toString(person.getCrnafScore()));
    }

    /**
     * 사용자가 ok클릭시 true, 그외 false.
     *
     * @return
     */
    public boolean isOkClicked(){
        return okClicked;
    }

    /**
     * 사용자가 'ok' 클릭시 호출.
     */
    @FXML
    private void handleOk(){
        if(isInputValid()){
            person.setName(nameField.getText());
            person.setSID(SIDField.getText());
            person.setMajor(majorField.getText());
            person.setMajorScore(Integer.parseInt(majorScoreField.getText()));
            person.setLiberalScore(Integer.parseInt(liberalScoreField.getText()));
            person.setCrnafScore(Integer.parseInt(crnafScoreField.getText()));

            okClicked = true;
            dialogStage.close();
        }
    }

    /**
     * 사용자가 'cancel' 클릭시 호출
     */
    @FXML
    private void handleCancel(){
        dialogStage.close();
    }

    /**
     * 텍스트 필드로 사용자 입력을 검사한다.
     *
     * @retuen true if the input is valid
     */
    private boolean isInputValid() {
        String errorMessage = "";

        if (nameField.getText() == null || nameField.getText().length() == 0){
            errorMessage += "이름이 공백입니다!!\n";
        }
        if (SIDField.getText() == null || SIDField.getText().length() == 0){
            errorMessage += "학번이 공백입니다!!\n";
        }
        if (majorField.getText() == null || majorField.getText().length() == 0){
            errorMessage += "학과가 공백입니다!!\n";
        }
        if(majorScoreField.getText() == null || majorScoreField.getText().length() == 0){
            errorMessage += "전공성적이 공백입니다!!\n";
        }else{
            //typeCheck
            try{
                Integer.parseInt(majorScoreField.getText());
            }catch (NumberFormatException e){
                errorMessage += "(전공성적) 잘못된 값입니다.정수를 입력해주세요!\n";
            }
        }
        if(liberalScoreField.getText() == null || liberalScoreField.getText().length() == 0){
            errorMessage += "교양성적이 공백입니다!!\n";
        }else{
            //typeCheck
            try{
                Integer.parseInt(liberalScoreField.getText());
            }catch (NumberFormatException e){
                errorMessage += "(교양성적) 잘못된 값입니다.정수를 입력해주세요!\n";
            }
        }
        if(crnafScoreField.getText() == null || crnafScoreField.getText().length() == 0){
            errorMessage += "시사성적이 공백입니다!!\n";
        }else{
            //typeCheck
            try{
                Integer.parseInt(crnafScoreField.getText());
            }catch (NumberFormatException e){
                errorMessage += "(시사성적) 잘못된 값입니다.정수를 입력해주세요!\n";
            }
        }

        if (errorMessage.length() == 0){
            return true;
        } else {
            // 오류메시지 print
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("잘못된 입력");
            alert.setHeaderText("올바르지 못한 입력이 있습니다.");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }
}
