package com.zhuweitung.dbp.cli;

import org.junit.Test;

public class CommandLineHelperTest {

    @Test
    public void help() {
        CommandLineHelper.help();
    }

    @Test
    public void version() {
        CommandLineHelper.version();
    }
}
