package io.jagiello;

import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.io.*;

public class ConsoleUtils {

    public static void runKeyListener(final int[] key) throws IOException {
        Terminal terminal = TerminalBuilder.builder()
                .jna(true)
                .system(true)
                .build();

        // raw mode means we get keypresses rather than line buffered input
        terminal.enterRawMode();
        Reader reader = terminal.reader();

        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    key[0] = reader.read();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    public static void drawScreen(Screen screen) throws IOException {
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new
                FileOutputStream(FileDescriptor.out), "UTF-8"), 512);
        out.write(screen.getScreen().toString());
        out.flush();
    }

    public static void clearScreen(){
        try {
            String operatingSystem = System.getProperty("os.name"); //Check the current operating system

            if (operatingSystem.contains("Windows")) {
                ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "cls");
                Process startProcess = pb.inheritIO().start();
                startProcess.waitFor();
            } else {
                ProcessBuilder pb = new ProcessBuilder("clear");
                Process startProcess = pb.inheritIO().start();

                startProcess.waitFor();
            }
        } catch (Exception e){
            System.out.println(e);
        }
    }
}
