package Utilities;

import java.util.Locale;

/**
 * Singleton class responsible for keeping track of the user's language settings.
 */
public class LanguageSettings {

    /**
     * this object starts null and is assigned on its first use.
     */
    private static LanguageSettings languageSettingsSingleton = null;


    /**
     * gets the systemLanguage. If the user's local string starts with en, sets the language to english. If it starts with fr, then the language is set to French. If its anything else,
     * it defaults to english.
     * @return the system language. English if it defaults.
     */
    public String getSystemLanguage() {
        String userLang = Locale.getDefault().getLanguage();

        if (userLang.startsWith("en")) {
            return "english";
        }
        else if (userLang.startsWith("fr")) {
            return "french";
        }
        //defaults to english when unsupported.
        else
            return "english";
    }

    /**
     * private constructor to prevent this class from being instantiated directly.
     */
    private LanguageSettings() {
    }

    /**
     * gets the instance of the LanguageSettings
     * @return returns a new instance of languageSettingsSingleton if it doesn't exist yet, or returns the existing one after its created.
     */
    public static LanguageSettings getInstance() {
        if(languageSettingsSingleton == null)
            languageSettingsSingleton = new LanguageSettings();

        return  languageSettingsSingleton;
    }



}
