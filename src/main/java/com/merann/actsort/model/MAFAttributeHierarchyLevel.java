package com.merann.actsort.model;

import java.util.List;

public class MAFAttributeHierarchyLevel {
    public String name;
    public String value;
    private List<MAFAttributeHierarchyLevel> levels;

    public List<MAFAttributeHierarchyLevel> getLevels() {
        return levels;
    }

    public void setLevels(List<MAFAttributeHierarchyLevel> levels) {
        this.levels = levels;
    }


}
