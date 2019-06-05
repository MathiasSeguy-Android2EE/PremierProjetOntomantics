/**
 * <ul>
 * <li>HumanRecyclerAdapter</li>
 * <li>com.android2ee.formation.intra.ontomantics.premierprojet.view</li>
 * <li>20/11/2015</li>
 * <p/>
 * <li>======================================================</li>
 * <p/>
 * <li>Projet : Mathias Seguy Project</li>
 * <li>Produit par MSE.</li>
 * <p/>
 * /**
 * <ul>
 * Android Tutorial, An <strong>Android2EE</strong>'s project.</br>
 * Produced by <strong>Dr. Mathias SEGUY</strong>.</br>
 * Delivered by <strong>http://android2ee.com/</strong></br>
 * Belongs to <strong>Mathias Seguy</strong></br>
 * ***************************************************************************************************************</br>
 * This code is free for any usage but can't be distribute.</br>
 * The distribution is reserved to the site <strong>http://android2ee.com</strong>.</br>
 * The intelectual property belongs to <strong>Mathias Seguy</strong>.</br>
 * <em>http://mathias-seguy.developpez.com/</em></br> </br>
 * <p/>
 * *****************************************************************************************************************</br>
 * Ce code est libre de toute utilisation mais n'est pas distribuable.</br>
 * Sa distribution est reservée au site <strong>http://android2ee.com</strong>.</br>
 * Sa propriété intellectuelle appartient à <strong>Mathias Seguy</strong>.</br>
 * <em>http://mathias-seguy.developpez.com/</em></br> </br>
 * *****************************************************************************************************************</br>
 */

package com.android2ee.formation.intra.ontomantics.premierprojet.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android2ee.formation.intra.ontomantics.premierprojet.R;
import com.android2ee.formation.intra.ontomantics.premierprojet.entity.Human;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mathias Seguy - Android2EE on 20/11/2015.
 */
public class HumanRecyclerAdapter extends RecyclerView.Adapter<HumanRecyclerAdapter.MyViewHolder> {
    /**
     * Constant for managing odd and even line based on stupidity
     */
    public static final int PollOfStupidView = 0;
    public static final int PoolOfSmartView = 1;
    /***********************************************************
     *  Attributes
     **********************************************************/
    /**
     * The dataset
     */
    private List<Human> humans=null;
    /**
     * The current business object
     */
    private Human humanTemp;
    /**
     * The current view
     */
    private View rowView;

    /**
     * The viewHolder
     */
    private MyViewHolder viewHolderTemp;
    /**
     * The layoutInflater
     */
    private LayoutInflater inflater;
    /**
     * CallBack to prevent the activity a click as been done on an item
     */
    private HumanRecyclerAdapterCallback activityCallback;

    /***********************************************************
     *  Constructors
     **********************************************************/
    /**
     * The constructor
     * @param dataSet the dataset
     * @param ctx the context
     */
    public HumanRecyclerAdapter(ArrayList<Human> dataSet,Context ctx,HumanRecyclerAdapterCallback callback){
        humans=dataSet;
        inflater=LayoutInflater.from(ctx);
        this.activityCallback= callback;

    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==PoolOfSmartView){
            rowView=inflater.inflate(R.layout.human_item_even,parent,false);
        }else{
            rowView=inflater.inflate(R.layout.human_item_odd,parent,false);
        }
        viewHolderTemp=new MyViewHolder(rowView,activityCallback);
        return viewHolderTemp;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        humanTemp=humans.get(position);
        holder.getTxvMessage().setText(humanTemp.getMessage());
        holder.getTxvName().setText(humanTemp.getName());
        holder.getImvPicture().setImageResource(humanTemp.getPicture());

    }

    @Override
    public int getItemCount() {
        return humans.size();
    }

    @Override
    public int getItemViewType(int position) {
        humanTemp = humans.get(position);
        if(humanTemp.isStupid()){
            return PollOfStupidView;
        }else{
            return PoolOfSmartView;
        }
    }
    //    public Human getItemAt(int position){
//        return humans.get(position);
//    }
//    public void clear(){
//        humans.clear();
//        notifyDataSetChanged();
//    }
//
//    public void add(Human human){
//        humans.add(0,human);
//        notifyItemInserted(0);
//    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView imvPicture;
        TextView txvName;
        TextView txvMessage;
        LinearLayout lilRoot;
        HumanRecyclerAdapterCallback callback;
        public MyViewHolder(View itemView,HumanRecyclerAdapterCallback callback) {
            super(itemView);
            imvPicture= (ImageView) itemView.findViewById(R.id.imvPicture);
            txvName= (TextView) itemView.findViewById(R.id.txvName);
            txvMessage= (TextView) itemView.findViewById(R.id.txvMessage);
            lilRoot= (LinearLayout) itemView.findViewById(R.id.lilRoot);
            this.callback=callback;
            //add listener
            lilRoot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemSelected();
                }
            });
        }

        public ImageView getImvPicture() {
            return imvPicture;
        }

        public TextView getTxvMessage() {
            return txvMessage;
        }

        public TextView getTxvName() {
            return txvName;
        }

        public void itemSelected(){
            callback.itemClicked(getAdapterPosition());
        }
    }
}
