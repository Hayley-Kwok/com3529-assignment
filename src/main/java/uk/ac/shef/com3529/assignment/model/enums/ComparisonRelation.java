package uk.ac.shef.com3529.assignment.model.enums;

public enum ComparisonRelation {
    EqualsEquals {
        public String toString() {
            return "==";
        }
    },
    NotEquals {
        public String toString() {
            return "!=";
        }
    },
    LargerThan {
        public String toString() {
            return ">";
        }
    },
    LargerOrEqualsTo {
        public String toString() {
            return ">=";
        }
    },
    SmallerThan {
        public String toString() {
            return "<";
        }
    },
    SmallerOrEqualsTo {
        public String toString() {
            return "<=";
        }
    }
}
