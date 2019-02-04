package com.merann.actsort.service.impl;

import com.merann.actsort.enumeration.Attribute;
import com.merann.actsort.model.MAFAttributeHierarchy;
import com.merann.actsort.model.MAFAttributeHierarchyLevel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import static com.merann.actsort.enumeration.Attribute.*;

public class TimeReportCreator {

//    public static final String ACTIVITY_ID = "AC_ORDER_ACTIVITY_ID";
//    public static final String ACTIVITY_START_DATE = "AC_ORDER_ACTIVITY_START_DATE";
//    public static final String ACTIVITY_WORK_CENTER = "AC_ORDER_ACTIVITY_WORK_CENTER";
//    public static final String ACTIVITY_ACTUAL_WORK = "AC_ORDER_ACTIVITY_ACTUAL_WORK";
//    public static final String ACTIVITY_UNIT_OF_WORK = "AC_ORDER_ACTIVITY_UNIT_OF_WORK";

    public String sortAndConvert(List<MAFAttributeHierarchy> attributeList) {
        final StringBuilder response = new StringBuilder();



        for (MAFAttributeHierarchy att : attributeList) {
            if (AC_ACTIVITIES.name().equals(att.getGroup())) {
                final Map<String, Map<String, List<Properties>>> timeReport = new TreeMap<>();
                for (MAFAttributeHierarchyLevel attributeHierarchyLevel : att.getLevels()) {
                    if (attributeHierarchyLevel.name.startsWith(AC_TIMEREPORT.name())) {
                        for (MAFAttributeHierarchyLevel activityLevel : attributeHierarchyLevel.getLevels()) {
                            final Properties activity = new Properties();

                            final String attName = activityLevel.name;
                            switch (valueOf(attName)) {
                                case AC_ORDER_ACTIVITY_ID:
                                case AC_ORDER_ACTIVITY_START_DATE:
                                case AC_ORDER_ACTIVITY_WORK_CENTER:
                                case AC_ORDER_ACTIVITY_ACTUAL_WORK:
                                case AC_ORDER_ACTIVITY_UNIT_OF_WORK:
                                    activity.setProperty(attName, activityLevel.value);
                                    break;
                            }

                            // map of activities with a key = startDate:
                            Map<String, List<Properties>> actsMap = timeReport.get(activity.getProperty(AC_ORDER_ACTIVITY_START_DATE.name()));

                            // reference to the list of activities from actsMap:
                            List<Properties> actsList;

                            // if map of activities (with current startDate) in the response IS NOT empty,
                            // then add current activity to this map:
                            if (actsMap != null) {
                                actsList = actsMap.get(activity.getProperty(AC_ORDER_ACTIVITY_WORK_CENTER.name()));
                                if (actsList == null) {
                                    actsList = new ArrayList<>();
                                    actsMap.put(activity.getProperty(AC_ORDER_ACTIVITY_WORK_CENTER.name()), actsList);
                                }
                                actsList.add(activity);
                            } else {
                                // if map of activities (with current startDate) in the response IS empty, then init new
                                // map (based on current startDate) in the response and add current activity to it:
                                timeReport.put(activity.getProperty(AC_ORDER_ACTIVITY_START_DATE.name()), new TreeMap<>());
                                actsList = new ArrayList<>();
                                actsList.add(activity);
                                actsMap = timeReport.get(activity.getProperty(AC_ORDER_ACTIVITY_START_DATE.name()));
                                actsMap.put(activity.getProperty(AC_ORDER_ACTIVITY_WORK_CENTER.name()), actsList);
                            }
                        }
                    }
                }

                // go through each startDate:
                for (Map.Entry<String, Map<String, List<Properties>>> startDateGroup : timeReport.entrySet()) {
                    // go through each workCenter for current startDate
                    for (Map.Entry<String, List<Properties>> workCenterGroup : startDateGroup.getValue().entrySet()) {
                        // add each activity (with current startDate and workCenter) to string result
                        for (Properties activity : workCenterGroup.getValue()) {
                            response.append("<tr>")
                                    .append("   <td>")
                                    .append("      <div class=\"simple-text\">")
                                    .append(activity.getProperty(AC_ORDER_ACTIVITY_ID.name()))
                                    .append("      </div>")
                                    .append("   </td>")
                                    .append("   <td>")
                                    .append("      <div class=\"simple-text\">")
                                    .append(activity.getProperty(AC_ORDER_ACTIVITY_START_DATE.name()))
                                    .append("      </div>")
                                    .append("   </td>")
                                    .append("   <td>")
                                    .append("      <div class=\"simple-text\">")
                                    .append(activity.getProperty(AC_ORDER_ACTIVITY_WORK_CENTER.name()))
                                    .append("      </div>")
                                    .append("   </td>")
                                    .append("   <td>")
                                    .append("      <div class=\"simple-text\">")
                                    .append(activity.getProperty(AC_ORDER_ACTIVITY_ACTUAL_WORK.name()))
                                    .append("      </div>")
                                    .append("   </td>")
                                    .append("   <td>")
                                    .append("      <div class=\"simple-text\">")
                                    .append(activity.getProperty(AC_ORDER_ACTIVITY_UNIT_OF_WORK.name()))
                                    .append("      </div>")
                                    .append("   </td>")
                                    .append(" </tr>");
                        }
                    }
                }





                /*Long timestamp = null;
                Map<Long, SortedMap<String, List<Map<String, String>>>> timeReports = new HashMap<>()
                for (MAFAttributeHierarchyLevel attributeHierarchyLevel : att.getLevels()) {
                    if (attributeHierarchyLevel.name.startsWith("AC_TIMEREPORT")) {
                        Map<String, String> timeReport = new HashMap<>()
                        for (MAFAttributeHierarchyLevel activityLevel : attributeHierarchyLevel.getLevels()) {
                            if (activityLevel.name.equals("AC_ORDER_ACTIVITY_ID")) {
                                timeReport.put("AC_ORDER_ACTIVITY_ID", activityLevel.value)
                            } else if (activityLevel.name.equals("AC_ORDER_ACTIVITY_START_DATE")) {
                                timestamp = initialFormat.parse(activityLevel.value).getTime()
                                timeReport.put("AC_ORDER_ACTIVITY_START_DATE", String.valueOf(timestamp))
                            } else if (activityLevel.name.equals("AC_ORDER_ACTIVITY_WORK_CENTER")) {
                                timeReport.put("AC_ORDER_ACTIVITY_WORK_CENTER", activityLevel.value)
                            } else if (activityLevel.name.equals("AC_ORDER_ACTIVITY_ACTUAL_WORK")) {
                                timeReport.put("AC_ORDER_ACTIVITY_ACTUAL_WORK", activityLevel.value)
                            } else if (activityLevel.name.equals("AC_ORDER_ACTIVITY_UNIT_OF_WORK")) {
                                timeReport.put("AC_ORDER_ACTIVITY_UNIT_OF_WORK", activityLevel.value)
                            }
                        }
                        if (timeReports.containsKey(timestamp)) {
                            if (timeReports.get(timestamp).containsKey(timeReport.get("AC_ORDER_ACTIVITY_WORK_CENTER"))) {
                                timeReports.get(timestamp).get(timeReport.get("AC_ORDER_ACTIVITY_WORK_CENTER")).add(timeReport)
                            } else {
                                timeReports.get(timestamp).put(timeReport.get("AC_ORDER_ACTIVITY_WORK_CENTER"), new ArrayList<>(timeReport))
                            }
                        } else {
                            Map<String, List<Map<String, String>>> userMap = new TreeMap<>()
                            userMap.put(timeReport.get("AC_ORDER_ACTIVITY_WORK_CENTER"), new ArrayList<>(timeReport))
                            timeReports.put(timestamp, userMap)
                        }
                    }
                }

                for (Map.Entry<Long, SortedMap<String, List<Map<String, String>>>> timestampEntry : timeReports.entrySet()) {
                    for (Map.Entry<String, List<Map<String, String>>> userEntry : timestampEntry.getValue()) {
                        for (Map<String, String> properties in userEntry.value) {
                            sb.append("<tr>")
                            sb.append("   <td>")
                            sb.append("      <div class=\"simple-text\">")
                            sb.append(StringEscapeUtils.escapeHtml4(properties.get("AC_ORDER_ACTIVITY_ID")))
                            sb.append("      </div>")
                            sb.append("   </td>")
                            sb.append("   <td>")
                            sb.append("      <div class=\"simple-text\">")
                            sb.append(getDateString(Long.valueOf(properties.get("AC_ORDER_ACTIVITY_START_DATE")), tz))
                            sb.append("      </div>")
                            sb.append("   </td>")
                            sb.append("   <td>")
                            sb.append("      <div class=\"simple-text\">")
                            sb.append(StringEscapeUtils.escapeHtml4(userEntry.key))
                            sb.append("      </div>")
                            sb.append("   </td>")
                            sb.append("   <td>")
                            sb.append("      <div class=\"simple-text\">")
                            sb.append(StringEscapeUtils.escapeHtml4(properties.get("AC_ORDER_ACTIVITY_ACTUAL_WORK")))
                            sb.append("      </div>")
                            sb.append("   </td>")
                            sb.append("   <td>")
                            sb.append("      <div class=\"simple-text\">")
                            sb.append(StringEscapeUtils.escapeHtml4(properties.get("AC_ORDER_ACTIVITY_UNIT_OF_WORK")))
                            sb.append("      </div>")
                            sb.append("   </td>")
                            sb.append(" </tr>")
                        }
                    }
                }*/
            }
        }

        return response.toString();
    }
}
