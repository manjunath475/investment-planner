package com.example.my_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class SipDetails extends AppCompatActivity {

    EditText Sip_Growth,Sip_Inv;
    ImageView img_bck;
    BarChart barChart;
    TextView txt_Graph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.sip_details);

        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("en","IN"));

        Sip_Growth = (EditText) findViewById(R.id.SipGrowth);
        Sip_Inv = (EditText) findViewById(R.id.SipInv);
        img_bck = (ImageView) findViewById(R.id.imgbck);
        barChart = (BarChart) findViewById(R.id.barChart);
        txt_Graph = (TextView) findViewById(R.id.txtGraph);

        Bundle bundle = getIntent().getBundleExtra("sip_data");
        Double growth = bundle.getDouble("sip_growth");
        Double inv = bundle.getDouble("sip_inv");
        Double amt = bundle.getDouble("sip_amt");
        Double gain = bundle.getDouble("sip_gain");
        Double tym = bundle.getDouble("sip_tym");

        Sip_Growth.setText(formatter.format(growth));
        Sip_Inv.setText(formatter.format(inv));

        img_bck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SipDetails.this,CalculateSIP.class);
                startActivity(intent);
            }
        });

        Double Bank_gain = 6.75d;
        Double LRF_gain = 9.5d;
        Double MRF_gain = 16d;
        Double HRF_gain = 21d;

        Double Bank = calc_growth(amt,Bank_gain,tym);
        Double LRF = calc_growth(amt,LRF_gain,tym);
        Double MRF = calc_growth(amt,MRF_gain,tym);
        Double HRF = calc_growth(amt,HRF_gain,tym);

        ArrayList<BarEntry> OtEntries = new ArrayList<>();
        OtEntries.add(new BarEntry(Bank.floatValue(),0));
        OtEntries.add(new BarEntry(LRF.floatValue(),1));
        OtEntries.add(new BarEntry(MRF.floatValue(),2));
        OtEntries.add(new BarEntry(HRF.floatValue(),3));

        BarDataSet bardataset = new BarDataSet(OtEntries, "Funds(Rs)");
        bardataset.setColors(ColorTemplate.COLORFUL_COLORS);
        bardataset.setValueTextColor(Color.BLACK);
        bardataset.setValueTextSize(12f);

        ArrayList<String> risks = new ArrayList<>();
        risks.add("Bank(RD)");
        risks.add("LRF");
        risks.add("MRF");
        risks.add("HRF");

        BarData data = new BarData(risks,bardataset);
        barChart.setData(data);
        barChart.setDescription(" ");

        bardataset.setValueTextColor(Color.BLACK);
        bardataset.setValueTextSize(12f);

        barChart.setTouchEnabled(true);
        barChart.setDragEnabled(true);
        barChart.setScaleEnabled(true);

        barChart.animateY(2000);

        txt_Graph.setText("For this calculation, it is assumed that high risk funds(HRF) give returns of 21% p.a., mid risk funds(MRF) gives 16% p.a., low risk funds(LRF) gives 9.5% p.a., bank deposit(RD) gives 6.75% p.a.\n\nActual returns depend on market conditions.");
    }

    public Double calc_growth(Double amt,Double Gain,Double tym)
    {
        Double func_growth,power,i,n;

        i = Gain/100/12;
        n = tym * 12;
        power = Math.pow((1+i),n);
        func_growth = amt * (power-1) * (1+i)/i;
        String formatGrowth = String.format("%.2f",func_growth);
        Double growthDec = Double.parseDouble(formatGrowth);
        return growthDec;
    }
}