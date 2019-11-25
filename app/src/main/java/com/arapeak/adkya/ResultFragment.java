package com.arapeak.adkya;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.arapeak.adkya.ui.fragments.ChalengeFragment;
import com.arapeak.adkya.ui.fragments.SecondMatrialFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class ResultFragment extends Fragment {
    TextView txt_average,txt_result,txt_time_elapse,txt_feedback;


    public void setResult(String result) {
        this.result = result;
    }

    public void setTime_elapsed(String time_elapsed) {
        this.time_elapsed = time_elapsed;
    }

    public void setAverage(int average) {
        this.average = average;
    }

    private String result ;
   private String time_elapsed ;
   private int average ;

    public ResultFragment() {
        // Required empty public constructor
    }

    private static ResultFragment Instance;

    public static ResultFragment getInstance(){



        if (Instance==null) {
            Instance= new ResultFragment();
        }
        return Instance;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_result, container, false);

        initView(view);


       Button btn_back_to_sections =view.findViewById(R.id.btn_back_to_sections);
        btn_back_to_sections.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment fragment= SecondMatrialFragment.getInstance();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.contianer_frame, fragment).commit();
            }
        });



       Button btn_repeat_quiz =  view.findViewById(R.id.btn_repeat_quiz);
       btn_repeat_quiz.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               repeatQuiz();
           }
       });


        txt_average.setText(average+"%");
        txt_result.setText(result);
        txt_time_elapse.setText(time_elapsed);



        if (average<50){
            txt_feedback.setText("معدلك ضعيف تمرن بشكل أفضل");
        }else if (average >= 50 && average <70){
            txt_feedback.setText("معدلك جيد , ولكن عليك التمرن بشكل أكبر");

        }else if (average >=70 && average <90){
            txt_feedback.setText("معدلك جيد جدا , استمر بالتميز");

        }else if (average >=90 && average <=100){
            txt_feedback.setText("أنت من الطلاب الرائعيين الذين حصلوا على معدل عالي");

        }else {
            txt_feedback.setText("");

        }


        return view;
    }

    private void repeatQuiz() {

        Fragment fragment= new ChalengeFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.contianer_frame, fragment).commit();

    }

    private void initView(View view) {
          txt_average= view.findViewById(R.id.txt_average);
          txt_result= view.findViewById(R.id.txt_result);
          txt_time_elapse= view.findViewById(R.id.txt_time_elapse);
          txt_feedback= view.findViewById(R.id.txt_feedback);

    }

}
