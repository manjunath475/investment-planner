package com.example.my_project;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.core.text.HtmlCompat;

public class WhatisOt extends AppCompatDialogFragment {

    String htmlText = "A form of investment where your funds are managed by an expert."+"<br><br><b>"+
            "Benefits of Mutual Fund:"+"</b><br><br>"+"<b>"+"High Gain: "+"</b>"+
            "Many Mutual Funds have given much higher profits than other modes of investment in the past."+"<br><br><b>"+
            "100% Transparent: "+"</b>"+"You can monitor your investments anytime, anywhere."+"<br><br><b>"+
            "Easy: "+"</b>"+"Easy investment and easy withdrawal. Mutual Funds also give you an option of partial withdrawal.";

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("What is Mutual Fund?")
                .setMessage(HtmlCompat.fromHtml(htmlText,0))
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        return builder.create();
    }
}
