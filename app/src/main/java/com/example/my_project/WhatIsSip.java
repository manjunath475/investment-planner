package com.example.my_project;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.core.text.HtmlCompat;

public class WhatIsSip extends AppCompatDialogFragment {
    
    String htmlText = "With SIP you invest a small amount of money every month in Mutual Funds. " +
            "Sip(Systematic Investment Plan) is a method of investing into Mutual Funds." +
            "<br><br><b>"+"Benefits of SIP"+"</b>"+"<br><br><b>"+"Saving Habit: "+"</b>"+"Invest monthly in small amounts just like EMI."+"<br><br><b>" +
            "Security: "+"</b>"+"Effect of market fluctuations gets reduced, giving you big benefits in long run."+"<br><br><b>" +
            "Easy: "+"</b>"+"One of the easiest way to start investing";

    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("What is SIP?")
                .setMessage(HtmlCompat.fromHtml(htmlText,0))
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        return builder.create();
    }
}
