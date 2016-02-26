package kr.pe.jady.wordict.domain.model;

/**
 * Created by imc038 on 2016. 2. 25..
 */
public class Word {
    private int id;
    private String korean;
    private String english;
    private String abbrKorean;
    private String abbrEnglish;
    private String description;

    public Word(String korean, String english, String abbrKorean, String abbrEnglish, String description) {
        this.korean = korean;
        this.english = english;
        this.abbrKorean = abbrKorean;
        this.abbrEnglish = abbrEnglish;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKorean() {
        return korean;
    }

    public void setKorean(String korean) {
        this.korean = korean;
    }

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    public String getAbbrKorean() {
        return abbrKorean;
    }

    public void setAbbrKorean(String abbrKorean) {
        this.abbrKorean = abbrKorean;
    }

    public String getAbbrEnglish() {
        return abbrEnglish;
    }

    public void setAbbrEnglish(String abbrEnglish) {
        this.abbrEnglish = abbrEnglish;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Word{" +
                "id=" + id +
                ", korean='" + korean + '\'' +
                ", english='" + english + '\'' +
                ", abbrKorean='" + abbrKorean + '\'' +
                ", abbrEnglish='" + abbrEnglish + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
