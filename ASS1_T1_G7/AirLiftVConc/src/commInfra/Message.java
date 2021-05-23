package commInfra;

import java.io.Serializable;

/**
 *
 *   Internal structure of the exchanged messages.
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
     * Pilot identification.
     */
    private int pilotId;

    /**
     * Hostess identification.
     */
    private int hostessId;

    /**
     * Passenger identification.
     */
    private int passengerId;

    /**
     * Pilot state.
     */
    private int pilotState;

    /**
     * Hostess state.
     */
    private int hostessState;

    /**
     * Passenger state.
     */
    private int passengerState;

    /**
     * Number of passengers in queue waiting for to show their documents to the hostess.
     */
    private int inQ;

    /**
     * True if the passenger has been called by the hostess to show his documents.
     */
    private boolean readyToShowDocuments;

    /**
     * Count number of passengers on the plane.
     */
    private int hostessCount;

    /**
     * Count number of passengers checked by hostess.
     */
    private int checkedPassengers;

    /**
     * True if the passenger has given his documents to the hostess for her to check.
     */
    private boolean readyToCheckDocuments;

    /**
     * True if the hostess can check next passenger documents.
     */
    private boolean readyForNextPassenger;

    /**
     * Reference to number of passengers in the plane.
     */
    private int inF;

    /**
     * True if the plane is ready to take off.
     */
    private boolean readyToTakeOff;

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
    public Message(){

    }

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
     * Get parameter serialVersionUID.
     *
     * @return serialVersionUID
     */
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    /**
     * Get parameter type.
     *
     * @return type
     */
    public int getType() {
        return type;
    }

    /**
     * Get parameter pilotID.
     *
     * @return pilotID
     */
    public int getPilotId() {
        return pilotId;
    }

    /**
     * Get parameter hostessId.
     *
     * @return hostessId
     */
    public int getHostessId() {
        return hostessId;
    }

    /**
     * Get parameter passengerId.
     *
     * @return passengerId
     */
    public int getPassengerId() {
        return passengerId;
    }

    /**
     * Get parameter pilotState.
     *
     * @return pilotState
     */
    public int getPilotState() {
        return pilotState;
    }

    /**
     * Get parameter hostessState.
     *
     * @return hostessState
     */
    public int getHostessState() {
        return hostessState;
    }

    /**
     * Get parameter passengerState.
     *
     * @return passengerState
     */
    public int getPassengerState() {
        return passengerState;
    }

    /**
     * Get parameter InQ.
     *
     * @return InQ
     */
    public int getInQ() {
        return inQ;
    }

    /**
     * Get parameter readyToShowDocuments.
     *
     * @return readyToShowDocuments
     */
    public boolean isReadyToShowDocuments() {
        return readyToShowDocuments;
    }

    /**
     * Get parameter hostessCount.
     *
     * @return hostessCount
     */
    public int getHostessCount() {
        return hostessCount;
    }

    /**
     * Get parameter checkedPassengers.
     *
     * @return checkedPassengers
     */
    public int getCheckedPassengers() {
        return checkedPassengers;
    }

    /**
     * Get parameter readyToCheckDocuments.
     *
     * @return readyToCheckDocuments
     */
    public boolean isReadyToCheckDocuments() {
        return readyToCheckDocuments;
    }

    /**
     * Get parameter readyForNextPassenger.
     *
     * @return readyForNextPassenger
     */
    public boolean isReadyForNextPassenger() {
        return readyForNextPassenger;
    }

    /**
     * Get parameter InF.
     *
     * @return InF
     */
    public int getInF() {
        return inF;
    }

    /**
     * Get parameter readyToTakeOff.
     *
     * @return readyToTakeOff
     */
    public boolean isReadyToTakeOff() {
        return readyToTakeOff;
    }

    public int getParametersSize() {
        return parametersSize;
    }

    public int getReturnAttributesSize() {
        return returnAttributesSize;
    }

    public int[] getParametersType() {
        return parametersType;
    }

    public Object[] getParameters() {
        return parameters;
    }

    public int[] getReturnAttributesType() {
        return returnAttributesType;
    }

    public Object[] getReturnAttributes() {
        return returnAttributes;
    }

    public int getReturnType() {
        return returnType;
    }

    public Object getReturnField() {
        return returnField;
    }

    public void setParametersSize(int parametersSize) {
        this.parametersSize = parametersSize;
    }

    public void setReturnAttributesSize(int returnAttributesSize) {
        this.returnAttributesSize = returnAttributesSize;
    }

    public void setParametersType(int[] parametersType) {
        this.parametersType = parametersType;
    }

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }

    public void setReturnAttributesType(int[] returnAttributesType) {
        this.returnAttributesType = returnAttributesType;
    }

    public void setReturnAttributes(Object[] returnAttributes) {
        this.returnAttributes = returnAttributes;
    }

    public void setReturnType(int returnType) {
        this.returnType = returnType;
    }

    public void setReturnField(Object returnField) {
        this.returnField = returnField;
    }
}
