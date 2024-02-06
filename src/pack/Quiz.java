package pack;

import java.net.*;
import java.util.*;
import org.json.simple.parser.*;
import org.json.simple.*;

public class Quiz {
 
    JSONArray quizData, jsonWrongAnswer; 
    JSONObject dataObject, jsonString;
    JSONParser parse;
    Scanner scan;
    String urlResponse, question[], correctAnswer[], option[][];
    int responseCode, index;
    HttpURLConnection con;
    URL url;

    public Quiz(){

        try{
            url = new URL("https://opentdb.com/api.php?amount=10&category=9&difficulty=medium&type=multiple");
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();

            responseCode = con.getResponseCode();

            if(responseCode != 200){
                throw new RuntimeException("HttpResponseCode: " + responseCode); 
            }
            else {
                urlResponse = new String();
                Thread.sleep(30000); // Wait for 30 seconds before sending the next request
                scan = new Scanner(url.openStream());

                while(scan.hasNext()){
                    urlResponse = scan.nextLine();
                }

                scan.close();

                parse  = new JSONParser();
                dataObject = (JSONObject) parse.parse(urlResponse);
                quizData = (JSONArray) dataObject.get("results");
                
                question  = new String[10];
                correctAnswer  = new String[10];
                option  = new String[10][4];

                for(int i = 0; i < quizData.size(); i++) {
                    jsonString =  (JSONObject) quizData.get(i);
                    question[i] = (String) jsonString.get("question");
                    correctAnswer[i] = (String) jsonString.get("correct_answer");
                    jsonWrongAnswer = (JSONArray) jsonString.get("incorrect_answers");
                    index = (int)(Math.random() * 3);
                    jsonWrongAnswer.add(index, jsonString.get("correct_answer"));
                    for(int j = 0; j < 4; j++) {
                        option[i][j] = (String) jsonWrongAnswer.get(j);
                    }
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public String[] getQuestions(){
        return question;
    }

    public String[] getCorrectAnswer(){
        return correctAnswer;
    }

    public String[][] getOptions(){
        return option;
    }
}