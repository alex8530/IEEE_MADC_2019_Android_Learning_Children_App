package com.arapeak.adkya.ui.fragments;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;

import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import io.github.sidvenu.mathjaxview.MathJaxView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


import android.os.CountDownTimer;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.arapeak.adkya.R;
import com.arapeak.adkya.ResultFragment;
import com.arapeak.adkya.api.APIService;
import com.arapeak.adkya.api.ServiceGenerator;
import com.arapeak.adkya.listener.IOnBackPressed;
import com.arapeak.adkya.model.getQuestion.Option;
import com.arapeak.adkya.model.getQuestion.Question;
import com.arapeak.adkya.model.getQuestion.ResultGetQuestion;
import com.arapeak.adkya.model.getanswer.Answer;
import com.arapeak.adkya.model.getanswer.ResultGetAnswer;
import com.arapeak.adkya.ui.activites.HomeActivity;
import com.arapeak.adkya.ui.activites.Login1Activity;
import com.arapeak.adkya.utils.Common;
import com.arapeak.adkya.utils.CustomProgress;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChalengeFragment extends Fragment implements IOnBackPressed {
    private static final String TAG = "ChalengeFragment";

    IOnBackPressed mListener;
    private Button  btn_send_answer;

    MathJaxView txt1,txt2,txt3,txt4;

    ConstraintLayout btn1,btn2,btn3,btn4;

    ImageView img_answer1,img_answer2,img_answer3,img_answer4;

  private   TextView tv_question,tv_time_elapesd,tv_question_number;
  ImageView img_question;



    AlertDialog dialog;
    AlertDialog.Builder mBuilder;
    Integer mOptionSelectedId=null;

   Question CurrunQuestion= new Question();
   Answer CurrunAnswer= new Answer();


    ArrayList<Option> options= new ArrayList<>();

    int questionNumber =1 ; //the first question is 1
    SeekBar mSeekBar;


     Integer repeatExam=1;
    /*
       //0>>  will reapet the exam and start from question 1 and increase the question questionNumber after new request
         * but if i put 1 >> that mean after new question request > the questionNumber with alwas return 1 ,
         * so >> if the user go out from the app > i put repeatExam = 1
         * * so here >> always start from question 1
        */


    long totalSeconds = 10000000;//max second
    long intervalSeconds = 1;
    CountDownTimer timer;
      int hour=0;
      int minte=0;
      int sec=0;

      boolean is_first_time=true;


      public static boolean isInChalange=false;


    CustomProgress customProgress= new CustomProgress();

    MathJaxView txt_fraction;



   static int[] soundCorrectList ;
   static int[] soundErrorList  ;


    SoundPool sounds;


    private static String btn_next="السؤال التالي" ;
    private static String btn_answer="أجب" ;


    Button btn_hide;


    ConstraintLayout constraintLayout;


    public ChalengeFragment() {
        // Required empty public constructor

     }
    private static ChalengeFragment Instance;

    public static ChalengeFragment getInstance(){


         
        if (Instance==null) {
            Instance= new ChalengeFragment();
        }
        return Instance;
    }

    @Override
    public void onStop() {
        super.onStop();
        timer.cancel();
        isInChalange=false;

         //be sure to re rest the call from quiestion number 1 if you leave the fragment

        repeatExam=1;



         //release audio
          cleanUpIfEnd();
          is_first_time=true;
     }

    @Override
    public void onStart() {
        super.onStart();

      //  is_first_time=true;
        initSounds();
        Log.d(TAG, "repeatExam on start: " +repeatExam);


    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        isInChalange=true;

          hour=0;
          minte=0;
          sec=0;

        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_chalenge, container, false);
        final ImageView menubar= view.findViewById(R.id.menubar);
         TextView tv_title= view.findViewById(R.id.tv_title);
        tv_title.setText(Common.CurrentSection.getName());
        init(view);


        //reset previous data
//        questionNumber=1;
        tv_question_number.setText(1 + "/20");
         mSeekBar.setProgress(1);


        sendQuestionRequest();

          timer = new CountDownTimer(totalSeconds * 1000, intervalSeconds * 1000) {


            public void onTick(long millisUntilFinished) {
                sec++;
                if (sec==59){
                    sec=0;
                    minte++;
                    if (minte ==59){
                        minte=0;
                        hour++;
                    }
                }

                String timeString = String.format(Locale.getDefault(),"%02d:%02d:%02d",hour,minte,sec);
                Log.d("seconds elapsed: " , timeString );

                tv_time_elapesd.setText(timeString);
            }

            public void onFinish() {
                Log.d( "done!", "Time's up!");
            }

        } ;


        menubar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //THIS IS FOR SHOW THE DrawerLayout
                //because the nav in the activity .. i use this
                DrawerLayout drawer = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);

                drawer.openDrawer(GravityCompat.START);
            }
        });

        ImageView notImage= view.findViewById(R.id.imageView9);
        notImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeActivity.replaceFragmentFromActivity(NotificationFragment.getInstance());

            }
        });



         btn1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

            //     txt1.setColor(getResources().getColor(R.color.white));
                //if there is image in this option>> show dailog and show the image


//                txt2.setTextColor(getResources().getColor(R.color.black));
//                txt3.setTextColor(getResources().getColor(R.color.black));
//                txt4.setTextColor(getResources().getColor(R.color.black));


                //set option 1
                mOptionSelectedId=options.get(0).getId();


//                playSound(soundList[getRandNumber(1,0)]);

                //change color when select the answer btn
                btn_send_answer.setBackgroundTintList(getResources().getColorStateList(R.color.greenColor2));

                return false;
            }
        });

        btn2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
//                 txt1.setTextColor(getResources().getColor(R.color.black));

//                txt2.setTextColor(getResources().getColor(R.color.white));
//                txt3.setTextColor(getResources().getColor(R.color.black));
//                txt4.setTextColor(getResources().getColor(R.color.black));


                //set option 2
                mOptionSelectedId=options.get(1).getId();
//                playSound(soundList[getRandNumber(1,0)]);

                btn_send_answer.setBackgroundTintList(getResources().getColorStateList(R.color.greenColor2));


                return false;
            }
        });

        btn3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

//                 txt1.setTextColor(getResources().getColor(R.color.black));

//                txt2.setTextColor(getResources().getColor(R.color.black));
//                txt3.setTextColor(getResources().getColor(R.color.white));
//                txt4.setTextColor(getResources().getColor(R.color.black));
//

                //set option 3
                mOptionSelectedId=options.get(2).getId();
//                playSound(soundList[getRandNumber(1,0)]);


                btn_send_answer.setBackgroundTintList(getResources().getColorStateList(R.color.greenColor2));


                return false;
            }
        });


        btn4.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

               //  txt1.setTextColor(getResources().getColor(R.color.black));

//                txt2.setTextColor(getResources().getColor(R.color.black));
//                txt3.setTextColor(getResources().getColor(R.color.black));
//                txt4.setTextColor(getResources().getColor(R.color.white));



                //set option 4
                mOptionSelectedId=options.get(3).getId();
//                playSound(soundList[getRandNumber(1,0)]);



                btn_send_answer.setBackgroundTintList(getResources().getColorStateList(R.color.greenColor2));


                return false;
            }
        });

        btn_send_answer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btn_send_answer.getText().equals(btn_answer)){
                    sendAnswerRequest();

                }else {
                    if (btn_send_answer.getText().equals(btn_next)){
                        //get new question
                        sendQuestionRequest();
                    }
                }
            }
        });



        changeStatusBarColor();


        initSounds();
        return view;
    }

    private void initSounds() {
        createSoundPool();


         soundCorrectList =new int[8];
          soundErrorList =new int[2];

        soundCorrectList[0]=sounds.load(getActivity(),R.raw.correct1,1);
        soundCorrectList[1]=sounds.load(getActivity(),R.raw.correct2,1);
        soundCorrectList[2]=sounds.load(getActivity(),R.raw.correct3,1);
        soundCorrectList[3]=sounds.load(getActivity(),R.raw.correct4,1);
        soundCorrectList[4]=sounds.load(getActivity(),R.raw.correct5,1);
        soundCorrectList[5]=sounds.load(getActivity(),R.raw.correct6,1);
        soundCorrectList[6]=sounds.load(getActivity(),R.raw.correct7,1);
        soundCorrectList[7]=sounds.load(getActivity(),R.raw.correct8,1);


        soundErrorList[0]=sounds.load(getActivity(),R.raw.error1,1);
        soundErrorList[1]=sounds.load(getActivity(),R.raw.error2,1);

    }


    private void showImageDailog(String urlImage) {

        mBuilder = new AlertDialog.Builder(getActivity());
        View mView = getLayoutInflater().inflate(R.layout.show_image_dailog, null);
        mBuilder.setView(mView);
        mBuilder.setCancelable(false);
        dialog = mBuilder.create();
        dialog.show();

        final Button btn_ok = mView.findViewById(R.id.btn_ok);
        final ImageView img = mView.findViewById(R.id.img);

        Picasso.get().load(urlImage).placeholder(R.drawable.ic_ans_img_icon).into(img);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
             }});
    }


    private void init(View view) {
        btn1= view.findViewById(R.id.btn_answer_1);
        btn2= view.findViewById(R.id.btn_answer_2);
        btn3= view.findViewById(R.id.btn_answer_3);
        btn4= view.findViewById(R.id.btn_answer_4);
        btn_send_answer= view.findViewById(R.id.btn_send_answer);
        tv_question= view.findViewById(R.id.textView25);
        tv_time_elapesd = view.findViewById(R.id.tv_time);
        tv_time_elapesd.setText("00:00:00");
        tv_question_number = view.findViewById(R.id.tv_question_number);
        tv_question_number.setText(questionNumber + "/20");
        mSeekBar=view.findViewById(R.id.seekbar);
        img_question=view.findViewById(R.id.img_question);
        mSeekBar.setProgress(questionNumber);

        txt1=view.findViewById(R.id.txt1);
        txt2=view.findViewById(R.id.txt2);
        txt3=view.findViewById(R.id.txt3);
        txt4=view.findViewById(R.id.txt4);
        img_answer1=view.findViewById(R.id.img_answer1);
        img_answer2=view.findViewById(R.id.img_answer2);
        img_answer3=view.findViewById(R.id.img_answer3);
        img_answer4=view.findViewById(R.id.img_answer4);


        txt_fraction=view.findViewById(R.id.txt_fraction);



        btn1.setVisibility(View.INVISIBLE);
        btn2.setVisibility(View.INVISIBLE);
        btn3.setVisibility(View.INVISIBLE);
        btn4.setVisibility(View.INVISIBLE);



        btn_hide=view.findViewById(R.id.btn_hide);



          constraintLayout= view.findViewById(R.id.consChal);


    }

    private void sendAnswerRequest() {

        customProgress.showProgress(getActivity(),"الرجاء الانتظار ",  false);

        // todo get time , change current question
        String QuestionID=String.valueOf(CurrunQuestion.getId());
        String optionID=String.valueOf( mOptionSelectedId );
        String timeElapsed=tv_time_elapesd.getText().toString();

        if (mOptionSelectedId==null){
             customProgress.hideProgress();
            Toast.makeText(getActivity(), "الرجاء اختيار اجابة", Toast.LENGTH_SHORT).show();
        }else {

            APIService apiService= ServiceGenerator.createService(APIService.class,Common.retrieveUserDataPreferance(getActivity()).getRememberToken());
            Call<ResultGetAnswer> call =  apiService.getAnswer(
                    QuestionID,
                    optionID,
                    String.valueOf(repeatExam),
                    Common.arabicToDecimal(timeElapsed)

            );
             call.enqueue(new Callback<ResultGetAnswer>() {
                @Override
                public void onResponse(Call<ResultGetAnswer> call, Response<ResultGetAnswer> response) {
                     customProgress.hideProgress();

                    if (response.isSuccessful()){
                        CurrunAnswer =response.body().getData() ;

                        if (CurrunAnswer.getQuestionNumber()==20) {
                            //1 = true 0 = false
                            timer.cancel();
//                            showEndDaialog();
                            getDataAndPassToResultFragment();
                        }else {
                            questionNumber=CurrunAnswer.getQuestionNumber()+1;
                            showCorrectAnswerThenSendNewQuestionRequest();
                            //reset selected btn , must be after show correct answer
                            mOptionSelectedId=null;
                            //i get the answer
                            //change the reatansewr to 0 ,>> to increase  questionNumber in the next request
                            //change to 1 only in defult state or if the user go out from the app
                            repeatExam=0;

                            Log.d(TAG, "repeatExam on inside answer: " +repeatExam);



                        }


                        //get next question todo ? or send new request question?
                         //you may need to get the next qouestion >> and equal to current question ...
                        // but the two question are randomly get that from api

                    }
                }
                @Override
                public void onFailure(Call<ResultGetAnswer> call, Throwable t) {
                     customProgress.hideProgress();

                    Toast.makeText(getActivity(), "خطأ في الاتصال", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }


    private void showCorrectAnswerThenSendNewQuestionRequest() {

        // 0 >> false   1 >>true
        int isCorrect= CurrunAnswer.getIsCorrect();
        int correctOpttionId= CurrunAnswer.getCorrectOptions().get(0);

        if (isCorrect==1){
            //true >> make the answer grean
            if ( options.get(0).getId()==correctOpttionId){
//            if true and   btn1 was selected  >> change background color
                btn1.setBackgroundResource(R.drawable.shape_btn_correct_answer);

            }else if (options.get(1).getId()==correctOpttionId){
                btn2.setBackgroundResource(R.drawable.shape_btn_correct_answer);
                
            }else if (options.get(2).getId()==correctOpttionId){
                btn3.setBackgroundResource(R.drawable.shape_btn_correct_answer);

            }else if (options.get(3).getId()==correctOpttionId){
                btn4.setBackgroundResource(R.drawable.shape_btn_correct_answer);

            }

                
        }else {
            //if quesetion not true >> make the correct answer green< and the false answer is red
          //  Toast.makeText(getActivity(), "ليس صحيح", Toast.LENGTH_SHORT).show();

            if ( options.get(0).getId()==correctOpttionId){
//            if true and   btn1 was selected  >> change background color
                btn1.setBackgroundResource(R.drawable.shape_btn_correct_answer);

            }else if (options.get(1).getId()==correctOpttionId){
                btn2.setBackgroundResource(R.drawable.shape_btn_correct_answer);

            }else if (options.get(2).getId()==correctOpttionId){
                btn3.setBackgroundResource(R.drawable.shape_btn_correct_answer);

            }else if (options.get(3).getId()==correctOpttionId){
                btn4.setBackgroundResource(R.drawable.shape_btn_correct_answer);

            }



            if (options.get(0).getId() ==mOptionSelectedId){
                btn1.setBackgroundResource(R.drawable.shape_btn_wrong_answer);

            }else if (options.get(1).getId() ==mOptionSelectedId){
                btn2.setBackgroundResource(R.drawable.shape_btn_wrong_answer);

            }else if (options.get(2).getId() ==mOptionSelectedId){
                btn3.setBackgroundResource(R.drawable.shape_btn_wrong_answer);

            }else if (options.get(3).getId() ==mOptionSelectedId){
                btn4.setBackgroundResource(R.drawable.shape_btn_wrong_answer);

            }
        }
       // sendQuestionRequest();
       // showDailogCorrectDiscriptionAnswer();

        if (CurrunAnswer.getIsCorrect()==1){

            runCorrectSound();
            showNextQuestion();

            //re hide the button
            btn_hide.setVisibility(View.GONE);

        }else {

            runWrongSound();
            showNextQuestion();
            showErrorIcon();

        }




    }

    private void showErrorIcon() {
        btn_hide.setVisibility(View.VISIBLE);
        btn_hide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showWrongDailog();
            }
        });
    }

    private void showNextQuestion() {
        btn_send_answer.setText(btn_next);

    }

    private void showDailogCorrectDiscriptionAnswer() {

        if (CurrunAnswer.getIsCorrect()==1){
           showCorrectDailog();
           runCorrectSound();
        }else {
            showWrongDailog();
           runWrongSound();

        }

    }

    private void runCorrectSound() {
        if (soundCorrectList!=null)
        playSound(soundCorrectList[getRandNumber(7,0)]);

    }

    private void runWrongSound() {
        if (soundErrorList!=null)
            playSound(soundErrorList[getRandNumber(1,0)]);
    }

    private void showWrongDailog() {

        mBuilder = new AlertDialog.Builder(getActivity() );
        View mView = getLayoutInflater().inflate(R.layout.wrong_answer_dailog, null);
        mBuilder.setCancelable(false);
        mBuilder.setView(mView);
        dialog = mBuilder.create();
        dialog.show();

        final Button btn_ok =  mView.findViewById(R.id.btn_ok);
        final TextView tv_disciption =  mView.findViewById(R.id.textView);

        tv_disciption.setText(Html.fromHtml(CurrunAnswer.getRetypeAnswer()));
        String textt=CurrunAnswer.getRetypeAnswer().toString().trim();
        Log.d(TAG, "showWrongDailog: "+Html.fromHtml(CurrunAnswer.getRetypeAnswer()).toString().trim());
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

                //get new question
//               sendQuestionRequest();

                //re hide the button
               // btn_hide.setVisibility(View.INVISIBLE);
            }
        });
    }
    private void showCorrectDailog() {

        mBuilder = new AlertDialog.Builder(getActivity() );
        View mView = getLayoutInflater().inflate(R.layout.correect_answer_dailog, null);
        mBuilder.setCancelable(false);
        mBuilder.setView(mView);
        dialog = mBuilder.create();
        dialog.show();


        final Button btn_ok =  mView.findViewById(R.id.btn_ok);

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

                //get new question
                sendQuestionRequest();
            }
        });
    }


    private void getDataAndPassToResultFragment(){

        String result=CurrunAnswer.getCorrectAnswers()+"/20";
        String time_elapsed= tv_time_elapesd.getText().toString();
        int average= CurrunAnswer .getScore() ;


//go to result fragment
        ResultFragment fragment= ResultFragment.getInstance();
        fragment.setResult(result);
        fragment.setAverage(average);
        fragment.setTime_elapsed(time_elapsed);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.contianer_frame, fragment).commit();



    }
    private void showEndDaialog() {




        /* this is for new api check report*/
        String materialId=String.valueOf(Common.CurrentMaterial.getId());
        String seconderyMaterialId=String.valueOf(Common.CurrentSecondMaterial.getId());
        String sectionId=String.valueOf(Common.CurrentSection.getId());
        String tok =  Common.retrieveUserDataPreferance(getActivity()).getRememberToken();

        Log.d(TAG, "showDialog: " + materialId +":"+seconderyMaterialId +":"+sectionId+":"+tok);

        mBuilder = new AlertDialog.Builder(getActivity() );
        View mView = getLayoutInflater().inflate(R.layout.dialog_complete_message, null);
        TextView tv_time_elapsed = mView.findViewById(R.id.tv_time_elapsed);
        TextView tv_result = mView.findViewById(R.id.tv_result);
        tv_result.setText(CurrunAnswer.getCorrectAnswers()+"/20");
        tv_time_elapsed.setText(tv_time_elapesd.getText().toString());
        mBuilder.setView(mView);
        mBuilder.setCancelable(false);
        dialog = mBuilder.create();
        dialog.show();

        final Button btn_back_to_sections =  mView.findViewById(R.id.btn_back_to_sections);

        btn_back_to_sections.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Fragment fragment= SecondMatrialFragment.getInstance();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.contianer_frame, fragment).commit();
            }
        });


        final Button btn_next_section =  mView.findViewById(R.id.btn_next_section);

        btn_next_section.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

                //save ids

                //increase the position
                Common.CURRENT_ADAPTER_SECTION_POSITION++;
                //parent
                Common.CurrentSecondMaterial=
                        Common.CURRENT_List_MATERIAL_DATA.
                                get(Common.CURRENT_ADAPTER_SECTION_PARENT_POSITION);


                if ( Common.CURRENT_ADAPTER_SECTION_POSITION  <= Common.CurrentSecondMaterial.getSections().size()-1 ){

                    //child
                    //remember increase one to next object
                    Common.CurrentSection=
                            Common.CURRENT_List_MATERIAL_DATA.
                                    get(Common.CURRENT_ADAPTER_SECTION_PARENT_POSITION).
                                    getSections().
                                    get(Common.CURRENT_ADAPTER_SECTION_POSITION);




                    Log.d(TAG, "Alex: " +Common.CURRENT_ADAPTER_SECTION_PARENT_POSITION);
                    Log.d(TAG, "Alex: " +Common.CURRENT_ADAPTER_SECTION_POSITION+1);
                    Log.d(TAG, "Alex: " + Common.CurrentSecondMaterial.getName());
                    Fragment fragment= new ChalengeFragment();//new fragment and not from instance because i need new one
                    FragmentManager fragmentManager =getActivity().getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.contianer_frame, fragment).commit();
                }else {
                    Toast.makeText(getActivity(), "انتهى القسم ", Toast.LENGTH_SHORT).show();
                    Fragment fragment=SecondMatrialFragment.getInstance(); //new fragment and not from instance because i need new one
                    FragmentManager fragmentManager =getActivity().getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.contianer_frame, fragment).commit();
                }


            }
        });



    }

    @Override
    public void onResume(){
        super.onResume();
        timer.start();
    }

    @Override
    public void onPause() {
        super.onPause();
      //  timer.cancel();
        Log.d(TAG, "repeatExam on pause: " +repeatExam);


    }

    private void showAlarmNoteToEndTheQuiz() {
        if (timer!=null){
            timer.cancel();

            mBuilder = new AlertDialog.Builder(getActivity() );
            View mView = getLayoutInflater().inflate(R.layout.show_alarm_message_to_end_quiz, null);
            mBuilder.setView(mView);
            mBuilder.setCancelable(false);
            dialog = mBuilder.create();
            dialog.show();

            final Button btn_ok =  mView.findViewById(R.id.btn_ok);
            final Button btn_cancel =  mView.findViewById(R.id.btn_cancel);

            btn_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    SendEndCheckReportRequest();


                }
            });
            btn_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();

                }
            });

            //dismiss if cancel
        }else {

            Activity activity = getActivity();
            if(activity != null){

                // etc ...
                startActivity(new Intent( activity, Login1Activity.class));

            }else {
                Log.d(TAG, "no activity ");
            }



        }

    }

    private void SendEndCheckReportRequest() {

        customProgress.showProgress(getActivity(),"الرجاء الانتظار..",false);

        String materialId=String.valueOf(Common.CurrentMaterial.getId());
        String seconderyMaterialId=String.valueOf(Common.CurrentSecondMaterial.getId());
        String sectionId=String.valueOf(Common.CurrentSection.getId());
        String tok =  Common.retrieveUserDataPreferance(getActivity()).getRememberToken();
        APIService apiService= ServiceGenerator.createService(APIService.class,tok);
        Call<ResponseBody> call= apiService.SendCheckReports(materialId,seconderyMaterialId,sectionId);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    timer.cancel();
                    dialog.dismiss();
                    customProgress.hideProgress();

                    Fragment fragment= SecondMatrialFragment.getInstance();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.contianer_frame, fragment).commit();
                }else {
                    customProgress.hideProgress();

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, "onFailure: error in send check report");
                customProgress.hideProgress();

            }
        });

    }


    private void resetAnswerButton() {
        btn1.clearFocus();
        btn2.clearFocus();
        btn3.clearFocus();
        btn4.clearFocus();

        //restore defult color
//         txt1.setTextColor(getResources().getColor(R.color.black));
//        txt2.setTextColor(getResources().getColor(R.color.black));
//        txt3.setTextColor(getResources().getColor(R.color.black));
//        txt4.setTextColor(getResources().getColor(R.color.black));

        //rest background
        btn1.setBackgroundResource(R.drawable.shape_btn_answer);
        btn2.setBackgroundResource(R.drawable.shape_btn_answer);
        btn3.setBackgroundResource(R.drawable.shape_btn_answer);
        btn4.setBackgroundResource(R.drawable.shape_btn_answer);



    }

    private void sendQuestionRequest() {
        customProgress.showProgress(getActivity(),"الرجاء الانتظار ",  false);
         String tok=Common.retrieveUserDataPreferance(getActivity()).getRememberToken() ;
        APIService apiService= ServiceGenerator.createService(APIService.class,tok);

        String materialId=String.valueOf(Common.CurrentMaterial.getId());
        String seconderyMaterialId=String.valueOf(Common.CurrentSecondMaterial.getId());
        String sectionId=String.valueOf(Common.CurrentSection.getId());

       Call<ResultGetQuestion> call =  apiService.getQuestion(materialId,seconderyMaterialId,sectionId);
       //todo get parameter
       call.enqueue(new Callback<ResultGetQuestion>() {
           @Override
           public void onResponse(Call<ResultGetQuestion> call, Response<ResultGetQuestion> response) {

               if (response.isSuccessful()){

                   //check if there is no question
                   if (response.body().getData().getText().equals("")){
                        customProgress.hideProgress();

                       showNoQuestionDailog();
                   }else {
                       CurrunQuestion= response.body().getData() ;
                       options= (ArrayList<Option>) CurrunQuestion.getOptions();
                       resetAnswerButton();
                       updateUiWithData();
                        customProgress.hideProgress();


                   }
               }
           }

           @Override
           public void onFailure(Call<ResultGetQuestion> call, Throwable t) {
                customProgress.hideProgress();

               Toast.makeText(getActivity(), "خطأ في الاتصال", Toast.LENGTH_SHORT).show();
           }
       });
    }

    private void showNoQuestionDailog() {
        timer.cancel();
        mBuilder = new AlertDialog.Builder(getActivity() );
        View mView = getLayoutInflater().inflate(R.layout.no_question_dailog, null);
        mBuilder.setView(mView);
        mBuilder.setCancelable(false);
        dialog = mBuilder.create();
        dialog.show();

        final Button btn_ok =  mView.findViewById(R.id.btn_ok);

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timer.cancel();
                dialog.dismiss();
                Fragment fragment= SecondMatrialFragment.getInstance();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.contianer_frame, fragment).commit();
            }
        });
    }


    private void updateUiWithData() {


        if (CurrunQuestion!= null && options !=null){


            //chack if there is 4 option or 3 or 2 or 1 ..
            if (!TextUtils.isEmpty(options.get(0).getText())){

                txt1.setText(options.get(0).getText());
                txt1.setWebViewClient(new WebViewClient(){
                    @Override
                    public void onPageFinished(WebView view, String url) {
                        super.onPageFinished(view, url);
                        btn1.setVisibility(View.VISIBLE);
                        resetAnswerButton();

                    }
                });

                if (options.get(0).getImage()!=null){
                    img_answer1.setVisibility(View.VISIBLE);
//                  Picasso.get().load(options.get(0).getImage()).into(img_answer1);
                    img_answer1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            showImageDailog(options.get(0).getImage());
                        }
                    });


                }else {
                    img_answer1.setVisibility(View.GONE);

                }


           }else {
                    btn1.setVisibility(View.INVISIBLE);
           }


            if (!TextUtils.isEmpty(options.get(1).getText())){
                //  btn1.setText(options.get(0).getText());
                txt2.setText(options.get(1).getText());
                txt2.setWebViewClient(new WebViewClient(){
                    @Override
                    public void onPageFinished(WebView view, String url) {
                        super.onPageFinished(view, url);
                        btn2.setVisibility(View.VISIBLE);
                        resetAnswerButton();

                    }
                });
                if (options.get(1).getImage()!=null){
                    img_answer2.setVisibility(View.VISIBLE);
                    img_answer2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            showImageDailog(options.get(1).getImage());
                        }
                    });
                }else {
                    img_answer2.setVisibility(View.GONE);

                }


            }else {
                btn2.setVisibility(View.INVISIBLE);
            }



            if (!TextUtils.isEmpty(options.get(2).getText())){
                //  btn1.setText(options.get(0).getText());
                txt3.setText(options.get(2).getText());
                txt3.setWebViewClient(new WebViewClient(){
                    @Override
                    public void onPageFinished(WebView view, String url) {
                        super.onPageFinished(view, url);
                        btn3.setVisibility(View.VISIBLE);
                        resetAnswerButton();

                    }
                });
                if (options.get(2).getImage()!=null){
                    img_answer3.setVisibility(View.VISIBLE);
                    img_answer3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            showImageDailog(options.get(2).getImage());
                        }
                    });
                }else {
                    img_answer3.setVisibility(View.GONE);

                }


            }else {
                btn3.setVisibility(View.INVISIBLE);
            }



            if (!TextUtils.isEmpty(options.get(3).getText())){
                //  btn1.setText(options.get(0).getText());
                txt4.setText(options.get(3).getText());
                txt4.setWebViewClient(new WebViewClient(){
                    @Override
                    public void onPageFinished(WebView view, String url) {
                        super.onPageFinished(view, url);
                        btn4.setVisibility(View.VISIBLE);
                        resetAnswerButton();

                    }
                });
                if (options.get(3).getImage()!=null){
                    img_answer4.setVisibility(View.VISIBLE);
                    img_answer4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            showImageDailog(options.get(3).getImage());
                        }
                    });                }else {
                    img_answer4.setVisibility(View.GONE);

                }


            }else {
                btn4.setVisibility(View.INVISIBLE);
            }


            //convert from html to text
            String desc=CurrunQuestion.getText();
            tv_question.setText(Html.fromHtml( desc ).toString().trim());



            if (CurrunQuestion.getImage()!=null){
                //show image and set it
                img_question.setVisibility(View.VISIBLE);
                Picasso.get().load(CurrunQuestion.getImage()).into(img_question);
                img_question.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showImageDailog(CurrunQuestion.getImage());
                    }
                });
            }else {
                img_question.setVisibility(View.GONE);
            }



            //check in there is fraction
            if (CurrunQuestion.getFraction()!=null){
                txt_fraction.setVisibility(View.VISIBLE);
                txt_fraction.setText(CurrunQuestion.getFraction());

            }else {
                txt_fraction.setVisibility(View.GONE);

            }
        }

        if (is_first_time){

            //this is the first time that user enter in this page  so reset this
            tv_question_number.setText(1 + "/20");
            mSeekBar.setProgress(1);

            is_first_time=false;
        }else {

            tv_question_number.setText(questionNumber + "/20");
            mSeekBar.setProgress(questionNumber);

        }



        //reset color of btn answer
        btn_send_answer.setBackgroundTintList(getResources().getColorStateList(R.color.gray_light2));
       btn_send_answer.setText(btn_answer);

        //rehide the button
        btn_hide.setVisibility(View.GONE);


    }
    public void changeStatusBarColor(){

            Window window = getActivity().getWindow();

    // clear FLAG_TRANSLUCENT_STATUS flag:
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

    // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

    // finally change the color
            window.setStatusBarColor(ContextCompat.getColor(getActivity(),R.color.colorPrimary));
    }


    @Override
    public void onMyBackPressed() {
        showAlarmNoteToEndTheQuiz();
         
    }



    protected void createSoundPool() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            createNewSoundPool();
        } else {
            createOldSoundPool();
        }
    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    protected void createNewSoundPool(){
        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        sounds = new SoundPool.Builder()
                .setAudioAttributes(attributes)
                .build();
    }
    @SuppressWarnings("deprecation")
    protected void createOldSoundPool(){
        sounds = new SoundPool(1, AudioManager.STREAM_MUSIC,0);
    }



    public final void cleanUpIfEnd() {
        soundCorrectList = null;
        soundErrorList = null;
        if (sounds!=null)
        sounds.release();
        sounds = null;
    }
    void playSound(int sound) {

        sounds.play(sound, 1, 1, 1, 0, 1f);
    }



    public int getRandNumber(int max , int min){

        return (new Random().nextInt(max - min + 1) + min);
    }


}
