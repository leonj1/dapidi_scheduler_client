package com.dapid.agent.services;

import java.io.IOException;
import java.util.List;

/**
 * Created for K and M Consulting LLC.
 * Created by Jose M Leon 2017
 **/
public class ShellProcess implements Process {

    private List<String> cmd;

    public ShellProcess(List<String> cmd) {
        this.cmd = cmd;
    }

    @Override
    public int start() throws IOException, InterruptedException {
        final ProcessBuilder processBuilder = new ProcessBuilder();
        // TODO Likely bug splitting on space. Consider ls -l /path/"some folder"/file.txt
        processBuilder.command(cmd).redirectErrorStream(true);
        final java.lang.Process process = processBuilder.start();
        return process.waitFor();
    }
}
