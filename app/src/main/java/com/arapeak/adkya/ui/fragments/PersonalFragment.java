package com.arapeak.adkya.ui.fragments;

import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.arapeak.adkya.R;
import com.arapeak.adkya.adapters.MyPagerAdapter;
import com.arapeak.adkya.api.APIService;
import com.arapeak.adkya.api.ServiceGenerator;
import com.arapeak.adkya.model.getStatistics.ResultGetStatistics;
import com.arapeak.adkya.ui.activites.HomeActivity;
import com.arapeak.adkya.utils.Common;
import com.google.android.material.tabs.TabLayout;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;


public class PersonalFragment extends Fragment {
    TextView tv_time,tv_rate;
    SeekBar seekBar;

    private static final String TAG = "PersonalFragment";
    public PersonalFragment() {
        // Required empty public constructor
    }
    private static PersonalFragment Instance;

    public static PersonalFragment getInstance(){
        if (Instance==null) {
            Instance= new PersonalFragment();
        }
        return Instance;
    }


    @Override
    public void onResume() {
        super.onResume();
        //this is for handel back press button inside fragment
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    // handle back button's click listener

                    Fragment fragment= HomeFragment.getInstance();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.contianer_frame, fragment).commit();


                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        // Inflate the layout for this fragment
          View view =  inflater.inflate(R.layout.fragment_personal, container, false);
        CircularImageView circularImageView = view.findViewById(R.id.circularImageView);
        TextView tv_name = view.findViewById(R.id.tv_name);
        TextView tv_classroom = view.findViewById(R.id.tv_classroom);
          tv_time = view.findViewById(R.id.tv_time);
          tv_rate = view.findViewById(R.id.tv_rate);
          seekBar= view.findViewById(R.id.seekbar);
        seekBar.setPadding(0, 0, 0, 0);


        Picasso.get().load(Common.retrieveUserDataPreferance(getActivity()).getImage()).into(circularImageView);
        tv_classroom.setText(getClassRoomNameById(Common.retrieveUserDataPreferance(getActivity()).getClassId() ));
        tv_name.setText(Common.retrieveUserDataPreferance(getActivity()).getName());



        TabLayout mTabLayout = view.findViewById(R.id.tabLayout);
        ViewPager mViewPager =view.findViewById(R.id.viewpager);
        MyPagerAdapter  adapter= new MyPagerAdapter(getChildFragmentManager(),getInstance().getContext());
        // i use getChildFragmentManager not getsupportfragment manager because getsupportfragment lost data when i swipe between fragment
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);



        ImageView menubar= view.findViewById(R.id.menubar);

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


        SendGetSatisticRequest();
        changeStatusBarColor();

        return view;

    }

    public void changeStatusBarColor(){

        Window window = getActivity().getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(getActivity(),R.color.statusbarColorPersonFragment));
    }

    private void SendGetSatisticRequest() {


        APIService apiService= ServiceGenerator.createService(APIService.class,Common.retrieveUserDataPreferance(getActivity()).getRememberToken());
        Call<ResultGetStatistics> call =  apiService.getStatistics();
        call.enqueue(new Callback<ResultGetStatistics>() {
            @Override
            public void onResponse(Call<ResultGetStatistics> call, Response<ResultGetStatistics> response) {

                if (response.isSuccessful()){
//                    if (! response.body().getData().getGeneralStudentRate() .contains("%")){
////                        tv_rate.setText(   response.body().getData().getGeneralStudentRate()+"%" );
////
////                    }else {
////                        tv_rate.setText(   response.body().getData().getGeneralStudentRate() );
////
////                    }
                    tv_rate.setText(response.body().getData().getTotalAverage().intValue()+"%");
                    tv_time.setText(response.body().getData().getSittingInteractingRate());
                    seekBar.setProgress(response.body().getData().getTotalAverage().intValue());


                }else {

                        Toast.makeText(getActivity(),  "خطأ في الاتصال", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ResultGetStatistics> call, Throwable t) {
                Toast.makeText(getActivity(), "خطأ في الاتصال"  , Toast.LENGTH_SHORT).show();


            }
        });
    }


    private String getClassRoomNameById(Integer classid) {
        String s = "";
        //get selected id
        if (classid != null) {
            switch (classid) {
                case 1:
                    s = getString(R.string.class1);
                    break;

                case 2:
                    s = getString(R.string.class2);
                    break;

                case 3:
                    s = getString(R.string.class3);

                    break;

                case 4:
                    s = getString(R.string.class4);

                    break;

                case 5:
                    s = getString(R.string.class5);

                    break;

                case 6:
                    s = getString(R.string.class6);

                    break;
            }

        }
        return s;
    }

}
