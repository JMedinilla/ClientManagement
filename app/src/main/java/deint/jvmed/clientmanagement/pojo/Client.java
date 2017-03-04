package deint.jvmed.clientmanagement.pojo;

import android.os.Parcel;
import android.os.Parcelable;

public class Client implements Parcelable {
    private int id;
    private String photo;
    private String name;
    private String surname;
    private String location;
    private String number;

    public Client(String photo, String name, String surname, String location, String number) {
        this.photo = photo;
        this.name = name;
        this.surname = surname;
        this.location = location;
        this.number = number;
    }

    public Client(int id, String photo, String name, String surname, String location, String number) {
        this.id = id;
        this.photo = photo;
        this.name = name;
        this.surname = surname;
        this.location = location;
        this.number = number;
    }

    private Client(Parcel in) {
        id = in.readInt();
        photo = in.readString();
        name = in.readString();
        surname = in.readString();
        location = in.readString();
        number = in.readString();
    }

    public static final Creator<Client> CREATOR = new Creator<Client>() {
        @Override
        public Client createFromParcel(Parcel in) {
            return new Client(in);
        }

        @Override
        public Client[] newArray(int size) {
            return new Client[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(photo);
        parcel.writeString(name);
        parcel.writeString(surname);
        parcel.writeString(location);
        parcel.writeString(number);
    }
}
