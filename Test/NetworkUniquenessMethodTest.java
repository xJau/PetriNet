package Test;

import Models.Network;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class NetworkUniquenessMethodTest {

    private ArrayList<Network> testingNetworks;


    public NetworkUniquenessMethodTest() {
        testingNetworks = new ArrayList<>();
        Network testingNetwork1 = new Network(0,"TESTNETWORK1");
        int[][] matrixIn = new int[2][2];
        matrixIn[0][0] = 1;
        matrixIn[0][1] = 1;
        matrixIn[1][0] = 1;
        matrixIn[1][1] = 1;
        int[][] matrixOut = new int[2][2];
        matrixOut[0][0] = 1;
        matrixOut[0][1] = 1;
        matrixOut[1][0] = 1;
        matrixOut[1][1] = 1;
        Network testingNetwork2 = new Network(1,"TESTNETWORK2",matrixIn,matrixOut);
        matrixIn[0][0] = 0;
        matrixIn[0][1] = 0;
        Network testingNetwork3 = new Network(2,"TESTNETWORK3",matrixIn,matrixOut);
        testingNetworks.add(testingNetwork1);
        testingNetworks.add(testingNetwork2);
        testingNetworks.add(testingNetwork3);
    }

    public boolean checkIfNetExists(int i, Network net, ArrayList<? extends Network> nets) {
        for (Network n : nets)
            if (net.equals(n) && n.getId() != i) return true;
        return false;
    }

    @Test
    public void checkIfNetExists_existingNetwork_returnTrue(){
        Network testingNet = new Network(4,"TESTNETWORK1");
        assert checkIfNetExists(4,testingNet,testingNetworks);
    }
    @Test
    public void checkIfNetExists_newNetwork_returnFalse(){
        int[][] matrixIn = new int[2][2];
        matrixIn[0][0] = 1;
        matrixIn[0][1] = 1;
        matrixIn[1][0] = 1;
        matrixIn[1][1] = 1;
        int[][] matrixOut = new int[2][2];
        matrixOut[0][0] = 0;
        matrixOut[0][1] = 1;
        matrixOut[1][0] = 1;
        matrixOut[1][1] = 0;
        Network testingNet = new Network(4,"TESTNETWORK4",matrixIn,matrixOut);
        assert !checkIfNetExists(4,testingNet,testingNetworks);
    }
}
