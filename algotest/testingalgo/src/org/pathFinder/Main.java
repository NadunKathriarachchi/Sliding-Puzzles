package org.pathFinder;

import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        while (!exit) {
            System.out.println("...................");
            System.out.println(":     Puzzle      :");
            System.out.println(": 1. Load File    :");
            System.out.println(": 2. Exit         :");
            System.out.print("Enter Number: ");


            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    loadMenu();
                    break;
                case 2:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid Number.Enter a number between 1 and 2.");
                    break;
            }
        }
        System.out.println("Exit Game!");
    }

    private static void loadMenu() {
        Scanner scanner = new Scanner(System.in);
        boolean backToMenu = false;
        while (!backToMenu) {
            System.out.println("..........................");
            System.out.println(":       Load Menu        :");
            System.out.println(": 1. Load from Benchmark :");
            System.out.println(": 2. Load from Examples  :");
            System.out.println(": 4. Back to Main Menu   :");
            System.out.print("Enter Number: ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    loadFromFolder("benchmark_series/");
                    break;
                case 2:
                    loadFromFolder("examples/");
                    break;
                case 4:
                    backToMenu=true;
                    break;
                default:
                    System.out.println("Invalid Number.Enter a number between 1 and 4.");
                    break;
            }
        }
    }



    private static void loadFromFolder(String folderPath) {
        File folder = new File(folderPath);
        File[] files = folder.listFiles();
        if (files != null && files.length > 0) {
            System.out.println("\nAvailable Files in the Selected Folder:");
            for (int i = 0; i < files.length; i++) {
                System.out.println((i + 1) + ". " + files[i].getName());
            }
            System.out.print("\nEnter Number of the File to Load: ");
            Scanner scanner = new Scanner(System.in);
            int fileChoice = scanner.nextInt();
            if (fileChoice >= 1 && fileChoice <= files.length) {
                String fileName = folderPath + files[fileChoice - 1].getName();
                new org.pathFinder.PathFind(fileName);
            } else {
                System.out.println("Invalid File Choice. Exit!");
            }
        } else {
            System.out.println("No Files Found in Selected Folder. Exit!");
        }
    }

}

