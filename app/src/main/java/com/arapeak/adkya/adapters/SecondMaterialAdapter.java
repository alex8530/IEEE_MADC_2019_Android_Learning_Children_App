package com.arapeak.adkya.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.arapeak.adkya.R;
import com.arapeak.adkya.listener.MyItemListener_nested;
import com.arapeak.adkya.model.getSecondMaterial.SecondMaterialData;
import com.arapeak.adkya.model.getSecondMaterial.Section;
import com.arapeak.adkya.ui.activites.LoginActivity;
import com.arapeak.adkya.ui.fragments.ChalengeFragment;
import com.arapeak.adkya.utils.Common;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SecondMaterialAdapter extends RecyclerView.Adapter<SecondMaterialAdapter.SecondMaterialViewHolder> implements MyItemListener_nested  {

    AlertDialog dialog;
    AlertDialog.Builder mBuilder;
    ArrayList<SecondMaterialData> mListMaterial=  new ArrayList<>();
    private Context mContext;


    public SecondMaterialAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public ArrayList<SecondMaterialData> getmListMaterial() {
        return mListMaterial;
    }

    public void setmListMaterial(ArrayList<SecondMaterialData> mListMaterial) {
        this.mListMaterial = mListMaterial;
    }


    @NonNull
    @Override
    public SecondMaterialViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vertical_material_item_rec_layout, parent, false);
        return new SecondMaterialViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SecondMaterialViewHolder holder, int position) {


         if (mListMaterial.get(position).getSections().size()!=0){

             //check if come from english section or not !!
             if (Common.CurrentMaterial.getId()==4){   //4>>>english
                 //BY DEFULT IS RTL,,, SO JUST IF ENGLISH OR NOT
                 holder.linearLayoutHorizenteal.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);

             }


             holder.tv_title.setText(mListMaterial.get(position).getName());

             Picasso.get().load( mListMaterial.get(position).getImage() ).placeholder(R.drawable.defult_image_second).into(holder.img_title);


             SectionMaterialAdapter   adapterSection = new SectionMaterialAdapter(mContext );
             adapterSection.setMyItemListenerNested(this);

             holder.recyle_inner_horizental_material.setHasFixedSize(true);
             holder.recyle_inner_horizental_material.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));



             //to save position of parent
             ArrayList<Section> list =   mListMaterial.get(position).getSections();
             for (Section section : list){
                 section.setParent_position(position);
             }

             adapterSection.setmList(list);
             holder.recyle_inner_horizental_material.setAdapter(adapterSection);


             //STORE LIST  TO KEEP TRACE LATER
             Common.CURRENT_List_MATERIAL_DATA=mListMaterial;//this is two dimitional array
         }else {
             //no sections for this material
             holder.tv_title.setVisibility(View.GONE);
             holder.recyle_inner_horizental_material.setVisibility(View.GONE);

         }



    }
     @Override
    public int getItemCount() {
        return(null != mListMaterial ? mListMaterial.size() : 0);
    }



    public class SecondMaterialViewHolder extends RecyclerView.ViewHolder {

        TextView tv_title;
        ImageView img_title;
        RecyclerView recyle_inner_horizental_material;
        LinearLayout linearLayoutHorizenteal;

        public SecondMaterialViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title=itemView.findViewById(R.id.tv_title);
            linearLayoutHorizenteal=itemView.findViewById(R.id.LinearLayoutHorizenteal);
            recyle_inner_horizental_material=itemView.findViewById(R.id.recyle_inner_horizental_material);
            img_title=itemView.findViewById(R.id.img_title);

        }
    }
    @Override
    public void onClickItemNested(Section section , boolean is_visitor) {

        if (is_visitor ==false){
            //save ids
            Common.CurrentSection=section;
            Common.CurrentSecondMaterial= mListMaterial.get(section.getParent_position());

             Fragment fragment= ChalengeFragment.getInstance();
          // Fragment fragment= new ChalengeFragment();

            FragmentManager fragmentManager =((FragmentActivity)mContext).getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.contianer_frame, fragment).commit();
        }else {
            //this is visitor
            showReqiuredRegisterVisitorDailog();
        }
      
    }

    private void showReqiuredRegisterVisitorDailog() {

            mBuilder = new AlertDialog.Builder(mContext );
            LayoutInflater li = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View mView = li.inflate(R.layout.dialog_require_register_visitor, null);


            mBuilder.setView(mView);
             dialog = mBuilder.create();
            dialog.show();

            final Button btn_ok =  mView.findViewById(R.id.btn_ok);

            btn_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    mContext.startActivity(new Intent(mContext, LoginActivity.class));


                }
            });

    }
}
