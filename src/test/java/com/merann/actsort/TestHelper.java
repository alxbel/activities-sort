package com.merann.actsort;

import com.merann.actsort.model.Activity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

class TestHelper {

    private static final int RANDOM_ACT_COUNT = 10;
    private static final int SAME_DATE_ACT_COUNT = 5;
    private static final int SAME_DATE_AND_WC_ACT_COUNT = 5;


    private static final String[] ACT_IDS = {
      "LABOR AC", "TRAVEL TIME AC", "LOADING AC", "UNLOADING AC", "DISTRIBUTION AC", "SHIPMENT AC"
    };
    private static final String[] ACT_WORK_CENTER_WORDS = {
        "Bit", "Concrete", "Operatic", "Cement", "Chill", "Neck", "Gossip", "Film", "Teargas", "Hopper",
            "Absurd", "Blister", "Crisis", "Echo"
    };
    private static final String[] ACT_UNITS_OF_WORK = {
            "HR", "IT", "Finances", "PR", "Logistics"
    };

    static List<Activity> createActivities() {
        List<Activity> activities = new ArrayList<>();
        activities.addAll(createRandomActs(RANDOM_ACT_COUNT));
        activities.addAll(createActsWithSameDate(SAME_DATE_ACT_COUNT));
        activities.addAll(createActsWithSameDateAndWC(SAME_DATE_AND_WC_ACT_COUNT));
        return activities;
    }

    private static List<Activity> createRandomActs(final int count) {
        List<Activity> activities = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            activities.add(createRandomAct());
        }
        return activities;
    }

    private static List<Activity> createActsWithSameDate(final int count) {
        List<Activity> activities = new ArrayList<>();
        final String sameDate = createAcDate();
        for (int i = 0; i < count; i++) {
            activities.add(new Activity(createAcId(), sameDate, createAcWorkCenter(), String.valueOf(range(1, 10)), createUnitOfWork()));
        }
        return activities;
    }

    private static List<Activity> createActsWithSameDateAndWC(final int count) {
        List<Activity> activities = new ArrayList<>();
        final String sameDate = createAcDate();
        final String sameWorkCenter = createAcWorkCenter();
        for (int i = 0; i < count; i++) {
            activities.add(new Activity(createAcId(), sameDate, sameWorkCenter, String.valueOf(range(1, 10)), createUnitOfWork()));
        }
        return activities;
    }

    private static Activity createRandomAct() {
        return new Activity(createAcId(), createAcDate(), createAcWorkCenter(), String.valueOf(range(1, 10)), createUnitOfWork());
    }

    private static String createAcId() {
        return ACT_IDS[range(0, ACT_IDS.length - 1)];
    }

    private static String createAcDate() {
        final int year = range(2015, 2019);
        final int month = range(1, 12);
        final int day = range(1, 30);

        return String.format("%d-%02d-%02d", year, month, day);
    }

    private static String createAcWorkCenter() {
        int wordCount = range(1, 3);
        if (wordCount == 3) {
            wordCount = range(1, 3);
        }

        StringBuilder workCenter = new StringBuilder();
        for (int i = 0; i < wordCount; i++) {
            String word = getRandomWorkCenter();
            while (workCenter.toString().contains(word)) {
                word = getRandomWorkCenter();
            }
            workCenter.append(word);
            if (i != wordCount - 1) {
                workCenter.append(" ");
            }
        }
        return workCenter.toString();
    }

    private static String createUnitOfWork() {
        return ACT_UNITS_OF_WORK[range(0, ACT_UNITS_OF_WORK.length - 1)];
    }

    private static String getRandomWorkCenter() {
        return ACT_WORK_CENTER_WORDS[range(0, ACT_WORK_CENTER_WORDS.length - 1)];
    }

    private static int range(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }
}
