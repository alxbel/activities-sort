package com.merann.actsort.service.impl;

import com.merann.actsort.model.Activity;
import com.merann.actsort.service.Converter;

import java.util.List;
import java.util.Map;

public class ActivityResponseToStringConverter implements Converter<Map<String, Map<String, List<Activity>>>, String> {

    /**
     * Converts weird activities response to {@link String} representation
     *
     * @param activitiesResponse sorted omfg collection of activities
     * @return stringified representation of sorted activities with all attributes presented within output
     */
    @Override
    public String convert(Map<String, Map<String, List<Activity>>> activitiesResponse) {
        final StringBuilder result = new StringBuilder();
        final int fieldSize = 25;
        final StringBuilder fmt = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            fmt.append("%-").append(fieldSize).append("s\t");
        }
        fmt.setCharAt(fmt.length()-1, '\n');
        final String head = String.format(fmt.toString(),
                "ACTIVITY_ID", "ACTIVITY_START_DATE", "ACTIVITY_WORK_CENTER",
                "ACTIVITY_UNIT_OF_WORK", "ACTIVITY_ACTUAL_WORK");
        result.append(head);
        for (int i = 0; i < head.length(); i++) {
            result.append("-");
        }
        result.append("\n");

        // the iteration through sorted collection is as follows:

        // go through each startDate:
        for (Map.Entry<String, Map<String, List<Activity>>> startDateGroup : activitiesResponse.entrySet()) {
            // go through each workCenter for current startDate
            for (Map.Entry<String, List<Activity>> workCenterGroup : startDateGroup.getValue().entrySet()) {
                // add each activity (with current startDate and workCenter) to string result
                for (Activity activity : workCenterGroup.getValue()) {
                    result.append(String.format(fmt.toString(),
                            activity.getAttribute(Activity.ACTIVITY_ID_ATTR),
                            activity.getAttribute(Activity.ACTIVITY_START_DATE_ATTR),
                            activity.getAttribute(Activity.ACTIVITY_WORK_CENTER_ATTR),
                            activity.getAttribute(Activity.ACTIVITY_UNIT_OF_WORK_ATTR),
                            activity.getAttribute(Activity.ACTIVITY_ACTUAL_WORK_ATTR)));
                }
            }
        }
        // return a long string with all activities in it
        return result.toString();
    }
}
