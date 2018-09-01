package com.iexpress.android.tipcalculatorstilljava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.NumberFormat;

public class TipCalculator extends AppCompatActivity {
    private static final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();

    private static final NumberFormat percentFormat = NumberFormat.getPercentInstance();

    private double billAmt = 0.0;
    private double percent = 0.15;
    private TextView amtTextView;
    private TextView percentTextView;
    private TextView tipTextView;
    private TextView totalTextView;

    private EditText amtEditText;

    private SeekBar percentSeekBar;

    private final SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            percent = progress / 100.0;
            calculate();
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    private final TextWatcher amtEditTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            //
            //  The book's solution has problem:  when amount-edit-text input changes, the amount-text-view does NOT change.
            //                                    Also, the beforeTextChanged is, well, somewhat a fraud, because, it makes sense to calculate AFTER-Text-Changed.
            //
//            try {
//                String amtInput = charSequence.toString();
//                if (!amtInput.isEmpty()) {
//                    billAmt = Double.parseDouble(charSequence.toString());
//                    amtTextView.setText(currencyFormat.format(billAmt));
//                }
//
//                calculate();
//
//            } catch (Exception e) {
//                amtEditText.setText("");
//                billAmt = 0.0;
//            }
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            try {
                billAmt = Double.parseDouble(editable.toString());
                amtTextView.setText(currencyFormat.format(billAmt));

                calculate();

            } catch (Exception e) {
                amtEditText.setText("");
                billAmt = 0.0;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // inflate the GUI
        setContentView(R.layout.activity_tip_calculator);

        amtTextView = (TextView) findViewById(R.id.amtTtextView);
        percentTextView = (TextView) findViewById(R.id.percentTextView);
        tipTextView = (TextView) findViewById(R.id.tipTextView);
        totalTextView = (TextView) findViewById(R.id.totalTextView);

        amtEditText = (EditText) findViewById(R.id.amtEditText);
        amtEditText.addTextChangedListener(amtEditTextWatcher);

        percentSeekBar = (SeekBar) findViewById(R.id.percentSeekBar);
        percentSeekBar.setOnSeekBarChangeListener(seekBarChangeListener);

        calculate();

    }

    private void calculate() {
        percentTextView.setText(percentFormat.format(percent));

        double tip = percent * billAmt;
        double total = billAmt + tip;

        tipTextView.setText(currencyFormat.format(tip));
        totalTextView.setText(currencyFormat.format(total));
    }


}
