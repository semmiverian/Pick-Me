package id.semmi.pickme.vote.add_vote;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import id.semmi.pickme.DialogHelper;
import id.semmi.pickme.R;
import id.semmi.pickme.dagger.PickMeApplication;

public class AddVoteActivity extends AppCompatActivity implements AddVoteView, AddVoteAdapter.OnAddMoreItemListener {
    private static final String TAG = AddVoteActivity.class.toString();
    @BindView(R.id.nameInput) AppCompatEditText nameInput;
    @BindView(R.id.describeInput) AppCompatEditText descriptionInput;
    @BindView(R.id.addVoteRecyclerView) RecyclerView recyclerView;
    @BindView(R.id.create_vote_button) AppCompatButton createVoteButton;
    @BindView(R.id.endDatePicker) AppCompatButton endDatePicker;
    @Inject AddVotePresenter addVotePresenter;

    private Unbinder unbind;
    private ArrayList<Vote> mVoteList = new ArrayList<>();
    private AddVoteAdapter mAddVoteAdapter;
    private DialogHelper dialogHelper;
    private MaterialDialog dialog;
    private String chosenDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vote);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ((PickMeApplication) getApplication()).getApplicationComponent().inject(this);

        dialogHelper = new DialogHelper(this);

        mVoteList.add(new Vote("Add Button"));

        unbind = ButterKnife.bind(this);
        mAddVoteAdapter = new AddVoteAdapter(this, mVoteList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(mAddVoteAdapter);
        mAddVoteAdapter.setAddMoreItemListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        addVotePresenter.setView(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbind.unbind();
    }

    @Override
    public String getVoteName() {
        return nameInput.getText().toString();
    }

    @Override
    public String getVoteDescription() {
        return descriptionInput.getText().toString();
    }


    @Override
    public void onAddMoreItemListener(View view, int position) {
        dialogHelper.inputDialog("Please Input Your Vote Option", "Vote Option", InputType.TYPE_CLASS_TEXT, new MaterialDialog.InputCallback() {
            @Override
            public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                mVoteList.add(unshiftIndex(), new Vote(input.toString()));
                mAddVoteAdapter.notifyItemInserted(unshiftIndex());
            }
        });
    }

    @OnClick(R.id.create_vote_button)
    public void onCreateVoteButton(View v) {
        dialog = dialogHelper.loadingDialog("Loading", "Processing your data");
        mVoteList.remove(mVoteList.size() - 1);
        addVotePresenter.createVote(mVoteList, chosenDate);
    }

    @OnClick(R.id.endDatePicker)
    public void onDatePickerClick(View v) {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        chosenDate =  "" + dayOfMonth + "/" + (++monthOfYear) + "/" + year;
                        endDatePicker.setText(chosenDate);
                    }
                },
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.setAccentColor(Color.parseColor("#4CAF50"));
        dpd.setMinDate(now);
        dpd.setTitle("Your Deadline of this Ads");
        dpd.show(getFragmentManager(), "Your Deadline of this Ads ");
    }

    @Override
    public void onSuccess(String message) {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
        dialogHelper.singlePositiveDialog("Success", message, "Ok", new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                dialog.dismiss();
            }
        });
    }

    @Override
    public void onError(String message) {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
        dialogHelper.singlePositiveDialog("Error", message, "Try Again", new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                dialog.dismiss();
            }
        });
    }

    private int unshiftIndex() {
        return mVoteList.size() -1;
    }
}
