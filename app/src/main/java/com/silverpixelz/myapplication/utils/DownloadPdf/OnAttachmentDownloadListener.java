package com.silverpixelz.myapplication.utils.DownloadPdf;

/**
 * Created by Dharmik Patel on 12-Aug-17.
 */

public interface OnAttachmentDownloadListener {
    void onAttachmentDownloadedSuccess();
    void onAttachmentDownloadedError();
    void onAttachmentDownloadedFinished();
    void onAttachmentDownloadUpdate(int percent);
}
