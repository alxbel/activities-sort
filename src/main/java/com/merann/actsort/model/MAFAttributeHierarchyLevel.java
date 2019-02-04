package com.merann.actsort.model;

import java.util.List;

public class MAFAttributeHierarchyLevel {
    public final String name;
    public final String value;
    private List<MAFAttributeHierarchyLevel> levels;

    public MAFAttributeHierarchyLevel(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public List<MAFAttributeHierarchyLevel> getLevels() {
        return levels;
    }

    public void setLevels(List<MAFAttributeHierarchyLevel> levels) {
        this.levels = levels;
    }
}
