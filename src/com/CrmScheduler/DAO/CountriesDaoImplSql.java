package com.CrmScheduler.DAO;

import com.CrmScheduler.HibernateConf;
import com.CrmScheduler.entity.Country;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * Implementation in sql of the Countries com.CrmScheduler.DAO
 */
@Component
public class CountriesDaoImplSql implements ICountriesDao {
    /**
     * Adds a new country to the database
     * @param newCountry the newCountry to add to the database.
     */
    @Override
    public void addCountry(Country newCountry) {

        try
        {
            Session session = HibernateConf.getSessionFactory().openSession();
            session.beginTransaction();

            session.save(newCountry);

            session.getTransaction().commit();
            session.close();

        } catch (Exception ex) {

            Alert alert = new Alert(Alert.AlertType.ERROR, ex.getLocalizedMessage(), ButtonType.OK);
            alert.showAndWait();
        }

    }

    /**
     * Gets a country based off its unique Id.
     * @param countryId the unique countryId.
     * @return returns the country with the matching countryId.
     * Unused but still implemented.
     */
    @Override
    public Country getCountry(int countryId) {
        try{
            Session session = HibernateConf.getSessionFactory().openSession();
            session.beginTransaction();

            Country foundCountry = session.get(Country.class, countryId);

            session.getTransaction().commit();
            session.close();
            return foundCountry;

        }

        catch(Exception ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR, ex.getLocalizedMessage(), ButtonType.OK);
            alert.showAndWait();
        }
        return null;
    }

    /**
     * Gets all countries from the database
     * @return returns an ArrayList of Country objects.
     */
    @Override
    public ArrayList<Country> getAllCountries() {
        try {
            Session session = HibernateConf.getSessionFactory().openSession();
            session.beginTransaction();

            ArrayList<Country> allCountries = new ArrayList<>();
            Query query = session.createQuery("from Country");
            allCountries.addAll(query.list());
            session.getTransaction().commit();
            session.close();
            return allCountries;
        }
        catch(Exception ex)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, ex.getLocalizedMessage(), ButtonType.OK);
            alert.showAndWait();
            return null;
        }
    }

    /**
     * Updates the country based on its unique countryId and newCountry values. Never used but implemented.
     * @param countryId the unique country Id.
     * @param newCountry the updated country information
     */
    @Override
    public void updateCountry(int countryId, Country newCountry) {
        try {

            Session session = HibernateConf.getSessionFactory().openSession();
            session.beginTransaction();

            session.update(newCountry);

            session.getTransaction().commit();
            session.close();

        }
        catch (Exception ex)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, ex.getLocalizedMessage(), ButtonType.OK);
            alert.showAndWait();
        }
    }

    /**
     * Deletes the country from the database. Never used but implemented.
     * @param countryId the unique country ID of the country to be deleted
     */
    @Override
    public void deleteCountry(int countryId) {


        try{
            Session session = HibernateConf.getSessionFactory().openSession();
            session.beginTransaction();

            Country foundCoutry = session.get(Country.class, countryId);

            session.delete(foundCoutry);

            session.getTransaction().commit();
            session.close();
        }
        catch (Exception ex)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, ex.getLocalizedMessage(), ButtonType.OK);
            alert.showAndWait();
        }

    }
}
