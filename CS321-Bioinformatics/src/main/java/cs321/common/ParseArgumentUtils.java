package cs321.common;

import cs321.common.ParseArgumentException;

public class ParseArgumentUtils
{
    /**
     * Verifies if lowRangeInclusive <= argument <= highRangeInclusive
     */
    public static void verifyRanges(int argument, int lowRangeInclusive, int highRangeInclusive) throws ParseArgumentException
    {
    	if (argument<lowRangeInclusive) {
    		throw new ParseArgumentException("Argument is smaller than lowRange");
    		
    	}
    	if(argument>highRangeInclusive) {
    		throw new ParseArgumentException("Argument is bigger that highRange");
    	}
    	
    	
    }

    public static int convertStringToInt(String argument) throws ParseArgumentException
    {
    	 
    	try {
    		int retVal = Integer.parseInt(argument);//convert string argument to integer
    		return retVal;
    	}
    	catch(NumberFormatException e) {
    		throw new ParseArgumentException("Argument Exception");
    	}
    		
    		
        
    }
}
