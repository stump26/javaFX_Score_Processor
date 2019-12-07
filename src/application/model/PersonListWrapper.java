package application.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
/**
 * Person 리스트를 감싸는 헬퍼 클래스이다.
 * XML로 저장하는데 이용.
 */
@XmlRootElement(name ="persons")
public class PersonListWrapper {
    private List<Person> persons;

    @XmlElement(name="person")
    public List<Person> getPersons(){
        return persons;
    }

    public void setPersons(List<Person> persons){
        this.persons = persons;
    }
}
