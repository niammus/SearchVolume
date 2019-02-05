package com.sallics.search.volume.Services;


import com.sallics.search.volume.Clients.AmazonClient;
import com.sallics.search.volume.Model.Keyword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Service class responsible for all business logic
 *
 * @author mustafa
 *
 */
@Service
public class SearchVolumeService {

    @Autowired
    AmazonClient amazonClient;


    /**
     * invoke webClient, process the response
     * @param keyWord
     * @return KeyWord consists of String keyword and int score
     * @throws Exception
     */
    public Keyword getSearchVolume(String keyWord) throws Exception {

        List<String> response = retrieveDataFromAmazonResponse(
                amazonClient.callAmazonCompletionService(keyWord));

        int score = calculateSearchVolume(response, keyWord);

        return  Keyword.builder()
                .score(score)
                .keyword(keyWord)
                .build();
    }


    /**
     * returns score value after processing the list of words retrieved from Amazon's
     * completion-service response
     * it's internally called from {@link #getSearchVolume(String keyWord)}
     * @param listOfWords
     * @param keyword
     * @return int Score
     */
    private int calculateSearchVolume(List<String> listOfWords, String keyword) {
        int score = 0;

        String singleKeyword = ("\""+keyword+" ");
        String quotedKeyword = ("\""+keyword+"\"");

        for (String word : listOfWords) {
            if (word.equals(quotedKeyword)) {
                score = score + 19;
            } else if (word.contains(singleKeyword)) {
                score = score + 9;
            }
        }
        return score;
    }


    /**
     * converts the string response of Amazon's completion service to list of words
     * it's internally called from {@link #getSearchVolume(String keyWord)}
     * @param response
     * @return List<String> which is the list of matching words(or sentences)
     */
    private List<String> retrieveDataFromAmazonResponse(String response) {

        response = response.replaceAll("\\[\\\"%s\\\",\\[","")
                            .replaceAll("\\],\\[.*?\\\"\\]","");

        return Stream.of(response.split(","))
                .map (String::new)
                .collect(Collectors.toList());


    }








}
