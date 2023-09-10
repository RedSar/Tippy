package com.example.tippy

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log

import android.widget.EditText
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.TextView

private const val TAG = "MainActivity"
private const val INITIAL_TIP_PERCENT = 15

class MainActivity : AppCompatActivity() {

    private lateinit var etBillValue: EditText
    private lateinit var tvPercentageTipLabel: TextView
    private lateinit var tvTipValue: TextView
    private lateinit var tvTotalValue: TextView
    private lateinit var seekBarTipPercent: SeekBar


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etBillValue = findViewById(R.id.etBillValue)
        tvPercentageTipLabel = findViewById(R.id.tvPercentageTipLabel)
        tvTipValue = findViewById(R.id.tvTipValue)
        tvTotalValue = findViewById(R.id.tvTotalValue)
        seekBarTipPercent = findViewById(R.id.seekBarTipPercent)

        seekBarTipPercent.progress = INITIAL_TIP_PERCENT
        tvPercentageTipLabel.text = "$INITIAL_TIP_PERCENT%"

        //
        seekBarTipPercent.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                Log.i(TAG,"Tip Percent : $progress")
                tvPercentageTipLabel.text = "$progress%"
                computeTipAndTotalAmount()

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}

        })

        etBillValue.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                Log.i(TAG, "Bill Amount: $s")
                if(etBillValue.text.isEmpty()) {
                    tvTotalValue.text = ""
                    tvTipValue.text = ""
                    return
                }
                computeTipAndTotalAmount()

            }
        })
    }

    private fun computeTipAndTotalAmount() {
        val billAmount = etBillValue.text.toString().toDouble()
        val tipPercentage = seekBarTipPercent.progress

        val tipAmount = billAmount * tipPercentage/100
        val totalAmount = billAmount + tipAmount

        tvTipValue.text = "%.2f".format(tipAmount)
        tvTotalValue.text = "%.2f".format(totalAmount)
    }
}