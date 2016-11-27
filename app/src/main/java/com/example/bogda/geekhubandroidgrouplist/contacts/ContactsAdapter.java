package com.example.bogda.geekhubandroidgrouplist.contacts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.bogda.geekhubandroidgrouplist.R;
import com.example.bogda.geekhubandroidgrouplist.data.Contact;

import java.util.ArrayList;

/**
 * Created by bogda on 27.11.2016.
 */

public class ContactsAdapter extends BaseAdapter {
    Context context;
    ArrayList<Contact> contacts;
    LayoutInflater lInflater;
    public ContactsAdapter(Context context, ArrayList<Contact> contacts){
        this.context = context;
        this.contacts = contacts;
        lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return contacts.size();
    }

    @Override
    public Object getItem(int position) {
        return contacts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.contact_list_item, parent, false);
        }
        TextView nameTextView = (TextView) view.findViewById(R.id.contacts_name_text_view);
        TextView numberTextView = (TextView) view.findViewById(R.id.contacts_number_text_view);
        nameTextView.setText(contacts.get(position).getName());
        numberTextView.setText(contacts.get(position).getNumber());
        return view;
    }
}
