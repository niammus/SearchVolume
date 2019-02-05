# ReadME 

implemented micro-service using 

@Java_8
@spring boot 2.0 



## Requirments 

you have to implement a microservice with a single REST endpoint. This endpoint should receive a single keyword as an input and should return a score for that same exact keyword. The score should be in the range [0 → 100] and represent the estimated search-volume (how often Amazon customers search for that exact keyword). A score of 0 means that the keyword is practically never searched for, 100 means that this is one of the hottest keywords in all of amazon.com right now.

Example API Request (note: 87 is not the real score)
REQUEST GET http://localhost:8080/estimate?keyword=iphone+charger
RESPONSE 
{
	“Keyword”:”iphone charger”,
	“score”:87
}

## Amazon Completetion service 

Amazon do not provide any information on the search-volume of keywords. It is a well-kept company secret. Also, cross-references to search-volumes from other search-engines like google are not accurate, since the search behavior for product discovery is fundamentally different from general search. The only hint we get from Amazon about which keywords are important comes from their AJAX autocomplete API. You can reverse-engineer how it works by typing text into the search-box on amazon.com.

Amazon Autocomplete API 
https://completion.amazon.com/search/complete

### Assumptions

- For any search input, Amazon will only return up to 10 keywords, that have an exact prefix-match with the input.
- Any keyword with a relevant search-volume can be returned by the API.
- Whenever the API is called, it operates in 2 steps:
    - Seek: Get all known keywords that match the prefix and create a Candidate-Set
    - Sort/Return: Sort the Candidate-Set by search-volume and return the top 10 results.
    
Hint: the order of the 10 returned keywords is comparatively insignificant!


## Algorithm 

the score of the search-volume of a certain keyword is calculation depends on how many occurrences of this keyword within the response. 

the Algorithm consumes the ten returned words/sentences(search sentences) from Amazon. For an occurrence of the Keyword within the sentence, the algorithm adds 9 points. So for 10 occurrences, the score is 90 points. If the sentence has more than one occurrence -within the same sentence- then, it's calculated as one. 

If one of the returned sentences matches the keyword exactley. that adds 19 points to the score.  

#### Example
Case 1: 
If the keyword is "iPhone" and one of the returned Strings from Amazon is "iphone" the this adds 19 points. And the rest is 'iphone' within a sentence. Like. 'iphone 6s' or 'iphone cover'. then each occurrence adds 9 points. 


Assuming that there's 9 occurance (9x9=81) +  19  (for single word 'iphone') = 100 

Case: 2 
If the keyword is "laptop", and all the returned Strings where 'laptop' + other words. Like, "laptop think pad", "laptop 13 inches" or "laptop core i7" .. etc. then the score is 90. 

Why exact matching returns 19 points? 

Well, the idea is to give exact matching a full score over partial matching of 90 points. But again, why? 

the service is design to estimate the search volume of a certain keyword. So, If we want to know how trending is 'iphone' right now. searches like, 'iphone charger' and 'iphone cable' does reflect the customers' interest, but a search word like 'iphone' gives a complete score. because in the earlier example. the search could be leading to something else. but an exact matching reveals that one of the hottest searches on Amazon is 'iphone'. 

## Accuracy

The accuracy for this solution is not best but it's not very bad! 

it's not accurate because Amazon is not returning how many searches for 'iphone' vs 'coffee' so for 'iphone' could be 1M searches, on the other hand, 'coffe' could have 1k searches. And both would achieve 100 points on this algorithm. hiding the search-volumes make it difficult to estimate the exact score. 

however, it's a very useful and simple tool to have a rough estimation using Amazon's real-time data. 

# Future Work

AI machine learning or NLP libraries can be used to process the returned results. for example while searching for 'iphone', 'iphone red', iphone 6' and 'iphone x plus' can be counted. Conversely, 'iphone charger' can be discarded. 
