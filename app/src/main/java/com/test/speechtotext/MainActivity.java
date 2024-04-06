package com.test.speechtotext;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.test.speechtotext.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private SpeechRecognizer speechRecognizer;
    //String check="I want to order two hamburgers with cheese";
   // String check="I want to order two hamburgers";
    List<String> STATIC_LIST = Arrays.asList("Apple", "Banana", "Cherry","pizza","hamburger","burger","mayo","cold drink");

     private static final Map<String, Integer> numberWords = new HashMap<>();

    static {
        numberWords.put("zero", 0);
        numberWords.put("one", 1);
        numberWords.put("two", 2);
        numberWords.put("three", 3);
        numberWords.put("four", 4);
        numberWords.put("five", 5);
        numberWords.put("six", 6);
        numberWords.put("seven", 7);
        numberWords.put("eight", 8);
        numberWords.put("nine", 9);
        // Add more mappings as needed
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        speechRecognizer.setRecognitionListener(new MyRecognitionListener());
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startVoiceRecognition();
            }
        });
       //Log.e("number value is >>",""+replaceNumberStrings(check));

       /* Log.e("number value is >>",""+findNumber(replacedText));
        Log.e("number value is >>",""+getFirstWordAfterNumber(replacedText));*/
        ///Log.e("number value is >>",""+splitBeforeAndAfterWith(replacedText));


        /*String[] parts = splitBeforeAndAfterWith(getTextAfterNumber(replacedText));

        if (parts.length == 2) {
            String beforeWith = parts[0].trim(); // String before "with"
            String afterWith = parts[1].trim(); // String after "with"

            System.out.println("Before 'with': " + beforeWith);
            System.out.println("After 'with': " + afterWith);
            Log.e("number value is >>",""+beforeWith);
            Log.e("number value is >>",""+afterWith);
        }
*/

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (result != null && result.size() > 0) {
                String check = result.get(0);
               // binding.searchTxt.setText(check);

                Log.e("final value is >>",""+check);
                String replacedText = replaceNumberStrings(check);
                Log.e("replacedTextis >>>>>>",""+replacedText);
                if(replacedText.toLowerCase().contains("and")|| replacedText.toLowerCase().contains("or")|| replacedText.toLowerCase().contains("with")){
                    Log.e("getTextAfterNumber >",""+getTextAfterNumber(replacedText));


                       String[] parts = splitBeforeAndAfterWith(getTextAfterNumber(replacedText)!=null?getTextAfterNumber(replacedText):replacedText);
                       String first = "";
                       if (parts.length == 2) {
                           String beforeWith = parts[0].trim(); // String before "with"
                           String afterWith = parts[1].trim(); // String after "with"

                           System.out.println("Before 'with': " + beforeWith);
                           System.out.println("After 'with': " + afterWith);
                           Log.e("number value is >>", "" + beforeWith);
                           Log.e("number value is >>", "" + afterWith);


                           if (replaceNumberStrings(beforeWith) != null) {
                               String replacedText_before = replaceNumberStrings(beforeWith);
                               Log.e("replacedText_before>>>",""+replacedText_before);
                               if (findNumber(replacedText_before) != null) {
                                   String number = findNumber(replacedText_before);
                                   if (replacedText_before.contains("with") || replacedText_before.contains("include") || replacedText_before.contains("included")) {

                                       List<String> words = extractWordsBeforeAndAfterWith(getTextAfterNumber(replacedText_before));

                                       if (words.size() == 2) {
                                           System.out.println("Word before 'with': " + words.get(0));
                                           System.out.println("Word after 'with': " + words.get(1));
                                           first = number + " " + words.get(0) + " with " + words.get(1);
                                           //   binding.searchTxt.setText(number+" " +words.get(0)+" with "+words.get(1));
                                           Log.e("number value is >>", "" + number + " " + words.get(0) + " with " + words.get(1));
                                       } else {
                                           System.out.println("No 'with' found in the sentence.");

                                       }


                                   } else {
                                       if (extractWordsafterNumber(beforeWith) != null) {
                                           Log.e("number value is >>", "" + number + " " + extractWordsafterNumber(beforeWith));
                                           // binding.searchTxt.setText(""+ number + " " + extractWordsafterNumber(beforeWith));
                                           first = extractWordsafterNumber(beforeWith);
                                       }
                                   }

                               }
                               else {
                                 //  Toast.makeText(MainActivity.this, "Invalid Order formate", Toast.LENGTH_LONG).show();
                                   binding.searchTxt.setText("1 "+beforeWith +" with "+afterWith);

                                 //  showAlert("Invalid Order format !");
                               }
                           }


                           if (replaceNumberStrings(afterWith) != null) {
                               String replacedText_after = replaceNumberStrings(afterWith);
                               if (findNumber(replacedText_after) != null) {
                                   String number = findNumber(replacedText_after);
                                   if (replacedText_after.contains("with") || replacedText_after.contains("include") || replacedText_after.contains("included")) {

                                       List<String> words = extractWordsBeforeAndAfterWith(getTextAfterNumber(replacedText_after));

                                       if (words.size() == 2) {
                                           System.out.println("Word before 'with': " + words.get(0));
                                           System.out.println("Word after 'with': " + words.get(1));
                                           // first=number+" " +words.get(0)+" with "+words.get(1);
                                           if (!first.equals("")) {
                                               binding.searchTxt.setText("" + first + "\n\n" + number + " " + words.get(0) + " with " + words.get(1));
                                           } else {
                                               binding.searchTxt.setText(number + " " + words.get(0) + " with " + words.get(1));
                                           }

                                           Log.e("number value is >>", "" + number + " " + words.get(0) + " with " + words.get(1));
                                       } else {
                                           System.out.println("No 'with' found in the sentence.");

                                       }


                                   } else {
                                       if (extractWordsafterNumber(afterWith) != null) {
                                           Log.e("number value is >>", "" + number + " " + extractWordsafterNumber(afterWith));
                                           if (!first.equals("")) {
                                               binding.searchTxt.setText("" + first + "\n\n" + number + " " + extractWordsafterNumber(afterWith));
                                           } else {
                                               binding.searchTxt.setText("" + number + " " + extractWordsafterNumber(afterWith));
                                           }
                                       }
                                   }

                               }
                               else {
                                  /* Toast.makeText(MainActivity.this, "Invalid Order formate", Toast.LENGTH_LONG).show();
                                   binding.searchTxt.setText("Invalid Order format ! ");
                                   showAlert("Invalid Order format !");*/
                                   binding.searchTxt.setText("1 "+beforeWith +" with "+afterWith);
                               }
                           } else {

                               binding.searchTxt.setText(check);
                           }

                       }



                }

                else {
                    if(replaceNumberStrings(check)!=null ){

                    if(findNumber(replacedText)!=null) {
                        String number = findNumber(replacedText);

                        if(replacedText.contains("with") ||replacedText.contains("include")||replacedText.contains("included")){




                            List<String> words = extractWordsBeforeAndAfterWith(getTextAfterNumber(replacedText));

                            if (words.size() == 2) {
                                System.out.println("Word before 'with': " + words.get(0));
                                System.out.println("Word after 'with': " + words.get(1));
                                binding.searchTxt.setText(number+" " +words.get(0)+" with "+words.get(1));
                                Log.e("number value is >>",""+number+" " +words.get(0)+" with "+words.get(1));
                            } else {
                                System.out.println("No 'with' found in the sentence.");

                            }



                        }

                        else {
                            if( extractWordsafterNumber(replacedText)!=null) {
                                Log.e("number value is >>", "" + number + " " + extractWordsafterNumber(replacedText));
                                binding.searchTxt.setText(""+ number + " " + extractWordsafterNumber(replacedText));
                            }
                        }

                    }else {


                        if(replacedText.contains("with") ||replacedText.contains("include")||replacedText.contains("included")){

                           if(extractWordsBeforeAndAfterWith(getTextAfterNumber(replacedText)) !=null) {
                               List<String> words = extractWordsBeforeAndAfterWith(getTextAfterNumber(replacedText));

                               if (words.size() == 2) {
                                   System.out.println("Word before 'with': " + words.get(0));
                                   System.out.println("Word after 'with': " + words.get(1));
                                   binding.searchTxt.setText(" " + words.get(0) + " with " + words.get(1));
                                   // Log.e("number value is >>",""+number+" " +words.get(0)+" with "+words.get(1));
                               } else {


                                   System.out.println("No 'with' found in the sentence.");

                               }
                           }


                        }else {
                            if(STATIC_LIST.contains(check) ){
                                binding.searchTxt.setText("1 "+check);
                            }else {
                            Toast.makeText(MainActivity.this,"Invalid Order formate",Toast.LENGTH_LONG).show();
                            binding.searchTxt.setText("Invalid Order format ! ");}
                        }
                    }
                }
                    else {
                        if(replacedText.contains("with") ||replacedText.contains("include")||replacedText.contains("included")){

                            List<String> words = extractWordsBeforeAndAfterWith(getTextAfterNumber(replacedText));

                            if (words.size() == 2) {
                                System.out.println("Word before 'with': " + words.get(0));
                                System.out.println("Word after 'with': " + words.get(1));
                                binding.searchTxt.setText(" " +words.get(0)+" with "+words.get(1));
                               // Log.e("number value is >>",""+number+" " +words.get(0)+" with "+words.get(1));
                            } else {
                                System.out.println("No 'with' found in the sentence.");

                            }



                        }else {
                            if(STATIC_LIST.contains(check) ){
                                binding.searchTxt.setText("1 "+check);
                            }else {

                                Toast.makeText(MainActivity.this, "Invalid Order formate", Toast.LENGTH_LONG).show();
                                binding.searchTxt.setText("Invalid Order format ! ");
                            }
                        }
                    }

                }

            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }


    private void startVoiceRecognition() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US");
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak something");
       

        try {
            startActivityForResult(intent, 100);
        } catch (ActivityNotFoundException e) {
            // Handle the exception if no speech recognition activity is found
        }
    }

    private class MyRecognitionListener implements RecognitionListener {
        @Override
        public void onReadyForSpeech(Bundle params) {

        }

        @Override
        public void onBeginningOfSpeech() {

        }

        @Override
        public void onRmsChanged(float rmsdB) {

        }

        @Override
        public void onBufferReceived(byte[] buffer) {

        }

        @Override
        public void onEndOfSpeech() {

        }

        @Override
        public void onError(int error) {

        }

        @Override
        public void onResults(Bundle results) {

        }

        @Override
        public void onPartialResults(Bundle partialResults) {

        }

        @Override
        public void onEvent(int eventType, Bundle params) {

        }
        // Implement methods from RecognitionListener interface if needed
        // For example: onReadyForSpeech, onBeginningOfSpeech, onEndOfSpeech, onError, onResults, etc.
    }


    public static String findNumber(String sentence) {
        // Regular expression pattern to match any digit
       // Pattern pattern = Pattern.compile("\\d+");
        Pattern pattern = Pattern.compile("\\b\\d+\\b");
        Matcher matcher = pattern.matcher(sentence);

        // If matcher finds any matches, return the first number found
        if (matcher.find()) {
            return matcher.group();
        } else {
            return null; // Return null if no number is found
        }
    }
    public static String replaceNumberStrings(String text) {

        // Regular expression pattern to match any number string
        Pattern pattern = Pattern.compile("\\b(?:zero|one|two|three|four|five|six|seven|eight|nine)\\b", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(text);

        // Iterate over matches and replace each number string with its numeric value
        StringBuffer result = new StringBuffer();
        while (matcher.find()) {
            String numberString = matcher.group().toLowerCase();
            Integer number = numberWords.get(numberString);
            if (number != null) {
                matcher.appendReplacement(result, number.toString());
            }
        }
        matcher.appendTail(result);

        return result.toString();
    }

    public static String getFirstWordAfterNumber(String text) {
        // Regular expression pattern to match a number followed by a word
        Pattern pattern = Pattern.compile("\\b\\d+\\s+\\w+\\b");
        Matcher matcher = pattern.matcher(text);

        // If matcher finds any matches, return the first word after the number found
        if (matcher.find()) {
            // Extract the word after the number
            String matchedText = matcher.group();
            String[] words = matchedText.split("\\s+"); // Split by whitespace
            if (words.length >= 2) {
                return words[1]; // Return the second word (first word after the number)
            }
        }
        return null; // Return null if no match is found or if the pattern does not match the expected format
    }
    public static String[] splitBeforeAndAfterWith(String sentence) {
        // Split the sentence by "with"
        String[] parts = new String[0];
        if(sentence.toLowerCase().contains("with")){
         parts = sentence.split("\\bwith\\b", 2);
        }else  if(sentence.toLowerCase().contains("include")){
         parts = sentence.split("\\binclude\\b", 2);
        }else  if(sentence.toLowerCase().contains("included")){
         parts = sentence.split("\\bincluded\\b", 2);
        }else  if(sentence.toLowerCase().contains("and")){
         parts = sentence.split("\\and\\b", 2);
        }else  if(sentence.toLowerCase().contains("or")){
         parts = sentence.split("\\bor\\b", 2);
        }

        // Return the parts
        return parts;
    }
    public static String getTextAfterNumber(String text) {
        // Regular expression pattern to match a number followed by any text
        Pattern pattern = Pattern.compile("\\b\\d+\\s*(.*)");
        Matcher matcher = pattern.matcher(text);

        // If matcher finds any matches, return the text after the number found
        if (matcher.find()) {
            // Group 1 contains the text after the number
            return matcher.group(1);
        }
        return null; // Return null if no match is found
    }

    public static List<String> extractWordsBeforeAndAfterWith(String sentence) {
        try {
            List<String> words = new ArrayList<>();

            // Split the sentence into words
            String[] wordArray = sentence.split("\\s+");

            // Find the index of "with" in the array
            int withIndex = -1;
            for (int i = 0; i < wordArray.length; i++) {
                if (wordArray[i].equalsIgnoreCase("with") || wordArray[i].equalsIgnoreCase("include") || wordArray[i].equalsIgnoreCase("included")) {
                    withIndex = i;
                    break;
                }
            }

            // If "with" is found, get the word before and after it
            if (withIndex != -1) {
                // Get the word before "with"
                if (withIndex > 0) {
                    words.add(wordArray[withIndex - 1]);
                } else {
                    words.add(""); // Add an empty string if no word before "with"
                }

                // Get the word after "with"
                if (withIndex < wordArray.length - 1) {
                    words.add(wordArray[withIndex + 1]);
                } else {
                    words.add(""); // Add an empty string if no word after "with"
                }
            }

            return words;
        }catch (Exception e){}
        return  null;
    }

    public String extractWordsafterNumber(String input) {


        // Define the pattern to match a number followed by a string
        Pattern pattern = Pattern.compile("\\d+(.*)");

        // Create a matcher with the input string
        Matcher matcher = pattern.matcher(input);

        // Check if a match is found
        if (matcher.find()) {
            // Extract the string after the number
            String result = matcher.group(1).trim();

            // Output the result
            System.out.println("String after number: " + result);
            return result;
        } else {
            System.out.println("No match found.");
            return null;
        }

    }


    public void showAlert(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Alert")
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Handle positive button click
                    }
                });
                /*.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Handle cancel button click
                        dialog.dismiss();
                    }
                });*/
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}