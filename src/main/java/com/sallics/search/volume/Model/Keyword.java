package com.sallics.search.volume.Model;


import lombok.Builder;
import lombok.Data;


/**
 * Keyword class to create keyword object
 *
 * this class will be returned to the controller and mapped to JSON directly
 *
 * @author mustafa
 *
 *
 */
@Data
@Builder
public class Keyword {

    /**
     * The keyword that is passed to the application to know the search volume for it
     */
    private String keyword;

    /**
     *  is the value of search-volume for a certain keyword
     *  the score is integer between 0-100
     */
    private int score;

    public Keyword(String keyword, int score) {
        this.keyword = keyword;
        this.score = score;
    }

    public Keyword() {
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
