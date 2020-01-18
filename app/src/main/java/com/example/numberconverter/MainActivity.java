package com.example.numberconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;



public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void convert(View view){
        EditText inp= (EditText) findViewById(R.id.inputNumber);
        String inpu=inp.getText().toString();
        if(!(inpu.equalsIgnoreCase(""))){
            double input=Double.parseDouble(inpu);
            String answerRaw=dec2bin(input);

            String toOtherFormats="";

            String x="";
            //extracts the whole part of the number
            for(int i=0; i<answerRaw.length();i++) {
                x= Character.toString(answerRaw.charAt(i));
                if (!x.equalsIgnoreCase(".")) {
                    toOtherFormats+=x;
                }
                else{break;}
            }


            String answerSign=sign(toOtherFormats);
            String answerOnes=ones(toOtherFormats);
            String answerTwos=twos(toOtherFormats);

            TextView textView=(TextView) findViewById(R.id.outputNumber);
            textView.setText(format(answerRaw));

            TextView textView1=(TextView) findViewById(R.id.signAndMagnitude);
            textView1.setText(format(answerSign));

            TextView textView2=(TextView) findViewById(R.id.onesComplement);
            textView2.setText(format(answerOnes));

            TextView textView3=(TextView) findViewById(R.id.twosComplement);
            textView3.setText(format(answerTwos));
        }

    }
    public void bin2dec(View view){
        Intent intent= new Intent(MainActivity.this,binToDec.class);
        startActivity(intent);
    }

    public String dec2bin(double n){
        double decPart;
        String answer;
        double wholePart;
        double absn=n;
        if(n<0){absn=-n;}
        int bitNum=0;
        if(absn>=1) {
            decPart = absn % 1;
            answer = ".";
            wholePart = absn - decPart;
            while (wholePart > 0) {
                if (wholePart % 2 == 1) {
                    answer = "1" + answer;
                    bitNum++;
                } else {
                    answer = "0" + answer;
                    bitNum++;
                }
                wholePart=java.lang.Math.floor(wholePart / 2);
            }
        }
	    else{
                decPart=absn;
                answer="0.";
                bitNum=1;
            }
	    //making the number of bits a power of 2
	    if(bitNum<2){
	        for(int i=bitNum;i<2;i++){
	            answer="0"+answer;
	            bitNum++;
	        }
        }
        else if(bitNum<4){
            for(int i=bitNum;i<4;i++){
                answer="0"+answer;
                bitNum++;
            }
        }
        else if(bitNum<8){
            for(int i=bitNum;i<8;i++){
                answer="0"+answer;
                bitNum++;
            }
        }
        else if(bitNum<16){
            for(int i=bitNum;i<16;i++){
                answer="0"+answer;
                bitNum++;
            }
        }
        else if(bitNum<32){
            for(int i=bitNum;i<32;i++){
                answer="0"+answer;
                bitNum++;
            }
        }
        else{}

        for(int i=bitNum; i<32;i++){
            if(decPart*2>=1){
                answer+="1";
                decPart=decPart*2-1;
            }
            else{
                answer+="0";
                decPart=decPart*2;
            }
        }
            if(n<0){answer='-'+answer;}
            return answer;
        }




    public String sign(String input){
        String answer = "";

        if (!Character.toString(input.charAt(0)).equalsIgnoreCase("-")) {
            answer=input;
        }
        else {
            //loops through bits from end to the last but 1
            for (int i = input.length() - 1; i > 1; i--) {
                answer=Character.toString(input.charAt(i))+answer;
            }
            answer="1"+answer;//make the last but 1 bit a 1. the negative sign is discarded
        }

        return answer;
    }

    public String ones(String input){
        String answer="";
        if (!Character.toString(input.charAt(0)).equalsIgnoreCase("-")) {
            answer=input;
        }
        else {
            String x = "";
            int invertIndex = 0;
            //inverting all bits
            for (int i = input.length() - 1; i > 0; i--) {
                x = Character.toString(input.charAt(i));
                if (x.equalsIgnoreCase("0")) {
                    answer = "1" + answer;
                } else {
                    answer = "0" + answer;
                }
            }

        }
        return answer;
    }
    public String twos(String input) {

        String answer="";
        if (!Character.toString(input.charAt(0)).equalsIgnoreCase("-")) {
            answer=input;
        }
        else {
            String x = "";
            int invertIndex = 0;
            //inverting all zeros till it meets the first 1
            for (int i = input.length() - 1; i > 0; i--) {
                x = Character.toString(input.charAt(i));
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
        }
        return answer;


    }
    public String format(String input){
        String answer="";

        int count=0;
        for (int i = input.length() - 1; i >= 0; i--) {
            answer=Character.toString(input.charAt(i))+answer;
            count++;
            if(count%4==0){answer=" "+answer;}
        }
        return answer;
    }
}
