package application.view;

import java.util.Arrays;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;
import application.model.Person;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;


/**
 * 점수 통계 뷰에 대한 컨트롤러.
 */
public class PersonScoreStatisticController {
    @FXML
    private BarChart<String,Integer> barChart;
    @FXML
    private ChoiceBox categoryBox;
    @FXML
    private CategoryAxis xAxis;
    @FXML
    private Label graphTitleLabel;
    @FXML
    private Button closeBtn;

    private ObservableList<String> scoreGradeTen = FXCollections.observableArrayList();
    private ObservableList<String> categoryListItems = FXCollections.observableArrayList();
    private String currentCategory;
    private int[] valuesX = new int[10];
    private int[] majorScoreCounter = new int[10];
    private int[] liberalScoreCounter = new int[10];
    private int[] crnafScoreCounter = new int[10];
    private int[] avgScoreCounter = new int[10];

    public void setCurrentCategory (String targetCategory){
        this.currentCategory=targetCategory;
    }
    public String getCurrentCategory (){
        return this.currentCategory;
    }

    /**
     * 컨트롤러 클래스 초기화.
     */
    @FXML
    private void initialize(){
        //성적 구간 X좌표를 배열로 설정온다.
        String[] Scores = {"0-10","10-20","20-30","30-40","40-50","50-60","60-70","70-80","80-90","90-100"};
        scoreGradeTen.addAll(Arrays.asList(Scores));
        xAxis.setCategories(scoreGradeTen);

        String[] CategoryItemsArray = {"전공","시사","교양","평균"};
        categoryListItems.addAll(Arrays.asList(CategoryItemsArray));
        categoryBox.setItems(categoryListItems);
        categoryBox.setValue(CategoryItemsArray[0]);
        setCurrentCategory(CategoryItemsArray[0]);

        // choiseBox change 이벤트 리스너 추가.
        categoryBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number prev, Number changed) {
                setCurrentCategory(CategoryItemsArray[(int)changed]);
                barChart.getData().clear();
                DrawGraph();
            }
        });
    }

    /**
     * 통계에 보여줄 data 설정.
     *
     * @param persons
     */
    public void setPersonData(List<Person> persons){
        for(Person p : persons){
            int majorScoreIndex = p.getMajorScore()/10;
            int liberalScoreIndex = p.getLiberalScore()/10;
            int crnafScoreIndex = p.getCrnafScore()/10;
            int avgScoreIndex = (int)p.getAverageScore()/10;
            majorScoreCounter[majorScoreIndex]++;
            liberalScoreCounter[liberalScoreIndex]++;
            crnafScoreCounter[crnafScoreIndex]++;
            avgScoreCounter[avgScoreIndex]++;
        }

        DrawGraph();
    }
    /**
     * graph drawing
     *
     * get "currentCategory" from this class field
     */
    public void DrawGraph(){
        if(this.getCurrentCategory().equals("전공")){
            valuesX=majorScoreCounter;
        }
        else if(this.getCurrentCategory().equals("교양")){
            valuesX=liberalScoreCounter;
        }
        else if(this.getCurrentCategory().equals("시사")){
            valuesX=crnafScoreCounter;
        }
        else{
            valuesX=avgScoreCounter;
        }

        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        for (int i = 0; i < valuesX.length; i++){
            series.getData().add(new XYChart.Data<>(scoreGradeTen.get(i), valuesX[i]));
        }
        barChart.getData().add(series);
        graphTitleLabel.setText(this.getCurrentCategory()+"점수 분포도");
    }
    /**
     * 사용자가 분류를 변경했을때 그래프 재설정.
     *
     */
    public void handleOnChangeCategory(){
        System.out.println(categoryBox.getValue());
    }

    /**
     * 사용자가 'close'버튼을 눌럿을시.
     */
    public void handleOnClickCloseStatistic(){
        Stage stage = (Stage) closeBtn.getScene().getWindow();
        stage.close();
    }
}
