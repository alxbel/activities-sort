package com.merann.actsort.service.impl;

import com.merann.actsort.model.MAFAttributeHierarchy;
import com.merann.actsort.model.MAFAttributeHierarchyLevel;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import static com.merann.actsort.enumeration.Attribute.*;

public class TimeReportCreator {

    private static final String DIV_CLASS = " class=\"simple-text\"";

    public void createHtml(List<MAFAttributeHierarchy> attributeList, String fileName) throws IOException {
        FileWriter fileWriter = new FileWriter(fileName);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.print(asString(attributeList));
        printWriter.close();
    }

    public String asString(List<MAFAttributeHierarchy> attributeList) {
        final StringBuilder report = new StringBuilder();
        report.append("<table border=\"3\">\n");

        for (MAFAttributeHierarchy att : attributeList) {
            if (AC_ACTIVITIES.name().equals(att.getGroup())) {
                final Map<String, Map<String, List<Properties>>> timeReport = new TreeMap<>();
                for (MAFAttributeHierarchyLevel attributeHierarchyLevel : att.getLevels()) {
                    if (attributeHierarchyLevel.name.startsWith(AC_TIMEREPORT.name())) {
                        final Properties activity = new Properties();
                        for (MAFAttributeHierarchyLevel activityLevel : attributeHierarchyLevel.getLevels()) {

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
                        //}
                    }
                }

                // go through each startDate:
                for (Map.Entry<String, Map<String, List<Properties>>> startDateGroup : timeReport.entrySet()) {
                    // go through each workCenter for current startDate
                    for (Map.Entry<String, List<Properties>> workCenterGroup : startDateGroup.getValue().entrySet()) {
                        // add each activity (with current startDate and workCenter) to string result
                        for (Properties activity : workCenterGroup.getValue()) {
                            report
                                    .append              ("<tr>\n")
                                    .append              ("\t<td>\n")
                                    .append(String.format("\t\t<div%s>\n", DIV_CLASS))
                                    .append(String.format("\t\t\t%s\n", activity.getProperty(AC_ORDER_ACTIVITY_ID.name())))
                                    .append              ("\t\t</div>\n")
                                    .append              ("\t</td>\n")
                                    .append              ("\t<td>\n")
                                    .append(String.format("\t\t<div%s>\n", DIV_CLASS))
                                    .append(String.format("\t\t\t%s\n", activity.getProperty(AC_ORDER_ACTIVITY_START_DATE.name())))
                                    .append              ("\t\t</div>\n")
                                    .append              ("\t</td>\n")
                                    .append              ("\t<td>\n")
                                    .append(String.format("\t\t<div%s>\n", DIV_CLASS))
                                    .append(String.format("\t\t\t%s\n", activity.getProperty(AC_ORDER_ACTIVITY_WORK_CENTER.name())))
                                    .append              ("\t\t</div>\n")
                                    .append              ("\t</td>\n")
                                    .append              ("\t<td>\n")
                                    .append(String.format("\t\t<div%s>\n", DIV_CLASS))
                                    .append(String.format("\t\t\t%s\n", activity.getProperty(AC_ORDER_ACTIVITY_ACTUAL_WORK.name())))
                                    .append              ("\t\t</div>\n")
                                    .append              ("\t</td>\n")
                                    .append              ("\t<td>\n")
                                    .append(String.format("\t\t<div%s>\n", DIV_CLASS))
                                    .append(String.format("\t\t\t%s\n", activity.getProperty(AC_ORDER_ACTIVITY_UNIT_OF_WORK.name())))
                                    .append              ("\t\t</div>\n")
                                    .append              ("\t</td>\n")
                                    .append              ("</tr>\n");
                        }
                    }
                }
            }
        }

        report.append("</table>");

        return report.toString();
    }
}
