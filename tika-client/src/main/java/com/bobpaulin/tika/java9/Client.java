package com.bobpaulin.tika.java9;

import java.io.BufferedReader;
import java.io.File;
import java.io.Reader;

import org.apache.tika.Tika;

public class Client {
	public static void main(String[] args) throws Exception {
		
		if(args.length != 1)
		{
			System.out.println("Please enter a file to parse");
			return;
		}
		
		Tika tika = new Tika();
		
		Reader tikaReader = tika.parse(new File(args[0]));
		
		BufferedReader br = new BufferedReader(tikaReader);
		
		String line;
		
		while( (line = br.readLine()) != null )
		{
			System.out.println(line);
		}
		System.out.println("Done");
	}

}
