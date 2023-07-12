/*
---------------------------------------
        CPCS-361- Project - Part I 
---------------------------------------
         GROUP (7) MEMBER - B1
---------------------------------------

---------------------------------------


 */
package CPCS361Group7;
// improt 
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class MemorySimulator {

    private int MemorySize;
    private ArrayList<Partition> Partitions;

    public MemorySimulator() {
        this.MemorySize = 0;
        this.Partitions = new ArrayList<>();
    }

    public MemorySimulator(int MemorySize) {
        this.MemorySize = MemorySize;
        this.Partitions = new ArrayList<>();
        this.Partitions.add(new Partition(0, MemorySize, "Free"));
    }

    public void Run() {
        // ask from user 
        System.out.print("Enter request type  RQ (request), RL (release), C (compact), STAT (status report) and X (exit)."
                + "\nallocator> ");
        int reminder = MemorySize;
        Scanner scanner = new Scanner(System.in);
        String user_input = scanner.nextLine();
        // This loop continues as long as the entry is not equal to x 
        while (!user_input.equalsIgnoreCase("x")) {
            //We will save the user's input in a string array and split it by space
            String[] input = user_input.split(" ");
            // if input [0] equals RQ then enters the condition
            if (input[0].equalsIgnoreCase("RQ")) {
                
                // In the case that the length of the input is not equal to 4, 
                // display an error message, then return the request again
                if (input.length != 4) {
                    System.out.println("An error occurred in the read.");
                } else {
                    // In the case input = 4 
                    // then save value input[1]= name process 
                    // then save value input [2] = size of prosess 
                    // then save value inpyt [3] = flag 
                    String name = input[1];
                    int size = Integer.parseInt(input[2]);
                    reminder = MemorySize-size;
                    if(reminder<size ){
                        System.out.println("The process has been rejected!\n");                        
                    }
                    String flag = input[3];
                    int ptype = 0;
                    // if flag = f , then ptype =3              
                    if (flag.equalsIgnoreCase("F")) {
                        ptype = 3;
                    }
                    // if flag = w , then ptype =2
                    if (flag.equalsIgnoreCase("W")) {
                        ptype = 2;
                    }
                    // if flag = b , then ptype =1
                    if (flag.equalsIgnoreCase("B")) {
                        ptype = 1;
                    }
                    // calling this method to allocate this process to memory 
                    MemoryAllocate(name, size, ptype);
                }
                // if input [0] equals RL then enters the condition   
            } else if (input[0].equalsIgnoreCase("RL")) {
                // save value input[1] to name  
                String name = input[1];
                
                // calling this method to release the memory that has been allocated to process 
                boolean result =MemoryDeallocate(name);    
                if (!result){
                    System.out.println("The process has been rejected!");
                }
                
                
                
                // if input [0] equals C then enters the condition  
            } else if (input[0].equalsIgnoreCase("C")) {
                // calling this method to compact unused holes of memory into one region    
                Compaction();
                // if input [0] equals STAT then enters the condition  
            } else if (input[0].equalsIgnoreCase("STAT")) {
                // Calling this method to will report the regions of memory that are allocated and the regions 
                ShowPartitions();
                // else , display erro massege 
            } else {
                System.out.println("This command is not recognized, try again");
            }
            System.out.print("allocator> ");
            user_input = scanner.nextLine();
        }
    }
    //---------------------------------------------------------------------------------------------------
    // allocate memory using one of the three approaches: first fit, best fit, and worst fit   
    private boolean MemoryAllocate(String name, int Size, int PolicyType) {
        Partition New = new Partition(name, Size, "Occupied");
        int ind = -1;
        if (PolicyType == 1) { // Best Fit
            ind = BestFit(New); // get index of Free partition that satisfies th ploicy
        } else if (PolicyType == 2) { // Worst Fit
            ind = WorstFit(New);
        } else if (PolicyType == 3) { // First Fit
            ind = FirstFit(New);
        }
        if (ind == -1) { // no free partition satisfies the policy
            return false;
        } else {
            Partition Chosen = this.Partitions.get(ind); // get the chosen partition returned from the policy
            int start = Chosen.getStartAddress();
            New.setStartAddress(start);
            //eliminating internal fragmentation)
            this.Partitions.remove(ind);
            this.Partitions.add(ind, New);
            int remainingSize = Chosen.getSize() - Size;
            if (remainingSize != 0) {
                int startFree = start + Size;
                this.Partitions.add(ind + 1, new Partition(name, startFree, remainingSize, "Free"));
            }
            return true;
        }
    }
    //----------------------------------------------------------------------------------------
    // the operating system searches through the list of free blocks of memory to find the block that is
    //closest in size to the memory request from the process.
    private int BestFit(Partition New) {
        int ind = -1, mn = 1000000;
        for (int i = 0; i < Partitions.size(); ++i) {
            if (Partitions.get(i).getStatus().equals("Free")) {
                if (Partitions.get(i).getSize() >= New.getSize()
                        && Partitions.get(i).getSize() < mn) {
                    mn = Partitions.get(i).getSize();
                    ind = i;
                }
            }
        }
        return ind;
    }
    //----------------------------------------------------------------------------------------
    //the process traverses the whole memory and always search for the largest hole/partition, 
    //and then the process is placed in that hole/partition. It is a slow process 
    //because it has to traverse the entire memory to search the largest hole. 
    private int WorstFit(Partition New) {
        int ind = -1, mx = 0;
        for (int i = 0; i < Partitions.size(); ++i) {
            if (Partitions.get(i).getStatus().equals("Free")) {
                if (Partitions.get(i).getSize() >= New.getSize()
                        && Partitions.get(i).getSize() > mx) {
                    mx = Partitions.get(i).getSize();
                    ind = i;
                }
            }
        }
        return ind;
    }
    //----------------------------------------------------------------------------------------
    //the operating system searches through the list of free blocks of memory, starting from
    //the beginning of the list, until it finds a block that is large enough to accommodate
    //the memory request from the process.
    private int FirstFit(Partition New) {
        for (int i = 0; i < Partitions.size(); ++i) {
            if (Partitions.get(i).getStatus().equals("Free")) {
                if (Partitions.get(i).getSize() >= New.getSize()) {
                    return i;
                }
            }
        }
        return -1;
    }
    //----------------------------------------------------------------------------------------
    // this method will release the memory that has been allocated to process that given as parameter
    private boolean MemoryDeallocate(String name) {
        for (int i = 0; i < Partitions.size(); ++i) {
            if (Partitions.get(i).getName().equalsIgnoreCase(name) && Partitions.get(i).getStatus() != "Free") {
                Partitions.get(i).setStatus("Free");
                return true;
            }
        }
        return false;
    }
    //----------------------------------------------------------------------------------------
    // this method will compact the set of holes into one larger hole. 
    private void Compaction() {
        int AllfreeSpace = getAllFreeSpaceinMemory();
        removeFreeParitions();
        int start = shiftOcuupiedPartitions();
        this.Partitions.add(new Partition(start, AllfreeSpace, "Free"));
    }
    //----------------------------------------------------------------------------------------
    private int shiftOcuupiedPartitions() {
        int start = 0;
        for (int i = 0; i < this.Partitions.size(); ++i) {
            if (this.Partitions.get(i).getStartAddress() != start) {
                this.Partitions.get(i).setStartAddress(start);
            }
            start = this.Partitions.get(i).getStartAddress() + this.Partitions.get(i).getSize();
        }
        return start;
    }
    //----------------------------------------------------------------------------------------
    private void removeFreeParitions() {
        for (int i = 0; i < this.Partitions.size(); ++i) {
            if (this.Partitions.get(i).getStatus() == "Free") {
                this.Partitions.remove(i);
                i--;
            }
        }
    }
    //----------------------------------------------------------------------------------------
    private int getAllFreeSpaceinMemory() {
        int sum = 0;
        for (int i = 0; i < this.Partitions.size(); ++i) {
            if (this.Partitions.get(i).getStatus() == "Free") {
                sum += this.Partitions.get(i).getSize();
            }
        }
        return sum;
    }
    //----------------------------------------------------------------------------------------
    // this method will report the regions of memory that are allocated and the regions
    private void ShowPartitions() {
        for (int i = 0; i < this.Partitions.size(); ++i) {
            int start = this.Partitions.get(i).getStartAddress();
            int size = this.Partitions.get(i).getSize();
            String status = this.Partitions.get(i).getStatus();
            String name = this.Partitions.get(i).getName();
            if (status.equalsIgnoreCase("Occupied")) {
                System.out.println(
                        "Addresses[" + start + ":" + (start + size - 1) + "] Process " + name.toUpperCase());
            } else {
                System.out.println(
                        "Addresses [" + start + ":" + (start + size - 1) + "] Unused");
            }
        }
    }
}
