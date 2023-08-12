package com.example.webdesigntutorialapp

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat

class LessonAdapter(
    context: Context,
    resource: Int,
    textViewResourceId: Int,
    objects: Array<out Lesson>
) : ArrayAdapter<Lesson>(context, resource, textViewResourceId, objects) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var resultView: View? = convertView
        if (resultView == null) {
            resultView = LayoutInflater.from(context).inflate(R.layout.card_view, parent, false)
        }
        val lessonTV: TextView = resultView!!.findViewById<TextView>(R.id.titleTV)
        val descriptionTV: TextView = resultView.findViewById<TextView>(R.id.descriptionTV)
        val estimatedTV: TextView = resultView.findViewById<TextView>(R.id.estimatedTV)
        val lesson:Lesson = getItem(position)!!
        lessonTV.text = lesson.name
        descriptionTV.text = lesson.description
        estimatedTV.text = lesson.estimated.toString()
        resultView.setOnClickListener{
            val youtubeIntent = Intent(Intent.ACTION_VIEW, Uri.parse(lesson.youtubeUrl)).apply {  }
            ContextCompat.startActivity(context, youtubeIntent, null)
        }
        return resultView
    }

}