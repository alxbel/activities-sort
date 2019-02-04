package com.merann.actsort;

import com.merann.actsort.enumeration.Attribute;
import com.merann.actsort.model.Activity;
import com.merann.actsort.model.MAFAttributeHierarchyLevel;

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

    static MAFAttributeHierarchyLevel createActivityId() {
        MAFAttributeHierarchyLevel activityId = new MAFAttributeHierarchyLevel();
        activityId.name = Attribute.AC_ORDER_ACTIVITY_ID.name();
        activityId.value = createIdValue();
        return activityId;
    }

    static MAFAttributeHierarchyLevel createActivityStartDate() {
        MAFAttributeHierarchyLevel activityId = new MAFAttributeHierarchyLevel();
        activityId.name = Attribute.AC_ORDER_ACTIVITY_START_DATE.name();
        activityId.value = createStartDateValue();
        return activityId;
    }

    static MAFAttributeHierarchyLevel createActivityWorkCenter() {
        MAFAttributeHierarchyLevel activityId = new MAFAttributeHierarchyLevel();
        activityId.name = Attribute.AC_ORDER_ACTIVITY_WORK_CENTER.name();
        activityId.value = createWorkCenterValue();
        return activityId;
    }

    static MAFAttributeHierarchyLevel createActivityUnitOfWork() {
        MAFAttributeHierarchyLevel activityId = new MAFAttributeHierarchyLevel();
        activityId.name = Attribute.AC_ORDER_ACTIVITY_UNIT_OF_WORK.name();
        activityId.value = createUnitOfWorkValue();
        return activityId;
    }

    static MAFAttributeHierarchyLevel createActivityActualWork() {
        MAFAttributeHierarchyLevel activityId = new MAFAttributeHierarchyLevel();
        activityId.name = Attribute.AC_ORDER_ACTIVITY_ACTUAL_WORK.name();
        activityId.value = String.valueOf(rndRange(1, 10));
        return activityId;
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
        final String sameDate = createStartDateValue();
        for (int i = 0; i < count; i++) {
            activities.add(new Activity(createIdValue(), sameDate, createWorkCenterValue(), String.valueOf(rndRange(1, 10)), createUnitOfWorkValue()));
        }
        return activities;
    }

    private static List<Activity> createActsWithSameDateAndWC(final int count) {
        List<Activity> activities = new ArrayList<>();
        final String sameDate = createStartDateValue();
        final String sameWorkCenter = createWorkCenterValue();
        for (int i = 0; i < count; i++) {
            activities.add(new Activity(createIdValue(), sameDate, sameWorkCenter, String.valueOf(rndRange(1, 10)), createUnitOfWorkValue()));
        }
        return activities;
    }

    private static Activity createRandomAct() {
        return new Activity(createIdValue(), createStartDateValue(), createWorkCenterValue(), String.valueOf(rndRange(1, 10)), createUnitOfWorkValue());
    }

    private static String createIdValue() {
        return ACT_IDS[rndRange(0, ACT_IDS.length - 1)];
    }

    private static String createStartDateValue() {
        final int year = rndRange(2015, 2019);
        final int month = rndRange(1, 12);
        final int day = rndRange(1, 30);

        return String.format("%d-%02d-%02d", year, month, day);
    }

    private static String createWorkCenterValue() {
        int wordCount = rndRange(1, 3);
        if (wordCount == 3) {
            wordCount = rndRange(1, 3);
        }

        StringBuilder workCenter = new StringBuilder();
        for (int i = 0; i < wordCount; i++) {
            String word = getRandomWorkCenterValue();
            while (workCenter.toString().contains(word)) {
                word = getRandomWorkCenterValue();
            }
            workCenter.append(word);
            if (i != wordCount - 1) {
                workCenter.append(" ");
            }
        }
        return workCenter.toString();
    }

    private static String createUnitOfWorkValue() {
        return ACT_UNITS_OF_WORK[rndRange(0, ACT_UNITS_OF_WORK.length - 1)];
    }

    private static String getRandomWorkCenterValue() {
        return ACT_WORK_CENTER_WORDS[rndRange(0, ACT_WORK_CENTER_WORDS.length - 1)];
    }

    static int rndRange(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }
}
