/**
 * 
 */
package sdpsose2018.hrms;

/**
 * @author Deepthi
 *
 */
public class PayRollUtil {
	
	public static double calculateNetSalary(double grossSalary,Double taxRate)
	{
		return (grossSalary - ((grossSalary * taxRate)/ 100)); 	
	}

}
