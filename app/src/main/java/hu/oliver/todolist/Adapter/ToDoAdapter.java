package hu.oliver.todolist.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import hu.oliver.myapplication.R;
import hu.oliver.todolist.AddNewTask;
import hu.oliver.todolist.MainActivity;
import hu.oliver.todolist.Model.ToDoModel;
import hu.oliver.todolist.Utils.DatabaseHandler;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ViewHolder> {

    private List<ToDoModel> toDoList;
    private MainActivity activity;
    private DatabaseHandler db;


    // MAIN METHODS

    public ToDoAdapter(DatabaseHandler db, MainActivity activity) {
        this.db = db;
        this.activity = activity;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_layout, parent, false);
        return new ViewHolder(itemView);
    }

    // standard impl. of RecyclerView adapter
    public void onBindViewHolder(ViewHolder holder, int position) {
        db.openDatabase();
        ToDoModel item = toDoList.get(position);
        holder.task.setText(item.getTask());
        holder.task.setChecked(toBoolean(item.getStatus()));
        holder.task.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()) {
                    db.updateStatus(item.getId(), 1);
                } else {
                    db.updateStatus(item.getId(), 0);
                }
            }
        });
    }


    // GET / SET / TO METHODS

    public int getItemCount() {
        return toDoList.size();
    }

    private boolean toBoolean(int n) {
        return n != 0;
    }

    public Context getContext() {
        return activity;
    }

    public void setTasks(List<ToDoModel> toDoList) {
        this.toDoList = toDoList;
        notifyDataSetChanged();
    }


    // CRUD

    public void editItem(int position) {
        ToDoModel item = toDoList.get(position);

        Bundle bundle = new Bundle();
        bundle.putInt("id", item.getId());
        bundle.putString("task", item.getTask());

        AddNewTask fragment = new AddNewTask();
        fragment.setArguments(bundle);
        fragment.show(activity.getSupportFragmentManager(), AddNewTask.TAG);
    }

    public void deleteItem(int position) {
        ToDoModel item = toDoList.get(position);
        db.deleteTask(item.getId());

        toDoList.remove(position);
        notifyItemRemoved(position);
    }


    // STATIC CLASS

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox task;
        public ViewHolder(View itemView) {
            super(itemView);
            task = itemView.findViewById(R.id.todoCheckbox);
        }
    }

}
