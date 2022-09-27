package com.lnct.ac.`in`.idealab.quiz

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.lnct.ac.`in`.idealab.R


class QuizWelcomeFragment : Fragment() {

lateinit var btnStartQuiz : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_quiz_welcome, container, false)

        btnStartQuiz = view.findViewById(R.id.btnStartQuiz)

        btnStartQuiz.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                startActivity(Intent(view.context,QuestionActivity::class.java))
            }
        })

        return view
    }


}