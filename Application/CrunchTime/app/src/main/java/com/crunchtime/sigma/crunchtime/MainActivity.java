package com.crunchtime.sigma.crunchtime;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.HashMap;


public class MainActivity extends Activity {

    private String DEFAULT_REPS_BOX_TEXT = "# reps";
    private String exercises[];
    private boolean clicked;

    Spinner exerciseSpinner;
    HashMap<String, Double> exerciseToCalorieRatio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        clicked = false;
        setContentView(R.layout.activity_main);
        initializeExerciseToCalorieMap();
        initializeSpinners();
    }

    private void initializeSpinners() {
        exercises = getResources().getStringArray(R.array.exercise_array);
        exerciseSpinner = (Spinner) findViewById(R.id.exercise_spinner);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.exercise_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        exerciseSpinner.setAdapter(adapter);
        exerciseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView unitsBox = (TextView) findViewById(R.id.units_textbox);
                if (position <= 2 || position == 6) {
                    unitsBox.setText("reps");
                } else {
                    unitsBox.setText("minutes");
                }
                if (clicked) {
                    convertToCalories(findViewById(android.R.id.content));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                TextView unitsBox = (TextView) findViewById(R.id.units_textbox);
                unitsBox.setText("");
            }
        });
        exerciseSpinner = (Spinner) findViewById(R.id.exercise_2_spinner);
        ArrayAdapter adapter2 = ArrayAdapter.createFromResource(this, R.array.exercise_array, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        exerciseSpinner.setAdapter(adapter2);
        exerciseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                TextView unitsBox = (TextView) findViewById(R.id.units_2_textbox);
//                if (position <= 2 || position == 6) {
//                    unitsBox.setText("reps");
//                } else {
//                    unitsBox.setText("min");
//                }
                if (clicked) {
                    convertToCalories(findViewById(android.R.id.content));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
//                TextView unitsBox = (TextView) findViewById(R.id.units_2_textbox);
//                unitsBox.setText("");
            }
        });
    }

    public void emptyRepsBox(View v) {
        EditText repsBox = (EditText) findViewById(R.id.reps_textbox);
        if (repsBox.getText().equals(DEFAULT_REPS_BOX_TEXT)){
            repsBox.setText("");
        }
//        TextView result2Box = (TextView) findViewById(R.id.result_2_textbox);
//        if (repsBox.getText().equals(DEFAULT_REPS_BOX_TEXT)){
//            repsBox.setText("");
//        }
    }

    private boolean initializeExerciseToCalorieMap() {
        exerciseToCalorieRatio = new HashMap<String, Double>();
        exerciseToCalorieRatio.put("Pushups", 100.0/350);
        exerciseToCalorieRatio.put("Situps", 100.0/200);
        exerciseToCalorieRatio.put("Squats", 100.0/225);
        exerciseToCalorieRatio.put("Leg-lifts", 100.0/25);
        exerciseToCalorieRatio.put("Plank", 100.0/25);
        exerciseToCalorieRatio.put("Jumping Jacks", 100.0/10);
        exerciseToCalorieRatio.put("Pullups", 100.0/100);
        exerciseToCalorieRatio.put("Cycling", 100.0/12);
        exerciseToCalorieRatio.put("Walking", 100.0/20);
        exerciseToCalorieRatio.put("Jogging", 100.0/12);
        exerciseToCalorieRatio.put("Swimming", 100.0/13);
        exerciseToCalorieRatio.put("Stair-Climbing", 100.0/15);
        return true;
    }

    public void convertToCalories(View v) {
        clicked = true;
        Spinner exerciseSpinner = (Spinner) findViewById(R.id.exercise_spinner);
        EditText repsBox = (EditText) findViewById(R.id.reps_textbox);
        TextView resultBox = (TextView) findViewById(R.id.result_textbox);
        String exercise = exerciseSpinner.getSelectedItem().toString();
        try {
            double numReps1 = Double.parseDouble(repsBox.getText().toString());
            double result = (exerciseToCalorieRatio.get(exercise) * numReps1);
            String resultString;
            if (((int) result) != result) {
                DecimalFormat form = new DecimalFormat("#.0");
                resultString = form.format(result);
            } else {
                resultString = ((int) result) + "";
            }
            resultBox.setText(resultString + " calories burned!", TextView.BufferType.EDITABLE);
            exerciseSpinner = (Spinner) findViewById(R.id.exercise_2_spinner);
            exercise = exerciseSpinner.getSelectedItem().toString();
            resultBox = (TextView) findViewById(R.id.result_2_textbox);
            result /= exerciseToCalorieRatio.get(exercise);
            if (((int) result) != result) {
                DecimalFormat form = new DecimalFormat("#.0");
                resultString = form.format(result);
            } else {
                resultString = ((int) result) + "";
            }
            int index = -1;
            for (int i = 0; i < exercises.length; i++) {
                if (exercises[i].equals(exercise)) {
                    index = i;
                }
            }
            String units;
            if (index <= 2 || index == 6) {
                units = "";
            } else {
                units = "minutes of ";
            }
            resultBox.setText("Equivalent to " + resultString + " " + units + exercise);
        } catch (Exception e) {
            Toast toast = Toast.makeText(getApplicationContext(), "Check number format", Toast.LENGTH_LONG);
            toast.show();
            resultBox.setText("Sorry! Couldn't parse that number.");
            resultBox = (TextView) findViewById(R.id.result_2_textbox);
            resultBox.setText("");
        }
    }

    public void imageButtonClicked(View v) {
        TextView resultBox = (TextView) findViewById(R.id.result_textbox);
        resultBox.setText("You clicked that image!");
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


//    EditText reps_text = (EditText) findViewById(R.id.reps_textbox);
//    reps_text.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//        @Override
//        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//            boolean handled = false;
//            if (actionId == EditorInfo.IME_ACTION_SEND) {
//                sendMessage();
//                handled = true;
//            }
//            return handled;
//        }
//    });

}
