package commInfra;

import java.io.Serializable;

/**
 *
 * Internal structure of the exchanged messages.
 *
 * Represents a message that is going to be sent between regions.
 * The class is serializable, and has getters to get the values from the fields.
 * Communication is based on a communication channel under the TCP protocol.
 *
 * @author Daniel Vala Correia
 * @author Alexandre Abreu
 */

public class Message implements Serializable {

    /**
     * Serialization key.
     */

    private static final long serialVersionUID = 2021L;

    /**
     * Message type.
     */

    private int type;

    /**
     * Size of Parameters to be sent.
     */

    private int parametersSize;

    /**
     * Size of Returned attributes.
     */

    private int returnAttributesSize;

    /**
     * Describes the parameters types.
     */

    private int[] parametersType = {};

    /**
     * Parameters field.
     */

    private Object[] parameters = {};

    /**
     * Describes the attributes types.
     */

    private int[] returnAttributesType = {};

    /**
     * Attributes field.
     */

    private Object[] returnAttributes = {};

    /**
     * Return type field.
     */

    private int returnType;

    /**
     * Return field.
     */

    private Object returnField = null;

    /**
     * Empty constructor for the message that initializes the default
     * values for all the variables.
     */

    public Message(){ }

    /**
     * Message instantiation.
     *
     * @param type message type
     */

    public Message(int type){
        this.type = type;
        this.parametersSize = 0;
        this.returnAttributesSize = 0;
        this.returnType = AttributeTypes.NONE;
    }

    /**
     * Get message type.
     *
     * @return message type
     */

    public int getType() {
        return type;
    }

    /**
     * Set message type.
     *
     * @param type message type
     */

    public void setType(int type) {
        this.type = type;
    }

    /**
     * Get parameters size.
     *
     * @return parameters size
     */

    public int getParametersSize() {
        return parametersSize;
    }

    /**
     * Set parameters size.
     *
     * @param parametersSize parameters size.
     */

    public void setParametersSize(int parametersSize) {
        this.parametersSize = parametersSize;
    }

    /**
     * Get returned attributes from server size.
     *
     * @return attributes size
     */

    public int getReturnAttributesSize() {
        return returnAttributesSize;
    }

    /**
     * Set returned attributes size.
     *
     * @param returnAttributesSize returned attributes size.
     */

    public void setReturnAttributesSize(int returnAttributesSize) {
        this.returnAttributesSize = returnAttributesSize;
    }

    /**
     * Get parameters type.
     *
     * @return parameters type
     */

    public int[] getParametersType() {
        return parametersType;
    }

    public void setParametersType(int[] parametersType) {
        this.parametersType = parametersType;
    }

    /**
     * Get parameters objects.
     *
     * @return parameters objects
     */

    public Object[] getParameters() {
        return parameters;
    }

    /**
     * Set parameters type.
     *
     * @param parameters parameters type
     */

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }

    /**
     * Get returned attributes from server type.
     *
     * @return attributes type
     */

    public int[] getReturnAttributesType() {
        return returnAttributesType;
    }

    /**
     * Set returned attributes type.
     *
     * @param returnAttributesType returned attributes type
     */

    public void setReturnAttributesType(int[] returnAttributesType) {
        this.returnAttributesType = returnAttributesType;
    }

    /**
     * Get returned attributes from server object.
     *
     * @return returned attributes object
     */

    public Object[] getReturnAttributes() {
        return returnAttributes;
    }

    /**
     * Set returned attributes.
     *
     * @param returnAttributes returned attributes size
     */

    public void setReturnAttributes(Object[] returnAttributes) {
        this.returnAttributes = returnAttributes;
    }

    /**
     * Get return type.
     *
     * @return return type
     */

    public int getReturnType() {
        return returnType;
    }

    /**
     * Set return type.
     *
     * @param returnType return type
     */

    public void setReturnType(int returnType) {
        this.returnType = returnType;
    }

    /**
     * Get return field.
     *
     * @return return field
     */

    public Object getReturnField() {
        return returnField;
    }

    /**
     * Set return field.
     *
     * @param returnField return field
     */

    public void setReturnField(Object returnField) {
        this.returnField = returnField;
    }
}
