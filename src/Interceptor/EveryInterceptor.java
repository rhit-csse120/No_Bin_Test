package Interceptor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class EveryInterceptor {
	
	// Add into csv file
	static private final String filepath = "src/testSupport/";
	static private final String csvFilename = "supportData.csv";
	// This array list will capture the 
	ArrayList<String> data = new ArrayList<String>();
	
	// Wrapping around the Junit test -> To get the name of Test 
	@Around("@annotation(org.junit.Test)")
	public Object logTest(ProceedingJoinPoint joinPoint) throws Throwable {
		// Get the method name and class name being intercepted
		String methodName = joinPoint.getSignature().getName();
		String className = joinPoint.getSignature().getDeclaringTypeName();

		// Log the method name
		data.add("Executing @Test method: " + className + "." + methodName + '\n');
		// Proceed with the actual test method execution and capture the result
		Object result = null;
		try {
			result = joinPoint.proceed(); // Call the intercepted method -> this method would implement the function inside the test
			data.add("\n");         // Adding extra space for formating
			exportResults(data);    // Save into the csv file
			System.out.println(data);
			data.clear();          // After each test we will have clear data for upcoming test
		} catch (Throwable throwable) {
			data.add("\n");        // Still saving in case students code fail
			exportResults(data);
			System.out.println(data);
			data.clear();
			throw throwable; // Ensure that exceptions are re-thrown
		} 

		return result;
	}

	/* Intercept the execution of any public methods in any class except the current class 
	 (it would mess up data if do not exclude) and also exclude the @annotation(org.junit.Test) 
	 so that we would not the test name again
	*/
	@Around("(execution(public * *(..)) || execution(static * *(..))) && !within(Interceptor.EveryInterceptor) && !@annotation(org.junit.Test)")
	public Object logTestMethod(ProceedingJoinPoint joinPoint) throws Throwable {
		// Get the file and method name
		String methodName = joinPoint.getSignature().getName();
		String className = joinPoint.getSignature().getDeclaringTypeName();

		data.add("Executing method: " + className + "." + methodName + '\n');
		// This method would help to get the parameters of the test cases
		Object[] args = joinPoint.getArgs();
		if (args.length > 0) {
			data.add("Arguments: " + argsToString(args)+"       ");
		}

		// Proceed with the actual method execution and capture the result
		Object result = joinPoint.proceed();
		data.add("Result of student code at" + methodName + " is: " + result+"      ");

		return result;
	}

	// Intercept any calls to JUnit assertions (assertTrue, assertFalse, assertEquals)
	@Around("call(static void org.junit.Assert.assertTrue(..)) || call(static void org.junit.Assert.assertFalse(..)) || call(static void org.junit.Assert.assertEquals(..))")
	public Object interceptJUnitAssertions(ProceedingJoinPoint joinPoint) throws Throwable {
		Object[] args = joinPoint.getArgs();
		// Proceed with the assertion and capture the result
		Object result = null;
		data.add("Result of student code at the Assertion: " + args[1]+"     ");
		try {
			result = joinPoint.proceed();
			// If no exception, the assertion passed
			data.add("Assertion passed: " + args[0] + "     "+'\n');
		} catch (AssertionError e) {
			// If an AssertionError is thrown, the assertion failed
			data.add("Assertion failed: " + args[0] + '\n');
			throw e; // rethrow the error so the test still fails as expected
		}

		return result;
	}

	// Convert the array into a readable string 
	private String argsToString(Object[] args) {
		StringBuilder str = new StringBuilder();
		for (int i = 0; i < args.length; i++) {
			str.append("Argument ").append(i).append(": ").append(String.valueOf(args[i])).append(", ");
		}
		return str.toString();
	}
	
	// Method to print to the file 
	private void exportResults(ArrayList<String> output) {

		try {
			File myObj = new File(filepath + csvFilename);
			myObj.createNewFile();

			try {
				FileOutputStream fos = new FileOutputStream(filepath + csvFilename, true);
				String outputString = output.toString().substring(1, output.toString().length() - 1) + "\n";

				// Software that does post processing of .csv file requires that there be no
				// spaces after the commas
				outputString = outputString.replace(",  ", ",");
				outputString = outputString.replace(", ", ",");

				fos.write(outputString.getBytes());
				fos.close();

			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}

}
