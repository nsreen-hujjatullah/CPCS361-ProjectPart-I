/*
---------------------------------------
        CPCS-361- Project - Part I 
---------------------------------------
         GROUP (7) MEMBER - B1
---------------------------------------

---------------------------------------
Tools : NetBeans IDE 8.2 
 */
package CPCS361Group7;

import java.util.ArrayList;
import java.util.Scanner;

public class CPCS361Group7Part1 {

    public static void main(String[] args) {
        int MemorySize;
        // ask user MemorySize
        System.out.println("-----------------------------------------------\n"
                         + "  CPCS-361- Project - Part I - GROUP (7) - B1 \n"
                         + "-----------------------------------------------");
        System.out.print("Enter Memory Size"
                + "\n./allocator ");
        Scanner scanner = new Scanner(System.in);
        MemorySize = scanner.nextInt();
        MemorySimulator MS = new MemorySimulator(MemorySize);
        MS.Run();
    }

}
/*
Device Specifications:
compiler name and version:  NetBeans IDE 8.2


 */