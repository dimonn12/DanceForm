package by.danceform.app.localization;

import by.danceform.app.domain.system.LanguageString;
import by.danceform.app.service.system.LanguageStringService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.lang.ref.SoftReference;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by dimonn12 on 24.02.2017.
 */
@Component
public class LocalizationHelper {

    private static final String LANGUAGE_COOKIE_NAME = "NG_TRANSLATE_LANG_KEY";

    private static Map<String, Map<String, SoftReference<String>>> cachedValues;

    @Inject
    private LanguageStringService languageStringService;

    @PostConstruct
    public void init() {
        cachedValues = new ConcurrentHashMap<>();
    }

    //TODO
    public void putIntoCache(String lang, String name, String value) {
        if(StringUtils.isBlank(lang) || StringUtils.isBlank(name) || StringUtils.isBlank(value)) {
            return;
        }
        Map<String, SoftReference<String>> cachedLang = cachedValues.get(lang);
        if(null == cachedLang) {
            cachedLang = new ConcurrentHashMap<>();
            cachedValues.put(lang, cachedLang);
        }
        cachedLang.put(name, new SoftReference<>(value));
    }

    public String get(HttpServletRequest request, String name) {
        String lang = LanguageString.DEFAULT_SYSTEM_LANGUAGE;
        for (Cookie cookie: request.getCookies()) {
            if (Objects.equals(cookie.getName(), LANGUAGE_COOKIE_NAME)) {
                lang = cookie.getValue();
                break;
            }
        }
        if (!StringUtils.isBlank(lang)) {
            lang = StringUtils.remove(lang, "%22");
            lang = lang.trim();
        }
        return get(name, lang);
    }

    public String get(String name, String lang) {
        Map<String, SoftReference<String>> cachedLang = cachedValues.get(lang);
        if(null == cachedLang) {
            update(name, lang);
            return get(name, lang);
        }
        SoftReference<String> softValue = cachedLang.get(name);
        if(null != softValue) {
            return softValue.get();
        } else {
            update(name, lang);
            return get(name, lang);
        }
    }

    public void clearCache() {
        cachedValues.clear();
    }

    private void update(String name, String lang) {
        LanguageString languageString = languageStringService.findByLanguage(name, lang);
        if((null == languageString || StringUtils.isBlank(languageString.getValue())) &&
           !Objects.equals(LanguageString.DEFAULT_SYSTEM_LANGUAGE, lang)) {
            languageString = languageStringService.findByLanguage(name, LanguageString.DEFAULT_SYSTEM_LANGUAGE);
        }
        if(null != languageString && !StringUtils.isBlank(languageString.getValue())) {
            putIntoCache(lang, name, languageString.getValue());
        } else {
            putIntoCache(lang, name, name);
        }
    }

}
