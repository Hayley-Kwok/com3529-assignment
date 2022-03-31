package uk.ac.shef.com3529.assignment.generateTestSuite;

import uk.ac.shef.com3529.assignment.model.VariableNode;

public class TestGenerationHelper {
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

}
