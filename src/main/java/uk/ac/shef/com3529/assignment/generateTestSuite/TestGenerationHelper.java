package uk.ac.shef.com3529.assignment.generateTestSuite;

import uk.ac.shef.com3529.assignment.model.VariableNode;

public class TestGenerationHelper {
    /**
     * generate the signature with the name and type of variables
     *
     * @param variables array of variables
     * @return signature with the name and type of variables
     */
    public static String generateParametersSignature(VariableNode<?>[] variables) {
        StringBuilder sb = new StringBuilder();
        for (VariableNode<?> variable : variables) {
            sb.append(getTypeString(variable.getType()));
            sb.append(" ");
            sb.append(variable.getName());
            sb.append(", ");
        }
        sb.setLength(sb.length() - 2);
        return sb.toString();
    }

    /**
     * get the type string of the given type
     *
     * @param type one of the type in Number
     * @return the corresponding type string
     */
    public static String getTypeString(Class<?> type) {
        if (type.equals(Integer.class)) {
            return "int";
        } else if (type.equals(Long.class)) {
            return "long";
        } else if (type.equals(Float.class)) {
            return "float";
        } else {
            return "double";
        }
    }

    /**
     * generate the signature with just the name of variables
     *
     * @param variables array of variables
     * @return signature with just the name of variables
     */
    public static String generateOnlyNameSignature(VariableNode<?>[] variables) {
        StringBuilder sb = new StringBuilder();
        for (VariableNode<?> variable : variables) {
            sb.append(variable.getName());
            sb.append(", ");
        }
        sb.setLength(sb.length() - 2);
        return sb.toString();
    }
}
