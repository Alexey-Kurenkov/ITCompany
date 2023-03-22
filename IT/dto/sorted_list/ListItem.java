package IT.dto.sorted_list;

import IT.exceptions.DateSequenceException;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

/**
 * в стеке нужно хранить не только T, но и тайминги - время начала и время окончания
 * это класс обёртка для бэклога, а уже List обернет этот класс.
 * Comparable.compareTo необходимо переопределять в потомках
 */
public class ListItem <T extends Comparable> {
    final private LocalDateTime start;
    private LocalDateTime finish;

    private T item;

    public ListItem(LocalDateTime start, T item) {
        this.start = start != null ? start : LocalDateTime.now();
        this.finish = null;
        this.item = item;
    }

    public T get() {
        return item;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getFinish() {
        return finish;
    }

    public void setFinish(LocalDateTime finish) throws DateSequenceException {
        if (start.isAfter(finish)) {
            throw new DateSequenceException();
        }

        this.finish = finish;
    }

    public long getDurationMilli() {
        return ChronoUnit.MILLIS.between(start, finish);
    }

    public boolean isClosed() {
        return finish != null;
    }
    public boolean isBusy() {
        return finish == null;
    }

    /**
     * this полностью включена в item
     * @param item
     * @return
     */
    public boolean isInclude(ListItem item) throws DateSequenceException {
        if (item == null) throw new DateSequenceException();

        long st = this.getStart().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        long si = item.getStart().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        long ft = this.getFinish() == null? Long.MAX_VALUE : this.getFinish().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        long fi = item.getFinish() == null? Long.MAX_VALUE : item.getFinish().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

        return st >= si && ft <= fi;
    }

    /**
     * проверка на пересечение this intersect item
     * @param item
     * @return
     * @throws DateSequenceException
     */
    public boolean isIntersect(ListItem item) throws DateSequenceException {
        if (item == null) throw new DateSequenceException();

        long st = this.getStart().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        long si = item.getStart().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        long ft = this.getFinish() == null? Long.MAX_VALUE : this.getFinish().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        long fi = item.getFinish() == null? Long.MAX_VALUE : item.getFinish().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

        return st <= fi && si <= ft;
    }
}
