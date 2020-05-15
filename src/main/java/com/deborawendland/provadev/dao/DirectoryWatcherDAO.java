package com.deborawendland.provadev.dao;

import java.io.IOException;
import java.nio.file.*;
import java.util.Optional;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.OVERFLOW;

public class DirectoryWatcherDAO {

    private Path homepath = Paths.get("/home/debs/Documents");
    private Path pathDataIn = Paths.get(homepath + "/data/in");

    public Optional<Path> directoryWatcher(){
        WatchService watcher = null;
        try {
            watcher = FileSystems.getDefault().newWatchService();
        } catch (IOException e) {
            // TODO criar excecao;
            e.printStackTrace();
        }
        while (true){
            WatchKey key;
            try {
                key = pathDataIn.register(watcher,
                        ENTRY_CREATE);
                //key = watcher.take();
            } catch (IOException x) {
                return Optional.empty(); // TODO CRIAR EXCECAO
            }

            for (WatchEvent<?> event: key.pollEvents()) {
                WatchEvent.Kind<?> kind = event.kind();
                if (kind == OVERFLOW) {
                    continue;
                }
                WatchEvent<Path> ev = (WatchEvent<Path>)event;
                Path filepath = ev.context();
                try {
                    Path child = pathDataIn.resolve(filepath);
                    if (!Files.probeContentType(child).equals("text/plain")) {
                        System.err.format("New file '%s'" +
                                " is not a plain text file.%n", filepath);
                        continue;
                    }
                } catch (IOException x) {
                    System.err.println(x);
                    continue;
                }

                // Email the file to the
                //  specified email alias.
                System.out.format("Emailing file %s%n", filepath);
                //Details left to reader....
                return Optional.of(filepath);
            }

/*            // Reset the key -- this step is critical if you want to
            // receive further watch events.  If the key is no longer valid,
            // the directory is inaccessible so exit the loop.
            boolean valid = key.reset();
            if (!valid) {
                break;
            }*/
        }
    }
}
