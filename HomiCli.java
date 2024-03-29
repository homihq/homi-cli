///usr/bin/env jbang "$0" "$@" ; exit $?
//DEPS info.picocli:picocli:4.7.1
//DEPS org.projectlombok:lombok:1.18.26


//SOURCES ShareOAS3Command.java


import picocli.CommandLine;
import picocli.CommandLine.Command;


import java.util.concurrent.Callable;

@Command(name = "homi", mixinStandardHelpOptions = true, version = "HomiCli 0.7.0",
        description = "APIOps CLI for HOMI API Management",
        subcommands = {ShareOAS3Command.class,}
)
class HomiCli implements Callable<Integer> {


    public static void main(String... args) {
        int exitCode = new CommandLine(new HomiCli()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public Integer call() throws Exception {
        System.out.println("Hello HomiCli 0.7.0");
        return 0;
    }


}
