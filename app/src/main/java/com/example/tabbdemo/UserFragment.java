package com.example.tabbdemo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

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

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class UserFragment extends Fragment {
    NewUserDBHelper newUserDBHelper;
    public FloatingActionButton floatingActionButtonOnAddUser;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        newUserDBHelper = new NewUserDBHelper(getContext());
//        if (!newUserDBHelper.addUser("Estifanos","Mathewos","0964018398")){
//            Toast.makeText(getContext(),"One User inserted succesfully", Toast.LENGTH_SHORT).show();
//        }
        // Inflate the layout for this fragment
        View user_fragment_layout = inflater.inflate(R.layout.fragment_user, container, false);


        floatingActionButtonOnAddUser=user_fragment_layout.findViewById(R.id.floatingActionButtonOnAddUser);
        final ListView list = user_fragment_layout.findViewById(R.id.list);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, getAllUsers());
        arrayAdapter.notifyDataSetChanged();
        list.setAdapter(arrayAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ArrayList<String> arrayList2 = new ArrayList<>();
                String clickedItem=(String) list.getItemAtPosition(position);
                Cursor result = newUserDBHelper.getUser(position);
                if (result.getCount()==0){
                    showMessge("Error", "No data found");
                }else {
                    StringBuffer stringBuffer = new StringBuffer();
                    while (result.moveToNext()){
                        stringBuffer.append(result.getString(1)+"\t"+result.getString(2)+"\n");
                        stringBuffer.append(result.getString(3));


                    }
                    showMessge("User:", stringBuffer.toString());
                }

                //showAddUserDialog();
                //Toast.makeText(UsersListActivity.this,clickedItem,Toast.LENGTH_LONG).show();
            }
        });

        setHasOptionsMenu(true);
        floatingActionButtonOnAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),AddUsers.class);
                startActivity(intent);

//                usersSQLiteDBHelper.onCreate(db);
                //SQLiteDatabase db = usersSQLiteDBHelper.getReadableDatabase();
//                if ( usersSQLiteDBHelper.insertData("estifanos","mathewos",964018398)==true){
//                    Toast.makeText(getActivity(),"ad0",Toast.LENGTH_SHORT).show();
//                }
              //  showAddUserDialog();

            }
        });
        return user_fragment_layout;
    }
  @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_users, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == R.id.action_m) {
//            Toast.makeText(getActivity(), "Clicked on " + item.getTitle(), Toast.LENGTH_SHORT)
//                    .show();
//        }
        return true;
    }
    public void adduserbuttonlistener(View v) {
      //  showAddUserDialog();

    }




    ArrayList<String> arrayList = new ArrayList<>();
    public ArrayList<String> getAllUsers(){

        Cursor result = newUserDBHelper.getAllUsers();
        if (result.getCount()==0){
            System.out.println(result.getCount());
            showMessge("Error", "No data found");
        }else {
            while (result.moveToNext()){
                    arrayList.add(result.getString(1)+"\t"+result.getString(2));

            }
             }

        return arrayList;
    }

    public void showMessge(String title, String message){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder.setCancelable(true);
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.show();
    }

}
