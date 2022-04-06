import java.io.*;
import java.util.*;

import java.util.Scanner;   //Import the Scanner class

/**
 * 
 * CSCU9T4 Java strings and files exercise.
 *
 */
public class FilesInOut {

    public static void main(String[] args) {
        //Variables
        ArrayList<String[]> partsList = new ArrayList<String[]>();  //an array list that will include all the line parts
        ArrayList<String> processedPartsList = new ArrayList<String>();  //an array list that will include all the processed parts of the line parts
        String[] lineParts; //an array that included the parts of a split string

        //Input variables
        String userInputFile;
        String userOutputFile;
        String userFlag;

        //Files
        File inputFile;
        File outputFIle;

        //Create a scanner object
        Scanner scanner = new Scanner(System.in);

        //Loop until the user gives a valid input file and check if it exists
        while (true){
            //Get user inputs
            System.out.println("Enter your input file: ");
            userInputFile = scanner.nextLine();

            //Assign the input to the file
            inputFile = new File(userInputFile);

            //Check if it exists
            if(inputFile.exists()){
                break;
            }

            //File does not exist
            System.out.println("Input file does not exist. Try again.");
        }

        //Loop until the user gives an output file that is not already created
        while (true){
            //Get user inputs
            System.out.println("Enter your output file: ");
            userOutputFile = scanner.nextLine();

            //Assign the input to the file
            outputFIle = new File(userOutputFile);

            //Check if it exists
            if(outputFIle.exists()){
                System.out.println("Output file already exists. Try again.");
                continue;
            }

            //File is not already created
            break;
        }

        //Loop until the user enters a valid flag
        while(true){
            System.out.println("Enter your flag. Leave blank for default or type -u for upper case: ");
            userFlag = scanner.nextLine();

            //Check if the flag input valid
            if(userFlag.equals("") || userFlag.equals("-u")){
                break;
            }

            //Input is not valid
            System.out.println("Flag input '" + userFlag + "' is not correct. Try again.");
        }

        //Try to open the file
        try {
            FileInputStream fileInputStream = new FileInputStream(inputFile);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));

            String currentLine;

            //Loop though all the file lines until there are no lines
            while ((currentLine = bufferedReader.readLine()) != null){
                //Split the current line and add the split parts into the partsList
                lineParts = currentLine.split(" ");
                partsList.add(lineParts);

                //Print the current line
                System.out.println(currentLine);
            }
        } catch (Exception e){
            System.out.println("File could not be found: " + e.getMessage());
        }

        //Process the line parts
        //Loop though all the part lists elements
        for(int i=0;i<partsList.size();i++){
            String correctedLine = new String();

            //Check if the current selected line part is split into 4 parts which means that the person has a middle initial
            //if the current selected line part is split into 3 parts it means that the person has not a middle initial
            if(partsList.get(i).length == 3){   //current part without a middle initial

                //Loop though all the line parts of the current selected part list element
                for(int c=0;c<partsList.get(i).length-1;c++){   //the parts length is -1 because it will not include the final part which is always the date
                    //Check if the user flag is -u
                    if(userFlag.equals("-u")){
                        correctedLine += partsList.get(i)[c].toUpperCase() + " ";
                    } else {
                        //Separate the first letter of the word
                        String firstLetter = partsList.get(i)[c].substring(0,1);
                        String remainingLetters = partsList.get(i)[c].substring(1,partsList.get(i)[c].length());

                        //Change the first letter to uppercase
                        firstLetter = firstLetter.toUpperCase();

                        //Connect the two strings
                        correctedLine += firstLetter + remainingLetters + " ";
                    }
                }

                //Process the last part which is always the date
                String datePart = (partsList.get(i)[partsList.get(i).length-1]);    //get the last part as a date
                StringBuilder stringBuilder = new StringBuilder(datePart);  //create a string builder to process the date
                stringBuilder.insert(2,"/");    //insert the / symbol at position 2
                stringBuilder.insert(5,"/");    //insert the / symbol at position 5

                correctedLine += stringBuilder;

                processedPartsList.add(correctedLine);  //add the string to the processedPartList array list

            } else if(partsList.get(i).length == 4){    //current part with middle initial

                //Loop though all the line parts of the current selected part list element
                for(int c=0;c<partsList.get(i).length-1;c++){   //the parts length is -1 because it will not include the final part which is always the date
                    //Check if the user flag is -u
                    if(userFlag.equals("-u")){

                        //Check if the current part is the middle initial
                        if(c==1){
                            correctedLine += partsList.get(i)[c].toUpperCase() + ". ";  //add a dot to the end of the middle initial
                        } else {
                            correctedLine += partsList.get(i)[c].toUpperCase() + " ";
                        }
                    } else {
                        //Separate the first letter of the word
                        String firstLetter = partsList.get(i)[c].substring(0,1);
                        String remainingLetters = partsList.get(i)[c].substring(1,partsList.get(i)[c].length());

                        //Change the first letter to uppercase
                        firstLetter = firstLetter.toUpperCase();

                        //Check if the current part is the middle initial
                        if(c==1) {
                            //Connect the two strings
                            correctedLine += firstLetter + remainingLetters + ". ";//add a dot to the end of the middle initial
                        } else {
                            //Connect the two strings
                            correctedLine += firstLetter + remainingLetters + " ";
                        }
                    }
                }

                //Process the last part which is always the date
                String datePart = (partsList.get(i)[partsList.get(i).length-1]);    //get the last part as a date
                StringBuilder stringBuilder = new StringBuilder(datePart);  //create a string builder to process the date
                stringBuilder.insert(2,"/");    //insert the / symbol at position 2
                stringBuilder.insert(5,"/");    //insert the / symbol at position 5

                correctedLine += stringBuilder;

                processedPartsList.add(correctedLine);  //add the string to the processedPartList array list
            }
        }

        //Try to write the data to the output file
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outputFIle));

            //Loop though all the string elements of the processed parts list and write them to the file
            for(int i=0;i< processedPartsList.size();i++){
                bufferedWriter.write(processedPartsList.get(i)+"\n");
            }
            bufferedWriter.close(); //close the writer
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    } // main
} // FilesInOut