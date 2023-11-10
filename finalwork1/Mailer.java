package mytry;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;


/*
 * used for communication among agents
 */
public class Mailer {
    // maps between agents and their mailboxes
    private HashMap<Integer, List<Message>> map = new HashMap<>();
    private int numberOfFinishedAgents;

    // send message @m to agent @receiver
    public void send(int receiver, Message m) {
        // get the mailbox of the receiver
        List<Message> l = map.get(receiver);

        // add the message
        synchronized (l) {
            l.add(m); 
        }
    }

    // agent @receiver reads the first message from its mail box
    public Message readOne(int receiver) {
        List<Message> l = map.get(receiver);
        if (l.isEmpty()) {
            return null;
        }

        synchronized (l) {
            Message m = l.get(0);
            l.remove(0);
            return m;
        }
    }

 
    public void sendAllToNeighbors(Agent agent) {
        Message currentMsg;
        for (Entry<Integer, ConsTable> entry: agent.getConstraints().entrySet()) {
            currentMsg = new Message(agent.getId(), agent.getAssignment());
			this.send(entry.getKey(), currentMsg);
            // send the other agent our id and assignment number
         
        }
        
        
    }
    public void readFromAllNeighbors(Agent agent) {
    	while (agent.getAssignments().size() < agent.getConstraints().size()) {
    		Message message = this.readOne(agent.getId());
			if (message == null) {
				continue;
			}
          agent.getAssignments().put(message.getId(), message.getAssignment());
   
    	}
    }
    
    public int isSuccessfulConstraint(Agent agent) {
    	 int numberOfConstraints=0;
    		for (Entry<Integer, ConsTable> entry:agent.getConstraints().entrySet() ) {
    			int assignments1 = -1, assignments2 = -1;
    			if (entry.getKey() <agent.getId()) {
    				assignments1 = agent.getAssignments().get(entry.getKey());
    				assignments2 = agent.getAssignment();
    			}
    			else {
    				assignments2 = agent.getAssignments().get(entry.getKey());
    				assignments1 =  agent.getAssignment();
    			}
    			if (entry.getValue().getTable()[assignments1][assignments2]) {
    				numberOfConstraints++;
    			}
    		}
    		return numberOfConstraints;

    }
    public void sendToLastAgent(int numberOfSuccessfulConstraints) {
        Message currentMsg = new Message(numberOfSuccessfulConstraints);
        this.send(map.size() - 1, currentMsg);

    }

    public synchronized void increaseNumberOfFinishedAgents() {
        numberOfFinishedAgents++;
    }

    // only used for initialization 
    public void put(int i) {
        List<Message> l = new ArrayList<Message>();
        this.map.put(i, l);
    }


}
