package com.merann.actsort.model;


import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Activity {
    public static final String ACTIVITY_ID_ATTR = "AC_ORDER_ACTIVITY_ID";
    public static final String ACTIVITY_START_DATE_ATTR = "AC_ORDER_ACTIVITY_START_DATE";
    public static final String ACTIVITY_WORK_CENTER_ATTR = "AC_ORDER_ACTIVITY_WORK_CENTER";
    public static final String ACTIVITY_ACTUAL_WORK_ATTR = "AC_ORDER_ACTIVITY_ACTUAL_WORK";
    public static final String ACTIVITY_UNIT_OF_WORK_ATTR = "AC_ORDER_ACTIVITY_UNIT_OF_WORK";

    private final Map<String, String> attributes = new HashMap<>();

    public Activity(String id, String startDate, String workCenter, String actualWork, String unitOfWork) {
        attributes.put(ACTIVITY_ID_ATTR, id);
        attributes.put(ACTIVITY_START_DATE_ATTR, startDate);
        attributes.put(ACTIVITY_WORK_CENTER_ATTR, workCenter);
        attributes.put(ACTIVITY_ACTUAL_WORK_ATTR, actualWork);
        attributes.put(ACTIVITY_UNIT_OF_WORK_ATTR, unitOfWork);
    }

    @Override
    public String toString() {
        return String.format("%-20s%-20s%-20s%-20s%-20s",
                attributes.get(ACTIVITY_ID_ATTR),
                attributes.get(ACTIVITY_START_DATE_ATTR),
                attributes.get(ACTIVITY_WORK_CENTER_ATTR),
                attributes.get(ACTIVITY_UNIT_OF_WORK_ATTR),
                attributes.get(ACTIVITY_ACTUAL_WORK_ATTR));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Activity activity = (Activity) o;
        return Objects.equals(attributes, activity.attributes);
    }

    public String getAttribute(String attribute) {
        return attributes.get(attribute);
    }

}
