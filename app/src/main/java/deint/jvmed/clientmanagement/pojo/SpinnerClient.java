package deint.jvmed.clientmanagement.pojo;

import android.os.Parcel;
import android.os.Parcelable;

public class SpinnerClient implements Parcelable {
    private int id;
    private String name;
    private String surname;

    public SpinnerClient(int id, String name, String surname) {
        this.id = id;
        this.name = name;
        this.surname = surname;
    }

    private SpinnerClient(Parcel in) {
        id = in.readInt();
        name = in.readString();
        surname = in.readString();
    }

    public static final Creator<SpinnerClient> CREATOR = new Creator<SpinnerClient>() {
        @Override
        public SpinnerClient createFromParcel(Parcel in) {
            return new SpinnerClient(in);
        }

        @Override
        public SpinnerClient[] newArray(int size) {
            return new SpinnerClient[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(surname);
    }
}
