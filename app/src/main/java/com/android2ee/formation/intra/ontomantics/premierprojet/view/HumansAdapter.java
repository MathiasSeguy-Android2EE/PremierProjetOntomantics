/**
 * <ul>
 * <li>HumansAdapter</li>
 * <li>com.android2ee.formation.intra.ontomantics.premierprojet.view</li>
 * <li>16/11/2015</li>
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android2ee.formation.intra.ontomantics.premierprojet.R;
import com.android2ee.formation.intra.ontomantics.premierprojet.entity.Human;

import java.util.ArrayList;

/**
 * Created by Mathias Seguy - Android2EE on 16/11/2015.
 */
public class HumansAdapter extends ArrayAdapter<Human> {
    public static final int PollOfStupidView = 0;
    public static final int PoolOfSmartView = 1;
    /***********************************************************
     *  Attributes
     **********************************************************/
    /**
     * The current business object
     */
    private Human humanTemp;
    /**
     * The current view
     */
    private View rowView;
    /**
     * The layoutInflater
     */
    private LayoutInflater inflater;
    /**
     * The ViewHolder associated to the current view
     */
    private ViewHolder viewHolder;

    /***********************************************************
     *  Constructors
     **********************************************************/

    /**
     * Constructor
     * @param context the context
     * @param dataset the dataset to display
     */
    public HumansAdapter(Context context, ArrayList<Human> dataset) {
        super(context, R.layout.human_item_even,dataset);
        //others stuff
        inflater=LayoutInflater.from(context);
    }

    /***********************************************************
     *  GetView
     **********************************************************/
    /**
     * {@inheritDoc}
     *
     * @param position
     * @param convertView
     * @param parent
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        humanTemp=getItem(position);
        //DO the magic stuff
        rowView=convertView;
        //my creation bloc
        if(rowView==null){
            if(getItemViewType(position)==PollOfStupidView){
                rowView=inflater.inflate(R.layout.human_item_even,parent,false);
            }else{
                rowView=inflater.inflate(R.layout.human_item_odd,parent,false);
            }
            viewHolder=new ViewHolder(rowView);
            rowView.setTag(viewHolder);
        }
        //my bloc of update of the view
        //rowView!=null && rowView.getTag==ViewHolder!=null
        viewHolder= (ViewHolder) rowView.getTag();
        return rowView;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        humanTemp=getItem(position);
        if(humanTemp.isStupid()){
            return PollOfStupidView;
        }else{
            return PoolOfSmartView;
        }
    }

    /***********************************************************
     *  ViewHolder Pattern
     **********************************************************/
    private class ViewHolder{
    View view;
    ImageView imvPicture;
    TextView txvName;
    TextView txvMessage;

    public ViewHolder(View view) {
        this.view = view;
        imvPicture= (ImageView) view.findViewById(R.id.imvPicture);
        txvName= (TextView) view.findViewById(R.id.txvName);
        txvMessage= (TextView) view.findViewById(R.id.txvMessage);
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
}
}
