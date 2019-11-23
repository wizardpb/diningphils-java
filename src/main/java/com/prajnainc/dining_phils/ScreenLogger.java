package com.prajnainc.dining_phils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * ScreenLogger
 *
 * A simple screen writer that does basic terminal control such as wrinting lines to specific areas of the screen.
 *
 * Hardwired to ANSI terminals only
 */
public class ScreenLogger {

    private final String CSI = "\033[";

    private int cmdLine;
    private String prompt;
    private BufferedReader console;

    public ScreenLogger(int cmdLine, String prompt) {
        this.cmdLine = cmdLine;
        this.prompt = prompt;
        this.console = new BufferedReader(new InputStreamReader(System.in));
    }

    private void writePrompt() {
        writeLine(cmdLine, prompt+" ");
    }

    public void writeLine(int position, String text) {
        System.out.printf(CSI+"%dH"+CSI+"2K"+text,position);
        writePrompt();
    }

    public String readInput() throws IOException {
        writePrompt();
        return console.readLine();
    }
}
