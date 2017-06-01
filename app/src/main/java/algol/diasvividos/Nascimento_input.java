package algol.diasvividos;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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

public class Nascimento_input extends AppCompatActivity implements View.OnClickListener,RewardedVideoAdListener {

    private InterstitialAd mInterstitialAd;
    private Button mWatchButton;
    private RewardedVideoAd mAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nascimento_input);
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3080914425533160/3058456934");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }
        });
        mWatchButton=(Button) findViewById(R.id.botao);
        mAd = MobileAds.getRewardedVideoAdInstance(this);
        mAd.setRewardedVideoAdListener(this);
        loadAd();
        //mWatchButton.setEnabled(false);
        mWatchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAd.isLoaded()){
                    mAd.show();
                }
            }
        });
        MobileAds.initialize(this,"ca-app-pub-3080914425533160~1647919339");
        final EditText Edit_Time = (EditText) findViewById(R.id.hora);
        Edit_Time.setOnClickListener(this);
        final EditText Edit_Data = (EditText) findViewById(R.id.data);
        Edit_Data.setOnClickListener(this);
        AdView adView = (AdView)findViewById(R.id.adView2);
        //adView.setAdSize(AdSize.BANNER);
        //adView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");
        //adView = (AdView)findViewById(R.id.adView);
        //AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        mudaResultado();
    }

    private void loadAd(){
        if (!mAd.isLoaded()){
            mAd.loadAd("ca-app-pub-3080914425533160/2918856138",new AdRequest.Builder().build());
        }
    }

    public void onRewardedVideoAdLoaded(){
        //mWatchButton.setEnabled(true);
    }

    @Override
    public void onRewardedVideoAdOpened() {

    }

    @Override
    public void onRewardedVideoStarted() {

    }

    @Override
    public void onRewardedVideoAdClosed() {

    }

    @Override
    public void onRewarded(RewardItem rewardItem) {

    }

    @Override
    public void onRewardedVideoAdLeftApplication() {

    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {

    }


    @Override
        public void onClick(View v){
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            } else {
                Log.d("TAG", "The interstitial wasn't loaded yet.");
            }
            /*if (mAd.isLoaded()){
                mAd.show();
            }*/
            switch (v.getId()) {
                case R.id.hora:
                    Calendar mcurrentTime = Calendar.getInstance();
                    int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                    int minute = mcurrentTime.get(Calendar.MINUTE);

                    TimePickerDialog mTimePicker;
                    mTimePicker = new TimePickerDialog(Nascimento_input.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                            if (selectedMinute>10){
                            ((EditText) findViewById(R.id.hora)).setText(selectedHour + ":" + selectedMinute);}
                            else{((EditText) findViewById(R.id.hora)).setText(selectedHour + ":0" + selectedMinute);}
                            mudaResultado();
                        }
                    }, hour, minute, true);
                    mTimePicker.setTitle("Que horas você nasceu?");
                    mTimePicker.show();
                    break;
                case R.id.data:
                    Calendar mcurrentDay = Calendar.getInstance();
                    int ano = mcurrentDay.get(Calendar.YEAR);
                    int mes = mcurrentDay.get(Calendar.MONTH);
                    int dia = mcurrentDay.get(Calendar.DAY_OF_MONTH);
                    DatePickerDialog mDatePicker;
                    mDatePicker = new DatePickerDialog(Nascimento_input.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int selectedYear, int selectedMounth, int selectedDay) {
                            ((EditText) findViewById(R.id.data)).setText(selectedDay + "/" + (selectedMounth+1)+"/"+selectedYear);
                            mudaResultado();
                        }
                    }, ano, mes, dia);
                    mDatePicker.setTitle("Que dia você nasceu?");
                    mDatePicker.show();
                    break;
                default:
                    break;
            }
        }

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
            String diaIn=((TextView) findViewById(R.id.data)).getText().toString();
            int dia1= Integer.parseInt(diaIn.substring(0,diaIn.indexOf("/")));
            diaIn=diaIn.substring(diaIn.indexOf("/")+1);
            int mes1= Integer.parseInt(diaIn.substring(0,diaIn.indexOf("/")))-1;
            int ano1=Integer.parseInt(diaIn.substring(diaIn.indexOf("/")+1));
            String horaIn=((TextView) findViewById(R.id.hora)).getText().toString();
            int hora1= Integer.parseInt(horaIn.substring(0,horaIn.indexOf(":")));
            int minuto1=Integer.parseInt(horaIn.substring(horaIn.indexOf(":")+1));
            calendar = new GregorianCalendar(ano1,mes1,dia1,hora1,minuto1);
            double nasceu = GetJulianDate(calendar);
            ((TextView) findViewById(R.id.Dias)).setText(String.valueOf((int) (atual-nasceu)));
            ((TextView) findViewById(R.id.Meses)).setText(String.valueOf((int) (atual/30.4375-nasceu/30.4375)));
            ((TextView) findViewById(R.id.Semanas)).setText(String.valueOf((int) (atual/7-nasceu/7)));
            ((TextView) findViewById(R.id.Horas)).setText(String.valueOf((int) (atual*24-nasceu*24)));
            ((TextView) findViewById(R.id.Minutos)).setText(String.valueOf((int) (atual*1440-nasceu*1440)));
            ((TextView) findViewById(R.id.Segundos)).setText(String.valueOf((int) (atual*86400-nasceu*86400)));
        }


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