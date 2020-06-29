package app.powered.by.burrhan.noteapp.Adapters;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import app.powered.by.burrhan.noteapp.R;


public class SliderAdapter extends PagerAdapter {
    Context context;

    LayoutInflater layoutInflater;
    public SliderAdapter(Context context) {
        this.context = context;
    }


    int images[] =
            {
                    R.drawable.ic_authentication,
                    R.drawable.ic_add,
                    R.drawable.ic_baseline_edit,
                    R.drawable.ic_save,
                    R.drawable.ic_close,
                    R.drawable.ic_baseline_delete
            };


    int headings[] =
            {
                    R.string.first_slide_title,
                    R.string.second_slide_title,
                    R.string.third_slide_title,
                    R.string.fourth_slide_title,
                    R.string.fifth_slide_title,
                    R.string.sixth_slide_title
            };

    int desciptions[] =
            {
                    R.string.first_slide_desc,
                    R.string.second_slide_desc,
                    R.string.third_slide_desc,
                    R.string.fourth_slide_desc,
                    R.string.fifth_slide_desc,
                    R.string.sixth_slide_desc
            };

    @Override
    public int getCount() {
        return headings.length;

//        we can write statically like 4 or 5 or number of length we know or we can use decs.length or may images
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==(ConstraintLayout) object;
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        View view= layoutInflater.inflate(R.layout.slides_layout,container,false);




        ImageView imageView = view.findViewById(R.id.slider_image);
        TextView heading = view.findViewById(R.id.slider_heading);
        TextView desc = view.findViewById(R.id.slider_desc);

        imageView.setImageResource(images[position]);
        heading.setText(headings[position]);
        desc.setText(desciptions[position]);

        container.addView(view);




        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ConstraintLayout)object);
    }
}
