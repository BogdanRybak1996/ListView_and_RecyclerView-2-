package com.example.bogda.geekhubandroidgrouplist.contacts;


import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.bogda.geekhubandroidgrouplist.R;
import com.example.bogda.geekhubandroidgrouplist.data.Contact;

import java.util.ArrayList;
import java.util.Collections;
import java.util.jar.Manifest;

import static android.app.Activity.RESULT_OK;

/**
 * Created by bogda on 27.11.2016.
 */

public class ContactsFragment extends Fragment {
    ListView contactsListView = null;
    Button addButton = null;
    public static final int PERMITION_READ_CONTACT_CODE = 1;
    public static final int PERMITION_WRITE_CONTACT_CODE = 2;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_contacts, container, false);
        contactsListView = (ListView) rootView.findViewById(R.id.contacts_list_view);
        addButton = (Button) rootView.findViewById(R.id.contacts_add_button);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        getContactsList();
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.WRITE_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(getActivity(), AddDialog.class);
                    startActivityForResult(intent, 1);
                }

                else{
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),android.Manifest.permission.WRITE_CONTACTS)){
                        Toast.makeText(getContext(),"Check permition in settings",Toast.LENGTH_LONG).show();
                    }
                    requestPermissions(new String[]{android.Manifest.permission.WRITE_CONTACTS}, PERMITION_WRITE_CONTACT_CODE);
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            addContact(data.getStringExtra("name"), data.getStringExtra("phone"));
        }
    }

    private void addContact(String name, String number) {
        ContentValues cv = new ContentValues();
        cv.put(Contacts.People.NAME, name);
        Uri u = getActivity().getContentResolver().insert(Contacts.People.CONTENT_URI, cv);
        Uri pathu = Uri.withAppendedPath(u, Contacts.People.Phones.CONTENT_DIRECTORY);
        cv.clear();
        cv.put(Contacts.People.NUMBER, number);
        getActivity().getContentResolver().insert(pathu, cv);


        //update list
        ArrayList<Contact> contacts = new ArrayList<Contact>();
        Cursor phones = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        while (phones.moveToNext()) {
            String contactName = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            contacts.add(new Contact(contactName, phoneNumber));
        }
        Collections.sort(contacts);
        contactsListView.setAdapter(new ContactsAdapter(getContext(), contacts));
    }

    private void getContactsList() {
        if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            ArrayList<Contact> contacts = new ArrayList<Contact>();
            Cursor phones = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
            while (phones.moveToNext()) {
                String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                contacts.add(new Contact(name, phoneNumber));
            }
            Collections.sort(contacts);
            contactsListView.setAdapter(new ContactsAdapter(getContext(), contacts));
        } else {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),android.Manifest.permission.READ_CONTACTS)){
                Toast.makeText(getContext(),"Check permition in settings",Toast.LENGTH_LONG).show();
            }
            requestPermissions(new String[]{android.Manifest.permission.READ_CONTACTS}, PERMITION_READ_CONTACT_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case PERMITION_READ_CONTACT_CODE:
                if(grantResults.length==1) {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        getContactsList();
                    }
                }
                break;
            case PERMITION_WRITE_CONTACT_CODE:
                if(grantResults.length==1){
                    if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                        Intent intent = new Intent(getActivity(), AddDialog.class);
                        startActivityForResult(intent, 1);
                    }
                }
        }
    }
}
