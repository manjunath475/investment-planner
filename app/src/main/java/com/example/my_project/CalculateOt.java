package com.example.my_project;

import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CalculateOt extends AppCompatActivity{

    EditText txt_Ot_Amt,txt_Ot_Gain,txt_Ot_Tym,txt_Ot_Growth,txt_Ot_Inv;
    Button btn_Ot_Calc,btn_Ot_Info;
    ImageView img_back,img_wt_mf;
    String regularexpression = "^(?=.)([+-]?([0-9]*)(.([0-9]+))?)$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.calculate_ot);

        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("en","IN"));

        txt_Ot_Amt = (EditText) findViewById(R.id.txtOtAmt);
        txt_Ot_Gain = (EditText) findViewById(R.id.txtOtGain);
        txt_Ot_Tym = (EditText) findViewById(R.id.txtIOtTym);
        txt_Ot_Growth = (EditText) findViewById(R.id.txtOtGrowth);
        txt_Ot_Inv = (EditText) findViewById(R.id.txtOtInv);

        TextView txt3 = findViewById(R.id.txt3);
        TextView txt4 = findViewById(R.id.txt4);

        btn_Ot_Calc = (Button) findViewById(R.id.btnOtCalc);
        img_back = (ImageView) findViewById(R.id.img_bck);
        btn_Ot_Info = (Button) findViewById(R.id.btnOtInfo);
        img_wt_mf = findViewById(R.id.img_wt_mf);
        btn_Ot_Info.setVisibility(View.GONE);
        txt3.setVisibility(View.GONE);
        txt4.setVisibility(View.GONE);
        txt_Ot_Growth.setVisibility(View.GONE);
        txt_Ot_Inv.setVisibility(View.GONE);

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CalculateOt.this,MainActivity.class);
                startActivity(intent);
            }
        });

        img_wt_mf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WhatisOt whatisOt = new WhatisOt();
                whatisOt.show(getSupportFragmentManager(),"MF dialogue");
            }
        });

        btn_Ot_Calc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String OtAmt=txt_Ot_Amt.getText().toString();
                String OtGain=txt_Ot_Gain.getText().toString();
                String OtTym=txt_Ot_Tym.getText().toString();

                if(OtAmt.equals(null) || OtAmt.equals("") || OtGain.equals(null) || OtGain.equals("") || OtTym.equals(null) || OtTym.equals(""))
                {
                    Toast.makeText(getBaseContext(), "Insufficient Inputs!", Toast.LENGTH_SHORT).show();
                }
                else if(!validateInput(OtAmt) || !validateInput(OtGain) || !validateInput(OtTym))
                {
                    Toast.makeText(getBaseContext(),"Invalid input!", Toast.LENGTH_LONG).show();
                }
                else {
                    Double growthDec = getGrowth();
                    Double invDec = getInv();

                    txt_Ot_Growth.setText(formatter.format(growthDec));
                    txt_Ot_Inv.setText(formatter.format(invDec));
                    btn_Ot_Info.setVisibility(View.VISIBLE);
                    txt3.setVisibility(View.VISIBLE);
                    txt4.setVisibility(View.VISIBLE);
                    txt_Ot_Growth.setVisibility(View.VISIBLE);
                    txt_Ot_Inv.setVisibility(View.VISIBLE);
                }
            }
        });

        btn_Ot_Info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Double growthDeci = getGrowth();
                Double invDeci = getInv();
                Double OtAmt=Double.parseDouble(txt_Ot_Amt.getText().toString());
                Double OtGain=Double.parseDouble(txt_Ot_Gain.getText().toString());
                Double OtTym=Double.parseDouble(txt_Ot_Tym.getText().toString());

                Bundle bundles = new Bundle();
                bundles.putDouble("ot_growth", growthDeci);
                bundles.putDouble("ot_inv", invDeci);
                bundles.putDouble("ot_amt",OtAmt);
                bundles.putDouble("ot_gain",OtGain);
                bundles.putDouble("ot_tym",OtTym);
                Intent intent = new Intent(CalculateOt.this, OtDetails.class);
                intent.putExtra("ot_data", bundles);
                startActivity(intent);
            }
        });
    }
    public Double getGrowth()
    {
        Double OtAmt=Double.parseDouble(txt_Ot_Amt.getText().toString());
        Double OtGain=Double.parseDouble(txt_Ot_Gain.getText().toString());
        Double OtTym=Double.parseDouble(txt_Ot_Tym.getText().toString());
        Double growth,power,r;

        r = OtGain/100;
        power = Math.pow((1+r),OtTym);
        growth = OtAmt * power;
        String formatGrowth = String.format("%.2f",growth);
        Double growthDec = Double.parseDouble(formatGrowth);
        return growthDec;

    }
    public Double getInv()
    {
        Double OtAmt=Double.parseDouble(txt_Ot_Amt.getText().toString());
        Double inv;

        inv = OtAmt;
        String formatInv = String.format("%.2f",inv);
        Double InvDec = Double.parseDouble(formatInv);
        return InvDec;
    }
    public boolean validateInput(String input)
    {
        Pattern pattern = Pattern.compile(regularexpression);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }
}
