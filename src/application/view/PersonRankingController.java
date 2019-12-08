package application.view;

import application.model.Person;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.Arrays;
import java.util.List;
import java.util.Comparator;

class avgScoreSorter implements Comparator<Person>
{
    @Override
    public int compare(Person p1, Person p2) {
        return Double.compare(p2.getAverageScore(),p1.getAverageScore());
    }
}

public class PersonRankingController {
    @FXML
    private List<TextField> tierTextFieldList;
    /**
     * dialog initializing.
     */
    public void initialize(){
    }

    /**
     * MainApp으로부터 person data 넘겨받아 처리.
     *
     * @param persons
     */
    public void setPersonData(List<Person> persons){
        persons.sort(new avgScoreSorter());
        int count = 0;
        for(TextField tf : tierTextFieldList){
            System.out.println(count);
            if(count<persons.size()){
                tf.setText(persons.get(count).getSID()+" / "+ persons.get(count).getName());
                count++;
            }else{
                tf.setText("");
            }
        }
    }

}
