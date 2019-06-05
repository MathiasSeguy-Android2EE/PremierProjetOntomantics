/**
 * <ul>
 * <li>MyFragment</li>
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

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import androidx.annotation.Nullable;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android2ee.formation.intra.ontomantics.premierprojet.R;
import com.android2ee.formation.intra.ontomantics.premierprojet.entity.Human;

import java.util.ArrayList;

/**
 * Created by Mathias Seguy - Android2EE on 20/11/2015.
 */
public class MyFragment extends Fragment implements HumanRecyclerAdapterCallback {
    public static final String MESSAGES = "messages";
    public static final String MY_DIALOG_FRAGMENT_TAG = "myDialog";
    /***********************************************************
     * Attributes
     **********************************************************/
    /**
     * The button to add the message in the edittext to the view Result
     */
    Button btnAdd;
    /**
     * The EditText where the user writes it message
     */
    EditText edtMessage;
    /**
     * The view that displays the result
     */
    RecyclerView rcvResult;
    /**
     * The list of message displayed by the listViex
     */
    ArrayList<Human> messages;
    /**
     * The ArrayAdapter that manage the dataset displayed by the listview
     */
    HumanRecyclerAdapter arrayAdapter;
    /**
     * The root layout of the view
     */
    LinearLayout rootLayout;
    /**
     * The AlertDialog's unique id
     */
    private static int ALERT_DIALOG_ID=110274;
    /**
     * The position of the selectedItem
     */
    private int selectedItemPosition=0;
    /**
     * To know if postICS
     */
    private boolean isPostICS=false;
    /**
     * Animpation Gingerbread
     */
    Animation animGinger;
    /**
     * Animation post ICS
     */
    AnimatorSet animatorICS;
    /**
     * The alertDialog
     */
    MyDialog myDialog=null;
    /***********************************************************
     * Constructor
     **********************************************************/
    public MyFragment() {
    }

    /***********************************************************
     * Managing life cycle
     **********************************************************/


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_layout,container,false);
        //know the version
        isPostICS=getResources().getBoolean(R.bool.postICS);
        //instanciate my graphical components
        btnAdd= (Button) view.findViewById(R.id.btnAdd);
        edtMessage= (EditText) view.findViewById(R.id.edtMessage);
        rcvResult = (RecyclerView) view.findViewById(R.id.lsvResult);
        rootLayout= (LinearLayout) view.findViewById(R.id.lilRoot);
        //instanciate the needed element for the listView
        messages=new ArrayList<Human>();
        arrayAdapter=new HumanRecyclerAdapter(messages,getActivity(),this);
        rcvResult.setLayoutManager(new LinearLayoutManager(getActivity()));
        rcvResult.setAdapter(arrayAdapter);
        //add the listeners
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem();
            }
        });

        //instanciate my animation
        if(isPostICS){
            //load ObjectAnimator
            animatorICS= (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(), R.animator.rotate_y);
            animatorICS.setTarget(btnAdd);
        }else{
            //load tween animation
            animGinger= AnimationUtils.loadAnimation(getActivity(), R.anim.bump);
        }
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState!=null){
            messages.clear();
//        messages.addAll(savedInstanceState.<Human>getParcelableArrayList(MESSAGES));
            for(Parcelable parcel:savedInstanceState.getParcelableArrayList(MESSAGES)){
                messages.add((Human) parcel);
            }
            arrayAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        //Manage the fucking FragmentDialog inner class memory leak
        // why AlertDialog are deprecated, tell me why ??!?!?
        FragmentManager fm = getFragmentManager();
        myDialog=(MyDialog)fm.findFragmentByTag(MY_DIALOG_FRAGMENT_TAG);
        if(myDialog!=null){
            myDialog.dismiss();
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(MESSAGES, messages);
        super.onSaveInstanceState(outState);
    }
    /***********************************************************
     *  Managing Menu
     **********************************************************/
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_menu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.menu_item_flush:
                //do your stuff
                flushTheList();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    /***********************************************************
     *  Business method
     **********************************************************/
    /**
     * Temp var for copying the edittext content
     */
    String messageTemp;
    /**
     * Copy the content of the edittext within the Result view
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void addItem(){
        if(isPostICS){
            animatorICS.start();
        }else{
            btnAdd.startAnimation(animGinger);
        }
        //copy the edittext's content
        messageTemp=edtMessage.getText().toString();
        //paste in the result view
        messages.add(0,new Human(messageTemp,arrayAdapter.getItemCount()));
        arrayAdapter.notifyItemInserted(0);
        //flush edit edittext
        edtMessage.setText("");
    }

    /**
     * Copy the item at the position gave as parameter into the edittext
     * @param position
     */
    public void itemClicked(int position){
        selectedItemPosition=position;
//        showDialog(ALERT_DIALOG_ID);

        FragmentManager fm = getFragmentManager();
        myDialog=(MyDialog)fm.findFragmentByTag(MY_DIALOG_FRAGMENT_TAG);
        if(myDialog==null){
            myDialog=new MyDialog();
        }
        myDialog.show(getFragmentManager(), MY_DIALOG_FRAGMENT_TAG);


    }
    /**
     * The action to do when the user click on ok on the alertDialog:
     * Copy paste the content of the selectediteml in the edit text
     */
    private void alertOk(){
        //find the value
        messageTemp=messages.get(selectedItemPosition).getMessage();
        //paste it in the edittext
        edtMessage.setText(messageTemp);
        //The toast
        Toast.makeText(getActivity(), getString(R.string.toast_message), Toast.LENGTH_LONG).show();
        Snackbar.make(rootLayout,
                getString(R.string.snack_message),
                Snackbar.LENGTH_LONG)
                .setAction(getString(R.string.snack_action),
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                undoPaste();
                            }
                        })
                .show();

    }

    /**
     * Undo the paste
     */
    private void undoPaste(){
        edtMessage.setText("");
    }

    /**
     * Flush the list
     */
    private void flushTheList(){

        messages.clear();
        arrayAdapter.notifyDataSetChanged();
    }


    /***********************************************************
     *  Managing Alert Dialog
     **********************************************************/
    public class MyDialog extends DialogFragment {
        public MyDialog() {
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            return  buildDialog();
        }

        /**
         * Create the alert dialog
         * @return AlertDialog
         */
        private AlertDialog buildDialog(){
            AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
            builder.setMessage("pipo");
            builder.setPositiveButton(getString(R.string.alertdialog_positive),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            alertOk();
                        }
                    });
            builder.setNegativeButton(getString(R.string.alertdialog_negative), null);
            return builder.create();
        }

        @Override
        public void onResume() {
            super.onResume();
            ((androidx.appcompat.app.AlertDialog)getDialog()).setMessage(messages.get(selectedItemPosition).getMessage());
        }
    }


}
