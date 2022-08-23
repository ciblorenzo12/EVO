package com.evopackage.evo;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

public class Event implements Serializable {
    private String _key, _name, _date, _location, _category, _uri, _description, _creator;

    public Event() {};

    public Event(String key, String name, String date, String location, String category, String creator, String uri, String description) {
        _key = key;
        _name = name;
        _date = date;
        _location = location;
        _category = category;
        _creator = creator;
        _uri = uri;
        _description = description;
    }

    public String GetKey() { return _key; }

    public String GetName() { return _name; }

    public String GetDate() { return _date; }

    public String GetLocation() { return _location; }

    public String GetCategory() { return _category; }

    public String GetCreator() { return _creator; }

    public String GetUri() { return _uri; }

    public String GetDescription() { return _description; }

    // compares 2 string dates
    // greater than > indicates more recent
    // returns 1 if a > b
    // returns -1 if a < b
    // returns 0 if a = b
    public static int compareDates(String a, String b) {
        String[] numsA = a.split("/");
        String[] numsB = b.split("/");

        // if year is more recent
        if (Integer.parseInt(numsA[2]) > Integer.parseInt(numsB[2]))
            return 1;
        else if (Integer.parseInt(numsA[2]) < Integer.parseInt(numsB[2]))
            return -1;
        else {
            // if month is more recent
            if (Integer.parseInt(numsA[0]) > Integer.parseInt(numsB[0]))
                return 1;
            else if (Integer.parseInt(numsA[0]) < Integer.parseInt(numsB[0]))
                return -1;
            else {
                // if day is more recent
                if (Integer.parseInt(numsA[1]) > Integer.parseInt(numsB[1]))
                    return 1;
                else
                    return -1;
            }
        }
    }

    // returns the list of events sort by their date (oldest to newest by default)
    public static ArrayList<Event> sortEventsByDate(ArrayList<Event> a, boolean reversed) {
        int n = a.size();
        for (int i = 0; i < n - 1; i++)
            for (int j = 0; j < n - i - 1; j++)
                if (eventGreater(a.get(j), a.get(j + 1)) ^ reversed) {
                    Event temp = a.get(j);
                    a.set(j, a.get(j + 1));
                    a.set(j + 1, temp);
                }
        return a;
    }

    // checks if event's date is today or in the future
    public static boolean isFutureEvent(Event e) {
        Calendar c = Calendar.getInstance();
        String currentDate = (c.get(Calendar.MONTH) + 1) + "/" + c.get(Calendar.DAY_OF_MONTH) + "/" + c.get(Calendar.YEAR);
        if (compareDates(e.GetDate(), currentDate) > 0)
            return true;
        return false;
    }

    private static boolean eventGreater(Event a, Event b) {
        if (compareDates(a.GetDate(), b.GetDate()) > 0)
            return true;
        else
            return false;
    }

}
