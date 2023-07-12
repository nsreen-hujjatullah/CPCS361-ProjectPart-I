/*
---------------------------------------
        CPCS-361- Project - Part I 
---------------------------------------
         GROUP (7) MEMBER - B1
---------------------------------------

---------------------------------------
 */
package CPCS361Group7;

/**
 *
 * @author nsree
 */
public class Partition {
    private int StartAddress;
    private int Size;
    private String Status;
    private String name;
    
    
    public Partition(){
        this.StartAddress = -1;
        this.Status = "";
        this.Size = 0;
        this.name = "";
        
    }
    
    public Partition(int StartAddress, int Size, String Status){
        this.StartAddress = StartAddress;
        this.Status = Status;
        this.Size = Size;
        
    }
    public Partition(String name,int StartAddress, int Size, String Status){
        this.StartAddress = StartAddress;
        this.Status = Status;
        this.Size = Size;
        this.name=name;
    }
    public Partition(int Size, String Status){
        this.StartAddress = -1;
        this.Status = Status;
        this.Size = Size;
    }

    public Partition(String name, int Size, String Status) {
        this.StartAddress = -1;
        this.Status = Status;
        this.Size = Size;
        this.name=name;
    }
    
    public void setStartAddress(int StartAddress){
        this.StartAddress = StartAddress;
    }
    
    public void setStatus(String Status){
        this.Status = Status;
    }
    
    public int getStartAddress(){
        return this.StartAddress;
    }
    
    public String getStatus(){
        return this.Status;
    }
    
    public void setSize(int Size){
        this.Size = Size;
    }
    
    public int getSize(){
        return this.Size;
    }
    
    public void increaseSize(int additionalSize){
        this.Size += additionalSize;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
}



