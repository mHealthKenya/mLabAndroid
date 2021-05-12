package com.example.kenweezy.mytablayouts.hts;

import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.kenweezy.mytablayouts.R;
import com.example.kenweezy.mytablayouts.RecyclerListener.RecyclerTouchListener;
import com.example.kenweezy.mytablayouts.adapters.HtsAdapter;
import com.example.kenweezy.mytablayouts.messagedialog.MessageDialog;
import com.example.kenweezy.mytablayouts.models.Htsmodel;
import com.example.kenweezy.mytablayouts.tables.Htsresults;

import java.util.ArrayList;
import java.util.List;

public class HtsPositiveFragment extends Fragment {
    View v;

    private RecyclerView recyclerView;

    private HtsAdapter adapter;
    private ArrayList<Htsmodel> itemsList;
    MessageDialog mdialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.hts_results_positive,container,false);

        initialise();
        setMyAdapter();
        setMyRecyclerView();
        getResults();
        setRecyclerClickListener();

        return  v;

    }


    public void setRecyclerClickListener(){

        try{




            recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new RecyclerTouchListener.ClickListener() {
                @Override
                public void onClick(View view, int position) {

                    Htsmodel mitem=itemsList.get(position);
                    String mclient=mitem.getClientCode();
                    String mgender=mitem.getGender();
                    String mage=mitem.getAge();
                    String mresult=mitem.getResult();
                    String msubmitted=mitem.getSubmitted();
                    String mreleased=mitem.getReleased();

                    mdialog.displayHtsMessage(mclient,mgender,mage,mresult,msubmitted,mreleased);


                }

                @Override
                public void onLongClick(View view, int position) {

                    Htsmodel mitem=itemsList.get(position);

                    Toast.makeText(getActivity(), "long clicked on item "+mitem.getClientCode(), Toast.LENGTH_SHORT).show();


                }
            }));



        }
        catch(Exception e){



        }
    }



    public void initialise(){

        try{

            mdialog=new MessageDialog(getActivity());
            recyclerView = (RecyclerView) v.findViewById(R.id.hts_positive_recycler_view);

            itemsList = new ArrayList<>();



            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(10), true));
            recyclerView.setItemAnimator(new DefaultItemAnimator());


        }
        catch(Exception e){



        }
    }




    private void getResults(){


        try {

            List<Htsresults> myl=Htsresults.findWithQuery(Htsresults.class,"select * from Htsresults where result=?","Positive");
            for(int x=0;x<myl.size();x++){
                String cc=myl.get(x).getClientcode();
                String gender=myl.get(x).getGender();
                String age=myl.get(x).getAge();
                String result=myl.get(x).getResult();
                String submitted=myl.get(x).getSubmitted();
                String released=myl.get(x).getReleased();

                Htsmodel hm= new Htsmodel(cc,gender,age,result,submitted,released);
                itemsList.add(hm);

            }

//            Htsmodel bm= new Htsmodel("client one","M","45","Negative","2018-02-08","2018-02-08");
//
//            Htsmodel bm1= new Htsmodel("client two","M","45","Positive","2018-02-08","2018-02-08");
//
//            itemsList.add(bm);
//            itemsList.add(bm1);





        } catch (Exception e) {

        }

        adapter.notifyDataSetChanged();

    }







    public void setMyAdapter(){

        try{

            adapter = new HtsAdapter(getActivity(), itemsList);
        }
        catch(Exception e){

            Toast.makeText(getActivity(), "error setting adapter", Toast.LENGTH_SHORT).show();
        }
    }

    public void setMyRecyclerView(){

        try{


            recyclerView.setAdapter(adapter);
        }
        catch(Exception e){

            Toast.makeText(getActivity(), "error setting recyclerview", Toast.LENGTH_SHORT).show();
        }
    }





    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}
