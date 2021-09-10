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

public class OtDetails extends AppCompatActivity {

    EditText Ot_Growth,Ot_Inv;
    ImageView img_back;
    BarChart barchart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.ot_details);

        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("en","IN"));

        Ot_Growth = (EditText) findViewById(R.id.OtGrowth);
        Ot_Inv = (EditText) findViewById(R.id.OtInv);
        barchart = (BarChart) findViewById(R.id.barchart);
        img_back = (ImageView) findViewById(R.id.imgback);
        TextView txt_graph = findViewById(R.id.txtgraph);

        Bundle bundle = getIntent().getBundleExtra("ot_data");
        Double ot_growth = bundle.getDouble("ot_growth");
        Double ot_inv = bundle.getDouble("ot_inv");
        Double amt = bundle.getDouble("ot_amt");
        Double tym = bundle.getDouble("ot_tym");

        Double Bank_gain = 6.75d;
        Double LRF_gain = 9.5d;
        Double MRF_gain = 16d;
        Double HRF_gain = 21d;

        Double Bank = calc_growth(amt,Bank_gain,tym);
        Double LRF = calc_growth(amt,LRF_gain,tym);
        Double MRF = calc_growth(amt,MRF_gain,tym);
        Double HRF = calc_growth(amt,HRF_gain,tym);

        Ot_Growth.setText(formatter.format(ot_growth));
        Ot_Inv.setText(formatter.format(ot_inv));

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OtDetails.this,CalculateOt.class);
                startActivity(intent);
            }
        });

        ArrayList<BarEntry> SipEntries = new ArrayList<>();
        SipEntries.add(new BarEntry(Bank.floatValue(),0));
        SipEntries.add(new BarEntry(LRF.floatValue(),1));
        SipEntries.add(new BarEntry(MRF.floatValue(),2));
        SipEntries.add(new BarEntry(HRF.floatValue(),3));

        BarDataSet barDataSet= new BarDataSet(SipEntries, "Funds(Rs)");
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(12f);

        ArrayList<String> risks = new ArrayList<>();
        risks.add("Bank(FD)");
        risks.add("LRF");
        risks.add("MRF");
        risks.add("HRF");

        BarData thedata = new BarData(risks,barDataSet);
        barchart.setData(thedata);

        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(12f);

        barchart.setTouchEnabled(true);
        barchart.setDragEnabled(true);
        barchart.setScaleEnabled(true);

        barchart.animateY(2000);
        barchart.setDescription(" ");

        txt_graph.setText("For this calculation, it is assumed that high risk funds(HRF) give return of 21% p.a., mid risk funds(MRF) gives 16% p.a., low risk funds(LRF) gives 9.5% p.a., bank deposit(FD) gives 6.75% p.a.\n\nActual returns depend on market conditions.");
    }

    public Double calc_growth(Double amt,Double Gain,Double tym)
    {
        Double r = Gain/100;
        Double power = Math.pow((1+r),tym);
        Double func_growth = amt * power;
        String grow = String.format("%.2f",func_growth);
        Double x = Double.parseDouble(grow);
        return x;
    }

}