package com.appliedolap.essbase.impl;

import com.appliedolap.essbase.ApiContext;
import com.appliedolap.essbase.EssApiException;
import com.appliedolap.essbase.EssApplication;
import com.appliedolap.essbase.EssApplicationConfig;
import com.appliedolap.essbase.EssApplicationConfiguration;
import com.appliedolap.essbase.client.ApiException;
import com.appliedolap.essbase.client.model.ApplicationConfigEntry;
import com.appliedolap.essbase.client.model.ApplicationConfigList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

/**
 * Reads and changes one application's configuration.
 *
 * <p>The reading half merges two endpoints, because neither answers the whole question: the catalogue
 * ({@code /configurationkeys}) has every key with its description, syntax and example but never a
 * value, and the configurations list has values but no descriptions.
 */
class EssApplicationConfigImpl implements EssApplicationConfig {

    private final ApiContext api;

    private final EssApplication application;

    EssApplicationConfigImpl(ApiContext api, EssApplication application) {
        this.api = api;
        this.application = application;
    }

    @Override
    public List<EssApplicationConfiguration> keys() {
        return keys(null);
    }

    @Override
    public List<EssApplicationConfiguration> keys(String pattern) {
        Map<String, String> values = currentValues();
        List<EssApplicationConfiguration> keys = new ArrayList<>();
        for (ApplicationConfigEntry entry : catalogue(pattern)) {
            keys.add(new EssApplicationConfigurationImpl(application, entry.getKey(),
                    values.get(upper(entry.getKey())), entry.getDescription(), entry.getSyntax(), entry.getExample()));
        }
        return Collections.unmodifiableList(keys);
    }

    @Override
    public List<EssApplicationConfiguration> configured() {
        List<EssApplicationConfiguration> configured = new ArrayList<>();
        for (EssApplicationConfiguration key : keys()) {
            if (key.isConfigured()) {
                configured.add(key);
            }
        }
        // A key the application sets but the catalogue doesn't list would otherwise vanish. That should
        // not happen, but a configuration you cannot see is worse than one shown without a description.
        Map<String, String> values = new LinkedHashMap<>(currentValues());
        configured.forEach(key -> values.remove(upper(key.getKey())));
        values.forEach((key, value) ->
                configured.add(new EssApplicationConfigurationImpl(application, key, value)));
        return Collections.unmodifiableList(configured);
    }

    @Override
    public Optional<EssApplicationConfiguration> get(String key) {
        if (key == null || key.isBlank()) {
            return Optional.empty();
        }
        return keys(key).stream().filter(entry -> key.equalsIgnoreCase(entry.getKey())).findFirst();
    }

    /**
     * Adding and replacing are one operation here, and deliberately so.
     * <p>
     * The REST API separates them - POST to the collection adds, PUT to the key replaces - and picking
     * wrong fails. That distinction is the server's business, not a caller's: "set this key to this
     * value" is one intention whether or not the application happened to have it already. Which one to
     * send is decided by looking, and the other is tried if the first is refused, since the answer can
     * go stale between the look and the write.
     */
    @Override
    public EssApplicationConfiguration set(String key, String value) {
        ApplicationConfigEntry body = new ApplicationConfigEntry().key(key).value(value);
        boolean exists = currentValues().containsKey(upper(key));
        try {
            ApplicationConfigEntry written = exists ? replace(key, body) : add(body);
            return new EssApplicationConfigurationImpl(application, written.getKey(), written.getValue());
        } catch (ApiException first) {
            try {
                ApplicationConfigEntry written = exists ? add(body) : replace(key, body);
                return new EssApplicationConfigurationImpl(application, written.getKey(), written.getValue());
            } catch (ApiException second) {
                throw new EssApiException(first);
            }
        }
    }

    private ApplicationConfigEntry add(ApplicationConfigEntry body) throws ApiException {
        return api.getApplicationConfigurationApi()
                .applicationConfigurationAddConfiguration(application.getName(), body);
    }

    private ApplicationConfigEntry replace(String key, ApplicationConfigEntry body) throws ApiException {
        return api.getApplicationConfigurationApi()
                .applicationConfigurationSetConfiguration(application.getName(), key, body);
    }

    @Override
    public void delete(String key) {
        try {
            api.getApplicationConfigurationApi()
                    .applicationConfigurationDeleteConfiguration(application.getName(), key);
        } catch (ApiException e) {
            throw new EssApiException(e);
        }
    }

    /** The keys this application sets, by upper-cased name - Essbase treats them case-insensitively. */
    private Map<String, String> currentValues() {
        Map<String, String> values = new LinkedHashMap<>();
        for (EssApplicationConfiguration entry : application.getConfigurations()) {
            values.put(upper(entry.getKey()), entry.getValue());
        }
        return values;
    }

    /**
     * Fetches the key catalogue.
     *
     * <p>Through the generated client, which it could not be until the specification was corrected:
     * this operation was declared as returning {@code List<ApplicationConfigList>} where the server
     * answers a single {@code {"items":[...]}} object, so the generated call failed outright with
     * {@code Cannot deserialize value of type `ArrayList<ApplicationConfigList>` from Object value}.
     * process.sh now types it as the one object it is.
     */
    private List<ApplicationConfigEntry> catalogue(String pattern) {
        String key = pattern == null || pattern.isBlank() ? null : pattern;
        try {
            ApplicationConfigList page = api.getApplicationConfigurationApi()
                    .applicationConfigurationGetConfigurationKeys(application.getName(), key, null);
            return page == null || page.getItems() == null ? Collections.emptyList() : page.getItems();
        } catch (ApiException e) {
            throw new EssApiException(e);
        }
    }

    private static String upper(String key) {
        return key == null ? "" : key.toUpperCase(Locale.ROOT);
    }

}
