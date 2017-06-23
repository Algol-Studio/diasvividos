package algol.diasvividos;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Nascimento_input extends AppCompatActivity implements View.OnClickListener{

    private InterstitialAd mInterstitialAd;
    static final String DATA = "data";
    static final String HORA = "hora";
    public static final String MyPREFERENCES = "MyPrefs" ;
    int adban,adinter;

    public static int getmJD() {
        return mJD;
    }

    public static void setmJD(int mJD) {
        Nascimento_input.mJD = mJD;
    }

    private static int mJD = 0;
    //public final ConstraintLayout adscontainer = (ConstraintLayout) findViewById(R.id.layoutao);
    SharedPreferences sharedPreferences;// = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nascimento_input);
        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String tex=sharedPreferences.getString(HORA,null);
        String tex1=sharedPreferences.getString(DATA,null);
        if(tex!=null) {
            ((EditText) findViewById(R.id.data)).setText(tex1);
            ((EditText) findViewById(R.id.hora)).setText(tex);
        }
        else{
            ((EditText) findViewById(R.id.data)).setText("01/01/2001");
            ((EditText) findViewById(R.id.hora)).setText("00:00");
        }
        /*mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-4165215562050947/2874666510");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }
        });*/
        MobileAds.initialize(this,"ca-app-pub-4165215562050947~5825119711");
        final EditText Edit_Time = (EditText) findViewById(R.id.hora);
        Edit_Time.setOnClickListener(this);
        final EditText Edit_Data = (EditText) findViewById(R.id.data);
        Edit_Data.setOnClickListener(this);
        final Button calen = (Button) findViewById(R.id.calen);
        calen.setOnClickListener(this);
        final AdView adView = (AdView)findViewById(R.id.adView2);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        adView.setOnClickListener(this);
        mudaResultado();
    }

    @Override
    public void recreate() {
        super.recreate();
    }

    @Override
        public void onClick(View v){
            /*if (mInterstitialAd.isLoaded() || adinter<3) {
                mInterstitialAd.show();
                adinter++;
            } else {
                Log.d("TAG", "The interstitial wasn't loaded yet.");
            }*/
            switch (v.getId()) {
                case R.id.calen:
                    Intent intent = new Intent(getApplicationContext(),calendario.class);
                    String diaIn = ((TextView) findViewById(R.id.data)).getText().toString();
                    intent.putExtra("diaIn",diaIn);
                    startActivity(intent);
                    break;
                case R.id.hora:
                    Calendar mcurrentTime = getCalen();
                    int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                    int minute = mcurrentTime.get(Calendar.MINUTE);

                    TimePickerDialog mTimePicker;
                    mTimePicker = new TimePickerDialog(Nascimento_input.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                            String tex;
                            if (selectedMinute>9) {
                                if (selectedHour > 9) {
                                    tex=selectedHour + ":" + selectedMinute;
                                    //((EditText) findViewById(R.id.hora)).setText();
                                }else {
                                    tex="0"+selectedHour + ":" + selectedMinute;
                                    //((EditText) findViewById(R.id.hora)).setText("0"+selectedHour + ":" + selectedMinute);
                                }
                                }
                            else {
                                if (selectedHour > 9) {
                                    tex=selectedHour + ":0" + selectedMinute;
                                    //((EditText) findViewById(R.id.hora)).setText(selectedHour + ":0" + selectedMinute);
                                }else{
                                    tex="0"+selectedHour + ":0" + selectedMinute;
                                    //((EditText) findViewById(R.id.hora)).setText("0"+selectedHour + ":" + selectedMinute);
                                }
                            }
                            ((EditText) findViewById(R.id.hora)).setText(tex);
                            sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor=sharedPreferences.edit();
                            editor.putString(HORA,tex);
                            editor.putString(DATA,((TextView) findViewById(R.id.data)).getText().toString());
                            editor.commit();
                            mudaResultado();
                        }
                    }, hour, minute, true);
                    mTimePicker.setTitle("Que horas você nasceu?");
                    mTimePicker.show();
                    break;
                case R.id.data:
                    Calendar mcurrentDay = getCalen();
                    int ano = mcurrentDay.get(Calendar.YEAR);
                    int mes = mcurrentDay.get(Calendar.MONTH);
                    int dia = mcurrentDay.get(Calendar.DAY_OF_MONTH);
                    DatePickerDialog mDatePicker;
                    mDatePicker = new DatePickerDialog(Nascimento_input.this,android.R.style.Theme_Holo_Dialog, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int selectedYear, int selectedMounth, int selectedDay) {
                            String tex;
                            if(selectedDay>9) {
                                if (selectedMounth > 8) {
                                    tex=selectedDay + "/" + (selectedMounth + 1) + "/" + selectedYear;
                                    //((EditText) findViewById(R.id.data)).setText(selectedDay + "/" + (selectedMounth + 1) + "/" + selectedYear);
                                }else{
                                    tex=selectedDay + "/0" + (selectedMounth + 1) + "/" + selectedYear;
                                    //((EditText) findViewById(R.id.data)).setText(selectedDay + "/0" + (selectedMounth + 1) + "/" + selectedYear);
                                }
                            }else{
                                if (selectedMounth > 8) {
                                    tex="0"+selectedDay + "/" + (selectedMounth + 1) + "/" + selectedYear;
                                    //((EditText) findViewById(R.id.data)).setText("0"+selectedDay + "/" + (selectedMounth + 1) + "/" + selectedYear);
                                }else{
                                    tex="0"+selectedDay + "/0" + (selectedMounth + 1) + "/" + selectedYear;
                                    //((EditText) findViewById(R.id.data)).setText("0"+selectedDay + "/0" + (selectedMounth + 1) + "/" + selectedYear);
                                }
                            }
                            ((EditText) findViewById(R.id.data)).setText(tex);
                            sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor=sharedPreferences.edit();
                            editor.putString(DATA,tex);
                            editor.putString(HORA,((TextView) findViewById(R.id.hora)).getText().toString());
                            editor.commit();
                            mudaResultado();
                        }
                    }, ano, mes, dia);
                    mDatePicker.setTitle("Que dia você nasceu?");
                    mDatePicker.show();
                    break;
                case R.id.adView2:
                    adban++;
                    if(adban>3){
                        ConstraintLayout adscontainer = (ConstraintLayout) findViewById(R.id.layoutao);
                        View admobAds = findViewById(R.id.adView2);
                        adscontainer.removeView(admobAds);;
                    }
                    break;
                default:
                    break;
            }
        }

        public Calendar getCalen(){
            String diaIn=((TextView) findViewById(R.id.data)).getText().toString();
            int dia1= Integer.parseInt(diaIn.substring(0,diaIn.indexOf("/")));
            diaIn=diaIn.substring(diaIn.indexOf("/")+1);
            int mes1= Integer.parseInt(diaIn.substring(0,diaIn.indexOf("/")))-1;
            int ano1=Integer.parseInt(diaIn.substring(diaIn.indexOf("/")+1));
            String horaIn=((TextView) findViewById(R.id.hora)).getText().toString();
            int hora1= Integer.parseInt(horaIn.substring(0,horaIn.indexOf(":")));
            int minuto1=Integer.parseInt(horaIn.substring(horaIn.indexOf(":")+1));
            Calendar calendar = new GregorianCalendar(ano1,mes1,dia1,hora1,minuto1);
            return calendar;
        };

        public void mudaResultado(){
            Calendar mcurrent = Calendar.getInstance();
            int hour = mcurrent.get(Calendar.HOUR_OF_DAY);
            int minute = mcurrent.get(Calendar.MINUTE);
            int second = mcurrent.get(Calendar.SECOND);
            int ano = mcurrent.get(Calendar.YEAR);
            int mes = mcurrent.get(Calendar.MONTH);
            int dia = mcurrent.get(Calendar.DAY_OF_MONTH);
            Calendar calendar = new GregorianCalendar(ano,mes,dia,hour,minute,second);
            double atual = GetJulianDate(calendar);
            calendar = getCalen();
            double nasceu = GetJulianDate(calendar);
            setmJD((int) nasceu);
            ((TextView) findViewById(R.id.Dias)).setText(String.valueOf((int) (atual-nasceu)));
            ((TextView) findViewById(R.id.Meses)).setText(String.valueOf((int) (atual/30.4375-nasceu/30.4375)));
            ((TextView) findViewById(R.id.Semanas)).setText(String.valueOf((int) (atual/7-nasceu/7)));
            ((TextView) findViewById(R.id.Horas)).setText(String.valueOf((int) (atual*24-nasceu*24)));
            ((TextView) findViewById(R.id.Minutos)).setText(String.valueOf((int) (atual*1440-nasceu*1440)));
            ((TextView) findViewById(R.id.Segundos)).setText(String.valueOf((int) (atual*86400-nasceu*86400)));
        }

    public void onSaveInstanceState(Bundle savedInstanceState){
        String diaIn=((TextView) findViewById(R.id.data)).getText().toString();
        String dia1= diaIn.substring(0,diaIn.indexOf("/"));
        diaIn=diaIn.substring(diaIn.indexOf("/")+1);
        String mes1= Integer.toString(Integer.parseInt(diaIn.substring(0,diaIn.indexOf("/")))-1);
        String ano1=diaIn.substring(diaIn.indexOf("/")+1);
        String horaIn=((TextView) findViewById(R.id.hora)).getText().toString();
        String hora1= horaIn.substring(0,horaIn.indexOf(":"));
        String minuto1=horaIn.substring(horaIn.indexOf(":")+1);
        if(Integer.parseInt(mes1)>9) {
            if(Integer.parseInt(dia1)>9) {
                savedInstanceState.putString(DATA, dia1 + "/" + mes1 + "/" + ano1);
            }else{savedInstanceState.putString(DATA, "0"+dia1 + "/" + mes1 + "/" + ano1);}
        }else{
            if (Integer.parseInt(dia1) > 9) {
                savedInstanceState.putString(DATA, dia1 + "/0" + mes1 + "/" + ano1);
            }else{savedInstanceState.putString(DATA, "0"+dia1 + "/0" + mes1 + "/" + ano1);
            }
        }
        if(Integer.parseInt(minuto1)<10) {
            if (Integer.parseInt(hora1) > 9) {
                savedInstanceState.putString(HORA, hora1 + ":0" + minuto1);
            } else {
                savedInstanceState.putString(HORA, "0"+hora1 + ":0" + minuto1);
            }
        }else{
            if (Integer.parseInt(hora1) > 9) {
                savedInstanceState.putString(HORA, hora1 + ":" + minuto1);
            } else {
                savedInstanceState.putString(HORA, "0"+hora1 + ":" + minuto1);
        }
    }
        super.onSaveInstanceState(savedInstanceState);};

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