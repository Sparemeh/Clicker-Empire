package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

// Modeled after CPSC 210 Alarm System project

// Represents an Event log with a list of events
public class EventLog implements Iterable<Event> {
    /** the only EventLog in the system (Singleton Design Pattern) */
    private static final EventLog theLog = new EventLog();
    private Collection<Event> events;

    /**
     * Prevent external construction.
     * (Singleton Design Pattern).
     */
    private EventLog() {
        events = new ArrayList<Event>();
    }

    /**
     * Gets instance of EventLog - creates it
     * if it doesn't already exist.
     * (Singleton Design Pattern)
     * @return  instance of EventLog
     */
    public static EventLog getInstance() {
        return theLog;
    }

    /**
     * Adds an event to the event log.
     * @param e the event to be added
     */
    public void logEvent(Event e) {
        events.add(e);
    }

    /**
     * Clears the event log and logs the event.
     */
    public void clear() {
        events.clear();
        logEvent(new Event("Event log cleared."));
    }

    @Override
    public Iterator<Event> iterator() {
        return events.iterator();
    }
}
