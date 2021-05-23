package commInfra;

/**
 * Message Types
 */

public enum MessageType {
    GET_INQ,
    PREPARE_FOR_PASS_BOARDING,
    WAIT_IN_QUEUE,
    CHECK_DOCUMENTS,
    SHOW_DOCUMENTS,
    WAIT_FOR_NEXT_PASSENGER,
    BOARD_THE_PLANE,
    GET_INF,
    SET_INF,
    REPORT_FINAL_REPORT,
    PARK_AT_TRANSFER_GATE,
    INFORM_PLANE_READY_FOR_BOARDING,
    WAIT_FOR_NEXT_FLIGHT,
    WAIT_FOR_ALL_BOARDING,
    INFORM_PLANE_READY_TO_TAKEOFF,
    WAIT_FOR_END_OF_FLIGHT,
    ANNOUNCE_ARRIVAL,
    LEAVE_THE_PLANE,
    GET_PTAL,
    INC_PTAL,
    SET_PASSENGER_STATE,
    SET_HOSTESS_STATE,
    SET_PILOT_STATE,
    REPORT_INITIAL_STATUS,
    REPORT_STATUS,
    REPORT_FINAL_INFO,
    SHUTDOWN
}
