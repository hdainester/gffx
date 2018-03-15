package gffx.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Locale {
    private static Map<String, Map<String, String>> langMap;
    private static Set<String> languages;
    private static String lang;

    static {
        langMap = new HashMap<>();
        languages = new HashSet<>();

        // parse locale file
        try(BufferedInputStream bis = new BufferedInputStream(new FileInputStream("res/ttt/locale"))) { // TODO static parse method(String url)
            int nextByte;
            char nextChar;
            String id = "", lang = "", val = "", buf = "";
            boolean lineLock=false, idNext=true, langNext=false, valNext=false;
            
            while((nextByte = bis.read()) > 0) {
                nextChar = (char)nextByte;

                if(nextChar == '#') // comment line
                    lineLock = true;
                else if(lineLock)
                    lineLock = nextChar != '\n';
                
                if(!lineLock) {
                    if(!(idNext || langNext || valNext)) {
                        if(nextChar == ':') {
                            id = buf.trim();
                            idNext = true;
                        } else if(nextChar == ',') {
                            lang = buf.trim();
                            langNext = true;
                        } else if(nextChar == ';') {
                            val = buf.trim();
                            valNext = true;
                        } else
                            buf += nextChar;
                    }

                    if(idNext) {
                        idNext = false;
                        lang = buf = "";
                        langMap.put(id, new HashMap<>());
                    } else if(langNext) {
                        langNext = false;
                        val = buf = "";
                    } else if(valNext) {
                        valNext = false;
                        buf = "";
                        languages.add(lang);
                        langMap.get(id).put(lang, val);
                    }
                }

            }
        } catch(Exception e) {
            e.printStackTrace();
        }

        lang = "";
    }

    public static String get(String id) {
        return get(id, lang);
    }

    public static String get(String id, String lang) {
        if(langMap.containsKey(id))
            if(langMap.get(id).containsKey(lang))
                return langMap.get(id).get(lang);
            else if(langMap.get(id).size() > 0)
                // first specified language in locale is default language
                return langMap.get(id).values().toArray(new String[]{})[0];

        return "%?!$%#";
    }

    public static String getLang() {
        return lang;
    }

    public static Set<String> getLanguages() {
        return languages;
    }

    public static void setLang(String lang) {
        Locale.lang = lang;
    }
}