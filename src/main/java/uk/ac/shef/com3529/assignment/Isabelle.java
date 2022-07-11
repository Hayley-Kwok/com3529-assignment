package uk.ac.shef.com3529.assignment;

import uk.ac.shef.com3529.assignment.model.VariableNode;

import java.util.HashSet;

public class Isabelle {
    private final HashSet<VariableNode> nodes;
    private VariableNode side1;

    public Isabelle() {
        nodes = new HashSet<>();
        side1 = new VariableNode<>(Integer.class, "side1", 2, 1, 10);
        System.out.println("side 1 hash code on creation: "+ side1.hashCode());
        nodes.add(side1);
    }

    public void SanityCheckHashSet(){
        System.out.println("Before Change");
        System.out.println("side 1 hash code: "+ side1.hashCode());
        System.out.println("A new node added? "+nodes.add(side1));
        System.out.println("Current size of the hashset: "+nodes.size());
        for (VariableNode node : nodes) {
            System.out.println("node in set hashcode: "+ node.hashCode());
        }


        side1.setValue(3); // THIS LINE CHANGE THE VALUE IN THE NODE

        System.out.println("After Change");
        for (VariableNode node : nodes) {
            System.out.println("Hash Value Equivalence: " + (node.hashCode() == side1.hashCode()));
            System.out.println("Equals Equivalence: " + (node.equals(side1)));
        }
        System.out.println("A new node added? "+nodes.add(side1));
        System.out.println("Current size of the hashset: "+nodes.size());

        System.out.println("Hashcode of nodes in hashset:");
        for (VariableNode node: nodes) {
            System.out.println(node.hashCode());
        }
    }

}
