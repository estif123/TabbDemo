package com.example.tabbdemo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class MessaegFragment extends Fragment {
    NewMessageModel newMessageModel;
    NewMessageHelper newMessageHelper;
    PagerAdapter pagerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        newMessageHelper = new NewMessageHelper(getContext());
//        if (!newMessageHelper.insertMessage("this is message","the temparature is increasing")){
//            Toast.makeText(getContext(),"data inserted succesfully", Toast.LENGTH_SHORT).show();
//        }
        // Inflate the layout for this fragment
        View message_fragment_layout = inflater.inflate(R.layout.fragment_messaeg, container, false);


        final ListView list = message_fragment_layout.findViewById(R.id.message_list);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, newMessageHelper.getAllMessages());
        list.setAdapter(arrayAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ArrayList<String> arrayList2 = new ArrayList<>();
                String clickedItem=(String) list.getItemAtPosition(position);
                Cursor result = newMessageHelper.getMessage(position);
                if (result.equals(null)){
                    showMessge("Error", "No Message found", position);
                }else {
                    StringBuffer stringBuffertitle = new StringBuffer();
                    StringBuffer stringBuffercontent = new StringBuffer();
                    while (result.moveToNext()){
                        stringBuffertitle.append(result.getString(1));
                        stringBuffercontent.append("Content\t"+result.getString(2)+"\n");
                    }
                    showMessge(stringBuffertitle.toString(), stringBuffercontent.toString(),position);
                }

            }
        });

        setHasOptionsMenu(true);

        return message_fragment_layout;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_users, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       if (item.getItemId() == R.id.action_chat) {
           Toast.makeText(getActivity(), "Clicked on " + item.getTitle(), Toast.LENGTH_SHORT)
                  .show();
        }
        return true;
    }
//ArrayList als = new ArrayList<>();

    ArrayList<NewMessageModel> arrayList = new ArrayList<>();
    public List<String> getAllMessages(){
    ArrayList ls = newMessageHelper.getAllMessages();

        return ls;

    }

    public void showMessge(final String title, String message, final int position){
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder.setCancelable(true);
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setNegativeButton("cancell", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                      switch (which){
                          case DialogInterface.BUTTON_NEGATIVE:
                              break;
                          case DialogInterface.BUTTON_POSITIVE:

                              int result = newMessageHelper.deleteMessage(title);
                              if (result==1){
                                  Snackbar.make(getView(),"deleted succesfully",Snackbar.LENGTH_SHORT).show();
                              }



                      }
                    }
                });
        alertDialogBuilder.show();

}

    }

