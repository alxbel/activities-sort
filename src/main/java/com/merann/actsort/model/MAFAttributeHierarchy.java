package com.merann.actsort.model;

import java.util.List;

public class MAFAttributeHierarchy {
    private String group;
    private List<MAFAttributeHierarchyLevel> levels;

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public List<MAFAttributeHierarchyLevel> getLevels() {
        return levels;
    }

    public void setLevels(List<MAFAttributeHierarchyLevel> levels) {
        this.levels = levels;
    }
}
