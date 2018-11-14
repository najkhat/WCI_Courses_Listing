package Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.mysummerproject.secattempt.R;
import java.util.List;
import Model.Courses;


class SearchViewHolder extends RecyclerView.ViewHolder{

    public TextView code,name,subject, grade,type, description;

    public SearchViewHolder(View itemView) {
        super(itemView);

        code = (TextView)itemView.findViewById(R.id.code);
        subject = (TextView)itemView.findViewById(R.id.subject);
        name = (TextView)itemView.findViewById(R.id.name);
        type = (TextView)itemView.findViewById(R.id.type);
        grade = (TextView)itemView.findViewById(R.id.grade);
        description = (TextView)itemView.findViewById(R.id.description);
    }
}

public class SearchAdapter extends RecyclerView.Adapter<SearchViewHolder>{

    private Context context;
    private List<Courses> courses;

    public SearchAdapter(Context context, List<Courses> courses) {
        this.context = context;
        this.courses = courses;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.activity_main,parent,false);
        return( new SearchViewHolder(itemView));
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {

        holder.code.setText(courses.get(position).getCode());
        holder.name.setText(courses.get(position).getName());
        holder.subject.setText(courses.get(position).getSubject());
        holder.type.setText(courses.get(position).getType());
        holder.grade.setText(courses.get(position).getGrade());
        holder.description.setText(courses.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return(courses.size());
    }
}
