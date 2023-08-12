package com.example.webdesigntutorialapp

import android.content.Intent
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.AdapterView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val mListView = findViewById<ListView>(R.id.list)
        val arrayAdapter: LessonAdapter

        val lessons: Array<Lesson> = arrayOf(
            Lesson("How to make a website", "This first video will get you setup for how to make a website by first setting up your computer for web development.", 5, "https://www.youtube.com/watch?v=gQojMIhELvM&list=PLoYCgNOIyGABDU532eesybur5HPBVfC1G"),
            Lesson("HTML Tutorial for Beginners", "In this tutorial html is the hero and we'll learn just how simple it is to get started...you really only need to learn a few words!", 12, "https://www.youtube.com/watch?v=RjHflb-QgVc&list=PLoYCgNOIyGABDU532eesybur5HPBVfC1G&index=2"),
            Lesson("HTML CSS Tutorial for Beginners - Web Development Tutorials For Beginners", "This video makes how CSS works easy to understand...it's just styles added to your page.  You can do a lot with them, but at the end of the day, there's very little you have to know about CSS itself to start using it.",7, "https://www.youtube.com/watch?v=J35jug1uHzE&list=PLoYCgNOIyGABDU532eesybur5HPBVfC1G&index=3"),
            Lesson("Hand-code an HTML + CSS layout", "In this web development tutorial for beginners series, we're learning html and css from the very beginning.  We'll learn by building a website from scratch rather than doing a separate html tutorial for beginners an css tutorial for beginners.", 11, "https://www.youtube.com/watch?v=dMK_3lH1YPo&list=PLoYCgNOIyGABDU532eesybur5HPBVfC1G&index=4"),
            Lesson("HTML CSS TUTORIAL FOR BEGINNERS - multiple pages", "You'll start by creating multiple HTML pages.  They can be in the same folder, or in their own folder, it doesn't matter, you just need to know where to point to them.  Then, you simply make A tags to point to them.  In HTML, when referencing a file, beginners tend to make 2 mistakes - they don't spell the file name right (or use capital/lowercase letters on accident) OR, they don't point to the right location for the HTML/CSS file.  If the HTML or CSS file is in a folder, you need to reference the folder as well.",9, "https://www.youtube.com/watch?v=iXSSHlOe47s&list=PLoYCgNOIyGABDU532eesybur5HPBVfC1G&index=6"),
            Lesson("Build an HTML + CSS Layout with Flexbox in just a few lines of code", "This course of web development tutorials for beginners will show you how to build an HTML CSS Layout, then add content with HTML CSS content blocks, which is where you learn all the tips and tricks it takes to build a website.", 7, "https://www.youtube.com/watch?v=aRMIdKRYg6c&list=PLoYCgNOIyGABDU532eesybur5HPBVfC1G&index=5"),
            Lesson("CSS SELECTORS MADE EASY - HTML CSS Tutorial for Beginners", "Using CSS selectors are the bread and butter of HTML and CSS coding.You have to think about what elements on your page you want to target and how to write rules that cleanly target and apply css styling to those elements.",11, "https://www.youtube.com/watch?v=dcCCOiQ1ZuM&list=PLoYCgNOIyGABDU532eesybur5HPBVfC1G&index=7"),
            Lesson("HTML & CSS Tutorial - Ways to code images...and how to do it well", "Here's how to properly code images into your webpages without making them fuzzy on hi-res devices like the latest iPhone.",22, "https://www.youtube.com/watch?v=7cwRaTqR4k0&list=PLoYCgNOIyGABDU532eesybur5HPBVfC1G&index=8"),
            Lesson("Responsive Design Tutorial - Tips for making web sites look great on any device", "There are a few \"gotchas\" to responsive design, and once you know them, the rest all makes sense.", 18, "https://www.youtube.com/watch?v=fgOO9YUFlGI&list=PLoYCgNOIyGABDU532eesybur5HPBVfC1G&index=9"),
            Lesson("How To Make A Website From HTML & CSS - FAST, CHEAP, EASY", "In under 10mins, turn your HTML and CSS into a website!", 9, "https://www.youtube.com/watch?v=5iUB31h2Hzs&list=PLoYCgNOIyGABDU532eesybur5HPBVfC1G&index=10"),

        )
        arrayAdapter = LessonAdapter(
            this,
            R.layout.card_view, R.id.titleTV, lessons
        )
        mListView.adapter = arrayAdapter

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.item1 -> {
                val intent = Intent(this, QuizActivity::class.java )
                startActivity(intent)
            }
        }
        return true
    }
}
