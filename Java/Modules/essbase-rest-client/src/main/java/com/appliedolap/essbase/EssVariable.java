package com.appliedolap.essbase;

import com.appliedolap.essbase.client.ApiClient;
import com.appliedolap.essbase.client.model.Variable;

public class EssVariable extends EssObject {

    private final Variable variable;

    public EssVariable(ApiClient client, Variable variable) {
        super(client);
        this.variable = variable;
    }

    @Override
    public String getName() {
        return variable.getName();
    }

    public String getValue() {
        return variable.getValue();
    }

}