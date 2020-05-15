package com.deborawendland.provadev.dao;

import com.deborawendland.provadev.exceptions.InvalidWatchServiceException;

import java.io.IOException;
import java.nio.file.*;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.OVERFLOW;

public class DirectoryWatcherDAO {

    private Path pathDataIn;

    public DirectoryWatcherDAO(Path pathDataIn) {
        this.pathDataIn = pathDataIn;
    }

    public Path directoryWatcher(){
        WatchService watcher = null;
        try {
            watcher = FileSystems.getDefault().newWatchService();
        } catch (IOException e) {
            throw new InvalidWatchServiceException("Error registering watcher");
        }
        while (true){
            WatchKey key;
            try {
                key = pathDataIn.register(watcher,
                        ENTRY_CREATE);
            } catch (IOException x) {
                throw new InvalidWatchServiceException("Error registering watcher");
            }

            for (WatchEvent<?> event: key.pollEvents()) {
                WatchEvent.Kind<?> kind = event.kind();
                if (kind == OVERFLOW) {
                    continue;
                }
                WatchEvent<Path> ev = (WatchEvent<Path>)event;
                Path filepath = ev.context().toAbsolutePath();
                return filepath;
            }
        }
    }
}
