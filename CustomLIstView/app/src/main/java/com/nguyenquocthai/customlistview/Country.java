package com.nguyenquocthai.customlistview;

public class Country {
    private String countryName;
    private int countryFlag;
    private int population;

    public Country(String countryName, int countryFlag, int population) {
        this.countryName = countryName;
        this.countryFlag = countryFlag;
        this.population = population;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public int getCountryFlag() {
        return countryFlag;
    }

    public void setCountryFlag(int countryFlag) {
        this.countryFlag = countryFlag;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }
}
