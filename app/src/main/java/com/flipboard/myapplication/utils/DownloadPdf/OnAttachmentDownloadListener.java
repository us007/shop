package com.flipboard.myapplication.utils.DownloadPdf;

/**
 */

public interface OnAttachmentDownloadListener {
    void onAttachmentDownloadedSuccess();
    void onAttachmentDownloadedError();
    void onAttachmentDownloadedFinished();
    void onAttachmentDownloadUpdate(int percent);
}
