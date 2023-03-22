package IT.dto;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class WorkDay implements Comparable {
    private LocalDate date;

    public WorkDay(LocalDate date) {
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }

    public DayOfWeek getDayOfWeek () {
        return date.getDayOfWeek();
    }

    @Override
    public int compareTo(Object o) {
        LocalDate d = (LocalDate) o;
        return d.compareTo(date);
    }
}
