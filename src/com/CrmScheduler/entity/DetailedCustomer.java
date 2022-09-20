package com.CrmScheduler.entity;

import com.CrmScheduler.DAO.FirstLevelDivisionsDaoImplSql;
import com.CrmScheduler.DAO.IFirstLevelDivisionsDao;
import com.CrmScheduler.HelperUtilties.TimeTools;
import javafx.collections.ObservableList;

/**
 * Extended version of the Customer model. Allows for keeping the Customer's division (name of their division) without knowing anything else about the customer. Useful for filtering lists using lambdas and displaying information in a table.
 * This class also maps 1:1 to the table used in CustomerManagerController.
 * @see com.CrmScheduler.controller.CustomerManagerController#buildTable(ObservableList) (ObservableList)
 */
public class DetailedCustomer extends Customer {

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }
    private String division;
    private int divisionId;

    public int getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    public DetailedCustomer(Customer c)
    {
        TimeTools timeTools = new TimeTools();

        setCustomerId(c.getCustomerId());
        setCustomerName(c.getCustomerName());
        setAddress(c.getAddress());
        setPostalCode(c.getPostalCode());
        setPhone(c.getPhone());
        setCreateDate(timeTools.ConvertUtcToSystemTime(c.getCreateDate()));
        setCreatedBy(c.getCreatedBy());
        setLastUpdate(timeTools.ConvertUtcToSystemTime(c.getLastUpdate()));
        setLastUpdatedBy(c.getLastUpdatedBy());
        setDivisionId(c.getFirstLevelDivision().getDivisionId());
        setDivision(c.getFirstLevelDivision().getDivision());

    }


}
