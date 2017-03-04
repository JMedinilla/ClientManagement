package deint.jvmed.clientmanagement.pojo;

import android.os.Parcel;
import android.os.Parcelable;

public class Meeting implements Parcelable {
    private int id;
    private Client client;
    private String date;
    private String start;
    private String end;
    private String description;

    public Meeting(Client client, String date, String start, String end, String description) {
        this.client = client;
        this.date = date;
        this.start = start;
        this.end = end;
        this.description = description;
    }

    public Meeting(int id, Client client, String date, String start, String end, String description) {
        this.id = id;
        this.client = client;
        this.date = date;
        this.start = start;
        this.end = end;
        this.description = description;
    }

    private Meeting(Parcel in) {
        id = in.readInt();
        client = in.readParcelable(Client.class.getClassLoader());
        date = in.readString();
        start = in.readString();
        end = in.readString();
        description = in.readString();
    }

    public static final Creator<Meeting> CREATOR = new Creator<Meeting>() {
        @Override
        public Meeting createFromParcel(Parcel in) {
            return new Meeting(in);
        }

        @Override
        public Meeting[] newArray(int size) {
            return new Meeting[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeParcelable(client, i);
        parcel.writeString(date);
        parcel.writeString(start);
        parcel.writeString(end);
        parcel.writeString(description);
    }
}
