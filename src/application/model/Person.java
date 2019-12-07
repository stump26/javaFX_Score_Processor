package application.model;

import java.time.LocalDate;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Person {
	private final StringProperty name;
	private final StringProperty SID;
	private final StringProperty major;
	private final IntegerProperty majorScore;
	private final IntegerProperty liberalScore;
	private final IntegerProperty crnafScore;

	public Person() {
		this(null);
	}

	public Person(String name) {
		this.name=new SimpleStringProperty(name);

		// 테스트용 더미데이터.
		this.SID = new SimpleStringProperty("2017E7443");
		this.major = new SimpleStringProperty("CS");
		this.majorScore = new SimpleIntegerProperty(80);
		this.liberalScore = new SimpleIntegerProperty(76);
		this.crnafScore = new SimpleIntegerProperty(92);
	}


	/******************
	 * get, set
	 *
	 ******************/
	public String getName() {
		return name.get();
	}
	public void setName(String name) {
		this.name.set(name);
	}


	public String getSID() {
		return SID.get();
	}
	public void setSID(String SID) {
		this.SID.set(SID);
	}

	public String getMajor() {
		return major.get();
	}
	public void setMajor(String major) {
		this.major.set(major);
	}

	public Integer getMajorScore() {
		return majorScore.get();
	}
	public void setMajorScore(Integer majorScore) {
		this.majorScore.set(majorScore);
	}

	public Integer getLiberalScore() {
		return liberalScore.get();
	}
	public void setLiberalScore(Integer liberalScore) {
		this.liberalScore.set(liberalScore);
	}

	public Integer getCrnafScore() {
		return crnafScore.get();
	}
	public void setCrnafScore(Integer crnafScore) {
		this.crnafScore.set(crnafScore);
	}


	public StringProperty nameProperty() {
		return name;
	}
	public StringProperty SIDProperty() {
		return SID;
	}
	public StringProperty majorProperty() {
		return major;
	}
	public IntegerProperty majorScoreProperty() {
		return majorScore;
	}
	public IntegerProperty liberalScoreProperty() {
		return liberalScore;
	}
	public IntegerProperty crnafScoreProperty() {
		return crnafScore;
	}

}
