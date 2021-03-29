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
	 * This test is important because is crucial to make sure that we can successfully update the permit of a permit holder.
	 * 
	 */
	@Test
	void updateTest() {
		// Add an test instance 
		Permit_list list = new Permit_list(); 
		Permanent_visitor_permit perm1 = new Permanent_visitor_permit("John", new Vehicle_info("1123"));
		Permanent_visitor_permit perm2 = new Permanent_visitor_permit("John", new Vehicle_info("4421"));
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
	/**
	 * This test check if yearReset() works
	 */
	@Test
	void yearResetTest() {
		Permit_list list = new Permit_list(); 
		// Permanent_visitor_permit instance
		Permanent_visitor_permit perm1 = new Permanent_visitor_permit("John", new Vehicle_info("1123"));
		// Change the attributes to contain values that are not default.
		perm1.setWarnings(1);
		perm1.setNoOfEntries(5);
		perm1.setSuspended(true);
		// add to list
		list.add("John", perm1);
		// Call reset method
		list.yearReset();
		// Check if number of warnings is 0
		assertEquals(0, perm1.getWarnings());
		// Check if number of entries is back to 0
		assertEquals(0, perm1.getNoOfEntries());
		// Check if suspended is false
		assertEquals(false, perm1.isSuspended());
		
	}
}
