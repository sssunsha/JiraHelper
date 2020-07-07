package com.hybris.caas.component;

import com.hybris.caas.constant.Constant;

public class CommandHelper {

    public Constant.Teams start(final String[] args) {
        if (args.length == 0) {
            return Constant.Teams.ALL;
        }
        switch (args[0]) {
            case "-h":
            case "--help":
                printHelpInformation();
                return Constant.Teams.NONE;
            case "-b":
            case "--bamboo":
                return Constant.Teams.BAMBOO;
            case "-m":
            case "--mooncake":
                return Constant.Teams.MOONCAKE;
            case "-a":
            case "--all":
                return Constant.Teams.ALL;
            default:
                System.out.println("command is invalid, please check the command help information");
                printHelpInformation();
                return Constant.Teams.NONE;
        }
    }

    private void printHelpInformation() {
        System.out.println("-h, --help: print help information");
        System.out.println("-a, --all [default]: generate the release report for both Bamboo and Mooncake services");
        System.out.println("-b, --bamboo: only generate release report for Bamboo services");
        System.out.println("-m, --mooncake: only generate release report for Mooncake services");
    }
}
