package cs321.create;

import cs321.btree.BTree;
import cs321.common.ParseArgumentException;

import java.io.*;
import java.util.List;

public class GeneBankCreateBTree
{

    public static void main(String[] args) throws Exception
    {
        System.out.println("Hello world from cs321.create.GeneBankCreateBTree.main");
        GeneBankCreateBTreeArguments geneBankCreateBTreeArguments = parseArguments(args);


    }

//    private static GeneBankCreateBTreeArguments parseArgumentsAndHandleExceptions(String[] args)
//    {
//        GeneBankCreateBTreeArguments geneBankCreateBTreeArguments = null;
//        try 
//        {
//            geneBankCreateBTreeArguments = parseArguments(args);
//        }
//        catch (Exception e)
//        {
//            printUsageAndExit(e.getMessage());
//        }
//        return geneBankCreateBTreeArguments;
//    }

    private static void printUsageAndExit(String errorMessage)
    {
    	System.out.println(errorMessage);
    	System.out.println("Usage: See README.md, section 5.1");
        System.exit(1);
    }

    public static GeneBankCreateBTreeArguments parseArguments(String[] args)
    {
    	int cacheStatusInt = Integer.parseInt(args[0]);
    	boolean cacheStatus = false;
    	if (cacheStatusInt == 1) {
    		cacheStatus = true;
    	} else if (cacheStatusInt == 0) {
    		cacheStatus = false;
    	} else {
    		printUsageAndExit("Cache Error");
    	}
    	
    	int degree = Integer.parseInt(args[1]);
    	if (degree < 0) {
    		printUsageAndExit("Degree is less than zero");
    	}
    	
    	String gbkFile = args[2];
    	if (!gbkFile.contains(".gbk")) {
    		printUsageAndExit("File does not end in .gbk");
    	}
    	
    	int subsequenceLength = Integer.parseInt(args[3]);
    	if(subsequenceLength > 31 || subsequenceLength < 1) {
    		printUsageAndExit("SubsequenceLength is out of range");
    	}
    	
    	int cacheSize = 100;
    	if (args.length >= 5) {
	    	cacheSize = Integer.parseInt(args[4]);
	    	if(cacheSize >500 || cacheSize <100) {
	    		printUsageAndExit("Cachesize is out of range");
	    	}
    	}
    	
    	int debugLevel = 0;
    	if (args.length >= 6) {
	    	debugLevel = Integer.parseInt(args[5]);
	    	if(debugLevel != 0 && debugLevel != 1) {
	    		printUsageAndExit("DebugLevel error");
	    	}
    	}
    	
    	GeneBankCreateBTreeArguments treeArgs = new GeneBankCreateBTreeArguments(cacheStatus, degree, gbkFile, subsequenceLength, cacheSize, debugLevel);
    	
        return treeArgs;

    }

}
