package com.silverpixelz.myapplication.ui.pdf;

import com.silverpixelz.myapplication.data.api.model.Pdf.Pdf;
import com.silverpixelz.myapplication.ui.base.View;

import java.util.List;

import okhttp3.ResponseBody;

/**
 * Created by Dharmik Patel on 28-Jun-17.
 */

public interface PdfActivityView extends View {
    void ShowProgress();

    void HideProgress();

    void ResponseError(String title,String message);

    void DownloadResponseError(String title,String message);

    void PdfResponse(List<Pdf> pdfList);

    void DownloadResponse(ResponseBody responseBody,String name,int id);
}
