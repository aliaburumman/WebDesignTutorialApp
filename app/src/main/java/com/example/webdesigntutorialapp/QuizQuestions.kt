package com.example.webdesigntutorialapp

import android.content.*
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteQueryBuilder
import android.net.Uri
import android.text.TextUtils
import java.lang.IllegalArgumentException
import java.util.HashMap

class QuizQuestions : ContentProvider() {
    companion object {
        val PROVIDER_NAME = "com.example.WebDesignTutorialApp.QuizQuestions"
        val QUESTIONS_URL = "content://" + PROVIDER_NAME + "/questions"
        val QUESTIONS_URI = Uri.parse(QUESTIONS_URL)
        val SCORES_URL = "content://" + PROVIDER_NAME + "/scores"
        val SCORES_URI = Uri.parse(SCORES_URL)
        val _ID = "_id"
        val QUESTION = "question"
        val ANSWER = "answer"
        private val QUESTIONS_PROJECTION_MAP: HashMap<String, Boolean>? = null
        val QUESTIONS = 1
        val QUESTION_ID = 2
        val SCORE = "score"
        private val SCORES_PROJECTION_MAP: HashMap<Int, Int>? = null
        val SCORES = 1
        val SCORES_ID = 2
        val uriMatcher: UriMatcher? = null
        val DATABASE_NAME = "Quiz"
        val QUESTIONS_TABLE_NAME = "questions"
        val SCORES_TABLE_NAME = "scores"
        val DATABASE_VERSION = 1
        val CREATE_QUESTIONS_DB_TABLE =
            "CREATE TABLE $QUESTIONS_TABLE_NAME ($_ID INTEGER PRIMARY KEY AUTOINCREMENT, $QUESTION TEXT NOT NULL, $ANSWER TEXT NOT NULL);"
        val CREATE_SCORES_DB_TABLE =
            "CREATE TABLE $SCORES_TABLE_NAME ($_ID INTEGER PRIMARY KEY AUTOINCREMENT, $SCORE INTEGER NOT NULL);"
        private var qUriMatcher = UriMatcher(UriMatcher.NO_MATCH);

        init {
            qUriMatcher.addURI(PROVIDER_NAME, "questions", QUESTIONS);
            qUriMatcher.addURI(PROVIDER_NAME, "questions/#", QUESTION_ID);
        }

        private var sUriMatcher = UriMatcher(UriMatcher.NO_MATCH);

        init {
            sUriMatcher.addURI(PROVIDER_NAME, "scores", SCORES);
            sUriMatcher.addURI(PROVIDER_NAME, "scores/#", SCORES_ID);
        }

        private var db: SQLiteDatabase? = null  //private

        //internal class
        private class DatabaseHelper internal constructor(context: Context?) :
            SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
            override fun onCreate(db: SQLiteDatabase) {
                db.execSQL(CREATE_QUESTIONS_DB_TABLE)
                db.execSQL(CREATE_SCORES_DB_TABLE)
                val qArr =
                    arrayOf(
                        mapOf("statement" to " HTML stands for Hyper Text Markup Language and is used for structuring web content.", "answer" to "True"),
                        mapOf("statement" to "CSS is primarily used for styling and layout of web content.", "answer" to "False"),
                        mapOf("statement" to "JavaScript is a programming language commonly used for client-side scripting in web development.", "answer" to "True"),
                        mapOf("statement" to "You can create a responsive design by using media queries in CSS to adapt your website layout to different screen sizes.", "answer" to "False"),
                        mapOf("statement" to " CSS Grid and Flexbox are both layout systems in CSS, but CSS Grid is better suited for complex two-dimensional layouts.", "answer" to "True"),
                        mapOf("statement" to "HTML comments are not displayed on the webpage when viewed in a browser.", "answer" to "False"),
                        mapOf("statement" to "The \"display: none;\" CSS property hides an element from the page, but it still occupies space in the layout.", "answer" to "True"),
                        mapOf("statement" to "Semantic HTML elements (e.g., \"header,\" \"nav,\" \"article\") contribute to better website structure and can improve search engine optimization.", "answer" to "True"),
                        mapOf("statement" to "\"ID\" attributes in HTML can be used multiple times within the same document without any issues.", "answer" to "False"),
                        mapOf("statement" to "The \"margin\" property is used to define the inner spacing of an element's content.", "answer" to "False"),
                        mapOf("statement" to " The \"target\" attribute in HTML links is used to open the link in a new tab/window by default.", "answer" to "False"),
                        mapOf("statement" to "The \"z-index\" property in CSS can only be applied to positioned elements (elements with position other than \"static\").", "answer" to "False"),
                        mapOf("statement" to " The \"box-sizing\" property in CSS can be set to \"border-box\" to include an element's padding and border within its specified width and height.", "answer" to "True"),
                        mapOf("statement" to "The \"alt\" attribute in the HTML \"img\" tag is used to provide alternative text for an image, which is important for accessibility.", "answer" to "True"),
                    )
                for (q in qArr) {
                    val content = ContentValues()
                    content.put(QUESTION, q["statement"].toString())
                    content.put(ANSWER, q["answer"].toString())

                    db.insert(QUESTIONS_TABLE_NAME, "", content)
                }
            }

            override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
                db.execSQL("DROP TABLE IF EXISTS $QUESTIONS_TABLE_NAME")
                db.execSQL("DROP TABLE IF EXISTS $SCORES_TABLE_NAME")
                onCreate(db)
            }
        }
    }

    override fun onCreate(): Boolean {
        val context = context
        val dbHelper = DatabaseHelper(context)
        db = dbHelper.writableDatabase

        return db != null
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        val questionUri = qUriMatcher.match(uri)
        val scoresUri = sUriMatcher.match(uri)
        var sortOrder = sortOrder
        val qb = SQLiteQueryBuilder()
        if (questionUri != -1) {
            // Query from questions table
            qb.tables = QUESTIONS_TABLE_NAME
            when (questionUri) {
                QUESTION_ID -> qb.appendWhere(_ID + "=" + uri.pathSegments[1])
                else -> {
                    null
                }
            }
            if (sortOrder == null || sortOrder === "") {
                sortOrder = _ID
            }
        } else if (scoresUri != -1) {
            // Query from scores table
            qb.tables = SCORES_TABLE_NAME
            when (scoresUri) {
                SCORES_ID -> qb.appendWhere(_ID + "=" + uri.pathSegments[1])
                else -> {
                    null
                }
            }
            if (sortOrder == null || sortOrder === "") {
                sortOrder = _ID
            }
        }
        val query = qb.query(db, projection, selection, selectionArgs, null, null, sortOrder)
        query.setNotificationUri(context!!.contentResolver, uri)
        return query
    }

    override fun getType(uri: Uri): String? {
        val questionUri = qUriMatcher.match(uri)
        val scoresUri = sUriMatcher.match(uri)
        if (questionUri != -1) {
            // Delete from questions table
            when (questionUri) {
                QUESTIONS -> return "Info about questions and their answers"
                QUESTION_ID -> return "Info about specific question and answer"
                else -> throw IllegalArgumentException("Unknown URI $uri")
            }
        } else if (scoresUri != -1) {
            // Delete from scores table
            when (scoresUri) {
                SCORES -> return "All scores of quizzes taken by user"
                SCORES_ID -> return "Specific score of a quiz taken by user"
                else -> throw IllegalArgumentException("Unknown URI $uri")
            }
        }
        throw IllegalArgumentException("Unknown URI $uri")
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val questionUri = qUriMatcher.match(uri)
        val scoresUri = sUriMatcher.match(uri)
        if (questionUri != -1) {
            // Insert into questions table
            val rowID = db!!.insert(QUESTIONS_TABLE_NAME, "", values)
            if (rowID > 0) {
                val _uri = ContentUris.withAppendedId(QUESTIONS_URI, rowID)
                context!!.contentResolver.notifyChange(_uri, null)
                return _uri
            }
        } else if (scoresUri != -1) {
            // Insert into scores table
            val rowID = db!!.insert(SCORES_TABLE_NAME, "", values)
            if (rowID > 0) {
                val _uri = ContentUris.withAppendedId(SCORES_URI, rowID)
                context!!.contentResolver.notifyChange(_uri, null)
                return _uri
            }
        }
        throw SQLException("Failed to query a record from $uri")
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        var count = 0
        val questionUri = qUriMatcher.match(uri)
        val scoresUri = sUriMatcher.match(uri)
        if (questionUri != -1) {
            // Delete from questions table
            when (questionUri) {
                QUESTIONS -> count = db!!.delete(
                    QUESTIONS_TABLE_NAME, selection,
                    selectionArgs
                )
                QUESTION_ID -> {
                    val id = uri.pathSegments[1]
                    count = db!!.delete(
                        QUESTIONS_TABLE_NAME,
                        _ID + " = " + id + if (!TextUtils.isEmpty(selection)) " AND ($selection)" else "",
                        selectionArgs,
                    )
                }
                else -> throw IllegalArgumentException("Unknown URI $uri")
            }
        } else if (scoresUri != -1) {
            // Delete from scores table
            when (scoresUri) {
                SCORES -> count = db!!.delete(
                    QUESTIONS_TABLE_NAME, selection,
                    selectionArgs
                )
                SCORES_ID -> {
                    val id = uri.pathSegments[1]
                    count = db!!.delete(
                        SCORES_TABLE_NAME,
                        _ID + " = " + id + if (!TextUtils.isEmpty(selection)) " AND ($selection)" else "",
                        selectionArgs,
                    )
                }
                else -> throw IllegalArgumentException("Unknown URI $uri")
            }
        }
        context!!.contentResolver.notifyChange(uri, null)
        return count
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        var count = 0
        val questionUri = qUriMatcher.match(uri)
        val scoresUri = sUriMatcher.match(uri)
        if (questionUri != -1) {
            // Delete from questions table
            when (questionUri) {
                QUESTIONS -> count = db!!.update(
                    QUESTIONS_TABLE_NAME, values, selection,
                    selectionArgs
                )
                QUESTION_ID -> {
                    val id = uri.pathSegments[1]
                    count = db!!.update(
                        QUESTIONS_TABLE_NAME,
                        values,
                        _ID + " = " + id + if (!TextUtils.isEmpty(selection)) " AND ($selection)" else "",
                        selectionArgs,
                    )
                }
                else -> throw IllegalArgumentException("Unknown URI $uri")
            }
        } else if (scoresUri != -1) {
            // Delete from scores table
            when (scoresUri) {
                SCORES -> count = db!!.update(
                    QUESTIONS_TABLE_NAME, values, selection,
                    selectionArgs
                )
                SCORES_ID -> {
                    val id = uri.pathSegments[1]
                    count = db!!.update(
                        SCORES_TABLE_NAME,
                        values,
                        _ID + " = " + id + if (!TextUtils.isEmpty(selection)) " AND ($selection)" else "",
                        selectionArgs,
                    )
                }
                else -> throw IllegalArgumentException("Unknown URI $uri")
            }
        }
        context!!.contentResolver.notifyChange(uri, null)
        return count
    }
}