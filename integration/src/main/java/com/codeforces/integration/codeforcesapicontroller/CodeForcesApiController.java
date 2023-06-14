package com.codeforces.integration.codeforcesapicontroller;

import com.codeforces.integration.codeforcesapi.CodeForcesMethods;
import com.codeforces.integration.codeforcesapi.CodeForcesResponse;
import com.codeforces.integration.codeforcesapi.RequestGenerator;
import java.util.ArrayList;
import java.util.HashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/")
@Slf4j
public class CodeForcesApi {

  @GetMapping("getUserInfo")
  public ResponseEntity<CodeForcesResponse> getUsers(@RequestBody HashMap<String, ArrayList<String>> handleNames) {
    String request = RequestGenerator.composeRequest(CodeForcesMethods.USER_INFO, handleNames);
      log.info("Trying with request :: {}", request);
    RestTemplate restTemplate = new RestTemplate();
    ResponseEntity result = null;
    try {
      result = restTemplate.exchange(request, HttpMethod.GET, null, CodeForcesResponse.class);
    } catch (Exception e) {
      log.info("Exception occurred :: {}", e.getMessage());
    }
    if(result == null) {
      return new ResponseEntity<>(new CodeForcesResponse("Null", "Null", "Null"), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    if(result.getStatusCode() == HttpStatus.BAD_REQUEST) {
      return new ResponseEntity<>((CodeForcesResponse)result.getBody(), HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<>((CodeForcesResponse)result.getBody(), HttpStatus.ACCEPTED);
  }
  /*
  Getting user status from codeforces api
   */
  @GetMapping("getUserStatus")
  public ResponseEntity<CodeForcesResponse> getUserStatus(@RequestParam  String handleName,
      @RequestParam String from, @RequestParam String count) {
    RestTemplate restTemplate = new RestTemplate();
    String request = RequestGenerator.composeRequest(CodeForcesMethods.USER_STATUS, handleName, from, count);
    log.info("Trying with request :: {}", request);
    ResponseEntity result = null;
    try {
        result = restTemplate.exchange(request, HttpMethod.GET, null, CodeForcesResponse.class);
    } catch (Exception e) {
        log.info("Exception occurred :: {}", e.getMessage());
    }
    if(result == null) {
      return new ResponseEntity<>(new CodeForcesResponse("Null", "Null", "Null"), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    if(result.getStatusCode() == HttpStatus.BAD_REQUEST) {
      return new ResponseEntity<>((CodeForcesResponse)result.getBody(), HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<>((CodeForcesResponse)result.getBody(), HttpStatus.ACCEPTED);
  }

}
