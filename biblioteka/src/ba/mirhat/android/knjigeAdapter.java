package ba.mirhat.android;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class knjigeAdapter extends BaseAdapter{
	Context context;
	List<Knjiga> data = new ArrayList<Knjiga>();
    private static LayoutInflater inflater = null;

    public knjigeAdapter(Context context, List<Knjiga> data) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.data = data;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View vi = convertView;
        if (vi == null)
            vi = inflater.inflate(R.layout.knjiga_red, null);
        TextView naziv = (TextView) vi.findViewById(R.id.naziv);
        naziv.setText(data.get(position).getNaziv());
        TextView autor = (TextView) vi.findViewById(R.id.autor);
        autor.setText(data.get(position).getAutor());
        return vi;
    }

	
}
