package pacsus.testing;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import pacsus.main.Date;
import pacsus.main.Day_visitor_permit;
import pacsus.main.Permanent_visitor_permit;
import pacsus.main.Permit;
import pacsus.main.Permit_list;
import pacsus.main.Regular_visitor_permit;
import pacsus.main.University_member_permit;
import pacsus.main.Vehicle_info;

class Tests {

	/**
	 * This test check if add() method adds permits to the Hashtable
	 */
	@Test
	void addTest() {
		// Check if add() adds successfully
		// Add a test instance 
		Permit_list list = new Permit_list(); 
		Permanent_visitor_permit perm1 = new Permanent_visitor_permit("John", new Vehicle_info("1123"));
		assertEquals(true, list.add("John", perm1));

		// Check if add() returns false if key already exists on hashtable
		assertEquals(false, list.add("John", perm1));
	}
	
	
	/**
	 * This test if populateList() method returns a String array of permits in Permit_list
	 */
	@Test
	void populateListTest() {

		Permit_list list = new Permit_list(); 
		Permanent_visitor_permit perm1 = new Permanent_visitor_permit("John", new Vehicle_info("1123"));
		Permanent_visitor_permit perm2 = new Permanent_visitor_permit("Mike", new Vehicle_info("4421"));

		// Array to check
		String[] checkArray =  new String[2];

		// Add 2 test instances
		list.add("John", perm1);
		list.add("Mike", perm2);
		// add 2 instance to array
		checkArray[0] = perm1.toString();
		checkArray[1] = perm2.toString();
		// check if the check array and the returning array are the same
		assertArrayEquals(checkArray, list.populateList());

	}

	
	/**
	 * This test check if warnings() successfully adds a warning when type==1
	 * and also checks if when warnings are 3 doesn't go over it
	 * and also checks if it's possible to delete a warning if type==-1
	 */
	@Test
	void warningsTest() {

		Permit_list list = new Permit_list(); 
		Permanent_visitor_permit perm1 = new Permanent_visitor_permit("John", new Vehicle_info("1123"));
		// add a test instance to list
		list.add("John", perm1);

		list.warnings("John", 1);
		// check if warning was added
		assertEquals(1, perm1.getWarnings());

		// Add 3 more warnings so it goes above 3 warnings
		list.warnings("John", 1);
		list.warnings("John", 1);
		list.warnings("John", 1);
		// it should stay on 3
		assertEquals(3, perm1.getWarnings());

		// Check if we can delete a warning
		list.warnings("John", -1);
		// Check if it has gone down by one
		assertEquals(2, perm1.getWarnings());

	}
	
	
	/**
	 * This test checks if when unsuspendedPermit() is called, 
	 * it successfully sets the warnings back to 0. 
	 * If warnings are less than 3, it checks if it didn't set back to 0
	 * 
	 */
	@Test
	void unsuspendPermitTest() {
		Permit_list list = new Permit_list(); 
		Permanent_visitor_permit perm1 = new Permanent_visitor_permit("John", new Vehicle_info("1123"));
		// add a test instance to list
		list.add("John", perm1);

		// Add 2 warnings
		list.warnings("John", 1);
		list.warnings("John", 1);
		
		list.unsuspendPermit("John");
		// Check if it didn't set back to 0
		assertEquals(2, perm1.getWarnings());
		
		// add 1 more
		list.warnings("John", 1);
		// Unsuspended permit == 0 warnings
		list.unsuspendPermit("John");
		// Check if warnings are 0
		assertEquals(0, perm1.getWarnings());
		
	}

	/**
	 * This test check if resetWarning resets the count of warnings back to 0
	 * This method is called when a yearReset is called which makes everything
	 * reset.
	 */
	@Test
	void resetWarningsTest() {
		Permit_list list = new Permit_list(); 
		Permanent_visitor_permit perm1 = new Permanent_visitor_permit("John", new Vehicle_info("1123"));
		// add a test instance to list
		list.add("John", perm1);

		// Add 2 warnings
		list.warnings("John", 1);
		list.warnings("John", 1);
		// reset all warnings
		list.resetWarnings("John");
		// Check if now is 0
		assertEquals(0, perm1.getWarnings());
	}

	/**
	 * This test is important because is crucial to make sure that we can successfully update the permit of a permit holder.
	 * 
	 */
	@Test
	void updateTest() {

		Permit_list list = new Permit_list(); 
		Permanent_visitor_permit perm1 = new Permanent_visitor_permit("John", new Vehicle_info("1123"));
		Permanent_visitor_permit perm2 = new Permanent_visitor_permit("John", new Vehicle_info("4421"));
		// Add a test instances
		list.add("John", perm1);
		// Change the permit with update
		list.update("John", perm2);
		// Check if we get the same permit as the one we updated with
		assertEquals(list.getPermit("John"), perm2);
	}


	/**
	 * This test check if deltePermit() works 
	 * 
	 */
	@Test
	void deltePermitTest() {
		// Add an test instance 
		Permit_list list = new Permit_list(); 
		Permanent_visitor_permit perm1 = new Permanent_visitor_permit("John", new Vehicle_info("1123"));
		list.add("John", perm1);
		// delete new permit
		list.deletePermit("John");
		// assert for null
		assertEquals(null, list.getPermit("John"));
	}

	/**
	 * This test check if getPermitsByType() works
	 */
	@Test
	void getPermitsByTypeTest() {
		Permit_list list = new Permit_list(); 
		// Permanent_visitor_permit instance
		Permanent_visitor_permit perm1 = new Permanent_visitor_permit("John", new Vehicle_info("1123"));
		list.add("John", perm1);
		// Day_visitor_permit
		Day_visitor_permit perm2 = new Day_visitor_permit("Martin", new Vehicle_info("5521"), "Jonathan", new Date(2));
		list.add("Martin", perm2);
		// University_member_permit
		University_member_permit perm3 = new University_member_permit("Greig",  new Vehicle_info("8753"), new Date(5));
		list.add("Greig", perm3);
		// Regular_visitor_permit
		Regular_visitor_permit perm4 = new Regular_visitor_permit("Tomas", new Vehicle_info("76841"), "Andre",  new Date(1),  new Date(5));
		list.add("Tomas", perm4);

		// Check if type Permanent_visitor_permit type works
		ArrayList<Permit> arrayList = new ArrayList<Permit>();
		arrayList.add(perm1);
		assertEquals(list.getPermitsByType("Permanent_visitor_permit"), arrayList);

		// Check if type Day_visitor_permit type works
		arrayList.clear();
		arrayList.add(perm2);
		assertEquals(list.getPermitsByType("Day_visitor_permit"), arrayList);

		// Check if type University_member_permit type works
		arrayList.clear();
		arrayList.add(perm3);
		assertEquals(list.getPermitsByType("University_member_permit"), arrayList);

		// Check if type Regular_visitor_permit type works
		arrayList.clear();
		arrayList.add(perm4);
		assertEquals(list.getPermitsByType("Regular_visitor_permit"), arrayList);

	}
	
	@Test
	void setPermitsHasEnteredTest() {
		Permit_list list = new Permit_list(); 
		Permanent_visitor_permit perm1 = new Permanent_visitor_permit("John", new Vehicle_info("1123"));
		// add a test instance to list
		list.add("John", perm1);
		// Set enteredToday to true
		perm1.setEnteredToday(true);
		// reset to false
		list.setPermitsHasEntered();
		
		assertEquals(false, perm1.isEnteredToday());
	}
}
