package IT.dto;

import IT.dto.interfaces.RatingCalculate;
import IT.dto.sorted_list.SortedList;
import IT.exceptions.*;

import java.time.LocalDate;

public class Lead extends Employee {
    final private static int dailyBonus = 2;
    final private SortedList<Employee> executors;

    public Lead(String name, int maxTasks, SortedList<Employee> executors) {
        super(name, maxTasks, new RatingCalculate() {
            @Override
            public double calculate() {
                return 0;
            }
        });
        this.executors = executors;
    }

    @Override
    void doWorkDay(LocalDate date) {
        try {
            while (!backlog.isEmpty() && !executors.isEmpty()) {
                Employee employee = executors.get().get();
                Task task = backlog.get().get();
                employee.addNewTask(task);
            }
        } catch (BacklogRangeException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    int getDailyBonus() {
        return dailyBonus;
    }
}
