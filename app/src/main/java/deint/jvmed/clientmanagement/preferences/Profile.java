package deint.jvmed.clientmanagement.preferences;

import android.content.Context;
import android.content.SharedPreferences;

public class Profile {
    private static final int MODE = Context.MODE_PRIVATE;
    private static final String FILE = "deint.jvmed.clientmanagement_preferences";

    private static final String COMPANY_NAME = "pref_company_name";
    private static final String COMPANY_CIF = "pref_company_cif";
    private static final String COMPANY_LOCATION = "pref_company_location";
    private static final String COMPANY_PHONE = "pref_company_phone";
    private static final String COMPANY_EMAIL = "pref_company_email";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public Profile(Context context) {
        sharedPreferences = context.getSharedPreferences(FILE, MODE);
        editor = sharedPreferences.edit();
    }

    public String getName() {
        return sharedPreferences.getString(COMPANY_NAME, "");
    }

    public String getCif() {
        return sharedPreferences.getString(COMPANY_CIF, "");
    }

    public String getLocation() {
        return sharedPreferences.getString(COMPANY_LOCATION, "");
    }

    public String getPhone() {
        return sharedPreferences.getString(COMPANY_PHONE, "");
    }

    public String getEmail() {
        return sharedPreferences.getString(COMPANY_EMAIL, "");
    }

    public void setName(String s) {
        editor.putString(COMPANY_NAME, s).commit();
    }

    public void setCif(String s) {
        editor.putString(COMPANY_CIF, s).commit();
    }

    public void setLocation(String s) {
        editor.putString(COMPANY_LOCATION, s).commit();
    }

    public void setPhone(String s) {
        editor.putString(COMPANY_PHONE, s).commit();
    }

    public void setEmail(String s) {
        editor.putString(COMPANY_EMAIL, s).commit();
    }
}
