package com.merann.actsort;

import com.merann.actsort.model.Activity;
import com.merann.actsort.service.Converter;
import com.merann.actsort.service.Sorter;
import com.merann.actsort.service.impl.ActivityResponseToStringConverter;
import com.merann.actsort.service.impl.ActivitySorter;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ActivitiesSortTest {

    private static List<Activity> activities;

    private final Sorter<Map<String, Map<String, List<Activity>>>, List<Activity>> activitySorter = new ActivitySorter();
    private final Converter<Map<String, Map<String, List<Activity>>>, String> responseToStringConverter = new ActivityResponseToStringConverter();

    @BeforeAll
    static void init() {
        activities = TestHelper.createActivities();
    }

    @Test
    @Ignore
    void testActivitiesResponseToStringConverter() {
        System.out.println(responseToStringConverter.convert(activitySorter.sort(activities)));
    }

    @Test
    void testSortedSizeEqualsUnsortedSize() {
        final Map<String, Map<String, List<Activity>>> sorted = activitySorter.sort(activities);
        int size = 0;
        for (Map.Entry<String, Map<String, List<Activity>>> startDateGroup : sorted.entrySet()) {
            // go through each workCenter for current startDate
            for (Map.Entry<String, List<Activity>> workCenterGroup : startDateGroup.getValue().entrySet()) {
                // go through each activity
                for (Activity ignored : workCenterGroup.getValue()) {
                    ++size;
                }
            }
        }
        Assertions.assertEquals(size, activities.size());
    }

    @Test
    void testSort() {
        final List<Activity> unsorted = new ArrayList<>();
        unsorted.add(new Activity("LABOR AC", "2019-01-21", "Absurd Bit", "IT", "3"));
        unsorted.add(new Activity("UNLOADING AC", "2018-01-21", "Teargas Gossip", "HR", "5"));
        unsorted.add(new Activity("LABOR AC", "2015-02-21", "Echo", "IT", "3"));
        unsorted.add(new Activity("TRAVEL TIME AC", "2016-01-21", "Concrete Neck", "HR", "1"));
        unsorted.add(new Activity("LOADING AC", "2019-02-21", "Absurd Bit", "IT", "2"));

        final Map<String, Map<String, List<Activity>>> sorted = activitySorter.sort(unsorted);
        int count = 0;
        for (Map.Entry<String, Map<String, List<Activity>>> startDateGroup : sorted.entrySet()) {
            // go through each workCenter for current startDate
            for (Map.Entry<String, List<Activity>> workCenterGroup : startDateGroup.getValue().entrySet()) {
                // go through each activity
                for (Activity activity : workCenterGroup.getValue()) {
                    switch (count) {
                        case 0:
                            Assertions.assertEquals(activity, unsorted.get(2));
                            break;
                        case 1:
                            Assertions.assertEquals(activity, unsorted.get(3));
                            break;
                        case 2:
                            Assertions.assertEquals(activity, unsorted.get(1));
                            break;
                        case 3:
                            Assertions.assertEquals(activity, unsorted.get(0));
                            break;
                        case 4:
                            Assertions.assertEquals(activity, unsorted.get(4));
                            break;
                    }
                    ++count;
                }
            }
        }
    }

    @Test
    void testSortWithSameDate() {
        final List<Activity> unsorted = new ArrayList<>();
        unsorted.add(new Activity("LABOR AC", "2019-01-21", "Absurd Bit", "IT", "3"));
        unsorted.add(new Activity("UNLOADING AC", "2018-01-21", "Teargas Gossip", "HR", "5"));
        unsorted.add(new Activity("LABOR AC", "2019-01-21", "Echo", "IT", "3"));
        unsorted.add(new Activity("TRAVEL TIME AC", "2016-01-21", "Concrete Neck", "HR", "1"));
        unsorted.add(new Activity("LOADING AC", "2019-02-21", "Absurd Bit", "IT", "2"));

        final Map<String, Map<String, List<Activity>>> sorted = activitySorter.sort(unsorted);
        int count = 0;
        for (Map.Entry<String, Map<String, List<Activity>>> startDateGroup : sorted.entrySet()) {
            // go through each workCenter for current startDate
            for (Map.Entry<String, List<Activity>> workCenterGroup : startDateGroup.getValue().entrySet()) {
                // go through each activity
                for (Activity activity : workCenterGroup.getValue()) {
                    switch (count) {
                        case 0:
                            Assertions.assertEquals(activity, unsorted.get(3));
                            break;
                        case 1:
                            Assertions.assertEquals(activity, unsorted.get(1));
                            break;
                        case 2:
                            Assertions.assertEquals(activity, unsorted.get(0));
                            break;
                        case 3:
                            Assertions.assertEquals(activity, unsorted.get(2));
                            break;
                        case 4:
                            Assertions.assertEquals(activity, unsorted.get(4));
                            break;
                    }
                    ++count;
                }
            }
        }
    }
}