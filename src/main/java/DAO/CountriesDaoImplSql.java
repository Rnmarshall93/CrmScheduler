package DAO;

import Utilities.ConnectionFactory;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import model.Country;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Implementation in sql of the Countries DAO
 */
public class CountriesDaoImplSql implements ICountriesDao {
    /**
     * Adds a new country to the database
     * @param newCountry the newCountry to add to the database.
     */
    @Override
    public void addCountry(Country newCountry) {

        Connection dbConn = ConnectionFactory.getSqlConnection();

        String query = "INSERT INTO countries (Country, Create_Date, Created_By, Last_Update, Last_Updated_By) " +
                "values (?,?,?,?,?)";

        try
        {
            PreparedStatement preparedStatement = dbConn.prepareStatement(query);

            preparedStatement.setString(1, newCountry.getCountry());
            preparedStatement.setDate(2, newCountry.getCreateDate());
            preparedStatement.setString(3, newCountry.getCreatedBy());
            preparedStatement.setDate(4, newCountry.getLastUpdate());
            preparedStatement.setString(5, newCountry.getLastUpdatedBy());

            preparedStatement.execute();

        } catch (SQLException ex) {

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

            Connection dbConn = ConnectionFactory.getSqlConnection();

            String query = "SELECT Country_ID, Country, Create_Date, Created_By, Last_Update, Last_Updated_By" +
                    " from Countries where Country_ID = ?";


            PreparedStatement preparedStatement = dbConn.prepareStatement(query);
            preparedStatement.setInt(1, countryId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                Country country = new Country();
                country.setCountryId(resultSet.getInt(1));
                country.setCountry(resultSet.getString(2));
                country.setCreateDate(resultSet.getDate(3));
                country.setCreatedBy(resultSet.getString(4));
                country.setLastUpdate(resultSet.getDate(5));
                country.setLastUpdatedBy(resultSet.getString(6));
                dbConn.close();
                return country;
            }
            dbConn.close();
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


            Connection dbConn = ConnectionFactory.getSqlConnection();

            String query = "SELECT Country_ID, Country, Create_Date, Created_By, Last_Update, Last_Updated_By " +
                    "from Countries";

            PreparedStatement preparedStatement = dbConn.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();


            ArrayList<Country> foundCountries = new ArrayList<>();
            while(resultSet.next())
            {
                Country country = new Country();
                country.setCountryId(resultSet.getInt(1));
                country.setCountry(resultSet.getString(2));
                country.setCreateDate(resultSet.getDate(3));
                country.setCreatedBy(resultSet.getString(4));
                country.setLastUpdate(resultSet.getDate(5));
                country.setLastUpdatedBy(resultSet.getString(6));
                foundCountries.add(country);
            }
            dbConn.close();

            return foundCountries;
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

            Connection dbConn = ConnectionFactory.getSqlConnection();

            String query = "update Countries set Country = ?, Create_Date = ?, Created_By = ?, Last_Update = ?, Last_Updated_By = ? " +
                    "where country_ID = ?";
            PreparedStatement preparedStatement = dbConn.prepareStatement(query);
            preparedStatement.setString(1, newCountry.getCountry());
            preparedStatement.setDate(2, newCountry.getCreateDate());
            preparedStatement.setString(3, newCountry.getCreatedBy());
            preparedStatement.setDate(4, newCountry.getLastUpdate());
            preparedStatement.setString(5, newCountry.getLastUpdatedBy());
            preparedStatement.setInt(6, countryId);
            preparedStatement.execute();
            dbConn.close();

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

        Connection dbConn = ConnectionFactory.getSqlConnection();

        try{
            String query = "Delete from Countries where ? = Country_ID";
            PreparedStatement preparedStatement = dbConn.prepareStatement(query);
            preparedStatement.setInt(1, countryId);
            preparedStatement.execute();
        }
        catch (Exception ex)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, ex.getLocalizedMessage(), ButtonType.OK);
            alert.showAndWait();
        }

    }
}
