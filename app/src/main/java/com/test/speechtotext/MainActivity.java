package com.test.speechtotext;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

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
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.test.speechtotext.adapter.ItemListAdapter;
import com.test.speechtotext.adapter.NotFoundItemListAdapter;
import com.test.speechtotext.databinding.ActivityMainBinding;
/*
import com.test.speechtotext.model.Example;
*/
import com.test.speechtotext.model.ItemSelected;
import com.test.speechtotext.model.newModel.Child;
import com.test.speechtotext.model.newModel.Child__1;
import com.test.speechtotext.model.newModel.Example;
import com.test.speechtotext.model.newModel.Product.Modifier;
import com.test.speechtotext.model.newModel.Product.Product;
import com.test.speechtotext.model.newModel.Product.Size;
import com.test.speechtotext.requestModel.CompletionRequest;
import com.test.speechtotext.requestModel.Message;
import com.test.speechtotext.utility.AppConfig;
import com.test.speechtotext.utility.AppConfig_second;
import com.test.speechtotext.utility.ErrorMessage;
import com.test.speechtotext.utility.NetworkUtil;

import android.view.Menu;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private SpeechRecognizer speechRecognizer;
    //String check="I want to order two hamburgers with cheese";
    // String check="I want to order two hamburgers";
    List<String> STATIC_LIST = Arrays.asList("the", "with", "and", "as", "a", "like", "or", "i", "not", "have", "am", "found", "like", "would", "can", "was", "to", "we", "one", "more");
    /*List<com.test.speechtotext.model.newModel.Menu> menuItems = new ArrayList<>();*/
    List<ItemSelected> item_LIST = new ArrayList<>();

    Example example;
    ItemListAdapter side_rv_adapter;

    ArrayList<String> noFoundItem = new ArrayList<>();
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


    List<com.test.speechtotext.model.newModel.Menu> orderMenus = new ArrayList<>();
    List<Child> orderChild = new ArrayList<>();
    List<Product> orderProduct = new ArrayList<>();
    List<Child__1> orderSecondChild = new ArrayList<>();
    List<Product> orderSecondProduct = new ArrayList<>();
    List<Size> orderSize = new ArrayList<>();
    List<Modifier> orderModifier = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        item_LIST.clear();
        noFoundItem.clear();
        getPrice();
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
        binding.clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.actualSearchTxt.setText("");
                binding.searchTxt.setText("");
                if (side_rv_adapter != null && item_LIST.size() > 0) {
                    item_LIST.clear();
                    side_rv_adapter.notifyDataSetChanged();
                }
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
                binding.actualSearchTxt.setText(check);
                // aiResponse();

                extractProductAndMatch(check, example.getMenus());
                Log.e("final value is >>", "" + check);


              /*  String replacedText = replaceNumberStrings(check);
                Log.e("replacedTextis >>>>>>", "" + replacedText);
                if (replacedText.toLowerCase().contains("and") || replacedText.toLowerCase().contains("or") || replacedText.toLowerCase().contains("with")) {
                    Log.e("getTextAfterNumber >", "" + getTextAfterNumber(replacedText));


                    String[] parts = splitBeforeAndAfterWith(getTextAfterNumber(replacedText) != null ? getTextAfterNumber(replacedText) : replacedText);
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
                            Log.e("replacedText_before>>>", "" + replacedText_before);
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

                            } else {
                                //  Toast.makeText(MainActivity.this, "Invalid Order formate", Toast.LENGTH_LONG).show();
                                binding.searchTxt.setText("1 " + beforeWith + " with " + afterWith);

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

                            } else {
                                  *//* Toast.makeText(MainActivity.this, "Invalid Order formate", Toast.LENGTH_LONG).show();
                                   binding.searchTxt.setText("Invalid Order format ! ");
                                   showAlert("Invalid Order format !");*//*
                                binding.searchTxt.setText("1 " + beforeWith + " with " + afterWith);
                            }
                        } else {

                            binding.searchTxt.setText(check);
                        }

                    }


                } else {
                    if (replaceNumberStrings(check) != null) {

                        if (findNumber(replacedText) != null) {
                            String number = findNumber(replacedText);

                            if (replacedText.contains("with") || replacedText.contains("include") || replacedText.contains("included")) {


                                List<String> words = extractWordsBeforeAndAfterWith(getTextAfterNumber(replacedText));

                                if (words.size() == 2) {
                                    System.out.println("Word before 'with': " + words.get(0));
                                    System.out.println("Word after 'with': " + words.get(1));
                                    binding.searchTxt.setText(number + " " + words.get(0) + " with " + words.get(1));
                                    Log.e("number value is >>", "" + number + " " + words.get(0) + " with " + words.get(1));
                                } else {
                                    System.out.println("No 'with' found in the sentence.");

                                }


                            } else {
                                if (extractWordsafterNumber(replacedText) != null) {
                                    Log.e("number value is >>", "" + number + " " + extractWordsafterNumber(replacedText));
                                    binding.searchTxt.setText("" + number + " " + extractWordsafterNumber(replacedText));
                                }
                            }

                        } else {


                            if (replacedText.contains("with") || replacedText.contains("include") || replacedText.contains("included")) {

                                if (extractWordsBeforeAndAfterWith(getTextAfterNumber(replacedText)) != null) {
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


                            } else {
                                if (STATIC_LIST.contains(check)) {
                                    binding.searchTxt.setText("1 " + check);
                                } else {
                                    Toast.makeText(MainActivity.this, "Invalid Order formate", Toast.LENGTH_LONG).show();
                                    binding.searchTxt.setText("Invalid Order format ! ");
                                }
                            }
                        }
                    }
                    else {
                        if (replacedText.contains("with") || replacedText.contains("include") || replacedText.contains("included")) {

                            List<String> words = extractWordsBeforeAndAfterWith(getTextAfterNumber(replacedText));

                            if (words.size() == 2) {
                                System.out.println("Word before 'with': " + words.get(0));
                                System.out.println("Word after 'with': " + words.get(1));
                                binding.searchTxt.setText(" " + words.get(0) + " with " + words.get(1));
                                // Log.e("number value is >>",""+number+" " +words.get(0)+" with "+words.get(1));
                            } else {
                                System.out.println("No 'with' found in the sentence.");

                            }


                        } else {
                            if (STATIC_LIST.contains(check)) {
                                binding.searchTxt.setText("1 " + check);
                            } else {

                                Toast.makeText(MainActivity.this, "Invalid Order formate", Toast.LENGTH_LONG).show();
                                binding.searchTxt.setText("Invalid Order format ! ");
                            }
                        }
                    }

                }*/

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
        if (sentence.toLowerCase().contains("with")) {
            parts = sentence.split("\\bwith\\b", 2);
        } else if (sentence.toLowerCase().contains("include")) {
            parts = sentence.split("\\binclude\\b", 2);
        } else if (sentence.toLowerCase().contains("included")) {
            parts = sentence.split("\\bincluded\\b", 2);
        } else if (sentence.toLowerCase().contains("and")) {
            parts = sentence.split("\\and\\b", 2);
        } else if (sentence.toLowerCase().contains("or")) {
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
        } catch (Exception e) {
        }
        return null;
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

  /*  private void aiResponse() {
        try {
            if (NetworkUtil.isNetworkAvailable(MainActivity.this)) {
                final Dialog materialDialog = ErrorMessage.initProgressDialog(MainActivity.this);

                Message message = new Message("user", "convert text into restaurant order value " + binding.actualSearchTxt.getText().toString());
                List<Message> messages = new ArrayList<>();
                messages.add(message);

// Create the completion request
                CompletionRequest request = new CompletionRequest(
                        "gpt-3.5-turbo",
                        messages,
                        0.0,
                        200,
                        1.0,
                        0.0,
                        0.0,
                        Collections.singletonList("11.")
                );


                Call<ResponseBody> call = AppConfig.api_Interface().completions(request);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        ErrorMessage.E("sendToken" + response.code());
                        if (response.isSuccessful()) {
                            try {
                                // ErrorMessage.E("sendToken" + response.body().string());
                                materialDialog.dismiss();
                                try {
                                    String result = response.body().string();
                                    JSONObject obj = new JSONObject(result);
                                    ErrorMessage.E("sendToken" + obj.toString());
                                    JSONArray choicesArray = obj.getJSONArray("choices");
                                    if (choicesArray.length() > 0) {
                                        JSONObject firstChoice = choicesArray.getJSONObject(0);
                                        JSONObject message = firstChoice.getJSONObject("message");
                                        String content = message.getString("content");
                                        System.out.println("Content: " + content);
                                        //  binding.searchTxt.setText("" + content);
                                        if (menuItems != null) {
                                            for (Example menuItem : menuItems) {
                                                ErrorMessage.E("item name>>>" + menuItem.getCategoryName());
                                                ErrorMessage.E("extractOrder>>>>" + extractOrder(content).getItem());
                                                if (menuItem.getCategoryName().contains(extractOrder(content).getItem())) {
                                                    ErrorMessage.E("item name>>>" + menuItem.getCategoryName());
                                                    ErrorMessage.E("extractOrder>>>>" + extractOrder(content).getItem());

                                                    // item_LIST.add("" + extractOrder(content).getQuantity() + " $" + menuItem.getCategoryName() + menuItem.getPrice());

                                                    for (int i = 0; i < menuItem.getSubCategory().size(); i++) {
                                                        if (menuItem.getSubCategory().get(i).getSubcategoryName().contains(extractOrder(content).getItem())) {
                                                            //   item_LIST.add("" + extractOrder(content).getQuantity() + menuItem.getSubCategory().get(i).getSubcategoryName() + " $" + menuItem.getSubCategory().get(i).getSubcategoryPrice());
                                                        }
                                                    }

                                                }
                                            }
                                            ErrorMessage.E("item_LIST>>>" + item_LIST.size());
                                        } else {
                                            binding.searchTxt.setText("Item not found, please try again.");
                                        }
                                    }


                                } catch (Exception e) {
                                    ErrorMessage.E("Exception" + e.toString());
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                                materialDialog.dismiss();
                                ErrorMessage.E("Exception" + e.toString());

                            }


                        } else {
                            materialDialog.dismiss();
                            try {
                                ErrorMessage.E("sendToken else is working" + response.errorBody().string());
                            } catch (Exception e) {
                                e.printStackTrace();
                                ErrorMessage.E("error in catch" + e.toString());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        // ErrorMessage.T(getActivity(), "Response Fail");
                        System.out.println("============update profile fail  :" + t.toString());
                        materialDialog.dismiss();
                    }
                });

            } else {
                ErrorMessage.T(MainActivity.this, getResources().getString(R.string.no_internet_found));
            }
        } catch (Exception e) {
            ErrorMessage.E("Exception>>>>" + e.toString());
        }

    }*/

/*    public RestaurantOrder extractOrder(String text) {
        Pattern pattern = Pattern.compile("(\\d+)\\s+(\\w+)");
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            int quantity = Integer.parseInt(matcher.group(1));
            String item = matcher.group(2);
            return new RestaurantOrder(quantity, item);
        } else {
            int quantity = 1;
            String item = matcher.group(2);
            return new RestaurantOrder(quantity, item);
        }

    }*/

    private void getPrice() {
        try {
            if (NetworkUtil.isNetworkAvailable(MainActivity.this)) {
                final Dialog materialDialog = ErrorMessage.initProgressDialog(MainActivity.this);
                Call<ResponseBody> call = AppConfig_second.api_Interface().menus();
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        materialDialog.dismiss();
                        ErrorMessage.E("sendToken" + response.code());
                        if (response.isSuccessful()) {
                            try {
                                //  ErrorMessage.E("sendToken" + response.body().string());
                                String result = response.body().string();
                                ErrorMessage.E("sendToken" + result);
                                JSONObject JSONObject = new JSONObject(result);
                                Gson gson = new Gson();

                               example = gson.fromJson(JSONObject.toString(), Example.class);

                               /* for (int i = 0; i < jsonArray.length(); i++) {

                                    Example example = gson.fromJson(jsonArray.getString(i), Example.class);
                                    menuItems.add(example.getMenus());
                                }*/

                              //  extractProductAndMatch("I would like to have a hamburger with hamburger.", menuItems);

                            } catch (Exception e) {
                                e.printStackTrace();
                                ErrorMessage.E("Exception" + e.toString());

                            }


                        } else {
                            try {
                                ErrorMessage.E("sendToken else is working" + response.errorBody().string());
                            } catch (Exception e) {
                                e.printStackTrace();
                                ErrorMessage.E("error in catch" + e.toString());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        // ErrorMessage.T(getActivity(), "Response Fail");
                        materialDialog.dismiss();
                        System.out.println("============update profile fail  :" + t.toString());
                    }
                });

            } else {
                ErrorMessage.T(MainActivity.this, getResources().getString(R.string.no_internet_found));
            }
        } catch (Exception e) {
            ErrorMessage.E("Exception>>>>" + e.toString());
        }

    }


    /*private void extractProductAndMatch(String text, List<Example> products) {
        // Regex pattern to extract the product name and quantity
        try {
            String[] words = text.split("\\s+");

            for (int i = 0; i < words.length; i++) {

                for (int j = 0; j < products.size(); j++) {

                    Pattern pattern = Pattern.compile("\\b" + Pattern.quote(products.get(j).getCategoryName().toLowerCase()) + "\\b", Pattern.CASE_INSENSITIVE);
                    Matcher matcher = pattern.matcher(words[i].toLowerCase());
                    ItemSelected itemSelected = new ItemSelected();
                    if (matcher.find()) {
                        ErrorMessage.E("Ordered Quantity: " + products.get(j).getCategoryName() + ", Product: " + words[i]);
                        if (i > 0) {
                            if (isNumeric(words[i - 1])) {
                                ErrorMessage.E("Ordered Quantity: " + words[i - 1] + ", Product: " + words[i]);
                                // item_LIST.add("" + words[i - 1] + " " + words[i] + " $" + products.get(j).getPrice());
                                itemSelected.setItem_name("" + words[i - 1] + " " + words[i] + " $" + products.get(j).getPrice());
                            } else {
                                ErrorMessage.E("Ordered Quantity: 1" + ", Product: " + words[i]);
                                //  item_LIST.add("1" + " " + words[i] + " $" + products.get(j).getPrice());
                                itemSelected.setItem_name("1" + " " + words[i] + " $" + products.get(j).getPrice());

                            }
                        } else {
                            //   item_LIST.add("1" + " " + words[i] + " $" + products.get(j).getPrice());
                            itemSelected.setItem_name("1" + " " + words[i] + " $" + products.get(j).getPrice());
                        }

                    } else {
                        ErrorMessage.E("Product not found: ");

                        for (int k = 0; k < products.get(j).getSubCategory().size(); k++) {
                            Pattern pattern1 = Pattern.compile("\\b" + Pattern.quote(products.get(j).getSubCategory().get(k).getSubcategoryName().toLowerCase()) + "\\b", Pattern.CASE_INSENSITIVE);
                            Matcher matcher1 = pattern1.matcher(words[i].toLowerCase());
                            if (matcher1.find()) {
                                if (i > 0) {
                                    if (isNumeric(words[i - 1])) {
                                        ErrorMessage.E("Ordered Quantity: " + words[i - 1] + ", Product: " + words[i]);
                                        // item_LIST.add("" + words[i - 1] + " " + words[i] + " $" + products.get(j).getSubCategory().get(k).getSubcategoryPrice());
                                        itemSelected.setSubcategory_name("" + words[i - 1] + " " + words[i] + " $" + products.get(j).getSubCategory().get(k).getSubcategoryPrice());

                                    } else {
                                        ErrorMessage.E("Ordered Quantity: 1" + ", Product: " + words[i]);
                                        //  item_LIST.add("1" + " " + words[i] + " $" + products.get(j).getSubCategory().get(k).getSubcategoryPrice());
                                        itemSelected.setSubcategory_name("1" + " " + words[i] + " $" + products.get(j).getSubCategory().get(k).getSubcategoryPrice());
                                    }
                                } else {
                                    //  item_LIST.add("1" + " " + words[i] + " $" + products.get(j).getSubCategory().get(k).getSubcategoryPrice());
                                    itemSelected.setSubcategory_name("1" + " " + words[i] + " $" + products.get(j).getSubCategory().get(k).getSubcategoryPrice());
                                }
                            } else {
                                if (!STATIC_LIST.contains(words[i].toLowerCase()) && !products.contains(words[i].toLowerCase())) {
                                    ErrorMessage.E("Product not found: >>>>>>>" + words[i].toLowerCase());
                                    // itemSelected.setError_message(words[i].toLowerCase()+" not found");
                                    if (noFoundItem.size() > 0) {

                                        if (!noFoundItem.contains(words[i])) {
                                            if (itemSelected.getItem_name() == null || itemSelected.getSubcategory_name() == null) {
                                                ErrorMessage.E("Product not found: >>>inner>>>>" + words[i]);
                                                noFoundItem.add(words[i]);
                                            }
                                        }
                                    } else {
                                        ErrorMessage.E("Product not found: >>>inner>first>>>" + words[i]);
                                        noFoundItem.add(words[i]);
                                    }
                                }
                            }
                        }
                    }

                    if (itemSelected != null) {
                        if (itemSelected.getItem_name() != null || (itemSelected.getSubcategory_name() != null || itemSelected.getError_message() != null)) {
                            item_LIST.add(itemSelected);
                        }
                    }

                }

            }
            ErrorMessage.E("Product found: >>>>>>>" +noFoundItem.size());


            if (item_LIST.size() > 0) {
                binding.searchTxt.setVisibility(View.GONE);
                binding.listRcv.setVisibility(View.VISIBLE);
                side_rv_adapter = new ItemListAdapter(MainActivity.this, item_LIST);
                binding.listRcv.setLayoutManager(new LinearLayoutManager(MainActivity.this, RecyclerView.VERTICAL, false));
                binding.listRcv.setItemAnimator(new DefaultItemAnimator());
                binding.listRcv.scheduleLayoutAnimation();
                binding.listRcv.setNestedScrollingEnabled(false);
                binding.listRcv.setAdapter(side_rv_adapter);
                binding.listRcv.setHasFixedSize(true);
                side_rv_adapter.notifyDataSetChanged();
            } else {
                binding.searchTxt.setVisibility(View.VISIBLE);
                binding.listRcv.setVisibility(View.GONE);
                binding.searchTxt.setText("Item not found, please try again.");

            }

            if (noFoundItem.size() > 0) {
                if (noFoundItem.size() > 0 && item_LIST.size() > 0) {

                    for (int k = 0; k < item_LIST.size(); k++) {
                        Log.e("print item name>>>>>", "" + item_LIST.get(k).getItem_name());


                        if (item_LIST.get(k).getItem_name() != null) {
                            String[] words1 = item_LIST.get(k).getItem_name().split("\\s+");
                            for (int i = 0; i < words1.length; i++) {
                                for (int g = 0; g < noFoundItem.size(); g++) {
                                    ErrorMessage.E("Product found: >>>>>>>" +noFoundItem.get(g).toLowerCase()+"<><>"+ words1[i].toLowerCase());


                                    Pattern pattern1 = Pattern.compile("\\b" + Pattern.quote(noFoundItem.get(g).toLowerCase()) + "\\b", Pattern.CASE_INSENSITIVE);
                                    Matcher matcher1 = pattern1.matcher(words1[i].toLowerCase());
                                    if (matcher1.find()) {
                                        noFoundItem.remove(words1[i]);
                                    }

                                }


                            }
                        }

                        if (item_LIST.get(k).getSubcategory_name() != null) {
                            String[] words3 = item_LIST.get(k).getSubcategory_name().split("\\s+");
                            for (int i = 0; i < words3.length; i++) {
                                for (int g = 0; g < noFoundItem.size(); g++) {
                                    Pattern pattern = Pattern.compile("\\b" + Pattern.quote(noFoundItem.get(g).toLowerCase()) + "\\b", Pattern.CASE_INSENSITIVE);
                                    Matcher matcher = pattern.matcher(words3[i].toLowerCase());
                                    if (matcher.find()) {
                                        noFoundItem.remove(g);
                                    }
                                }
                            }
                        }
                    }
                }
                Log.e("print item name>>><<<>>", "" + noFoundItem.size());

                binding.notfoundListRcv.setVisibility(View.VISIBLE);
                NotFoundItemListAdapter side_rv_adapter = new NotFoundItemListAdapter(MainActivity.this, noFoundItem);
                binding.notfoundListRcv.setLayoutManager(new LinearLayoutManager(MainActivity.this, RecyclerView.VERTICAL, false));
                binding.notfoundListRcv.setItemAnimator(new DefaultItemAnimator());
                binding.notfoundListRcv.scheduleLayoutAnimation();
                binding.notfoundListRcv.setNestedScrollingEnabled(false);
                binding.notfoundListRcv.setAdapter(side_rv_adapter);
                binding.notfoundListRcv.setHasFixedSize(true);
                side_rv_adapter.notifyDataSetChanged();
            } else {
                binding.notfoundListRcv.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            Log.e("print item <<<e>>>>", "" + e.toString());
        }
    }*/

    private void extractProductAndMatch(String text, List<com.test.speechtotext.model.newModel.Menu> products) {
        // Regex pattern to extract the product name and quantity
        try {
            String[] words = text.split("\\s+");

            for (int i = 0; i < words.length; i++) {

                for (int j = 0; j < products.size(); j++) {

                    Pattern pattern = Pattern.compile("\\b" + Pattern.quote(products.get(j).getName().toLowerCase()) + "\\b", Pattern.CASE_INSENSITIVE);
                    Matcher matcher = pattern.matcher(words[i].toLowerCase());

                    if (matcher.find()) {
                        ErrorMessage.E("Ordered Quantity: " + products.get(j).getName() + ", Product: " + words[i]);
                        orderMenus.add(products.get(j));
                    }

                    for (int k = 0; k < products.get(j).getProducts().size(); k++) {
                        Pattern pattern1 = Pattern.compile("\\b" + Pattern.quote(products.get(j).getProducts().get(k).getName().toLowerCase()) + "\\b", Pattern.CASE_INSENSITIVE);
                        Matcher matcher1 = pattern1.matcher(words[i].toLowerCase());

                        if (matcher1.find()) {
                            ErrorMessage.E("Ordered Quantity: " + products.get(j).getName() + ", Product: " + words[i]);
                            orderProduct.add(products.get(j).getProducts().get(k));
                        }else {
                            for (int l = 0; l < products.get(j).getProducts().get(k).getSizes().size(); l++) {
                                Pattern patternl = Pattern.compile("\\b" + Pattern.quote(products.get(j).getProducts().get(k).getSizes().get(l).getName().toLowerCase()) + "\\b", Pattern.CASE_INSENSITIVE);
                                Matcher matcherl = patternl.matcher(words[i].toLowerCase());

                                if (matcherl.find()) {
                                    ErrorMessage.E("Ordered Quantity: " + products.get(j).getName() + ", Product: " + words[i]);
                                    orderSize.add(products.get(j).getProducts().get(k).getSizes().get(l));
                                }

                            }for (int l = 0; l < products.get(j).getProducts().get(k).getModifiers().size(); l++) {
                                Pattern patternl = Pattern.compile("\\b" + Pattern.quote(products.get(j).getProducts().get(k).getModifiers().get(l).getName().toLowerCase()) + "\\b", Pattern.CASE_INSENSITIVE);
                                Matcher matcherl = patternl.matcher(words[i].toLowerCase());

                                if (matcherl.find()) {
                                    ErrorMessage.E("Ordered Quantity: " + products.get(j).getName() + ", Product: " + words[i]);
                                    orderModifier.add(products.get(j).getProducts().get(k).getModifiers().get(l));
                                }

                            }
                        }

                    }for (int k = 0; k < products.get(j).getChildren().size(); k++) {
                        Pattern pattern1 = Pattern.compile("\\b" + Pattern.quote(products.get(j).getChildren().get(k).getName().toLowerCase()) + "\\b", Pattern.CASE_INSENSITIVE);
                        Matcher matcher1 = pattern1.matcher(words[i].toLowerCase());

                        if (matcher1.find()) {
                            ErrorMessage.E("Ordered Quantity: " + products.get(j).getName() + ", Product: " + words[i]);
                            orderChild.add(products.get(j).getChildren().get(k));
                        }else {
                            for (int m = 0; m < products.get(j).getChildren().get(k).getChildren().size(); m++) {
                                Pattern pattern_m = Pattern.compile("\\b" + Pattern.quote(products.get(j).getChildren().get(k).getChildren().get(m).getName().toLowerCase()) + "\\b", Pattern.CASE_INSENSITIVE);
                                Matcher matcher_m = pattern_m.matcher(words[i].toLowerCase());

                                if (matcher_m.find()) {
                                    ErrorMessage.E("Ordered Quantity: " + products.get(j).getName() + ", Product: " + words[i]);
                                    orderSecondChild.add(products.get(j).getChildren().get(k).getChildren().get(m));
                                }

                            }for (int m = 0; m < products.get(j).getChildren().get(k).getProducts().size(); m++) {
                                Pattern pattern_m = Pattern.compile("\\b" + Pattern.quote(products.get(j).getChildren().get(k).getProducts().get(m).getName().toLowerCase()) + "\\b", Pattern.CASE_INSENSITIVE);
                                Matcher matcher_m = pattern_m.matcher(words[i].toLowerCase());

                                if (matcher_m.find()) {
                                    ErrorMessage.E("Ordered Quantity: " + products.get(j).getName() + ", Product: " + words[i]);
                                    orderSecondProduct.add(products.get(j).getChildren().get(k).getProducts().get(m));
                                }

                            }
                        }
                    }

                }

            }
            ErrorMessage.E("Product found: >>>>>>>" +noFoundItem.size());


            if (item_LIST.size() > 0) {
                binding.searchTxt.setVisibility(View.GONE);
                binding.listRcv.setVisibility(View.VISIBLE);
                side_rv_adapter = new ItemListAdapter(MainActivity.this, item_LIST);
                binding.listRcv.setLayoutManager(new LinearLayoutManager(MainActivity.this, RecyclerView.VERTICAL, false));
                binding.listRcv.setItemAnimator(new DefaultItemAnimator());
                binding.listRcv.scheduleLayoutAnimation();
                binding.listRcv.setNestedScrollingEnabled(false);
                binding.listRcv.setAdapter(side_rv_adapter);
                binding.listRcv.setHasFixedSize(true);
                side_rv_adapter.notifyDataSetChanged();
            } else {
                binding.searchTxt.setVisibility(View.VISIBLE);
                binding.listRcv.setVisibility(View.GONE);
                binding.searchTxt.setText("Item not found, please try again.");

            }


        } catch (Exception e) {
            Log.e("print item <<<e>>>>", "" + e.toString());
        }
    }

    public boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
