package com.example.hl_th.findatm;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by HL_TH on 12/4/2016.
 */

public class SearchDialog extends Dialog {
    Activity activity;
    Button btn_cancel;
    Button btn_option;
    Button btn_around;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showatm);
        init();
        excute();


    }

    protected SearchDialog(Activity context) {

        super(context);
        this.activity = context;

    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    @Override
    public void hide() {
        super.hide();
    }


    private void excute() {
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
    public void init() {
        btn_cancel = (Button) findViewById(R.id.id_btn_cancel);
    }

}
