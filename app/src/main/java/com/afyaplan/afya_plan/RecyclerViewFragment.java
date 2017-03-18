package com.afyaplan.afya_plan;

import com.dpizarro.autolabel.library.AutoLabelUI;
import com.dpizarro.autolabel.library.Label;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RecyclerViewFragment extends Fragment {

    private final String KEY_INSTANCE_STATE_PEOPLE = "statePeople";

    private AutoLabelUI mAutoLabel;
    private List<Person> mPersonList;
    private MyRecyclerAdapter adapter;
    private RecyclerView recyclerView;

    public static RecyclerViewFragment newInstance() {
        return new RecyclerViewFragment();
    }

    public RecyclerViewFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recyclerview_fragment, container, false);
        findViews(view);
        setListeners();
        setRecyclerView();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            List<Person> people = savedInstanceState.getParcelableArrayList(KEY_INSTANCE_STATE_PEOPLE);
            if (people != null) {
                mPersonList = people;
                adapter.setPeople(people);
                recyclerView.setAdapter(adapter);
            }
        }
    }

    private void itemListClicked(int position) {
        Person person = mPersonList.get(position);
        boolean isSelected = person.isSelected();
        boolean success;
        if (isSelected) {
            success = mAutoLabel.removeLabel(position);
        } else {
            success = mAutoLabel.addLabel(person.getName(), position);
        }
        if (success) {
            adapter.setItemSelected(position, !isSelected);
        }
    }

    private void setListeners() {
        mAutoLabel.setOnLabelsCompletedListener(new AutoLabelUI.OnLabelsCompletedListener() {
            @Override
            public void onLabelsCompleted() {
                Snackbar.make(recyclerView, "Completed!", Snackbar.LENGTH_SHORT).show();
            }
        });

        mAutoLabel.setOnRemoveLabelListener(new AutoLabelUI.OnRemoveLabelListener() {
            @Override
            public void onRemoveLabel(Label removedLabel, int position) {
                adapter.setItemSelected(position, false);
            }

        });

        mAutoLabel.setOnLabelsEmptyListener(new AutoLabelUI.OnLabelsEmptyListener() {
            @Override
            public void onLabelsEmpty() {
                Snackbar.make(recyclerView, "EMPTY!", Snackbar.LENGTH_SHORT).show();
            }
        });

        mAutoLabel.setOnLabelClickListener(new AutoLabelUI.OnLabelClickListener() {
            @Override
            public void onClickLabel(Label labelClicked) {
                Snackbar.make(recyclerView, labelClicked.getText(), Snackbar.LENGTH_SHORT).show();
            }
        });

    }

    private void findViews(View view) {

        mAutoLabel = (AutoLabelUI) view.findViewById(R.id.label_view);
        mAutoLabel.setBackgroundResource(R.drawable.round_corner_background);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
    }

    private void setRecyclerView() {
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(llm);

        mPersonList = new ArrayList<>();

        //Populate list
        getAllContacts();
        //List<Integer> agesList = new ArrayList<Integer>(Arrays.asList(ages));
        TypedArray photos = getResources().obtainTypedArray(R.array.photos);

        photos.recycle();

        adapter = new MyRecyclerAdapter(mPersonList);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new MyRecyclerAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View v, int position) {
                itemListClicked(position);
            }
        });
    }

    private void getAllContacts() {
        Person person;

        ContentResolver contentResolver = getActivity().getContentResolver();
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");

        if( cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int hasPhoneNumber = Integer.parseInt(cursor.getString(
                        cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.HAS_PHONE_NUMBER)));

                //check if user has a phone number
                if(hasPhoneNumber > 0) {
                    String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                    String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    String phoneNumber = "12345678";

                    Cursor phoneCursor = contentResolver.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[] {id},
                            null);
                    if (phoneCursor.moveToNext()) {
                        phoneNumber = phoneCursor.getString(
                                phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    }

                    phoneCursor.close();
                    person = new Person(name, phoneNumber, getPhoto(id), false);

                    mPersonList.add(person);
                }
            }
        }
    }

    //get contact photo of particular person

    private Bitmap getPhoto(String id) {
        Bitmap photo = null;

        try {

            InputStream input =
                    ContactsContract.Contacts.openContactPhotoInputStream (
                            getActivity().getContentResolver(),
                            ContentUris.withAppendedId(
                                    ContactsContract.Contacts.CONTENT_URI,
                                    new Long(id).longValue()));

            if (input != null) {
                photo = BitmapFactory.decodeStream(input);
                input.close();
            }
            else {
                photo = BitmapFactory.decodeResource(getActivity().getResources(),
                        R.drawable.account_box);
            }



        }

        catch (IOException iox) {
            iox.printStackTrace();
        }

        return photo;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(KEY_INSTANCE_STATE_PEOPLE,
                (ArrayList<? extends Parcelable>) adapter.getPeople());

    }
}
