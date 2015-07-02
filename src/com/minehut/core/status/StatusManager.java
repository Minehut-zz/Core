package com.minehut.core.status;

import com.minehut.core.Core;
import com.minehut.core.status.download.StatusDownloader;
import com.minehut.core.status.upload.StatusUploader;

/**
 * Created by luke on 6/19/15.
 */
public class StatusManager {
    private Core core;
    private StatusUploader statusUploader;
    private StatusDownloader statusDownloader;

    public StatusManager(Core core) {
        this.core = core;
        this.statusUploader = new StatusUploader();
        this.statusDownloader = new StatusDownloader(core);
    }

    public StatusUploader getStatusUploader() {
        return statusUploader;
    }
}
