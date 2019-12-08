package application.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
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
		this.SID = new SimpleStringProperty("");
		this.major = new SimpleStringProperty("");
		this.majorScore = new SimpleIntegerProperty(0);
		this.liberalScore = new SimpleIntegerProperty(0);
		this.crnafScore = new SimpleIntegerProperty(0);
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
	public int getTotalScore(){
		return this.getMajorScore()+this.getLiberalScore()+this.getCrnafScore();
	}
	public double getAverageScore(){
		return this.getTotalScore()/(double)3;
	}
	public char getGrade(int scoreTen){
		switch (scoreTen){
			case 10:
			case 9: return 'A';
			case 8:	return 'B';
			case 7:
			case 6: return 'C';
			case 5: return 'D';
			default: return 'F';
		}
	}
	public char getMajorGrade() {
		return getGrade((int)getMajorScore()/10);
	}
	public char getLiberalGrade() {
		return getGrade((int)getLiberalScore()/10);
	}public char getCrnafGrade() {
		return getGrade((int)getCrnafScore()/10);
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
