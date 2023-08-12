package com.example.webdesigntutorialapp



import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.SimpleCursorAdapter
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment

class Score:DialogFragment(R.layout.score) {
    @SuppressLint("Recycle", "Range")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        val tv : TextView = view.findViewById(R.id.scoreTV)
        val c = activity?.contentResolver?.query(QuizQuestions.SCORES_URI, null, null, null)
        if (c != null && c.moveToLast()) {
            tv.text = c.getInt(c.getColumnIndex(QuizQuestions.SCORE)).toString()
        }
        var scoreArray = arrayOf<String>()
        for (i in 1..c?.count!!) {
            scoreArray = scoreArray.plus(c.getInt(c.getColumnIndex(QuizQuestions.SCORE)).toString())
            var position = c.position
            c.moveToPrevious()
        }
        scoreArray = scoreArray.sortedArray()

        val adapter = ArrayAdapter<String>(view.context, android.R.layout.simple_list_item_1, scoreArray)

        val scoresLV : ListView = view.findViewById(R.id.scoresLV)

        scoresLV.adapter = adapter
    }
}