package com.CrmScheduler.DAO;

import com.CrmScheduler.entity.Country;
import com.CrmScheduler.entity.FirstLevelDivision;

import java.util.ArrayList;

/**
 * Interface for a country Dao.
 */
public interface ICountriesDao {

    /**
     * Adds a country.
     * @param c the country to add.
     */
    void addCountry(Country c);

    /**
     * Gets a country.
     * @param countryId the id of the country.
     * @return returns the found country.
     */
    Country getCountry(int countryId);

    /**
     * Gets all countries.
     * @return returns an ArrayList of Countries.
     */
    ArrayList<Country> getAllCountries();


    Country getCountryMatchingName(String countryName);

    /**
     * Updates a country based on Id.
     * @param countryId the countryId of the country to find.
     * @param newCountry the updated country info.
     */
    void updateCountry(int countryId, Country newCountry);

    /**
     * Deletes a country from the database.
     * @param countryId the countryId of the country to delete.
     */
    void deleteCountry(int countryId);

}
