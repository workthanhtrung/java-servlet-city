package model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class City {
    protected int id;
    protected String name, nation;
    protected float area, population, gdp;
    protected String description;

    public City() {
    }

    public City(String name, String nation, float area, float population, float gdp, String description) {
        this.name = name;
        this.nation = nation;
        this.area = area;
        this.population = population;
        this.gdp = gdp;
        this.description = description;
    }

    public City(int id, String name, String nation, float area, float population, float gdp, String description) {
        this.id = id;
        this.name = name;
        this.nation = nation;
        this.area = area;
        this.population = population;
        this.gdp = gdp;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NotEmpty
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotEmpty
    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    @NotNull
    @Min(1)
    @Max(Long.MAX_VALUE)
    public float getArea() {
        return area;
    }

    public void setArea(Float area) {
        this.area = area;
    }

    @NotNull
    @Min(1)
    public float getPopulation() {
        return population;
    }

    public void setPopulation(float population) {
        this.population = population;
    }

    @NotNull
    @Min(1)
    public float getGdp() {
        return gdp;
    }

    public void setGdp(float gdp) {
        this.gdp = gdp;
    }

    @NotEmpty
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
