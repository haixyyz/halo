package org.xujin.halo.dto.event;

import org.xujin.halo.dto.DTO;

/**
 * @author xujin
 */
public class Event extends DTO {
    private static final long serialVersionUID = 5740150436439366761L;
    protected String eventId;
    protected String eventType;

    public String getEventType(){
        return eventType;
    }

    public String getEventId(){
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }
}
