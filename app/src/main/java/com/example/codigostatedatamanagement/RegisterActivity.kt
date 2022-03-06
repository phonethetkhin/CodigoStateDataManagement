package com.example.codigostatedatamanagement

import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.util.TypedValue
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.*

class RegisterActivity : AppCompatActivity() {
    private lateinit var edtFName: EditText
    private lateinit var edtLName: EditText
    private lateinit var edtEmail: EditText
    private lateinit var edtDOB: EditText
    private lateinit var edtNationality: EditText
    private lateinit var edtResidence: EditText
    private lateinit var btnFemale: MaterialButton
    private lateinit var btnMale: MaterialButton
    var gender = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        findViewById<ImageView>(R.id.imgBack).setOnClickListener {
            super.onBackPressed()
        }
        initViews()
        findViewById<Button>(R.id.btnCreateAccount).setOnClickListener {
            if (isNotBlanks()) {
                if (edtEmail.text.isValidEmail()) {
                    Toast.makeText(
                        this,
                        "Your account is created successfully!",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                } else {
                    Toast.makeText(this, "Please enter a valid email address", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
        findViewById<EditText>(R.id.edtDOB).setOnClickListener {
            chooseDate()
        }
        //default
        btnFemale.strokeWidth =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2F, resources.displayMetrics)
                .toInt()
        btnMale.strokeWidth = 0
        btnFemale.setOnClickListener {
            btnFemale.strokeWidth =
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2F, resources.displayMetrics)
                    .toInt()
            btnMale.strokeWidth = 0
            gender = 0
        }
        btnMale.setOnClickListener {
            btnFemale.strokeWidth = 0
            btnMale.strokeWidth =
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2F, resources.displayMetrics)
                    .toInt()
            gender = 1
        }

    }

    private fun initViews() {
        edtFName = findViewById(R.id.edtFirstName)
        edtLName = findViewById(R.id.edtLastName)
        edtEmail = findViewById(R.id.edtEmail)
        edtDOB = findViewById(R.id.edtDOB)
        edtNationality = findViewById(R.id.edtNationality)
        edtResidence = findViewById(R.id.edtResidence)

        btnFemale = findViewById(R.id.btnFemale)
        btnMale = findViewById(R.id.btnMale)
    }

    private fun isNotBlanks(): Boolean {
        var isNotBlanks = false
        when {
            TextUtils.isEmpty(edtFName.text!!.trim().toString()) -> {
                Toast.makeText(this, "The first name field is required.", Toast.LENGTH_SHORT).show()
            }
            TextUtils.isEmpty(edtLName.text!!.trim().toString()) -> {
                Toast.makeText(this, "The last name field is required.", Toast.LENGTH_SHORT).show()
            }
            TextUtils.isEmpty(edtEmail.text!!.trim().toString()) -> {
                Toast.makeText(this, "The email field is required.", Toast.LENGTH_SHORT).show()
            }
            TextUtils.isEmpty(edtDOB.text!!.trim().toString()) -> {
                Toast.makeText(this, "The date of birth field is required.", Toast.LENGTH_SHORT)
                    .show()
            }
            TextUtils.isEmpty(edtNationality.text!!.trim().toString()) -> {
                Toast.makeText(this, "The nationality field is required.", Toast.LENGTH_SHORT)
                    .show()
            }
            TextUtils.isEmpty(edtResidence.text!!.trim().toString()) -> {
                Toast.makeText(this, "The residence field is required.", Toast.LENGTH_SHORT).show()
            }
            else -> {
                isNotBlanks = true
            }
        }
        return isNotBlanks
    }

    fun CharSequence?.isValidEmail() =
        !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()


    private fun chooseDate() {
        val datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select date")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build()
        datePicker.addOnPositiveButtonClickListener {
            edtDOB.setText(getCurrentDate(it))
        }
        datePicker.show(this.supportFragmentManager, datePicker.toString())
    }

    private fun getCurrentDate(date: Long): String {
        val timeZoneUTC = TimeZone.getDefault()
        // It will be negative, so that's the -1
        val offsetFromUTC = timeZoneUTC.getOffset(Date().time) * -1
        // Create a date format, then a date object with our offset
        val simpleFormat = SimpleDateFormat("dd/MM/yyyy", Locale.US)
        val date = Date(date + offsetFromUTC)
        return simpleFormat.format(date).toString()
    }
}