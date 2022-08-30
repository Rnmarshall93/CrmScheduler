package DAO;

import model.FirstLevelDivision;
import java.util.ArrayList;

/**
 * Interface for a FirstLevelDivision DAO.
 */
public interface IFirstLevelDivisionsDao {

    /**
     * adds a First Level Division.
     * @param firstLevelDivision the FirstLevelDivision to add.
     */
    public void addFirstLevelDivision(FirstLevelDivision firstLevelDivision);

    /**
     * Gets a FirstLevelDivision
     * @param divisionId the Id of the FirstLevelDivision to get.
     * @return returns the found FirstLevelDivision with a matching divisionId.
     */
    public FirstLevelDivision getFirstLevelDivision(int divisionId);

    /**
     * gets all FirstLevelDivisions.
     * @return returns an ArrayList of FirstLevelDivisions.
     */
    public ArrayList<FirstLevelDivision> getAllFirstLevelDivisions();

    /**
     * Updates a division based on its divisionId.
     * @param divisionId the divisionId to match the FirstlevelDivision to.
     * @param firstLevelDivision the new information for the FirstLevelDivision.
     */
    public void updateDivision(int divisionId, FirstLevelDivision firstLevelDivision);

    /**
     * Deletes a FirstLevelDivision
     * @param divisionId the divisionId to delete.
     */
    public void deleteDivision(int divisionId);

}
