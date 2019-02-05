package com.merann.actsort;


import com.merann.actsort.model.MAFAttributeHierarchy;
import com.merann.actsort.model.MAFAttributeHierarchyLevel;
import com.merann.actsort.service.impl.TimeReportCreator;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.merann.actsort.TestHelper.*;
import static com.merann.actsort.enumeration.Attribute.*;

public class TimeReportTest {

    @Test
    void testReport() throws IOException {
        final int reportsCount = 50;
        MAFAttributeHierarchy activities = new MAFAttributeHierarchy();
        activities.setGroup(AC_ACTIVITIES.name());

        List<MAFAttributeHierarchyLevel> timeReports = new ArrayList<>();

        for (int i = 0; i < reportsCount; i++) {
            StringBuilder reportNumbers = new StringBuilder();
            for (int j = 0; j < 9; j++) {
                reportNumbers.append(rndRange(0, 9));
            }
            MAFAttributeHierarchyLevel timeReport = new MAFAttributeHierarchyLevel();
            timeReport.name = AC_TIMEREPORT.name() + "_" + reportNumbers;
            timeReport.setLevels(new ArrayList<>(new ArrayList<MAFAttributeHierarchyLevel>() {{
                add(createActivityId());
                add(createActivityStartDate());
                add(createActivityWorkCenter());
                add(createActivityUnitOfWork());
                add(createActivityActualWork());
            }}));
            timeReports.add(timeReport);
        }
        activities.setLevels(timeReports);

        TimeReportCreator timeReportCreator = new TimeReportCreator();
        List<MAFAttributeHierarchy> hierarchy = new ArrayList<MAFAttributeHierarchy>(){{add(activities);}};
        timeReportCreator.createHtml(hierarchy, "target/report" +
                DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format(LocalDateTime.now()) +
                ".html");
    }
}
