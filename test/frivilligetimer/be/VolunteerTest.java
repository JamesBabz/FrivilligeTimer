/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frivilligetimer.be;

import java.util.Calendar;
import java.util.Date;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author James
 */
public class VolunteerTest
{

    public VolunteerTest()
    {
    }

    /**
     * Test of getInactiveSinceString method, of class Volunteer.
     */
    @Test
    public void testGetInactiveSinceString()
    {

        Calendar cal = Calendar.getInstance();

        Date date = cal.getTime();

        System.out.println("getInactiveSinceString");
        Volunteer instance = new Volunteer(0, "Hans", "Jensen", "12345678", "hans@jensen.dk", "pref", "note", null, null);
        String expResult = "";
        String result = instance.getInactiveSinceString();
        assertEquals(expResult, result);
    }

    /**
     * Test of setInactiveSinceString method, of class Volunteer.
     */
    @Test
    public void testSetInactiveSinceString()
    {
        System.out.println("setInactiveSinceString");
        String value = "21-12-2010";
        Volunteer instance = new Volunteer(0, "Hans", "Jensen", "12345678", "hans@jensen.dk", "pref", "note", null, null);
        instance.setInactiveSinceString(value);
        assertEquals(value, instance.getInactiveSinceString());
    }

    /**
     * Test of getPreference method, of class Volunteer.
     */
    @Test
    public void testGetPreference()
    {
        System.out.println("getPreference");
        Volunteer instance = new Volunteer(0, "Hans", "Jensen", "12345678", "hans@jensen.dk", "pref", "note", null, null);
        String expResult = "pref";
        String result = instance.getPreference();
        assertEquals(expResult, result);
    }

    /**
     * Test of setPreference method, of class Volunteer.
     */
    @Test
    public void testSetPreference()
    {
        System.out.println("setPreference");
        String preference = "new pref";
        Volunteer instance = new Volunteer(0, "Hans", "Jensen", "12345678", "hans@jensen.dk", "pref", "note", null, null);
        instance.setPreference(preference);
        assertEquals(preference, instance.getPreference());
    }

    /**
     * Test of getNote method, of class Volunteer.
     */
    @Test
    public void testGetNote()
    {
        System.out.println("getNote");
        Volunteer instance = new Volunteer(0, "Hans", "Jensen", "12345678", "hans@jensen.dk", "pref", "note", null, null);
        String expResult = "note";
        String result = instance.getNote();
        assertEquals(expResult, result);
    }

    /**
     * Test of setNote method, of class Volunteer.
     */
    @Test
    public void testSetNote()
    {
        System.out.println("setNote");
        String note = "new note";
        Volunteer instance = new Volunteer(0, "Hans", "Jensen", "12345678", "hans@jensen.dk", "pref", "note", null, null);
        instance.setNote(note);
        assertEquals(note, instance.getNote());
    }

    /**
     * Test of toString method, of class Volunteer.
     */
    @Test
    public void testToString()
    {
        System.out.println("toString");
        Volunteer instance = new Volunteer(0, "Hans", "Jensen", "12345678", "hans@jensen.dk", "pref", "note", null, null);
        String expResult = "Hans Jensen";
        String result = instance.toString();
        assertEquals(expResult, result);
    }

    /**
     * Test of getHoursInCurrentPeriod method, of class Volunteer.
     */
    @Test
    public void testGetHoursInCurrentPeriod()
    {
        System.out.println("getHoursInCurrentPeriod");
        Volunteer instance = new Volunteer(0, "Hans", "Jensen", "12345678", "hans@jensen.dk", "pref", "note", null, null);
        int expResult = 0;
        int result = instance.getHoursInCurrentPeriod();
        assertEquals(expResult, result);
    }

    /**
     * Test of setHoursInCurrentPeriod method, of class Volunteer.
     */
    @Test
    public void testSetHoursInCurrentPeriod()
    {
        System.out.println("setHoursInCurrentPeriod");
        int value = 10;
        Volunteer instance = new Volunteer(0, "Hans", "Jensen", "12345678", "hans@jensen.dk", "pref", "note", null, null);
        instance.setHoursInCurrentPeriod(value);
        assertEquals(value, instance.getHoursInCurrentPeriod());
    }

}
