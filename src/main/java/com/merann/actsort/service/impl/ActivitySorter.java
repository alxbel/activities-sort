package com.merann.actsort.service.impl;

import com.merann.actsort.model.Activity;
import com.merann.actsort.service.Sorter;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;


public class ActivitySorter implements Sorter<Map<String, Map<String, List<Activity>>>, List<Activity>> {

    /**
     * Sort activities by attributes 'start date' and 'work center'
     * Structure of the returned response: Map<startDate, Map<workCenter, List<activity>>>
     *
     * @param unsortedActivities list of unsorted activities
     * @return response with sorted activities
     */
    @Override
    public Map<String, Map<String, List<Activity>>> sort(List<Activity> unsortedActivities) {
        final Map<String, Map<String, List<Activity>>> response = new TreeMap<>();

        for (Activity activity : unsortedActivities) {
            // attribute 'start date' and 'work center', on which activities are being sorted:
            /*final String startDate = activity.getAttribute(Activity.ACTIVITY_START_DATE_ATTR);
            final String workCenter = activity.getAttribute(Activity.ACTIVITY_WORK_CENTER_ATTR);

            // map of activities with a key = startDate:
            Map<String, List<Activity>> actsMap = response.get(startDate);

            // reference to the list of activities from actsMap:
            List<Activity> actsList;

            // if map of activities (with current startDate) in the response IS NOT empty,
            // then add current activity to this map:
            if (actsMap != null) {
                actsList = actsMap.get(workCenter);
                if (actsList == null) {
                    actsList = new ArrayList<>();
                    actsMap.put(workCenter, actsList);
                }
                actsList.add(activity);

            } else {
                // if map of activities (with current startDate) in the response IS empty, then init new
                // map (based on current startDate) in the response and add current activity to it:
                response.put(startDate, new TreeMap<>());
                actsList = new ArrayList<>();
                actsList.add(activity);
                actsMap = response.get(startDate);
                actsMap.put(workCenter, actsList);
            }*/
        }

        return response;
    }
}
