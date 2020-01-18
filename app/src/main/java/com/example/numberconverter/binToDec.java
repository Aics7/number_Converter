package com.example.numberconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class binToDec extends AppCompatActivity {

    private RadioGroup radioGroup;
    private int binType=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bin_to_dec);


        radioGroup = (RadioGroup) findViewById(R.id.binType);

        radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // find which radio button is selected
                if(checkedId == R.id.raw) {
                    Toast.makeText(getApplicationContext(), "choice: Raw",
                            Toast.LENGTH_SHORT).show();
                    binType=1;
                } else if(checkedId == R.id.signM) {
                    Toast.makeText(getApplicationContext(), "choice: Sign and Magnitude",
                            Toast.LENGTH_SHORT).show();
                    binType=2;
                } else if(checkedId == R.id.onesC){
                    Toast.makeText(getApplicationContext(), "choice: 1's complement",
                            Toast.LENGTH_SHORT).show();
                    binType=3;
                }
                else if(checkedId == R.id.twosC){
                    Toast.makeText(getApplicationContext(), "choice: 2's complement",
                            Toast.LENGTH_SHORT).show();
                    binType=4;
                }
            }

        });


    }

    public void convert(View view){
        EditText inp= (EditText) findViewById(R.id.inputNumberBin);
        String input=inp.getText().toString();
        if(!(input.equalsIgnoreCase(""))){
            boolean negate=false;
            if(binType!=1) {
                String onlyWholePart = "";
                String x = "";
                //extracts the whole part of the number
                for (int i = 0; i < input.length(); i++) {
                    x = Character.toString(input.charAt(i));
                    if (!x.equalsIgnoreCase(".")) {
                        onlyWholePart += x;
                    } else {
                        break;
                    }
                }
                input=onlyWholePart;
            }
            if(Character.toString(input.charAt(0)).equalsIgnoreCase("1")) {
                if (binType == 2) {
                    negate=true;
                    String x="";
                    for(int i=1; i<input.length();i++) {
                        x += Character.toString(input.charAt(i));
                    }
                    input="0"+x;
                } else if (binType == 3) {
                    negate=true;
                    input = straightOnes(input);
                } else if (binType == 4) {
                    negate=true;
                    input =straightTwos(input);
                }
            }


            TextView textView=(TextView) findViewById(R.id.outputNumberBin);
            String answer=bin2dec(input);
            if(negate){answer="-"+answer;}
            textView.setText(answer);
        }

    }

    public String bin2dec(String n){
        String answer="";
        double ansValue=0;
        int pointIndex=-1;
        int bitNum=0;
        boolean isBin=true;

        if(n.equalsIgnoreCase("invalid")){
            Toast.makeText(binToDec.this, "Input invalid for the selected format. More digits required", Toast.LENGTH_LONG).show();
            return "Input cannot be 1";
        }
        for(int i=0; i<n.length();i++){
            if(Character.toString(n.charAt(i)).equalsIgnoreCase(".")){
                pointIndex=i;
            }
            if(!Character.toString(n.charAt(i)).equalsIgnoreCase(".")
                    && !Character.toString(n.charAt(i)).equalsIgnoreCase("0")
                    && !Character.toString(n.charAt(i)).equalsIgnoreCase("1")){
                isBin=false;
                Toast.makeText(binToDec.this, "Binary numbers can only be a series of 1s ans 0s", Toast.LENGTH_LONG).show();
                return "input must be binary!";
            }
            bitNum=i;
        }
        if(pointIndex==-1){
            for(int i=0;i<=bitNum;i++){
                int bit=Integer.parseInt(Character.toString(n.charAt(i)));
                ansValue+=bit*Math.pow(2,bitNum-i);
            }
        }
        else{
            for(int i=0;i<pointIndex;i++){
                int bit=Integer.parseInt(Character.toString(n.charAt(i)));
                ansValue+=bit*Math.pow(2,pointIndex-i-1);
            }
            for(int i=pointIndex+1;i<=bitNum;i++){
                int bit=Integer.parseInt(Character.toString(n.charAt(i)));
                ansValue+=bit*1/(Math.pow(2,(i-pointIndex)));
            }

        }
        answer=Double.toString(ansValue);
        return answer;
    }


    public String straightOnes(String input){

        if(input.equalsIgnoreCase("1")||input.equalsIgnoreCase("0")) {
            return "invalid";
        }

        String answer="";

            String x = "";
            for (int i = input.length() - 1; i > 0; i--) {
                x = Character.toString(input.charAt(i));

                if(!x.equalsIgnoreCase(".")
                        && !x.equalsIgnoreCase("0")
                        && !x.equalsIgnoreCase("1")){
                    Toast.makeText(binToDec.this, "Binary numbers can only be a series of 1s ans 0s", Toast.LENGTH_LONG).show();
                    return "input must be binary!";
                }

                if (x.equalsIgnoreCase("0")) {
                    answer = "1" + answer;
                } else {
                    answer = "0" + answer;
                }
            }
        return answer;
    }

    public String straightTwos(String input) {

        if(input.equalsIgnoreCase("1")||input.equalsIgnoreCase("0")){
            return "invalid";

        }

        String answer="";

            String x = "";
            int invertIndex = 0;
            //inverting all zeros till it meets the first 1
            for (int i = input.length() - 1; i > 0; i--) {
                x = Character.toString(input.charAt(i));

                if(!x.equalsIgnoreCase(".")
                        && !x.equalsIgnoreCase("0")
                        && !x.equalsIgnoreCase("1")){
                    Toast.makeText(binToDec.this, "Binary numbers can only be a series of 1s ans 0s", Toast.LENGTH_LONG).show();
                    return "input must be binary!";
                }

                if (x.equalsIgnoreCase("0")) {
                    answer = "0" + answer;
                } else {
                    answer = "1" + answer;
                    invertIndex = i-1;
                    //i=0;//breaks the loop by force
                    break;
                }
            }
            //inverts the rest
            for (int i = invertIndex; i > 0; i--) {
                x = Character.toString(input.charAt(i));
                if (x.equalsIgnoreCase("0")) {
                    answer = "1" + answer;
                } else {
                    answer = "0" + answer;
                }
            }

        return answer;


    }
}
