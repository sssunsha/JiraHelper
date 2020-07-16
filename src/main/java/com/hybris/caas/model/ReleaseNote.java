package com.hybris.caas.model;

public class ReleaseNote implements Comparable<ReleaseNote>{
    public String index;
    public String type;
    public String description;
    public String repository;
    public String team;

    // Story, Bug, Task
    @Override
    public int compareTo(ReleaseNote o) {
       return this.type.compareTo(o.type);
    }
}
