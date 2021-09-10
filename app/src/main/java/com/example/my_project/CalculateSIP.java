package com.example.my_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

public class CalculateSIP extends AppCompatActivity{

    Button btnSipCal,btn_Sip_Info;
    EditText txt_Sip_Amt,txt_Sip_Gain,txt_Sip_Tym,txt_Sip_Growth,txt_Sip_Inv;
    ImageView img_bck,img_wt_sip;
    String regularexpression = "^(?=.)([+-]?([0-9]*)(.([0-9]+))?)$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.calculate_sip);

        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("en","IN"));

        txt_Sip_Amt = (EditText) findViewById(R.id.txtSipAmt);
        txt_Sip_Gain = (EditText) findViewById(R.id.txtSipGain);
        txt_Sip_Tym = (EditText) findViewById(R.id.txtSipTym);
        txt_Sip_Growth = (EditText) findViewById(R.id.txtSipGrowth);
        txt_Sip_Inv = (EditText) findViewById(R.id.txtSipInv);

        TextView txt1 = findViewById(R.id.txt1);
        TextView txt2 = findViewById(R.id.txt2);

        img_bck = (ImageView) findViewById(R.id.img_bck);
        btnSipCal = (Button) findViewById(R.id.btn_sip_calc);
        btn_Sip_Info = (Button) findViewById(R.id.btnSipInfo);
        img_wt_sip = (ImageView) findViewById(R.id.img_wt_sip) ;
        btn_Sip_Info.setVisibility(View.GONE);
        txt1.setVisibility(View.GONE);
        txt2.setVisibility(View.GONE);
        txt_Sip_Growth.setVisibility(View.GONE);
        txt_Sip_Inv.setVisibility(View.GONE);

        img_bck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CalculateSIP.this,MainActivity.class);
                startActivity(intent);
            }
        });

        img_wt_sip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WhatIsSip whatIsSip = new WhatIsSip();
                whatIsSip.show(getSupportFragmentManager(),"Sip dialogue");
            }
        });

        btnSipCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String SipAmt=txt_Sip_Amt.getText().toString();
                String SipGain=txt_Sip_Gain.getText().toString();
                String SipTym=txt_Sip_Tym.getText().toString();

                if(SipAmt.equals(null) || SipAmt.equals("") || SipGain.equals(null) || SipGain.equals("") || SipTym.equals(null) || SipTym.equals("") )
                {
                    Toast.makeText(CalculateSIP.this, "Insufficient Input", Toast.LENGTH_SHORT).show();
                }
                else if(!validateInput(SipAmt) || !validateInput(SipGain) || !validateInput(SipTym))
                {
                    Toast.makeText(getBaseContext(),"Invalid input", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Double growthDec = getGrowth();
                    Double invDec = getInv();

                    txt_Sip_Growth.setText(formatter.format(growthDec));
                    txt_Sip_Inv.setText(formatter.format(invDec));

                    btn_Sip_Info.setVisibility(View.VISIBLE);
                    txt1.setVisibility(View.VISIBLE);
                    txt2.setVisibility(View.VISIBLE);
                    txt_Sip_Growth.setVisibility(View.VISIBLE);
                    txt_Sip_Inv.setVisibility(View.VISIBLE);
                }
            }
        });

        btn_Sip_Info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Double growthDec = getGrowth();
                Double invDec=getInv();
                Double SipAmt=Double.parseDouble(txt_Sip_Amt.getText().toString());
                Double SipGain=Double.parseDouble(txt_Sip_Gain.getText().toString());
                Double SipTym=Double.parseDouble(txt_Sip_Tym.getText().toString());

                Bundle bundle = new Bundle();
                bundle.putDouble("sip_growth", growthDec);
                bundle.putDouble("sip_inv", invDec);
                bundle.putDouble("sip_amt",SipAmt);
                bundle.putDouble("sip_amt",SipAmt);
                bundle.putDouble("sip_gain",SipGain);
                bundle.putDouble("sip_tym",SipTym);
                Intent it = new Intent(CalculateSIP.this, SipDetails.class);
                it.putExtra("sip_data", bundle);
                startActivity(it);
            }
        });
    }
    public Double getGrowth()
    {
        Double SipAmt=Double.parseDouble(txt_Sip_Amt.getText().toString());
        Double SipGain=Double.parseDouble(txt_Sip_Gain.getText().toString());
        Double SipTym=Double.parseDouble(txt_Sip_Tym.getText().toString());
        Double growth,power,i,n;

        i = SipGain/100/12;
        n = SipTym * 12;
        power = Math.pow((1+i),n);
        growth = SipAmt * (power-1) * (1+i)/i;
        String formatGrowth = String.format("%.2f",growth);
        Double growthDec = Double.parseDouble(formatGrowth);
        return growthDec;

    }
    public Double getInv()
    {
        Double SipAmt=Double.parseDouble(txt_Sip_Amt.getText().toString());
        Double SipTym=Double.parseDouble(txt_Sip_Tym.getText().toString());
        Double inv;

        inv = SipAmt * SipTym * 12;
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
