package DAO;

import model.Contact;
import model.Country;

import java.util.ArrayList;

/**
 * Interface for a country Dao.
 */
public interface ICountriesDao {

    /**
     * Adds a country.
     * @param c the country to add.
     */
    public void addCountry(Country c);

    /**
     * Gets a country.
     * @param countryId the id of the country.
     * @return returns the found country.
     */
    public Country getCountry(int countryId);

    /**
     * Gets all countries.
     * @return returns an ArrayList of Countries.
     */
    public ArrayList<Country> getAllCountries();

    /**
     * Updates a country based on Id.
     * @param countryId the countryId of the country to find.
     * @param newCountry the updated country info.
     */
    public void updateCountry(int countryId, Country newCountry);

    /**
     * Deletes a country from the database.
     * @param countryId the countryId of the country to delete.
     */
    public void deleteCountry(int countryId);

}
