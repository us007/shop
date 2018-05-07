package com.flipboard.myapplication.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.flipboard.myapplication.R;
import com.flipboard.myapplication.data.api.model.Pdf.Pdf;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 */

public class PdfAdapter extends RecyclerView.Adapter<PdfAdapter.ViewHolder> {

    private List<Pdf> dataSet;
    private Activity mContext;
    private PdfAdapterInterface pdfAdapterInterface;

    public PdfAdapter(List<Pdf> os_versions, Activity context,PdfAdapterInterface pdfAdapterInterface) {
        this.dataSet = os_versions;
        this.mContext = context;
        this.pdfAdapterInterface = pdfAdapterInterface;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemLayoutView = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.list_pdf, viewGroup, false);
        return new ViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {

        //animate(viewHolder.cardViewProduct,i);
        Pdf fp = dataSet.get(i);
        int id = fp.getId();
        String Name = fp.getPdf_name();
        String pdf = fp.getDocument_upload();

        viewHolder.pdfTextView1.setText(Name);
        viewHolder.pdfTextView2.setText(pdf);

        viewHolder.imgDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pdfAdapterInterface.pdf(fp);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.pdf_textView_1)
        TextView pdfTextView1;
        @BindView(R.id.pdf_textView_2)
        TextView pdfTextView2;
        @BindView(R.id.img_download)
        ImageButton imgDownload;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            ButterKnife.bind(this, itemLayoutView);
        }

    }

}