/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 */
package night.events;


public class Event {
    private boolean cancelled;

    public void cancel() {
        this.cancelled = true;
    }

    public Event() {
    }

    public boolean isCancelled() {
        return this.cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Event)) {
            return false;
        }
        Event other = (Event)o;
        if (!other.canEqual(this)) {
            return false;
        }
        return this.isCancelled() == other.isCancelled();
    }

    protected boolean canEqual(Object other) {
        return other instanceof Event;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + (this.isCancelled() ? 79 : 97);
        return result;
    }

    public String toString() {
        return "Event(cancelled=" + this.isCancelled() + ")";
    }
}

