package com.example.webdesigntutorialapp

import android.annotation.SuppressLint
import android.content.ContentValues
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity

class QuizActivity : AppCompatActivity() {
    @SuppressLint("Recycle", "Range", "SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)
        val questionsURI = QuizQuestions.QUESTIONS_URI
        val c = contentResolver.query(questionsURI, null, null, null)
        val questionNoTV: TextView = findViewById(R.id.questionNoTV)
        val questionTV: TextView = findViewById(R.id.questionTV)
        val button : Button = findViewById(R.id.button)
        var correct = 0

        var answer = "False"
        var counter = 1
        if (c != null && c.moveToFirst()) {
            if (counter == c.count) {
                button.text = "Finish"
            }
            questionNoTV.text = "Question #$counter"
            questionTV.text = c.getString(c.getColumnIndex(QuizQuestions.QUESTION))
            answer = c.getString(c.getColumnIndex(QuizQuestions.ANSWER))
        }
        button.setOnClickListener {
            val radioSelectedId = findViewById<RadioGroup?>(R.id.radioGroup2).checkedRadioButtonId
            if (radioSelectedId != -1) {
                val answerRadio: RadioButton = findViewById(radioSelectedId)
                if (answer == answerRadio.text) {
                    ++correct
                    Toast.makeText(this, "Correct", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Wrong", Toast.LENGTH_SHORT).show()
                }
                if (c != null && c.moveToNext()) {
                    ++counter
                    findViewById<RadioGroup?>(R.id.radioGroup2).clearCheck()
                    questionNoTV.text = "Question #$counter"
                    questionTV.text = c.getString(c.getColumnIndex(QuizQuestions.QUESTION))
                    answer = c.getString(c.getColumnIndex(QuizQuestions.ANSWER))
                    if (c.count == counter) {
                        button.text = "Finish"
                    }
                } else {
                    button.text = "Next"
                    val content = ContentValues()
                    content.put(QuizQuestions.SCORE, correct)
                    contentResolver.insert(QuizQuestions.SCORES_URI, content)
                    counter = 1
                    correct = 0
                    c?.moveToFirst()
                    questionNoTV.text = "Question #$counter"
                    questionTV.text = c?.getString(c.getColumnIndex(QuizQuestions.QUESTION))
                    answer = c?.getString(c.getColumnIndex(QuizQuestions.ANSWER)).toString()
                    findViewById<RadioGroup?>(R.id.radioGroup2).clearCheck()
                    val scoreDialog = Score()
                    scoreDialog.show(supportFragmentManager, "Score dialog")
                }
            } else {
                Toast.makeText(this, "Choose an answer", Toast.LENGTH_SHORT).show()
            }
        }
    }
}