package com.sallics.search.volume.Controllers;


import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.sallics.search.volume.ErrorHandling.ApiError;
import com.sallics.search.volume.Model.Keyword;
import com.sallics.search.volume.Services.SearchVolumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;


/**
 * This is Controller class to receive and map HTTP requests
 * @author mustafa
 */

@RestController
@RequestMapping("/search-volume")
public class SearchVolumeController {


    @Autowired
    SearchVolumeService searchVolumeService;

    /**
     * the function is mapped to Get request to path /search-volume
     * @param keyWord is passed as string variable
     * @return {@link ResponseEntity<?>} with a body of Keyword or ApiError
     * @throws Exception
     *
     * this function is monitored by Hystrix circuit breaker, if the execution time takes more than 10 second, An error will be returned
     *
     * the value of the timeout duration can be changed by changing the value of @HystrixProperty-timeoutInMilliseconds
     *
     */
    @HystrixCommand(fallbackMethod = "timedOut", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000")
    })
    @GetMapping
    public ResponseEntity<?> getSearchVolume(@RequestParam(value = "keyword") String keyWord) throws Exception {
        return ResponseEntity.ok().body( searchVolumeService.getSearchVolume(keyWord));
    }


    /**
     * this is fallback function which will be called by hystrix in case of circuit breaking
     *
     * it has been customised to return ResponseEntity with a body of {@link ApiError}
     *
     * @param keyWord
     * @return {@link ResponseEntity<ApiError>}
     */
    public ResponseEntity<?> timedOut(String keyWord) {

        return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).body(
                ApiError.builder()
                        .timestamp(LocalDateTime.now())
                        .exception("TimeOut Exception")
                        .message("Request handling took more than 10 seconds")
                        .status(HttpStatus.REQUEST_TIMEOUT)
                .build()
        );
    }









}
