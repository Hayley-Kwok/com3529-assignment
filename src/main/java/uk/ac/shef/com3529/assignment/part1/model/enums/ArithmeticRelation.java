package uk.ac.shef.com3529.assignment.part1.model.enums;

public enum ArithmeticRelation {
    Add {
        public String toString() {
            return "+";
        }
    },
    Minus {
        public String toString() {
            return "-";
        }
    },
    Multiply {
        public String toString() {
            return "*";
        }
    },
    Divide {
        public String toString() {
            return "/";
        }
    }
}
