package com.CrmScheduler.DAO;

import com.CrmScheduler.HibernateConf;
import com.CrmScheduler.entity.Contact;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import com.CrmScheduler.entity.FirstLevelDivision;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Implementation in sql for the FirstLevelDivision com.CrmScheduler.DAO
 */
@Component
public class FirstLevelDivisionsDaoImplSql implements IFirstLevelDivisionsDao {

    /**
     * Adds a new first level division to the database Unused but still implemented.
     * @param newFirstLevelDivision the new first level division to add.
     */
    @Override
    public void addFirstLevelDivision(FirstLevelDivision newFirstLevelDivision) {

        try
        {
            Session session = HibernateConf.getSessionFactory().openSession();
            session.beginTransaction();

            session.save(newFirstLevelDivision);

            session.getTransaction().commit();
            session.close();

        } catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR,ex.getLocalizedMessage(), ButtonType.OK);
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
            Session session = HibernateConf.getSessionFactory().openSession();
            session.beginTransaction();
            FirstLevelDivision foundDivision = session.get(FirstLevelDivision.class, divisionId);
            session.getTransaction().commit();

            session.close();
            return foundDivision;
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

            Session session = HibernateConf.getSessionFactory().openSession();
            session.beginTransaction();

            ArrayList<FirstLevelDivision> foundFirstLevelDivisions = new ArrayList<>();
            Query query = session.createQuery("from FirstLevelDivision");
            foundFirstLevelDivisions.addAll(query.list());

            session.getTransaction().commit();
            session.close();

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

            Session session = HibernateConf.getSessionFactory().openSession();
            session.beginTransaction();

            session.update(firstLevelDivision);

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
     * Deletes a FirstLevelDivision. Unused but implemented.
     * @param divisionId the unique divisionId to match and delete a FirstLevelDivision with.
     */
    @Override
    public void deleteDivision(int divisionId) {

        try{
            Session session = HibernateConf.getSessionFactory().openSession();
            session.beginTransaction();

            FirstLevelDivision foundDivision = session.get(FirstLevelDivision.class, divisionId);
            session.delete(foundDivision);

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
