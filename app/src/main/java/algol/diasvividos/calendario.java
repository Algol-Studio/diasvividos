package algol.diasvividos;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import java.util.Calendar;
import java.util.Date;

import android.os.Bundle;
import android.view.View.OnClickListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import static algol.diasvividos.R.anim.parado;

@SuppressLint("SimpleDateFormat")
public class calendario extends AppCompatActivity implements View.OnClickListener{

            private static String mdata ="";
            private boolean undo = false;
            private CaldroidFragment caldroidFragment;
            private CaldroidFragment dialogCaldroidFragment;

            private void setCustomResourceForDates() {
                Calendar cal = Calendar.getInstance();

                // Min date is last 7 days
                cal.add(Calendar.DATE, -7);
                Date blueDate = cal.getTime();

                // Max date is next 7 days
                cal = Calendar.getInstance();
                cal.add(Calendar.DATE, 7);
                Date greenDate = cal.getTime();

                /*if (caldroidFragment != null) {
                    ColorDrawable blue = new ColorDrawable(Color.BLUE);
                    ColorDrawable green = new ColorDrawable(Color.GREEN);
                    caldroidFragment.setBackgroundDrawableForDate(blue, blueDate);
                    caldroidFragment.setBackgroundDrawableForDate(green, greenDate);
                    caldroidFragment.setTextColorForDate(Color.WHITE, blueDate);
                    caldroidFragment.setTextColorForDate(Color.WHITE, greenDate);
                }*/
            }

            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                overridePendingTransition(R.anim.fadein, parado);
                setContentView(R.layout.activity_calendario);
                final Button calen = (Button) findViewById(R.id.button2);
                calen.setOnClickListener((OnClickListener) this);
                String data = getIntent().getStringExtra("diaIn");
                setData(data);
                final SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");

                // //////////////////////////////////////////////////////////////////////
                // **** This is to show customized fragment. If you want customized
                // version, uncomment below line ****
		 caldroidFragment = new CaldroidSampleCustomFragment();

                // Setup arguments

                // If Activity is created after rotation
                if (savedInstanceState != null) {
                    caldroidFragment.restoreStatesFromKey(savedInstanceState,
                            "CALDROID_SAVED_STATE");
                }
                // If activity is created from fresh
                else {
                    Bundle args = new Bundle();
                    Calendar cal = Calendar.getInstance();
                    args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
                    args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
                    args.putBoolean(CaldroidFragment.ENABLE_SWIPE, true);
                    args.putBoolean(CaldroidFragment.SIX_WEEKS_IN_CALENDAR, true);

                    // Uncomment this to customize startDayOfWeek
                    // args.putInt(CaldroidFragment.START_DAY_OF_WEEK,
                    // CaldroidFragment.TUESDAY); // Tuesday

                    // Uncomment this line to use Caldroid in compact mode
                    // args.putBoolean(CaldroidFragment.SQUARE_TEXT_VIEW_CELL, false);

                    // Uncomment this line to use dark theme
//            args.putInt(CaldroidFragment.THEME_RESOURCE, com.caldroid.R.style.CaldroidDefaultDark);

                    caldroidFragment.setArguments(args);
                }

                setCustomResourceForDates();

                // Attach to the activity
                FragmentTransaction t = getSupportFragmentManager().beginTransaction();
                t.replace(R.id.calendarView, caldroidFragment);
                t.commit();

                // Setup listener
                /*final CaldroidListener listener = new CaldroidListener() {

                    @Override
                    public void onSelectDate(Date date, View view) {
                        Toast.makeText(getApplicationContext(), formatter.format(date),
                                Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onChangeMonth(int month, int year) {
                        String text = "month: " + month + " year: " + year;
                        Toast.makeText(getApplicationContext(), text,
                                Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onLongClickDate(Date date, View view) {
                        Toast.makeText(getApplicationContext(),
                                "Long click " + formatter.format(date),
                                Toast.LENGTH_SHORT).show();
                    }

                };*/

                // Setup Caldroid
                //caldroidFragment.setCaldroidListener(listener);

                final Bundle state = savedInstanceState;
            }

            /**
             * Save current states of the Caldroid here
             */
            @Override
            protected void onSaveInstanceState(Bundle outState) {
                // TODO Auto-generated method stub
                super.onSaveInstanceState(outState);

                if (caldroidFragment != null) {
                    caldroidFragment.saveStatesToKey(outState, "CALDROID_SAVED_STATE");
                }

                if (dialogCaldroidFragment != null) {
                    dialogCaldroidFragment.saveStatesToKey(outState,
                            "DIALOG_CALDROID_SAVED_STATE");
                }
            }

    public void setData(String data){
        this.mdata=data;
    }

    public static String getMdata(){
        return mdata;
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button2:
                Intent intent = new Intent(getApplicationContext(), Nascimento_input.class);
                finish();
                overridePendingTransition(parado,R.anim.fadeout);
                startActivity(intent);
                break;
        }
    };

        }
/*    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein, parado);
        setContentView(R.layout.activity_calendario);
        final Button calen = (Button) findViewById(R.id.button2);
        calen.setOnClickListener((OnClickListener) this);
        String data = getIntent().getStringExtra("diaIn");
        CaldroidFragment caldroidFragment = new CaldroidFragment();
        Bundle args = new Bundle();
        Calendar cal = Calendar.getInstance();
        args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
        args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
        caldroidFragment.setArguments(args);

        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        t.replace(R.id.calendarView, caldroidFragment);
        t.commit();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button2:
                Intent intent = new Intent(getApplicationContext(), Nascimento_input.class);
                finish();
                overridePendingTransition(parado,R.anim.fadeout);
                startActivity(intent);
                break;
        }
    };

}
*/