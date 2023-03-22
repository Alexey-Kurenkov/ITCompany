package IT.dto;

import IT.dto.interfaces.RatingCalculate;
import IT.dto.sorted_list.ListItem;
import IT.exceptions.BacklogRangeException;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Developer extends Employee {
    final private static int dailyBonus = 3;
    private RatingCalculate bonusCalculate;

    public Developer(String name, int maxTasks) {
        super(name, maxTasks, new RatingCalculate() {
            @Override
            public double calculate() {
                /*SortedList<Task> bl = backlog;
                final SortedList<WorkDay> wd = histroryWorkDay;
                int rating = bonuses + (bl.getlistSize() * 3 + wd.getlistSize() * 2);*/
                return 0;
            }
        });
    }

    @Override
    void doWorkDay(LocalDate date) {
        try {
            ListItem<WorkDay> wd = histroryWorkDay.get();
            LocalDateTime start = wd.getStart(); // старт рабочего дня
            LocalDateTime finish = wd.getFinish();

            for (LocalDateTime step = start; start.isBefore(finish); step.plusHours(1)) {
                if (!backlog.isEmpty()) {
                    Task task = backlog.get().get();
                    System.out.println(this + " взял в работу задачу " + task + " в " + step);
                    System.out.println(this + " завершил работу задачи " + task + " в " + step.plusHours(1));
                }
            }
        } catch (BacklogRangeException e) {}
    }

    @Override
    int getDailyBonus() {
        return dailyBonus;
    }
}