package IT.dto;

/**
 * задача для ИТ специалиста, есть идентификатор, имя, время запланированное для реализации.
 */
public class Task implements Comparable {
    private static int autoIncrement = 0;
    private int id;
    private String name;
    private int hoursPlanned;
    private Priority priority = Priority.NORMAL;;

    public Task(String name, int hoursPlanned) {
        this.name = name;
        this.hoursPlanned = hoursPlanned;
        id = ++autoIncrement;
    }

    @Override
    public String toString() {
        return this.getClass().getName() + " { id = " + id + ", name = '" + name + "'}";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Employee)) return false;
        return ((Task) obj).getId() == getId();
    }

    public int getId() {
        return id;
    }

    public int getHoursPlanned() {
        return hoursPlanned;
    }

    public void setHoursPlanned(int hoursPlanned) {
        this.hoursPlanned = hoursPlanned;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    @Override
    public int compareTo(Object o) {
        Task t = (Task) o;
        return this.priority.ordinal() - t.priority.ordinal();
    }

    public enum Priority {
        HIGH, NORMAL, LOW;
    }
}
