import java.util.Scanner;
import java.io.*;

public class TridactylAsseelMarieanneFINAL {

	public static void main(String args[]) throws IOException{
		Scanner in = new Scanner (System.in);

		System.out.print("WELCOME TO ASSEEL AND MARIE-ANNE'S TRIDACTYL CODE DECODER! \n\nPlease enter the file name you would like to decode: ");

		String file = in.next();
		Scanner inFile = new Scanner(new File(file));
		
		String passwordEntered = inFile.nextLine();
		String masterPassword = buildPasswordCode(passwordEntered);
		
		while(inFile.hasNextLine()){
			String line = inFile.nextLine(); 
			System.out.println(decimalToLetters(base3ToDecimal(decimalToBase3(line, lettersToDecimal(line, masterPassword))), masterPassword));
			
		}
	}

	public static String buildPasswordCode(String password) { 
		StringBuffer alphabet = new StringBuffer("ABCDEFGHIJKLMNOPQRSTUVWXYZ "); 
		StringBuffer newOrder = new StringBuffer("");
		
		for (int i = 0; i < password.length(); i++) {
			if (alphabet.indexOf(String.valueOf(password.charAt(i)).toUpperCase()) == - 1)
				password = password.substring(0, i) + password.substring(i+1);
		}
		
		password = password.toUpperCase();
		
		for (int i = 0;  i < password.length(); i++) { //for loop finds the character at position i and adds it to the new order if its not already in it and updates and deletes letters from the alphabet string used
			if (newOrder.indexOf(Character.toString(password.charAt(i))) == - 1) { //if the letter at i is not already in the new order string
				newOrder.append(password.charAt(i)); //add the letter to new order

				int pos = alphabet.toString().indexOf(password.charAt(i)); //finding the position of the letter just used 
				if (pos != -1)  //if the letter used is still in the alphabet string
					alphabet.deleteCharAt(pos); //remove from string
			}
		}

		newOrder.append(alphabet); //add the remaining letters

		return newOrder.toString().toUpperCase(); //return the new order of letters casting to a string and putting it all in uppercase

	}
	
	public static int [] lettersToDecimal (String line, String masterPassword) {
		int [] decimalNums = new int [line.length()];
		for (int i = 0; i < decimalNums.length; i++) {
			char[] alphabetPass = masterPassword.toCharArray();
			decimalNums[i] = String.valueOf(alphabetPass).indexOf(line.charAt(i));
		}
		
		return decimalNums;
	}
	
	public static int [] decimalToBase3 (String line, int [] decimalNums) {

		int base3Nums [] = new int [decimalNums.length * 3]; //new array which holds the conversions of the 27 decimal nums and isolates the 3 digits of the base 3 number
		int cnt = 0; //counter for decimalNums array

		for (int i = 0; i < base3Nums.length; i += 3) {
			base3Nums[i] = decimalNums[cnt]/9; //finding the first number of the base 3 number at decimal i
			base3Nums[i+1] = decimalNums[cnt]%9/3; //finding the second number of the base 3 number at decimal i
			base3Nums[i+2] = decimalNums[cnt]%9%3; //find the third number of the base 3 number at decimal i
			cnt++;
			
			if(cnt >= line.length())
				break;

		}
		
		return base3Nums;

	}
	
	public static int [] base3ToDecimal (int [] base3Nums) {

		String nineNums = "";

		for (int i = 0; i <= base3Nums.length - 9; i += 9) {
			nineNums = "" + base3Nums[i] + base3Nums[i + 1] + base3Nums[i + 2] + base3Nums[i + 3] + base3Nums[i + 4] + base3Nums[i + 5] + base3Nums[i + 6] + base3Nums[i + 7] + base3Nums[i + 8]; 

			char placeHolder1 = nineNums.charAt(6); //is a placeholder for character at position 6 in order to help switching the most significant digits
			base3Nums[i+6] = base3Nums[i+3]; //switching the digits (between B and C, switch most significant digit)
			base3Nums[i+3] = Integer.parseInt(String.valueOf(placeHolder1)); //switching the second digit (continued, uses the place holder to use the original value of the number before switched)

			char placeHolder2 = nineNums.charAt(7); //is a placeholder for character at position 7 in order to help switch the middle digits
			base3Nums[i+7] = base3Nums[i+1]; //switching the digits (between A and C, switch middle digit)
			base3Nums[i+1] = Integer.parseInt(String.valueOf(placeHolder2)); //switching the second digit (continued, uses the place holder to use the original value of the number before switched)

			char placeHolder3 = nineNums.charAt(5); //is a placeholder for character at position 6 in order to help switching the least significant digits
			base3Nums[i+5] = base3Nums[i+2]; //switching the digits (between A and B, switch the least significant digit)
			base3Nums[i+2] = Integer.parseInt(String.valueOf(placeHolder3)); //switching the second digit (continued, uses the place holder to use the original value of the number before switched)

		}

		int decimalNums[] = new int [base3Nums.length / 3]; //new int array to hold the base 3 nums converted into decimal nums

		for (int i = 0; i < base3Nums.length; i += 3) { //for loop in which loops through all 81 numbers of the base3Nums array and converts them back into decimal and stores it in the decimal array
			decimalNums[i/3] = base3Nums[i] * 9 + base3Nums[i+1] * 3 + base3Nums[i+2]; //stores decimalnums at i/3 since the for loop is going by 3 81 times, so instead it counts 1-27 for the decimal array. Takes the first base 3 num, * 9 to get the decimal number, takes the second base 3 num, * 3 to get the decimal number and then takes the last base 3 number and add all these three values to get the decimal equivalent
		}

		return decimalNums;

	}
	
	public static String decimalToLetters (int [] decimalNums, String masterPassword) {
		String letters = "";
		
		for (int i = 0; i < decimalNums.length; i++) { //for loop which loops 27 times to switch each decimal nums to the corresponding letter
			
			char[] alphabet = masterPassword.toCharArray(); //creates char array filled with all of the alphabet
		    letters += Character.toString(alphabet[decimalNums[i]]); //adds the decimal number converted to the corrresponding letter to the letters string
		}
		
		return letters;
	}
	
	

}