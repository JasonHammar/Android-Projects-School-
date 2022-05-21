package edu.sjsu.android.zoodirectory;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.app.Activity;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<String> values;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView txtHeader;
        public TextView txtFooter;
        public View layout;
        public ImageView icon;
        public Context context;
        public ViewHolder(View v) {
            super(v);
            layout = v;
            icon = (ImageView) v.findViewById(R.id.icon);
            txtHeader = (TextView) v.findViewById(R.id.firstLine);
            txtFooter = (TextView) v.findViewById(R.id.secondLine);
            context = v.getContext();


        }
    }
    public void add(int position, String item) {
        values.add(position, item);
        notifyItemInserted(position);
    }
    public void remove(int position) {
        values.remove(position);
        notifyItemRemoved(position);
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(List<String> myDataset) {
        values = myDataset;
    }
    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.row_layout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final String name = values.get(position);
        holder.txtHeader.setText(name);
        if(holder.txtHeader.getText() == "Lion"){
            holder.icon.setImageResource(R.drawable.lion);
        }else if(holder.txtHeader.getText() == "Crocodile"){
            holder.icon.setImageResource(R.drawable.crocodile);
        }else if(holder.txtHeader.getText() == "Fruit Bat"){
            holder.icon.setImageResource(R.drawable.fruitbat);
        }else if(holder.txtHeader.getText() == "Koala"){
            holder.icon.setImageResource(R.drawable.koala);
        }else if(holder.txtHeader.getText() == "Silverback Gorilla"){
            holder.icon.setImageResource(R.drawable.silverback);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                if(holder.txtHeader.getText().toString().contains("Silverback Gorilla")){
                    AlertDialog alertDialog = new AlertDialog.Builder(view.getContext()).create();
                    alertDialog.setTitle("Zoo Directory");
                    alertDialog.setMessage("Warning, This animal is scary, do you want to continue?");
                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(view.getContext(), AnimalFacts.class);
                            intent.putExtra("name", holder.txtHeader.getText());
                            view.getContext().startActivity(intent);
                        }
                    });
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    alertDialog.show();
                }else{
                    Intent intent = new Intent(view.getContext(), AnimalFacts.class);
                    intent.putExtra("name", holder.txtHeader.getText());
                    view.getContext().startActivity(intent);
                }

            }
        });
        holder.txtFooter.setText(" ");
    }
    public void openDialog(){
        ScaryDialog scaryDialog = new ScaryDialog();

    }
    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return values.size();
    }

}
