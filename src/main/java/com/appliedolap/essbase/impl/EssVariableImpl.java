package com.appliedolap.essbase.impl;

import com.appliedolap.essbase.AbstractEssObject;
import com.appliedolap.essbase.ApiContext;
import com.appliedolap.essbase.EssVariable;
import com.appliedolap.essbase.client.model.Variable;

/**
 * What the three scopes of variable have in common: a name, a value, and the fact that the server
 * models all three with the same {@code Variable} document.
 *
 * <p>Abstract because the operations that matter are not shared. Editing and deleting go to a
 * different endpoint per scope, and the version of this class that let them be inherited had
 * application and cube variables deleting themselves through the <em>server</em> endpoint - so
 * deleting an application variable either removed an unrelated server variable of the same name or
 * failed, and in neither case did what was asked.
 */
public abstract class EssVariableImpl extends AbstractEssObject implements EssVariable {

    /** Mutable: {@link #setValue} writes the new value back so the object matches the server. */
    private Variable variable;

    EssVariableImpl(ApiContext api, Variable variable) {
        super(api);
        this.variable = variable;
    }

    @Override
    public String getName() {
        return variable.getName();
    }

    @Override
    public Type getType() {
        return Type.VARIABLE;
    }

    @Override
    public String getValue() {
        return variable.getValue();
    }

    @Override
    public void setValue(String value) {
        Variable edited = new Variable();
        edited.setName(getName());
        edited.setValue(value);
        variable = edit(edited);
    }

    /**
     * Sends the edit to whichever endpoint owns this scope.
     *
     * @param edited the new state
     * @return what the server says it now holds
     */
    protected abstract Variable edit(Variable edited);

    /** The document as the server sent it, for a subclass that needs to resend it. */
    protected Variable variable() {
        return variable;
    }

    @Override
    public String toString() {
        return getName() + "=" + getValue();
    }

}
