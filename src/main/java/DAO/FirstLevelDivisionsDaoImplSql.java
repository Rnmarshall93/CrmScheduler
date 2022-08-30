package DAO;

import Utilities.ConnectionFactory;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import model.FirstLevelDivision;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Implementation in sql for the FirstLevelDivision DAO
 */
public class FirstLevelDivisionsDaoImplSql implements IFirstLevelDivisionsDao {

    /**
     * Adds a new first level division to the database Unused but still implemented.
     * @param newFirstLevelDivision the new first level division to add.
     */
    @Override
    public void addFirstLevelDivision(FirstLevelDivision newFirstLevelDivision) {

        Connection dbConn = ConnectionFactory.getSqlConnection();

        String query = "INSERT INTO first_level_divisions (Division, Create_Date, Created_By, Last_Update, LAst_Updated_By, Country_ID) " +
                "values (?,?,?,?,?,?)";

        try
        {
            PreparedStatement preparedStatement = dbConn.prepareStatement(query);
            preparedStatement.setString(1, newFirstLevelDivision.getDivision());
            preparedStatement.setDate(2, newFirstLevelDivision.getCreateDate());
            preparedStatement.setString(3, newFirstLevelDivision.getCreatedBy());
            preparedStatement.setDate(4, newFirstLevelDivision.getLastUpdate());
            preparedStatement.setString(5, newFirstLevelDivision.getLastUpdatedBy());
            preparedStatement.setInt(6, newFirstLevelDivision.getCountyId());
            preparedStatement.execute();

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR,e.getLocalizedMessage(), ButtonType.OK);
            alert.showAndWait();
        }

    }

    /**
     * Gets a FirstLevelDivision based off its unique divisionId
     * @param divisionId the unique divisionId to find
     * @return returns the matching first level division.
     */
    @Override
    public FirstLevelDivision getFirstLevelDivision(int divisionId) {
        try{

            Connection dbConn = ConnectionFactory.getSqlConnection();

            String query = "SELECT Division_Id, Division, Create_Date, Created_By, Last_Update, LAst_Updated_By, Country_ID" +
                    " from first_level_divisions where Division_ID = ?";


            PreparedStatement preparedStatement = dbConn.prepareStatement(query);
            preparedStatement.setInt(1, divisionId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                FirstLevelDivision  firstLevelDivision = new FirstLevelDivision();
                firstLevelDivision.setDivisionId(resultSet.getInt(1));
                firstLevelDivision.setDivision(resultSet.getString(2));
                firstLevelDivision.setCreateDate(resultSet.getDate(3));
                firstLevelDivision.setCreatedBy(resultSet.getString(4));
                firstLevelDivision.setLastUpdate(resultSet.getDate(5));
                firstLevelDivision.setLastUpdatedBy(resultSet.getString(6));
                firstLevelDivision.setCountyId(resultSet.getInt(7));
                dbConn.close();
                return firstLevelDivision;
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
     * Gets all first level divisions from the database.
     * @return returns an array list of FirstLevelDivision types
     */
    @Override
    public ArrayList<FirstLevelDivision> getAllFirstLevelDivisions() {
        try {


            Connection dbConn = ConnectionFactory.getSqlConnection();

            String query = "SELECT Division_Id, Division, Create_Date, Created_By, Last_Update, LAst_Updated_By, Country_ID " +
                    "from first_level_divisions";


            PreparedStatement preparedStatement = dbConn.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<FirstLevelDivision> foundFirstLevelDivisions = new ArrayList<>();

            while(resultSet.next())
            {
                FirstLevelDivision firstLevelDivision = new FirstLevelDivision();
                firstLevelDivision.setDivisionId(resultSet.getInt(1));
                firstLevelDivision.setDivision(resultSet.getString(2));
                firstLevelDivision.setCreateDate(resultSet.getDate(3));
                firstLevelDivision.setCreatedBy(resultSet.getString(4));
                firstLevelDivision.setLastUpdate(resultSet.getDate(5));
                firstLevelDivision.setLastUpdatedBy(resultSet.getString(6));
                firstLevelDivision.setCountyId(resultSet.getInt(7));
                foundFirstLevelDivisions.add(firstLevelDivision);
            }
            dbConn.close();

            return foundFirstLevelDivisions;
        }
        catch(Exception ex)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, ex.getLocalizedMessage(), ButtonType.OK);
            alert.showAndWait();
            return null;
        }
    }


    /**
     * Updates a firstLevelDivision with new information from the firstLevelDivision param. Unused but implemented.
     * @param divisionId the unique id of the division to find and update.
     * @param firstLevelDivision the updated firstLevelDivision
     */
    @Override
    public void updateDivision(int divisionId, FirstLevelDivision firstLevelDivision) {
        try {

            Connection dbConn = ConnectionFactory.getSqlConnection();

            String query = "update First_Level_Divisions set Division_Id = ?, Division = ?, Create_Date = ?, Created_By = ?, Last_Update = ?, Last_Updated_By = ?, Country_ID = ? " +
                    "where Division_ID = ?";
            PreparedStatement preparedStatement = dbConn.prepareStatement(query);

            preparedStatement.setInt(1, firstLevelDivision.getDivisionId());
            preparedStatement.setString(2, firstLevelDivision.getDivision());
            preparedStatement.setDate(3, firstLevelDivision.getCreateDate());
            preparedStatement.setString(4, firstLevelDivision.getCreatedBy());
            preparedStatement.setDate(5, firstLevelDivision.getLastUpdate());
            preparedStatement.setString(6, firstLevelDivision.getLastUpdatedBy());
            preparedStatement.setInt(7, firstLevelDivision.getCountyId());
            preparedStatement.setInt(8, divisionId);
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
     * Deletes a FirstLevelDivision. Unused but implemented.
     * @param divisionId the unique divisionId to match and delete a FirstLevelDivision with.
     */
    @Override
    public void deleteDivision(int divisionId) {

        Connection dbConn = ConnectionFactory.getSqlConnection();

        try{
            String query = "Delete from first_level_divisions where ? = Division_ID";
            PreparedStatement preparedStatement = dbConn.prepareStatement(query);
            preparedStatement.setInt(1, divisionId);
            preparedStatement.execute();
        }
        catch (Exception ex)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, ex.getLocalizedMessage(), ButtonType.OK);
            alert.showAndWait();
        }
    }
}
