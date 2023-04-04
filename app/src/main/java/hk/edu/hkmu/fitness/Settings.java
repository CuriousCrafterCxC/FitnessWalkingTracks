package hk.edu.hkmu.fitness;

public class Settings {
    private static String lang = "en";
    private static String selection = "en";
    public static String getLang (){
        return lang;
    }
    public static void setLang (String lang){
        Settings.lang = lang;
    }
    public static String getSelection(){
        return selection;
    }
    public static void setSelection(String lang){
        Settings.selection = lang;
    }
}
