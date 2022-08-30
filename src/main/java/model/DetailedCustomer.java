package model;

import DAO.IFirstLevelDivisionsDao;
import DAO.FirstLevelDivisionsDaoImplSql;
import Utilities.TimeTools;
import javafx.collections.ObservableList;

/**
 * Extended version of the Customer model. Allows for keeping the Customer's division (name of their division) without knowing anything else about the customer. Useful for filtering lists using lambdas and displaying information in a table.
 * This class also maps 1:1 to the table used in CustomerManagerController.
 * @see controller.CustomerManagerController#buildTable(ObservableList) (ObservableList)
 */
public class DetailedCustomer extends Customer {

    public String getDivision() {
        return division;
    }

    private String division;


    public DetailedCustomer(Customer c)
    {
        this.setCustomerId(c.getCustomerId());
        this.setCustomerName(c.getCustomerName());
        this.setAddress(c.getAddress());
        this.setPostalCode(c.getPostalCode());
        this.setPhone(c.getPhone());
        this.setCreateDate(TimeTools.ConvertUtcToSystemTime(c.getCreateDate()));
        this.setCreatedBy(c.getCreatedBy());
        this.setLastUpdate(TimeTools.ConvertUtcToSystemTime(c.getLastUpdate()));
        this.setLastUpdatedBy(c.getLastUpdatedBy());
        this.setDivisionId(c.getDivisionId());
        IFirstLevelDivisionsDao IFirstLevelDivisionsDao = new FirstLevelDivisionsDaoImplSql();
        FirstLevelDivision customersDivision = IFirstLevelDivisionsDao.getFirstLevelDivision(c.getDivisionId());
        this.division = customersDivision.getDivision();
    }


}
