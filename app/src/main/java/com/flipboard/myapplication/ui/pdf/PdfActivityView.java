package com.flipboard.myapplication.ui.pdf;

import com.flipboard.myapplication.data.api.model.Pdf.Pdf;
import com.flipboard.myapplication.ui.base.View;

import java.util.List;

import okhttp3.ResponseBody;

/**
 */

public interface PdfActivityView extends View {
    void ShowProgress();

    void HideProgress();

    void ResponseError(String title,String message);

    void DownloadResponseError(String title,String message);

    void PdfResponse(List<Pdf> pdfList);

    void DownloadResponse(ResponseBody responseBody,String name,int id);
}
