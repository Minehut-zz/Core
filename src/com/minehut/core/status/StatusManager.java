package com.minehut.core.status;

import com.minehut.core.Core;
import com.minehut.core.status.download.StatusDownloader;
import com.minehut.core.status.menu.ServerMenuManager;
import com.minehut.core.status.upload.StatusUploader;

/**
 * Created by luke on 6/19/15.
 */
public class StatusManager {
    private Core core;
    private StatusUploader statusUploader;
    private StatusDownloader statusDownloader;
    private ServerMenuManager serverMenuManager;

    public StatusManager(Core core) {
        this.core = core;
        this.statusUploader = new StatusUploader();
        this.statusDownloader = new StatusDownloader(core);
        this.serverMenuManager = new ServerMenuManager(this.statusDownloader);
    }

    public StatusUploader getStatusUploader() {
        return statusUploader;
    }

    public StatusDownloader getStatusDownloader() {
        return statusDownloader;
    }

    public ServerMenuManager getServerMenuManager() {
        return serverMenuManager;
    }
}
