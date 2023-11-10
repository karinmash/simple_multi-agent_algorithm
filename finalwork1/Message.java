package mytry;

/*
 * messages communicate sending messages to each other
 */
public class Message {
    private int id;  //id of the sender
    private int assignment;
    private int numberOfConstraints;
    

    // a message should include information.
    // you are required to add corresponding fields and constructor parameters
    // in order to pass on that information
    public Message(int id, int assignment) {
        this.id= id;
        this.assignment = assignment;
    }

    // msg for the last agent only - to count the total number of successful constraint
    public Message(int numberOfConstraints){
        this.numberOfConstraints = numberOfConstraints;
    }

    public int getId() {
        return id;
    }

    public int getAssignment() {
        return assignment;
    }

    public int getNumberOfConstraints() {
        return numberOfConstraints;
    }
}

