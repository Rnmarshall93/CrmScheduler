package com.CrmScheduler.HelperUtilties;

import com.CrmScheduler.entity.Appointment;

/**
 * singleton class responsible for keeping track of the impending appointment that is assigned during a successful login. This provides a convenient way to track the impending appointment across
 * all forms without worrying about passing it back and forth from one form to another.
 */
public class ImpendingAppointmentSingleton {

    /**
     * The class is originally null.
     */
  private static ImpendingAppointmentSingleton instance = null;


    /**
     * represents an appointment that is found to be impending.
     */
    private Appointment foundAppointment;

    /**
     * getter for the foundAppointment.
     * @return foundAppointment
     */
    public Appointment getFoundAppointment() {
        return foundAppointment;
    }

    /**
     * setter for the foundAppointment
     * @param foundAppointment the foundAppointment to set the value to.
     */
    public void setFoundAppointment(Appointment foundAppointment) {
        this.foundAppointment = foundAppointment;
    }

    /**
     * this class is a singleton, so the constructor is private so it can never be implemented as a new object.
     */
  private ImpendingAppointmentSingleton(){}

    /**
     * Sets up the instance of this class if it doesn't exist. If it does, returns the already existing instance.
     * @return the existing instance or a new one if it doesn't exist.
     */
  public static ImpendingAppointmentSingleton getInstance()
  {
      if(instance == null)
          instance = new ImpendingAppointmentSingleton();

      return instance;
  }


}
