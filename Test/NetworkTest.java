package Test;

import Models.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class NetworkTest {

    private Network defaultTestingNetwork;
    private Network generateMatrixTestNetwork;

    public NetworkTest(){
        defaultTestingNetwork = new Network(0, "defaultTestingNetwork");
    }

    @Test
    @DisplayName("addPlace_toExistingTransition_addElementToList")
    public void addPlace_toExistingTransition_addElementToList() {
        try {
            Transition existingTransition = defaultTestingNetwork.getTransitions().get(0);
            defaultTestingNetwork.addPlace(777, existingTransition, true);
        } catch (Exception e) {}
        assert defaultTestingNetwork.getPlaces().size() == 2;
    }

    @Test
    @DisplayName("addPlace_toNotExistingTransition_dontAddElementToList")
    public void addPlace_toNotExistingTransition_dontAddElementToList() {
        try {
            Transition existingTransition = defaultTestingNetwork.getTransitions().get(1);
            defaultTestingNetwork.addPlace(777, existingTransition, true);
        } catch (Exception e) {}
        assert defaultTestingNetwork.getTransitions().size() == 1;
        assert defaultTestingNetwork.getPlaces().size() != 2;
    }
    @Test
    @DisplayName("addTransition_toExistingPlace_addElementToList")
    public void addTransition_toExistingPlace_addElementToList() {
        try {
            Place existingPlace = defaultTestingNetwork.getPlaces().get(0);
            defaultTestingNetwork.addTransition(777, existingPlace, true);
        } catch (Exception e) {}
        assert defaultTestingNetwork.getTransitions().size() == 2;
    }

    @Test
    @DisplayName("addTransition_toNotExistingPlace_dontAddElementToList")
    public void addTransition_toNotExistingPlace_dontAddElementToList() {
        try {
            Place notExistingPlace = defaultTestingNetwork.getPlaces().get(1);
            defaultTestingNetwork.addTransition(777, notExistingPlace, true);
        } catch (Exception e) {}
        assert defaultTestingNetwork.getTransitions().size() != 2;
    }

    @Test
    @DisplayName("checkConnectivity_connectedNet_resultsConnected")
    public void checkConnectivity_connectedNet_resultsConnected() {
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
        generateMatrixTestNetwork = new Network(1, "generateMatrixTestNetwork", matrixIn, matrixOut);

        assert generateMatrixTestNetwork.checkConnectivity() == true;
    }

    @Test
    @DisplayName("checkConnectivity_notConnectedNet_resultsNotConnected")
    public void checkConnectivity_notConnectedNet_resultsNotConnected() {
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
        generateMatrixTestNetwork = new Network(1, "generateMatrixTestNetwork", matrixIn, matrixOut);

        generateMatrixTestNetwork.getMatrixOut()[0][0] = 0;
        generateMatrixTestNetwork.getMatrixOut()[0][1] = 0;
        generateMatrixTestNetwork.getMatrixIn()[0][0] = 0;
        generateMatrixTestNetwork.getMatrixIn()[0][1] = 0;
        assert generateMatrixTestNetwork.checkConnectivity() != true;
    }

    @Test
    @DisplayName("connect_twoNotConnectedNodes_addElementToList")
    public void connect_twoNotConnectedNodes_addElementToList(){
        //defaultTestingNetwork only has 1 link P0 -> T0
        try {
            Place newPlace = new Place(1);
            defaultTestingNetwork.getPlaces().add(newPlace);
            Node notConnectedPlace = defaultTestingNetwork.getPlaces().get(1);
            Node notConnectedTransition = defaultTestingNetwork.getTransitions().get(0);
            Link newLink = new Link(notConnectedPlace,notConnectedTransition);
            if (!defaultTestingNetwork.checkLinkExist(newLink)) defaultTestingNetwork.getLinks().add(newLink);
        } catch (Exception e) {}

        assert defaultTestingNetwork.getLinks().size() == 2;
    }

    @Test
    @DisplayName("connect_twoConnectedNodes_dontAddElementToList")
    public void connect_twoConnectedNodes_dontAddElementToList(){
        try {
            Node connectedPlace = defaultTestingNetwork.getPlaces().get(0);
            Node connectedTransition = defaultTestingNetwork.getTransitions().get(0);
            Link newLink = new Link(connectedPlace,connectedTransition);
            if (!defaultTestingNetwork.checkLinkExist(newLink)) defaultTestingNetwork.getLinks().add(newLink);
        } catch (Exception e) {}

        //defaultTestingNetwork only has 1 link P0 -> T0
        assert defaultTestingNetwork.getLinks().size() != 2;
    }

    @Test
    @DisplayName("genereteMatrix_correctInputs_correctNodesArrayDimensions")
    public void genereteMatrix_correctInputs_correctNodesArrayDimensions() {
        int[][] matrixIn = new int[2][2];
        int[][] matrixOut = new int[2][2];

        assert matrixIn.length == matrixOut.length;
        assert matrixIn[0].length == matrixOut[0].length;

        try{
            generateMatrixTestNetwork = new Network(1, "generateMatrixTestNetwork", matrixIn, matrixOut);
            assert matrixIn.length == generateMatrixTestNetwork.getPlaces().size();
            assert matrixIn[0].length == generateMatrixTestNetwork.getTransitions().size();
        }catch (Exception e){
            System.out.println("Cannot create Network");
        }
    }

    @Test
    @DisplayName("genereteMatrix_invalidInputs_incorrectNodesArrayDimensions")
    public void genereteMatrix_invalidInputs_incorrectNodesArrayDimensions() {
        int[][] matrixIn = new int[2][2];
        int[][] matrixOut = new int[3][3];

        assert matrixIn.length != matrixOut.length;
        assert matrixIn[0].length != matrixOut[0].length;

        try{
            generateMatrixTestNetwork = new Network(1, "generateMatrixTestNetwork", matrixIn, matrixOut);
            assert matrixIn.length == generateMatrixTestNetwork.getPlaces().size();
            assert matrixIn[0].length == generateMatrixTestNetwork.getTransitions().size();
        }catch (Exception e){
            System.out.println("Cannot create Network");
        }
    }

}



