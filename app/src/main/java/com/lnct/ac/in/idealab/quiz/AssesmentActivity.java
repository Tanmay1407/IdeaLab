package com.lnct.ac.in.idealab.quiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.lnct.ac.in.idealab.R;
import com.lnct.ac.in.idealab.models.QuestionModel;

import java.util.ArrayList;

public class AssesmentActivity extends AppCompatActivity {

    RecyclerView q_rv;
    LinearLayout view_holder;
    TextView submit_btn;
    RadioGroup rg;
    SnapHelper snap_helper;
    EditText aet;

    TextView qtv, btn;

    private int ques_pos;
    ArrayList<QuestionModel> model_list;

    String[] ans;
    RadioButton[] rblist;

    ViewGroup.LayoutParams parm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assesment);

//        q_rv = findViewById(R.id.q_rv);
//        view_holder = findViewById(R.id.view_holder);
//        submit_btn = findViewById(R.id.submit_btn);

//        snap_helper = new PagerSnapHelper();

//        View mcq_view = getLayoutInflater().inflate(R.layout.layout_mcq_question, null);
//        View desc_view = getLayoutInflater().inflate(R.layout.layout_mcq_question, null);

        rblist = new RadioButton[]{new RadioButton(this), new RadioButton(this), new RadioButton(this), new RadioButton(this), new RadioButton(this)};

        rg = findViewById(R.id.rg);
        aet = findViewById(R.id.aet);
        btn = findViewById(R.id.save_btn);
        qtv = findViewById(R.id.qtv);

        for(RadioButton rbn: rblist) {
            rbn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            rbn.setTextColor(getResources().getColor(R.color.logo_blue, getTheme()));
        }

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(model_list.get(ques_pos).isMcq()) {
                    int id = rg.getCheckedRadioButtonId();
                    int idx = rg.indexOfChild(rg.findViewById(id));
                    if(idx != -1) ans[ques_pos] = idx+" "+model_list.get(ques_pos).getOptions().get(idx);
                    else ans[ques_pos] = idx+" ";
                }
                else {
                    ans[ques_pos] = aet.getText().toString();
                }

                ques_pos++;
                if(ques_pos < model_list.size()) {
                    loadQuestion(ques_pos);
                }
                else {
                    StringBuilder sb = new StringBuilder();
                    for (String k: ans) {
                        sb.append(k + "\n");
                    }
                    Log.i("ans of quiz", sb.toString());
                    Toast.makeText(AssesmentActivity.this, "Finished", Toast.LENGTH_SHORT).show();
                }
            }
        });

//        submit_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(model_list.get(ques_pos).isMcq()) {
//                }
//            }
//        });

        ArrayList<String> opt = new ArrayList<>();
        opt.add("option 1qwertyuioplkjhgfdsazxcvbnm");
        opt.add("option 2");
        opt.add("option 3");
        QuestionModel q1 = new QuestionModel("This is Question number 1", false, null);
        QuestionModel q2 = new QuestionModel("This is Question number 2", true, opt);


        model_list = new ArrayList<>();
        model_list.add(q1);
        model_list.add(q2);
        model_list.add(q1);
        model_list.add(q2);
        model_list.add(q1);
        model_list.add(q2);
        model_list.add(q1);
//        model_list.add(q2);
//        model_list.add(q1);
//        model_list.add(q2);
//        model_list.add(q1);
//        model_list.add(q2);
//        model_list.add(q1);
//        model_list.add(q2);

        ans = new String[model_list.size()];

//        Adapter adap = new Adapter(this, lst);
//        q_rv.getRecycledViewPool().setMaxRecycledViews(0, 1);
//        q_rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
//        q_rv.setAdapter(adap);
//        snap_helper.attachToRecyclerView(q_rv);
//        q_rv.getRecycledViewPool().setMaxRecycledViews(TYPE_VIDEO_VIEW, 0);
//        q_rv.getRecycledViewPool().setMaxRecycledViews(, 0);

        ques_pos = 0;
        loadQuestion(ques_pos);

    }

    public void loadQuestion(int i) {
        if(model_list.get(i).isMcq()) {
            aet.setVisibility(View.GONE);
            rg.setVisibility(View.VISIBLE);

            rg.removeAllViews();

            ArrayList<String> opt = model_list.get(i).getOptions();

            for(int j=0; j<opt.size(); j++) {
                RadioButton rb = rblist[j];
                rb.setChecked(false);
                if(rb.getParent() != null) ((ViewGroup)rb.getParent()).removeView(rb);
                rb.setText(opt.get(j));
                rg.addView(rb, j, new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
            }

        }
        else {
            aet.setVisibility(View.VISIBLE);
            rg.setVisibility(View.GONE);
            aet.setText("");
        }

        qtv.setText(model_list.get(i).getQ());
    }

    class Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private final int MCQ = 0;
        private final int DESC = 1;
        Context c;
        ArrayList<QuestionModel> list;
        String[] ans;
        private RadioButton[] rblist;
        int pos;

        public Adapter(Context c, ArrayList<QuestionModel> list) {
            this.c = c;
            this.list = list;
            ans = new String[list.size()+5];
            pos = 0;
            rblist = new RadioButton[]{new RadioButton(c), new RadioButton(c), new RadioButton(c), new RadioButton(c), new RadioButton(c), new RadioButton(c), new RadioButton(c), new RadioButton(c), new RadioButton(c), new RadioButton(c), new RadioButton(c), new RadioButton(c),new RadioButton(c), new RadioButton(c), new RadioButton(c), new RadioButton(c), new RadioButton(c), new RadioButton(c), new RadioButton(c), new RadioButton(c), new RadioButton(c), new RadioButton(c), new RadioButton(c), new RadioButton(c), new RadioButton(c), new RadioButton(c), new RadioButton(c), new RadioButton(c), new RadioButton(c), new RadioButton(c), new RadioButton(c), new RadioButton(c), new RadioButton(c), new RadioButton(c), new RadioButton(c), new RadioButton(c)};
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v;
            switch(viewType) {
                case MCQ:
                    v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_mcq_question, parent, false);
                    return new McqHolder(v);
                case DESC:
                    v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_desc_question, parent, false);
                    return new DescHolder(v);
            }
            return null;
        }


        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            if (holder.getItemViewType() == MCQ) {
                McqHolder hold = (McqHolder) holder;
                hold.qtv.setText(list.get(position).getQ());
                hold.rg.removeAllViews();
                ArrayList<String> opt = list.get(position).getOptions();
                try {
                    int check = Integer.parseInt(ans[hold.getBindingAdapterPosition() - 1]);
                } catch(Exception e){}
                finally {

                }

                for(int i=0; i<opt.size(); i++) {
                    if(pos >= 12) pos = 0;
                    RadioButton rb = rblist[pos++];
                    rb.setChecked(false);
                    if(rb.getParent() != null) ((ViewGroup)rb.getParent()).removeView(rb);
                    rb.setText(opt.get(i));
                    hold.rg.addView(rb, i, new ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT));
                    int finalI = i;
//                    rb.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            rb.setChecked(true);
//                            Log.i("dynamic radio button", finalI +"");
//                            ans[hold.getBindingAdapterPosition()-1] = finalI +"";
//                        }
//                    });
                }

                hold.btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int id = hold.rg.getCheckedRadioButtonId();
                        int idx = hold.rg.indexOfChild(hold.rg.findViewById(id));
                        ans[hold.getBindingAdapterPosition()-1] = idx+"";
                        Log.i("ans ans", "option: "+(idx)+"  ");
                    }
                });

//                hold.rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//                    @Override
//                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
//                        int id = radioGroup.getCheckedRadioButtonId();
//                        int idx = radioGroup.indexOfChild(radioGroup.findViewById(id));
//                        ans[hold.getBindingAdapterPosition()-1] = i+"";//+opt.get(i-1);
////                        ans.set(hold.getAdapterPosition(), "option: "+(i+1)+"  "+opt.get(i));
//                        Log.i("ans   ans", "option: "+(idx)+"  ");//+opt.get(i-1));
//                    }
//                });
            }
            else {
                DescHolder hold = (DescHolder) holder;
                hold.qtv.setText(list.get(position).getQ());
                hold.btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ans[hold.getBindingAdapterPosition()] = hold.aet.getText().toString();
//                            ans.set(holder.getBindingAdapterPosition() , hold.aet.getText().toString());
                        Log.i("ans   ans", hold.aet.getText().toString());
                    }
                });
            }
        }

        @Override
        public int getItemViewType(int position) {
            if(list.get(position).isMcq()) return MCQ;
            return DESC;
        }

        @Override
        public int getItemCount() {
            return list.size();
//            return 1;
        }

        class McqHolder extends RecyclerView.ViewHolder {

            TextView qtv, btn;
            RadioGroup rg;

            public McqHolder(@NonNull View itemView) {
                super(itemView);
                qtv = itemView.findViewById(R.id.qtv);
                rg = itemView.findViewById(R.id.rg);
                btn = itemView.findViewById(R.id.save_btn);
            }
        }

        class DescHolder extends RecyclerView.ViewHolder {

            TextView qtv, btn;
            EditText aet;

            public DescHolder(@NonNull View itemView) {
                super(itemView);
                qtv = itemView.findViewById(R.id.qtv);
                aet = itemView.findViewById(R.id.aet);
                btn = itemView.findViewById(R.id.save_btn);
            }
        }

    }

}