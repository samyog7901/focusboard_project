package org.focusboard.model;

public enum TaskStatus {
    NOT_STARTED {
        public String getBadgeClass() { return "bg-yellow-100 text-yellow-800"; }
        public String getDisplayText() { return "To Do"; }
    },
    IN_PROGRESS {
        public String getBadgeClass() { return "bg-blue-100 text-blue-800"; }
        public String getDisplayText() { return "In Progress"; }
    },
    DONE {
        public String getBadgeClass() { return "bg-green-100 text-green-800"; }
        public String getDisplayText() { return "Completed"; }
    };

    public abstract String getBadgeClass();
    public abstract String getDisplayText();
}