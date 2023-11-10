package mytry;
import java.util.HashMap;
import java.util.Random;
import java.util.Map.Entry;



public class Agent implements Runnable {
    /**
	 * @return the assignments
	 */
	public HashMap<Integer, Integer> getAssignments() {
		return assignments;
	}

	private int id;
    private Mailer mailer;
    private HashMap<Integer, ConsTable> constraints;
    private HashMap<Integer, Integer> assignments = new HashMap<Integer, Integer>();
    // -----------
    private int domainSize,n;
    private int assignment;
   

    /*
     * constructor parameters -
     * agent's id
     * a reference to mailer
     * a reference to csp
     */
    public Agent(int id, Mailer mailer, HashMap<Integer, ConsTable> constraints, int domainSize,int n) {
        this.id = id;
        this.mailer = mailer;
        this.constraints = constraints;
       

        // --------
        this.domainSize = domainSize;
        this.n=n;
        assignment = (new Random()).nextInt(domainSize);

      
    }

    public int getId() {
        return id;
    }

    public HashMap<Integer, ConsTable> getConstraints() {
        return constraints;
    }

    public int getAssignment() {
        return assignment;
    }

   
   /* public static HashMap<VarTuple, ConsTable> calcPrivateInfo(int id, CSP csp, int n) {
        HashMap<VarTuple, ConsTable> cons_tables = csp.getCons_tables();
        HashMap<VarTuple, ConsTable> privateInformation = new HashMap<VarTuple, ConsTable>();
        for (int i = 0; i < n; i++) {
        	if(id != i) {
            VarTuple op1 = new VarTuple(id, i);
            VarTuple op2 = new VarTuple(i, id);
            if (cons_tables.containsKey(op1)) {
                privateInformation.put(op1, cons_tables.get(op1)); // insert the neighbors
            }
            if (cons_tables.containsKey(op2)) {
                privateInformation.put(op1, cons_tables.get(op2)); // insert the neighbors
            }
        }
        }
        return privateInformation;
    } */

 /*   public boolean isSuccessfulConstraint(int idOtherAgent, int assignmentOtherAgent) {
        VarTuple varTuple = new VarTuple(id, idOtherAgent);
        if (!constraints.containsKey(varTuple))
            return false;
        // check if we have true in table[assignment][assignmentOtherAgent]
        return constraints.get(varTuple).getTable()[assignment][assignmentOtherAgent];
    }
*/

    
    
    @Override
    public void run() {
     // send all the other agents our constraints with them
    	 mailer.sendAllToNeighbors(this);
     // read all the msg that that other agents sent to us
    	 mailer.readFromAllNeighbors(this);
    	try {
			Thread.sleep(100);
		} catch (InterruptedException error) {
			// TODO Auto-generated catch block
			error.printStackTrace();
		}
    	
     int numberOfConstraints=mailer.isSuccessfulConstraint(this);
        System.out.println("ID: " + id + ", assignment: " + assignment +
                ", successful constraint checks: " + numberOfConstraints);
        // send to the last agent the numberOfSuccessfulConstraints
        if(id != n-1) {
        mailer.sendToLastAgent(numberOfConstraints);
        }
        // the last agent should print the total number of successful constraints
        int count = 0;
		while (count < n - 1) {
			Message message = mailer.readOne(id);
			if (message == null) {
				continue;
			}
			count++;
			numberOfConstraints += message.getNumberOfConstraints();
		}
		System.out.println("total number of constraint checks: " + numberOfConstraints);
     
        
    }

	
}
