package mytry;

import java.util.ArrayList;
import java.util.HashMap;


public class Main {

    public static void main(String[] args) throws InterruptedException {
        // extract parameters
    	   int n = Integer.valueOf(args[0]).intValue();
           int d = Integer.valueOf(args[1]).intValue();
           double p1 = Double.valueOf(args[2]).doubleValue();
           double p2 = Double.valueOf(args[3]).doubleValue();

           // generate and print CSP
           Generator gen = new Generator(n, d, p1, p2);
           CSP csp = gen.generateDCSP();
           csp.print();

           // initialize mailer
           Mailer mailer = new Mailer();
           for (int i = 0; i < n; i++) {
               mailer.put(i);
           }

           // create agents
           ArrayList<Thread> threads = new ArrayList<Thread>();
           for (int i = 0; i < n; i++) {
               // use the csp to extract the private information of each agent
           //    HashMap<VarTuple, ConsTable> private_information = Agent.calcPrivateInfo(i, csp, n);
        	   HashMap<Integer, ConsTable> private_information= csp.calcPrivateInfo(i);
               Thread t = new Thread(new Agent(i, mailer, private_information, csp.getDomainSize(),n));
               threads.add(t);
           }

           // run agents as threads
           for (Thread t : threads) {
               t.start();
           }

           // wait for all agents to terminate
           for (Thread t : threads) {
               t.join();
           }
    }

}
