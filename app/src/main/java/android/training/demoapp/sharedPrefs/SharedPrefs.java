package android.training.demoapp.sharedPrefs;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefs {

    private static SharedPreferences.Editor editor;
    private static SharedPreferences preferences;

    //nombre archivos share prefes+
    private static final String GENERAL_PREFS = "GeneralPrefs";

    //nombre de los campos share prefs
    public static final String PROVINCIA_ID = "idProvincia";
    public static final String IS_PROVINCIA = "isProvincia";
    public static final String FECHHA = "fecha";

    //CONSTRUCTOR
    private SharedPrefs(Context context){
        editor = getEditor(context);
        preferences = getSharedPreferences(context);
    }

    //Share editor
    private static SharedPreferences.Editor getEditor(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(GENERAL_PREFS, Activity.MODE_PRIVATE);
        return preferences.edit();
    }

    //Sahre preferenes
    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(GENERAL_PREFS, Activity.MODE_PRIVATE);
    }



    public static Object getPref(Context context, String preference){

        if (preferences == null) {
            preferences = getSharedPreferences(context);
        }

        switch (preference) {
            case IS_PROVINCIA:
                return getIsProvincia();

            case PROVINCIA_ID:
                return getIdProvincia();

            case FECHHA:
                return getFecha();

            default:
                return new Object();
        }
    }


    public static void setPref(Context context, String preference, Object value) {
        if (editor == null) {
            editor = getEditor(context);
        }
        switch (preference) {
            case IS_PROVINCIA:
                setIsProvincia((boolean) value);
                break;
            case PROVINCIA_ID:
                setIdProvincia((String) value);
                break;
            case FECHHA:
                setFecha((String) value);
                break;
            default:
        }
    //Importante aplicar
        editor.apply();
    }



    private static boolean getIsProvincia(){
        return preferences.getBoolean(IS_PROVINCIA, false);
    }
    private static void setIsProvincia(boolean value){
        editor.putBoolean(IS_PROVINCIA, value);
    }

    private static String getIdProvincia(){
        return preferences.getString(PROVINCIA_ID, null);
    }
    private static void setIdProvincia(String value){
        editor.putString(PROVINCIA_ID, value);
    }

    private static String getFecha(){
        return preferences.getString(FECHHA, null);
    }
    private static void setFecha(String value){
        editor.putString(FECHHA, value);
    }
}
