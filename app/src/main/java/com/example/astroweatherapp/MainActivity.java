package com.example.astroweatherapp;

import android.content.SharedPreferences;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.astrocalculator.AstroCalculator;
import com.astrocalculator.AstroDateTime;
import com.example.astroweatherapp.Fragments.PageFragment1;
import com.example.astroweatherapp.Fragments.PageFragment2;
import com.example.astroweatherapp.Fragments.PageFragment3;
import com.rd.PageIndicatorView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements PageFragment3.OnMessageReadListener {
    private TextView textViewSunLong, textViewSunLat, textViewMoonLon, textViewMoonLat, moonPhase, phase;
    private ViewPager pager;
    private PagerAdapter pagerAdapter;

    private PageIndicatorView pageIndicatorView;

    private TextView sunriseResult, sunsetResult, moonriseResult, moonsetResult, moonFull, moonNew, civilTwilight, civilDawn;
    public TextView lat, lon;
    public boolean flaga;
    public String message;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new PageFragment1());
        fragmentList.add(new PageFragment3());
        fragmentList.add(new PageFragment2());

        pager = findViewById(R.id.pager);
        pagerAdapter = new SlidePageAdapter(getSupportFragmentManager(), fragmentList);

        pager.setAdapter(pagerAdapter);
        pageIndicatorView = findViewById(R.id.pageIndicatorView);

        pager.setCurrentItem(1);

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {/*empty*/}

            @Override
            public void onPageSelected(int position) {
                pageIndicatorView.setSelection(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {/*empty*/}
        });
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        pager.setOffscreenPageLimit(3);

        //textView is a TextView from page_1 which displays what was entered in two EditTexts from page_3
        if (findViewById(R.id.longi) != null && findViewById(R.id.lati) != null && findViewById(R.id.longiResult) != null && findViewById(R.id.latiResult) != null) {
            if(savedInstanceState!=null){
                return;
            }
            //Adding transaction performed after clicking the button from page_3
            PageFragment3 pageFragment3 = new PageFragment3();
            FragmentTransaction fragmentTransaction1 = getSupportFragmentManager().beginTransaction().add(R.id.longi, pageFragment3);
            FragmentTransaction fragmentTransaction2 = getSupportFragmentManager().beginTransaction().add(R.id.lati, pageFragment3);
            FragmentTransaction fragmentTransaction3 = getSupportFragmentManager().beginTransaction().add(R.id.longiResult, pageFragment3);
            FragmentTransaction fragmentTransaction4 = getSupportFragmentManager().beginTransaction().add(R.id.latiResult, pageFragment3);
            fragmentTransaction1.commit();
            fragmentTransaction2.commit();
            fragmentTransaction3.commit();
            fragmentTransaction4.commit();
        }
    }


    @Override
    public void onSaveInstanceState (Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        sunriseResult = findViewById(R.id.sunriseResult);
        sunsetResult = findViewById(R.id.sunsetResult);
        civilDawn = findViewById(R.id.civilDawn);
        civilTwilight = findViewById(R.id.civilTwilight);
        moonPhase = findViewById(R.id.phase);
        phase = findViewById(R.id.sinodicDay);
        moonriseResult = findViewById(R.id.moonriseResult);
        moonsetResult = findViewById(R.id.moonsetResult);
        moonNew = findViewById(R.id.newMoon);
        moonFull = findViewById(R.id.fullMoon);
        if(flaga==true) {
            savedInstanceState.putString("sunrise", sunriseResult.getText().toString());
            savedInstanceState.putString("sunset", sunsetResult.getText().toString());
            savedInstanceState.putString("civilDawn", civilDawn.getText().toString());
            savedInstanceState.putString("civilTwilight", civilTwilight.getText().toString());
            savedInstanceState.putString("moonPhase", moonPhase.getText().toString());
            savedInstanceState.putString("phase", phase.getText().toString());
            savedInstanceState.putString("moonrise", moonriseResult.getText().toString());
            savedInstanceState.putString("moonset", moonsetResult.getText().toString());
            savedInstanceState.putString("moonNew", moonNew.getText().toString());
            savedInstanceState.putString("moonFull", moonFull.getText().toString());
            savedInstanceState.putString("message", message);
            savedInstanceState.putBoolean("flaga", flaga);

        }

    }

    @Override
    public void onRestoreInstanceState (Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        sunriseResult = findViewById(R.id.sunriseResult);
        sunsetResult = findViewById(R.id.sunsetResult);
        civilDawn = findViewById(R.id.civilDawn);
        civilTwilight = findViewById(R.id.civilTwilight);
        moonPhase = findViewById(R.id.phase);
        phase = findViewById(R.id.sinodicDay);
        moonriseResult = findViewById(R.id.moonriseResult);
        moonsetResult = findViewById(R.id.moonsetResult);
        moonNew = findViewById(R.id.newMoon);
        moonFull = findViewById(R.id.fullMoon);
        sunriseResult.setText(savedInstanceState.getString("sunrise"));
        sunsetResult.setText(savedInstanceState.getString("sunset"));
        civilDawn.setText(savedInstanceState.getString("civilDawn"));
        civilTwilight.setText(savedInstanceState.getString("civilTwilight"));
        moonPhase.setText(savedInstanceState.getString("moonPhase"));
        phase.setText(savedInstanceState.getString("phase"));
        moonriseResult.setText(savedInstanceState.getString("moonrise"));
        moonsetResult.setText(savedInstanceState.getString("moonset"));
        moonNew.setText(savedInstanceState.getString("moonNew"));
        moonFull.setText(savedInstanceState.getString("moonFull"));
        message=savedInstanceState.getString("message");
        flaga=savedInstanceState.getBoolean("flaga");

    }

    private AstroDateTime getAstroDateTime() {
        long deviceDate = System.currentTimeMillis();
        int year = Integer.parseInt(new SimpleDateFormat("yyyy", Locale.ENGLISH).format(deviceDate));
        int month = Integer.parseInt(new SimpleDateFormat("MM", Locale.ENGLISH).format(deviceDate));
        int day = Integer.parseInt(new SimpleDateFormat("dd", Locale.ENGLISH).format(deviceDate));
        int hour = Integer.parseInt(new SimpleDateFormat("hh", Locale.GERMANY).format(deviceDate));
        int minute = Integer.parseInt(new SimpleDateFormat("mm", Locale.GERMANY).format(deviceDate));
        int second = Integer.parseInt(new SimpleDateFormat("ss", Locale.GERMANY).format(deviceDate));
        int timeZoneOffset = 1;
        boolean dayLightSaving = true;
        return new AstroDateTime(year, month, day, hour, minute, second, timeZoneOffset, dayLightSaving);
    }

    public static double round(double val, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        val = val * factor;
        long tmp = Math.round(val);
        return (double) tmp / factor;
    }

    @Override
    public void onMessageRead(String message) {
        String[] longAndLat = message.split("/");
        flaga=true;
        textViewSunLong = findViewById(R.id.lati);
        textViewSunLong.setText(longAndLat[0]);

        textViewSunLat = findViewById(R.id.longi);
        textViewSunLat.setText(longAndLat[1]);

        textViewMoonLon = findViewById(R.id.longiResult);
        textViewMoonLon.setText(longAndLat[0]);

        textViewMoonLat = findViewById(R.id.latiResult);
        textViewMoonLat.setText(longAndLat[1]);

        sunriseResult = findViewById(R.id.sunriseResult);
        sunsetResult = findViewById(R.id.sunsetResult);
        civilDawn = findViewById(R.id.civilDawn);
        civilTwilight = findViewById(R.id.civilTwilight);
        moonPhase = findViewById(R.id.phase);
        phase = findViewById(R.id.sinodicDay);


        moonriseResult = findViewById(R.id.moonriseResult);
        moonsetResult = findViewById(R.id.moonsetResult);

        moonNew = findViewById(R.id.newMoon);
        moonFull = findViewById(R.id.fullMoon);

        lat = findViewById(R.id.lati);
        lon = findViewById(R.id.longi);

        AstroCalculator.Location location;
        AstroCalculator astroCalculator;

        location = new AstroCalculator.Location(Double.valueOf(lat.getText().toString()), Double.valueOf(lon.getText().toString()));
        astroCalculator = new AstroCalculator(getAstroDateTime(),location);

        Double illumination = round(astroCalculator.getMoonInfo().getIllumination(),2);

        String getSunrisegetHour = String.valueOf(astroCalculator.getSunInfo().getSunrise().getHour()-1);
        if (Integer.valueOf(getSunrisegetHour) < 10) { getSunrisegetHour = "0" + getSunrisegetHour; }
        String getSunrisegetMinute = String.valueOf(astroCalculator.getSunInfo().getSunrise().getMinute());
        if (Integer.valueOf(getSunrisegetMinute) < 10) { getSunrisegetMinute = "0" + getSunrisegetMinute; }
        String getSunrisegetSecond = String.valueOf(astroCalculator.getSunInfo().getSunrise().getSecond());
        if (Integer.valueOf(getSunrisegetSecond) < 10) { getSunrisegetSecond = "0" + getSunrisegetSecond; }

        sunriseResult.setText("Sunrise at: " + "\n" + String.valueOf(getSunrisegetHour + ":" + getSunrisegetMinute + ":" + getSunrisegetSecond + " | Azimuth: " + Math.round(astroCalculator.getSunInfo().getAzimuthRise()) + "°"));

        /*----------------------------------------------------------------------------------------------------------------------------*/

        String getSunsetgetHour = String.valueOf(astroCalculator.getSunInfo().getSunset().getHour()-1);
        if (Integer.valueOf(getSunsetgetHour) < 10) { getSunsetgetHour = "0" + getSunsetgetHour; }
        String getSunsetgetMinute = String.valueOf(astroCalculator.getSunInfo().getSunset().getMinute());
        if (Integer.valueOf(getSunsetgetMinute) < 10) { getSunsetgetMinute = "0" + getSunsetgetMinute; }
        String getSunsetgetSecond = String.valueOf(astroCalculator.getSunInfo().getSunset().getSecond());
        if (Integer.valueOf(getSunsetgetSecond) < 10) { getSunsetgetSecond = "0" + getSunsetgetSecond; }

        sunsetResult.setText("Sunset at: " + "\n"  + String.valueOf(getSunsetgetHour + ":" + getSunsetgetMinute + ":" + getSunsetgetSecond + " | Azimuth: " + Math.round(astroCalculator.getSunInfo().getAzimuthSet()) + "°"));

        /*----------------------------------------------------------------------------------------------------------------------------*/

        String getTwilightEveninggetHour = String.valueOf(astroCalculator.getSunInfo().getTwilightEvening().getHour()-1);
        if (Integer.valueOf(getTwilightEveninggetHour) < 10) { getTwilightEveninggetHour = "0" + getTwilightEveninggetHour; }
        String getTwilightEveninggetMinute = String.valueOf(astroCalculator.getSunInfo().getTwilightEvening().getMinute());
        if (Integer.valueOf(getTwilightEveninggetMinute) < 10) { getTwilightEveninggetMinute = "0" + getTwilightEveninggetMinute; }
        String getTwilightEveninggetSecond = String.valueOf(astroCalculator.getSunInfo().getTwilightEvening().getSecond());
        if (Integer.valueOf(getTwilightEveninggetSecond) < 10) { getTwilightEveninggetSecond = "0" + getTwilightEveninggetSecond; }

        civilTwilight.setText("Civil Twilight at: " + "\n"  + String.valueOf(getTwilightEveninggetHour + ":" + getTwilightEveninggetMinute + ":" + getTwilightEveninggetSecond));

        /*----------------------------------------------------------------------------------------------------------------------------*/

        String getTwilightMorninggetHour = String.valueOf(astroCalculator.getSunInfo().getTwilightMorning().getHour()-1);
        if (Integer.valueOf(getTwilightMorninggetHour) < 10) { getTwilightMorninggetHour = "0" + getTwilightMorninggetHour; }
        String getTwilightMorningetMinute = String.valueOf(astroCalculator.getSunInfo().getTwilightMorning().getMinute());
        if (Integer.valueOf(getTwilightMorningetMinute) < 10) { getTwilightMorningetMinute = "0" + getTwilightMorningetMinute; }
        String getTwilightMorninggetSecond = String.valueOf(astroCalculator.getSunInfo().getTwilightMorning().getSecond());
        if (Integer.valueOf(getTwilightMorninggetSecond) < 10) { getTwilightMorninggetSecond = "0" + getTwilightMorninggetSecond; }

        civilDawn.setText("Civil Dawn at: " + "\n"  + String.valueOf(getTwilightMorninggetHour + ":" + getTwilightMorningetMinute + ":" + getTwilightMorninggetSecond));

        /*----------------------------------------------------------------------------------------------------------------------------*/

        String getMoonrisegetHour = String.valueOf(astroCalculator.getMoonInfo().getMoonrise().getHour()-1);
        if (Integer.valueOf(getMoonrisegetHour) < 10) { getMoonrisegetHour = "0" + getMoonrisegetHour; }
        String getMoonrisegetMinute = String.valueOf(astroCalculator.getMoonInfo().getMoonrise().getMinute());
        if (Integer.valueOf(getMoonrisegetMinute) < 10) { getMoonrisegetMinute = "0" + getMoonrisegetMinute; }
        String getMoonrisegetSecond = String.valueOf(astroCalculator.getMoonInfo().getMoonrise().getSecond());
        if (Integer.valueOf(getMoonrisegetSecond) < 10) { getMoonrisegetSecond = "0" + getMoonrisegetSecond; }

        moonriseResult.setText("Civil Dawn at: " + "\n"  + String.valueOf(getMoonrisegetHour + ":" + getMoonrisegetMinute + ":" + getMoonrisegetSecond));

        /*----------------------------------------------------------------------------------------------------------------------------*/

        String getMoonsetgetHour = String.valueOf(astroCalculator.getMoonInfo().getMoonset().getHour()-1);
        if (Integer.valueOf(getMoonsetgetHour) < 10) { getMoonsetgetHour = "0" + getMoonsetgetHour; }
        String getMoonsetgetMinute = String.valueOf(astroCalculator.getMoonInfo().getMoonset().getMinute());
        if (Integer.valueOf(getMoonsetgetMinute) < 10) { getMoonsetgetMinute = "0" + getMoonsetgetMinute; }
        String getMoonsetgetSecond = String.valueOf(astroCalculator.getMoonInfo().getMoonset().getSecond());
        if (Integer.valueOf(getMoonsetgetSecond) < 10) { getMoonsetgetSecond = "0" + getMoonsetgetSecond; }

        moonsetResult.setText("Moonset at: " + "\n" + String.valueOf(getMoonsetgetHour + ":" + getMoonsetgetMinute + ":" + getMoonsetgetSecond));

        /*----------------------------------------------------------------------------------------------------------------------------*/

        String getNextFullMoongetYear = String.valueOf(astroCalculator.getMoonInfo().getNextFullMoon().getYear());
        if (Integer.valueOf(getMoonsetgetHour) < 10) { getMoonsetgetHour = "0" + getMoonsetgetHour; }
        String getNextFullMoongetMonth = String.valueOf(astroCalculator.getMoonInfo().getNextFullMoon().getMonth());
        if (Integer.valueOf(getMoonsetgetMinute) < 10) { getMoonsetgetMinute = "0" + getMoonsetgetMinute; }
        String getNextFullMoongetDay = String.valueOf(astroCalculator.getMoonInfo().getNextFullMoon().getDay());
        if (Integer.valueOf(getMoonsetgetSecond) < 10) { getMoonsetgetSecond = "0" + getMoonsetgetSecond; }

        moonFull.setText("Full Moon on: " + "\n" + String.valueOf(getNextFullMoongetYear + "." + getNextFullMoongetMonth + "." + getNextFullMoongetDay));

        /*----------------------------------------------------------------------------------------------------------------------------*/

        String getNextNewMoongetYear = String.valueOf(astroCalculator.getMoonInfo().getNextNewMoon().getYear());
        if (Integer.valueOf(getNextNewMoongetYear) < 10) { getNextNewMoongetYear = "0" + getNextNewMoongetYear; }
        String getNextNewMoongetMonth = String.valueOf(astroCalculator.getMoonInfo().getNextNewMoon().getMonth());
        if (Integer.valueOf(getNextNewMoongetMonth) < 10) { getNextNewMoongetMonth = "0" + getNextNewMoongetMonth; }
        String getNextNewMoongetDay = String.valueOf(astroCalculator.getMoonInfo().getNextNewMoon().getDay());
        if (Integer.valueOf(getNextNewMoongetDay) < 10) { getNextNewMoongetDay = "0" + getNextNewMoongetDay; }

        moonNew.setText("New Moon on: " + "\n" + String.valueOf(getNextNewMoongetYear + "." + getNextNewMoongetMonth + "." + getNextNewMoongetDay));

        moonPhase.setText("Phase: " + "\n" + String.valueOf(Math.round(illumination * 100.0) + "%"));
        phase.setText((int) astroCalculator.getMoonInfo().getAge() + "th synodic day");
    }
}


