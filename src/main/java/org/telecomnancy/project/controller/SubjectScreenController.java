package org.telecomnancy.project.controller;

import org.telecomnancy.project.Main;
import org.telecomnancy.project.model.Subject;

public class SubjectScreenController {

    private final Main main;
    private final Subject subject;

    public SubjectScreenController(Main main, Subject subject) {
        this.main = main;
        this.subject = subject;
    }
}
