package algol.diasvividos;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;

public class Nascimento_input extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nascimento_input);
        final EditText Edit_Time = (EditText) findViewById(R.id.hora);
        Edit_Time.setOnClickListener(this);
        final EditText Edit_Data = (EditText) findViewById(R.id.data);
        Edit_Data.setOnClickListener(this);


    }
        @Override
        public void onClick(View v){
            switch (v.getId()) {
                case R.id.hora:
                    Calendar mcurrentTime = Calendar.getInstance();
                    int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                    int minute = mcurrentTime.get(Calendar.MINUTE);

                    TimePickerDialog mTimePicker;
                    mTimePicker = new TimePickerDialog(Nascimento_input.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                            ((EditText) findViewById(R.id.hora)).setText(selectedHour + ":" + selectedMinute);
                        }
                    }, hour, minute, true);
                    mTimePicker.setTitle("Que horas você nasceu?");
                    mTimePicker.show();
                    break;
                case R.id.data:
                    ((EditText)findViewById(R.id.data)).setText("20000");
                    break;
                default:
                    break;
            }
        }

/*    Calendar myCalendar = Calendar.getInstance();
   // DatePicker DatePickerDialog = (DatePicker) findViewById(R.id.datePicker);
    EditText edittext = (EditText) findViewById(R.id.data);

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }

    };

   edittext.setOnClickListener(new OnClickListener() {
        //@Override
        public void onClick(View view) {
            new DatePickerDialog(classname.this, date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        }
    });

    private void updateLabel() {

        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        edittext.setText(sdf.format(myCalendar.getTime()));
    }

    EditText data,hora;
    int year,month,day;

    public void showDate(){
        data = (EditText)findViewById(R.id.data);
        data.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View V){
                        aparecedia(findViewById(datePicker))
                    }
                }
        );
    }
}
*/


    public static double GetJulianDate(Calendar calendarDate){

        int year = calendarDate.get(Calendar.YEAR);
        int month = calendarDate.get(Calendar.MONTH) + 1;
        int day = calendarDate.get(Calendar.DAY_OF_MONTH);
        double hour = calendarDate.get(Calendar.HOUR_OF_DAY);
        double minute = calendarDate.get(Calendar.MINUTE);
        double second = calendarDate.get(Calendar.SECOND);
        int isGregorianCal = 1;
        int A;
        int B;
        int C;
        int D;
        double fraction = day + ((hour + (minute / 60) + (second / 60 / 60)) / 24);

        if (year < 1582)
        {
            isGregorianCal = 0;
        }

        if (month < 3)
        {
            year = year - 1;
            month = month + 12;
        }

        A = year / 100;
        B = (2 - A + (A / 4)) * isGregorianCal;

        if (year < 0)
        {
            C = (int)((365.25 * year) - 0.75);
        }
        else
        {
            C = (int)(365.25 * year);
        }

        D = (int)(30.6001 * (month + 1));
        double JD = B + C + D + 1720994.5 + fraction;

        return JD;
}}
