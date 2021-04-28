package com.easy.customdialog;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
// Aniket Kumar
import com.github.andreilisun.swipedismissdialog.SwipeDismissDialog;
import com.squareup.picasso.Picasso;

public class DialogClass {
    SwipeDismissDialog swipeDismissDialog;

    public static DialogClass dialogInstance = new DialogClass();

    // Class Instance
    public static DialogClass getInstance() {
        return dialogInstance;
    }

    // Default Constructor
    private DialogClass() {
    }

    public void AlertDialog(final MainActivity mainActivity, String title, String image, String successUrl) {
        View view = LayoutInflater.from(mainActivity).inflate(R.layout.custom_dialog, null);
        swipeDismissDialog = new SwipeDismissDialog.Builder(mainActivity).setView(view).build().show();
        TextView heading = view.findViewById(R.id.title);
        Button btnSuccess = view.findViewById(R.id.btnSuccess);
        ImageView imageView = view.findViewById(R.id.image);
        heading.setText(title);
        Picasso.get().load(image).into(imageView);
        btnSuccess.setOnClickListener(v -> {
            swipeDismissDialog.dismiss();
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(successUrl));
            mainActivity.startActivity(intent);
            mainActivity.finish();
        });
    }
}
