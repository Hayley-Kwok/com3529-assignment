package uk.ac.shef.com3529.assignment.model.enums;

/**
 * This enum represent the binary relations of two node, including different arithmetic relations and AND/OR relations.
 */
public enum BinaryRelation {
    Or {
        public String toString() {
            return "||";
        }
    },
    And {
        public String toString() {
            return "&&";
        }
    }
}
